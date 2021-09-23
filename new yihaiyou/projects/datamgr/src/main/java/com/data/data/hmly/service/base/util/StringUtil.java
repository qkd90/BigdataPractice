package com.data.data.hmly.service.base.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class StringUtil extends StringUtils {

	public static String getSnFromInt(int num) {
		String sn = "";
		String numStr = num + "";
		for (int i = 0; i < (4 - numStr.length()); i++) {
			sn = sn + "0";
		}

		return sn + numStr;
	}

	public static String getSqlInStr(int... strings) {
		StringBuffer sb = new StringBuffer();
		for (int s : strings) {
			sb.append("'");
			sb.append(s);
			sb.append("',");
		}

		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

	/**
	 * 获取当前毫秒数
	 * 
	 * @return
	 */
	public synchronized static String getMilliseconds() {
		Date d = new Date();
		Long ms = d.getTime() / 1000;
		return ms + "";
	}

	/**
	 * 将数据库字段转换为Camel命名形式
	 * 事例：
	 * input: shop_name
	 * output:shopName
	 * @param column
	 * @return
	 */
	public static String changDBColumnToPropertyName(String column){
		if (StringUtils.isBlank(column)) {
			return "";
		}
		
		String[] words = column.split("_");
		StringBuilder builder = new StringBuilder(words[0]);
		for (int i = 1; i < words.length; i++) {
			builder.append(StringUtils.capitalize(words[i]));
		}
		
		return builder.toString();
	}
	
	/**
	 * 将Camel字段转换为数据库命名形式
	 * 事例：
	 * input: shopName
	 * output:shop_name
	 * @param propertyName
	 * @return
	 */
	public static String changPropertyNameToDBColumn(String propertyName){
		if (StringUtils.isBlank(propertyName)) {
			return "";
		}
		
		StringBuffer dBColumn = new StringBuffer();
		char[] propertyArr = propertyName.toCharArray();
		for (char c : propertyArr)
		{
			if (Character.isUpperCase(c))
			{
				dBColumn.append("_");
			};
			
			dBColumn.append(Character.toLowerCase(c));
		}
		
		return dBColumn.toString();
	}

	/**
	 * 判断指定字符串是否为null或者为空
	 * @return true：空，false:非空
	 */
	public static boolean isEmpty(String source){
		if (source == null || source.length() == 0) {
			return true;
		}
		
		return false;
	}
	
	public static String getStrFromFile(InputStream is)
	{
		StringBuffer fileContent = new StringBuffer();
		
		InputStreamReader read = null;
		
		try
		{
			read = new InputStreamReader(is, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			
			String lineTxt = "";
			
			while((lineTxt = bufferedReader.readLine()) != null)
			{
				fileContent.append(lineTxt);
				fileContent.append("\n");
	        }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				read.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return fileContent.toString();
		
	}
	
	
	public static String escapeHtml(String s)
	{
		if (s == null)
		{
			return null;
		}
		return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	public static String encryptDes(String msg, String strKey) throws Exception
	{
		Key key = getDesKey(strKey);
		
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key); 
		byte[] byteFina = cipher.doFinal(msg.getBytes("UTF-8"));
		
		return new String(Hex.encodeHex(byteFina));
	}
	
	public static String decryptDes(String msg, String strKey) throws Exception
	{
		Key key = getDesKey(strKey);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key); 
		byte[] byteFina = cipher.doFinal(Hex.decodeHex(msg.toCharArray()));
		
		return new String(byteFina, "UTF-8");
	}
	
	private static Key getDesKey(String strKey) throws Exception
	{
		KeyGenerator _generator = KeyGenerator.getInstance("DES");
		_generator.init(new SecureRandom(Hex.decodeHex(strKey.toCharArray())));
		
		return _generator.generateKey();
	}
	
	/**
	 * 拆分字符串，并且去除空的数组元素
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String[] splitStr(String str, String separator)
	{
		if (StringUtils.isBlank(str))
		{
			return new String[0];
		}
		
		String[] arr = str.split(separator);
		List<String> list = new ArrayList<String>();
		for (String s : arr)
		{
			if (StringUtils.isNotBlank(s))
			{
				list.add(s);
			}
		}
		
		return list.toArray(arr);
	}
	
	public static int parseInt(Object v)
	{
		int result = 0;
		try
		{
			result = Integer.parseInt(v.toString());
		}
		catch (Exception e)
		{
			result = 0;
		}
		
		return result;
	}
	
	public static String numberFormat(String s)
	{
		return numberFormat(s, "##.#");
	}
	
	/**
     * 数字格式话
     * @param a
     * @return
     */
    public static String numberFormat(String s, String format)
    {
    	String strNum = "";
    	if (s == null)
    	{
    		return strNum;
    	}
    	
    	double d = 0d;
    	try
    	{
    		d = Double.parseDouble(s);
    		strNum = new DecimalFormat(format).format(d);
    	}
    	catch (Exception e)
    	{
    		strNum = "";
    	}
    	
    	return strNum;
    }
    
    public static String dateFormatYMD(Date d)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	return sdf.format(d);
    }
    
    public static String percent(double a, double b)
    {
    	if (b == 0)
    	{
    		return "0%";
    	}
    	
    	double result = a / b * 100;
    	
    	return new DecimalFormat("##.#").format(result);
    }
    
    public static String toJson(Object o)
    {
    	String result = "";
    	ObjectMapper om = new ObjectMapper();
    	try
    	{
    		result = om.writeValueAsString(o);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return result;
    	
    }
}
