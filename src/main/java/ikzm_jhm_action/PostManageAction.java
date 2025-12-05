package ikzm_jhm_action;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import ikzm_jhm_dao.ConnectionDAO;
import ikzm_jhm_dao.PostDAO;
import ikzm_jhm_dao.PostDetailDAO;
import ikzm_jhm_dao.PostExamSelectionDAO;
import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.PostDetail;
import ikzm_jhm_dto.PostExamSelection;
import ikzm_jhm_model.PostForm;
import ikzm_jhm_utils.ModelConverter;

/**
 * 投稿の登録・更新・削除といったトランザクションを司るアクションクラス。
 * DAO 呼び出しとフォーム変換をまとめ、整合性を担保する。
 */
public class PostManageAction {

	private final ConnectionDAO connectionDao = new ConnectionDAO();

	/**
	 * 新規投稿を登録する。関連テーブルへの登録も含めて一つのトランザクションで処理する。
	 *
	 * @param form   入力フォーム
	 * @param userId 投稿者ユーザー ID
	 * @return 登録成功時 true
	 * @throws Exception DAO 操作またはトランザクション失敗
	 */
	public boolean createPost(PostForm form, int userId) throws Exception {

		PostDAO postDao = new PostDAO();
		Connection conn = null;
		boolean hasError = false;

		try {
			conn = connectionDao.getConnection();
			conn.setAutoCommit(false);

			// フォーム型をDTOに変換
			Post post = ModelConverter.toPostDto(form, userId);

			// 作成日時の取得
			post.setCreateAt(LocalDateTime.now());
			post.setUpdatedAt(LocalDateTime.now());

			// POSTの有効状態を設定
			post.setActive(true);

			// postテーブルへINSERT
			int newPostId = postDao.insertPost(post, conn);
			if (newPostId == 0) {
				throw new Exception("DB-0002");
			}

			PostDetail detail = ModelConverter.toPostDetailDto(form);
			detail.setPostId(newPostId);

			PostDetailDAO detailDao = new PostDetailDAO();
			if (!detailDao.insert(detail, conn)) {
				throw new Exception("DB-0011");
			}

			List<PostExamSelection> selections = ModelConverter.toSelectionList(form);

			if (selections != null && !selections.isEmpty()) {
				for (PostExamSelection sel : selections) {
					sel.setPostId(newPostId);
				}

				PostExamSelectionDAO selectionDao = new PostExamSelectionDAO();
				if (!selectionDao.insertList(selections, conn)) {
					throw new Exception("DB-0021");
				}
			}
			conn.commit();
			return true;
		} catch (Exception e) {
			hasError = true;
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException rollbackEx) {
					throw new Exception("APP-2000");
				}
			}
			throw new Exception("APP-2000");
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException closeEx) {
					if (!hasError) {
						throw new Exception("APP-2002");
					}
				}
			}
		}

	}

	/**
	 * 既存投稿を更新する。詳細や試験選択も含めて全体を更新する。
	 *
	 * @param form   入力フォーム
	 * @param userId 投稿者ユーザー ID
	 * @return 更新成功時 true（投稿が他人のものなどの場合は false）
	 * @throws Exception DAO 操作またはトランザクション失敗
	 */
	public boolean updatePost(PostForm form, int userId) throws Exception {

		// DAOインスタンスを生成
		PostDAO postDao = new PostDAO();
		PostDetailDAO detailDao = new PostDetailDAO();
		PostExamSelectionDAO selectionDao = new PostExamSelectionDAO();

		int postId = form.getPostId();

		// 該当する投稿が存在するか確認
		Post existing = postDao.searchPostById(postId);
		if (existing == null || existing.getUserId() != userId) {
			return false;
		}

		Connection conn = null;
		boolean hasError = false;
		try {
			conn = connectionDao.getConnection();
			conn.setAutoCommit(false);

			// Postテーブルの更新
			Post post = ModelConverter.toPostDto(form, userId);
			post.setUpdatedAt(LocalDateTime.now());
			if (!postDao.update(post, conn)) {
				throw new Exception("DB-0003");
			}

			// Detailテーブルの更新
			PostDetail detail = ModelConverter.toPostDetailDto(form);
			detail.setPostId(postId);
			if (!detailDao.update(detail, conn)) {
				throw new Exception("DB-0012");
			}

			// ExamSelectionのレコードは一度削除して追加する
			selectionDao.deleteByPostId(postId, conn);

			List<PostExamSelection> selections = ModelConverter.toSelectionList(form);
			if (selections != null && !selections.isEmpty()) {
				for (PostExamSelection sel : selections) {
					sel.setPostId(postId);
				}
				if (!selectionDao.insertList(selections, conn)) {
					throw new Exception("DB-0021");
				}
			}

			conn.commit();
			return true;
		} catch (Exception e) {
			hasError = true;
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException rollbackEx) {
					throw new Exception("APP-2001");
				}
			}
			throw new Exception("APP-2001");
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException closeEx) {
					if (!hasError) {
						throw new Exception("APP-2002");
					}
				}
			}
		}
	}

	/**
	 * 投稿レコードを論理削除する。
	 *
	 * @param postId 投稿 ID
	 * @throws Exception DAO 層での失敗
	 */
	public void deletePost(int postId) throws Exception {
		PostDAO postDAO = new PostDAO();
		postDAO.deletePost(postId);
	}

	/**
	 * TODO: 投稿検索処理。現在は未実装のため null を返す。
	 *
	 * @return いつでも null
	 */
	public Post searchPostAction() {
		return null;
	}

}