<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Reset Password</title>
</head>
<body>
	<h1>Hello ${customer.getName()} your OTP verification is success</h1>
	<h2>Enter New Password</h2>
	<form action="/customer/reset-password" method="post">
		<input type="hidden" name="email" value="${customer.getEmail()}"
			required="required"> <br> <input type="password"
			name="password">
		<button>Reset Password</button>
		<button type="reset">Cancel</button>
	</form>
</body>
</html>