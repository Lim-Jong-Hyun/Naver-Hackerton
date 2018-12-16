<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript" src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
</script>
<c:forEach var="u" items="${list }" varStatus="status">
<img id="i" src="${u }">
<c:if test="${status.count%3 eq 0 }">
<br>
</c:if>
</c:forEach>
</body>
</html>