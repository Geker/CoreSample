package org.corejava.bean;

import org.junit.Assert;
import org.junit.Test;

public class InstanseofInterface {

    @Test
    public void test() {
        Computer computer = new Computer();
        Assert.assertTrue(computer instanceof AbstractCalc);
        Assert.assertTrue(computer instanceof Icalc);
    }

}
