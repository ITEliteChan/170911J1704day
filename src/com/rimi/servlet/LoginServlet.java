package com.rimi.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rimi.bean.UserBean;
import com.rimi.dao.UserDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	UserDao  ud=new UserDao();
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		//接收请求信息
		String userName=request.getParameter("userName");
		String userPs=request.getParameter("userPs");
		// 去数据库查询 是否存在该用户
		UserBean ub=ud.login(userName, userPs);
		if(ub!=null) {
			//登录成功  跳转主页  请求转发：能够携带参数到页面
			request.setAttribute("ub", ub);
			//将用户的集合存储到request中
			request.setAttribute("userInfo", ud.userInfo());
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}else {
			//登录失败   留在登录页面
			response.sendRedirect("login.html");
		}
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
