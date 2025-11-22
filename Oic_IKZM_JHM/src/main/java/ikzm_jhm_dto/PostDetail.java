package ikzm_jhm_dto;

import java.time.LocalDate;

public class PostDetail {
	int postId;
	LocalDate resultDate;
	int scheduled_hires;
	int totalApplicants;
	String adviceText;

	public PostDetail(int postId, LocalDate resultDate, int scheduled_hires, int totalApplicants, String adviceText) {
		super();
		this.postId = postId;
		this.resultDate = resultDate;
		this.scheduled_hires = scheduled_hires;
		this.totalApplicants = totalApplicants;
		this.adviceText = adviceText;
	}

	public PostDetail() {
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public LocalDate getResultDate() {
		return resultDate;
	}

	public void setResultDate(LocalDate resultDate) {
		this.resultDate = resultDate;
	}

	public int getScheduled_hires() {
		return scheduled_hires;
	}

	public void setScheduled_hires(int scheduled_hires) {
		this.scheduled_hires = scheduled_hires;
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

}
