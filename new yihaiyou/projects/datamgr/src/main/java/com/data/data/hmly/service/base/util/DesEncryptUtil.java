package com.data.data.hmly.service.base.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * DES对称加密解密算法工具类(采用十六进制形式)
 * 
 * @author qsRoy
 * 
 */
public class DesEncryptUtil {
	// 密钥算法
	private static final String KEY_ALGORITHM = "DES";

	/**
	 * 转换密钥
	 * 
	 * @param key 二进制密钥
	 * @return Key 秘钥
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}
	
	/**
	 * 解密
	 * 
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		// 实例化
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		// 初始化,设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		return decrypt(data, decodeHex(key));
	}
	
	/**
	 * 解密
	 * 
	 * @param data 待解密数据(十六进制字符串)
	 * @param key 密钥
	 * @return String 解密数据
	 * @throws Exception
	 */
	public static String decryptHex(String data, String key) throws Exception {
		return new String(decrypt(decodeHex(data), decodeHex(key)));
	}

	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		// 实例化
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		// 初始化,设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		return encrypt(data, decodeHex(key));
	}
	
	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return String 加密数据(十六进制字符串)
	 * @throws Exception
	 */
	public static String encryptHex(String data, String key) throws Exception {
		return encodeHex(encrypt(data.getBytes(), decodeHex(key)));
	}
	
	/**
	 * 生成密钥
	 * 
	 * @param seed 盐值
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;

		if (seed != null) {
			secureRandom = new SecureRandom(decodeHex(seed));
		} else {
			secureRandom = new SecureRandom();
		}

		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(secureRandom);

		SecretKey secretKey = kg.generateKey();

		return secretKey.getEncoded();
	}

	/**
	 * 生成密钥(十六进制字符串)
	 * 
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static String initHexKey() throws Exception {
		return initHexKey(null);
	}

	/**
	 * 生成密钥(十六进制字符串)
	 * 
	 * @param seed 盐值
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static String initHexKey(String seed) throws Exception {
		return encodeHex(initKey(seed));
	}
	
	/**
	 * 将字节数组转化为十六进制字符串
	 * @param data 字节数组
	 * @return 十六进制字符串
	 */
	private static String encodeHex(byte [] data) {
		return Hex.encodeHexString(data);
	}
	
	/**
	 * 将十六进制字符串转化为字节数组
	 * @param data 十六进制字符串
	 * @return 字节数组
	 * @throws Exception
	 */
	private static byte[] decodeHex(String data) throws Exception {
		return Hex.decodeHex(data.toCharArray());
	}
}
