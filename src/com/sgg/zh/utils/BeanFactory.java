package com.sgg.zh.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
			//5.反射	以前的逻辑直接返回的是
			//return Class.forName(value).newInstance();
			
			//现在对service中所有的add方法进行加强,返回的是代理对象
			Object obj = Class.forName(value).newInstance();
			//是service的实现类
			if(id.endsWith("Service")) {
				Object proxyObj= Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//继续判断是否调用的add方法或者regist
						if("add".equals(method.getName()) || "regis".equals(method.getName())) {
							System.out.println("添加操作");
							return method.invoke(obj, args);
						}
						return method.invoke(obj, args);
					}
				});
				//若是service方法返回的是代理对象
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
