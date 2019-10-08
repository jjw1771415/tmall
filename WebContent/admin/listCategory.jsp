<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>
<script>
$(function(){
    $("#addForm").submit(function(){
        if(!checkEmpty("name","分类名称")){
            return false;
    	}
        if(!checkEmpty("categoryPic","分类图片")){
            return false;
		}
        return true;
    });
});
 
</script>
<div >
	<div>
		<span class="label label-primary">分类管理</span>
		<br>
		<br>
	</div>
	
	<div>
		<table class="table table-striped table-bordered table-hover table-condensed">
			<tr>
				<th>ID</th>
				<th>图片</th>
				<th>分类名称</th>
				<th width="80px">属性管理</th>
				<th width="80px">产品管理</th>
				<th width="80px">编辑</th>
				<th width="80px">删除</th>
			</tr>
			<c:forEach items="${cs}" var="c">
				<tr>
					<td>${c.id}</td>
					<td><img src="img/category/${c.id}.jpg" height="40px"></td>
					<td>${c.name}</td>
					<td><a href="admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>
					<td><a href="admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>
					<td><a href="admin_category_edit?id=${c.id}"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a></td>
					<td><a deleteLink="true" href="admin_category_delete?id=${c.id}"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="pageDiv">
		<%@ include file="../include/admin/adminPage.jsp" %>
	</div>
	
	<div class="panel panel-primary addDiv">
      <div class="panel-heading">新增分类</div>
      <div class="panel-body">
            <form method="post" id="addForm" action="admin_category_add" enctype="multipart/form-data">
                <table class="addTable">
                    <tr>
                        <td>分类名称</td>
                        <td><input  id="name" name="name" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>分类图片</td>
                        <td>
                            <input id="categoryPic" accept="image/*" type="file" name="filepath" />
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
      </div>
    </div>
	
	<div>
		<%@ include file="../include/admin/adminFooter.jsp" %>
	</div>
</div>
	