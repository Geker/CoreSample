package cn.openlo.batchCheck;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

public class ZkTest {
    // private static final String addr = "172.16.200.1:22181";
    private static final String addr = "127.0.0.1:2181";
    private static final String groupName = "intf";
    public static void main(String[] args) throws Exception {
        String grp="/BatchGrp-" + groupName;
        CuratorFramework client =
                CuratorFrameworkFactory.newClient(addr + "/_lo/openlo-batch-schedule", 3000, 3000, new RetryOneTime(1));
        client.start();
        String svrPath = grp + "/" + "TaskExecutions" + "/" + "bn.ufo.notify.sms" + "/" + "20160822" + "/" + "sendSmsBatchTask";
        long bef = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        List<String> childs = client.getChildren().forPath(svrPath);
        for (String str : childs)
        {
            String ss = new String(client.getData().forPath(svrPath + "/" + str));
            sb.append(ss);
        }
        System.err.println((System.currentTimeMillis() - bef) / (childs.size() + 1));
        System.err.println(childs.size());
    }





}
