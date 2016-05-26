<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap core CSS -->
<link href="/static/bootstrap/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="/static/bootstrap/bootstrap-theme.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="/static/bootstrap/theme.css" rel="stylesheet">
<title>验证结果...</title>
</head>
<body role="document">
	<div class="container theme-showcase" role="main">
		<div class="row">
			<div class="col-sm-6">
				<div class="panel panel-warning">
					<div class="panel-body">
						<p>
							<c:choose>
								<c:when test="${verify}">
									验证成功！<span class="label label-warning" id="time">5</span>秒后页面将自动跳转...
								</c:when>
								<c:otherwise>
									验证邮箱错误，请稍后重新验证！<span class="label label-warning" id="time">5</span>秒后页面将自动跳转...
								</c:otherwise>
							</c:choose>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<!-- Bootstrap core JavaScript -->
<script src="/static/bootstrap/jquery.min.js"></script>
<script src="/static/bootstrap/bootstrap.min.js"></script>
<script src="/static/bootstrap/docs.min.js"></script>
<script type="text/javascript">
	$(function() {
		jump();
	});
	var delay = 5;
	function jump() {
		$('#time').html(delay);
		if (delay == 0) {
			location.href = "${callback}";
		} else {
			delay--;
			window.setTimeout("jump()", 1000);
		}
	}
</script>
</html>