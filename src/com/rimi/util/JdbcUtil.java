package com.rimi.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {

	// 链接数据库的参数
	private static String driver;
	private static String url;
	private static String userName;
	private static String password;
	// 操作数据库的对象
	private Connection con;
	private Statement sta;
	private ResultSet rs;
	private PreparedStatement  psta;
	// jdbc模板类
	// 1. 导入jar包
	// 2.加载驱动类
	static { // 只加载一次 配置文件
		// 加载配置文件
		Properties pro = new Properties();
		// 配置文件流信息
		InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
		// 解析配置文件
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 将配置文件中的值取出
		driver = pro.getProperty("driver");
		url = pro.getProperty("url");
		userName = pro.getProperty("username");
		password = pro.getProperty("password");
		// 加载驱动 
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 3. 创建链接 1. 自动提交 2.手动提交
	private void getConnecton() {
		// 自动提交
		try {
			con = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getConnection2() {
		// 手动提交
		try {
			con = DriverManager.getConnection(url, userName, password);
			con.setAutoCommit(false); // 设置手动提交
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 4. 通过连接 创建通道 1.状态通道 2.预状态通道 3.存储过程通道
	// 自动提交 状态通道
	private void createStatement() {
		this.getConnecton();
		try {
			sta = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 手动提交 状态通道
	private void createStatement2() {
		this.getConnection2();
		try {
			sta = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//创建预状态通道    使用自动提交的链接
	private   void   createPstatement(String sql) {
		this.getConnecton();
		try {
			psta=con.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("创建与状态通道失败");
		}
	}
	
	//创建与状态通道  使用手动提交的链接
	private  void createPstatement2(String sql) {
		this.getConnection2();
		try {
			psta=con.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 5. 执行sql 语句 1.基于状态通道 的 查询 和 修改（修改，添加，删除） 2. 基于与状态通道 查询和修改
	// 基于自动提交 通道执行sql 语句 查询 修改
	public ResultSet query(String sql) {
		this.createStatement();
		try {
			// 执行sql语句
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 基于状态通道修改
	public boolean update(String sql) {
		this.createStatement();
		int num = 0;
		try {
			// 执行sql语句 得到影响行数
			num = sta.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 判断是否操作成功
		if (num > 0) {
			return true;
		}
		return false;
	}

	// 基于状态通道 批处理
	public boolean updates(String[] sqls) throws SQLException {
		// 使用手动提交的通道
		this.createStatement2();
		boolean isok = true;
		if (sqls != null) {
			// 相当于 将sql语句执行 但是操作的是缓存中的数据 数据库中真实的数据不发生改变
			for (int i = 0; i < sqls.length; i++) {
				try {
					// 将sql语句添加到缓存中 语句有错误会发生异常
					sta.addBatch(sqls[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// 执行sql语句
				int[] nums = sta.executeBatch();

				for (int j = 0; j < nums.length; j++) {
					if (nums[j] <= 0) {
						isok = false;
					}
				}
			}
		}
		return isok;
	}

	
	//基于与状态通道的查询和修改  
	public  ResultSet  query2(String sql,String[] param) {
		//
		this.createPstatement(sql);
		//绑定参数
		this.bandl(param);
		//执行sql语句
		try {
			rs=psta.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	//基于与状态自动提交 的修改
	public boolean  update2(String sql,String[] param) {
		
		this.createPstatement(sql);
		//绑定参数
		this.bandl(param);
		
		try {
			int  num=psta.executeUpdate();
			if(num<=0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	//与状态通道的批处理
	public  boolean  updates2(String sql,String[][]  params) throws SQLException {
		//
		this.createPstatement2(sql);
		//
		for (int i = 0; i < params.length; i++) {
			//每次循环拿到一条记录的数据
			String[] param=params[i];
			//绑定参数
			this.bandl(param);
			//将sql语句天添加到缓存中
			try {
				psta.addBatch();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		boolean isok=true;
		int[]  num=psta.executeBatch();
		for (int i = 0; i < num.length; i++) {
			if(num[i]<=0) {
				isok=false;
			}
		}
		
		return isok;
	}	
	
	
	
	//与状态通道绑定参数
	private void  bandl(String[] param) {
		if(param!=null) {
			for (int i = 0; i < param.length; i++) {
				try {
					
					psta.setString(i+1,param[i]);
					
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("绑定参数失败");
				}
			}
		}
	}
	
	public void myCommit() {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void myRollBack() {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 6. 关闭资源 关闭连接
	public void closeRes() {
		// 采用就近原则
		try {
			if (rs != null) {
				rs.close();
			}
			if(psta!=null) {
				psta.close();
			}
			if (sta != null) {
				sta.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
