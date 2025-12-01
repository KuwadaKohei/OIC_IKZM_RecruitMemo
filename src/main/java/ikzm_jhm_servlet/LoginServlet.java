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

/**
 * クラス説明
 *Google OAuth認証の完了とセッションへのユーザー情報格納を行うサーブレット
 *doGetのみ実装
 *リクエストパラメータに認可コードが含まれている場合はログイン処理を実行
 *ログインの核になる処理はサービス層で実装(actionパッケージ)
 *パラメータがない場合は、login,jspへフォワード
 *初回ログイン実行時は、ユーザーへ学科情報入力をリクエストする/NewAccountへ遷移
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String code = request.getParameter("code");
		String action = request.getParameter("action");
		String state = request.getParameter("state");

		AuthAction authAction = new AuthAction();

		//初回アクセスやaction=null：ログインボタン画面を表示
		if (action == null && (code == null || code.isEmpty())) {

			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);

		}

		//ログインボタン押下：同意画面へリダイレクト
		if ("requestAuth".equals(action)) {

			HttpSession session = request.getSession();

			//CSRF対策
			String stateValue = authAction.stateValueGenerator();
			session.setAttribute("oauth_State", stateValue);

			String authUrl = authAction.buildGoogleAuthUrl(stateValue);

			response.sendRedirect(authUrl);
			return;
		}

		//認可コードがある場合、ログイン処理を実行
		if (code != null && !code.isEmpty()) {

			HttpSession session = request.getSession();

			String expectedState = (String) session.getAttribute("oauth_State");

			//CSRF対策の認証 認可サーバーへ問い合わせたstateとレスポンスのstateを照合する
			if (expectedState == null || state == null || !expectedState.equals(state)) {
				/*
				 * CSRF認証不可としてエラー表示
				 * 不正なログインを検知しました、ログインページに戻ります。等
				 */
				return;
			}
			//stateの破棄 これガチで再利用不可
			session.removeAttribute("oauth_State");

			AuthResult result = authAction.handleOAuthCallBack(code);

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

		//どれにも該当しない場合ログインへ遷移(通常到達しないが安全のため設定)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
		
	}

}
