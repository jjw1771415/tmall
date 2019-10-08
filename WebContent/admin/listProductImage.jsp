<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<title>产品图片管理</title>

<script>
$(function(){
    $(".addFormSingle").submit(function(){
        if(checkEmpty("filePathSignle","图片文件")){
            $("#filepathSingle").value("");
            return true;
        }
        return false;
    });
    $(".addFormDetail").submit(function(){
        if(checkEmpty("filePathDetail","图片文件"))
            return true;
        return false;
    });
});
 
</script>

<div>
	<ol class="breadcrumb">
	  <li><a href="admin_category_list">所有分类</a></li>
	  <li><a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
	  <li class="active">${p.name}</li>
	  <li class="active">产品图片管理</li>
	</ol>
			<table class="addPicture" align="center">
				<tr>
					<td class="addPictureTableTD">
						<div class="panel panel-warning addDiv">
			      			<div class="panel-heading">新增产品<b class="text-primary">单个</b>图片</div>
			      				<div class="panel-body">
					            	<form method="post" class="addFormSingle" action="admin_productImage_add" enctype="multipart/form-data">
					                	<table class="addTable">
											<tr>
												<td>请选择本地图片   尺寸400×400最佳</td>
											</tr>
											<tr>
												<td><input type="file" name="filePath" id="filePathSignle"></td>
											</tr>
						                    <tr class="submitTR">
						                        <td colspan="2" align="center">
						                        	<input name="type" value="type_single" type="hidden">
						                            <input name="pid" value="${p.id}" type="hidden">
						                            <button type="submit" class="btn btn-success">提 交</button>
						                        </td>
						                    </tr>
					                	</table>
					           	 	</form>
			      				</div>
			    			</div>
			    		</td>
			    		<td class="addPictureTableTD">
							<div class="panel panel-warning addDiv">
			      			<div class="panel-heading">新增产品<b class="text-primary">详情</b>图片</div>
			      				<div class="panel-body">
					            	<form method="post" class="addFormDetail" action="admin_productImage_add" enctype="multipart/form-data">
					                	<table class="addTable">
											<tr>
												<td>请选择本地图片   宽度790为最佳</td>
											</tr>
											<tr>
												<td><input type="file" name="filePath" id="filePathDetail"></td>
											</tr>
						                    <tr class="submitTR">
						                        <td colspan="2" align="center">
						                        	<input name="type" value="type_detail" type="hidden">
						                            <input name="pid" value="${p.id}" type="hidden">
						                            <button type="submit" class="btn btn-success">提 交</button>
						                        </td>
						                    </tr>
					                	</table>
					           	 	</form>
			      				</div>
			    			</div>
			    		</td>
			    	</tr>
			    	<tr>
			    		<td class="addPictureTableTD">
							<table class="table table-striped table-bordered table-hover table-condensed">
									<tr>
										<th>ID</th>
										<th>产品单个缩略图</th>
										<th>删除</th>
									</tr>
									<c:forEach items="${pis_single}" var="pi_single">
										<tr>
											<td>${pi_single.id}</td>
											<td>
												<a title="查看原图" href="img/productSingle/${pi_single.id}.jpg">
													<img height="50px" src="img/productSingle/${pi_single.id}.jpg">
												</a>
											</td>
											<td><a deleteLink="true" href="admin_productImage_delete?id=${pi_single.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
										</tr>
									</c:forEach>
							</table>
						</td>
						<td class="addPictureTableTD">
							<table class="table table-striped table-bordered table-hover table-condensed">
									<tr>
										<th>ID</th>
										<th>产品详情缩略图</th>
										<th>删除</th>
									</tr>
									<c:forEach items="${pis_detail}" var="pi_detail">
										<tr>
											<td>${pi_detail.id}</td>
											<td>
												<a title="查看原图" href="img/productDetail/${pi_detail.id}.jpg">
													<img height="50px" src="img/productDetail/${pi_detail.id}.jpg">
												</a>
											</td>
											<td><a deleteLink="true" href="admin_productImage_delete?id=${pi_detail.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
				</table>
</div>