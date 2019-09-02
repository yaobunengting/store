package com.sgg.zh.service.impl;

import com.sgg.zh.Entity.User;
import com.sgg.zh.dao.UserDao;
import com.sgg.zh.dao.impl.UserDaoImpl;
import com.sgg.zh.service.UserService;

public class UserServiceImpl implements UserService{

	/**
	 * ÓÃ»§×¢²á
	 */
	@Override
	public void regist(User user) throws Exception {
		UserDao dao = new UserDaoImpl();
		dao.add(user);
	}

}
