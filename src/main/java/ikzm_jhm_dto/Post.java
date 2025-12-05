package ikzm_jhm_dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 投稿の基本情報を保持する DTO。
 */
public class Post {
	private int postId;
	private int userId;
	private int departmentId;
	private int methodId;
	private int recruitmentNo;
	private String companyName;
	private String venueAddress;
	private LocalDate examDate;
	private int grade;
	private boolean isAnonymous;
	private LocalDateTime createAt;
	private LocalDateTime updatedAt;
	private List<PostExamSelection> examSelection;
	private boolean isActive;

	/**
	 * すべてのフィールドを指定するコンストラクタ。
	 */
	public Post(int postId, int userId, int departmentId, int methodId, int recruitmentNo, String companyName,
			String venueAddress, LocalDate examDate, int grade, boolean isAnonymous, LocalDateTime createAt,
			LocalDateTime updatedAt, List<PostExamSelection> examSelection, boolean isActive) {
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
		this.isActive = isActive;
	}

	/**
	 * デフォルトコンストラクタ。
	 */
	public Post() {
	}

	/**
	 * 投稿IDを返す。
	 *
	 * @return 投稿ID
	 */
	public int getPostId() {
		return postId;
	}

	/**
	 * 投稿IDを設定する。
	 *
	 * @param postId 投稿ID
	 */
	public void setPostId(int postId) {
		this.postId = postId;
	}

	/**
	 * 投稿者ユーザーIDを返す。
	 *
	 * @return ユーザーID
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * 投稿者ユーザーIDを設定する。
	 *
	 * @param userId ユーザーID
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
	 * 求人票番号を返す。
	 */
	public int getRecruitmentNo() {
		return recruitmentNo;
	}

	/**
	 * 求人票番号を設定する。
	 */
	public void setRecruitmentNo(int recruitmentNo) {
		this.recruitmentNo = recruitmentNo;
	}

	/**
	 * 受験企業名を返す。
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 受験企業名を設定する。
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 会場住所を返す。
	 */
	public String getVenueAddress() {
		return venueAddress;
	}

	/**
	 * 会場住所を設定する。
	 */
	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}

	/**
	 * 試験日を返す。
	 */
	public LocalDate getExamDate() {
		return examDate;
	}

	/**
	 * 試験日を設定する。
	 */
	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
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
	 * 匿名投稿かを返す。
	 */
	public boolean isAnonymous() {
		return isAnonymous;
	}

	/**
	 * 匿名投稿フラグを設定する。
	 */
	public void setAnonymous(boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	/**
	 * 作成日時を返す。
	 */
	public LocalDateTime getCreateAt() {
		return createAt;
	}

	/**
	 * 作成日時を設定する。
	 */
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	/**
	 * 更新日時を返す。
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * 更新日時を設定する。
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * 選択した試験内容一覧を返す。
	 */
	public List<PostExamSelection> getExamSelection() {
		return examSelection;
	}

	/**
	 * 選択した試験内容一覧を設定する。
	 */
	public void setExamSelection(List<PostExamSelection> examSelection) {
		this.examSelection = examSelection;
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

}
