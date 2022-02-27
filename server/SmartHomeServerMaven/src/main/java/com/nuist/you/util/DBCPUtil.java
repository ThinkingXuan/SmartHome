package com.nuist.you.util;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBCPUtil {

	private static DataSource dataSource;
	static{
		try {
			//读取配置文件，初始化数据源
			InputStream in = DBCPUtil.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			Properties props = new Properties();
			props.load(in);
			dataSource = BasicDataSourceFactory.createDataSource(props);
		}  catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
		
	}
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("获取数据库连接失败");
		}
	}
	
	/**
	 * 测试数据库连接
	 * @param args
	 */
	public static void main(String[] args) {
		LogUtil.info(getDataSource().toString());
		LogUtil.info(getConnection().toString());
	}
	
}
