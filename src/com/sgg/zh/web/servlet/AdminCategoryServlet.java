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
 * ��̨�������
 */
@WebServlet("/adminCategory")
public class AdminCategoryServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");

	/**
	 * չʾ���з���
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.����categoryService,��ѯ���еķ�����Ϣ,����ֵlist
		List<Category> list = cs.findAll();
		//2.��list����request����,����ת������
		request.setAttribute("list", list);
		
		return "/admin/category/list.jsp";
	}

	/**
	 * ��ת�����ҳ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/category/add.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����cname
		String cname = request.getParameter("cname");
		
		//2.��װcategory
		Category category = new Category();
		//2.1����cid
		category.setCid(UUIDUtils.getId());
		//2.2����cname
		category.setCname(cname);
		
		//3.����service�����Ӳ���
		cs.add(category);
		//4.�ض����ѯ���з���
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		return null;
	}
	
	/**
	 * ͨ��id��ȡ������Ϣ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����cid
		String cid = request.getParameter("cid");
		//2.����service��ɲ�ѯ����	����ֵ:category
		
		Category c =  cs.getById(cid);
		
		//3.��category����request����,����ת��/admin/category/edit.jsp
		request.setAttribute("bean", c);
		return "/admin/category/edit.jsp";
		}
	
	/**
	 * ���·�����Ϣ����
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//��ȡcid
		String cid = request.getParameter("cid");
		
		//��ȡcname
		String cname = request.getParameter("cname");
		//��װ����
		Category c = new Category();
		c.setCid(cid);
		c.setCname(cname);
		
		//����service��ɸ��²���
		cs.update(c);
		
		//�ض����ѯ����
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
	}
	
	/**
	 * ɾ������
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//��ȡcid
		String cid = request.getParameter("cid");
	
		//����service���ɾ������
		cs.delete(cid);
		
		//�ض���
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
	}
}
