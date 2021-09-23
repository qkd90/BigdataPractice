package com.hmlyinfo.base.image;

import java.io.IOException;


public interface ImageService {

	String saveImage(byte[] file, String path, String fileName) throws IOException;

}
