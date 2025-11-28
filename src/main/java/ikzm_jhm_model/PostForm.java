package ikzm_jhm_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 投稿・編集フォームの入力データを保持するクラス。
 * View(JSP)とController/Action間のデータの橋渡しを行う。
 * 編集画面での「初期値入力」にも使用される。
 */
public class PostForm {

	// 編集時のみ使用 (新規時は0またはnull)
	private int postId;

	// 基本情報
	private String companyName;
	private String examDateStr; // HTML date inputからはStringで来る ("yyyy-MM-dd")
	private String venueAddress;
	private int departmentId;
	private int methodId;
	private String recruitmentNoStr; // 数値だが入力時はString扱いが安全 (#なしの数字)
	private int grade;
	private boolean isAnonymous;

	// 詳細情報
	private String resultDateStr;
	private String scheduledHiresStr;
	private String totalApplicantsStr;
	private String adviceText;

	// 試験内容 (チェックボックス等)
	// チェックされた ExamContent.contentId のリスト
	private List<Integer> selectedExamIds = new ArrayList<>();

	// 詳細記述 (Map<contentId, 詳細テキスト>)
	// 例: key=専門試験ID, value="Javaプログラミング"
	private Map<Integer, String> examDetails = new HashMap<>();

	public PostForm() {
	}

	// ゲッター・セッター群
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

	public String getExamDateStr() {
		return examDateStr;
	}

	public void setExamDateStr(String examDateStr) {
		this.examDateStr = examDateStr;
	}

	public String getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
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

	public String getRecruitmentNoStr() {
		return recruitmentNoStr;
	}

	public void setRecruitmentNoStr(String recruitmentNoStr) {
		this.recruitmentNoStr = recruitmentNoStr;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean isAnonymous() {
		return isAnonymous;
	}

	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public String getResultDateStr() {
		return resultDateStr;
	}

	public void setResultDateStr(String resultDateStr) {
		this.resultDateStr = resultDateStr;
	}

	public String getScheduledHiresStr() {
		return scheduledHiresStr;
	}

	public void setScheduledHiresStr(String scheduledHiresStr) {
		this.scheduledHiresStr = scheduledHiresStr;
	}

	public String getTotalApplicantsStr() {
		return totalApplicantsStr;
	}

	public void setTotalApplicantsStr(String totalApplicantsStr) {
		this.totalApplicantsStr = totalApplicantsStr;
	}

	public String getAdviceText() {
		return adviceText;
	}

	public void setAdviceText(String adviceText) {
		this.adviceText = adviceText;
	}

	public List<Integer> getSelectedExamIds() {
		return selectedExamIds;
	}

	public void setSelectedExamIds(List<Integer> selectedExamIds) {
		this.selectedExamIds = selectedExamIds;
	}

	public Map<Integer, String> getExamDetails() {
		return examDetails;
	}

	public void setExamDetails(Map<Integer, String> examDetails) {
		this.examDetails = examDetails;
	}
}