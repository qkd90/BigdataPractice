import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by caiys on 2016/11/10.
 */
public class RSAEncrypt {
    /** 指定加密算法为DESede */
    private static String ALGORITHM = "RSA";
    /** 指定key的大小 */
    private static int KEYSIZE = 1024;
    /** 指定公钥存放文件 */
    private static String PUBLIC_KEY = null;
    /** 指定私钥存放文件 */
    private static String PRIVATE_KEY = null;

    /**
     * 生成密钥对
     */
    private static void generateKeyPair() throws Exception{
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        /** 得到公钥 */
        PUBLIC_KEY = getKeyString(kp.getPublic());

        /** 得到私钥 */
        PRIVATE_KEY = getKeyString(kp.getPrivate());

        System.out.println("公钥：" + PUBLIC_KEY);
        System.out.println("私钥：" + PRIVATE_KEY);
    }

    /**
     * 得到密钥字符串（经过base64编码）
     * @return
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }

    /**
     * 加密方法
     * source： 源数据
     */
    public static String encrypt(String source, Key publicKey) throws Exception{
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b1);
    }

    /**
     * 解密算法
     * cryptograph:密文
     */
    public static String decrypt(String cryptograph, Key privateKey) throws Exception{
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b1 = decoder.decodeBuffer(cryptograph);
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }

    /**
     * 字符串转为PublicKey
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 字符串转转换私钥
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static void main(String[] args) throws Exception {
        String source = "Hello World!";         // 要加密的字符串
        generateKeyPair();  // 生成密钥对
        String cryptograph = encrypt(source, getPublicKey(PUBLIC_KEY));     // 生成的密文
        System.out.println("公钥加密结果：" + cryptograph);
        String target = decrypt(cryptograph, getPrivateKey(PRIVATE_KEY));   // 解密密文
        System.out.println("私钥解密结果：" + target);

        cryptograph = encrypt(source, getPrivateKey(PRIVATE_KEY));     // 生成的密文
        System.out.println("私钥加密结果：" + cryptograph);
        target = decrypt(cryptograph, getPublicKey(PUBLIC_KEY));   // 解密密文
        System.out.println("公钥解密结果：" + target);
    }
}
