package com.zuipin.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class IllegalCharacterFilterRequestWrapper extends HttpServletRequestWrapper {
    private Pattern pn_1 = Pattern.compile("<script[\\s\\S]*?<\\/script[\\s\\S]*?>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private Pattern pn_2 = Pattern.compile("['‘’]+", Pattern.CASE_INSENSITIVE);
    
    private List<Pattern> getFilters() {
        List<Pattern> filters = new ArrayList<Pattern>();
        filters.add(pn_1);
        filters.add(pn_2);
        return filters;
    }   
    
    public IllegalCharacterFilterRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * request 包装类，覆盖父类的getParameter 方法，过滤 script 标签
     */
    @Override
    public String getParameter(String string) {
        String value = super.getParameter(string);
        return filterIllegalChars(value);
    }
    
    private String filterIllegalChars(final String value) {
        try {
            if (null == value)
                return null;
            String v = URLDecoder.decode(value, "UTF-8");
            for (Pattern p : getFilters()) {
                v = p.matcher(v).replaceAll("");
            }
            return v;
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
    
}
