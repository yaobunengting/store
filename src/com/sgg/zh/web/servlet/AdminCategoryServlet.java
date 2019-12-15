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

/**
 * 后台分类管理
 */
@WebServlet("/adminCategory")
public class AdminCategoryServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 展示所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.调用categoryService,查询所有的分类信息,返回值list
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = cs.findAll();
		//2.将list放入request域中,请求转发即可
		request.setAttribute("list", list);
		
		return "/admin/category/list.jsp";
	}

	
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.接受cid
		
		//2.调用service完成查询操作	返回值:category
		
		//3.将category放入request域中,请求转发/admin/category/edit.jsp
		
		return "/admin/category/list.jsp";
		}
}
