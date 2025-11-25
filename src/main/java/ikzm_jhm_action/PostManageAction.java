package ikzm_jhm_action;

import ikzm_jhm_dao.PostDAO;
import ikzm_jhm_dto.Post;

public class PostManageAction {
	
	//新規投稿処理（複数DAO呼び出し、トランザクション管理)
	public int createPost(ReportForm form) {
		
	}

	//投稿編集処理（旧データ削除・新規挿入含むトランザクション)
	public void updatePost(ReportForm form) {

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