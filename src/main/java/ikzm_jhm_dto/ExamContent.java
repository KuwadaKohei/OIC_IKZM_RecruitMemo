package ikzm_jhm_dto;

/**
 * 試験内容マスタのDTO。カテゴリ・名称・詳細記述要否を保持する。
 */
public class ExamContent {
	private int contentId;
	private String contentCategory;
	private String contentName;
	private boolean needsDetail;

	/**
	 * 全フィールド指定のコンストラクタ。
	 *
	 * @param contentId       コンテンツID
	 * @param contentCategory カテゴリ
	 * @param contentName     名称
	 * @param needsDetail     詳細入力要否
	 */
	public ExamContent(int contentId, String contentCategory, String contentName, boolean needsDetail) {
		super();
		this.contentId = contentId;
		this.contentCategory = contentCategory;
		this.contentName = contentName;
		this.needsDetail = needsDetail;
	}

	/**
	 * デフォルトコンストラクタ。
	 */
	public ExamContent() {
	}

	/**
	 * コンテンツIDを返す。
	 *
	 * @return コンテンツID
	 */
	public int getContentId() {
		return contentId;
	}

	/**
	 * コンテンツIDを設定する。
	 *
	 * @param contentId コンテンツID
	 */
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	/**
	 * カテゴリを返す。
	 *
	 * @return カテゴリ名
	 */
	public String getContentCategory() {
		return contentCategory;
	}

	/**
	 * カテゴリを設定する。
	 *
	 * @param contentCategory カテゴリ
	 */
	public void setContentCategory(String contentCategory) {
		this.contentCategory = contentCategory;
	}

	/**
	 * 名称を返す。
	 *
	 * @return コンテンツ名
	 */
	public String getContentName() {
		return contentName;
	}

	/**
	 * 名称を設定する。
	 *
	 * @param contentName コンテンツ名
	 */
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	/**
	 * 詳細入力が必要かを返す。
	 *
	 * @return true: 必要
	 */
	public boolean isNeedsDetail() {
		return needsDetail;
	}

	/**
	 * 詳細入力要否を設定する。
	 *
	 * @param needsDetail true なら詳細入力が必要
	 */
	public void setNeedsDetail(boolean needsDetail) {
		this.needsDetail = needsDetail;
	}

}
