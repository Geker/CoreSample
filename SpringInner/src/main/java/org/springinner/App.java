package org.springinner;



import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springinner.aop.TargetBean;

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
		// IObj bean1 = cAppCtx.getBean("box", IObj.class);
		// System.err.println(bean1.toString());
		TargetBean bean2 = cAppCtx.getBean("userlogin", TargetBean.class);
		// System.err.println(bean2.toString());

		bean2.invoke();

		bean2.invoke2();
    }
}
