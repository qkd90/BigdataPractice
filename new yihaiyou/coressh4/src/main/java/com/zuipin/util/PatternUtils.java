package com.zuipin.util;

import java.util.regex.Pattern;

public class PatternUtils {
    
    public static Boolean isEmail(String email) {
        if (StringUtils.isNotBlank(email)) {
            return email.contains("@");
        }
        return false;
    }
    
    public static Boolean isMobile(String mobile) {
        return (null == mobile) ? false : Pattern.matches("^1[0-9]{10}", mobile);
    }
    
    public static Boolean isMemberCard(String userName) {
        return (null == userName) ? false : userName.startsWith("88888");
    }
}
