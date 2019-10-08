<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<title>用户管理</title>

<div>
	<div>
		<span class="label label-primary">用户管理</span>
		<br>
		<br>
	</div>
	<div>
		<table class="table table-striped table-bordered table-hover table-condensed">
			<tr>
				<th>ID</th>
				<th>用户名称</th>
			</tr>
			<c:forEach items="${us}" var="u">
				<tr>
					<td>${u.id}</td>
					<td>${u.name}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="pageDiv">
		<%@ include file="../include/admin/adminPage.jsp" %>
		<%@ include file="../include/admin/adminFooter.jsp" %>
	</div>
</div>