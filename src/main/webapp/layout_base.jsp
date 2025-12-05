<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><c:choose>
		<c:when test="${not empty pageTitle}">
			<c:out value="${pageTitle}" />
		</c:when>
		<c:otherwise>Recruit Memo</c:otherwise>
	</c:choose></title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
	rel="stylesheet">
</head>

<body class="bg-light">
	<div class="d-flex min-vh-100">
		<div
			class="collapse collapse-horizontal show d-lg-flex flex-column flex-shrink-0 bg-dark text-white"
			id="sidebarMenu">
			<div class="d-flex flex-column h-100" style="min-width: 260px;">
				<jsp:include page="/sidebar.jsp" />
			</div>
		</div>
		<div class="flex-grow-1 d-flex flex-column">
			<header class="bg-white border-bottom shadow-sm">
				<div
					class="container-fluid py-3 d-flex align-items-center justify-content-between gap-2">
					<button class="btn btn-outline-secondary d-lg-none" type="button"
						data-bs-toggle="collapse" data-bs-target="#sidebarMenu"
						aria-controls="sidebarMenu" aria-expanded="false"
						aria-label="Toggle navigation">
						<i class="bi bi-list"></i>
					</button>
					<h1 class="h5 mb-0">
						<c:choose>
							<c:when test="${not empty pageTitle}">
								<c:out value="${pageTitle}" />
							</c:when>
							<c:otherwise>Recruit Memo</c:otherwise>
						</c:choose>
					</h1>
					<div class="d-none d-lg-block" style="width: 40px;"></div>
				</div>
			</header>
			<main class="flex-grow-1 container-fluid py-4">
				<c:choose>
					<c:when test="${not empty contentPage}">
						<jsp:include page="${contentPage}" />
					</c:when>
					<c:otherwise>
						<div class="text-center text-muted py-5">
							<i class="bi bi-layout-sidebar-inset display-6"></i>
							<p class="mt-3">コンテンツが設定されていません。</p>
						</div>
					</c:otherwise>
				</c:choose>
			</main>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>

</html>