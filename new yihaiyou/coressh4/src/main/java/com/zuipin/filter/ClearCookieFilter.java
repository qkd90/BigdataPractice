package com.zuipin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ClearCookieFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // if (CookieUtils.getCookie((HttpServletRequest) request,
        // Constants.COOKIE_CLEAR) == null) {
        // CookieUtils.clearCookie((HttpServletRequest) request,
        // (HttpServletResponse) response);// 清空cookie
        // CookieUtils.addCookie((HttpServletRequest) request,
        // (HttpServletResponse) response, Constants.COOKIE_CLEAR, "true",
        // Constants.BROWSE_EXPIRY);
        // }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
