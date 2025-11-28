/***************************/
/* DBの接続と切断***********/
/***************************/
package ikzm_jhm_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDAO {
	//DBと接続する
	protected static Connection createConnection() throws Exception{
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			//ドライバロードエラー番号(未規定)
			throw new IllegalStateException("DB-2001");
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/IKZMRecruitMemo","Jun0224","Jun123");
		}catch(SQLException e) {
			//DB接続エラー番号(未規定)
			throw new Exception("DB-2001");
		}
		return con;
	}
	
	//DBを切断する
	protected static void closeConnection(Connection con) throws Exception{
		if(con != null) {
			try {
				con.close();
			}catch(SQLException e){
				//DB切断エラー番号(未規定)
				throw new Exception("DB-2002");
			}
		}
	}
}
