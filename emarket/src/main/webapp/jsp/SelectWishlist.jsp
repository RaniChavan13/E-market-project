<%@page import="com.springboot.emarket.dto.Wishlist"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Select WishList</title>
</head>
<body>
	<h1>Select One Option</h1>

<a href="/customer/wishlist-create/${id}"><button>Create New WishList</button></a><br>

<%List<Wishlist> wishlists=(List<Wishlist>)request.getAttribute("wishlists");
if(wishlists!=null){
	for(Wishlist wishlist:wishlists){
%>
<br>
<a href="/customer/wishlist-add/<%=wishlist.getId()%>/${id}"><button><%=wishlist.getName()%></button></a><br>

<%} }%>




</body>
</html>