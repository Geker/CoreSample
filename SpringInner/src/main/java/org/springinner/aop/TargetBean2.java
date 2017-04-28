package org.springinner.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TargetBean2 implements IInterface, IInterface2 {
	static Logger logger = LoggerFactory.getLogger(TargetBean2.class);

	private String str = "target String";
	public void invoke() {
		logger.debug("invoke Bean method");
	}

	public void invoke2() {
		logger.debug("invoke2 Bean method");
	}

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "C";
    }
}
