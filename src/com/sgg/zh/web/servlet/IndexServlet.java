package com.sgg.zh.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����ҳ��ص�servlet
 */
@WebServlet("/index")
public class IndexServlet extends BaseServlet {
	
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȥ���ݿ��в�ѯ������Ʒ��������Ʒ ����û����request����,����ת��
		
		
		
		
		return "/jsp/index.jsp";
	}

}
