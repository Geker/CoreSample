package cn.openlo.batchCheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZKUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.zk.back.DumpJob;


public class BatchDel {
    private static final String addr = "127.0.0.1:2181";
    private static final String groupName = "local";
    private static final int keepDays = 3;
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
            .sessionTimeoutMs(3000).connectionTimeoutMs(3000).retryPolicy(new RetryOneTime(1))
            .authorization("digest", (zkuser.trim() + ":" + zkpass.trim()).getBytes()).build();
        client.start();
        String[] grps = StringUtils.split(grpNames, ',');
        for (String grpName : grps) {
            String grp = "/BatchGrp-" + grpName;
            try {
            // dumpTaskExecutionAndServerInfos(grp, client, _keepdays);
            delTaskExecutions(grp, client, _keepdays);
            delServerExectionInfos(grp, client, _keepdays);
            }
            catch (Exception e) {
                System.err.println(grp + " del fail!");
                e.printStackTrace();
            }
        }
        client.close();
        System.err.println("finish!");
    }

    /**
     * dump为文件的方法，暂时未用。文件太多无法处理。
     *
     * @param grp
     * @param client
     * @param _keepdays
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private static void dumpTaskExecutionAndServerInfos(String grp, CuratorFramework client, Integer _keepdays) throws Exception {
        if ("none".equalsIgnoreCase(expPath))
            return;
        System.err.println(DateTime.now().toString() + " dumpServerInfos start!");

        String svrPath = grp + "/" + "Servers";
        String filepath = (expPath != null ? expPath : "./bak") + File.separator + DateTime.now().toString("yyyyMMdd");
        DumpJob serverInfoJob = new DumpJob(client.getZookeeperClient().getZooKeeper(), filepath, svrPath, _keepdays);
        serverInfoJob.go();
        System.err.println(DateTime.now().toString() + " dumpServerInfos Finish!");
        System.err.println(DateTime.now().toString() + " TaskExecution start!");

        String grpPath = grp + "/" + "TaskExecutions";
        DumpJob jobExecution = new DumpJob(client.getZookeeperClient().getZooKeeper(), filepath, grpPath, _keepdays);
        jobExecution.go();
        System.err.println(DateTime.now().toString() + " TaskExecution Finish!");

    }

    /**
     * 暂时未用，executing在check中进行了删除。
     *
     * @param grp
     * @param client
     * @param _keepdays
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private static void delTaskExecutings(String grp, CuratorFramework client, Integer _keepdays) throws Exception {
        if ("none".equalsIgnoreCase(expPath))
            return;
        System.err.println(DateTime.now().toString() + " dumpServerInfos start!");

        String svrPath = grp + "/" + "Servers";
        String filepath = (expPath != null ? expPath : "./bak") + File.separator + DateTime.now().toString("yyyyMMdd");
        DumpJob serverInfoJob = new DumpJob(client.getZookeeperClient().getZooKeeper(), filepath, svrPath, _keepdays);
        serverInfoJob.go();
        System.err.println(DateTime.now().toString() + " dumpServerInfos Finish!");
        System.err.println(DateTime.now().toString() + " TaskExecution start!");

        String grpPath = grp + "/" + "TaskExecutions";
        DumpJob jobExecution = new DumpJob(client.getZookeeperClient().getZooKeeper(), filepath, grpPath, _keepdays);
        jobExecution.go();
        System.err.println(DateTime.now().toString() + " TaskExecution Finish!");

    }

    private static void delServerExectionInfos(String grp, CuratorFramework client, Integer keepdays)
            throws Exception, InterruptedException, KeeperException {
        String svrPath = grp + "/" + "Servers";

        System.err.println("start delete Svr ExeInfo :" + svrPath);

        Iterator<String> itorServer = client.getChildren().forPath(svrPath).iterator();
        while (itorServer.hasNext()) {
            String server = new String(itorServer.next().getBytes());
            if ("MASTER".equals(server))
                continue;
            String serverPath = svrPath + "/" + server;
            Iterator<String> itorSvr = client.getChildren().forPath(serverPath).iterator();
            while (itorSvr.hasNext()) {
                String date = new String(itorSvr.next().getBytes());
                if ("SERVER_STATUS".equals(date))
                    continue;
                if (!checkDate(date, keepdays))
                    continue;
                String svrdatePath = serverPath + "/" + date;
                long start = System.currentTimeMillis();
                List<String> list = ZKUtil.listSubTreeBFS(client.getZookeeperClient().getZooKeeper(), svrdatePath);
                // Collections.sort(list,Collections.<String>reverseOrder());
                long read = System.currentTimeMillis();

                System.err.println("[" + (read - start) + "]" + "path children count:" + list.size() + "     -" + svrdatePath);

                for (int i = list.size() - 1; i >= 0; --i) {
                    // Delete the leaves first and eventually get rid of the root
                    try {
                        client.delete().forPath(list.get(i));// Delete all versions of the node with -1.
                    }
                    catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                long del = System.currentTimeMillis();

                System.err.println("[" + (del - read) + "]" + "success delete Svr ExeInfo -" + svrdatePath);
            }
        }
    }

    private static void delTaskExecutions(String grp, CuratorFramework client, Integer keepdays)
            throws Exception, InterruptedException, KeeperException {
        String grpPath = grp + "/" + "TaskExecutions";
        System.err.println("start delete TaskExecutions :" + grpPath);
        Iterator<String> itorSystem = client.getChildren().forPath(grpPath).iterator();
        while (itorSystem.hasNext()) {
            String system = new String(itorSystem.next().getBytes());
            String sysPath = grpPath + "/" + system;
            Iterator<String> itorDate = client.getChildren().forPath(sysPath).iterator();
            while (itorDate.hasNext()) {
                String date = new String(itorDate.next().getBytes());
                if (!checkDate(date, keepdays))
                    continue;
                String datePath = sysPath + "/" + date;
                long start = System.currentTimeMillis();
                List<String> list = ZKUtil.listSubTreeBFS(client.getZookeeperClient().getZooKeeper(), datePath);
                // Collections.sort(list,Collections.<String>reverseOrder());
                long read = System.currentTimeMillis();
                System.err.println("[" + (read - start) + "]" + "path children count:" + list.size() + "        -" + datePath);

                for (int i = list.size() - 1; i >= 0; --i) {
                    // Delete the leaves first and eventually get rid of the root
                    try {
                        client.delete().forPath(list.get(i));// Delete all versions of the node with -1.
                    }
                    catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                long del = System.currentTimeMillis();
                System.err.println("[" + (del - read) + "]" + "success delete TaskExecutions  -" + datePath);
            }
        }
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
