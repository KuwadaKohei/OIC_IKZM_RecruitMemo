package ikzm_jhm_model;

import ikzm_jhm_dto.User;

/**
 * 認証処理の結果（ユーザー情報、新規登録フラグ、学科登録要否）をまとめた値オブジェクト。
 */
public class AuthResult {
	private final User user;
	private final boolean newUser;
	private final boolean needsDepartment;

	/**
	 * すべての値を指定するコンストラクタ。
	 */
	public AuthResult(User user, boolean newUser, boolean needsDepartment) {
		this.user = user;
		this.newUser = newUser;
		this.needsDepartment = needsDepartment;
	}

	/**
	 * 認証済みユーザーを取得する。
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 取得したユーザーが新規作成されたかどうか。
	 */
	public boolean isNewUser() {
		return newUser;
	}

	/**
	 * 学科登録が必要かどうかの判定結果。
	 */
	public boolean needsDepartment() {
		return needsDepartment;
	}
}