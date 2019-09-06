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
			
			// 2.������ֵת��json��ʽ���ص�ҳ����
			//request.setAttribute("clist", clist);
			
			//д��ȥ
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
