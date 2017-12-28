<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<!-- <a href="./notice/noticeList?name=notice">Go Notice</a> -->
<a href="./notice/noticeList">Go Notice</a>
<a href="./qna/qnaList">qna</a>
<!-- <a href="./member/memberList">member</a> -->
</body>
</html>
