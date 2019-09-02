package com.sgg.zh.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  ͨ�õ�Servlet
 */
public class BaseServlet extends HttpServlet {
       
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.��ȡ����
			Class clazz = this.getClass();
			System.out.println(this);
			
			//2.��ȡ����ķ���
			String m = request.getParameter("method");
			if(m == null) {
				m = "index";
			}
			
			//3.��ȡ��������
			Method method = clazz.getMethod(m, HttpServletRequest.class, HttpServletResponse.class);
			
			//�÷���ִ��,����ֵΪ����ת����·��
			String s = (String) method.invoke(this, request, response);
			
			//�ж�s�Ƿ�Ϊ��
			if(s != null) {
				//����ת��
				request.getRequestDispatcher(s).forward(request, response);	//�൱��userServlet.add(request, response)
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		} 
		
	}
	
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return null;
	}

	

}
