package ikzm_jhm_action;

import java.util.ArrayList;

import ikzm_jhm_dao.UserDAO;
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
}
