package com.sgg.zh.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ʵ�幤����
 * @author Administrator
 *
 */
public class BeanFactory {
	public static Object getBean(String id) {
		
		//1.ͨ��id��ȡһ��ָ����ʵ����
		
		//2..��ȡdocument����
		try {
			Document doc = new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
		
			//3.��ȡָ����bean����
			Element ele = (Element) doc.selectSingleNode("//bean[@id='"+id+"']");
			//4.��ȡbean�����class����
			String value = ele.attributeValue("class");
			//5.����
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
