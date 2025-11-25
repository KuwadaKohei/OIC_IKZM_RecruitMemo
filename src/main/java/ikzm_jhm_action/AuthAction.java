package ikzm_jhm_action;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import ikzm_jhm_dao.UserDAO;
import ikzm_jhm_dto.User;
import ikzm_jhm_model.AuthResult;
import ikzm_jhm_model.GoogleOAuthInfo;

/*
 * クラス説明
 * ログインに関するロジックを受け取る。
 * 内部的にAuthResult型を定義しているが、
 * これはログイン処理を行う際に認証の戻り値を格納するためのクラスである。
 * 
 */

public class AuthAction {

	//クライアントID等の取得
	private static final String CLIENT_ID = GoogleOAuthInfo.CLIENT_ID;
	private static final String CLIENT_SECRET = GoogleOAuthInfo.CLIENT_SECRET;
	private static final String REDIRECT_URI = GoogleOAuthInfo.REDIRECT_URI;

	//認証サービスで使用するインスタンス
	private static final NetHttpTransport HTTP = new NetHttpTransport();
	private static final GsonFactory JSON = GsonFactory.getDefaultInstance();

	//グーグル認可サーバーへ問い合わせるURLの作成
	public String buildGoogleAuthUrl(String state) {
		return new GoogleAuthorizationCodeRequestUrl(
				CLIENT_ID,
				REDIRECT_URI,
				java.util.Arrays.asList("openid", "email", "profile"))
						.setAccessType("online")
						.setState(state)
						.build();
	}

	public AuthResult handleOAuthCallBack(String authCode) {

		//認可コードをトークンに交換
		try {
			GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
					HTTP,
					JSON,
					"https://oauth2.googleapis.com/token",
					CLIENT_ID,
					CLIENT_SECRET,
					authCode,
					REDIRECT_URI).execute();

			String idTokenString = tokenResponse.getIdToken();

			//Googleトークンの検証
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP, JSON)
					.setAudience(Collections.singletonList(CLIENT_ID))
					.setIssuer("https://accounts.google.com")
					.build();
			GoogleIdToken idToken = verifier.verify(idTokenString);

			if (idToken == null) {
				//IDトークンの検証に失敗したエラーを発行する
				throw new RuntimeException("");
			}

			//情報を抽出するオブジェクト
			Payload payload = idToken.getPayload();

			//トークンから情報の抽出
			String accountId = payload.getSubject();
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			UserDAO userDAO = new UserDAO();
			User user = userDAO.findByGoogleAccountId(accountId);

			String userType = whichUserType(email);
			boolean newUser = false;

			if (user == null) {
				int newId = userDAO.insertGoogleUser(accountId, email, userType, name, true);
				user = new User(newId, accountId, email, userType, name, true,false);
				newUser = true;
			}

			//学科の登録が必要か確認する
			boolean needsDept = !userDAO.hasDepartment(user.getUserId());
			return new AuthResult(user, newUser, needsDept);
		} catch (IOException | GeneralSecurityException e) {
			//ユーザー認証に失敗した例外を発行
			return null;
		}
	}

	/*
	 * ユーザーのタイプを判定する
	 * 現時点ではユーザータイプが生徒か教員のみのため不必要に見えるが
	 * 今後増えたときのためにモジュール化した
	 */
	private String whichUserType(String email) {
		//8桁の数字+@oic-ok.ac.jpを判定します。特に正規表現の案内はしません。変更の際はググって。
		if (email.matches("^\\d{8}@oic-ok\\.ac\\.jp$")) {
			return "student";
		} else {
			return "teacher";
		}
	}

	//CSRF対策のためのランダムな文字列を生成する
	public String stateValueGenerator() {

		SecureRandom random = new SecureRandom();
		int stateByteLength = 16;

		//実際の文字列生成
		byte[] bytes = new byte[stateByteLength];
		random.nextBytes(bytes);

		//Httpリクエストで使用するため、URLとして使えるようにエンコード
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

	}
}
