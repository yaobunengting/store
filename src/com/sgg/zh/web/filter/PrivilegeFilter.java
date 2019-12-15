package com.sgg.zh.web.filter;

import java.io.IOException;
import java.util.logging.LogRecord;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.User;

public class PrivilegeFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//ǿת
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//2.�Լ���ҵ���߼�
		//��session�л�ȡuser,�ж�user�Ƿ�Ϊ��,��Ϊ������ת��
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			request.setAttribute("msg", "���ȵ�¼");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);;
			return ;
		}
		//3.����
		chain.doFilter(request, response);
	}

}
