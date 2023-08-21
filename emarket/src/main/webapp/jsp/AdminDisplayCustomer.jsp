<%@page import="com.springboot.emarket.dto.Customer"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Display Customer</title>
</head>
<body>
	<%
	List<Customer> customers = (List<Customer>) request.getAttribute("customers");
	%>
	<h1>Customer Details</h1>
	<table border="2px solid black">
		<tr>
			<th>Email</th>
			<th>Name</th>
			<th>Gender</th>
			<th>Date of Birth</th>
			<th>Address</th>
		</tr>
		<%
		for (Customer customer : customers) {
		%>
		<tr>
			<th><%=customer.getEmail()%></th>
			<th><%=customer.getName()%></th>
			<th><%=customer.getGender()%></th>
			<th><%=customer.getDob()%></th>
		</tr>
		<%
		}
		%>
	</table>
	<br>
	<br>
	<a href="/jsp/AdminHome.jsp"><button>Back</button></a>
</body>
</html>