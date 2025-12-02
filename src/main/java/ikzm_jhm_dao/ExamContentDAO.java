/***************************/
/* 試験情報 ****************/
/***************************/
package ikzm_jhm_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ikzm_jhm_dto.ExamContent;

public class ExamContentDAO {
	
	//全ての試験情報を取得する
	public List<ExamContent> findAll() throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ExamContent> list = new ArrayList<>();
		
		int contentId = 0;
		String contentCategory = null;
		String contentName = null;
		boolean needsDetail = false;
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM ExamContent";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next() == true) {
					contentId = rs.getInt("contentId");
					contentCategory = rs.getString("contentId");
					contentName = rs.getString("contentName");
					needsDetail = rs.getBoolean("needsDetail");
					
					//Postインスタンスを生成
					ExamContent examContent = new ExamContent(
							contentId,
							contentCategory,
							contentName,
							needsDetail
					);
					
					//listに格納
					list.add(examContent);
					
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

}
