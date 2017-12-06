package com.bn.dubbo.los;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.alibaba.dubbo.common.json.JSON;
import com.bn.dubbo.client.DubboClient;
import com.bn.dubbo.conf.DubboConfigurer;

import cn.openlo.protocol.dubbo.DubboGenericResult;

public class LosInvoke {
    private static final String DUBBO_REGISTRY_ADDRESS = "zookeeper://127.0.0.1:2181";

    public void execute(String svcName, Map<String, Object> props, String zkAddr) {
        DubboClient dubboClient;
        Properties dubboConfig = new Properties();
        dubboConfig.put("dubbo.registry.address", zkAddr);
        // dubboConfig.put("dubbo.provider.proxy", "jdk");
        // dubboConfig.put("dubbo.consumer.proxy", "jdk");
        // dubboConfig.put("dubbo.application.compiler", "jdk");
        DubboConfigurer.configure(dubboConfig);
        dubboClient = new DubboClient("consumer");
        DubboGenericResult result = (DubboGenericResult) dubboClient.invoke(svcName, props);
        try {
            System.err.println(JSON.json(result));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return;

    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("batch.conf"));
        } catch (IOException e) {
            System.err.println("加载配置文件失败" + e);
        }
        String zkAddr = (String) properties.get("zk");
        if(zkAddr==null)
            zkAddr = DUBBO_REGISTRY_ADDRESS;
        else
            zkAddr = "zookeeper://" + zkAddr;
        String svc = (String) properties.get("svc.name");
        properties.remove("svc.name");
        properties.remove("zk");
        HashMap<String, Object> maps = new HashMap<>();
        for (Entry<Object, Object> a : properties.entrySet()) {
            maps.put(a.getKey().toString(), a.getValue());
        }
        new LosInvoke().execute(svc, maps, zkAddr);
        System.err.println("finish");
        System.exit(0);
    }


}
