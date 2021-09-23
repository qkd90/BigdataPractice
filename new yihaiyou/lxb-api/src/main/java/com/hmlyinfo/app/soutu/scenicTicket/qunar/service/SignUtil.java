package com.hmlyinfo.app.soutu.scenicTicket.qunar.service;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.hmlyinfo.base.util.MD5;

public class SignUtil {
	private static final Logger logger = Logger.getLogger(SignUtil.class);
    
    /**
     * 加签
     * 
     */
    public static String sign(Map<String, String> paramMap, String distributorKey) {
        if(distributorKey == null || distributorKey.isEmpty()){
            throw new IllegalArgumentException("merchantKey is empty");
        }
        if(paramMap == null || paramMap.isEmpty()){
            throw new IllegalArgumentException("paramMap is empty");
        }
        // 对传递的map进行字典序排序
        TreeMap<String, String> treeMap = new TreeMap<String, String>(paramMap);
        StringBuffer buffer = new StringBuffer("partnerKey").append("=").append(distributorKey);
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            String value = String.valueOf(entry.getValue());
            //过滤sign字段
            if ("sign".equals(entry.getKey())) {
                continue;
            }
            // 判断值是否为空
            if (value != null && !"".equals(value)) {
                buffer.append("&").append(entry.getKey().trim()).append("=").append(value);
            }
        }
        logger.info("buffer:" + buffer.toString());
        String sign = MD5.GetMD5Code(buffer.toString());
        return sign;
    }
    
    /**
     * 验签
     * 
     */
    public static boolean checkSign(Map<String, String> paramMap, String distributorKey, String sign) {
        if(distributorKey == null||distributorKey.isEmpty()){
            throw new IllegalArgumentException("merchantKey is empty");
        }
        if(paramMap == null||paramMap.isEmpty()){
            throw new IllegalArgumentException("paramMap is empty");
        }
        if(sign == null ||sign.isEmpty()){
            throw new IllegalArgumentException("sign is empty");
        }
        return sign.equals(sign(paramMap,distributorKey));
    }

}