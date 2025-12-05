package ikzm_jhm_action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ikzm_jhm_dao.DepartmentDAO;
import ikzm_jhm_dao.ExamContentDAO;
import ikzm_jhm_dao.PostDAO;
import ikzm_jhm_dao.PostDetailDAO;
import ikzm_jhm_dao.PostExamSelectionDAO;
import ikzm_jhm_dao.SubmissionMethodDAO;
import ikzm_jhm_dao.UserDAO;
import ikzm_jhm_dto.Department;
import ikzm_jhm_dto.ExamContent;
import ikzm_jhm_dto.Post;
import ikzm_jhm_dto.PostDetail;
import ikzm_jhm_dto.SubmissionMethod;
import ikzm_jhm_dto.User;
import ikzm_jhm_model.PostForm;
import ikzm_jhm_utils.ModelConverter;
import ikzm_jhm_viewmodel.PostViewModel;
import ikzm_jhm_viewmodel.SearchResultViewModel;

/**
 * 投稿データに関する一覧取得や詳細取得、検索処理をまとめたアクションクラス。
 * DAO 呼び出しと ViewModel 変換の中継役を担う。
 */
public class PostAction {

	/**
	 * 投稿一覧を取得し {@link SearchResultViewModel} に変換する。
	 *
	 * @return 投稿一覧のビューモデル
	 * @throws Exception DAO からの例外を透過
	 */
	public SearchResultViewModel getPostList() throws Exception {

		PostDAO dao = new PostDAO();

		List<Post> posts = dao.findAll();
		return buildSearchResultViewModel(posts);
	}

	/**
	 * 編集用に投稿フォームを取得する。
	 * 投稿が存在しない、もしくは他ユーザーの投稿の場合は null を返す。
	 *
	 * @param postId 投稿 ID
	 * @param userId 操作ユーザー ID
	 * @return 編集用フォーム、または null
	 * @throws Exception DAO からの例外を透過
	 */
	public PostForm getPostFormForEdit(int postId, int userId) throws Exception {

		PostDAO postDao = new PostDAO();

		Post post = postDao.searchPostById(postId);

		if (post == null || post.getUserId() != userId) {
			// データがない、または他人なら弾く
			return null;
		}

		// 関連データを収集する
		PostDetailDAO detailDao = new PostDetailDAO();
		PostDetail detail = detailDao.findByPostId(postId);

		PostExamSelectionDAO selectionDao = new PostExamSelectionDAO();
		post.setExamSelection(selectionDao.findByPostId(postId));

		return ModelConverter.toPostForm(post, detail);
	}

	/**
	 * キーワードに応じた投稿検索を行う。
	 * プレフィックス #/@ に応じて検索条件を切り替える。
	 *
	 * @param searchWord 画面からの検索語
	 * @return 検索結果のビューモデル
	 * @throws Exception DAO 層の例外
	 */
	public SearchResultViewModel searchPosts(String searchWord) throws Exception {
		PostDAO dao = new PostDAO();
		List<Post> posts = null;

		if (searchWord == null || searchWord.isBlank()) {
			// 全件取得
			return getPostList();
		}

		// 検索文字列から空白文字を取り除く
		String term = searchWord.trim();

		try {
			if (term.startsWith("#")) {
				// #求人番号検索
				int termInt = Integer.parseInt(term.substring(1));
				posts = dao.searchPostByRecruitmentNo(termInt);

			} else if (term.startsWith("@")) {
				// 日付検索
				String dateStr = term.substring(1);
				LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				posts = dao.searchPostByExamDate(date);

			} else {
				// 通常検索
				posts = dao.searchPostByKeyword(term);

			}
		} catch (NumberFormatException | java.time.format.DateTimeParseException e) {
			posts = List.of();
		}

		return buildSearchResultViewModel(posts);
	}

