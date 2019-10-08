<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	$(function() {
		<c:if test="${!empty msg}">
    	$("span.errorMessage").html("${msg}");
    	$("div.registerErrorMessageDiv").css("visibility","visible");
    </c:if>
	    $(".registerForm").submit(function() {
			if($("#name").val().length == 0){
				$("span.errorMessage").html("请输入用户名");
				$("div.registerErrorMessageDiv").css("visibility","visible");
				return false;
			}
			if($("#password").val().length == 0){
				$("span.errorMessage").html("请输入密码");
				$("div.registerErrorMessageDiv").css("visibility","visible");
				return false;
			}
			if($("#repeatpassword").val().length == 0){
				$("span.errorMessage").html("请再次输出密码");
				$("div.registerErrorMessageDiv").css("visibility","visible");
				return false;
			}
			if($("#password").val() != $("#repeatpassword").val()){
				$("span.errorMessage").html("重复密码不一致");
				$("div.registerErrorMessageDiv").css("visibility","visible");
				return false;
			}
			return true;
		});
	});
</script>

<form method="post" action="fore_register" class="registerForm">
<div class="registerDiv">
	<div class="reqisterErrorMessageDiv">
		<div class="alert alert-danger" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
			<span class="errorMessage"></span>
		</div>
	</div>
	
	<table class="registerTable" align="center">
		<tr>
			<td class="registerTip registerTableLeftTD">设置会员名</td>
			<td></td>
		</tr>	
		<tr>
			<td class="registerTableLeftTD">登录名</td>
			<td class="registerTableRightTD"><input id="name" name="name" placeholder="会员名一经设置，无法修改"></td>
		</tr>
		<tr>
			<td class="registerTip registerTableLeftTD">设置登录密码</td>
			<td class="registerTableRightTD">登录时验证，保护账号安全</td>
		</tr>
		<tr>
			<td class="registerTableLeftTD">登录密码</td>
			<td class="registerTableRightTD"><input id="password" name="password"type="password" placeholder="请设置你的登录密码"></td>
		</tr>
		<tr>
			<td class="registerTableLeftTD">确认密码</td>
			<td class="registerTableRightTD"><input id="repeatpassword" type="password" placeholder="请再次输入你的密码"></td>
		</tr>
		<tr>
			<td colspan="2" class="registerButtonTD">
				<a href="registerSuccess.jsp"><button>提	         交</button></a>
			</td>
		</tr>
	</table>
</div>
</form>