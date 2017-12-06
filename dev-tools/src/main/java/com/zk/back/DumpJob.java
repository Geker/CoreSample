package com.zk.back;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

/**
 * DumpJob
 * @author Dietrich Featherston
 */
/**
 * <p>
 * Title: DumpJob
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: ufo Copyright (C) 2016
 * </p>
 *
 * @author SONGQQ
 * @version
 * @since 2016年11月9日
 */
public class DumpJob implements Job, Watcher {

    private String zkServer;
    private String outputDir;
    private String znode;
    private ZooKeeper zk;
    private Integer keepdays;

    public DumpJob(String zkServer, String outputDir, String znode) {
        this.zkServer = zkServer;
        this.outputDir = outputDir;
        this.znode = znode;
    }

    public DumpJob(ZooKeeper zk, String outputDir, String znode) {
        this.zk = zk;
        this.outputDir = outputDir;
        this.znode = znode;
    }

    public DumpJob(ZooKeeper zk, String outputDir, String znode, int keepdays) {
        this.zk = zk;
        this.outputDir = outputDir;
        this.znode = znode;
        this.keepdays = keepdays;
    }

    @Override
    public void go() {
        System.out.println("dumping data from zookeeper");
        System.out.println("zookeeper server: " + (zkServer == null ? "zk" : zkServer));
        System.out.println("reading from zookeeper path: " + znode);
        System.out.println("dumping to local directory: " + outputDir);
        try {
            if (zk == null)
                zk = new ZooKeeper(zkServer, 10000, this);
            go(zk);
            System.out.println("dumping success!");

        }
        catch (IOException e) {
            System.err.println("error connecting to " + zkServer);
            System.exit(1);
        }
    }

    private void go(ZooKeeper zk) {
        try {
            while (!zk.getState().isConnected()) {
                System.out.println("connecting to " + zkServer + " with chroot " + znode);
                Thread.sleep(1000L);
            }
            dumpChild(zk, outputDir + znode, znode, "");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void dumpChild(ZooKeeper zk, String outputPath, String znodeParent, String znode) throws Exception {

        if (keepdays != null && !checkDateABeforeDays(znode, keepdays))
            return;
        String znodePath = znodeParent + znode;

        // System.out.println("znodePath: " + znodePath);
        // System.out.println("outputPath: " + outputPath);
        String currznode = znodePath.length() == 0 ? "/" : znodePath;
        List<String> children = zk.getChildren(currznode, false);
        if (!children.isEmpty()) {

            // ensure parent dir is created
            createFolder(outputPath);

            // this znode is a dir, so ensure the directory is created and build a __znode value in its dir
            writeZnode(zk, outputPath + "/_znode", currznode);

            for (String c : children) {
                // System.out.println("c: " + c);
                dumpChild(zk, outputPath + "/" + c, znodePath + "/", c);
            }
        }
        else {
            // this znode has no contents to write a plan file with the znode contents here
            writeZnode(zk, outputPath, currznode);
        }
    }

    private void writeZnode(ZooKeeper zk, String outFile, String znode) throws Exception {
        Stat stat = new Stat();
        byte[] data = zk.getData(znode, false, stat);
        if (data != null && data.length > 0 && stat.getEphemeralOwner() == 0) {
            String str = new String(data);
            if (!str.equals("null")) {
                FileOutputStream out = new FileOutputStream(outFile);
                out.write(data);
                out.flush();
                out.close();
            }
        }
        else {
            if (!outFile.contains("_znode") && stat.getEphemeralOwner() == 0) {
                // Create an empty folder for Permanent nodes.
                createFolder(outFile);
            }

        }
    }

    private void createFolder(String path) {
        File f = new File(path);
        if (!f.exists()) {
            boolean s = f.mkdirs();
            if (s) {
                // System.out.println("Created folder: " + path);
            }
        }

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        ;
        ;
    }

    /**
     * 判断日期提前几天
     *
     * @param date
     * @return
     */
    public static boolean checkDateABeforeDays(String date, int days) {
        try {
            LocalDate dt = LocalDate.parse(date, DateTimeFormat.forPattern("yyyyMMdd"));
            LocalDate ldate = LocalDate.now();
            return ldate.minusDays(days).isAfter(dt);
        }
        catch (Exception e) {
            return true;
        }

    }
}
