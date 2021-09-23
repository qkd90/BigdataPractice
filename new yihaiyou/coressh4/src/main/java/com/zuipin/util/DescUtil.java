package com.zuipin.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DescUtil {
    private static final String PASSWORD_CRYPT_KEY = "_jxmall_";
    private final static String DES                = "DES";
    
    /**
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回加密后的数据
     * @throws Exception
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2013-2-1
     * @功能说明：加密
     */
    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);
    }
    
    /**
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     * @throws Exception
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2013-2-1
     * @功能说明： 解密
     */
    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);
    }
    
    /**
     * @param data
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2013-2-1
     * @功能说明：密码解密
     */
    public final static String decrypt(String data) {
        try {
            return new String(decrypt(hex2byte(data.getBytes()), PASSWORD_CRYPT_KEY.getBytes()));
        } catch (Exception e) {
        }
        return null;
    }
    
    /**
     * @param password
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2013-2-1
     * @功能说明：密码加密
     */
    public final static String encrypt(String password) {
        try {
            return byte2hex(encrypt(password.getBytes(), PASSWORD_CRYPT_KEY.getBytes()));
        } catch (Exception e) {
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(DescUtil.encrypt("12861"));
    }
    
    /**
     * @param b
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2013-2-1
     * @功能说明：二行制转字符串
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
        
    }
    
    /**
     * @param b
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2013-2-1
     * @功能说明：字符串转二行制
     */
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}
