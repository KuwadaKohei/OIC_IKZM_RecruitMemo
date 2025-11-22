package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 投稿削除リクエストの受付、投稿削除後にDeletedPost.jspへ遷移する。管理者権限の厳格なチェックを行う
 * doPostのみ実装、受け取ったPostIdの値に対応する投稿情報を削除する(PostReportAction.deleteReport(postId)に問い合わせる
 */
@WebServlet("/DeleteReport")
public class DeleteReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
