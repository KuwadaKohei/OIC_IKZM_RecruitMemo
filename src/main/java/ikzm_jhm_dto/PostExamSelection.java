package ikzm_jhm_dto;

public class PostExamSelection {
	private int postId;
	private int contentId;
	private String detailText;

	public PostExamSelection(int postId, int contentId, String detailText) {
		super();
		this.postId = postId;
		this.contentId = contentId;
		this.detailText = detailText;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
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
