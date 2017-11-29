<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>${board}</h1>
<table>
	<tr>
		<td>num</td>
		<td>title</td>
		<td>contents</td>
		<td>writer</td>
		<td>reg_date</td>
		<td>hit</td>
	</tr>
	
	<c:forEach items="${list}" var ="dto">
		<tr>
			<td>${dto.num}</td>
			<td>${dto.title}</td>
			<td>${dto.contents}</td>
			<td>${dto.writer}</td>
			<td>${dto.reg_date}</td>
			<td>${dto.hit}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>