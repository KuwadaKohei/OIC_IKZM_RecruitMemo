package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_action.AuthAction;
import ikzm_jhm_dto.User;
import ikzm_jhm_model.AuthResult;
import ikzm_jhm_model.Error;

/**
 * Google OAuth 認証フローのエントリーポイントとなるサーブレット。
 * <p>
 * 認可コード付きのコールバックを処理し、セッションへユーザー情報を保持する。
 * 認証状態に応じてログイン画面、新規ユーザー登録画面、トップページへ遷移させる。
 * </p>
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_BACK_PATH = "/login.jsp";
	private static final String DEFAULT_BACK_LABEL = "ログイン画面に戻る";

	/**
	 * Google OAuth 認証リクエストおよびコールバックを処理する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード時に発生
	 * @throws IOException      リダイレクト時に発生
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String code = request.getParameter("code");
			String action = request.getParameter("action");
			String state = request.getParameter("state");

			AuthAction authAction = new AuthAction();

			// 初回アクセスやaction=null：ログインボタン画面を表示
			if (action == null && (code == null || code.isEmpty())) {

				RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
				return;

			}

			// ログインボタン押下：同意画面へリダイレクト
			if ("requestAuth".equals(action)) {

				HttpSession session = request.getSession();

				// CSRF対策
				String stateValue = authAction.stateValueGenerator();
				session.setAttribute("oauth_State", stateValue);

				String authUrl = authAction.buildGoogleAuthUrl(stateValue);

				response.sendRedirect(authUrl);
				return;
			}

			// 認可コードがある場合、ログイン処理を実行
			if (code != null && !code.isEmpty()) {

				HttpSession session = request.getSession();

				String expectedState = (String) session.getAttribute("oauth_State");

				// CSRF対策の認証 認可サーバーへ問い合わせたstateとレスポンスのstateを照合する
				if (expectedState == null || state == null || !expectedState.equals(state)) {
					/*
					 * CSRF認証不可としてエラー表示
					 * 不正なログインを検知しました、ログインページに戻ります。等
					 */
					return;
				}
				// stateの破棄 これガチで再利用不可
				session.removeAttribute("oauth_State");

				AuthResult result = authAction.handleOAuthCallBack(code);

				if (result == null) {
					request.setAttribute("pageMessage", "認証に失敗しました。再度ログインしてください。");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
					dispatcher.forward(request, response);
					return;
				}

				// セッションにログインユーザーを保持
				User loginUser = result.getUser();
				session.setAttribute("loginUser", loginUser);

				// 初回（学科未設定）なら /NewUser、そうでなければトップへ
				if (result.isNewUser() || result.needsDepartment()) {
					response.sendRedirect(request.getContextPath() + "/NewUser");
				} else {
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				return;
			}

			// どれにも該当しない場合ログインへ遷移(通常到達しないが安全のため設定)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
			handleError(request, response, e);
		}
	}

	/**
	 * エラー情報をリクエストへ格納し、共通エラー画面へフォワードする。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @param e        発生した例外
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      フォワード失敗時
	 */
	private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws ServletException, IOException {
		String code = Error.getCode(e.getMessage());
		request.setAttribute("errorCode", code);
		request.setAttribute("error", Error.getMessage(code));
		setDefaultBackAttributes(request);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * 戻り先URLとラベルが未設定の場合にデフォルト値を補完する。
	 *
	 * @param request リクエスト情報
	 */
	private void setDefaultBackAttributes(HttpServletRequest request) {
		if (request.getAttribute("backUrl") == null) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
		}
		if (request.getAttribute("backLabel") == null) {
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
		}
	}

}
