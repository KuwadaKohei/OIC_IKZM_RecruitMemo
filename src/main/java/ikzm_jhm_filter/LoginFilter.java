package ikzm_jhm_filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
 * ログイン認証を確認するフィルター。
 * 原則すべてリクエストを遮断し、特定の除外パスのみ許可する
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

	// ホワイトリスト
	private static final List<String> EXCLUDE_PATHS = Arrays.asList(
			"/Login",
			"/login.jsp",
			"/css",
			"/js",
			"/images");

	/**
	 * ログイン状態を確認し、未ログイン時は共通エラーへフォワードする。
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// 現在のリクエストパスを取得
		String requestPath = httpRequest.getServletPath();

		// 除外リストに含まれているか確認
		if (isExcluded(requestPath)) {
			// 除外対象ならチェックしない
			chain.doFilter(request, response);
			return;
		}

		// 3. ログインセッションチェック
		HttpSession session = httpRequest.getSession();
		boolean isLoggedIn = false;
		if (session != null) {
			User user = (User) session.getAttribute("loginUser");
			if (user != null) {
				isLoggedIn = true;
			}
		}

		if (isLoggedIn) {
			// ログイン済みなら通過
			chain.doFilter(request, response);
		} else {
			forwardToError(httpRequest, httpResponse);
		}
	}

	/**
	 * 未ログインエラー画面へフォワードする。
	 */
	private void forwardToError(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("errorCode", "AUTH-1010");
		request.setAttribute("error", Error.getMessage("AUTH-1010"));
		request.setAttribute("backUrl", request.getContextPath() + "/Login");
		request.setAttribute("backLabel", "ログイン画面に戻る");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * リクエストパスが除外対象かどうかを判定する。
	 *
	 * @param path サーブレットパス
	 * @return true なら除外対象
	 */
	private boolean isExcluded(String path) {
		for (String exclude : EXCLUDE_PATHS) {
			if (path.equals(exclude) || path.startsWith(exclude + "/")) {
				return true;
			}
		}
		return false;
	}
}