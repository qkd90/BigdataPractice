package com.zuipin.init;

import java.util.ArrayList;
import java.util.List;

public class ContextInitBeans {
    /**
     * 初始化对像集合
     */
    private List<ContextInit> contextInitBeans;
    
    public List<ContextInit> getContextInitBeans() {
        if (null == contextInitBeans) {
            contextInitBeans = new ArrayList<ContextInit>();
        }
        return contextInitBeans;
    }
    
    public void setContextInitBeans(List<ContextInit> contextInitBeans) {
        this.contextInitBeans = contextInitBeans;
    }
}
