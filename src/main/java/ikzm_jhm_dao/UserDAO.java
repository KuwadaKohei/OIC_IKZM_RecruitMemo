/***************************/
/* ユーザの操作 ************/
/***************************/
package ikzm_jhm_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ikzm_jhm_dto.User;

public class UserDAO {

	//ユーザーIDを使用してユーザーレコードを取得する
	public User findById(int searchWordInt) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		User user = new User();
		
		String googleAccountId = null;
		String email = null;
		String userType = null;
		String name = null;
		int departmentId = 0;
		int grade = 0;
		boolean isActive = false;
		boolean isAdmin = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM User WHERE UserId = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , searchWordInt);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					googleAccountId = rs.getString("googleAccountId");
					email = rs.getString("email");
					userType = rs.getString("userType");
					name = rs.getString("name");
					departmentId = rs.getInt("departmentId");
					grade = rs.getInt("grade");
					isActive = rs.getBoolean("isActive");
					isAdmin = rs.getBoolean("isAdmin");
				}
				
				//Userの中身を格納
				user.setUserId(searchWordInt);
				user.setGoogleAccountId(googleAccountId);
				user.setEmail(email);
				user.setUserType(userType);
				user.setName(name);
				user.setDepartmentId(departmentId);
				user.setGrade(grade);
				user.setActive(isActive);
				user.setAdmin(isAdmin);
			}
			rs.close();
			pstmt.close();
			return user;
		}catch(SQLException e) {
			//情報取得に失敗(未規定)
			throw new Exception("DB-2003");
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}

	//GoogleアカウントIDを使用してユーザーレコードを取得する
	public User findByGoogleAccountId(String targetAccountId) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		User user = new User();
		
		int userId = 0;
		String email = null;
		String userType = null;
		String name = null;
		int departmentId = 0;
		int grade = 0;
		boolean isActive = false;
		boolean isAdmin = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM User WHERE googleAccountId = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1 , targetAccountId);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					userId = rs.getInt("userId");
					email = rs.getString("email");
					userType = rs.getString("userType");
					name = rs.getString("name");
					departmentId = rs.getInt("departmentId");
					grade = rs.getInt("grade");
					isActive = rs.getBoolean("isActive");
					isAdmin = rs.getBoolean("isAdmin");
				}
				
				//Userの中身を格納
				user.setUserId(userId);
				user.setGoogleAccountId(targetAccountId);
				user.setEmail(email);
				user.setUserType(userType);
				user.setName(name);
				user.setDepartmentId(departmentId);
				user.setGrade(grade);
				user.setActive(isActive);
				user.setAdmin(isAdmin);
			}
			rs.close();
			pstmt.close();
			return user;
		}catch(SQLException e) {
			//情報取得に失敗(未規定)
			throw new Exception("DB-2003");
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}

	//メールアドレスを使用してユーザーリストを取得する
	public ArrayList<User> findListByEmail(String email) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = new ArrayList<>();
		
		int userId = 0;
		String googleAccountId = null;
		String userType = null;
		String name = null;
		int departmentId = 0;
		int grade = 0;
		boolean isActive = false;
		boolean isAdmin = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM User WHERE email = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1 , email);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					userId = rs.getInt("userId");
					googleAccountId = rs.getString("googleAccountId");
					userType = rs.getString("userType");
					name = rs.getString("name");
					departmentId = rs.getInt("departmentId");
					grade = rs.getInt("grade");
					isActive = rs.getBoolean("isActive");
					isAdmin = rs.getBoolean("isAdmin");
					
					//Userインスタンスを生成
					User user = new User(userId , googleAccountId , email , userType , name , departmentId , grade , isActive , isAdmin);
					
					//listに格納
					list.add(user);
				}
			}
			rs.close();
			pstmt.close();
			return list;
		}catch(SQLException e) {
			//情報取得に失敗(未規定)
			throw new Exception("DB-2003");
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}

	//名前を使用してユーザーリストを取得する
	public ArrayList<User> findListByUsername(String name) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = new ArrayList<>();
		
		int userId = 0;
		String googleAccountId = null;
		String email = null;
		String userType = null;
		int departmentId = 0;
		int grade = 0;
		boolean isActive = false;
		boolean isAdmin = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM User WHERE name = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1 , name);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					userId = rs.getInt("userId");
					googleAccountId = rs.getString("googleAccountId");
					email = rs.getString("email");
					userType = rs.getString("userType");
					departmentId = rs.getInt("departmentId");
					grade = rs.getInt("grade");
					isActive = rs.getBoolean("isActive");
					isAdmin = rs.getBoolean("isAdmin");
					
					//Userインスタンスを生成
					User user = new User(userId , googleAccountId , email , userType , name , departmentId , grade , isActive , isAdmin);
					
					//listに格納
					list.add(user);
				}
			}
			rs.close();
			pstmt.close();
			return list;
		}catch(SQLException e) {
			//情報取得に失敗(未規定)
			throw new Exception("DB-2003");
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}

	//新規ログインユーザーを登録し、ユーザーIDを返す
	public int insertGoogleUser(String accountId, String email, String userType, String name, int departmentId , int grade , boolean isActive, boolean isAdmin) throws Exception{
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int userId = 0;
		
		try {
			if(con != null) {
				String sql = "INSERT INTO USER(accountId , email , userType , name , departmentId , grade , isActive , isAdmin) VALUES(?,?,?,?,?,?,?,?)";
				pstmt = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1 , accountId);
				pstmt.setString(2 , email);
				pstmt.setString(3 , userType);
				pstmt.setString(4 , name);
				pstmt.setInt(5, departmentId);
				pstmt.setInt(6, grade);
				pstmt.setBoolean(7 , isActive);
				pstmt.setBoolean(8 , isAdmin);
				
				pstmt.executeUpdate();
				
				//オートインクリメントの値を取得する
				rs = pstmt.getGeneratedKeys();
				if(rs.next()) {
					userId = rs.getInt("userId");
				}
			}
			pstmt.close();
			
			return userId;
		}catch(SQLException e) {
			//情報登録に失敗(未規定)
			e.getStackTrace();
			throw new Exception("DB-2006");
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}

	//引数のユーザーIDのレコードに学科が登録されているか確認する
	public boolean hasDepartment(int userId) throws Exception{
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int departmentId = 0;
		
		boolean result = false;
		
		try {
			if(con != null) {
				String sql = "SELECT departmentId FROM User WHERE userId = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , userId);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					departmentId = rs.getInt("departmentId");
					
					if(departmentId == 0) {
						result = false;
					}else {
						result =  true;
					}
				}
			}
			rs.close();
			pstmt.close();
			return result;
		}catch(SQLException e) {
			//情報取得に失敗(未規定)
			throw new Exception("DB-2003");
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}
}
