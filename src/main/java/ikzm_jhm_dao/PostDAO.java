/***************************/
/* メモの操作 **************/
/***************************/
package ikzm_jhm_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.PostExamSelection;

public class PostDAO {
	//postIdをキーにしてメモ検索
	public Post searchPostById(int target) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Post post = new Post();
		
		int postId = 0;
		int userId = 0;
		int departmentId = 0;
		int methodId = 0;
		String recruitmentNo = null;
		String companyName = null;
		String venueAddress = null;
		LocalDate examDate = null;
		int grade = 0;
		boolean isAnonymous = false;
		LocalDateTime createAt = null;
		LocalDateTime updatedAt = null;
		List<PostExamSelection> examSelection = null;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM POST WHERE POSTID = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , target);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					postId = rs.getInt("postId");
					userId = postId = rs.getInt("userId");
					departmentId = rs.getInt("departmentId");
					methodId = rs.getInt("methodId");
					recruitmentNo = rs.getString("recruitmentNo");
					companyName = rs.getString("companyName");
					venueAddress = rs.getString("venueAddress");
					Date date_e = rs.getDate("examDate");
					grade = rs.getInt("grade");
					isAnonymous = rs.getBoolean("isAnonymous");
					Date date_c = rs.getDate("createAt");
					Date date_u = rs.getDate("updatedAt");
					//List型のexamSelectionの処理//
					
					//localDate型に変換
					examDate =  LocalDate.ofInstant(date_e.toInstant(), ZoneId.systemDefault());
					
					//localDateTime型に変換
					createAt =  LocalDateTime.ofInstant(date_c.toInstant(), ZoneId.systemDefault());
					updatedAt =  LocalDateTime.ofInstant(date_u.toInstant(), ZoneId.systemDefault());
				}
				
				//Postの中身を格納
				post.setPostId(postId);
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
		String recruitmentNo = null;
		String companyName = null;
		String venueAddress = null;
		LocalDate examDate = null;
		int grade = 0;
		boolean isAnonymous = false;
		LocalDateTime createAt = null;
		LocalDateTime updatedAt = null;
		List<PostExamSelection> examSelection = null;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM POST WHERE USERID = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1 , userId);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					postId = rs.getInt("postId");
					departmentId = rs.getInt("departmentId");
					methodId = rs.getInt("methodId");
					recruitmentNo = rs.getString("recruitmentNo");
					companyName = rs.getString("companyName");
					venueAddress = rs.getString("venueAddress");
					Date date_e = rs.getDate("examDate");
					grade = rs.getInt("grade");
					isAnonymous = rs.getBoolean("isAnonymous");
					Date date_c = rs.getDate("createAt");
					Date date_u = rs.getDate("updatedAt");
					//List型のexamSelectionの処理//
					
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
							examSelection
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

	public void deletePost(int postId) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
