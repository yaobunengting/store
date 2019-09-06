package com.sgg.zh.service.impl;

import com.sgg.zh.dao.UserDao;
import com.sgg.zh.dao.impl.UserDaoImpl;
import com.sgg.zh.entity.User;
import com.sgg.zh.service.UserService;
import com.sgg.zh.utils.MailUtils;

public class UserServiceImpl implements UserService{

	/**
	 * �û�ע��
	 */
	@Override
	public void regist(User user) throws Exception {
		UserDao dao = new UserDaoImpl();
		dao.add(user);
		
		//�����ʼ�
		/*
		 * email:�ռ��˵ĵ�ַ
		 * emailMsg:�ʼ�������
		 */
		String emailMsg = "��ӭ��ע������ǵ�һԱ,<a href='http://localhost:8080/store/user?method=active&code="+user.getCode()+"'>��˼���</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}

	/**
	 * �û�����
	 */
	@Override
	public User active(String code) throws Exception {
		UserDao dao = new UserDaoImpl();
		//1.ͨ��code��ȡһ���û�
		User user = dao.getByCode(code);
		
		//2.�ж��û��Ƿ�Ϊ��
		if(user == null) {
			return null;
		}
		
		//3.�޸��û�״̬
		//���û���״̬����Ϊ1
		user.setState(1);
		dao.update(user);
		return user;
	}

	/**
	 * �û���¼
	 */
	@Override
	public User login(String username, String password) throws Exception {
		UserDao dao = new UserDaoImpl();
		return dao.getByUsernameAndPwd(username, password);
	}

}