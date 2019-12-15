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
 * ��̨�������
 */
@WebServlet("/adminCategory")
public class AdminCategoryServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * չʾ���з���
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.����categoryService,��ѯ���еķ�����Ϣ,����ֵlist
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = cs.findAll();
		//2.��list����request����,����ת������
		request.setAttribute("list", list);
		
		return "/admin/category/list.jsp";
	}

	
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����cid
		
		//2.����service��ɲ�ѯ����	����ֵ:category
		
		//3.��category����request����,����ת��/admin/category/edit.jsp
		
		return "/admin/category/list.jsp";
		}
}
