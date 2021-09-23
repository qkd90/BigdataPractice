package com.zuipin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAManager {
	private static KeyPair	keyPair;
	
	static {
		keyPair = getKeyPair();
	}
	
	public RSAManager(String rsakeyPath) {
		keyPair = getKeyPair(rsakeyPath);
	}
	
	public KeyPair getKeyPair(String rsakeyPath) {
		try {
			// 产生新密钥对
			InputStream is = new FileInputStream(new File(rsakeyPath));
			ObjectInputStream oos = new ObjectInputStream(is);
			KeyPair kp = (KeyPair) oos.readObject();
			oos.close();
			return kp;
		} catch (Exception e) {
			throw new SecurityException("couldn't get the key pair");
		}
	}
	
	public static KeyPair getKeyPair() {
		try {
			// 产生新密钥对
			InputStream is = FileUtils.class.getResourceAsStream("/RSAKey.xml");
			ObjectInputStream oos = new ObjectInputStream(is);
			KeyPair kp = (KeyPair) oos.readObject();
			oos.close();
			return kp;
		} catch (Exception e) {
			throw new SecurityException("couldn't get the key pair");
		}
	}
	
	public static void saveRSAKey() throws NoSuchAlgorithmException, IOException {
		SecureRandom sr = new SecureRandom();
		KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
		kg.initialize(1024, sr);
		FileOutputStream fos = new FileOutputStream("C:/key.xml");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kg.generateKeyPair());
		oos.close();
	}
	
	public static String encrypt(String unencryptedString) {
		if (unencryptedString == null || unencryptedString.trim().length() == 0)
			throw new IllegalArgumentException("unencrypted string was null or empty");
		
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
			byte[] cleartext = unencryptedString.getBytes(Constants.UTF8);
			byte[] ciphertext = cipher.doFinal(cleartext);
			
			return new String(Base64.encodeBase64(ciphertext), Constants.UTF8);
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}
	
	public static String decrypt(String encryptedString) {
		if (encryptedString == null || encryptedString.trim().length() <= 0)
			throw new IllegalArgumentException("encrypted string was null or empty");
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
			byte[] cleartext = Base64.decodeBase64(encryptedString.getBytes(Constants.UTF8));
			byte[] ciphertext = cipher.doFinal(cleartext);
			return new String(ciphertext, Constants.UTF8);
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(RSAManager.encrypt("123"));
	}
}
