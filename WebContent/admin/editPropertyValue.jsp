<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>

<title>编辑产品属性值</title>
<script>
	$(function() {
		$("input.pvValue").keyup(function() {
			var value = $(this).val();
			var page = "admin_product_updatePropertyUpdate";
			var pvid = $(this).attr("pvid");
			var parentSpan = $(this).parent("span");
			
			$.post(page,{"value":value,"pvid":pvid},function(result){
				if(result == "success"){
					parentSpan.css("border","1px solid green");
				}else{
					parentSpan.css("border","1px solid red");
				}
			});
		});
	});
</script>
<div>
	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li><a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
		<li class="active">${p.name}</li>
		<li class="active">编辑产品属性</li>
	</ol>
	<div class="editPVDiv">
		<c:forEach items="${pvs}" var="pv">
			<div class="eachPV">
				<span class="pvName">${pv.property.name}</span>
				<span class="pvValue"><input type="text" class="pvValue" pvid="${pv.id}" value="${pv.value}" ></span>
			</div>
		</c:forEach>
		<div style="clear:both"></div>
	</div>
</div>