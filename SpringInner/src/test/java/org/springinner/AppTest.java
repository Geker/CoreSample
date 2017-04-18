package org.springinner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springinner.aop.TargetBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AppTest {

    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Autowired
    AbstractApplicationContext cAppCtx;

    @Test
    public void testName() throws Exception {
        TargetBean bean = cAppCtx.getBean("proxybean", TargetBean.class);
        bean.invoke();
        bean.invoke2();
    }


}
