package com.kas.clientservice.haiyansmartenforce.tcsf.util;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class StringUtil {

	public static boolean isEmptyString(String str) {
		return str == null || str.trim().length() == 0 ;
	}

	public static boolean isEmptyList(List<Object> objList) {
		return objList == null || objList.size() == 0 ;
	}


	public static boolean isEmptyArry(Object[] obj) {
		return obj == null || obj.length == 0 ;
	}


	public static byte[] str2bytes(String str) {
		byte[] b = null, data = null;
		try {
			b = str.getBytes("utf-8");
			data = new String(b, "utf-8").getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return data;
	}


}
