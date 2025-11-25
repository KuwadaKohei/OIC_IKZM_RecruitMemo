package ikzm_jhm_servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 編集フォーム表示（データ読み込み）と、POST時のデータ更新 (PostManageService)
 * doPostのみ
 * Action=Editでフォームの表示、Action=EditConfirmで情報の登録を行う
 */
@WebServlet("/PostEdit")
public class PostEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
