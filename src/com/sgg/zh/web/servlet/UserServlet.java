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
 * 和用户相关的servlet
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {
	UserService s = (UserService) BeanFactory.getBean("UserService");

	/**
	 * 跳转到注册页面
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
		// 1.封装数据
		User user = new User();
		/*
		 * 注册自定义转换器 需要把Data类型和String转换
		 */
		ConvertUtils.register(new MyConventer(), Date.class);
		// 封装到user
		BeanUtils.populate(user, request.getParameterMap());

		// 1.1设置用户id
		user.setUid(UUIDUtils.getId());

		// 1.2设置激活码
		user.setCode(UUIDUtils.getCode());

		// 1.3加密密码
		user.setPassword(MD5Utils.md5(user.getPassword()));

		// 2.调用Service
		s.regist(user);

		// 3.页面请求转发
		request.setAttribute("msg", "用户注册成功,别忘了激活");

		return "/jsp/msg.jsp";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) {
		// 1.获取激活码
		String code = request.getParameter("code");
		
		// 2.调用service完成激活
		try {
			User user = s.active(code);
			if (user == null) {
				// 通过激活码没有找到用户
				request.setAttribute("msg", "请重新激活");
			} else {
				// 添加信息
				request.setAttribute("msg", "用户激活成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 3.请求转发到msg.jsp
		return "/jsp/msg.jsp";
	}

	/**
	 * 跳转登陆页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) {
		return "/jsp/login.jsp";
	}

	/**
	 * 用戶登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) {
		// 1.获取用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Utils.md5(password);
		// 2.调用service完成登录操作,返回user
		UserService s = new UserServiceImpl();
		try {
			User user = s.login(username, password);
			// 3.判断用户
			if (user == null) {
				// 用户名密码不匹配
				request.setAttribute("msg", "用户名密码不匹配");
				return "/jsp/login.jsp";
			} else {
				// 继续判断用户的状态是否激活
				if (Constant.USER_IS_ACTIVE != user.getState()) {
					// 用户未激活
					request.setAttribute("msg", "用户未激活");
					return "/jsp/login.jsp";
				}
			}
			// 4.将user放入session中,重定向
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
	 * 用户退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// 干掉session
		request.getSession().invalidate();

		// 重定向
		try {
			response.sendRedirect(request.getContextPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理自动登录
		return null;
	}
}
