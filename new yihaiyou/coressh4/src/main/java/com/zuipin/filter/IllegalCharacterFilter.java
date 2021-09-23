package com.zuipin.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class IllegalCharacterFilter implements Filter {
    @Override
    public void destroy() {
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 添加 RequestWrapper 过滤非法字符
        if (request instanceof HttpServletRequest) {
            IllegalCharacterFilterRequestWrapper icf = new IllegalCharacterFilterRequestWrapper((HttpServletRequest) request);
            chain.doFilter(icf, response);
        } else {
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }
    
}
