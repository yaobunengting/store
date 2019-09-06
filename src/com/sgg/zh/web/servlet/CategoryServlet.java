package com.sgg.zh.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.entity.Category;
import com.sgg.zh.service.CategoryService;
import com.sgg.zh.service.impl.CategoryServiceImpl;
import com.sgg.zh.utils.JsonUtils;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {

	/**
	 * 查询所有的分类
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) {
		
		// 1.调用categoryService,查询所有的分类,返回值list<Category>
		CategoryService cs = new CategoryServiceImpl();
		List<Category> clist = null;
		
		try {
			clist = cs.findAll();
			
			// 2.将返回值转成json格式返回到页面上
			//request.setAttribute("clist", clist);
			
			//写回去
			String json = JsonUtils.list2json(clist);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
