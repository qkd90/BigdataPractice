package com.zuipin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {
	
	public final static Integer				maxFileLen		= 40;
	
	public final static SimpleDateFormat	sdf				= new SimpleDateFormat("yyyyMMddHHmmss");
	
	public final static String				DEFAULT_ENCODE	= "utf-8";
	
	private final static Log				log				= LogFactory.getLog(FileUtils.class);
	
	public static void copy(String sourceFilePath, String targetFilePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(sourceFilePath);
			copy(fileInputStream, targetFilePath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void copy(File inputFile, String targetFilePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(inputFile);
			copy(fileInputStream, targetFilePath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void copy(InputStream inputStream, String targetChildFilePath) {
		try {
			BufferedInputStream input = new BufferedInputStream(inputStream);
			File outputFile = new File(targetChildFilePath);
			if (outputFile.exists()) {
				outputFile.delete();
			}
			File parentFile = outputFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile));
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void copyTextFileToUTF8(InputStream inputStream, String inputEncode, String targetChildFilePath, TextFileContentProccessor proccessor) {
		copyTextFile(inputStream, inputEncode, targetChildFilePath, "UTF8", proccessor);
	}
	
	public static void copyTextFile(InputStream inputStream, String inputEncode, String targetChildFilePath, String outputEncode, TextFileContentProccessor proccessor) {
		OutputStreamWriter writer = null;
		BufferedReader input = null;
		try {
			InputStreamReader reader = new InputStreamReader(inputStream, inputEncode);
			input = new BufferedReader(reader);
			File outputFile = new File(targetChildFilePath);
			if (outputFile.exists()) {
				outputFile.delete();
			}
			File parentFile = outputFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			writer = new OutputStreamWriter(new FileOutputStream(outputFile), outputEncode);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = input.readLine()) != null) {
				sb.append(line).append("\r\n");
			}
			writer.write(proccessor.proccess(sb.toString()));
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				writer.close();
				input.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static String getFileType(File file) {
		String fileName = file.getName();
		return getFileType(fileName);
	}
	
	public static String getFileType(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex > -1) {
			return fileName.substring(lastDotIndex + 1).toLowerCase();
		}
		return "";
	}
	
	public static void close(InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}
	
	public static void deleteFile(String file) {
		new File(file).delete();
	}
	
	public static String readFileStream(String file, String encode) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode));
		try {
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp).append("\n");
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return sb.toString();
	}
	
	public static List<String[]> readFileStream(String file, String encode, String regex) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode));
		List<String[]> list = new ArrayList<String[]>();
		String[] split = null;
		try {
			String temp = null;
			while ((temp = br.readLine()) != null) {
				split = temp.split(regex);
				list.add(split);
			}
			return list;
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}
	
	public static void write(String content, File file, String encoding) {
		try {
			file.getParentFile().mkdirs();
			OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), encoding);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void copyTEXTFileAndProccessStrings(boolean overwrite, InputStream inputStream, String inputEncode, String targetName, String outputEncode,
			TextFileContentProccessor proccessor) {
		try {
			InputStreamReader reader = new InputStreamReader(inputStream, inputEncode);
			BufferedReader input = new BufferedReader(reader);
			File outputFile = new File(targetName);
			if (outputFile.exists()) {
				if (overwrite) {
					outputFile.delete();
				} else {
					return;
				}
			}
			File parentFile = outputFile.getParentFile();
			if (parentFile != null && !parentFile.exists()) {
				parentFile.mkdirs();
			}
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile), outputEncode);
			BufferedWriter output = new BufferedWriter(writer);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = input.readLine()) != null) {
				sb.append(line).append("\r\n");
			}
			String content = sb.toString();
			output.write(proccessor.proccess(content));
			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void makeDirectory(String folderPath) {
		try {
			File file = new File(folderPath.toString());
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void createFile(String file) {
		try {
			File f = new File(file);
			if (!f.exists()) {
				f.createNewFile();
			}
			f = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getEncodeName(String unpackEncode, String targetName) {
		return targetName + "_" + unpackEncode;
	}
	
	/**
	 * 去年文件名的特殊字符,并限制生成文件名大小
	 * 
	 * @param fileName
	 * @param maxLen
	 * @return
	 */
	public static String getFileName(String fileName, Integer maxLen) {
		if (fileName == null)
			return null;
		char content[] = new char[fileName.length()];
		fileName.getChars(0, fileName.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
				case '?':
				case '、':
				case '╲':
				case '*':
				case '“':
				case '”':
				case '<':
				case '>':
				case '|':
				case '\\':
				case '\'':
				case '/':
					log.info("filter " + content[i]);
					break;
				default:
					result.append(content[i]);
			}
			if (result.length() >= maxLen)
				return result.toString();
		}
		return result.toString();
		
	}
	
	public static String getTimeStr() {
		return sdf.format(new Date());
	}
	
	/**
	 * @return 获取绝对路径
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static void main(String[] args) {
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	public static void copyDir(String sourceFilePath, String targetFilePath) {
		File sourceFile = new File(sourceFilePath);
		if (sourceFile.isDirectory()) {
			String[] children = sourceFile.list();
			// 递归复制目录中的子目录
			for (int i = 0; i < children.length; i++) {
				String sourceFilePathSub = sourceFilePath + "/" + children[i];
				String targetFilePathSub = targetFilePath + "/" + children[i];
				copyDir(sourceFilePathSub, targetFilePathSub);
			}
		} else {
			copy(sourceFilePath, targetFilePath);
		}
	}
	
}
