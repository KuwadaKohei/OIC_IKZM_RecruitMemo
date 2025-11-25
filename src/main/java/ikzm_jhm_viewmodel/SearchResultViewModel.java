package ikzm_jhm_viewmodel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/*
 * クラス説明
 * 投稿検索機能の結果の一覧表示に特化したビューモデル
 * 詳細情報などを除く概要情報のみを格納する
 */
public class SearchResultViewModel {
	private int postId; //ポストID
	private int userId; //投稿者ID
	private int departmentId; //学科ID
	private int methodId; //応募方法ID
	private int recruitmentNo; //求人番号ID
	private String companyName; //事業所名
	private LocalDate examDate; //受験日
	private String adviceTest; //受験のアドバイス
	private List<Integer> selectedExamIds; //チェックが入った試験項目IDのリスト
	private Map<Integer, String> detailTexts; //contentIdをキーとする、詳細記述（作文テーマ、専門など）のマップ

	public SearchResultViewModel(int postId, int userId, int departmentId, int methodId, int recruitmentNo,
			String companyName, LocalDate examDate, String adviceTest, List<Integer> selectedExamIds,
			Map<Integer, String> detailTexts) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.departmentId = departmentId;
		this.methodId = methodId;
		this.recruitmentNo = recruitmentNo;
		this.companyName = companyName;
		this.examDate = examDate;
		this.adviceTest = adviceTest;
		this.selectedExamIds = selectedExamIds;
		this.detailTexts = detailTexts;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getMethodId() {
		return methodId;
	}

	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	public int getRecruitmentNo() {
		return recruitmentNo;
	}

	public void setRecruitmentNo(int recruitmentNo) {
		this.recruitmentNo = recruitmentNo;
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

	public String getAdviceTest() {
		return adviceTest;
	}

	public void setAdviceTest(String adviceTest) {
		this.adviceTest = adviceTest;
	}

	public List<Integer> getSelectedExamIds() {
		return selectedExamIds;
	}

	public void setSelectedExamIds(List<Integer> selectedExamIds) {
		this.selectedExamIds = selectedExamIds;
	}

	public Map<Integer, String> getDetailTexts() {
		return detailTexts;
	}

	public void setDetailTexts(Map<Integer, String> detailTexts) {
		this.detailTexts = detailTexts;
	}

}
