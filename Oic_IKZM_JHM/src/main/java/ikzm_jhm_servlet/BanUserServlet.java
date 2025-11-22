package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ユーザーの制限(BAN)リクエストを受付、ユーザーIDの検索機能、検索結果からユーザーのアクティブステータス(isActive)を編集するメソッドを持つ
 * doGetでユーザーBAN画面(banUser.jsp)へ遷移
 * doPost、Action=Searchでユーザー情報の検索結果を表示、Action=Deleteで該当するユーザー情報の削除を実行
 */
@WebServlet("/BanUser")
public class BanUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
