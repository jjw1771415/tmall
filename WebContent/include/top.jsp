<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="top">
	<a href="${contextPath}"><span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>天猫首页</a>
	<span>喵，欢迎来到天猫</span>
	
	<c:if test="${!empty user}">
		<span>${user.name}</span>
		<a href="fore_logout">退出</a>
	</c:if>
	<c:if test="${empty user}">
		<a href="login.jsp">请登录</a>
		<a href="register.jsp">免费注册</a>
	</c:if>
	
	<span class="pull-right">
		<a href="fore_bought">我的订单</a>
		<a href="fore_cart">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
            	购物车<strong>${cartItemTotalNumber}件</strong>
        </a>
	</span>
</nav>