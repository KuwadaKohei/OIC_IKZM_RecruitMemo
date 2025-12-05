package ikzm_jhm_model;

/**
 * Google OAuth の認証情報を一元管理する定数クラス。
 * 学習用途であり、本番環境では安全な秘匿方法に置き換えること。
 */
public final class GoogleOAuthInfo {

	private GoogleOAuthInfo() {
	}

	// 未発行です
	public static final String CLIENT_ID = "クライアントID";
	public static final String CLIENT_SECRET = "DEVシークレットID";
	public static final String REDIRECT_URI = "http://localhost:8080/IKZM_JHM/Login";

	// 取得情報のスコープ
	// これの仕様をactionクラスで置き換えるかを検討中
	public static final String[] SCOPES = { "openid", "email", "profile" };
}