package org.springinner.base;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

@SuppressWarnings("deprecation")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "appCtx.xml")
public class SimpleTest {

    static {
        System.setProperty("org.apache.logging.log4j.simplelog.StatusLogger.level", "INFO");
    }
    private static final Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    // @Autowired
    // BaseBean baseBean;

    @Test
    public void testName() throws Exception {

        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("/org/springinner/base/appCtx.xml"));
        Object bean = factory.getBean("baseBean");
        System.err.println(bean);

    }

    public static void main(String[] args) {
        SimpleTest app = new SimpleTest();
    }
}
