<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>
<script>
	$(function() {
		$("#addForm").submit(function() {
			if(!checkEmpty("name","属性名称")){
				return false;
			}
			return true;
		});
		$("a").click(function(){
			var deleteProperty = $(this).attr("deleteProperty");
			console.log(deleteProperty);
			if("true"==deleteProperty){
				var confirmDelete = confirm("如果继续执行此操作，您当前分类下所有产品的该条属性对应的属性值将被删除。是否继续？");
				if(confirmDelete)
					return true;
				return false;
			}
		});
	});
</script>
<div>
	<ol class="breadcrumb">
	  <li><a href="admin_category_list">所有分类</a></li>
	  <li><a href="admin_property_list?cid=${c.id}">${c.name}</a></li>
	  <li class="active">属性管理</li>
	</ol>
	
	<div>
		<table class="table table-striped table-bordered table-hover table-condensed">
			<tr>
				<th>ID</th>
				<th>属性名称</th>
				<th>编辑</th>
				<th>删除</th>
			</tr>
			<c:forEach items="${pts}" var="pt">
				<tr>
					<td>${pt.id}</td>
					<td>${pt.name}</td>
					<td><a href="admin_property_edit?id=${pt.id}"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a></td>
					<td><a deleteProperty="true" href="admin_property_delete?id=${pt.id}"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="pageDiv">
	<%@ include file="../include/admin/adminPage.jsp" %>
	</div>
	
	<div class="panel panel-primary addDiv">
		<div class="panel-heading">新增属性</div>
		<div class="panel-body">
			<form id="addForm" action="admin_property_add" method="post" >	
				<table class="addTable">
					<tr>
						<td>属性名称</td>
						<td><input id="name" name="name"  type="text" class="form-control"></td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
						<input type="hidden" name="cid" value="${c.id}">
						<button type="submit" class="btn btn-success">提 交</button>
						</td>
					</tr>
				</table>					
			</form>
		</div>
	</div>
	<%@ include file="../include/admin/adminFooter.jsp" %>
</div>