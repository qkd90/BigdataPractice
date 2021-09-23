package com.zuipin.util;

import java.security.MessageDigest;

public class MD5 {

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public MD5() {
	}

	public static int MD5_LEN = 32;

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return (new StringBuilder(String.valueOf(hexDigits[d1]))).append(hexDigits[d2]).toString();
	}

	public static String Encode(String origin) {
		String resultString = origin;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception exception) {
		}
		return resultString;
	}

	public static String MD5Encode(String key) {
		return Encode(Encode(key));
	}

	public static String caiBeiMD5(String value) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = value.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		String value = "CompanyID=23&ContantName=为为为&Currency=RMB&CurrentId=1&CustomerId=23956&CustomerName=yehlq&FromCity=北京&FromDec=北京&FromProvince=北京&Goods=525&InsuranceType=1&IsTraffic=0&Money=254&Packing=散装(BULK)&PartnerID=23956&Phone=18950002222&PlateNumber=432&PolicyCurrency=RMB&ProductID=1&ProductState=432&StartTime=2015-12-15&SufferAddress=大&SufferCertificateNo=432&SufferName=432&SufferPhone=432&ToCity=北京&ToDec=北京&ToProvince=北京&TransporTypeId=7&Transportation=全密封箱式货车&WaybillNo=4326235388ba11c6f5eab11386ff72db7f8";
		System.out.println(caiBeiMD5(value));
	}

}
