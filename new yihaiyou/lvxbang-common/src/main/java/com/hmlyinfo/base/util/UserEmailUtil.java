package com.hmlyinfo.base.util;

import java.util.Date;

public class UserEmailUtil {
	/**
	 * DES加密秘钥
	 */
	private static final String DES_ENCRYPT_KEY = "04d05bb368347f62";

	/**
	 * 验证分隔字符
	 */
	private static final String USER_VAL_KEY_SPLIT_CHAR = "#";

	/**
	 * 验证串明文标准长度
	 */
	private static final int USER_VAL_KEY_LENGTH = 47;

	/**
	 * 有效时间天数
	 */
	private static final int USER_VAL_DATE_VALID_NUM = 7;

	/**
	 * 返回发送明文验证串
	 *
	 * @param userId 用户编号
	 * @return 明文验证串
	 */
	private static String getSendValKey(String userId) {
		StringBuffer keySb = new StringBuffer();
		keySb.append(userId).append(USER_VAL_KEY_SPLIT_CHAR).append(new Date().getTime());
		return keySb.toString();
	}

	/**
	 * 加密发送验证串
	 *
	 * @param userId 用户编号
	 * @return 密文验证串
	 * @throws Exception
	 */
	public static String encryptSendSid(String userId) throws Exception {
		return DesEncryptUtil.encryptHex(getSendValKey(userId), DES_ENCRYPT_KEY);
	}

	/**
	 * 解密发送验证串
	 *
	 * @param encodeSid 待解密的密文验证串
	 * @return 明文验证串
	 * @throws Exception
	 */
	public static String decryptSendSid(String encodeSid) throws Exception {
		return DesEncryptUtil.decryptHex(encodeSid, DES_ENCRYPT_KEY);
	}

	/**
	 * 明文串中取出用户ID
	 *
	 * @param decodeSid 明文验证串
	 * @return 用户ID
	 */
	public static String parseUserId(String decodeSid) {
		if (!StringUtil.isBlank(decodeSid)) {
			String[] tmpArr = decodeSid.split(USER_VAL_KEY_SPLIT_CHAR);
			return tmpArr[0];
		}
		return null;
	}

	/**
	 * 加密用户id
	 *
	 * @param userId 用户编号
	 * @return 密文验证串
	 * @throws Exception
	 */
	public static String encryptSendUid(String userId) throws Exception {
		return DesEncryptUtil.encryptHex(userId, DES_ENCRYPT_KEY);
	}
}
