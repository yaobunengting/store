package com.sgg.zh.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.sgg.zh.Entity.User;
import com.sgg.zh.service.UserService;
import com.sgg.zh.service.impl.UserServiceImpl;
import com.sgg.zh.utils.UUIDUtils;

/**
 *  ���û���ص�servlet
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("user,servlet��add����ִ����");
		
		return null;
	}
	
	/**
	 * ��ת��ע��ҳ��
	 * @param request
	 * @param response
	 * @return
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) {
		return "/jsp/register.jsp";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("regist");
		//1.��װ����
		User user = new User();
		BeanUtils.populate(user, request.getParameterMap());
		
		//1.1�����û�id
		user.setUid(UUIDUtils.getId());
		//1.2���ü�����
		user.setCode(UUIDUtils.getCode());
		System.out.println(user);
		//2.����Service
		UserService s = new UserServiceImpl();
		//s.regist(user);
		
		//3.ҳ������ת��
		request.setAttribute("msg", "�û�ע��ɹ�,�����˼���");
		
		
		
		return "/jsp/msg.jsp";
	}
	
	/**
	 * ��ת��½ҳ��
	 * @param request
	 * @param response
	 * @return
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) {
		return "/jsp/login.jsp";
	}
	
	

}
