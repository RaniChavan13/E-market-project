<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enter OTP</title>
</head>
<body>
	<h1>${pass }</h1>
	<h2>${fail }</h2>
	<h1>Hello ${merchant.getName()} Enter OTP ${extra}</h1>
	<form action="/merchant/forgot-otp/${merchant.getEmail()}"
		method="post">
		<input type="text" name="otp" placeholder="Enter Otp">
		<button>Verify</button>
		<button type="reset">cancel</button>
	</form>
	<br>
	<a href="/merchant/resend-forgot-otp/${merchant.getEmail()}">Click
		here to resend otp</a>
</body>
</html>