package ikzm_jhm_action;

import java.util.ArrayList;
import java.util.List;

import ikzm_jhm_dao.DepartmentDAO;
import ikzm_jhm_dao.UserDAO;
import ikzm_jhm_dto.Department;
import ikzm_jhm_dto.User;

/**
 * ユーザー管理関連のビジネスロジックをまとめたアクションクラス。
 * 検索・状態変更・学科登録といった操作を提供する。
 */
public class UserAction {

	/**
	 * 検索文字列をもとにユーザー一覧を取得する。
	 *
	 * @param searchWord ユーザー名またはメールアドレスの一部
	 * @return 該当ユーザーのリスト
	 * @throws Exception DAO 層の例外
	 */
	public ArrayList<User> searchUser(String searchWord) throws Exception {

		UserDAO userDao = new UserDAO();
		ArrayList<User> userList = new ArrayList<User>();

		if (searchWord.startsWith("/")) {
			userList = userDao.findListByEmail(searchWord);
		} else {
			userList = userDao.findListByUsername(searchWord);
		}

		return userList;

	}

	/**
	 * ユーザーを一時停止状態にする。
	 *
	 * @param userIdString 対象ユーザー ID（文字列）
	 * @return 成功時 true
	 * @throws Exception DAO 層の例外
	 */
	public boolean banUser(String userIdString) throws Exception {
		int userId = parseUserId(userIdString);
		if (userId <= 0) {
			return false;
		}

		UserDAO userDao = new UserDAO();
		return userDao.updateActiveStatus(userId, false);
	}

	/**
	 * 停止中ユーザーの制限を解除する。
	 *
	 * @param userIdString 対象ユーザー ID（文字列）
	 * @return 成功時 true
	 * @throws Exception DAO 層の例外
	 */
	public boolean unBanUser(String userIdString) throws Exception {
		int userId = parseUserId(userIdString);
		if (userId <= 0) {
			return false;
		}

		UserDAO userDao = new UserDAO();
		return userDao.updateActiveStatus(userId, true);
	}

	/**
	 * 文字列から安全にユーザー ID をパースする。
	 *
	 * @param userIdString ユーザー ID 文字列
	 * @return 正常な整数 ID。失敗時は -1
	 */
	private int parseUserId(String userIdString) {
		if (userIdString == null || userIdString.isBlank()) {
			return -1;
		}
		try {
			return Integer.parseInt(userIdString);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * 学科マスタ一覧を取得する。
	 *
	 * @return 学科リスト（null の場合は空リスト）
	 * @throws Exception DAO 層の例外
	 */
	public List<Department> getDepartmentList() throws Exception {
		DepartmentDAO dao = new DepartmentDAO();
		// DAOが未実装でも動くように、nullチェックや空リスト返却を考慮
		List<Department> list = dao.findAll();
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
	}

	/**
	 * 学科と学年情報をユーザーに紐づける。
	 *
	 * @param userId       対象ユーザー ID
	 * @param departmentId 学科 ID
	 * @param grade        学年
	 * @return 更新成功時 true（バリデーション NG の場合は false）
	 * @throws Exception DAO 層の例外
	 */
	public boolean registerUserDetail(int userId, int departmentId, int grade) throws Exception {
		// バリデーション (簡易)
		if (departmentId <= 0 || grade <= 0) {
			return false;
		}

		UserDAO dao = new UserDAO();
		return dao.updateDepartmentInfo(userId, departmentId, grade);
	}
}
