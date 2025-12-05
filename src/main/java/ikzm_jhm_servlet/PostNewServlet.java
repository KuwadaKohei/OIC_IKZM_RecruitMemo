package ikzm_jhm_servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_action.PostManageAction;
import ikzm_jhm_dto.User;
import ikzm_jhm_model.Error;
import ikzm_jhm_model.PostForm;
import ikzm_jhm_utils.ModelConverter;

/**
 * 新規投稿フォームの表示と登録処理を司るサーブレット。
 */
@WebServlet("/ReportNew")
public class PostNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_BACK_PATH = "/ReportNew";
	private static final String DEFAULT_BACK_LABEL = "投稿フォームに戻る";

	/**
	 * 空の投稿フォームを生成し、新規投稿画面を表示する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      フォワード失敗時
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 新規投稿用の空フォーム
		PostForm form = new PostForm();
		request.setAttribute("postForm", form);

		// フォーム入力画面へ遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("/postNew.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * 入力内容の検証、確認画面表示、投稿登録処理を実行する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      リダイレクト失敗時
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		// リクエストパラメータを PostForm に一括変換
		PostForm form = ModelConverter.toPostForm(request);
		List<String> errorCodes = form.validate();

		if ("confirm".equals(action)) {

			if (!errorCodes.isEmpty()) {
				request.setAttribute("errorCodes", errorCodes);
				request.setAttribute("errorMessages", resolveMessages(errorCodes));
				request.setAttribute("postForm", form);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/postNew.jsp");
				dispatcher.forward(request, response);
				return;
			}

			// 確認画面へ遷移
			request.setAttribute("postForm", form);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/postConfirm.jsp");
			dispatcher.forward(request, response);

		} else if ("create".equals(action)) {

			if (!errorCodes.isEmpty()) {
				request.setAttribute("errorCodes", errorCodes);
				request.setAttribute("errorMessages", resolveMessages(errorCodes));
				request.setAttribute("postForm", form);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/postNew.jsp");
				dispatcher.forward(request, response);
				return;
			}

			// 登録実行
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginUser");
			if (user == null) {
				response.sendRedirect(request.getContextPath() + "/Login");
				return;
			}

			PostManageAction manageAction = new PostManageAction();
			boolean isSuccess;
			try {
				isSuccess = manageAction.createPost(form, user.getUserId());
			} catch (Exception e) {
				request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
				request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
				handleError(request, response, e);
				return;
			}

			if (isSuccess) {
				// 登録成功 → 一覧画面へリダイレクト
				response.sendRedirect(request.getContextPath() + "/ReportList");

			} else {
				// 登録失敗 → エラーメッセージを出して入力画面に戻る
				request.setAttribute("pageMessage", "登録処理中にエラーが発生しました。");
				request.setAttribute("postForm", form);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/postNew.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			// action不正時は入力画面へ戻す
			doGet(request, response);

		}
	}

	/**
	 * エラーコードを解析し、共通エラー画面へ遷移する。
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
	 * 戻り先情報のデフォルト値をリクエストへ補完する。
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

	/**
	 * エラーコードを画面表示用の文言に変換する。
	 *
	 * @param codes エラーコード一覧
	 * @return エラーメッセージ一覧
	 */
	private List<String> resolveMessages(List<String> codes) {
		return codes.stream().map(Error::getMessage).collect(Collectors.toList());
	}

}
