package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_action.PostManageAction;
import ikzm_jhm_dao.PostDAO;
import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.User;
import ikzm_jhm_model.Error;

/**
 * 投稿削除リクエストを管理し、権限制御を行いながら対象投稿を削除するサーブレット。
 */
@WebServlet("/DeleteReport")
public class PostDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_BACK_PATH = "/ReportList";
	private static final String DEFAULT_BACK_LABEL = "投稿一覧へ戻る";

	/**
	 * 削除確認画面の表示および削除処理の本実行を行う。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      リダイレクト失敗時
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String action = request.getParameter("action");
			String postIdString = request.getParameter("postId");
			if (postIdString == null || postIdString.isBlank()) {
				response.sendRedirect(request.getContextPath() + "/ReportList");
				return;
			}

			int postId;
			try {
				postId = Integer.parseInt(postIdString);
			} catch (NumberFormatException e) {
				response.sendRedirect(request.getContextPath() + "/ReportList");
				return;
			}

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginUser");
			if (user == null) {
				response.sendRedirect(request.getContextPath() + "/Login");
				return;
			}

			PostDAO postDAO = new PostDAO();
			Post post = postDAO.searchPostById(postId);
			if (post == null) {
				response.sendRedirect(request.getContextPath() + "/ReportList");
				return;
			}

			boolean authorized = post.getUserId() == user.getUserId() || user.isAdmin();
			if (!authorized) {
				session.invalidate();
				RequestDispatcher dispatcher = request.getRequestDispatcher("/unauthorized.jsp");
				dispatcher.forward(request, response);
				return;
			}

			PostManageAction manageAction = new PostManageAction();

			if ("confirm".equals(action)) {
				request.setAttribute("postDetail", post);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteConfirm.jsp");
				dispatcher.forward(request, response);
				return;
			}

			if ("execute".equals(action)) {
				manageAction.deletePost(postId);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/DeletedPost.jsp");
				dispatcher.forward(request, response);
				return;
			}

			response.sendRedirect(request.getContextPath() + "/ReportList");
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
