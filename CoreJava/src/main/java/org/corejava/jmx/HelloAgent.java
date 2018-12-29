package org.corejava.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class HelloAgent {
    private MBeanServer mbs;
    private MBeanServer mbs2;
    public HelloAgent() {
        this.mbs = MBeanServerFactory.createMBeanServer("HelloAgent");
        mbs2=ManagementFactory.getPlatformMBeanServer();
        HelloWorld hw = new HelloWorld();
        HelloWorld hw2 = new HelloWorld();
        ObjectName helloWorldName = null;
        ObjectName helloWorldName2 = null;

        try{
            helloWorldName = new ObjectName("HelloAgent:name=helloWorld");
            helloWorldName2 = new ObjectName("HelloAgent:name=helloWorld2");

            mbs.registerMBean(hw, helloWorldName);
            mbs2.registerMBean(hw, helloWorldName);


            mbs.registerMBean(hw2, helloWorldName);


        } catch (Exception e) {
            e.printStackTrace();
        }
        startHtmlAdaptorServer();
    }

    public void startHtmlAdaptorServer(){
        HtmlAdaptorServer htmlAdaptorServer = new HtmlAdaptorServer();
        ObjectName adapterName = null;
        try {
            // 多个属性使用,分隔
            adapterName = new ObjectName("HelloAgent:name=htmladapter,port=9092");
            htmlAdaptorServer.setPort(9092);
            mbs.registerMBean(htmlAdaptorServer, adapterName);
            htmlAdaptorServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException{
        System.out.println(" hello agent is running");
        HelloAgent agent = new HelloAgent();
        System.in.read();
    }
}
