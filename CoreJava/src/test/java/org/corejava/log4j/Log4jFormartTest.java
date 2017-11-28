package org.corejava.log4j;

import java.net.InetSocketAddress;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jFormartTest {

    private static final Logger logger = LoggerFactory.getLogger(Log4jFormartTest.class);
    @Test
    public void test() {

        InetSocketAddress isa = new InetSocketAddress("10.170.33.64", 122);
        logger.info("info:{}", isa);
    }

}
