<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<%@ include file="header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		var num = ${answerCount};
		var arr = new Array();
		var cnt=0;
		var flag= true;
		
		var IMAGEURL="http://dev.apis.naver.com/panoramaCaptcha/panorama/thumbnail/";
		
		$('#btn').click(function(){
			if($("input[name=id]").val()=="" ||$("input[name=pwd]").val()=="" ||$("input[name=name]").val()=="" || $("input[name=email]").val()=="" ){
				alert("Fill All Blank");
				return;
			}else if(flag){
				alert("You Must Check NCaptcha");
				return;
			}
			alert("Complete");
			$("#f").submit();
		});
		
// 		$("img[name=pic]").mouseover(function(){
// 			$(this).attr("style","cursor:pointer");
// 		});
		
		$("img[name=pic]").click(function(){
			
			var bzstNo = $(this).attr("src").split("?")[0].split(IMAGEURL)[1].split("/")[0];
			var panoTypeCd = $(this).attr("src").split("?")[0].split(IMAGEURL)[1].split("/")[1];
			
			if(cnt+1>num){
				alert("You Must Choose Only "+num +" IMAGES");

				return;
			}
			
			$(this).attr("style","width:120px; height:120px");

			var info = new Object;
			info.bzstNo = bzstNo;
			info.panTypeCd = panoTypeCd;
			arr.push(info);
			var json  =JSON.stringify(arr);

			if(cnt+1==num){
				
			 $.ajax({
				type:"POST",
				url:"/check",
				data: json,
				contentType:"application/json",
				success: function(responseData){
					if(responseData==true){
						alert("NCaptcha Clear!!");
					flag= false;
					}else{
						alert("NCaptcha Failed!!");
					}
				}
			 });
			}
			cnt++;
	});
});
</script>


</head>
<body>
	<div>
		<br>
		<div class="container">
			<form action="/add" id="f" name="f" method="post">
				<div class="form-group">
					<label for="ID">ID</label> <input type="text" class="form-control"
						name="id" placeholder="Enter ID">
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">Password</label> <input
						type="password" class="form-control" name="pwd"
						placeholder="Enter Password">
				</div>
				<div class="form-group">
					<label for="Name">Name</label> <input type="text"
						class="form-control" name="name" placeholder="Enter Name">
				</div>
				<div class="form-group">
					<label for="exampleInputEmail1">Email address</label> <input
						type="email" class="form-control" name="email"
						aria-describedby="emailHelp" placeholder="Enter email">
				</div>
				<tbody>

					<div>
						<p class="text-primary">${answerImg}</p>
					</div>

					<div>
						<c:forEach var="u" items="${Images }" varStatus="status">
							<c:if test="${status.index%4 eq 0 }">
								<br>
								<br>
							</c:if>
							<img id="i_${status.index }" src="${u }" class="rounded"
								name="pic" style=""  data-toggle="0">
						</c:forEach>
					</div>
					
					<br>
					<button type="button" id="btn" class="btn btn-primary">Submit</button>
			</form>
		</div>
</body>
</html>