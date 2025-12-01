package ikzm_jhm_action;

import java.time.LocalDateTime;
import java.util.List;

import ikzm_jhm_dao.PostDAO;
import ikzm_jhm_dao.PostDetailDAO;
import ikzm_jhm_dao.PostExamSelectionDAO;
import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.PostDetail;
import ikzm_jhm_dto.PostExamSelection;
import ikzm_jhm_model.PostForm;
import ikzm_jhm_utils.ModelConverter;

public class PostManageAction {

	//新規投稿処理（複数DAO呼び出し、トランザクション管理)
	public boolean createPost(PostForm form, int userId) {

		PostDAO postDao = new PostDAO();

		try {

			//フォーム型をDTOに変換
			Post post = ModelConverter.toPostDto(form, userId);

			//作成日時の取得
			post.setCreateAt(LocalDateTime.now());
			post.setUpdatedAt(LocalDateTime.now());

			//POSTの有効状態を設定
			post.setActive(true);

			//postテーブルへINSERT
			int newPostId = postDao.insertPost(post);

			if (newPostId == 0) {
				//ID取得失敗
				return false;
			}

			PostDetail detail = ModelConverter.toPostDetailDto(form);
			detail.setPostId(newPostId);

			List<PostExamSelection> selections = ModelConverter.toSelectionList(form);

			if (selections != null && !selections.isEmpty()) {
				for (PostExamSelection sel : selections) {
					sel.setPostId(newPostId);
				}

				SelectionDAO selectionDao = new SelectionDAO();
				selectionDao.insertList(selections);
			}

			return true;
		} catch (Exception e) {

			//追加処理に失敗(処理エラー)
			return false;
		}

	}

	public PostForm getPostFormForEdit(int postId, int userId) {

		PostDAO postDao = new PostDAO();

		Post post = postDao.searchPostById(postId);

		if (post == null || post.getUserId() != userId) {
			//データがない、または他人なら弾く
			return null;
		}

		//関連データを収集する
		PostDetailDAO detailDao = new PostDetailDAO();
		PostDetail detail = detailDao.findByPostId(postId);

		return ModelConverter.toPostForm(post, detail);
	}

	//投稿編集処理（旧データ削除・新規挿入含むトランザクション)
	public boolean updatePost(PostForm form, int postId) {
		
		//DAOインスタンスを生成
		PostDAO postDao = new PostDAO();
		PostDetailDAO detailDao = new PostDetailDAO();
		PostExamSelectionDAO selectionDao = new PostExamSelectionDAO();
		
		try {
			int postId = form.getPostId();
			
			
		}
	}

	//投稿レコードを削除（トランザクション管理）
	public void deletePost(int postId) {
		PostDAO postDAO = new PostDAO();
		postDAO.deletePost(postId);
	}

	public Post searchPostAction() {
		return null;
	}

}