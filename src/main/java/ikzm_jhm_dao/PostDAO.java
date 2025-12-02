/***************************/
/* メモの操作 **************/
/***************************/

package ikzm_jhm_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.PostExamSelection;

public class PostDAO {
	/**********************↓自分メモのメソッド↓**********************/	
	//POSTテーブルからユーザーIDと求人番号を使用してPostリストを取得する
	public List<Post> searchPostByRecruitmentNo(int userId, int substring) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Post> list = new ArrayList<>();

		int postId = 0;
		int departmentId = 0;
		int methodId = 0;
		String companyName = null;
		String venueAddress = null;
		LocalDate examDate = null;
		int grade = 0;
		boolean isAnonymous = false;
		LocalDateTime createAt = null;
		LocalDateTime updatedAt = null;
		List<PostExamSelection> examSelection = null;
		boolean isActive = false;

		try {
			if(con != null) {
				String sql = "SELECT * FROM Post WHERE userId = ? AND recruitmentNo = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , userId);
				pstmt.setInt(2 , substring);
				rs = pstmt.executeQuery();
				while (rs.next() == true) {
					postId = rs.getInt("postId");
					departmentId = rs.getInt("departmentId");
					methodId = rs.getInt("methodId");
					companyName = rs.getString("companyName");
					venueAddress = rs.getString("venueAddress");
					Date date_e = rs.getDate("examDate");
					grade = rs.getInt("grade");
					isAnonymous = rs.getBoolean("isAnonymous");
					Date date_c = rs.getDate("createAt");
					Date date_u = rs.getDate("updatedAt");
					//List型のexamSelectionの処理//
					isActive = rs.getBoolean("isActive");

					//localDate型に変換
					examDate = LocalDate.ofInstant(date_e.toInstant(), ZoneId.systemDefault());

					//localDateTime型に変換
					createAt = LocalDateTime.ofInstant(date_c.toInstant(), ZoneId.systemDefault());
					updatedAt = LocalDateTime.ofInstant(date_u.toInstant(), ZoneId.systemDefault());

					//Postインスタンスを生成
					Post post = new Post(
							postId,
							userId,
							departmentId,
							methodId,
							substring,
							companyName,
							venueAddress,
							examDate,
							grade,
							isAnonymous,
							createAt,
							updatedAt,
							examSelection,
							isActive);

					//listに格納
					list.add(post);

				}
			}
			rs.close();
			pstmt.close();
			return list;
		} catch (SQLException e) {
			//情報取得に失敗(未規定)
			throw new Exception("DB-2003");
		} finally {
			ConnectionDAO.closeConnection(con);
		}
	}

	//POSTテーブルからユーザーIDと試験日付を使用してPostリストを取得する
	public List<Post> searchPostByExamDate(int userId, LocalDate examDate) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Post> list = new ArrayList<>();

		//LocalDate→String→java.sql.Dateに変換
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = simpleDateFormat.format(examDate.toString());
		java.sql.Date date_sql = java.sql.Date.valueOf(date);

		int postId = 0;
		int departmentId = 0;
		int methodId = 0;
		String companyName = null;
		int recruitmentNo = 0;
		String venueAddress = null;
		int grade = 0;
		boolean isAnonymous = false;
		LocalDateTime createAt = null;
		LocalDateTime updatedAt = null;
		List<PostExamSelection> examSelection = null;
		boolean isActive = false;

		try {
			if(con != null) {
				String sql = "SELECT * FROM Post WHERE userId = ? AND examDate = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, userId);
				pstmt.setDate(2, date_sql);
				rs = pstmt.executeQuery();
				while (rs.next() == true) {
					postId = rs.getInt("postId");
					departmentId = rs.getInt("departmentId");
					methodId = rs.getInt("methodId");
					recruitmentNo = rs.getInt("recruitmentNo");
					companyName = rs.getString("companyName");
					venueAddress = rs.getString("venueAddress");
					grade = rs.getInt("grade");
					isAnonymous = rs.getBoolean("isAnonymous");
					Date date_c = rs.getDate("createAt");
					Date date_u = rs.getDate("updatedAt");
					//List型のexamSelectionの処理//
					isActive = rs.getBoolean("isActive");
					
					
					//localDateTime型に変換
					createAt =  LocalDateTime.ofInstant(date_c.toInstant(), ZoneId.systemDefault());
					updatedAt =  LocalDateTime.ofInstant(date_u.toInstant(), ZoneId.systemDefault());
					
					//Postインスタンスを生成
					Post post = new Post(
							postId,
							userId,
							departmentId,
							methodId,
							recruitmentNo,
							companyName,
							venueAddress,
							examDate,
							grade,
							isAnonymous,
							createAt,
							updatedAt,
							examSelection,
							isActive
					);
					
					//listに格納
					list.add(post);
					
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
	
	//POSTテーブルからユーザーIDと***********を使用してPostリストを取得する
	public List<Post> searchPostByKeyword(int userId, String term) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	/**********************↓みんなメモのメソッド↓**********************/
	//全てのメモを取得
	public List<Post> findAll() throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Post> list = new ArrayList<>();
		
		int postId = 0;
		int userId = 0;
		int departmentId = 0;
		int methodId = 0;
		int recruitmentNo = 0;
		String companyName = null;
		String venueAddress = null;
		LocalDate examDate = null;
		int grade = 0;
		boolean isAnonymous = false;
		LocalDateTime createAt = null;
		LocalDateTime updatedAt = null;
		List<PostExamSelection> examSelection = null;
		boolean isActive = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM Post WHERE isActive = true AND examDate >= DATE_SUB(CURDATE(), INTERVAL 3 YEAR) ORDER BY postId DESC AND isActive = true";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					postId = rs.getInt("postId");
					userId = rs.getInt("userId");
					departmentId = rs.getInt("departmentId");
					methodId = rs.getInt("methodId");
					recruitmentNo = rs.getInt("recruitmentNo");
					companyName = rs.getString("companyName");
					venueAddress = rs.getString("venueAddress");
					Date date_e = rs.getDate("examDate");
					grade = rs.getInt("grade");
					isAnonymous = rs.getBoolean("isAnonymous");
					Date date_c = rs.getDate("createAt");
					Date date_u = rs.getDate("updatedAt");
					//List型のexamSelectionの処理//
					isActive = rs.getBoolean("isActive");
					
					//localDate型に変換
					examDate =  LocalDate.ofInstant(date_e.toInstant(), ZoneId.systemDefault());
					
					//localDateTime型に変換
					createAt =  LocalDateTime.ofInstant(date_c.toInstant(), ZoneId.systemDefault());
					updatedAt =  LocalDateTime.ofInstant(date_u.toInstant(), ZoneId.systemDefault());
					
					//Postインスタンスを生成
					Post post = new Post(
							postId,
							userId,
							departmentId,
							methodId,
							recruitmentNo,
							companyName,
							venueAddress,
							examDate,
							grade,
							isAnonymous,
							createAt,
							updatedAt,
							examSelection,
							isActive
					);
					
					//listに格納
					list.add(post);
					
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
	
	//postIdをキーにしてメモ検索
	public Post searchPostById(int target) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Post post = new Post();
		
		int userId = 0;
		int departmentId = 0;
		int methodId = 0;
		int recruitmentNo = 0;
		String companyName = null;
		String venueAddress = null;
		LocalDate examDate = null;
		int grade = 0;
		boolean isAnonymous = false;
		LocalDateTime createAt = null;
		LocalDateTime updatedAt = null;
		List<PostExamSelection> examSelection = null;
		boolean isActive = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM Post WHERE postId = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , target);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					userId = rs.getInt("userId");
					departmentId = rs.getInt("departmentId");
					methodId = rs.getInt("methodId");
					recruitmentNo = rs.getInt("recruitmentNo");
					companyName = rs.getString("companyName");
					venueAddress = rs.getString("venueAddress");
					Date date_e = rs.getDate("examDate");
					grade = rs.getInt("grade");
					isAnonymous = rs.getBoolean("isAnonymous");
					Date date_c = rs.getDate("createAt");
					Date date_u = rs.getDate("updatedAt");
					//List型のexamSelectionの処理//
					isActive = rs.getBoolean("isActive");
					
					//localDate型に変換
					examDate =  LocalDate.ofInstant(date_e.toInstant(), ZoneId.systemDefault());
					
					//localDateTime型に変換
					createAt =  LocalDateTime.ofInstant(date_c.toInstant(), ZoneId.systemDefault());
					updatedAt =  LocalDateTime.ofInstant(date_u.toInstant(), ZoneId.systemDefault());
				}
				
				//Postの中身を格納
				post.setPostId(target);
				post.setUserId(userId);
				post.setDepartmentId(departmentId);
				post.setMethodId(methodId);
				post.setRecruitmentNo(recruitmentNo);
				post.setCompanyName(companyName);
				post.setVenueAddress(venueAddress);
				post.setExamDate(examDate);
				post.setGrade(grade);
				post.setAnonymous(isAnonymous);
				post.setCreateAt(createAt);
				post.setUpdatedAt(updatedAt);
				post.setExamSelection(examSelection);
				post.setActive(isActive);
			}
			rs.close();
			pstmt.close();
			return post;
		}catch(SQLException e) {
			//情報取得に失敗(未規定)
			throw new Exception("DB-2003");
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}
	
	
	//userIdをキーにしてメモ検索
	public ArrayList<Post> searchPostListByUserId(int userId) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Post> list = new ArrayList<>();
		
		int postId = 0;
		int departmentId = 0;
		int methodId = 0;
		int recruitmentNo = 0;
		String companyName = null;
		String venueAddress = null;
		LocalDate examDate = null;
		int grade = 0;
		boolean isAnonymous = false;
		LocalDateTime createAt = null;
		LocalDateTime updatedAt = null;
		List<PostExamSelection> examSelection = null;
		boolean isActive = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM Post WHERE userId = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , userId);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					postId = rs.getInt("postId");
					departmentId = rs.getInt("departmentId");
					methodId = rs.getInt("methodId");
					recruitmentNo = rs.getInt("recruitmentNo");
					companyName = rs.getString("companyName");
					venueAddress = rs.getString("venueAddress");
					Date date_e = rs.getDate("examDate");
					grade = rs.getInt("grade");
					isAnonymous = rs.getBoolean("isAnonymous");
					Date date_c = rs.getDate("createAt");
					Date date_u = rs.getDate("updatedAt");
					//List型のexamSelectionの処理//
					isActive = rs.getBoolean("isActive");
					
					//localDate型に変換
					examDate =  LocalDate.ofInstant(date_e.toInstant(), ZoneId.systemDefault());
					
					//localDateTime型に変換
					createAt =  LocalDateTime.ofInstant(date_c.toInstant(), ZoneId.systemDefault());
					updatedAt =  LocalDateTime.ofInstant(date_u.toInstant(), ZoneId.systemDefault());
					
					//Postインスタンスを生成
					Post post = new Post(
							postId,
							userId,
							departmentId,
							methodId,
							recruitmentNo,
							companyName,
							venueAddress,
							examDate,
							grade,
							isAnonymous,
							createAt,
							updatedAt,
							examSelection,
							isActive
					);
					
					//listに格納
					list.add(post);
					
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
	
	//POSTテーブルから試験日付を使用してPostリストを取得する
	public List<Post> searchPostByExamDate(LocalDate examDate) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Post> list = new ArrayList<>();
		
		//LocalDate→String→java.sql.Dateに変換
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = simpleDateFormat.format(examDate.toString());
		java.sql.Date date_sql = java.sql.Date.valueOf(date);
		
		int postId = 0;
		int userId = 0;
		int departmentId = 0;
		int methodId = 0;
		int recruitmentNo = 0;
		String companyName = null;
		String venueAddress = null;
		int grade = 0;
		boolean isAnonymous = false;
		LocalDateTime createAt = null;
		LocalDateTime updatedAt = null;
		List<PostExamSelection> examSelection = null;
		boolean isActive = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM Post WHERE examDate = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setDate(1 , date_sql);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					postId = rs.getInt("postId");
					userId = rs.getInt("userId");
					departmentId = rs.getInt("departmentId");
					methodId = rs.getInt("methodId");
					recruitmentNo = rs.getInt("recruitmentNo");
					companyName = rs.getString("companyName");
					venueAddress = rs.getString("venueAddress");
					grade = rs.getInt("grade");
					isAnonymous = rs.getBoolean("isAnonymous");
					Date date_c = rs.getDate("createAt");
					Date date_u = rs.getDate("updatedAt");
					//List型のexamSelectionの処理//
					isActive = rs.getBoolean("isActive");

					//localDateTime型に変換
					createAt = LocalDateTime.ofInstant(date_c.toInstant(), ZoneId.systemDefault());
					updatedAt = LocalDateTime.ofInstant(date_u.toInstant(), ZoneId.systemDefault());

					//Postインスタンスを生成
					Post post = new Post(
							postId,
							userId,
							departmentId,
							methodId,
							recruitmentNo,
							companyName,
							venueAddress,
							examDate,
							grade,
							isAnonymous,
							createAt,
							updatedAt,
							examSelection,
							isActive);

					//listに格納
					list.add(post);

				}
			}
			rs.close();
			pstmt.close();
			return list;
		} catch (SQLException e) {
			//情報取得に失敗(未規定)
			throw new Exception("DB-2003");
		} finally {
			ConnectionDAO.closeConnection(con);
		}
	}

	//POSTテーブルから求人番号を使用してPostリストを取得する
	public List<Post> searchPostByRecruitmentNo(int substring) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Post> list = new ArrayList<>();
		
		int postId = 0;
		int userId = 0;
		int departmentId = 0;
		int methodId = 0;
		String companyName = null;
		String venueAddress = null;
		LocalDate examDate = null;
		int grade = 0;
		boolean isAnonymous = false;
		LocalDateTime createAt = null;
		LocalDateTime updatedAt = null;
		List<PostExamSelection> examSelection = null;
		boolean isActive = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM Post WHERE recruitmentNo = ? AND isActive = true";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , substring);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					postId = rs.getInt("postId");
					userId = rs.getInt("userId");
					departmentId = rs.getInt("departmentId");
					methodId = rs.getInt("methodId");
					companyName = rs.getString("companyName");
					venueAddress = rs.getString("venueAddress");
					Date date_e = rs.getDate("examDate");
					grade = rs.getInt("grade");
					isAnonymous = rs.getBoolean("isAnonymous");
					Date date_c = rs.getDate("createAt");
					Date date_u = rs.getDate("updatedAt");
					//List型のexamSelectionの処理//
					isActive = rs.getBoolean("isActive");
					
					//localDate型に変換
					examDate =  LocalDate.ofInstant(date_e.toInstant(), ZoneId.systemDefault());
					
					//localDateTime型に変換
					createAt =  LocalDateTime.ofInstant(date_c.toInstant(), ZoneId.systemDefault());
					updatedAt =  LocalDateTime.ofInstant(date_u.toInstant(), ZoneId.systemDefault());
					
					//Postインスタンスを生成
					Post post = new Post(
							postId,
							userId,
							departmentId,
							methodId,
							substring,
							companyName,
							venueAddress,
							examDate,
							grade,
							isAnonymous,
							createAt,
							updatedAt,
							examSelection,
							isActive
					);
					
					//listに格納
					list.add(post);
					
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

	public List<Post> searchPostByKeyword(String term) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	/**********************↓共通メソッド↓**********************/

	public int insertPost(Post post) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int postId = post.getPostId();
		
		//LocalDate,LocalDateTime→String→java.sql.Dateに変換
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = simpleDateFormat.format(post.getExamDate().toString());
		java.sql.Date date_exam = java.sql.Date.valueOf(date);
		
		date = simpleDateFormat.format(post.getExamDate().toString());
		java.sql.Date date_create = java.sql.Date.valueOf(date);
		
		date = simpleDateFormat.format(post.getExamDate().toString());
		java.sql.Date date_update = java.sql.Date.valueOf(date);
		
		try {
			if(con != null) {
				String sql = "INSERT INTO Post(userId , departmentId , methodId , recruitmentNo , companyName , venueAddress , examDate , grade , isAnonymous , createAt , updatedAt , isActive)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , post.getUserId());
				pstmt.setInt(2 , post.getDepartmentId());
				pstmt.setInt(3 , post.getMethodId());
				pstmt.setInt(4 , post.getRecruitmentNo());
				pstmt.setString(5 , post.getCompanyName());
				pstmt.setString(6 , post.getVenueAddress());
				pstmt.setDate(7 , date_exam);
				pstmt.setInt(8 , post.getGrade());
				pstmt.setBoolean(9 , post.isAnonymous());
				pstmt.setDate(10 , date_create);
				pstmt.setDate(11 , date_update);
				pstmt.setBoolean(12 , post.isActive());
				
				pstmt.executeUpdate();
				
				//オートインクリメントの値を取得する
				rs = pstmt.getGeneratedKeys();
				if(rs.next()) {
					postId = rs.getInt("postId");
				}
			}
			pstmt.close();
			return postId;
		}catch(SQLException e) {
			//情報削除に失敗(未規定)
			throw new Exception("DB-2003");
			
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}

	public void update(Post post) {
		// TODO 自動生成されたメソッド・スタブ

	}

	//メモを削除する(疑似的に)
	public void deletePost(int postId) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		
		try {
			if(con != null) {
				String sql = "UPDATE Post SET isActive = false WHERE postId = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , postId);
				pstmt.executeUpdate();
			}
			pstmt.close();
		}catch(SQLException e) {
			//情報削除に失敗(未規定)
			throw new Exception("DB-2003");
			
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}
}
