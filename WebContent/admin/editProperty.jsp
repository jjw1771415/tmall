<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>
<script>
	$(function() {
		$("#editForm").submit(function() {
			if(!checkEmpty("name","属性名称")){
				return false;
			}
			return true;
		});
	});
</script>
<div class="panel panel-primary editDiv">
		<div class="panel-heading">编辑属性</div>
		<div class="panel-body">
			<form id="editForm" action="admin_property_update" method="post" >	
				<table class="editTable">
					<tr>
						<td>属性名称</td>
						<td><input id="name" name="name" type="text" class="form-control" value="${pt.name}"></td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
						<input type="hidden" name="id" value="${pt.id}">
						<input type="hidden" name="cid" value="${pt.category.id}">
						<button type="submit" class="btn btn-success">提 交</button>
						</td>
					</tr>
				</table>					
			</form>
		</div>
	</div>
	<%@ include file="../include/admin/adminFooter.jsp" %>