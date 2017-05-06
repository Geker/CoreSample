package org.springinner.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springinner.autowiring.Location;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

	public MyBeanPostProcessor() {
		super();
		System.out.println("这是BeanPostProcessor实现类构造器！！");
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String arg1) throws BeansException {

		System.out.println("bean处理器：bean创建之后.." + bean.getClass());
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String arg1) throws BeansException {
		System.out.println("bean处理器：bean创建之前.." + bean.getClass());
		if (bean instanceof Location)
		{
			Location loc=(Location) bean;
			System.err.println(loc);
			loc.setI(9999);
			
		}
		return bean;
	}
}