package ikzm_jhm_dto;

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

	public User() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGoogleAccountId() {
		return googleAccountId;
	}

	public void setGoogleAccountId(String googleAccountId) {
		this.googleAccountId = googleAccountId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
