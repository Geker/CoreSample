package cn.openlo.batchCheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.alibaba.dubbo.common.json.JSON;

import cn.tools.compress.CompressUtils;
import cn.tools.csv.CsvWriter;

public class BatchBakClean {
    private static final String addr = "127.0.0.1:2181";
    private static final String groupName = "local";
    private static final int keepDays = 3;
    // private static final ACL ACL = null;
    private static String expPath = null;

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("batch.conf"));
        }
        catch (IOException e) {
            System.err.println("加载配置文件batch.conf 失败 " + e);
        }
        String zkAddr = (String) properties.get("zk");
        String batchGrp = (String) properties.get("batchGrp");
        String _days = (String) properties.get("keepDays");
        expPath = (String) properties.get("expPath");

        Integer conf_keepdays = (_days == null ? null : Integer.parseInt(_days));

        String addrs = (zkAddr == null ? addr : zkAddr);
        String grpNames = (batchGrp == null ? groupName : batchGrp);
        Integer _keepdays = (conf_keepdays == null ? keepDays : conf_keepdays);
        String zkuser = (String) properties.get("zkuser");
        String zkpass = (String) properties.get("zkpass");
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(addrs + "/_lo/openlo-batch-schedule")
            .sessionTimeoutMs(3000).connectionTimeoutMs(3000).retryPolicy(new RetryOneTime(10))
            .authorization("digest", (zkuser + ":" + zkpass).getBytes()).build();

        ACL aclDigest =
                new ACL(Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest(zkuser.trim() + ":" + zkpass.trim())));
        List<org.apache.zookeeper.data.ACL> aclList = Arrays.asList(aclDigest);
        // CuratorFramework client = CuratorFrameworkFactory.newClient(addrs + "/_lo/openlo-batch-schedule", 3000, 3000, new
        // RetryOneTime(1));
        client.start();
        client.setACL().withACL(aclList);

        String[] grps = StringUtils.split(grpNames, ',');
        for (String grpName : grps) {
            String grp = "/BatchGrp-" + grpName;
            try {
                dumpServerInfos(grp, client, _keepdays);
                dumpTaskExecutions(grp, client, _keepdays);

            }
            catch (Exception e) {
                System.err.println(grp + " bak fail!");
                e.printStackTrace();
            }
        }
        client.close();
        System.err.println("all bak finish!");
    }

    private static void dumpServerInfos(String grp, CuratorFramework client, Integer _keepdays) throws Exception {
        String svrPath = grp + "/" + "Servers";

        System.err.println("start bak ServerInfo :" + svrPath);
        String filepath = (expPath != null ? expPath : "./bak") + File.separator + DateTime.now().toString("yyyyMMdd");
        String filename = filepath + File.separator + grp + "." + "ServerInfo.csv";

        Iterator<String> itorServer = client.getChildren().forPath(svrPath).iterator();

        while (itorServer.hasNext()) {
            String server = itorServer.next();
            if ("MASTER".equals(server))
                continue;
            String serverPath = svrPath + "/" + server;
            Iterator<String> itorSvrDate = client.getChildren().forPath(serverPath).iterator();
            while (itorSvrDate.hasNext()) {
                String date = itorSvrDate.next();
                if ("SERVER_STATUS".equals(date))
                    continue;
                if (!checkDate(date, _keepdays))
                    continue;
                String svrdatePath = serverPath + "/" + date;
                Iterator<String> itorSvrDateTask = client.getChildren().forPath(svrdatePath).iterator();
                List<ServerInfo> serverinfos = new ArrayList<>();

                while (itorSvrDateTask.hasNext()) {
                    String taskName = itorSvrDateTask.next();
                    if ("__NEXT_DAY__".equals(taskName))
                        continue;
                    String taskRunsPath = svrdatePath + "/" + taskName;
                    Iterator<String> itorTaskRuns = client.getChildren().forPath(taskRunsPath).iterator();
                    while (itorTaskRuns.hasNext()) {
                        try {
                            String runkey = itorTaskRuns.next();
                            String svrtaskrunPath = taskRunsPath + "/" + runkey;
                            byte[] data = client.getData().forPath(svrtaskrunPath);
                            String content = new String(data, "UTF-8");
                            TaskExecutions2ServerInfo info;
                            try {
                                info = JSON.parse(content, TaskExecutions2ServerInfo.class);
                            }
                            catch (Exception e) {
                                System.err.println("parse Error!!!" + svrtaskrunPath + " " + content);
                                continue;
                            }
                            ServerInfo svrInfo = new ServerInfo();
                            svrInfo.setRunkey(runkey);
                            svrInfo.setServer(server);
                            svrInfo.setStatus(info.getStatus().toString());
                            svrInfo.setSubpath(info.getSubPath());
                            svrInfo.setTaskname(taskName);
                            svrInfo.setTransdate(date);
                            serverinfos.add(svrInfo);
                        }
                        catch (Exception e) {
                            System.err.println("error!" + e.getMessage());
                        }
                    }

                }
                CsvWriter.writeWithCsvWriter(filename, serverinfos);

            }
        }
        System.err.println("finish bak ServerInfo :" + svrPath);

        File file = new File(filename);
        if (file.exists()) {
            File zipfile = new File(filename + ".zip");
            CompressUtils.ZipFiles(new File[] { file }, zipfile);
            FileUtils.deleteQuietly(file);
        }
        //
        // appZip.generateFileList(file.getCanonicalFile().toString());
        // appZip.zipIt(file.getCanonicalFile().toString() + ".zip");
    }

    private static void dumpTaskExecutions(String grp, CuratorFramework client, Integer _keepdays) throws Exception {
        String exePath = grp + "/" + "TaskExecutions";

        System.err.println("start bak  TaskExeInfo :" + exePath);
        String filepath = (expPath != null ? expPath : "./bak") + File.separator + DateTime.now().toString("yyyyMMdd");
        String file_TaskExecutionInfo = filepath + File.separator + grp + "." + "TaskExecutionInfo.csv";
        String file_TaskExecutionDetail = filepath + File.separator + grp + "." + "TaskExecutionDetail.csv";

        Iterator<String> itorSystem = client.getChildren().forPath(exePath).iterator();
        while (itorSystem.hasNext()) {
            String system = itorSystem.next();

            String sysPath = exePath + "/" + system;
            Iterator<String> itorDate = client.getChildren().forPath(sysPath).iterator();
            while (itorDate.hasNext()) {
                String date = itorDate.next();
                // if (!"20161117".equals(date))
                // continue;
                if (!checkDate(date, _keepdays))
                    continue;
                String datePath = sysPath + "/" + date;
                Iterator<String> itorTask = client.getChildren().forPath(datePath).iterator();
                while (itorTask.hasNext()) {
                    String taskName = itorTask.next();
                    String taskPath = datePath + "/" + taskName;
                    List<TaskExecutionInfo> taskInfos = new ArrayList<>();
                    List<TaskExecutionDetail> taskDetailslist = new ArrayList<>();
                    Iterator<String> itorRunkey = client.getChildren().forPath(taskPath).iterator();
                    while (itorRunkey.hasNext()) {
                        String runkey = itorRunkey.next();
                        String runkeyPath = taskPath + "/" + runkey;
                        if (client.checkExists().forPath(runkeyPath) == null)
                            continue;
                        Iterator<String> itorRunkeySeq = client.getChildren().forPath(runkeyPath).iterator();
                        while (itorRunkeySeq.hasNext()) {
                            try {

                                String runkeyseq = itorRunkeySeq.next();
                                String runkeySeqPath = runkeyPath + "/" + runkeyseq;
                                byte[] data = client.getData().forPath(runkeySeqPath);
                                String dataStr = new String(data, "UTF-8");
                                ExecutionTaskInfo task;
                                try {
                                    task = JSON.parse(dataStr, ExecutionTaskInfo.class);
                                }
                                catch (Exception e) {
                                    System.err.println("parse error!!!" + runkeySeqPath + " " + dataStr);
                                    continue;
                                }
                                TaskExecutionInfo info = new TaskExecutionInfo();
                                info.setRunkey(runkey + runkeyseq);// 将runkey_uninSeq合并作为唯一的runkey值
                                info.setRunkey_value(dataStr.replaceAll("\\s+", " "));
                                info.setStatus(task.getStatus().toString());
                                info.setSystemname(system);
                                info.setTaskname(taskName);
                                info.setTransdate(date);
                                taskInfos.add(info);
                                List<TaskExecutionDetail> info_details = getExecutionDetails(client, runkeySeqPath, runkey + runkeyseq);
                                taskDetailslist.addAll(info_details);
                            }
                            catch (Exception e) {
                                System.err.println(e.getMessage());
                            }
                        }

                    }
                    CsvWriter.writeWithCsvWriter(file_TaskExecutionInfo, taskInfos);
                    CsvWriter.writeWithCsvWriter(file_TaskExecutionDetail, taskDetailslist);

                }

            }

        }
        File file = new File(file_TaskExecutionInfo);
        if (file.exists()) {
            File zipfile = new File(file_TaskExecutionInfo + ".zip");
            CompressUtils.ZipFiles(new File[] { file }, zipfile);
            FileUtils.deleteQuietly(file);
        }
        File filedetail = new File(file_TaskExecutionDetail);
        if (filedetail.exists()) {
            File zipfiledetail = new File(file_TaskExecutionDetail + ".zip");
            CompressUtils.ZipFiles(new File[] { filedetail }, zipfiledetail);

            FileUtils.deleteQuietly(filedetail);
        }
        System.err.println("finish bak  TaskExeInfo :" + exePath);

    }

    private static List<TaskExecutionDetail> getExecutionDetails(CuratorFramework client, String runkeyPath, String runkey)
            throws Exception {

        List<TaskExecutionDetail> details = new ArrayList<>();
        try {
            Iterator<String> itorRunkeyDivide = client.getChildren().forPath(runkeyPath).iterator();
            while (itorRunkeyDivide.hasNext()) {
                String divide = itorRunkeyDivide.next();
                String dividePath = runkeyPath + "/" + divide;
                byte[] divBytes = client.getData().forPath(dividePath);
                String divStr = new String(divBytes, "UTF-8");
                DivideTaskInfo divideInfo = JSON.parse(divStr, DivideTaskInfo.class);
                Iterator<String> itorStep = client.getChildren().forPath(dividePath).iterator();
                while (itorStep.hasNext()) {
                    String step = itorStep.next();
                    String stepPath = dividePath + "/" + step;
                    byte[] stepBytes = client.getData().forPath(stepPath);
                    String stepStr = new String(stepBytes, "UTF-8");
                    StepTaskInfo stepInfo = JSON.parse(stepStr, StepTaskInfo.class);
                    Iterator<String> itorJob = client.getChildren().forPath(stepPath).iterator();
                    while (itorJob.hasNext()) {
                        String job = itorJob.next();
                        String jobPath = stepPath + "/" + job;
                        byte[] jobBytes = client.getData().forPath(jobPath);
                        String jobStr = new String(jobBytes, "UTF-8");
                        JobTaskRunItem jobItem = JSON.parse(jobStr, JobTaskRunItem.class);
                        TaskExecutionDetail detail = new TaskExecutionDetail();
                        detail.setRunkey(runkey);
                        detail.setDivide(divide);
                        detail.setDivide_value(divStr.replaceAll("\\s+", " "));
                        detail.setDivide_status(divideInfo.getStatus().toString());
                        detail.setStep(step);
                        detail.setStep_value(stepStr.replaceAll("\\s+", " "));
                        detail.setStep_status(stepInfo.getCurStatus().toString());
                        if (StringUtils.isNotEmpty(stepInfo.getFinishMsg()))
                            detail.setStep_finishMsg(stepInfo.getFinishMsg().replaceAll("\\s+", " "));
                        detail.setJob(job);
                        detail.setJob_value(jobStr.replaceAll("\\s+", " "));
                        detail.setJob_status(jobItem.getStatus().toString());
                        details.add(detail);
                    }

                }

            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return details;

    }

    /**
     * 判断日期提前几天
     *
     * @param date
     * @return
     */
    public static boolean checkDate(String date, int days) {
        LocalDate dt = LocalDate.parse(date, DateTimeFormat.forPattern("yyyyMMdd"));
        LocalDate ldate = LocalDate.now();
        return ldate.minusDays(days).isAfter(dt);

    }

}
