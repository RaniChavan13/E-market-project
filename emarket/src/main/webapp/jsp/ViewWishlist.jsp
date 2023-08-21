<%@page import="com.springboot.emarket.dto.Wishlist"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Wishlist</title>
</head>
<body>
	<%
	List<Wishlist> wishlists = (List<Wishlist>) request.getAttribute("list");
	%>
	<br>
	<table border="2px">
		<tr>
			<th>WishList Name</th>
			<th>View</th>
			<th>Delete</th>
		</tr>
		<%
		for (Wishlist wishlist : wishlists) {
		%>
		<tr>
			<td><%=wishlist.getName()%></td>
			<td><a
				href="/customer/wishlist/product-view/<%=wishlist.getId()%>"><button>View</button></a></td>
			<td><a
				href="/customer/wishlist-delete/<%=wishlist.getId()%>"><button>Delete</button></a></td>
		</tr>
		<%
		}
		%>
	</table>
	<br>
</body>
</html>