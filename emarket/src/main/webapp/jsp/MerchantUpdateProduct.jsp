<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Merchant Update Product</title>
</head>
<body>
<form action="/merchant/product-update" method="post">
Id:<input type="text" name="id" value="${product.getId() }" readonly="readonly"><br>
Name:<input type="text" name="name" value="${product.getName() }"><br>
Price:<input type="text" name="price" value="${product.getPrice() }"><br>
Stock:<input type="text" name="stock" value="${product.getStock() }"><br>
Description:<textarea rows="5" name="description" cols="30">${product.getDescription()}</textarea><br>
<button>update</button>
		<button type="reset">Reset</button>
	</form>
</body>
</html>