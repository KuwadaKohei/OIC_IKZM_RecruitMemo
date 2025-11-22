package ikzm_jhm_action;

import ikzm_jhm_dao.UserDAO;
import ikzm_jhm_dto.User;

public class UserAction {

	/*
	 * 検索文字列からユーザー情報を創作する
	 * 使用クラス：UserManageServlet
	 */
	public User SearchUser(String searchWord) {

		User user = null;
		UserDAO userDao = new UserDAO();

		if (searchWord.startsWith("/")) {
			user = userDao.findByEmail(searchWord);
		}else if(searchWord.startsWith("@")) {
			int searchWordInt = Integer.parseInt(searchWord);
			user = userDao.findById(searchWordInt);
		}else {
			user = userDao.findByUsername(searchWord);
		}
		
		return user;
	}
}
