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

/**
 * Google OAuth を利用した認証フローをまとめたアクションクラス。
 * 認可 URL の生成、コールバック処理、ユーザー種別判定などを提供する。
 */
public class AuthAction {

	// クライアントID等の取得
	private static final String CLIENT_ID = GoogleOAuthInfo.CLIENT_ID;
	private static final String CLIENT_SECRET = GoogleOAuthInfo.CLIENT_SECRET;
	private static final String REDIRECT_URI = GoogleOAuthInfo.REDIRECT_URI;

	// 認証サービスで使用するインスタンス
	private static final NetHttpTransport HTTP = new NetHttpTransport();
	private static final GsonFactory JSON = GsonFactory.getDefaultInstance();

	/**
	 * Google 認可サーバーへリダイレクトするための URL を生成する。
	 *
	 * @param state CSRF 対策用のステート文字列
	 * @return 認可エンドポイントの完全 URL
	 */
	public String buildGoogleAuthUrl(String state) {
		return new GoogleAuthorizationCodeRequestUrl(
				CLIENT_ID,
				REDIRECT_URI,
				java.util.Arrays.asList("openid", "email", "profile"))
				.setAccessType("online")
				.setState(state)
				.build();
	}

	/**
	 * 認可コードを受け取り、アクセストークン・ID トークンを検証したうえで
	 * {@link AuthResult} を構築する。
	 *
	 * @param authCode Google から返却された認可コード
	 * @return 認証結果を格納した {@link AuthResult}
	 * @throws Exception 検証失敗や通信障害が発生した場合
	 */
	public AuthResult handleOAuthCallBack(String authCode) throws Exception {

		// 認可コードをトークンに交換
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

			// Googleトークンの検証
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP, JSON)
					.setAudience(Collections.singletonList(CLIENT_ID))
					.setIssuer("https://accounts.google.com")
					.build();
			GoogleIdToken idToken = verifier.verify(idTokenString);

			if (idToken == null) {
				// IDトークンの検証に失敗したエラーを発行する
				throw new Exception("AUTH-1001");
			}

			// 情報を抽出するオブジェクト
			Payload payload = idToken.getPayload();

			// トークンから情報の抽出
			String accountId = payload.getSubject();
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			UserDAO userDAO = new UserDAO();
			User user = userDAO.findByGoogleAccountId(accountId);

			String userType = whichUserType(email);
			boolean newUser = false;

			if (user == null) {
				int newId = userDAO.insertGoogleUser(accountId, email, userType, name, true, false);
				user = new User(newId, accountId, email, userType, name, 0, 0, true, false);

				newUser = true;
			}

			// 学科の登録が必要か確認する
			boolean needsDept = !userDAO.hasDepartment(user.getUserId());
			return new AuthResult(user, newUser, needsDept);
		} catch (IOException | GeneralSecurityException e) {
			// ユーザー認証に失敗した例外を発行
			throw new Exception("AUTH-1000");
		}
	}

	/**
	 * メールアドレスからユーザー種別を推定する。
	 *
	 * @param email 判定対象メールアドレス
	 * @return student / teacher のいずれか
	 */
	private String whichUserType(String email) {
		// 8桁の数字+@oic-ok.ac.jpを判定します。特に正規表現の案内はしません。変更の際はググって。
		if (email.matches("^\\d{8}@oic-ok\\.ac\\.jp$")) {
			return "student";
		} else {
			return "teacher";
		}
	}

	/**
	 * CSRF 対策用のステート文字列を生成する。
	 *
	 * @return URL セーフな Base64 文字列
	 */
	public String stateValueGenerator() {

		SecureRandom random = new SecureRandom();
		int stateByteLength = 16;

		// 実際の文字列生成
		byte[] bytes = new byte[stateByteLength];
		random.nextBytes(bytes);

		// Httpリクエストで使用するため、URLとして使えるようにエンコード
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

	}
}
