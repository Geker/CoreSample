package org.corejava.exception;

public class RtException extends RuntimeException {
    public RtException() {
    }

    public RtException(Throwable e) {
        super(e);
    }
}
