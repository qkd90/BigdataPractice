package com.zuipin.util;

import org.apache.poi.ss.usermodel.Row;

/**
 * 功能描述：POI表格上传回调函数
 * 
 * @author : Teny_lu 刘鹰
 * @ProjectName : zuipinerp
 * @FileName : ExcelFirstCallBack.java
 * @E_Mail : liuying5590@163.com
 * @CreatedTime : 2014年10月23日-下午2:26:37
 */
public abstract interface ExcelFirstCallBack {
	
	public abstract boolean paseFirstSheet(Row paramRow);
	
}
