package com.bn.dubbo.conf;

import java.util.Properties;

import org.apache.dubbo.common.utils.ConfigUtils;

/**
 *
 * <p>
 * Title: DubboConfigurer
 * </p>
 * <p>
 * Description:dubbo协议应用级全局配置，在应用启动时执行
 * </p>
 *
 * @author duanjunfeng
 * @version
 * @since 2016年8月3日
 */
public class DubboConfigurer {
    /**
     * dubbo协议应用级全局配置，在应用启动时执行
     *
     * @param dubboConfig
     */
    public static void configure(Properties dubboConfig) {
        ConfigUtils.setProperties(dubboConfig);
    }
}
