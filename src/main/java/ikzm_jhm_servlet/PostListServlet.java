package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 一覧データの取得(ReportAction)
 * ホーム画面に相当し、index.jspへの遷移を行うクラス。
 * 
 */
@WebServlet({ "/ReportList", "/index.jsp", "/Home" })
public class PostListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
