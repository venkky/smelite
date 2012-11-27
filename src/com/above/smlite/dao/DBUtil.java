package com.above.smlite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DBUtil {
	
	private static String _dataSource = null;
	private static String _driver = null;
	private static String _connUrl = null;
	private static boolean _initialized = false;
	private static DataSource _ds = null;
	
	private final static String DATA_SOURCE_NAME = "jdbc.datasource";
	private final static String JDBC_DRIVER = "jdbc.driver";
	private final static String JDBC_CONN_URL = "jdbc.connection.url";
	
	
	public static Connection getConnection() throws Exception {

		if(!_initialized ){
			_initialize();
		}
		
		if(_dataSource == null || 
				_dataSource.trim().length() == 0){
				Class.forName(_driver).newInstance();
				return DriverManager.getConnection(_connUrl);
			
		} else {
			
			if(_ds == null){
				Context _ctx = new InitialContext();
				_ds = (DataSource)_ctx.lookup(_dataSource);
				return _ds.getConnection();
			}
		}
		
		return null;
		
	}
	
	private static void _initialize() throws Exception {
		
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dbinfo.properties"));
		
		_dataSource = props.getProperty(DATA_SOURCE_NAME);
		_driver = props.getProperty(JDBC_DRIVER);
		_connUrl = props.getProperty(JDBC_CONN_URL);
		
		_initialized = true;
	}

}
