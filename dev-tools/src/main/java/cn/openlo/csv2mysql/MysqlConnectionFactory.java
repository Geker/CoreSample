package cn.openlo.csv2mysql;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;





/**
 * 
 * <p>
 * Title: MysqlConnectionFactory
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: ufo Copyright (C) 2016
 * </p>
 *
 * @author SONGQQ
 * @version
 * @since 2016年12月2日
 */
public enum MysqlConnectionFactory {
	INSTANCE;

    // private Connection conn;
    MysqlDataSource mysqlDataSource ;
	private MysqlConnectionFactory() {
		initConn();

	}

	private void initConn() {
		try {
            Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        mysqlDataSource = new MysqlDataSource();

	}
    //
    // public Connection getConnection() {
    //
    // return conn;
    // }
	
    public MysqlDataSource getDataSource() {

        return mysqlDataSource;
	}

    public void close() {

    }
}
