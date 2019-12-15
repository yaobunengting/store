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
		//强转
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//2.自己的业务逻辑
		//从session中获取user,判断user是否为空,若为空请求转发
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			request.setAttribute("msg", "请先登录");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);;
			return ;
		}
		//3.放行
		chain.doFilter(request, response);
	}

}
