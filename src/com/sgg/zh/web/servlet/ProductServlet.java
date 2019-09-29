package com.sgg.zh.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.PageBean;
import com.sgg.zh.entity.Product;
import com.sgg.zh.service.ProductService;
import com.sgg.zh.utils.BeanFactory;

/**
 * ��Ʒservlet
 */
@WebServlet("/product")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	ProductService ps = (ProductService) BeanFactory.getBean("ProductService");

	/**
	 * ͨ��id��ѯ������Ʒ����
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.��ȡ��Ʒid
		String pid = request.getParameter("pid");

		try {
			// 2.����service
			Product p = ps.getById(pid);
			// 3.���������request����
			request.setAttribute("bean", p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/jsp/product_info.jsp";
	}

	/**
	 * ��Ҷ��ѯ����
	 * 
	 * @param request
	 * @param responce
	 * @return
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse responce) {
		// 1.��ȡ���ǰҳ ����һ��pageSize
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 12;

		// 2.����service,����ֵpageBean
		PageBean<Product> bean;
		try {
			bean = ps.findByPage(currPage, pageSize, cid);
			System.out.println("bean:" + bean);
			// 3.���������request��,����ת��
			request.setAttribute("pb", bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/jsp/product_list.jsp";

	}

}
