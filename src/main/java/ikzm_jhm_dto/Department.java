package ikzm_jhm_dto;

/**
 * 学科マスタを表すシンプルな DTO。
 */
public class Department {
	private int departmentId;
	private String departmentName;

	/**
	 * 全フィールドを指定するコンストラクタ。
	 *
	 * @param departmentId   学科ID
	 * @param departmentName 学科名
	 */
	public Department(int departmentId, String departmentName) {
		super();
		this.departmentId = departmentId;
		this.departmentName = departmentName;
	}

	/**
	 * デフォルトコンストラクタ。
	 */
	public Department() {
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
	 * 学科名を返す。
	 *
	 * @return 学科名
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * 学科名を設定する。
	 *
	 * @param departmentName 学科名
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
