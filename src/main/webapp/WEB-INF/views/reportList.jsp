<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Recruit Memo | 投稿一覧</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
	rel="stylesheet">
</head>

<body class="bg-body-tertiary">
	<header class="bg-white border-bottom shadow-sm">
		<div
			class="container-fluid py-3 d-flex justify-content-between align-items-center">
			<div class="d-flex align-items-center gap-2">
				<button class="btn btn-outline-secondary d-lg-none" type="button"
					data-bs-toggle="offcanvas" data-bs-target="#layoutSidebar"
					aria-controls="layoutSidebar" aria-label="Toggle sidebar">
					<i class="bi bi-list"></i>
				</button>
				<span class="fs-5 fw-semibold">Recruit Memo</span>
			</div>
			<div class="text-muted small">投稿一覧</div>
		</div>
	</header>

	<div class="container-fluid">
		<div class="row flex-nowrap">
			<aside id="layoutSidebar"
				class="col-12 col-lg-3 col-xl-2 p-0 bg-white border-end min-vh-100">
				<jsp:include page="/sidebar.jsp" />
			</aside>
			<main class="col-12 col-lg-9 col-xl-10 py-4 px-4">
				<section class="bg-white rounded-3 shadow-sm p-4">
					<div
						class="d-flex flex-wrap gap-3 justify-content-between align-items-center mb-3">
						<div>
							<h1 class="h4 mb-1">投稿一覧</h1>
							<p class="text-muted mb-0">最新の投稿を確認できます。</p>
						</div>
						<a class="btn btn-primary"
							href="${pageContext.request.contextPath}/ReportNew"> <i
							class="bi bi-file-earmark-plus me-1"></i>新規投稿
						</a>
					</div>

					<form method="get"
						action="${pageContext.request.contextPath}/ReportList"
						class="row g-2 mb-4">
						<div class="col-sm-9 col-md-10">
							<input type="text" name="searchWord" value="${param.searchWord}"
								class="form-control" placeholder="企業名やキーワードで検索">
						</div>
						<div class="col-sm-3 col-md-2 d-grid">
							<button type="submit" class="btn btn-outline-secondary">
								<i class="bi bi-search me-1"></i>検索
							</button>
						</div>
					</form>

					<c:choose>
						<c:when test="${postList == null || empty postList.reports}">
							<div class="alert alert-info mb-0">
								表示できる投稿がありません。新規投稿を作成するか、検索条件を変更してください。</div>
						</c:when>
						<c:otherwise>
							<div class="list-group list-group-flush">
								<c:forEach var="report" items="${postList.reports}">
									<a class="list-group-item list-group-item-action py-3"
										href="${pageContext.request.contextPath}/ReportView?postId=${report.postId}">
										<div class="d-flex w-100 justify-content-between">
											<div>
												<h2 class="h6 mb-1">${report.companyName}</h2>
												<p class="mb-1 text-muted small">
													求人票番号: ${report.recruitmentNo} <span class="mx-1">|</span>
													${report.departmentCourseName}
												</p>
											</div>
											<small class="text-muted">試験日: ${report.examDate}</small>
										</div>
										<p class="mb-0 text-muted small">最終更新: ${report.updatedAt}
										</p>
									</a>
								</c:forEach>
							</div>
						</c:otherwise>
					</c:choose>
				</section>
			</main>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>

</html>