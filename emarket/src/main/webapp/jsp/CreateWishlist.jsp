<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Wishlist</title>
</head>
<body>
	<form action="/customer/wishlist-create/${id}" method="post">
		Enter WishList Name:<input type="text" name="name">
		<button>Create</button>
	</form>
</body>
</html>