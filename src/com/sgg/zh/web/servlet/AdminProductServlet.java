package com.sgg.zh.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.Category;
import com.sgg.zh.entity.Product;
import com.sgg.zh.service.CategoryService;
import com.sgg.zh.service.ProductService;
import com.sgg.zh.utils.BeanFactory;

/**
 * 后台商品管理
 */
@WebServlet("/adminProduct")
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询所有商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.调用service查询所有返回一个list
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list = ps.findAll();
		
		//2.将list放入request域中,请求转发
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
	}
	
	/**
	 * 跳转到添加商品的页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询所有的分类 返回list
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = cs.findAll();
		//将list放入request域中
		request.setAttribute("clist", clist);
		return "/admin/product/add.jsp";
	}

	/**
	 * 商品下架
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取pid
		String pid = request.getParameter("pid");
		System.out.println("oid");
		//调用service查询商品
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		
		Product p = ps.getById(pid);
		p.setPflag(1);
		ps.update(p);
		
		response.sendRedirect(request.getContextPath() + "/adminProduct?method=findAll");
		
		return null;
	}
	
	public String takeOff(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.调用service查询所有返回一个list
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list = ps.findAllByTakeOff();
		
		//2.将list放入request域中,请求转发
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
	}
}
