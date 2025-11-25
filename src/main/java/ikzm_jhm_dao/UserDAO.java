package ikzm_jhm_dao;

import java.util.ArrayList;

import ikzm_jhm_dto.User;

/*
 * クラス説明
 * Userテーブルとのやり取りを担うクラス
 */
public class UserDAO {

	//ユーザーIDを使用してユーザーレコードを取得する
	public User findById(int searchWordInt) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	//GoogleアカウントIDを使用してユーザーレコードを取得する
	public User findByGoogleAccountId(String targetAccountId) {
		return null;
	}

	//メールアドレスを使用してユーザーリストを取得する
	public ArrayList<User> findListByEmail(String email) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	//名前を使用してユーザーリストを取得する
	public ArrayList<User> findListByUsername(String email) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	//新規ログインユーザーを登録し、ユーザーIDを返す
	public int insertGoogleUser(String accountId, String email, String userType, String name, boolean isActive) {
		return 0;
	}

	//引数のユーザーIDのレコードに学科が登録されているか確認する
	public boolean hasDepartment(int userId) {
		return false;

	}

}
