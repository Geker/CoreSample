package com.bn.dubbo.client;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;



/**
 *
 * <p> Title: DubboClient </p>
 * <p> Description:dubbo协议应用级客户端，在应用启动时执行 </p>
 *
 * @author duanjunfeng
 * @version
 * @since 2016年8月3日
 */
public class DubboClient {

    private static final int SERVICE_TIMEOUT = 15000;

    private static final String PARAMETER_TYPE_GENERIC = cn.openlo.protocol.dubbo.DubboGenericParams.class.getName();

    private final Lock lock = new ReentrantLock();

    /**
     * cache ReferenceConfig
     * KEY为serviceName，VALUE为ReferenceConfig对象
     */
    private Map<String, ReferenceConfig<GenericService>> serviceName2ReferenceConfig =
            new HashMap<>();
    /**
     * cache ReferenceConfig
     * KEY为serviceName拼接serviceConfig，VALUE为ReferenceConfig对象
     */
    private Map<String, ReferenceConfig<GenericService>> service2ReferenceConfig = new HashMap<>();
    /**
     * cache ApplicationConfig
     * KEY为applicationName，VALUE为ApplicationConfig对象
     */
    private Map<String, ApplicationConfig> applicationConfigCache = new HashMap<>();

    private String consumerName;

    /**
     *
     * @param consumerName
     */
    public DubboClient(String consumerName) {
        super();
        this.consumerName = consumerName;
    }

    public Object invoke(String serviceName, Map<?, ?> parameters) {
        return this.invoke(serviceName, null, parameters);
    }

    public Object invoke(String serviceName, Map<String, ? extends Object> serviceMethodConfig, Map<?, ?> parameters) {
        Object serviceResponse = null;
        GenericService gs = null;
        if (serviceMethodConfig != null && serviceMethodConfig.size() > 0) {
            gs = getGenericService(consumerName, serviceName, serviceMethodConfig);
        }
        if (gs == null) {
            gs = getGenericService(consumerName, serviceName);
        }
        String[] parameterTypes = new String[] { PARAMETER_TYPE_GENERIC };
        Object[] args = new Object[] { new cn.openlo.protocol.dubbo.DubboGenericParams(parameters) };
        try {
            serviceResponse = gs.$invoke(serviceName, parameterTypes, args);
        }
        catch (GenericException e) {
            // TODO logger
            System.err.println(e);
        }
        return serviceResponse;
    }

    /**
     * 根据serviceName，serviceMethodParameters获取GenericService实例
     * serviceMethodParameters必须非空
     */
    private GenericService getGenericService(String gearName, String serviceName, Map<String, ? extends Object> serviceMethodConfig) {
        this.lock.lock();
        try {
            String key = serviceName + "_" + serviceMethodConfig.toString();
            ReferenceConfig<GenericService> reference = service2ReferenceConfig.get(key);
            if (reference == null) {
                reference = new ReferenceConfig<>();
                BeanUtils.populate(reference, serviceMethodConfig);
                reference.setGeneric(true);
                reference.setInterface(serviceName);
                reference.setApplication(getApplicationConfig(gearName));
                service2ReferenceConfig.put(key, reference);
            }
            return reference.get();
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            // TODO logger
            System.err.println(e);
        }
        finally {
            this.lock.unlock();
        }
        return null;
    }

    /**
     * 根据serviceName获取GenericService实例
     */
    private GenericService getGenericService(String consumerName, String serviceName) {
        this.lock.lock();
        try {
            ReferenceConfig<GenericService> reference = serviceName2ReferenceConfig.get(serviceName);
            if (reference == null) {
                reference = new ReferenceConfig<>();
                reference.setGeneric(true);
                reference.setInterface(serviceName);
                reference.setApplication(getApplicationConfig(consumerName));
                reference.setTimeout(SERVICE_TIMEOUT);
                serviceName2ReferenceConfig.put(serviceName, reference);
            }
            return reference.get();
        }
        finally {
            this.lock.unlock();
        }
    }

    /**
     * 服务引用应用配置，设置调用服务的应用名称，用于服务治理时依赖计算
     *
     * 【外部调用时加锁】
     */
    private ApplicationConfig getApplicationConfig(String consumerName) {
        ApplicationConfig applicationConfig = applicationConfigCache.get(consumerName);
        if (applicationConfig == null) {
            applicationConfig = new ApplicationConfig();
            applicationConfig.setName(consumerName);
            applicationConfigCache.put(consumerName, applicationConfig);
        }
        return applicationConfig;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

}
