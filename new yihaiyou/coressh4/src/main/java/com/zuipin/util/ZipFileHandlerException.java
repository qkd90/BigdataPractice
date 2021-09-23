package com.zuipin.util;

public class ZipFileHandlerException extends Exception {
    private static final long serialVersionUID = 9163227759702062670L;

    public ZipFileHandlerException(String message) {
        super(message);
    }

    public ZipFileHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}