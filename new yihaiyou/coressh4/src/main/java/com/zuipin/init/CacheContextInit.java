package com.zuipin.init;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;

public class CacheContextInit implements ContextInit {
    @Autowired
    private MemCachedClient memCachedClient;
    
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void init(ServletContext context) {
        // TODO Auto-generated method stub
        memCachedClient.flushAll();
    }
    
}
