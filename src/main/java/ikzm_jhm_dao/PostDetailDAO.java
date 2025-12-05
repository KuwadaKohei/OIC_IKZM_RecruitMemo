package ikzm_jhm_dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import ikzm_jhm_dto.PostDetail;

/**
 * 投稿詳細(post_details)を扱うDAO。
 */
public class PostDetailDAO {

	private final ConnectionDAO connectionDao = new ConnectionDAO();

	/**
	 * 投稿IDに紐づく詳細情報を取得する。
	 * 
	 * @param postId 投稿ID
	 * @return DTO（存在しなければnull）
	 */
	public PostDetail findByPostId(int postId) throws Exception {
		PostDetail detail = null;
		String sql = "SELECT post_id, result_date, scheduled_hires, total_applicants, advice_text FROM post_details WHERE post_id = ?";

		try (Connection conn = connectionDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, postId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				detail = mapDetail(rs);
			}
		} catch (SQLException e) {
			throw new Exception("DB-0010");
		}

		return detail;
	}

	/**
	 * 投稿IDのリストを受け取り、対応する詳細情報をまとめて取得する。
	 *
	 * @param postIds 投稿IDリスト
	 * @return key=postId のマップ
	 * @throws Exception DBアクセス例外
	 */
	public Map<Integer, PostDetail> findByPostIds(List<Integer> postIds) throws Exception {
		Map<Integer, PostDetail> detailMap = new HashMap<>();
		if (postIds == null || postIds.isEmpty()) {
			return detailMap;
		}

		List<Integer> uniqueIds = new ArrayList<>(new LinkedHashSet<>(postIds));
		if (uniqueIds.isEmpty()) {
			return detailMap;
		}

		StringBuilder placeholders = new StringBuilder();
		for (int i = 0; i < uniqueIds.size(); i++) {
			if (i > 0) {
				placeholders.append(", ");
			}
			placeholders.append("?");
		}

		String sql = "SELECT post_id, result_date, scheduled_hires, total_applicants, advice_text FROM post_details WHERE post_id IN ("
				+ placeholders + ")";

		try (Connection conn = connectionDao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < uniqueIds.size(); i++) {
				stmt.setInt(i + 1, uniqueIds.get(i));
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				PostDetail detail = mapDetail(rs);
				detailMap.put(detail.getPostId(), detail);
			}
		} catch (SQLException e) {
			throw new Exception("DB-0010");
		}

		return detailMap;
	}

	/**
	 * 詳細情報を新規登録する。
	 * 
	 * @param detail DTO
	 * @return 追加成功時true
	 */
	public boolean insert(PostDetail detail) throws Exception {
		try (Connection conn = connectionDao.getConnection()) {
			return insert(detail, conn);
		} catch (SQLException e) {
			throw new Exception("DB-0011");
		}
	}

	/**
	 * 既存コネクションを利用して詳細情報を新規登録する。
	 *
	 * @param detail DTO
	 * @param conn   共有トランザクション用コネクション
	 * @return 追加成功時true
	 * @throws SQLException SQL例外
	 */
	public boolean insert(PostDetail detail, Connection conn) throws Exception {
		boolean inserted = false;
		String sql = "INSERT INTO post_details (post_id, result_date, scheduled_hires, total_applicants, advice_text) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			bindDetailParameters(stmt, detail);
			inserted = stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new Exception("DB-0011");
		}

		return inserted;
	}

	/**
	 * 詳細情報を更新する。
	 * 
	 * @param detail DTO
	 * @return 更新成功時true
	 */
	public boolean update(PostDetail detail) throws Exception {
		try (Connection conn = connectionDao.getConnection()) {
			return update(detail, conn);
		} catch (SQLException e) {
			throw new Exception("DB-0012");
		}
	}

	/**
	 * 既存コネクションを利用して詳細情報を更新する。
	 *
	 * @param detail DTO
	 * @param conn   共有トランザクション用コネクション
	 * @return 更新成功時true
	 * @throws SQLException SQL例外
	 */
	public boolean update(PostDetail detail, Connection conn) throws Exception {
		boolean updated = false;
		String sql = "UPDATE post_details SET result_date = ?, scheduled_hires = ?, total_applicants = ?, advice_text = ? WHERE post_id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			setDate(stmt, 1, detail.getResultDate());
			setNullableInt(stmt, 2, detail.getScheduled_hires());
			setNullableInt(stmt, 3, detail.getTotalApplicants());
			stmt.setString(4, detail.getAdviceText());
			stmt.setInt(5, detail.getPostId());
			updated = stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new Exception("DB-0012");
		}

		return updated;
	}

	/**
	 * ResultSetをPostDetailに変換する。
	 * 
	 * @param rs クエリ結果
	 * @return DTO
	 * @throws SQLException 変換例外
	 */
	private PostDetail mapDetail(ResultSet rs) throws SQLException {
		PostDetail detail = new PostDetail();
		detail.setPostId(rs.getInt("post_id"));
		Date resultDate = rs.getDate("result_date");
		if (resultDate != null) {
			detail.setResultDate(resultDate.toLocalDate());
		}
		detail.setScheduled_hires(rs.getInt("scheduled_hires"));
		detail.setTotalApplicants(rs.getInt("total_applicants"));
		detail.setAdviceText(rs.getString("advice_text"));
		return detail;
	}

	/**
	 * INSERT用パラメータを設定する。
	 * 
	 * @param stmt   PreparedStatement
	 * @param detail DTO
	 * @throws SQLException 設定例外
	 */
	private void bindDetailParameters(PreparedStatement stmt, PostDetail detail) throws SQLException {
		stmt.setInt(1, detail.getPostId());
		setDate(stmt, 2, detail.getResultDate());
		setNullableInt(stmt, 3, detail.getScheduled_hires());
		setNullableInt(stmt, 4, detail.getTotalApplicants());
		stmt.setString(5, detail.getAdviceText());
	}

	/**
	 * LocalDateをDATEに変換し設定する。
	 */
	private void setDate(PreparedStatement stmt, int index, java.time.LocalDate date) throws SQLException {
		if (date != null) {
			stmt.setDate(index, Date.valueOf(date));
		} else {
			stmt.setNull(index, Types.DATE);
		}
	}

	/**
	 * 0以下をNULL扱いにした整数設定を行う。
	 */
	private void setNullableInt(PreparedStatement stmt, int index, int value) throws SQLException {
		if (value > 0) {
			stmt.setInt(index, value);
		} else {
			stmt.setNull(index, Types.INTEGER);
		}
	}
}
