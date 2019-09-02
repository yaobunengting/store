package com.sgg.zh.dao.impl;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;

import com.sgg.zh.Entity.User;
import com.sgg.zh.dao.UserDao;
import com.sgg.zh.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	/**
	 * ÃÌº””√ªß
	 * @throws SQLException 
	 */
	@Override
	public void add(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
		qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(), 
				user.getName(), user.getEmail(), user.getTelephpne(),
				user.getBirthday(), user.getSex(), user.getState(), user.getCode());
	}

}
