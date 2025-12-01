/***************************/
/* 学科・コース ************/
/***************************/
package ikzm_jhm_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ikzm_jhm_dto.Department;

public class DepartmentDAO {

	//学科IDに対応するレコードを取得する
	public Department findById(int departmentId) throws Exception {
		Connection con = ConnectionDAO.createConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Department dpt = new Department();
		
		String departmentName = null;
		
		
		
		try {
			if(con != null) {
				String sql = "SELECT * FROM Department WHERE departmentId = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, departmentId);
				
				rs = pstmt.executeQuery();
				
				while(rs.next() == true) {
					departmentName = rs.getString("departmentName");	
				}
				
				dpt.setDepartmentId(departmentId);
				dpt.setDepartmentName(departmentName);
			}		
			rs.close();
			pstmt.close();
			return dpt;
			
		}catch(SQLException e) {
			//情報取得に失敗(未規定)
			throw new Exception("DB-2003");
		}finally {
			ConnectionDAO.closeConnection(con);
		}
	}

}
