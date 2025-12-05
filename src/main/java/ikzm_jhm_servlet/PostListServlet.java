package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ikzm_jhm_action.PostAction;
import ikzm_jhm_model.Error;
import ikzm_jhm_viewmodel.SearchResultViewModel;

/**
 * 投稿一覧を取得し、トップページを表示するサーブレット。
 */
@WebServlet({ "/ReportList", "/Home" })
public class PostListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_BACK_PATH = "/ReportList";
	private static final String DEFAULT_BACK_LABEL = "投稿一覧へ戻る";

	/**
	 * 投稿一覧または検索結果を取得し、トップページへフォワードする。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      フォワード失敗時
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String searchWord = request.getParameter("searchWord");

			PostAction reportAction = new PostAction();
			SearchResultViewModel viewModel;

			if (searchWord != null && !searchWord.isBlank()) {

				viewModel = reportAction.searchPosts(searchWord);

			} else {

				viewModel = reportAction.getPostList();
			}

			request.setAttribute("postList", viewModel);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/reportList.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
			handleError(request, response, e);
		}
	}

	/**
	 * エラー情報を設定して共通エラー画面へ遷移する。
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
	 * 戻り先情報が未指定の場合にデフォルト値を補完する。
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
