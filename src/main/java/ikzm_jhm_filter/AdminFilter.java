package ikzm_jhm_filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_dto.User;

/**
 * 管理者権限を確認するフィルター。
 * 管理者でないユーザーからのアクセスを拒否する。
 */
@WebFilter({ "/User/Suspend" })
public class AdminFilter implements Filter {

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
			// 管理者でない場合
			// TODO: エラーハンドリング実装時に、「権限がありません」等のメッセージをスコープにセットする

			httpResponse.sendRedirect(httpRequest.getContextPath() + "/ReportList");
		}
	}
}