package org.springinner.initanddestory;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


public class InitDestory2 implements InitializingBean, DisposableBean {
    AtomicInteger i = new AtomicInteger(0);
    private static final Logger logger = LoggerFactory.getLogger(InitDestory2.class);

    @Override
    public void destroy() throws Exception {
        logger.error("destroy Bean ----" + i.incrementAndGet());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.error("afterPropertiesSet----" + i.incrementAndGet());

    }

    // @PostConstruct // 初始化方法的注解方式 等同与init-method=init
    public void init() {
        logger.error("调用初始化方法.... -------" + i.incrementAndGet());
    }

    // @PreDestroy // 销毁方法的注解方式 等同于destory-method=destory222
    public void destory() {
        logger.error("调用销毁化方法.... ----" + i.incrementAndGet());
    }

}
