package ikzm_jhm_viewmodel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 検索結果一覧画面全体のビューモデル。
 * 複数の結果アイテム（Summary）と、ページング情報などを保持します。
 */
public class SearchResultViewModel {

	// 検索結果のリスト
	private List<Summary> reports;

	// 必要に応じてページング情報などをここに追加
	// private int totalCount;
	// private int currentPage;

	public SearchResultViewModel(List<Summary> reports) {
		this.reports = reports;
	}

	public List<Summary> getReports() {
		return reports;
	}

	//1件分の概要情報を保持する内部クラス

	public static class Summary {
		private int postId;
		private int userId;
		private int departmentId;
		private int methodId;
		private int recruitmentNo;
		private String companyName;
		private LocalDate examDate;
		private List<Integer> selectedExamIds;
		private Map<Integer, String> detailTexts;

		

		public Summary(int postId, int userId, int departmentId, int methodId, int recruitmentNo, String companyName,
				LocalDate examDate, List<Integer> selectedExamIds, Map<Integer, String> detailTexts) {
			super();
			this.postId = postId;
			this.userId = userId;
			this.departmentId = departmentId;
			this.methodId = methodId;
			this.recruitmentNo = recruitmentNo;
			this.companyName = companyName;
			this.examDate = examDate;
			this.selectedExamIds = selectedExamIds;
			this.detailTexts = detailTexts;
		}

		// --- Getters ---
		public int getPostId() {
			return postId;
		}

		public String getCompanyName() {
			return companyName;
		}

		public LocalDate getExamDate() {
			return examDate;
		}

		public List<Integer> getSelectedExamIds() {
			return selectedExamIds;
		}

		public Map<Integer, String> getDetailTexts() {
			return detailTexts;
		}
		// ... その他のGetterも必要に応じて追加 ...
	}
}