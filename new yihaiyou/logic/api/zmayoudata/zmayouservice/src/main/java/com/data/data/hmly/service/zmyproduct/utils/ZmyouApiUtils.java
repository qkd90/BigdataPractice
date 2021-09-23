package com.data.data.hmly.service.zmyproduct.utils;


import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by dy on 2016/5/9.
 */
public class ZmyouApiUtils {

    /**
     * 功能描述：Base64Utils编码
     * @param unEncode
     * @return
     */
    public static String encode(String unEncode) {
        byte[] bt = unEncode.getBytes();
        return Base64Utils.encodeToString(bt);
    }

    /**
     * 功能描述：Base64Utils解码
     * @param unEncode
     * @return
     */
    public static String decode(String unEncode) {
        byte[] bt = Base64Utils.decodeFromString(unEncode);
        String resultStr = null;
        try {
           resultStr = new String(bt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultStr;
    }

}
