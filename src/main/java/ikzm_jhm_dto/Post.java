package ikzm_jhm_dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Post {
	private int postId;
	private int userId;
	private int departmentId;
	private int methodId;
	private String recruitmentNo;
	private String companyName;
	private String venueAddress;
	private LocalDate examDate;
	private int grade;
	private boolean isAnonymous;
	private LocalDateTime createAt;
	private LocalDateTime updatedAt;
	private List<PostExamSelection> examSelection;

	public Post(int postId, int userId, int departmentId, int methodId, String recruitmentNo, String companyName,
			String venueAddress, LocalDate examDate, int grade, boolean isAnonymous, LocalDateTime createAt,
			LocalDateTime updatedAt, List<PostExamSelection> examSelection) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.departmentId = departmentId;
		this.methodId = methodId;
		this.recruitmentNo = recruitmentNo;
		this.companyName = companyName;
		this.venueAddress = venueAddress;
		this.examDate = examDate;
		this.grade = grade;
		this.isAnonymous = isAnonymous;
		this.createAt = createAt;
		this.updatedAt = updatedAt;
		this.examSelection = examSelection;
	}

	public Post() {
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getMethodId() {
		return methodId;
	}

	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	public String getRecruitmentNo() {
		return recruitmentNo;
	}

	public void setRecruitmentNo(String recruitmentNo) {
		this.recruitmentNo = recruitmentNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}

	public LocalDate getExamDate() {
		return examDate;
	}

	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean isAnonymous() {
		return isAnonymous;
	}

	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<PostExamSelection> getExamSelection() {
		return examSelection;
	}

	public void setExamSelection(List<PostExamSelection> examSelection) {
		this.examSelection = examSelection;
	}

}
