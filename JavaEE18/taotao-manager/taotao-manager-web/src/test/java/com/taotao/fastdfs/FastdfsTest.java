package com.taotao.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FastdfsTest {

	@Test
	public void testUpload() throws Exception {
		// 1、把FastDFS提供的jar包添加到工程中
		// 2、初始化全局配置。加载一个配置文件。
		ClientGlobal.init(
				"G:\\Eclipse-mars-workspace\\JavaEE18\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\properties\\client.conf");
		// 3、创建一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
		// 4、创建一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 5、声明一个StorageServer对象，null。
		StorageServer storageServer = null;
		// 6、获得StorageClient对象。
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 7、直接调用StorageClient对象方法上传文件即可。
		String[] strings = storageClient.upload_file("D:\\用户目录\\我的图片\\猪八戒.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testFastDfsClient() throws Exception {
		FastDFSClient client = new FastDFSClient("G:\\Eclipse-mars-workspace\\JavaEE18\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\properties\\client.conf");
		String uploadFile = client.uploadFile("D:\\用户目录\\我的图片\\八戒.jpg", "jpg");
		System.out.println(uploadFile);
	}
}
