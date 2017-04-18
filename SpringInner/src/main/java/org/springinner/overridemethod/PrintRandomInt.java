package org.springinner.overridemethod;

public abstract class PrintRandomInt {

    /**
     * 抽象方法，通过lookup方法注入一个独特的bean
     * 
     * @return
     */
    public abstract RandomGen getRandom();

    public void print() {
        System.err.println(getRandom().getRandom());
    }
}
