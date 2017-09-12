package com.rimi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rimi.bean.UserBean;
import com.rimi.util.JdbcUtil;

public class UserDao extends JdbcUtil{

	/**
	 * 用户登录的方法
	 * @param name
	 * @param ps
	 * @return
	 */
	public  UserBean  login(String username,String password) {
		String sql="select * from  studentg  where username = ? and password = ?";
		String[] param= {username,password};
		ResultSet rs=this.query2(sql, param);
		
		UserBean ub=null;
		try {
			if(rs.next()) {
				 ub=new UserBean(rs.getInt(1),rs.getString(2),rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ub;
	}
	
	public  List   userInfo() {
		String sql="select  *  from studentg";
		ResultSet rs=this.query(sql);
		List list=new ArrayList();
		try {
			while(rs.next()) {
				UserBean ub=new UserBean(rs.getInt(1),rs.getString(2),rs.getString(3));
				list.add(ub);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
}
