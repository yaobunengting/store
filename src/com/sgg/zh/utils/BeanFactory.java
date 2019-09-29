package com.sgg.zh.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 实体工厂类
 * @author Administrator
 *
 */
public class BeanFactory {
	public static Object getBean(String id) {
		
		//1.通过id获取一个指定的实现类
		
		//2..获取document对象
		try {
			Document doc = new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
		
			//3.获取指定的bean对象
			Element ele = (Element) doc.selectSingleNode("//bean[@id='"+id+"']");
			//4.获取bean对象的class属性
			String value = ele.attributeValue("class");
			//5.反射
			return Class.forName(value).newInstance();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;
	}
	
}
