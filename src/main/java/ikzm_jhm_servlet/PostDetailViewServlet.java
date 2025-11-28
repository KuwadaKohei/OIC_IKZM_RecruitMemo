package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ikzm_jhm_action.PostAction;
import ikzm_jhm_viewmodel.PostViewModel;

/**
 * 詳細データ取得、権限確認
 * reportView.jspへのフォワード。
 */
@WebServlet("/ReportView")
public class PostDetailViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		PostViewModel viewModel = postAction.getPostDetails(postId);

		if (viewModel == null) {
			//データが存在しない場合
			request.setAttribute("errorMessage", "指定された投稿は見つかりませんでした。");
		}

		request.setAttribute("postDetail", viewModel);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/postView.jsp");
		dispatcher.forward(request, response);
	}
}
