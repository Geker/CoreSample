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
        System.err.println(Number.class.isAssignableFrom(Integer.class));
        // 只能读取main/resources下的文件。
        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext cAppCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        IObj bean1 = cAppCtx.getBean("box", IObj.class);
        System.err.println(bean1.toString());

    }
}
