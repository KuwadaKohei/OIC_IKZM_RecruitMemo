package ikzm_jhm_dto;

public class PostExamSelection {
	private int contentId;
	private String detailText;

	public PostExamSelection(int contentId, String detailText) {
		super();
		this.contentId = contentId;
		this.detailText = detailText;
	}

	public PostExamSelection() {
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getDetailText() {
		return detailText;
	}

	public void setDetailText(String detailText) {
		this.detailText = detailText;
	}

}
