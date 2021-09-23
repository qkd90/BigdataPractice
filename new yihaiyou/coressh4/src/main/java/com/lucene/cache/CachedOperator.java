package com.lucene.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;

public class CachedOperator {
    @Autowired
    private MemCachedClient memCachedClient;
    private Log             logger;
    
    public CachedOperator() {
        logger = LogFactory.getLog(getClass());
    }
    
    public void add(String key, Object serializable) {
        if (existsKey(key)) {
            logger.warn((new StringBuilder("Already exists in cache : ")).append(key).toString());
            return;
        } else {
            memCachedClient.add(key, serializable);
            return;
        }
    }
    
    
    public void delete(String key) {
        // TODO Auto-generated method stub
        if (existsKey(key.trim()))
            memCachedClient.delete(key.trim());
    }
    
    public boolean existsKey(String key) {
        // TODO Auto-generated method stub
        return memCachedClient.keyExists(key);
    }
    
    @SuppressWarnings("unchecked")
	public <T> T  get(String key) {
        // TODO Auto-generated method stub
        return (T) memCachedClient.get(key);
    }
    
}
