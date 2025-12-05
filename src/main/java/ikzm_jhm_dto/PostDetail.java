package ikzm_jhm_dto;

import java.time.LocalDate;

/**
 * 投稿詳細情報（結果日やアドバイス等）を保持する DTO。
 */
public class PostDetail {
	int postId;
	LocalDate resultDate;
	int scheduled_hires;
	int totalApplicants;
	String adviceText;

	/**
	 * すべてのフィールドを指定するコンストラクタ。
	 */
	public PostDetail(int postId, LocalDate resultDate, int scheduled_hires, int totalApplicants, String adviceText) {
		super();
		this.postId = postId;
		this.resultDate = resultDate;
		this.scheduled_hires = scheduled_hires;
		this.totalApplicants = totalApplicants;
		this.adviceText = adviceText;
	}

	/**
	 * デフォルトコンストラクタ。
	 */
	public PostDetail() {
	}

	/**
	 * 投稿IDを返す。
	 */
	public int getPostId() {
		return postId;
	}

	/**
	 * 投稿IDを設定する。
	 */
	public void setPostId(int postId) {
		this.postId = postId;
	}

	/**
	 * 合否結果日を返す。
	 */
	public LocalDate getResultDate() {
		return resultDate;
	}

	/**
	 * 合否結果日を設定する。
	 */
	public void setResultDate(LocalDate resultDate) {
		this.resultDate = resultDate;
	}

	/**
	 * 予定採用人数を返す。
	 */
	public int getScheduled_hires() {
		return scheduled_hires;
	}

	/**
	 * 予定採用人数を設定する。
	 */
	public void setScheduled_hires(int scheduled_hires) {
		this.scheduled_hires = scheduled_hires;
	}

	/**
	 * 応募総数を返す。
	 */
	public int getTotalApplicants() {
		return totalApplicants;
	}

	/**
	 * 応募総数を設定する。
	 */
	public void setTotalApplicants(int totalApplicants) {
		this.totalApplicants = totalApplicants;
	}

	/**
	 * アドバイス文を返す。
	 */
	public String getAdviceText() {
		return adviceText;
	}

	/**
	 * アドバイス文を設定する。
	 */
	public void setAdviceText(String adviceText) {
		this.adviceText = adviceText;
	}

}
