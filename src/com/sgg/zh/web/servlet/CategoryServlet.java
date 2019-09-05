package com.sgg.zh.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgg.zh.Entity.Category;
import com.sgg.zh.service.CategoryService;
import com.sgg.zh.service.impl.CategoryServiceImpl;

public class CategoryServlet extends BaseServlet {

	/**
	 * ��ѯ���еķ���
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) {
		// 1.����categoryService,��ѯ���еķ���,����ֵlist<Category>
		CategoryService cs = new CategoryServiceImpl();
		List<Category> clist = null;
		try {
			clist = cs.findAll();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 2.������ֵת��json��ʽ���ص�ҳ����
		//request.setAttribute("clist", clist);

		return null;
	}
}
