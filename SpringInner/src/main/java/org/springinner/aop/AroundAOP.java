package org.springinner.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AroundAOP implements MethodInterceptor {
	static Logger logger = LoggerFactory.getLogger(AroundAOP.class);

	@Override
	public Object invoke(MethodInvocation invoke) throws Throwable {
        // 只处理不是invoke的方法
		if (!invoke.getMethod().getName().equals("invoke")) {
			Object[] objs = invoke.getArguments();

			// String userName = (String) objs[0];
			//
			// // 在目标方法执行前调用
			logger.debug("正在对" + objs + "进行登录验证");
			// 通过反射调用执行方法
			Object obj = invoke.proceed();
			// 在目标方法执行之后调用
			logger.debug(objs + "登录成功");

			return obj;
		} else {
			Object obj = invoke.proceed();
			return obj;
		}

	}

}
