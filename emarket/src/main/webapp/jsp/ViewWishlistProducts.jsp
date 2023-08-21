<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="com.springboot.emarket.dto.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Wishlist Products</title>
</head>
<body>
	<%
	List<Product> products = (List<Product>) request.getAttribute("list");
	%>
	<h1>product Details</h1>
	<table border="2px solid black">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Image</th>
			<th>Price</th>
			<th>Stock</th>
			<th>Description</th>
			<th>Add to Cart</th>
			<th>Remove</th>
		</tr>

		<%
		for (Product product : products) {
		%>
		<tr>
			<th><%=product.getId()%></th>
			<th><%=product.getName()%></th>
			<th>
				<%
				String base64 = Base64.encodeBase64String(product.getImage());
				%> <img src="data:image/jpeg;base64,<%=base64%>" alt="Picture"
				style="width: 100px; height: auto;">
			</th>
			<th><%=product.getPrice()%></th>
			<th><%=product.getStock()%></th>
			<th><%=product.getDescription()%></th>
			<th><a href="/customer/cart-add/<%=product.getId()%>"><button>Add</button></a></th>
			<th><a
				href="/customer/wishlist-remove/${id}/<%=product.getId()%>"><button>Remove</button></a></th>
		</tr>
		<%
		}
		%>
	</table>
	<br>
	<br>
	<a href="/jsp/CustomerHome.jsp"><Button>Back</Button> </a>
</body>
</html>