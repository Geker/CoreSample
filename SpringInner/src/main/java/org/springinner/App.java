package org.springinner;



import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Resource resource = new ClassPathResource("applicationContext.xml");
        ClassPathXmlApplicationContext cAppCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        // XmlBeanFactory bf = new XmlBeanFactory(resource);
        ISay bean = cAppCtx.getBean(ISay.class);
        System.err.println(bean.toString());

    }
}
