package com.bn.dubbo.los;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.bn.dubbo.client.DubboClient;
import com.bn.dubbo.conf.DubboConfigurer;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.openlo.gw.proxy.GenericRequest;
import cn.openlo.protocol.dubbo.DubboGenericResult;

public class LosInvoke {
    private static final String DUBBO_REGISTRY_ADDRESS = "zookeeper://127.0.0.1:2181";

    static public DubboGenericResult execute(String svcName, Map<String, Object> props, String zkAddr) {
        DubboClient dubboClient;
        Properties dubboConfig = new Properties();
        dubboConfig.put("dubbo.registry.address", zkAddr);
        dubboConfig.put("dubbo.registry.protocol", "dubbo");
        dubboConfig.put("dubbo.registry.client", "curator");
        DubboConfigurer.configure(dubboConfig);
        dubboClient = new DubboClient("consumer");
        DubboGenericResult result = (DubboGenericResult) dubboClient.invoke(svcName, props);
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            System.out.println(objectMapper.writeValueAsString(result));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    static public DubboGenericResult execute(String svcName, Map<String, Object> props) {
        return execute(svcName, props, DUBBO_REGISTRY_ADDRESS);
    }

    @SuppressWarnings("unchecked")
    static public DubboGenericResult execute(String svcName, Object req) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return execute(svcName, objectMapper.convertValue(req, Map.class), DUBBO_REGISTRY_ADDRESS);
    }

    /**
     * 调用LOS代理--替换特殊的参数
     *
     * @param svcName
     * @param param
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object execute_forLosProxy(String svcName, Object param) throws Exception {

        GenericRequest gr = new GenericRequest();
        gr.setParam(param);
        ObjectMapper objectMapper = new ObjectMapper();
        return execute(svcName, objectMapper.convertValue(gr, Map.class), DUBBO_REGISTRY_ADDRESS);
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("batch.conf"));
        }
        catch (IOException e) {
            System.err.println("加载配置文件失败" + e);
        }
        String zkAddr = (String) properties.get("zk");
        if (zkAddr == null)
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
        LosInvoke.execute(svc, maps, zkAddr);
        System.err.println("finish");
        System.exit(0);
    }
}
