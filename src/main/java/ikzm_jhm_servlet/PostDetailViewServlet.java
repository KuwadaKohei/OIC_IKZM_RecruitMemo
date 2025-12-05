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
import ikzm_jhm_viewmodel.PostViewModel;

/**
 * 投稿の詳細情報を取得し、詳細画面へフォワードするサーブレット。
 */
@WebServlet("/ReportView")
public class PostDetailViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_BACK_PATH = "/ReportList";
	private static final String DEFAULT_BACK_LABEL = "投稿一覧へ戻る";

	/**
	 * 投稿IDから詳細情報を取得し、詳細画面を表示する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      リダイレクト失敗時
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strId = request.getParameter("postId");
		int postId = 0;

		try {
			postId = Integer.parseInt(strId);
		} catch (NumberFormatException e) {
			//
			response.sendRedirect(request.getContextPath() + "/ReportList");
			return;
		}

		PostAction postAction = new PostAction();
		try {
			PostViewModel viewModel = postAction.getPostDetails(postId);

			if (viewModel == null) {
				request.setAttribute("pageMessage", "指定された投稿は見つかりませんでした。");
			}

			request.setAttribute("postDetail", viewModel);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/postView.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
			handleError(request, response, e);
		}
	}

	/**
	 * エラー情報をリクエストに積み、共通エラー画面へ遷移する。
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
