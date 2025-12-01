package ikzm_jhm_servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ikzm_jhm_action.UserAction;
import ikzm_jhm_dto.Department;
import ikzm_jhm_dto.User;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/NewUser")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewUserServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 学科プルダウン用のリストを取得
		UserAction userAction = new UserAction();
		List<Department> deptList = userAction.getDepartmentList();

		// リクエストスコープにセット
		request.setAttribute("deptList", deptList);

		// 入力画面 (newUser.jsp) へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/newUser.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//ログイン済みユーザー情報の取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		
		//学年・学科入力情報の取得
		String departmentIdStr = request.getParameter("departmentId");
		String gradeStr = request.getParameter("grade");
		
		//取得情報を数値に変換
		int departmentId = Integer.parseInt(departmentIdStr);
		int grade = Integer.parseInt(gradeStr);

		//ログイン情報が見つからない場合弾く
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}

		// 更新実行
		UserAction userAction = new UserAction();
		boolean isSuccess = userAction.registerUserDetail(user.getUserId(), departmentId, grade);

		if (isSuccess) {
			//登録情報を使用しログイン情報を更新
			user.setDepartmentId(departmentId);
            user.setGrade(grade);
            session.setAttribute("loginUser", user);
            
            //ホーム画面に遷移
			response.sendRedirect(request.getContextPath() + "/ReportList");
		} else {
			// エラー時
			request.setAttribute("errorMessage", "登録に失敗しました。システム管理者にお問い合わせください。");
			doGet(request, response);
		}
	}

}
