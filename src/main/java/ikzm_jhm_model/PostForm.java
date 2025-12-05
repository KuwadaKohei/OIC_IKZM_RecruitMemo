package ikzm_jhm_model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

	/**
	 * デフォルトコンストラクタ。
	 */
	public PostForm() {
	}

	// ゲッター・セッター群
	/**
	 * 投稿IDを返す。
	 *
	 * @return 投稿ID
	 */
	public int getPostId() {
		return postId;
	}

	/**
	 * 投稿IDを設定する。
	 *
	 * @param postId 投稿ID
	 */
	public void setPostId(int postId) {
		this.postId = postId;
	}

	/**
	 * 事業所名を返す。
	 *
	 * @return 事業所名
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 事業所名を設定する。
	 *
	 * @param companyName 事業所名
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 受験日文字列を返す。
	 *
	 * @return yyyy-MM-dd形式の文字列
	 */
	public String getExamDateStr() {
		return examDateStr;
	}

	/**
	 * 受験日文字列を設定する。
	 *
	 * @param examDateStr yyyy-MM-dd形式の文字列
	 */
	public void setExamDateStr(String examDateStr) {
		this.examDateStr = examDateStr;
	}

	/**
	 * 会場住所を返す。
	 *
	 * @return 会場住所
	 */
	public String getVenueAddress() {
		return venueAddress;
	}

	/**
	 * 会場住所を設定する。
	 *
	 * @param venueAddress 会場住所
	 */
	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
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
	 * 学科IDを設定する。
	 *
	 * @param departmentId 学科ID
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * 応募方法IDを返す。
	 *
	 * @return 応募方法ID
	 */
	public int getMethodId() {
		return methodId;
	}

	/**
	 * 応募方法IDを設定する。
	 *
	 * @param methodId 応募方法ID
	 */
	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	/**
	 * 求人票番号文字列を返す。
	 *
	 * @return 求人票番号（文字列）
	 */
	public String getRecruitmentNoStr() {
		return recruitmentNoStr;
	}

	/**
	 * 求人票番号文字列を設定する。
	 *
	 * @param recruitmentNoStr 求人票番号（文字列）
	 */
	public void setRecruitmentNoStr(String recruitmentNoStr) {
		this.recruitmentNoStr = recruitmentNoStr;
	}

	/**
	 * 学年を返す。
	 *
	 * @return 学年
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * 学年を設定する。
	 *
	 * @param grade 学年
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * 匿名投稿フラグを返す。
	 *
	 * @return 匿名ならtrue
	 */
	public boolean isAnonymous() {
		return isAnonymous;
	}

	/**
	 * 匿名投稿フラグを設定する。
	 *
	 * @param isAnonymous trueなら匿名
	 */
	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	/**
	 * 結果日文字列を返す。
	 *
	 * @return 結果日（文字列）
	 */
	public String getResultDateStr() {
		return resultDateStr;
	}

	/**
	 * 結果日文字列を設定する。
	 *
	 * @param resultDateStr 結果日（文字列）
	 */
	public void setResultDateStr(String resultDateStr) {
		this.resultDateStr = resultDateStr;
	}

	/**
	 * 予定採用人数文字列を返す。
	 *
	 * @return 予定採用人数（文字列）
	 */
	public String getScheduledHiresStr() {
		return scheduledHiresStr;
	}

	/**
	 * 予定採用人数文字列を設定する。
	 *
	 * @param scheduledHiresStr 予定採用人数
	 */
	public void setScheduledHiresStr(String scheduledHiresStr) {
		this.scheduledHiresStr = scheduledHiresStr;
	}

	/**
	 * 応募者数文字列を返す。
	 *
	 * @return 応募者数（文字列）
	 */
	public String getTotalApplicantsStr() {
		return totalApplicantsStr;
	}

	/**
	 * 応募者数文字列を設定する。
	 *
	 * @param totalApplicantsStr 応募者数
	 */
	public void setTotalApplicantsStr(String totalApplicantsStr) {
		this.totalApplicantsStr = totalApplicantsStr;
	}

	/**
	 * アドバイス文を返す。
	 *
	 * @return アドバイス文
	 */
	public String getAdviceText() {
		return adviceText;
	}

	/**
	 * アドバイス文を設定する。
	 *
	 * @param adviceText アドバイス文
	 */
	public void setAdviceText(String adviceText) {
		this.adviceText = adviceText;
	}

	/**
	 * 選択済み試験IDリストを返す。
	 *
	 * @return 試験IDのリスト
	 */
	public List<Integer> getSelectedExamIds() {
		return selectedExamIds;
	}

	/**
	 * 選択済み試験IDリストを設定する。
	 *
	 * @param selectedExamIds 試験IDのリスト
	 */
	public void setSelectedExamIds(List<Integer> selectedExamIds) {
		this.selectedExamIds = selectedExamIds;
	}

	/**
	 * 試験詳細テキストのマップを返す。
	 *
	 * @return key=contentId, value=詳細テキスト
	 */
	public Map<Integer, String> getExamDetails() {
		return examDetails;
	}

	/**
	 * 試験詳細テキストのマップを設定する。
	 *
	 * @param examDetails 詳細テキストマップ
	 */
	public void setExamDetails(Map<Integer, String> examDetails) {
		this.examDetails = examDetails;
	}

	/**
	 * 入力内容のバリデーションを行う。
	 * 
	 * @return エラーコードのリスト (エラーがない場合は空のリスト)
	 */
	public List<String> validate() {
		List<String> errors = new ArrayList<>();

		// 会社名
		if (companyName == null || companyName.isBlank()) {
			errors.add("FORM-3000");
		} else if (companyName.length() > 100) {// DBの定義に合わせて修正予定
			errors.add("FORM-3001");
		}

		// 受験日
		if (examDateStr == null || examDateStr.isBlank()) {
			errors.add("FORM-3010");
		} else {
			try {
				LocalDate.parse(examDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			} catch (DateTimeParseException e) {
				errors.add("FORM-3011");
			}
		}

		// 求人票番号
		if (recruitmentNoStr != null && !recruitmentNoStr.isBlank()) {
			if (!recruitmentNoStr.matches("\\d+")) {
				errors.add("FORM-3020");
			}
		}

		// マスタ選択系
		if (departmentId <= 0) {
			errors.add("FORM-3030");
		}
		if (methodId <= 0) {
			errors.add("FORM-3031");
		}
		if (grade <= 0) {
			errors.add("FORM-3032");
		}

		// 試験内容
		if (selectedExamIds == null || selectedExamIds.isEmpty()) {
			errors.add("FORM-3040");
		}

		return errors;
	}
}