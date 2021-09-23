package com.spark.service.rddservice.pz;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.LogFactory;

public enum ExtendsClasss implements Serializable {
	
	instance;
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 254285429011969926L;
	
	public Class<?> loadClass(String jarPath, String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		
		try {
			LogFactory.getLog(ExtendsClasss.class).info(String.format("=======LOAD CLASS %s=======", className));
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			LogFactory.getLog(ExtendsClasss.class).info(String.format("=======LOAD CLASS FROM EXTENDS======="));
			return loadExtendsClass(jarPath, className);
		} catch (Exception e) {
			return null;
		}
	}
	
	private Class<?> loadExtendsClass(String jarPath, String className) throws ClassNotFoundException, IOException {
		File jarFile = new File(jarPath);
		List<URL> urls = new ArrayList<URL>();
		for (File file : jarFile.listFiles()) {
			try {
				if (file.getName().endsWith(".jar")) {
					urls.add(file.toURI().toURL());
				}
			} catch (MalformedURLException e) {
				// LogFactory.getLog(MapLoadPZStrategyFromJarAndRunIt.class).error(e.getMessage(), e);
			}
		}
		URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
		LogFactory.getLog(ExtendsClasss.class).info(String.format("=======LOAD EXTENDS CLASS %s=======", className));
		Class<?> loadClass = classLoader.loadClass(className);
		// classLoader.close();
		Thread.currentThread().setContextClassLoader(classLoader);
		return loadClass;
	}
	
}
