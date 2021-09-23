package com.hmlyinfo.app.soutu.scenicTicket.qunar.service;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
public class BASE64Utils {

    private final static Base64 base64 = new Base64();

    /**
     * base64编码
     *
     */
    public static String encode(String text) {
        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }
        byte[] b = base64.encode(text.getBytes());
        return new String(b);
    }

    /**
     * base64编码
     *
     */
    public static String encode(String text, Charset charset) {
        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }
        return new String(base64.encode(text.getBytes(charset)));
    }

    /**
     * base64编码
     *
     */
    public static String encode(byte[] text) {
        if (ArrayUtils.isEmpty(text)) {
            return StringUtils.EMPTY;
        }
        return new String(base64.encode(text));
    }
    

    /**
     * base64解密
     * 
     */
    public static String decode(String text) {
        if (StringUtils.isBlank(text)) {
            return StringUtils.EMPTY;
        }
        byte[] decode = base64.decode(text);
        if(decode==null){
            return StringUtils.EMPTY;
        }
        return new String(decode);
    }
}