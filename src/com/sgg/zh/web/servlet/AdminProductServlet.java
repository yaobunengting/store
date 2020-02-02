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
 * ��̨��Ʒ����
 */
@WebServlet("/adminProduct")
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ѯ������Ʒ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����service��ѯ���з���һ��list
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list = ps.findAll();
		
		//2.��list����request����,����ת��
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
	}
	
	/**
	 * ��ת�������Ʒ��ҳ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//��ѯ���еķ��� ����list
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = cs.findAll();
		//��list����request����
		request.setAttribute("clist", clist);
		return "/admin/product/add.jsp";
	}

	/**
	 * ��Ʒ�¼�
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//��ȡpid
		String pid = request.getParameter("pid");
		System.out.println("oid");
		//����service��ѯ��Ʒ
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		
		Product p = ps.getById(pid);
		p.setPflag(1);
		ps.update(p);
		
		response.sendRedirect(request.getContextPath() + "/adminProduct?method=findAll");
		
		return null;
	}
	
	public String takeOff(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����service��ѯ���з���һ��list
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list = ps.findAllByTakeOff();
		
		//2.��list����request����,����ת��
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
	}
}
