<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var ch=false;
	$(function(){
		$("#id").change(function(){
			ch=false;
		});
		
		
		$("#idCheck").click(function(){
			ch=true;
			var id=$("#id").val();
			window.open("./memberIdCheck.jsp?id="+id, "", "width=400, height=300, left=400, top=200");
		});
		
		$("#btn").click(function(){
			if(!ch){
				alert("ID 중복확인 해")
			}
		});
	});


</script>
</head>
<body>

</body>
</html>