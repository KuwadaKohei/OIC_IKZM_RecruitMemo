package ikzm_jhm_dto;

/**
 * 投稿に紐づく試験選択肢を表す DTO。
 */
public class PostExamSelection {
	private int postId;
	private int contentId;
	private String detailText;

	/**
	 * 全フィールド指定のコンストラクタ。
	 */
	public PostExamSelection(int postId, int contentId, String detailText) {
		super();
		this.postId = postId;
		this.contentId = contentId;
		this.detailText = detailText;
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
	 * 試験コンテンツIDを返す。
	 */
	public int getContentId() {
		return contentId;
	}

	/**
	 * 試験コンテンツIDを設定する。
	 */
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	/**
	 * 詳細テキストを返す。
	 */
	public String getDetailText() {
		return detailText;
	}

	/**
	 * 詳細テキストを設定する。
	 */
	public void setDetailText(String detailText) {
		this.detailText = detailText;
	}

}
