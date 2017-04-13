package org.springinner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AppTest {
    @Autowired
    AbstractApplicationContext cAppCtx;

    @Test
    public void testName() throws Exception {
        ISay bean = cAppCtx.getBean(ISay.class);
        System.err.println(bean.toString());
    }


}
