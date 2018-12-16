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
<div class="container">
<div class="main-div">
    <form action="/loginform" method="post">
        <div class="form-group">
            <input type="text" class="form-control" id="id" name="id" placeholder="ID">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" id="pwd" name="pwd" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-primary">Login</button>
    </form>
    </div>
</div>

</body>
</html>