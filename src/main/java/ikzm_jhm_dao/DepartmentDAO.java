package ikzm_jhm_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ikzm_jhm_dto.Department;

/**
 * 学科マスタ(departments)を操作するDAO。
 */
public class DepartmentDAO {

	private final ConnectionDAO connectionDao = new ConnectionDAO();

	/**
	 * 全学科を取得する。
	 * 
	 * @return 学科リスト（0件時は空）
	 */
	public List<Department> findAll() throws Exception {
		List<Department> list = new ArrayList<Department>();
		String sql = "SELECT department_id, department_name FROM departments ORDER BY department_name";

		try (Connection conn = connectionDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(mapDepartment(rs));
			}
		} catch (SQLException e) {
			throw new Exception("DB-0030");
		}

		return list;
	}

	/**
	 * 主キーで学科を1件取得する。
	 * 
	 * @param departmentId 学科ID
	 * @return DTO（見つからない場合はnull）
	 */
	public Department findById(int departmentId) throws Exception {
		Department department = null;
		String sql = "SELECT department_id, department_name FROM departments WHERE department_id = ?";

		try (Connection conn = connectionDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, departmentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				department = mapDepartment(rs);
			}
		} catch (SQLException e) {
			throw new Exception("DB-0030");
		}

		return department;
	}

	/**
	 * ResultSetをDepartment DTOへ変換する。
	 * 
	 * @param rs クエリ結果
	 * @return DTO
	 * @throws SQLException 変換エラー
	 */
	private Department mapDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setDepartmentId(rs.getInt("department_id"));
		department.setDepartmentName(rs.getString("department_name"));
		return department;
	}
}
