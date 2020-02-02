package com.sgg.zh.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.Category;
import com.sgg.zh.service.CategoryService;
import com.sgg.zh.utils.BeanFactory;
import com.sgg.zh.utils.UUIDUtils;

/**
 * 后台分类管理
 */
@WebServlet("/adminCategory")
public class AdminCategoryServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");

	/**
	 * 展示所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.调用categoryService,查询所有的分类信息,返回值list
		List<Category> list = cs.findAll();
		//2.将list放入request域中,请求转发即可
		request.setAttribute("list", list);
		
		return "/admin/category/list.jsp";
	}

	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/category/add.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.接受cname
		String cname = request.getParameter("cname");
		
		//2.封装category
		Category category = new Category();
		//2.1设置cid
		category.setCid(UUIDUtils.getId());
		//2.2设置cname
		category.setCname(cname);
		
		//3.调用service完成添加操作
		cs.add(category);
		//4.重定向查询所有分类
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		return null;
	}
	
	/**
	 * 通过id获取分类信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.接受cid
		String cid = request.getParameter("cid");
		//2.调用service完成查询操作	返回值:category
		
		Category c =  cs.getById(cid);
		
		//3.将category放入request域中,请求转发/admin/category/edit.jsp
		request.setAttribute("bean", c);
		return "/admin/category/edit.jsp";
		}
	
	/**
	 * 更新分类信息方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取cid
		String cid = request.getParameter("cid");
		
		//获取cname
		String cname = request.getParameter("cname");
		//封装参数
		Category c = new Category();
		c.setCid(cid);
		c.setCname(cname);
		
		//调用service完成更新操作
		cs.update(c);
		
		//重定向查询所有
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
	}
	
	/**
	 * 删除分类
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取cid
		String cid = request.getParameter("cid");
	
		//调用service完成删除操作
		cs.delete(cid);
		
		//重定向
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
	}
}
