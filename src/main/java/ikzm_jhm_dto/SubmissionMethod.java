package ikzm_jhm_dto;

/**
 * 応募方法マスタの DTO。
 */
public class SubmissionMethod {
	private int methodId;
	private String methodName;

	/**
	 * 全フィールド指定のコンストラクタ。
	 */
	public SubmissionMethod(int methodId, String methodName) {
		super();
		this.methodId = methodId;
		this.methodName = methodName;
	}

	/**
	 * デフォルトコンストラクタ。
	 */
	public SubmissionMethod() {
	}

	/**
	 * 応募方法IDを返す。
	 */
	public int getMethodId() {
		return methodId;
	}

	/**
	 * 応募方法IDを設定する。
	 */
	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	/**
	 * 応募方法名を返す。
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * 応募方法名を設定する。
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
