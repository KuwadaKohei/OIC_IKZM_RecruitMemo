package ikzm_jhm_dto;

public class User {
	private int userId;
	private String googleAccountId;
	private String email;
	private String userType;
	private String name;
	private boolean isActive;

	public User(int userId, String googleAccountId, String email, String userType, String name, boolean isActive) {
		super();
		this.userId = userId;
		this.googleAccountId = googleAccountId;
		this.email = email;
		this.userType = userType;
		this.name = name;
		this.isActive = isActive;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
