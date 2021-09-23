package com.zuipin.util;

import java.text.MessageFormat;

public class MessageFormatRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 5020951134189427909L;

    public MessageFormatRuntimeException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, (Object[]) arguments));
    }

    public MessageFormatRuntimeException(String pattern, Throwable cause, Object... arguments) {
        super(MessageFormat.format(pattern, (Object[]) arguments), cause);
    }

    public MessageFormatRuntimeException(Throwable cause) {
        super(cause);
    }
}
