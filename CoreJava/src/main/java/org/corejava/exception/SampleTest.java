package org.corejava.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleTest {

    private static final Logger logger = LoggerFactory.getLogger(SampleTest.class);
    public static void main(String[] args) throws SubException {
        SampleMethod sm = new SampleMethod();
        logger.error("error test {},{}");
        RtException rt = new RtException();
        logger.debug("cause ", rt.getCause());
    }
}
