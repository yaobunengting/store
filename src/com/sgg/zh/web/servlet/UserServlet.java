package com.sgg.zh.web.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.sgg.zh.Entity.User;
import com.sgg.zh.myconventer.MyConventer;
import com.sgg.zh.service.UserService;
import com.sgg.zh.service.impl.UserServiceImpl;
import com.sgg.zh.utils.MD5Utils;
import com.sgg.zh.utils.UUIDUtils;

/**
 * 和用户相关的servlet
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {

	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("user,servlet的add方法执行了");

		return null;
	}

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
		System.out.println("regist");
		// 1.封装数据
		User user = new User();
		// 注册自定义转换器
		ConvertUtils.register(new MyConventer(), LocalDate.class);
		BeanUtils.populate(user, request.getParameterMap());

		// 1.1设置用户id
		user.setUid(UUIDUtils.getId());
		
		// 1.2设置激活码
		user.setCode(UUIDUtils.getCode());
		
		// 1.3加密密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		// 2.调用Service
		UserService s = new UserServiceImpl();
		s.regist(user);

		// 3.页面请求转发
		request.setAttribute("msg", "用户注册成功,别忘了激活");

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

}
