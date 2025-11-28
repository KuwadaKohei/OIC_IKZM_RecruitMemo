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
import ikzm_jhm_dto.User;
import ikzm_jhm_model.PostForm;
import ikzm_jhm_utils.ModelConverter;

/**
 * 投稿フォーム(postNew.jsp)表示と、POST時のデータバリデーションと登録PostManageAction
 * doGetで投稿フォームの表示
 * doPostでバリテーションチェックと登録処理を行う(PostManageAction
 */
@WebServlet("/ReportNew")
public class PostNewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//新規投稿用の空フォーム
		PostForm form = new PostForm();
		request.setAttribute("postForm", form);

		//フォーム入力画面へ遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("/postNew.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		//リクエストパラメータを PostForm に一括変換
		PostForm form = ModelConverter.toPostForm(request);

		if ("confirm".equals(action)) {

			//確認画面へ遷移
			request.setAttribute("postForm", form);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/postConfirm.jsp");
			dispatcher.forward(request, response);

		} else if ("create".equals(action)) {
			
			//登録実行
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginUser");

			PostManageAction manageAction = new PostManageAction();
			boolean isSuccess = manageAction.createPost(form, user.getUserId());
			
			if(isSuccess) {
				//登録成功 → 一覧画面へリダイレクト
				response.sendRedirect(request.getContextPath() + "/ReportList");
				
			}else {
				//登録失敗 → エラーメッセージを出して入力画面に戻る
				request.setAttribute("errorMessage", "登録処理中にエラーが発生しました。");
				request.setAttribute("postForm", form);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/postNew.jsp");
                dispatcher.forward(request, response);
			}
		}else {
			//action不正時は入力画面へ戻す
			doGet(request, response);
			
		}
	}

}
