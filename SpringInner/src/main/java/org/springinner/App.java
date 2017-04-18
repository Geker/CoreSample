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
        //
        // PrintRandomInt bean2 = cAppCtx.getBean("printRandomInt", PrintRandomInt.class);
        // bean2.print();
        // // PrintRandomInt bean3 = cAppCtx.getBean("printRandomInt", PrintRandomInt.class);
        // // bean3.print();
        //
        // PrintRandomInt bean4 = cAppCtx.getBean("printRandomInt2", PrintRandomInt.class);
        // bean4.print();
        // PrintRandomInt bean5 = cAppCtx.getBean("printRandomInt2", PrintRandomInt.class);
        // bean5.print();
        // cAppCtx.destroy();
		// IObj bean1 = cAppCtx.getBean("box", IObj.class);
		// System.err.println(bean1.toString());
		// System.err.println(bean2.toString());

        // AOP
        TargetBean bean2 = cAppCtx.getBean("proxybean", TargetBean.class);
		bean2.invoke();
        bean2.invoke2();

    }
}
