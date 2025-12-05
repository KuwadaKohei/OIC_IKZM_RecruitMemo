<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>就活用メモアプリ-メモ作成</title>
</head>
<body>
	<form method="post" action="ReportNew?action=confirm" id="postForm">
		<!-- ポストID(nullにならないように0を指定) -->
		<input type="hidden" name="postId" value=0>
		
		<!-- 事業所名 -->
		<input type="text" name="companyName">
		
		<!-- 求人票 -->
		<input type="text" name="recruitmentNo">
		
		<!-- 試験会場 -->
		<input type="text" name="venueAddress">
		
		<!-- 受験日(****/**/**) -->
		<input type="text" name="examDate">
		
		<!-- 応募方法 -->
		<input type="radio" name="methodId" value=0>学校斡旋
		<input type="radio" name="methodId" value=1>自己開拓
		<input type="radio" name="methodId" value=2>縁故
		
		<!-- 受験形式 -->
		<p id="write" onclick="openModal(write)">筆記試験</p><br>
		<p id="check" onclick="openModal(check)">諸検査</p><br>
		<p id="essay" onclick="openModal(essay)">作文</p><br>
		<p id="interview" onclick="openModal(interview)">面接</p><br>
		<p id="discussion" onclick="openModal(discussion)">グループディスカッション</p>
		
		<!-- 受験のアドバイス☆ -->
		<input type="text" name="adviceText">
	</form>
	
	<!-- 筆記試験のモーダル -->
	
	<!-- 諸検査のモーダル -->
	
	<!-- 作文のモーダル -->
	
	<!-- 面接のモーダル -->
	
	<!-- グループディスカッションのモーダル -->
	
</body>
</html>