	/**
	 * 投稿詳細を取得し、表示用 {@link PostViewModel} に変換する。
	 *
	 * @param postId 投稿 ID
	 * @return 投稿詳細ビューモデル。存在しない場合は null
	 * @throws Exception DAO 層の例外
	 */
	public PostViewModel getPostDetails(int postId) throws Exception {

		PostDAO postDao = new PostDAO();
		PostDetailDAO detailDao = new PostDetailDAO();
		PostExamSelectionDAO selectionDao = new PostExamSelectionDAO();
		UserDAO userDao = new UserDAO();
		DepartmentDAO deptDao = new DepartmentDAO();
		SubmissionMethodDAO methodDao = new SubmissionMethodDAO();
		ExamContentDAO examContentDao = new ExamContentDAO();

		Post post = postDao.searchPostById(postId);

		if (post == null) {
			return null;
		}

		// 詳細情報を取得
		PostDetail detail = detailDao.findByPostId(postId);

		// 投稿情報に基づく
		post.setExamSelection(selectionDao.findByPostId(postId));

		String posterName = "匿名";
		if (!post.isAnonymous()) {
			User user = userDao.findById(post.getUserId());
			if (user != null) {
				posterName = user.getName();
			}
		}

		// 学科名を取得
		Department dept = deptDao.findById(post.getDepartmentId());
		String deptName = (dept != null) ? dept.getDepartmentName() : "不明";

		// 応募方法名を取得
		SubmissionMethod method = methodDao.findById(post.getMethodId());
		String methodName = (method != null) ? method.getMethodName() : "不明";

		Map<Integer, String> examCategoryMap = new HashMap<>();
		Map<Integer, String> examNameMap = new HashMap<>();

		List<ExamContent> allContents = examContentDao.findAll();
		if (allContents != null) {
			for (ExamContent c : allContents) {
				examCategoryMap.put(c.getContentId(), c.getContentCategory());
				examNameMap.put(c.getContentId(), c.getContentName());
			}
		}

		return ModelConverter.toPostViewModel(post, detail, deptName, methodName, posterName, examCategoryMap,
				examNameMap);
	}

	/**
	 * ログインユーザー自身の投稿一覧を検索する。
	 *
	 * @param userId     ログインユーザー ID
	 * @param searchWord 検索語（任意）
	 * @return 投稿一覧ビューモデル
	 * @throws Exception DAO 層の例外
	 */
	public SearchResultViewModel getMyReportList(int userId, String searchWord) throws Exception {

		PostDAO dao = new PostDAO();
		List<Post> posts = null;

		if (searchWord == null || searchWord.isBlank()) {
			// 全件取得
			posts = dao.searchPostListByUserId(userId);
		} else {
			// 検索取得

			// 検索文字列から空白文字を取り除く
			String term = searchWord.trim();

			try {
				if (term.startsWith("#")) {
					// 求人番号検索
					int recruitmentNo = Integer.parseInt(term.substring(1));
					posts = dao.searchPostByRecruitmentNo(userId, recruitmentNo);

				} else if (term.startsWith("@")) {
					// 試験日検索
					String prefix = term.substring(1);
					LocalDate date = LocalDate.parse(prefix, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					posts = dao.searchPostByExamDate(userId, date);

				} else {
					// プレフィックスなしの検索を行う
					posts = dao.searchPostByKeyword(userId, term);

				}
			} catch (NumberFormatException | java.time.format.DateTimeParseException e) {
				posts = List.of();
			}
		}

		// Post DTOリストを ViewModel に変換して返す
		return buildSearchResultViewModel(posts);
	}

	/**
	 * 投稿 DTO のリストから {@link SearchResultViewModel} を構築する。
	 *
	 * @param posts 投稿 DTO リスト
	 * @return ビューモデル
	 * @throws Exception 必要な付帯情報取得時の例外
	 */
	private SearchResultViewModel buildSearchResultViewModel(List<Post> posts) throws Exception {
		if (posts == null || posts.isEmpty()) {
			return new SearchResultViewModel(Collections.<SearchResultViewModel.Summary>emptyList());
		}
		Map<Integer, String> departmentCourseMap = loadDepartmentCourseMap();
		Map<Integer, PostDetail> postDetailMap = loadPostDetailMap(posts);
		return ModelConverter.toSearchResultViewModel(posts, departmentCourseMap, postDetailMap);
	}

	/**
	 * 学科マスタを読み込み、departmentId → 学科名のマップを生成する。
	 *
	 * @return 学科名マップ
	 * @throws Exception DAO からの例外
	 */
	private Map<Integer, String> loadDepartmentCourseMap() throws Exception {
		Map<Integer, String> departmentMap = new HashMap<>();
		DepartmentDAO deptDao = new DepartmentDAO();
		List<Department> departments = deptDao.findAll();
		if (departments != null) {
			for (Department department : departments) {
				departmentMap.put(department.getDepartmentId(), department.getDepartmentName());
			}
		}
		return departmentMap;
	}

	/**
	 * 投稿 ID リストから投稿詳細情報を一括で取得する。
	 *
	 * @param posts 投稿リスト
	 * @return postId → PostDetail のマップ
	 * @throws Exception DAO 層の例外
	 */
	private Map<Integer, PostDetail> loadPostDetailMap(List<Post> posts) throws Exception {
		if (posts == null || posts.isEmpty()) {
			return Collections.emptyMap();
		}
		List<Integer> postIds = new ArrayList<>();
		for (Post post : posts) {
			postIds.add(post.getPostId());
		}
		PostDetailDAO detailDao = new PostDetailDAO();
		return detailDao.findByPostIds(postIds);
	}
}
