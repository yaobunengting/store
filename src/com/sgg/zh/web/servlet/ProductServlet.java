package com.sgg.zh.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.Product;
import com.sgg.zh.service.ProductService;
import com.sgg.zh.service.impl.ProductServiceImpl;

/**
 * ��Ʒservlet
 */
@WebServlet("/product")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 *	ͨ��id��ѯ������Ʒ����
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ��Ʒid
		String pid = request.getParameter("pid");
		//2.����service
		ProductService ps = new ProductServiceImpl();
		try {
			Product p = ps.getById(pid);
			//3.���������request����
			request.setAttribute("bean", p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return "/jsp/product_info.jsp";
	}

}
