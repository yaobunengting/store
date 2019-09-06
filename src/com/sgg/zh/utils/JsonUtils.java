package com.sgg.zh.utils;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.xml.XMLSerializer;

/**
 * ����json���ݸ�ʽ�Ĺ�����
 * @author Administrator
 *
 */
public class JsonUtils {
	
	/**
	 * ������ת����String���͵�JSON���ݸ�ʽ
	 * @param object
	 * @return
	 */
	public static String array2json(Object[] object) {
		return JSONArray.fromObject(object).toString();
	}
	
	/**
	 * ��listת����String���͵�JSON���ݸ�ʽ
	 * @param list
	 * @return
	 */
	public static String list2json(List list) {
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * ��mapת����String���͵�JSON���ݸ�ʽ
	 * @param map
	 * @return
	 */
	public static String map2json(Map map) {
		return JSONArray.fromObject(map).toString();
	}
	
	/**
	 * ��object����ת����String���͵�JSON��������
	 * @param object
	 * @return
	 */
	public static String object2json(Object object) {
		return JSONArray.fromObject(object).toString();
	}

	/**
	 * ��XML���ݸ�ʽת����String���͵�JSON���ݸ�ʽ
	 * @param xml
	 * @return
	 */
	public static String xml2json(String xml) {
		return ((JSONArray)new XMLSerializer().read(xml)).toString();
	}
	
	public static JsonConfig configJson(String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(true);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		return jsonConfig;

	}
}
