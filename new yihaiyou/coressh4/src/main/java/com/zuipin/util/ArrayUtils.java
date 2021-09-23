package com.zuipin.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
    public static <T> T[] array(T... items) {
        return items;
    }

    public static String string(Object[] items, String split) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            if (i > 0) {
                builder.append(split);
            }
            builder.append(items[i]);
        }
        return builder.substring(0, builder.lastIndexOf(split));
    }

    public static <T> boolean hasItem(T[] array) {
        return array != null && array.length > 0;
    }

    public static <T> boolean contains(T[] array, T target) {
        if (array == null)
            return false;
        for (T item : array) {
            if (item != null && item.equals(target))
                return true;
        }
        return false;
    }

    public static List<Long> stringArrayTOLongArray(String[] originArray) {
        List<Long> targetList = new ArrayList<Long>();
        try {
            for (String s : originArray) {
                targetList.add(Long.parseLong(s));
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
        return targetList;
    }
}
