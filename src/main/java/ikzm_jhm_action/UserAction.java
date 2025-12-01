package ikzm_jhm_action;

import java.util.ArrayList;
import java.util.List;

import ikzm_jhm_dao.DepartmentDAO;
import ikzm_jhm_dao.UserDAO;
import ikzm_jhm_dto.Department;
import ikzm_jhm_dto.User;

public class UserAction {

	/*
	 * 検索文字列からユーザー情報を捜索する
	 * 使用クラス：UserManageServlet
	 */
	public ArrayList<User> searchUser(String searchWord) {

		UserDAO userDao = new UserDAO();
		ArrayList<User> userList = new ArrayList<User>();

		if (searchWord.startsWith("/")) {
			userList = userDao.findListByEmail(searchWord);
		} else {
			userList = userDao.findListByUsername(searchWord);
		}

		return userList;

	}

	public void banUser(String target) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void unBanUser(String target) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public List<Department> getDepartmentList() {
		DepartmentDAO dao = new DepartmentDAO();
		// DAOが未実装でも動くように、nullチェックや空リスト返却を考慮
		List<Department> list = dao.findAll();
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
	}

	public boolean registerUserDetail(int userId, int departmentId, int grade) {
		// バリデーション (簡易)
		if (departmentId <= 0 || grade <= 0) {
			return false;
		}

		UserDAO dao = new UserDAO();
		return dao.updateDepartmentInfo(userId, departmentId, grade);
	}
}
