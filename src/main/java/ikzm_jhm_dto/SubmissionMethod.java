package ikzm_jhm_dto;

public class SubmissionMethod {
	private int methodId;
	private String methodName;

	public SubmissionMethod(int methodId, String methodName) {
		super();
		this.methodId = methodId;
		this.methodName = methodName;
	}

	public SubmissionMethod() {
	}

	public int getMethodId() {
		return methodId;
	}

	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
