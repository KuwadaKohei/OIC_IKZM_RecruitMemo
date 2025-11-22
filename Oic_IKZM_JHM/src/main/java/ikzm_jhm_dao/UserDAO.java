package ikzm_jhm_dao;

import ikzm_jhm_dto.User;

/*
 * クラス説明
 * Userテーブルとのやり取りを担うクラス
 */
public class UserDAO {

	//GoogleアカウントIDを使用してユーザーレコードを取得する
	public User findByGoogleAccountId(String targetAccountId) {
		return null;
	}

	//新規ログインユーザーを登録し、ユーザーIDを返す
	public int insertGoogleUser(String accountId, String email, String userType,String name, boolean isActive) {
		return 0;
	}

	//引数のユーザーIDのレコードに学科が登録されているか確認する
	public boolean hasDepartment(int userId) {
		
	}

}
