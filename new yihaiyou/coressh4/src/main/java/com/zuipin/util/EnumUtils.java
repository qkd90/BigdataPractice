package com.zuipin.util;

public class EnumUtils {
    @SuppressWarnings("unchecked")
    public static <T extends Enum> T toEnum(Class<T> enumType, String value, T defaultValue) {
        try {
            return (T) Enum.valueOf(enumType, value);
        }
        catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
}
