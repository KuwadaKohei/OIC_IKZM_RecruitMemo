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
 * 編集フォーム表示（データ読み込み）と、POST時のデータ更新 (PostManageService)
 * doPostのみ
 * Action=Editでフォームの表示、Action=EditConfirmで情報の登録を行う
 */
@WebServlet("/ReportEdit")
public class PostEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		//.リクエストからフォームを生成
		PostForm form = ModelConverter.toPostForm(request);

		form.setPostId(Integer.parseInt(request.getParameter("postId")));

		if ("confirm".equals(action)) {
			// 確認画面へ (編集用の確認画面 postEditConfirm.jsp など)
			request.setAttribute("postForm", form);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/postEditConfirm.jsp");
			dispatcher.forward(request, response);
			return;
		}

		if ("update".equals(action)) {
			// 更新実行
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginUser");

			if (user == null) {
				response.sendRedirect(request.getContextPath() + "/Login");
				return;
			}
			PostManageAction manageAction = new PostManageAction();
			boolean isSuccess = manageAction.updatePost(form, user.getUserId());

			if (isSuccess) {
				response.sendRedirect(request.getContextPath() + "/ReportView?postId=" + form.getPostId());
			} else {
				request.setAttribute("errorMessage", "更新中にエラーが発生しました。");
				request.setAttribute("postForm", form);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/postEdit.jsp");
				dispatcher.forward(request, response);
			}
		}

	}

}
