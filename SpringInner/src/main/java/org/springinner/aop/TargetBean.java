package org.springinner.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TargetBean {
	static Logger logger = LoggerFactory.getLogger(TargetBean.class);

	private String str = "target String";
	public void invoke() {
		logger.debug("invoke Bean method");
	}

	public void invoke2() {
		logger.debug("invoke2 Bean method");
	}
}
