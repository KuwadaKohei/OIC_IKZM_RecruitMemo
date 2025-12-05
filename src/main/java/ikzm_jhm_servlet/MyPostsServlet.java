package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_action.PostAction;
import ikzm_jhm_dto.User;
import ikzm_jhm_model.Error;
import ikzm_jhm_viewmodel.SearchResultViewModel;

/**
 * ログインユーザー自身の投稿を検索し、自分の投稿一覧画面へフォワードするサーブレット。
 */
@WebServlet("/MyReports")
public class MyPostsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_BACK_PATH = "/MyReports";
	private static final String DEFAULT_BACK_LABEL = "自分の投稿一覧へ戻る";

	/**
	 * 投稿一覧を取得し、自分の投稿画面へ表示する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      フォワード失敗時
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchWord = request.getParameter("searchWord");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");

		PostAction reportAction = new PostAction();
		try {
			// 検索ワードがある場合はAction内でフィルタリング処理が行われる
			SearchResultViewModel viewModel = reportAction.getMyReportList(user.getUserId(), searchWord);

			// viewModelをJSPへ渡す
			request.setAttribute("postList", viewModel);

			// 遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/MyReports.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
			handleError(request, response, e);
		}
	}

	/**
	 * エラー情報を設定し、共通エラー画面へ遷移する。
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
		if (request.getAttribute("backUrl") == null) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
		}
		if (request.getAttribute("backLabel") == null) {
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		dispatcher.forward(request, response);
	}

}
