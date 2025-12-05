<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>エラーが発生しました</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>

<body>
	<main>
		<h1>エラーが発生しました</h1>
		<p>${error}</p>
		<c:if test="${not empty errorCode}">
			<p>エラーコード: ${errorCode}</p>
		</c:if>
		<c:choose>
			<c:when test="${not empty backUrl}">
				<a href="${backUrl}">${backLabel}</a>
			</c:when>
			<c:otherwise>
				<a href="${pageContext.request.contextPath}/ReportList">トップへ戻る</a>
			</c:otherwise>
		</c:choose>
	</main>
</body>

</html>