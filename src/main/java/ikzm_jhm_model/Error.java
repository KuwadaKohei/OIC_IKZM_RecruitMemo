package ikzm_jhm_model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * エラーコードとユーザー向けメッセージを管理するユーティリティクラス。
 */
public final class Error {

	private static final String DEFAULT_CODE = "SYS-9000";

	private static final Map<String, String> MESSAGES;

	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("DB-0001", "投稿情報の取得に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0002", "投稿の登録に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0003", "投稿の更新に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0004", "投稿の削除に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0010", "投稿詳細の取得に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0011", "投稿詳細の登録に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0012", "投稿詳細の更新に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0020", "試験選択情報の取得に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0021", "試験選択情報の登録に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0022", "試験選択情報の削除に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0030", "学科情報の取得に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0040", "試験内容の取得に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0050", "応募方法の取得に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0060", "ユーザー検索に失敗しました。検索条件を確認して再度お試しください。");
		map.put("DB-0061", "ユーザーの状態更新に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0062", "ユーザー情報の取得に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0063", "Googleアカウントの登録に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0064", "ユーザーの学科情報確認に失敗しました。時間をおいて再度お試しください。");
		map.put("DB-0065", "ユーザーの学科情報更新に失敗しました。時間をおいて再度お試しください。");

		map.put("AUTH-1000", "Googleアカウントの認証に失敗しました。再度ログインしてください。");
		map.put("AUTH-1001", "認証トークンの検証に失敗しました。再度ログインしてください。");
		map.put("AUTH-1010", "ログインされていません。ログインしてから操作をやり直してください。");
		map.put("AUTH-1011", "管理者限定の機能です。メインメニューから操作してください。");

		map.put("APP-2000", "投稿の登録処理に失敗しました。時間をおいて再度お試しください。");
		map.put("APP-2001", "投稿の更新処理に失敗しました。時間をおいて再度お試しください。");
		map.put("APP-2002", "投稿処理後のリソース解放に失敗しました。時間をおいて再度お試しください。");

		map.put("FORM-3000", "事業所名は必須です。");
		map.put("FORM-3001", "事業所名は100文字以内で入力してください。");
		map.put("FORM-3010", "受験日は必須です。");
		map.put("FORM-3011", "受験日の形式が正しくありません。");
		map.put("FORM-3020", "求人票番号は半角数字で入力してください。");
		map.put("FORM-3030", "学科を選択してください。");
		map.put("FORM-3031", "応募方法を選択してください。");
		map.put("FORM-3032", "学年を選択してください。");
		map.put("FORM-3040", "試験内容を少なくとも1つ選択してください。");

		map.put("SYS-9000", "予期しないエラーが発生しました。時間をおいて再度お試しください。");
		MESSAGES = Collections.unmodifiableMap(map);
	}

	private Error() {
	}

	/**
	 * エラーコードに対応するメッセージを取得する。
	 * 未定義コードの場合はデフォルトメッセージを返す。
	 *
	 * @param code エラーコード
	 * @return ユーザー向けメッセージ
	 */
	public static String getMessage(String code) {
		if (code == null || !MESSAGES.containsKey(code)) {
			return MESSAGES.get(DEFAULT_CODE);
		}
		return MESSAGES.get(code);
	}

	/**
	 * 存在しないコードに対してデフォルトコードを返すヘルパー。
	 *
	 * @param code エラーコード
	 * @return 有効なエラーコード
	 */
	public static String getCode(String code) {
		if (code == null || !MESSAGES.containsKey(code)) {
			return DEFAULT_CODE;
		}
		return code;
	}
}
