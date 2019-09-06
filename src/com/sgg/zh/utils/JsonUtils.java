package com.sgg.zh.utils;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.xml.XMLSerializer;

/**
 * 处理json数据格式的工具类
 * @author Administrator
 *
 */
public class JsonUtils {
	
	/**
	 * 将数组转换成String类型的JSON数据格式
	 * @param object
	 * @return
	 */
	public static String array2json(Object[] object) {
		return JSONArray.fromObject(object).toString();
	}
	
	/**
	 * 将list转换成String类型的JSON数据格式
	 * @param list
	 * @return
	 */
	public static String list2json(List list) {
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 将map转换成String类型的JSON数据格式
	 * @param map
	 * @return
	 */
	public static String map2json(Map map) {
		return JSONArray.fromObject(map).toString();
	}
	
	/**
	 * 将object对象转换成String类型的JSON数据类型
	 * @param object
	 * @return
	 */
	public static String object2json(Object object) {
		return JSONArray.fromObject(object).toString();
	}

	/**
	 * 将XML数据格式转换成String类型的JSON数据格式
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
