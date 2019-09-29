package com.sgg.zh.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.sgg.zh.constant.Constant;
import com.sgg.zh.entity.User;
import com.sgg.zh.myconventer.MyConventer;
import com.sgg.zh.service.UserService;
import com.sgg.zh.service.impl.UserServiceImpl;
import com.sgg.zh.utils.BeanFactory;
import com.sgg.zh.utils.MD5Utils;
import com.sgg.zh.utils.UUIDUtils;

/**
 * ���û���ص�servlet
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {
	UserService s = (UserService) BeanFactory.getBean("UserService");

	/**
	 * ��ת��ע��ҳ��
	 * 
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
		// 1.��װ����
		User user = new User();
		/*
		 * ע���Զ���ת���� ��Ҫ��Data���ͺ�Stringת��
		 */
		ConvertUtils.register(new MyConventer(), Date.class);
		// ��װ��user
		BeanUtils.populate(user, request.getParameterMap());

		// 1.1�����û�id
		user.setUid(UUIDUtils.getId());

		// 1.2���ü�����
		user.setCode(UUIDUtils.getCode());

		// 1.3��������
		user.setPassword(MD5Utils.md5(user.getPassword()));

		// 2.����Service
		s.regist(user);

		// 3.ҳ������ת��
		request.setAttribute("msg", "�û�ע��ɹ�,�����˼���");

		return "/jsp/msg.jsp";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) {
		// 1.��ȡ������
		String code = request.getParameter("code");
		
		// 2.����service��ɼ���
		try {
			User user = s.active(code);
			if (user == null) {
				// ͨ��������û���ҵ��û�
				request.setAttribute("msg", "�����¼���");
			} else {
				// �����Ϣ
				request.setAttribute("msg", "�û�����ɹ�");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 3.����ת����msg.jsp
		return "/jsp/msg.jsp";
	}

	/**
	 * ��ת��½ҳ��
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) {
		return "/jsp/login.jsp";
	}

	/**
	 * �Ñ���¼
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) {
		// 1.��ȡ�û���������
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Utils.md5(password);
		// 2.����service��ɵ�¼����,����user
		UserService s = new UserServiceImpl();
		try {
			User user = s.login(username, password);
			// 3.�ж��û�
			if (user == null) {
				// �û������벻ƥ��
				request.setAttribute("msg", "�û������벻ƥ��");
				return "/jsp/login.jsp";
			} else {
				// �����ж��û���״̬�Ƿ񼤻�
				if (Constant.USER_IS_ACTIVE != user.getState()) {
					// �û�δ����
					request.setAttribute("msg", "�û�δ����");
					return "/jsp/login.jsp";
				}
			}
			// 4.��user����session��,�ض���
			request.getSession().setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * �û��˳�
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// �ɵ�session
		request.getSession().invalidate();

		// �ض���
		try {
			response.sendRedirect(request.getContextPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �����Զ���¼
		return null;
	}
}
