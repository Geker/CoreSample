package cn.openlo.batchCheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

import com.alibaba.dubbo.common.json.JSON;
import com.google.common.collect.Lists;

import cn.openlo.batchCheck.TaskExecutions2ServerInfo.TaskExecutionsStatus;

/**
 * <p>
 * Title: BatchCheck
 * </p>
 * <p>
 * Description: ExecutingTasks下的节点清理
 * </p>
 * <p>
 * Copyright: ufo Copyright (C) 2017
 * </p>
 *
 * @author SONGQQ
 * @version
 * @since 2017年8月4日
 */
public class BatchCheck {
    private static final String addr = "127.0.0.1:2181";
    private static final String groupName = "local";

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

        String addrs = addr;
        String grpNames = groupName;
        addrs = zkAddr;
        grpNames = batchGrp;

        String[] grps = StringUtils.split(grpNames, ',');
        for (String grpName : grps) {
            try {
                String grp_executions = "/BatchGrp-" + grpName + "/" + "ExecutingTasks";

                String zkuser = (String) properties.get("zkuser");
                String zkpass = (String) properties.get("zkpass");
                CuratorFramework client = CuratorFrameworkFactory.builder().connectString(addrs + "/_lo/openlo-batch-schedule")
                    .sessionTimeoutMs(3000).connectionTimeoutMs(3000).retryPolicy(new RetryOneTime(5))
                    .authorization("digest", (zkuser.trim() + ":" + zkpass.trim()).getBytes()).build();

                // CuratorFramework client =
                // CuratorFrameworkFactory.newClient(addrs + "/_lo/openlo-batch-schedule", 3000, 3000, new RetryOneTime(1));
                client.start();

                List<String> taskexecutionings = client.getChildren().forPath(grp_executions);
                List<String> fails = Lists.newArrayList();

                List<String> delItems = new ArrayList<>();

                for (String executing : taskexecutionings) {
                    if ("locks".equals(executing)) {
                        continue;
                    }
                    String path = grp_executions + "/" + executing;
                    try {
                        byte[] datas = client.getData().forPath(path);
                        if (datas == null || datas.length == 0) {
                            continue;
                        }

                        TaskExecutions2ServerInfo obj = JSON.parse(new String(datas), TaskExecutions2ServerInfo.class);
                        if (obj != null && !TaskExecutionsStatus.DONE.equals(obj.getStatus())) {
                            fails.add(String.format("%-120s --%-10s -- %s", path, obj.getStatus(), JSON.json(obj)));
                            // if (TaskExecutionsStatus.ERROR.equals(obj.getStatus())) {
                            delItems.add(executing);
                            // }
                        }
                    }
                    catch (Exception e) {
                        System.err.println("JSON.parse EROR  " + e.getMessage());
                    }
                }

                Collections.sort(fails);
                if (fails.size() == 0) {
                    System.err.println("ALL TASK SUCCESS!");
                }
                else {
                    File file = new File(grpName + ".fails." + new SimpleDateFormat("yyyyMMdd-HHmm-S").format(new Date()) + ".txt");
                    FileUtils.writeLines(file, fails);

                    for (String item : delItems) {
                        String path = grp_executions + "/" + item;
                        String lockPath = grp_executions + "/" + "locks" + "/" + item + ".lock";
                        try {
                            client.delete().forPath(path);
                            if (client.checkExists().forPath(lockPath) != null) {
                                client.delete().forPath(lockPath);
                            }
                        }
                        catch (Exception e) {
                            System.err.println("del [" + path + "] error");
                        }
                    }
                    System.err.println("HAVE TASK FAIL!");
                }
            }
            catch (Exception e) {
                System.err.println(grpName + " Check fail!");

                e.printStackTrace();
            }
        }

    }
}
