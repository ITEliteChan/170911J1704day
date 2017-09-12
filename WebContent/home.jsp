<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.rimi.bean.UserBean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% UserBean ub=(UserBean)request.getAttribute("ub"); %>
	当前登录人：<%=ub.getUserName() %><br>
	el表达式：		${ub.userName}
	
	jsp页面遍历 循环
	jstl 标签库	<!--var 变量    items要便利的集合  -->
	<c:forEach var="u" items="${userInfo}">
			${u.userName} <br>
			${u.password} <br>
	</c:forEach>

</body>
</html>