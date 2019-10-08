<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="categoryMenu">
	<c:forEach items="${cs}" var="c">
		<div cid="${c.id}" class="eachCategory">
			<span class="glyphicon glyphicon-link"></span>
			<a href="fore_category?cid=${c.id}">${c.name}</a>
		</div>
	</c:forEach>
</div>