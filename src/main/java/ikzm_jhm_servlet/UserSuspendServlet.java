package ikzm_jhm_servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ikzm_jhm_action.UserAction;
import ikzm_jhm_dto.User;

/**
 * ユーザーの制限(BAN)リクエストを受付、ユーザーIDの検索機能、検索結果からユーザーのアクティブステータス(isActive)を編集するメソッドを持つ
 * doGetでユーザーBAN画面(banUser.jsp)へ遷移
 * doPost、Action=Searchでユーザー情報の検索結果を表示、Action=Deleteで該当するユーザー情報の削除を実行
 */
@WebServlet("/User/Suspend")
public class UserSuspendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//検索フォームに入力された文字列を受け取る
		String searchWord = request.getParameter("searchWord");

		//初期状態、管理者からの検索を受け付け結果を返す
		if (searchWord != null || !searchWord.isBlank()) {

			ArrayList<User> userList = new ArrayList<User>();

			UserAction userAction = new UserAction();
			userList = userAction.searchUser(searchWord);

			request.setAttribute("searchResult", userList);

		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/searchManageUser.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//操作の種類を受け取る
		String action = request.getParameter("action");

		//制限・制限解除の対象になるユーザーのユーザーIDを受け取る
		String userId = request.getParameter("userId");

		UserAction userAction = new UserAction();

		//管理者が選択したユーザーを本当に制限するのか確認する。
		if ("confirmBan".equals(action)) {

			request.setAttribute("userId", userId);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmBan");
			dispatcher.forward(request, response);

			return;
		}

		//管理者が選択したユーザーの制限を本当にするのかを確認する。
		if ("confirmUnBan.".equals(action)) {

			request.setAttribute("userId", userId);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmUnBan");
			dispatcher.forward(request, response);

			return;
		}

		//管理者が確認を承諾し、制限処理を実行する。
		if ("executeBan".equals(action)) {

			userAction.banUser(userId);

			request.setAttribute("userId", userId);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/successBan.jsp");
			dispatcher.forward(request, response);

			return;
		}

		//管理者が確認を承諾し、制限解除処理を確認する。
		if ("executeUnBan".equals(action)) {

			userAction.unBanUser(userId);

			request.setAttribute("userId", userId);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/successUnBan.jsp");
			dispatcher.forward(request, response);

			return;
		}
	}

}
