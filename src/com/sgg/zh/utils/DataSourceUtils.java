package com.sgg.zh.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.cj.xdevapi.Statement;


/**
 * 数据库连接工具类
 * 
 * @author Administrator
 *
 */
public class DataSourceUtils {
	private static ComboPooledDataSource ds = new ComboPooledDataSource();
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();

	/**
	 * 从线程中获取连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = tl.get();
		if (conn == null) {
			conn = ds.getConnection();
			// 和当前线程绑定
			tl.set(conn);
		}
		return conn;
	}

	// 获取数据源
	public static DataSource getDataSource() {
		return ds;
	}

	// 释放资源
	public static void closeResource(Statement st, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(st);
	}

	// 释放资源
	public static void closeResource(Connection conn, Statement st, ResultSet rs) {
		closeResource(st, rs);
		closeConn(conn);
	}

	private static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				// 和线程解绑
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
	 * 开启事务 默认的话为自动提交， 每执行一个update ,delete或者insert的时候都会自动提交到数据库，无法回滚事务。
	 * 设置connection.setautocommit(false);只有程序调用connection.commit()的时候才会将先前执行的语句一起提交到数据库，这样就实现了数据库的事务。
	 * 
	 * true：sql命令的提交（commit）由驱动程序负责 false：sql命令的提交由应用程序负责，程序必须调用commit或者rollback方法
	 * 
	 * @throws SQLException
	 */
	public static void startTransaction() throws SQLException {
		getConnection().setAutoCommit(false);
	}

	/**
	 * 事务提交且释放资源
	 */
	public static void commitAndClose() {
		Connection conn = null;
		try {
			conn = getConnection();
			// 事务提交
			conn.commit();
			// 关闭资源
			conn.close();
			// 解除绑定
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
			// 事务回滚
			conn.close();
			// 关闭资源
			conn.rollback();
			// 接触绑定
			tl.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
