package ikzm_jhm_model;

import ikzm_jhm_dto.User;

/*
 * クラス説明
 * ログイン認証の呼び出し元サーブレットが利用する戻り値の型
 */

public class AuthResult {
	private final User user;
	private final boolean newUser;
	private final boolean needsDepartment;

	public AuthResult(User user, boolean newUser, boolean needsDepartment) {
		this.user = user;
		this.newUser = newUser;
		this.needsDepartment = needsDepartment;
	}

	public User getUser() {
		return user;
	}

	public boolean isNewUser() {
		return newUser;
	}

	public boolean needsDepartment() {
		return needsDepartment;
	}
}