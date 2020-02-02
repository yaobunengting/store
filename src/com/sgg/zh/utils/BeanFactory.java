package com.sgg.zh.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
			//5.����	��ǰ���߼�ֱ�ӷ��ص���
			//return Class.forName(value).newInstance();
			
			//���ڶ�service�����е�add�������м�ǿ,���ص��Ǵ������
			Object obj = Class.forName(value).newInstance();
			//��service��ʵ����
			if(id.endsWith("Service")) {
				Object proxyObj= Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//�����ж��Ƿ���õ�add��������regist
						if("add".equals(method.getName()) || "regis".equals(method.getName())) {
							System.out.println("��Ӳ���");
							return method.invoke(obj, args);
						}
						return method.invoke(obj, args);
					}
				});
				//����service�������ص��Ǵ������
				return proxyObj;
			}
			return obj;
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
