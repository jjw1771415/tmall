<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:forEach items="${cs}" var="c">
	<div cid="${c.id}" class="productsAsideCategorys">
		<c:forEach items="${c.productsByRow}" var="ps">
			<div class="row show1">
				<c:forEach items="${ps}" var="p">
					<c:if test="${!empty p.subTitle}">
						<a href="fore_product?pid=${p.id}">
							<c:forEach items="${fn:split(p.subTitle,' ')}" var="title" varStatus="st">
								<c:if test="${st.index==0}">
									${title}
								</c:if>
							</c:forEach>
						</a>
					</c:if>
				</c:forEach>
				<div class="seperator"></div>
			</div>
		</c:forEach>
	</div>
</c:forEach>