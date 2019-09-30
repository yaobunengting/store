package com.sgg.zh.service.impl;

import com.sgg.zh.dao.UserDao;
import com.sgg.zh.dao.impl.UserDaoImpl;
import com.sgg.zh.entity.User;
import com.sgg.zh.service.UserService;
import com.sgg.zh.utils.BeanFactory;
import com.sgg.zh.utils.MailUtils;

public class UserServiceImpl implements UserService{
	UserDao dao = (UserDao) BeanFactory.getBean("UserDao");

	/**
	 * 用户注册
	 */
	@Override
	public void regist(User user) throws Exception {
		dao.add(user);
		
		//发送邮件
		/*
		 * email:收件人的地址
		 * emailMsg:邮件的内容
		 */
		String emailMsg = "欢迎您注册成我们的一员,<a href='http://localhost:8080/store/user?method=active&code="+user.getCode()+"'>点此激活</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}

	/**
	 * 用户激活
	 */
	@Override
	public User active(String code) throws Exception {
		//1.通过code获取一个用户
		User user = dao.getByCode(code);
		
		//2.判断用户是否为空
		if(user == null) {
			return null;
		}
		
		//3.修改用户状态
		//将用户的状态设置为1
		user.setState(1);
		dao.update(user);
		return user;
	}

	/**
	 * 用户登录
	 */
	@Override
	public User login(String username, String password) throws Exception {
		return dao.getByUsernameAndPwd(username, password);
	}

}