package com.sgg.zh.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.cj.xdevapi.Statement;


/**
 * ���ݿ����ӹ�����
 * 
 * @author Administrator
 *
 */
public class DataSourceUtils {
	private static ComboPooledDataSource ds = new ComboPooledDataSource();
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();

	/**
	 * ���߳��л�ȡ����
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = tl.get();
		if (conn == null) {
			conn = ds.getConnection();
			// �͵�ǰ�̰߳�
			tl.set(conn);
		}
		return conn;
	}

	// ��ȡ����Դ
	public static DataSource getDataSource() {
		return ds;
	}

	// �ͷ���Դ
	public static void closeResource(Statement st, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(st);
	}

	// �ͷ���Դ
	public static void closeResource(Connection conn, Statement st, ResultSet rs) {
		closeResource(st, rs);
		closeConn(conn);
	}

	private static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				// ���߳̽��
				tl.remove();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null;

		}

	}

	private static void closeStatement(Statement st) {
		if (st != null) {
			try {
				((Connection) st).close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			st = null;
		}

	}

	private static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs = null;
	}

	/**
	 * �������� Ĭ�ϵĻ�Ϊ�Զ��ύ�� ÿִ��һ��update ,delete����insert��ʱ�򶼻��Զ��ύ�����ݿ⣬�޷��ع�����
	 * ����connection.setautocommit(false);ֻ�г������connection.commit()��ʱ��ŻὫ��ǰִ�е����һ���ύ�����ݿ⣬������ʵ�������ݿ������
	 * 
	 * true��sql������ύ��commit�������������� false��sql������ύ��Ӧ�ó����𣬳���������commit����rollback����
	 * 
	 * @throws SQLException
	 */
	public static void startTransaction() throws SQLException {
		getConnection().setAutoCommit(false);
	}

	/**
	 * �����ύ���ͷ���Դ
	 */
	public static void commitAndClose() {
		Connection conn = null;
		try {
			conn = getConnection();
			// �����ύ
			conn.commit();
			// �ر���Դ
			conn.close();
			// �����
			tl.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void rollbackAndClose() {
		Connection conn = null;
		try {
			conn = getConnection();
			// ����ع�
			conn.close();
			// �ر���Դ
			conn.rollback();
			// �Ӵ���
			tl.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
