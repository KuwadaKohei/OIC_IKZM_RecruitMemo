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
import java.util.ArrayList;
import java.util.List;

import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.PostExamSelection;

public class PostDAO {

	public Post searchPostById(int target) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
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
					postId = rs.getInt(postId);
					userId = postId = rs.getInt(userId);
					departmentId = rs.getInt(departmentId);
					methodId = rs.getInt(methodId);
					recruitmentNo = rs.getString(recruitmentNo);
					companyName = rs.getString(companyName);
					venueAddress = rs.getString(venueAddress);
					examDate = rs.getDate(examDate);
				}
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
	
	//
	public ArrayList<Post> searchPostListByUserId(int userId){
		
	}

	public void deletePost(int postId) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
