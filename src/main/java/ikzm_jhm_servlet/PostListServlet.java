package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ikzm_jhm_action.PostAction;
import ikzm_jhm_viewmodel.SearchResultViewModel;

/**
 * 一覧データの取得(ReportAction)
 * ホーム画面に相当し、index.jspへの遷移を行うクラス。
 * 
 */
@WebServlet({ "/ReportList", "/index.jsp", "/Home" })
public class PostListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchWord = request.getParameter("searchWord");

		PostAction reportAction = new PostAction();
		SearchResultViewModel viewModel;

		if (searchWord != null && !searchWord.isBlank()) {

			viewModel = reportAction.searchPosts(searchWord);

		} else {

			viewModel = reportAction.getPostList();
		}

		request.setAttribute("postList", viewModel);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

}
