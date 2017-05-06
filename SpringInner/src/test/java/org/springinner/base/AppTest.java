package org.springinner.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "appCtx.xml")
public class AppTest {

    static {
        System.setProperty("org.apache.logging.log4j.simplelog.StatusLogger.level", "INFO");
    }
    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Autowired
    BaseBean baseBean;

    @Test
    public void testName() throws Exception {
        String name = baseBean.getName();
        System.err.println(name);

    }

    public static void main(String[] args) {
        AppTest app = new AppTest();
    }
}
