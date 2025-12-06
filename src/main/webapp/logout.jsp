<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="jakarta.tags.core" %>
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<!DOCTYPE html>
		<html lang="ja">

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<title>Recruit Memo | Logout</title>
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
				integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
				crossorigin="anonymous">
			<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
		</head>

		<body class="bg-light">
			<div class="container min-vh-100 d-flex flex-column justify-content-center py-5">
				<div class="text-center mx-auto" style="max-width: 420px;">
					<div class="mb-4">
						<h1 class="h3 fw-semibold text-dark">ログアウトしました</h1>
						<p class="text-muted">再度利用するにはログインしてください。</p>
					</div>
					<div class="card border-0 shadow-sm">
						<div class="card-body py-5">
							<form method="get" action="${contextPath}/">
								<button type="submit" class="btn btn-primary btn-lg px-5">
									<i class="bi bi-box-arrow-in-right me-2"></i>ログイン画面へ戻る
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>

			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
				integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
				crossorigin="anonymous"></script>
		</body>

		</html>