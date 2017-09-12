package com.rimi.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet  extends HttpServlet{

	
	
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		System.out.println("调用service方法");
		super.service(arg0, arg1);
	}

	@Override
	public void destroy() {
		System.out.println("servlet销毁");
	}

	@Override
	public void init() throws ServletException {
		System.out.println("servlet初始化");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("get");
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post");
		
		//获取初始化参数
		String name=this.getInitParameter("name");
		String pass=this.getInitParameter("password");
		System.out.println(name);
		System.out.println(pass);
		// 获取初始化参数的另外两种方式
		
		//接收请求信息
		
		//对请求信进行处理  判断
		
		//根据 处理的结果  进行响应
		
	}

	//必须继承 httpservlet
	// 实现方法   doGet  doPost
	
	
	
	
}
