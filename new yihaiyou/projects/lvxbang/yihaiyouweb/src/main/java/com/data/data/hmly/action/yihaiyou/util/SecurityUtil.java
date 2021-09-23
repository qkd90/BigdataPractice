package com.data.data.hmly.action.yihaiyou.util;

import com.data.data.hmly.service.pay.util.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Created by huangpeijie on 2016-11-09,0009.
 */
public class SecurityUtil {
    public static String shaEncode(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            byte[] beforeByte = input.getBytes("utf-8");
            byte[] afterByte = digest.digest(beforeByte);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < afterByte.length; i++) {
                int val = ((int) afterByte[i]) & 0xff;
                if (val < 16) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(val));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String rsaEncode(String input, String keyPath) {
        try {
            File file = new File(keyPath);
            String key = FileUtils.readFileToString(file, "utf-8");
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(org.apache.xerces.impl.dv.util.Base64.decode(key));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //Cipher类为加密和解密提供密码功能，通过getinstance实例化对象
            Cipher cipher = Cipher.getInstance("RSA");
            //初始化加密
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encode(cipher.doFinal(input.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
