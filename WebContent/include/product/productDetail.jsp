<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
	$(function() {
		$("div.reviewDivlistReviews").hide();
		$("div.productDetail").show();
		$("a.productDeatilTopReviewLink").click(function() {
			$("div.reviewDivlistReviews").show();
			$("div.productDetail").hide();
			$(this).addClass("selected");
			$("a.productDetailTopPartSelectedLink").removeClass("selected");
		});
		$("a.productDetailTopPartSelectedLink").click(function() {
			$("div.reviewDivlistReviews").hide();
			$("div.productDetail").show();
			$(this).addClass("selected");
			$("a.productDeatilTopReviewLink").removeClass("selected");
		});
	});
</script>
<div class="productDetailDiv">
	<div class="productDetailTopPart">
		<a href="#nowhere" class="productDetailTopPartSelectedLink selected">商品详情</a>
		<a href="#nowhere" class="productDeatilTopReviewLink">累计评价 <span class="productDetailTopReviewLinkNumber">${p.reviewCount}</span></a>
	</div>
    <div class="reviewDivlistReviews">
        <c:forEach items="${rs}" var="r">
            <div class="reviewDivlistReviewsEach">
                <div class="reviewDate"><fmt:formatDate value="${r.createDate}" pattern="yyyy-MM-dd"/></div>
                <div class="reviewContent">${r.content}</div>
                <div class="reviewUserInfo pull-right">${r.user.anonymousName}<span class="reviewUserInfoAnonymous">(匿名)</span></div>
            </div>
        </c:forEach>
    </div>
	<div class="productDetail">
		<div class="productParamterPart">
			<div class="productParamter">产品参数：</div>
			
			<div class="productParamterList">
				<c:forEach items="${ptvs}" var="ptv">
					<span>${ptv.property.name}：   ${fn:substring(ptv.value,0,10)}</span>
				</c:forEach>
			</div>
			<div style="clear: both;"></div>
		</div> 
		
		<div class="productDetailImagePart">
			<c:forEach items="${p.productDetailImages}" var="pi">
				<img src="img/productDetail/${pi.id}.jpg">
			</c:forEach>
		</div>
	</div>
</div>