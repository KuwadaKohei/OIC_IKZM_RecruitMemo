package ikzm_jhm_viewmodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ikzm_jhm_dto.PostDetail;

/**
 * 検索結果一覧画面全体のビューモデル。
 * 複数の結果アイテム（Summary）と、ページング情報などを保持します。
 */
public class SearchResultViewModel {

	// 検索結果のリスト
	private List<Summary> reports;

	// ページング情報などをここに追加
	// private int totalCount;
	// private int currentPage;

	/**
	 * 検索結果リストを受け取るコンストラクタ。
	 *
	 * @param reports 検索結果概要リスト
	 */
	public SearchResultViewModel(List<Summary> reports) {
		this.reports = reports;
	}

	/**
	 * 検索結果の一覧を返す。
	 *
	 * @return 検索結果概要リスト
	 */
	public List<Summary> getReports() {
		return reports;
	}

	// 1件分の概要情報を保持する内部クラス

	/**
	 * 検索結果1件分の概要情報を保持する値オブジェクト。
	 */
	public static class Summary {
		private final int postId;
		private final int userId;
		private final int departmentId;
		private final String departmentCourseName;
		private final int recruitmentNo;
		private final String companyName;
		private final LocalDate examDate;
		private final LocalDateTime createdAt;
		private final LocalDateTime updatedAt;
		private final PostDetail postDetail;

		/**
		 * 1件分の概要情報を構築するコンストラクタ。
		 *
		 * @param postId               投稿ID
		 * @param userId               ユーザーID
		 * @param departmentId         学科ID
		 * @param departmentCourseName 学科・コース名
		 * @param recruitmentNo        求人票番号
		 * @param companyName          企業名
		 * @param examDate             試験日
		 * @param createdAt            作成日時
		 * @param updatedAt            更新日時
		 * @param postDetail           投稿詳細情報
		 */
		public Summary(
				int postId,
				int userId,
				int departmentId,
				String departmentCourseName,
				int recruitmentNo,
				String companyName,
				LocalDate examDate,
				LocalDateTime createdAt,
				LocalDateTime updatedAt,
				PostDetail postDetail) {
			this.postId = postId;
			this.userId = userId;
			this.departmentId = departmentId;
			this.departmentCourseName = departmentCourseName;
			this.recruitmentNo = recruitmentNo;
			this.companyName = companyName;
			this.examDate = examDate;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.postDetail = postDetail;
		}

		/**
		 * 投稿IDを返す。
		 *
		 * @return 投稿ID
		 */
		public int getPostId() {
			return postId;
		}

		/**
		 * 投稿者ユーザーIDを返す。
		 *
		 * @return 投稿者ユーザーID
		 */
		public int getUserId() {
			return userId;
		}

		/**
		 * 学科IDを返す。
		 *
		 * @return 学科ID
		 */
		public int getDepartmentId() {
			return departmentId;
		}

		/**
		 * 学科・コース名を返す。
		 *
		 * @return 学科・コース名
		 */
		public String getDepartmentCourseName() {
			return departmentCourseName;
		}

		/**
		 * 求人票番号を返す。
		 *
		 * @return 求人票番号
		 */
		public int getRecruitmentNo() {
			return recruitmentNo;
		}

		/**
		 * 受験企業名を返す。
		 *
		 * @return 受験企業名
		 */
		public String getCompanyName() {
			return companyName;
		}

		/**
		 * 試験日を返す。
		 *
		 * @return 試験日
		 */
		public LocalDate getExamDate() {
			return examDate;
		}

		/**
		 * 作成日時を返す。
		 *
		 * @return 作成日時
		 */
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		/**
		 * 更新日時を返す。
		 *
		 * @return 更新日時
		 */
		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		/**
		 * 投稿詳細を返す。
		 *
		 * @return 投稿詳細
		 */
		public PostDetail getPostDetail() {
			return postDetail;
		}
	}
}