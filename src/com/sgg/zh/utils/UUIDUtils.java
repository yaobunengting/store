package com.sgg.zh.utils;

import java.util.UUID;

public class UUIDUtils {
	
	/**
	 * �������id
	 * @return
	 */
	public static String getId() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	/**
	 * ���������
	 * @return
	 */
	public static String getCode() {
		return getId();
	}
}
