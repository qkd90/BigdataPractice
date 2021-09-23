package com.hmlyinfo.base.util;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.exception.BizValidateException;
import org.apache.commons.lang.StringUtils;

public class Validate {

	public static void notNull(Object object) {
		notNull(object, ErrorCode.ERROR_50001);
	}

	public static void notNull(Object object, int errorcode) {
		notNull(object, errorcode, null);
	}

	public static void notNull(Object object, int errorcode, String msg) {
		if (object == null || StringUtils.isBlank(object.toString())) {
			throw new BizValidateException(errorcode, msg);
		}
	}

	public static void isTrue(boolean isTrue, int errorcode) {
		isTrue(isTrue, errorcode, null);
	}

	public static void isTrue(boolean isTrue, int errorcode, String msg) {
		if (!isTrue) {
			throw new BizValidateException(errorcode, msg);
		}
	}
}