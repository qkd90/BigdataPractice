package com.zuipin.util;

import java.text.MessageFormat;

public class MessageFormatException extends Exception {
    private static final long serialVersionUID = 1528492354732164944L;

    public MessageFormatException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, (Object[]) arguments));
    }

    public MessageFormatException(String pattern, Throwable cause, Object... arguments) {
        super(MessageFormat.format(pattern, (Object[]) arguments), cause);
    }
}
