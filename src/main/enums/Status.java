package main.enums;

public enum Status {
	
	NONE(0), ACCEPTED(1), IN_PROGRESS(2), FINISHED(3), DECLINED(4);
	
	private int code;
	
	private Status(int i) {
		code = i;
	}
	
	public int getCode() {
		return code;
	}

}
