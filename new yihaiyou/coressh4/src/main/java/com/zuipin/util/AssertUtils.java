package com.zuipin.util;

public class AssertUtils {
    public static void assertHasText(String text, String description) {
        if (!StringUtils.hasText(text))
            throw new AssertError(description);
    }

    public static void assertNotNull(Object object, String description) {
        if (object == null)
            throw new AssertError(description);
    }

    public static class AssertError extends RuntimeException {
        private static final long serialVersionUID = -247192406863089119L;

        public AssertError(String message) {
            super(message);
        }
    }
}
