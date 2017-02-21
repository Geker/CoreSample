package org.corejava.spring;

import org.corejava.bean.Icalc;
import org.corejava.spring.aop.HijackBeforeMethod;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "org.corejava" })
public class SpringContextConfig {

    // <bean id="hijackBeforeMethodBean" class="com.mkyong.aop.HijackBeforeMethod" />
    //
    // <bean id="customerServiceProxy"
    // class="org.springframework.aop.framework.ProxyFactoryBean">
    //
    // <property name="target" ref="customerService" />
    //
    // <property name="interceptorNames">
    // <list>
    // <value>hijackBeforeMethodBean</value>
    // </list>
    // </property>
    @Bean
    public HijackBeforeMethod hijackBeforeMethod() {
        return new HijackBeforeMethod();
    }

    @Bean
    public ProxyFactoryBean customerServiceProxy() {
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        proxy.setTargetClass(Icalc.class);
        // proxy.setInterfaces(Icalc.class);
        proxy.setInterceptorNames("hijackBeforeMethod");
        return proxy;

    }

}
