package ikzm_jhm_dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ikzm_jhm_dto.Post;

public class PostDAO {

	public Post searchPostById(String target) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	//
	public ArrayList<Post> searchPostListByUserId(int userId) {

	}

	public void deletePost(int postId) {
		// TODO 自動生成されたメソッド・スタブ

	}

	//POSTテーブルからユーザーIDと求人番号を使用してPostリストを取得する
	public List<Post> searchPostByRecruitmentNo(int userId, String substring) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	//POSTテーブルからユーザーIDと日付を使用してPostリストを取得する
	public List<Post> searchPostByExamDate(int userId, LocalDate date) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public List<Post> searchPostByKeyword(int userId, String term) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public List<Post> findAll() {
		/*SQLクエリ
		SELECT * FROM Posts 
		WHERE is_deleted = false  -- 論理削除されていないもの
		AND exam_date >= DATE_SUB(CURDATE(), INTERVAL 3 YEAR) -- 過去3年以内
		ORDER BY created_at DESC; -- 作成日時の降順（新しい順）
		 */
	}

	public List<Post> searchPostByExamDate(LocalDate date) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public List<Post> searchPostByRecruitmentNo(String substring) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public List<Post> searchPostByKeyword(String term) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public int insertPost(Post post) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
