package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_action.ReportAction;
import ikzm_jhm_dto.User;
import ikzm_jhm_viewmodel.SearchResultViewModel;

/**
 * 自分の投稿リスト取得 (ReportService)、JSP (myReports.jsp) へのフォワード。
 */
@WebServlet("/MyReports")
public class MyReportsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String searchWord = request.getParameter("searchWord");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		
		ReportAction reportAction = new ReportAction();
		
		//検索ワードがある場合はAction内でフィルタリング処理が行われる
        SearchResultViewModel viewModel = reportAction.getMyReportList(user.getUserId(), searchWord);
		
        //viewModelをJSPへ渡す
        request.setAttribute("viewModel", viewModel);
        
        //遷移
        RequestDispatcher dispatcher = request.getRequestDispatcher("/MyReports.jsp");
        dispatcher.forward(request, response);
	}

}
