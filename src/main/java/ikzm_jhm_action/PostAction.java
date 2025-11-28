package ikzm_jhm_action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ikzm_jhm_dao.PostDAO;
import ikzm_jhm_dto.Post;
import ikzm_jhm_utils.ModelConverter;
import ikzm_jhm_viewmodel.PostReportViewModel;
import ikzm_jhm_viewmodel.SearchResultViewModel;

public class PostAction {

	//一覧/検索リスト取得、ViewModel変換
	public SearchResultViewModel getPostList() {

		PostDAO dao = new PostDAO();

		List<Post> posts = dao.findAll();
		return ModelConverter.toSearchResultViewModel(posts);
	}

	//ユーザーから受け取った検索情報をもとに必要なDAOを呼び出す
	public SearchResultViewModel searchPosts(String searchWord) {
		PostDAO dao = new PostDAO();
		List<Post> posts = null;

		if (searchWord == null || searchWord.isBlank()) {
			//全件取得
			return getPostList();
		}

		//検索文字列から空白文字を取り除く
		String term = searchWord.trim();

		if (term.startsWith("#")) {
			// #求人番号検索
			posts = dao.searchPostByRecruitmentNo(term.substring(1));

		} else if(term.startsWith("@")){
			//日付検索
			try {
				String dateStr = term.substring(1);
				LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				posts = dao.searchPostByExamDate(date);
			}catch(Exception e) {
				
			}
			
		}else {
			//通常検索
			posts = dao.searchPostByKeyword(term);
			
		}
		
		return ModelConverter.toSearchResultViewModel(posts);
	}

	//投稿の詳細データ取得、ViewModel変換、匿名性処理
	public PostReportViewModel getPostDetails(int postId) {
	}

	public List<PostReportViewModel> getMyPostList(int userId) {

	}

	//特定のユーザーの投稿を全て取得
	public SearchResultViewModel getMyReportList(int userId, String searchWord) {

		PostDAO dao = new PostDAO();
		List<Post> posts = null;

		if (searchWord == null || searchWord.isBlank()) {
			//全件取得
			posts = dao.searchPostListByUserId(userId);
		} else {
			//検索取得

			//検索文字列から空白文字を取り除く
			String term = searchWord.trim();

			if (term.startsWith("#")) {
				//求人番号検索
				posts = dao.searchPostByRecruitmentNo(userId, term.substring(1));

			} else if (term.startsWith("@")) {
				//試験日検索
				String prefix = term.substring(1);
				LocalDate date = LocalDate.parse(prefix, DateTimeFormatter.ofPattern("yyyyMMdd"));
				posts = dao.searchPostByExamDate(userId, date);

			} else {
				//プレフィックスなしの検索を行う
				posts = dao.searchPostByKeyword(userId, term);

			}
		}

		// Post DTOリストを ViewModel に変換して返す
		return ModelConverter.toSearchResultViewModel(posts);
	}
}
