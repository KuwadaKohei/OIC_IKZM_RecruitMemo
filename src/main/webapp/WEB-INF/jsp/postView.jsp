<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>就活用メモアプリ-メモ詳細</title>
</head>
<body>
	<!-- 学科・コース -->
	${postDetail.departmentName}
	
	<!-- 学年 -->
	${postDetail.grade}
	
	<!-- もし、求人票がある時 -->
	<c:choose ="${not empty sessionScope.postDetail.recruitmentNo}">
		<!-- 求人No -->
		${postDetail.recruitmentNo}
		<c:otherwise>
			<p>求人なし</p>
		</c:otherwise>
	</c:choose>
	
	<!-- 事業所名 -->
	${postDetail.companyName}
	
	<!-- 試験会場 -->
	${postDetail.venueAddress}
	
	<!-- 受験日 -->
	${postDetail.examDate}
	
	<!-- 応募方法 -->
	${postDetail.submissionMethodName}
	
	<!-- 受験 -->
	<c:forEach items="${sessionScope.postDetail.selectedExams}" var="postDetail" varStatus="status">
		<!-- 受験形式(筆記試験・諸検査・作文・面接・グループディスカッション)-->
		${postDetail.selectedExams.categoryName}
		
		<!-- 受験内容-->
		${postDetail.selectedExams.itemName}	
		
		<!-- 受験詳細-->
		${postDetail.selectedExams.detailText}
	</c:forEach>
	
	<!-- 受験形式(筆記試験・諸検査・作文・面接・グループディスカッション)-->
	${postDetail.selectedExams.categoryName}

	<!-- 受験へのアドバイス☆-->
	${postDetail.adviceText}
	
	<!-- 作成日 -->
	${postDetail.createdAt}
	
	<!-- 最終更新日 -->
	${postDetail.updatedAt}
</body>
</html>