package org.springinner.autowiring;



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


        // AOP
        BaseBean bean2 = cAppCtx.getBean("baseBean", BaseBean.class);
        System.err.println(bean2.getLocation().toString());

    }
}
