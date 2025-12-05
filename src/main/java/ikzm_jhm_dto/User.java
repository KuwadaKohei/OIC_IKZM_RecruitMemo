package ikzm_jhm_dto;

/**
 * ユーザーテーブルのレコードを表す DTO。
 */
public class User {
	private int userId;
	private String googleAccountId;
	private String email;
	private String userType;
	private String name;
	private int departmentId;
	private int grade;
	private boolean isActive;
	private boolean isAdmin;

	/**
	 * 全フィールド指定コンストラクタ。
	 */
	public User(int userId, String googleAccountId, String email, String userType, String name, int departmentId,
			int grade, boolean isActive, boolean isAdmin) {
		super();
		this.userId = userId;
		this.googleAccountId = googleAccountId;
		this.email = email;
		this.userType = userType;
		this.name = name;
		this.departmentId = departmentId;
		this.grade = grade;
		this.isActive = isActive;
		this.isAdmin = isAdmin;
	}

	/**
	 * デフォルトコンストラクタ。
	 */
	public User() {
	}

	/**
	 * ユーザーIDを返す。
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * ユーザーIDを設定する。
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * GoogleアカウントIDを返す。
	 */
	public String getGoogleAccountId() {
		return googleAccountId;
	}

	/**
	 * GoogleアカウントIDを設定する。
	 */
	public void setGoogleAccountId(String googleAccountId) {
		this.googleAccountId = googleAccountId;
	}

	/**
	 * メールアドレスを返す。
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * メールアドレスを設定する。
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * ユーザー種別を返す。
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * ユーザー種別を設定する。
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 表示名を返す。
	 */
	public String getName() {
		return name;
	}

	/**
	 * 表示名を設定する。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 学科IDを返す。
	 */
	public int getDepartmentId() {
		return departmentId;
	}

	/**
	 * 学科IDを設定する。
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * 学年を返す。
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * 学年を設定する。
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * 有効フラグを返す。
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * 有効フラグを設定する。
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * 管理者フラグを返す。
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * 管理者フラグを設定する。
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
