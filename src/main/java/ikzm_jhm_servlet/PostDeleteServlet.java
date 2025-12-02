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

/**
 * 投稿削除リクエストの受付、投稿削除後にDeletedPost.jspへ遷移する。管理者権限の厳格なチェックを行う
 * doPostのみ実装、受け取ったPostIdの値に対応する投稿情報を削除する(PostReportAction.deleteReport(postId)に問い合わせる
 */
@WebServlet("/DeleteReport")
public class PostDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		String target = request.getParameter("target");
		
		int targetId = Integer.parseInt(target);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");

		PostManageAction pma = new PostManageAction();
		PostDAO postDAO = new PostDAO();

		//不正を検知、ログアウト処理
		if (user.getUserId() != targetId && !user.isAdmin()) {
			session.invalidate();

			RequestDispatcher dispatcher = request.getRequestDispatcher("/unauthorized.jsp");
			dispatcher.forward(request, response);
		}

		//削除前の確認
		if ("confirm".equals(action)) {
			
			Post post = postDAO.searchPostById(target);
			
			request.setAttribute("post", post);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteConfirm.jsp");
			dispatcher.forward(request, response);
		}
		
		//削除処理
		if("execute".equals(action)) {
			
			pma.deletePost(targetId);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/DeletedPost.jsp");
			dispatcher.forward(request, response);
		}
	}

}
