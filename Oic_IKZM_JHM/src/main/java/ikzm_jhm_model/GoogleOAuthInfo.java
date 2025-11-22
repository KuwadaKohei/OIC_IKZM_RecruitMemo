package ikzm_jhm_model;

/*
 * クラス説明
 * GoogleOAuthの認証情報を定数格納するクラス。
 * 管理を楽にするために作成しています。
 * あくまで学習目的であり、GoogleOAuthの認証情報の要秘匿性や、
 * 本番環境でこの扱い方をすることの危険性を理解しています。
 */
public final class GoogleOAuthInfo {

	private GoogleOAuthInfo() {
	}

	//未発行です
	public static final String CLIENT_ID = "クライアントID";
	public static final String CLIENT_SECRET = "DEVシークレットID";
	public static final String REDIRECT_URI = "http://localhost:8080/IKZM_JHM/Login";

	// 取得情報のスコープ
	//これの仕様をactionクラスで置き換えるかを検討中
	public static final String[] SCOPES = { "openid", "email", "profile" };
}