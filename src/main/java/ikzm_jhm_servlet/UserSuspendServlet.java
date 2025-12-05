package ikzm_jhm_servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ikzm_jhm_action.UserAction;
import ikzm_jhm_dto.User;
import ikzm_jhm_model.Error;

/**
 * 管理者によるユーザー検索およびBAN/解除処理を提供するサーブレット。
 */
@WebServlet("/User/Suspend")
public class UserSuspendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_BACK_PATH = "/User/Suspend";
	private static final String DEFAULT_BACK_LABEL = "ユーザー管理に戻る";

	/**
	 * ユーザー検索を実行し、ユーザー管理画面を表示する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      フォワード失敗時
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 検索フォームに入力された文字列を受け取る
		String searchWord = request.getParameter("searchWord");

		// 初期状態、管理者からの検索を受け付け結果を返す
		if (searchWord != null && !searchWord.isBlank()) {

			ArrayList<User> userList = new ArrayList<User>();

			UserAction userAction = new UserAction();
			try {
				userList = userAction.searchUser(searchWord);
			} catch (Exception e) {
				request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
				request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
				handleError(request, response, e);
				return;
			}

			request.setAttribute("userList", userList);

		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/searchManageUser.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * BAN/解除の各種操作を受け付け、確認・実行画面へ遷移させる。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      フォワード失敗時
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 操作の種類を受け取る
		String action = request.getParameter("action");

		// 制限・制限解除の対象になるユーザーのユーザーIDを受け取る
		String userId = request.getParameter("userId");

		UserAction userAction = new UserAction();

		try {
			// 管理者が選択したユーザーを本当に制限するのか確認する。
			if ("confirmBan".equals(action)) {

				request.setAttribute("userId", userId);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmBan");
				dispatcher.forward(request, response);

				return;
			}

			// 管理者が選択したユーザーの制限を本当にするのかを確認する。
			if ("confirmUnBan".equals(action)) {

				request.setAttribute("userId", userId);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmUnBan");
				dispatcher.forward(request, response);

				return;
			}

			// 管理者が確認を承諾し、制限処理を実行する。
			if ("executeBan".equals(action)) {

				boolean success = userAction.banUser(userId);
				request.setAttribute("userId", userId);
				if (!success) {
					request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
					request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
					handleError(request, response, new Exception("DB-0061"));
					return;
				}

				RequestDispatcher dispatcher = request.getRequestDispatcher("/successBan.jsp");
				dispatcher.forward(request, response);

				return;
			}

			// 管理者が確認を承諾し、制限解除処理を確認する。
			if ("executeUnBan".equals(action)) {

				boolean success = userAction.unBanUser(userId);
				request.setAttribute("userId", userId);
				if (!success) {
					request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
					request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
					handleError(request, response, new Exception("DB-0061"));
					return;
				}

				RequestDispatcher dispatcher = request.getRequestDispatcher("/successUnBan.jsp");
				dispatcher.forward(request, response);

				return;
			}
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
