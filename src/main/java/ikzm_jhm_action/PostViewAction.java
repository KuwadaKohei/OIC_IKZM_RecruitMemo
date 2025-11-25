package ikzm_jhm_action;

import java.util.ArrayList;

import ikzm_jhm_dao.PostDAO;
import ikzm_jhm_dto.Post;

public class PostViewAction {
	//指定・操作投稿の投稿者本人であるかを確認
	public boolean checkUserAuth(int userId, int postId) {
		return false;
		
	}
	
	public ArrayList<Post> myReports(int userId){
		
		PostDAO postDAO = new PostDAO();
		return postDAO.searchPostListByUserId(userId);
	}

	public ArrayList<Post> myReportsSearch(int userId,String word) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
