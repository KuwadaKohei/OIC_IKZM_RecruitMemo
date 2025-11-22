package ikzm_jhm_dto;

public class ExamContent {
	private int contentId;
	private String contentCategory;
	private String contentName;
	private boolean needsDetail;

	public ExamContent(int contentId, String contentCategory, String contentName, boolean needsDetail) {
		super();
		this.contentId = contentId;
		this.contentCategory = contentCategory;
		this.contentName = contentName;
		this.needsDetail = needsDetail;
	}

	public ExamContent() {
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getContentCategory() {
		return contentCategory;
	}

	public void setContentCategory(String contentCategory) {
		this.contentCategory = contentCategory;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public boolean isNeedsDetail() {
		return needsDetail;
	}

	public void setNeedsDetail(boolean needsDetail) {
		this.needsDetail = needsDetail;
	}

}
