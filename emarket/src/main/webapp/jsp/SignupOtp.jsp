<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enter OTP</title>
</head>
<body>
	<h1>${pass}</h1>
	<h1>${fail}</h1>
	<h1>Hello ${merchant.getName() }Enter OTP ${extra}</h1>
	<form action="/merchant/verify-otp/${merchant.getEmail() }"
		method="post">
		<input type="text" name="otp" placeholder="Enter OTP">
		<button>Verify</button>
		<button type="reset">Cancel</button>
	</form>
	<br>
	<a href="/merchant/resend-otp/${merchant.getEmail()}">click here to
		Resend OTP</a>
</body>
</html>