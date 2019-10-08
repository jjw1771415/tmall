<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	$(function() {
		<c:if test="${!empty msg}">
			$("span.errorMessage").html("${msg}");
			$("div.loginErrorMessageDiv").show();
		</c:if>
		
		$("form.loginForm").submit(function() {
			if($("#name").val().length == 0 || $("#password").val().length == 0){
				$("span.errorMessage").html("请输入账号密码");
				$("div.loginErrorMessageDiv").show();
				return false;
			}
			return ture;
		});
		
		$("form.loginForm input").keyup(function() {
			$("div.loginErrorMessageDiv").hide();
		});
		var left = window.innerWidth/2+162;
		$("div.loginSmallDiv").css("left",left);
	});
</script>
<div class="loginDiv">
	<div class="simpleLogo">
		<a href="http://10.158.56.202/tmall"><img src="img/site/simpleLogo.png"></a>
	</div>
	<img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">
	<form class="loginForm" action="fore_login" method="post">
		<div id="loginSmallDiv" class="loginSmallDiv">
			<div class="loginErrorMessageDiv">
				<div class="alert alert-danger" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
					<span class="errorMessage"></span>
				</div>
			</div>
			
			<div class="login_acount_text">账户登录</div>
			<div class="loginInput">
				<span class="loginInputIcon">
					<span class="glyphicon glyphicon-user" ></span>
				</span>
					<input id="name" name="name" placeholder="请输入会员名">
			</div>
			
			<div class="loginInput">
				<span class="loginInputIcon">
					<span class="glyphicon glyphicon-lock"></span>
				</span>
					<input id="password" name="password" type="password" placeholder="请输入你的密码">
			</div>
			
			<span class="text-danger">不要输入真实的天猫账户密码</span>
			<br><br>
			
			<div>
				<a class="notImplementLink" href="#nowhere">忘记登录密码</a>
				<a href="register.jsp" class="pull-right">免费注册</a>
			</div>
			
			<div style="margin-top: 20px">
				<button class="btn btn-block redButton" type="submit">登录</button>
			</div>
		</div>
	</form>
</div>