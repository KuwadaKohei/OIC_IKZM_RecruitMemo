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

import ikzm_jhm_action.PostAction;
import ikzm_jhm_action.PostManageAction;
import ikzm_jhm_dto.User;
import ikzm_jhm_model.Error;
import ikzm_jhm_model.PostForm;
import ikzm_jhm_utils.ModelConverter;

/**
 * 投稿編集フォームの表示と更新処理を担当するサーブレット。
 */
@WebServlet("/ReportEdit")
public class PostEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_BACK_PATH = "/ReportList";
	private static final String DEFAULT_BACK_LABEL = "投稿一覧へ戻る";

	/**
	 * 指定された投稿を編集できるフォームを表示する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      リダイレクト失敗時
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");

		String postIdStr = request.getParameter("postId");
		int postId = Integer.parseInt(postIdStr);

		PostAction postAction = new PostAction();
		PostForm form;
		try {
			form = postAction.getPostFormForEdit(postId, user.getUserId());
		} catch (Exception e) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
			handleError(request, response, e);
			return;
		}

		if (form == null) {
			request.setAttribute("pageMessage", "指定された投稿が見つかりませんでした。");
			response.sendRedirect(request.getContextPath() + "/ReportList");
			return;
		}

		request.setAttribute("postForm", form);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/postEdit.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * 編集フォームのバリデーション、確認画面表示、更新処理を実行する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      リダイレクト失敗時
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		// .リクエストからフォームを生成
		PostForm form = ModelConverter.toPostForm(request);
		form.setPostId(Integer.parseInt(request.getParameter("postId")));
		List<String> errorCodes = form.validate();

		if ("confirm".equals(action)) {
			if (!errorCodes.isEmpty()) {
				request.setAttribute("errorCodes", errorCodes);
				request.setAttribute("errorMessages", resolveMessages(errorCodes));
				request.setAttribute("postForm", form);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/postEdit.jsp");
				dispatcher.forward(request, response);
				return;
			}
			// 確認画面へ (編集用の確認画面 postEditConfirm.jsp など)
			request.setAttribute("postForm", form);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/postEditConfirm.jsp");
			dispatcher.forward(request, response);
			return;
		}

		if ("update".equals(action)) {
			if (!errorCodes.isEmpty()) {
				request.setAttribute("errorCodes", errorCodes);
				request.setAttribute("errorMessages", resolveMessages(errorCodes));
				request.setAttribute("postForm", form);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/postEdit.jsp");
				dispatcher.forward(request, response);
				return;
			}
			// 更新実行
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginUser");

			if (user == null) {
				response.sendRedirect(request.getContextPath() + "/Login");
				return;
			}
			PostManageAction manageAction = new PostManageAction();
			boolean isSuccess;
			try {
				isSuccess = manageAction.updatePost(form, user.getUserId());
			} catch (Exception e) {
				String editPath = String.format("/ReportEdit?postId=%d", form.getPostId());
				request.setAttribute("backUrl", request.getContextPath() + editPath);
				request.setAttribute("backLabel", "投稿編集へ戻る");
				handleError(request, response, e);
				return;
			}

			if (isSuccess) {
				response.sendRedirect(request.getContextPath() + "/ReportView?postId=" + form.getPostId());
			} else {
				request.setAttribute("pageMessage", "更新中にエラーが発生しました。");
				request.setAttribute("postForm", form);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/postEdit.jsp");
				dispatcher.forward(request, response);
			}
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
	 * 戻り先のURLおよびラベルが未設定の場合にデフォルト値を設定する。
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
	 * エラーコードを人間可読なメッセージへ変換する。
	 *
	 * @param codes エラーコード一覧
	 * @return メッセージ一覧
	 */
	private List<String> resolveMessages(List<String> codes) {
		return codes.stream().map(Error::getMessage).collect(Collectors.toList());
	}

}
