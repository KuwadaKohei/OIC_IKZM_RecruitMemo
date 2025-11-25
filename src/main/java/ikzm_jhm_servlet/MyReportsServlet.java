package ikzm_jhm_servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_action.PostViewAction;
import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.User;

/**
 * 自分の投稿リスト取得 (ReportService)、JSP (myReports.jsp) へのフォワード。
 */
@WebServlet("/MyReports")
public class MyReportsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		String searchWord = request.getParameter("searchWord");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		
		PostViewAction pva = new PostViewAction();
		
		int userId = user.getUserId();
		
		if(action == null) {
			
			ArrayList<Post> postList = pva.myReports(userId);
			
			session.setAttribute("myReports", postList);
			
		}
		
		if("search".equals(action)) {
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/MyReports.jsp");
		dispatcher.forward(request, response);
	}

}
