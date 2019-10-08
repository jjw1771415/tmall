<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="${contextPath}">
	<img src="img/site/simpleLogo.png" class="simpleLogo" id="simpleLogo">
</a>

<form action="fore_search" method="post">
	<div class="simpleSearchDiv pull-right">
		<input name="keyword" type="text" value="${param.keyword}" placeholder="搜索	天猫	     商品/品牌/店铺">
		<button type="submit" class="searchButton">搜天猫</button>
		<div class="searchBelow">
			<c:forEach items="${cs}" var="c" varStatus="st">
				<c:if test="${st.count >= 5 and st.count <= 7}">
					<span>
						<a href="fore_category?cid=${c.id}">${c.name}</a>
					</span>
					<c:if test="${st.count != 7}">
						<span>|</span>
					</c:if>
				</c:if>
			</c:forEach>
		</div>
	</div>
</form>
<br><br><br>
