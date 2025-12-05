package ikzm_jhm_filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_dto.User;
import ikzm_jhm_model.Error;

/**
 * 管理者権限を確認するフィルター。
 * 管理者でないユーザーからのアクセスを拒否する。
 */
@WebFilter({ "/User/Suspend" })
public class AdminFilter implements Filter {

	/**
	 * 管理者セッションを確認し、未管理者なら共通エラー画面へ遷移させる。
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();

		boolean isAdmin = false;
		if (session != null) {
			User user = (User) session.getAttribute("loginUser");
			if (user != null && user.isAdmin()) {
				isAdmin = true;
			}
		}

		if (isAdmin) {
			// 管理者なら通過
			chain.doFilter(request, response);
		} else {
			forwardToError(httpRequest, httpResponse);
		}
	}

	/**
	 * 権限エラー画面へフォワードする。
	 */
	private void forwardToError(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("errorCode", "AUTH-1011");
		request.setAttribute("error", Error.getMessage("AUTH-1011"));
		request.setAttribute("backUrl", request.getContextPath() + "/ReportList");
		request.setAttribute("backLabel", "メインメニューへ戻る");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		dispatcher.forward(request, response);
	}
}