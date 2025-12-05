<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>就活用メモアプリ-自分のメモ</title>
</head>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope.postList}">
			<c:forEach items="${sessionScope.postList}" var="postList" varStatus="status">
				<a href="ReportView?postId=${postList.postId}">
					<!-- もし、求人Noがある時 -->
					<c:when test="${not empty sessionScope.postList.recruitmentNo}">
						<!-- 求人No -->
						${postList.recruitmentNo}
					</c:when>
					
					<!-- 事業所名 -->
					${postList.companyName}
					
					<!-- 作成日 -->
					${postList.createdAt}
					
					<!-- 最終更新日 -->
					${postList.updatedAt}
				</a>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<p>メモがありません</p>
		</c:otherwise>
	</c:choose>
</body>
</html>