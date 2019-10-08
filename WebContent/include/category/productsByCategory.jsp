<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${empty param.categorycount}">
	<c:set var="categoryCount" scope="page" value="100"></c:set>
</c:if>

<c:if test="${!empty param.categoryCount}">
	<c:set var="categoryCount" scope="page" value="${param.categoryCount}"></c:set>
</c:if>

<div class="categoryProducts">
	<c:forEach items="${c.products}" var="p" varStatus="stc">
		<c:if test="${stc.count<=categoryCount}">
			<div class="productUnit" price="${p.promotePrice}">
				<div class="productUnitFrame">
					<a href="fore_product?pid=${p.id}" >
						<img class="productImage" src="img/productSingle_middle/${p.firstProductImage.id}.jpg">
					</a>
					<span class="productPrice">¥<fmt:formatNumber value="${p.promotePrice}" minFractionDigits="2" type="number"></fmt:formatNumber></span>
					<a class="productLink" href="fore_product?pid=${p.id}">${fn:substring(p.name,0,50)}</a>
					
					<a class="tmallLink" href="fore_product?pid=${p.id}">天猫专卖</a>
					
					<div class="show1 productInfo">
						<span class="monthDeal">月成交
							<span class="productDealNumber">
							${p.saleCount}笔
							</span>
						</span>
						
						<span class="productReview">评价
							<span class="productReviewNumber">
							${p.reviewCount}
							</span>
						</span>
						
						<span class="wangwang">
							<a class="wangwanglink" href="#nowhere">
							<img src="img/site/wangwang.png">
							</a>
						</span>
					</div>
				</div>
			</div>
		</c:if>
	</c:forEach>
	<div style="clear: both;"></div>
</div>