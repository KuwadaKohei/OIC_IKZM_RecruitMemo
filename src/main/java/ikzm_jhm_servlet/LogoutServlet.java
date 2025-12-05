package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログアウトリクエストを受け付け、セッション破棄後にログアウト画面へ遷移させるサーブレット。
 */
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ログアウト処理を実行し、ログアウト画面へフォワードする。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      フォワード失敗時
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// セッションスコープを削除
		HttpSession session = request.getSession();
		session.invalidate();

		// ログアウト画面へ遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("logout.jsp");
		dispatcher.forward(request, response);
	}

}
