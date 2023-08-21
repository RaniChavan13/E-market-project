<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>payment-Option</title>
</head>
<body>
	${fail}
	<form action="/admin/payment-add" method="post">
		Enter payment Method Name: <input type="text" name="name"><br>
		<button>Add</button>
		<button type="reset">Cancel</button>
	</form>
	<br>
	<a href="/jsp/AdminHome.jsp"><button>Back</button></a>
</body>
</html>