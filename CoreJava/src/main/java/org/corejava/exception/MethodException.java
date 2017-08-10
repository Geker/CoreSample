package org.corejava.exception;

public interface MethodException {
    public void func() throws Exception;

    public void func1() throws RtException;

    public void func2() throws SubException;

    public void func3() throws Throwable;
}
