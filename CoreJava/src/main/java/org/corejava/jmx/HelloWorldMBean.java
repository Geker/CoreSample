package org.corejava.jmx;

public interface HelloWorldMBean {
    String getGreeting();
    void setGreeting(String greeting);
    void printGreeting();
}
