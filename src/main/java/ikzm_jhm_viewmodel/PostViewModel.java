package ikzm_jhm_viewmodel;

import java.time.LocalDate;
import java.util.List;

/**
 * 就活報告書の詳細画面表示に特化したビューモデル。
 * Posts, Departments, SubmissionMethods, PostDetails, PostExamSelectionsの情報を集約する。
 */
public class PostViewModel {

	//Postsコア情報と関連情報 (Posts, Departments, SubmissionMethods)
	private int postId;
	private String companyName;
	private LocalDate examDate;
	private String venueAddress;
	private String departmentName; // Departmentsから取得
	private String submissionMethodName; // SubmissionMethodsから取得
	private int recruitmentNo;
	private int grade;
	private String posterName; // Usersから取得 (isAnonymousフラグに応じて「匿名」または実名)
	//PostDetails情報
	private LocalDate resultDate;
	private int scheduledHires;
	private int totalApplicants;
	private String adviceText; // 受験のアドバイス
	//試験内容の選択結果 (ExamContent, PostExamSelections)
	private List<SelectedExamItem> selectedExams;// 選択された試験項目のリスト。JSP側でループさせて表示する。

	public PostViewModel(int postId, String companyName, LocalDate examDate, String venueAddress,
			String departmentName, String submissionMethodName, int recruitmentNo,
			int grade, String posterName, LocalDate resultDate, int scheduledHires,
			int totalApplicants, String adviceText, List<SelectedExamItem> selectedExams) {

		this.postId = postId;
		this.companyName = companyName;
		this.examDate = examDate;
		this.venueAddress = venueAddress;
		this.departmentName = departmentName;
		this.submissionMethodName = submissionMethodName;
		this.recruitmentNo = recruitmentNo;
		this.grade = grade;
		this.posterName = posterName;
		this.resultDate = resultDate;
		this.scheduledHires = scheduledHires;
		this.totalApplicants = totalApplicants;
		this.adviceText = adviceText;
		this.selectedExams = selectedExams;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public LocalDate getExamDate() {
		return examDate;
	}

	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}

	public String getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getSubmissionMethodName() {
		return submissionMethodName;
	}

	public void setSubmissionMethodName(String submissionMethodName) {
		this.submissionMethodName = submissionMethodName;
	}

	public int getRecruitmentNo() {
		return recruitmentNo;
	}

	public void setRecruitmentNo(int recruitmentNo) {
		this.recruitmentNo = recruitmentNo;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getPosterName() {
		return posterName;
	}

	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	public LocalDate getResultDate() {
		return resultDate;
	}

	public void setResultDate(LocalDate resultDate) {
		this.resultDate = resultDate;
	}

	public int getScheduledHires() {
		return scheduledHires;
	}

	public void setScheduledHires(int scheduledHires) {
		this.scheduledHires = scheduledHires;
	}

	public int getTotalApplicants() {
		return totalApplicants;
	}

	public void setTotalApplicants(int totalApplicants) {
		this.totalApplicants = totalApplicants;
	}

	public String getAdviceText() {
		return adviceText;
	}

	public void setAdviceText(String adviceText) {
		this.adviceText = adviceText;
	}

	public List<SelectedExamItem> getSelectedExams() {
		return selectedExams;
	}

	public void setSelectedExams(List<SelectedExamItem> selectedExams) {
		this.selectedExams = selectedExams;
	}

	public static class SelectedExamItem {
		private String categoryName; // ExamContent.content_category
		private String itemName; // ExamContent.content_name
		private String detailText; // PostExamSelections.detail_text (専門、テーマ、人数など)

		// コンストラクタ
		public SelectedExamItem(String categoryName, String itemName, String detailText) {
			this.categoryName = categoryName;
			this.itemName = itemName;
			this.detailText = detailText;
		}

		// ゲッターのみ (データ表示用)
		public String getCategoryName() {
			return categoryName;
		}

		public String getItemName() {
			return itemName;
		}

		public String getDetailText() {
			return detailText;
		}
	}
}