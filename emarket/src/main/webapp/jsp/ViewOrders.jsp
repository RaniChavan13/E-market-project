<%@page import="com.springboot.emarket.dto.Item"%>
<%@page import="com.springboot.emarket.dto.ShoppingOrder"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Orders</title>
</head>
<body>
	<%
	List<ShoppingOrder> orders = (List<ShoppingOrder>) request.getAttribute("orders");
	%>
	<table>
		<tr>
			<th>Order Id</th>
			<th>order Date</th>
			<th>Delivery Date</th>
			<th>payment Method</th>
			<th>Items</th>
			<th>Total Amount</th>
		</tr>
		<%
		for (ShoppingOrder order : orders) {
		%>
		<tr>
			<th><%=order.getId()%></th>
			<th><%=order.getDeliveryDate().minusDays(3)%></th>
			<th><%=order.getDeliveryDate()%></th>
			<th><%=order.getPaymentMode()%></th>
			<th>
				<%
				for (Item item : order.getItems()) {
				%><%=item.getName()%> <%
 }
 %>
			</th>
			<th><%=order.getTotalPrice()%></th>
		</tr>
		<%
		}
		%>
	</table>
	<a href="/jsp/CustomerHome.jsp"><button>Back</button></a>
</body>
</html>