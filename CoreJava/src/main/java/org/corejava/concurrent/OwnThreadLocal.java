package org.corejava.concurrent;

import org.apache.commons.lang3.RandomStringUtils;

public class OwnThreadLocal extends ThreadLocal<String> {

    @Override
    protected String initialValue() {
        return RandomStringUtils.randomNumeric(100);
    }

}
