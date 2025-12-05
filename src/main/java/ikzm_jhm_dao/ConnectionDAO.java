package ikzm_jhm_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベース接続を提供するユーティリティ DAO。
 */
public class ConnectionDAO {

	/**
	 * 環境変数またはデフォルト設定を用いて MySQL への接続を確立する。
	 *
	 * @return オープン済み JDBC コネクション
	 * @throws SQLException ドライバ読み込み失敗や接続失敗時
	 */
	public Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("MySQL JDBCドライバーの読み込みに失敗しました。", e);
		}

		String url = System.getenv("RECRUITMEMO_DB_URL");
		String user = System.getenv("RECRUITMEMO_DB_USER");
		String password = System.getenv("RECRUITMEMO_DB_PASSWORD");

		if (url == null || url.isBlank()) {
			url = "jdbc:mysql://localhost:3306/ikzm_recruitMemo";
		}
		if (user == null || user.isBlank()) {
			user = "root";
		}
		if (password == null) {
			password = "";
		}

		return DriverManager.getConnection(url, user, password);
	}
}
