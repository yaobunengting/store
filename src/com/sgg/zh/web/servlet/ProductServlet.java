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
 * 商品servlet
 */
@WebServlet("/product")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	ProductService ps = (ProductService) BeanFactory.getBean("ProductService");

	/**
	 * 通过id查询单个商品详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.获取商品id
		String pid = request.getParameter("pid");

		try {
			// 2.调用service
			Product p = ps.getById(pid);
			// 3.将结果放入request域中
			request.setAttribute("bean", p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/jsp/product_info.jsp";
	}

	/**
	 * 分叶查询数据
	 * 
	 * @param request
	 * @param responce
	 * @return
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse responce) {
		// 1.获取类别当前页 设置一个pageSize
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 12;

		// 2.调用service,返回值pageBean
		PageBean<Product> bean;
		try {
			bean = ps.findByPage(currPage, pageSize, cid);
			System.out.println("bean:" + bean);
			// 3.将结果放入request中,请求转发
			request.setAttribute("pb", bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/jsp/product_list.jsp";

	}

}
