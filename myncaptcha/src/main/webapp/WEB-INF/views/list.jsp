<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<%@ include file="header.jsp" %>
</head>
<body>
<br>
<div class = "container">
	<table class="table table-condensed">
		<thread>
			<tr>
				<th>NUM</th>
				<th>ID</th>
				<th>NAME</th>
				<th>E-MAIL</th>
			</tr>
		</thread>
		<tbody>
			<c:forEach var="m" items="${ list}" varStatus="status">
			<tr>
				<td>${m.num }</td>
				<td>${m.id }</td>
				<td>${m.name }</td>
				<td>${m.email }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

</div>

</body>
</html>