package ikzm_jhm_servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_action.UserAction;
import ikzm_jhm_dto.Department;
import ikzm_jhm_dto.User;
import ikzm_jhm_model.Error;

/**
 * 新規ユーザーの学科・学年情報を登録するためのサーブレット。
 * <p>
 * doGet で入力画面を表示し、doPost で登録処理を実行する。
 * </p>
 */
@WebServlet("/NewUser")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_BACK_PATH = "/NewUser";
	private static final String DEFAULT_BACK_LABEL = "学科情報入力に戻る";

	/**
	 * デフォルトコンストラクタ。
	 */
	public NewUserServlet() {
	}

	/**
	 * 学科一覧を取得し、新規ユーザー入力画面を表示する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException フォワード失敗時
	 * @throws IOException      フォワード失敗時
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 学科プルダウン用のリストを取得
		UserAction userAction = new UserAction();
		List<Department> deptList;
		try {
			deptList = userAction.getDepartmentList();
		} catch (Exception e) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
			handleError(request, response, e);
			return;
		}

		// リクエストスコープにセット
		request.setAttribute("deptList", deptList);

		// 入力画面 (newUser.jsp) へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/newUser.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * 入力された学科・学年情報を登録し、完了後に投稿一覧へ遷移する。
	 *
	 * @param request  リクエスト情報
	 * @param response レスポンス情報
	 * @throws ServletException エラー画面へのフォワード時
	 * @throws IOException      リダイレクト時
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログイン済みユーザー情報の取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");

		// 学年・学科入力情報の取得
		String departmentIdStr = request.getParameter("departmentId");
		String gradeStr = request.getParameter("grade");

		int departmentId;
		int grade;
		try {
			departmentId = Integer.parseInt(departmentIdStr);
			grade = Integer.parseInt(gradeStr);
		} catch (NumberFormatException e) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
			handleError(request, response, e);
			return;
		}

		// ログイン情報が見つからない場合弾く
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}

		// 更新実行
		UserAction userAction = new UserAction();
		boolean isSuccess;
		try {
			isSuccess = userAction.registerUserDetail(user.getUserId(), departmentId, grade);
		} catch (Exception e) {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
			handleError(request, response, e);
			return;
		}

		if (isSuccess) {
			// 登録情報を使用しログイン情報を更新
			user.setDepartmentId(departmentId);
			user.setGrade(grade);
			session.setAttribute("loginUser", user);

			// ホーム画面に遷移
			response.sendRedirect(request.getContextPath() + "/ReportList");
		} else {
			request.setAttribute("backUrl", request.getContextPath() + DEFAULT_BACK_PATH);
			request.setAttribute("backLabel", DEFAULT_BACK_LABEL);
			handleError(request, response, new Exception("DB-0065"));
		}
	}

	/**
	 * エラー内容を設定し、共通エラー画面へフォワードする。
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
