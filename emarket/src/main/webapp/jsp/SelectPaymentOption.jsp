<%@page import="com.springboot.emarket.dto.Payment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Select Payment Option</title>
</head>
<body>
	<h1>Select One Payment Option</h1>
	<form action="/customer/placeorder" method="post">
		<%
		List<Payment> list = (List<Payment>) request.getAttribute("list");
		%>
		<%
		for (Payment payment : list) {
		%>
		<input type="radio" name="pid" value="<%=payment.getId()%>"><%=payment.getName()%>
		<br>
		<%
		}
		%>
		<button>Submit</button>
	</form>

</body>
</html>