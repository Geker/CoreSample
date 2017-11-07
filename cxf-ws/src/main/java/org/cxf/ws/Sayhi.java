package org.cxf.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sayhi implements Isayhi {
	Logger log = LoggerFactory.getLogger(Sayhi.class);
    @Override
    public String sayhi(String name) {
		log.debug("enter............");
		System.out.println(Thread.currentThread().getName() + " tName");
		return "hi:" + name;
    }

}
