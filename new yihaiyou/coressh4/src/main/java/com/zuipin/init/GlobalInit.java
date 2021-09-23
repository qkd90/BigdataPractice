package com.zuipin.init;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GlobalInit implements ContextInit {
    private Log                 log     = LogFactory.getLog(GlobalInit.class);
    private Map<String, String> pathMap = new HashMap<String, String>();
    
    @Override
    public void init(ServletContext context) {
        try {
            if (pathMap != null && pathMap.size() > 0) {
                for (String key : pathMap.keySet()) {
                    context.setAttribute(key, pathMap.get(key));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        pathMap.clear();
        pathMap = null;
    }
    
    public Map<String, String> getPathMap() {
        return pathMap;
    }
    
    public void setPathMap(Map<String, String> pathMap) {
        this.pathMap = pathMap;
    }
}
