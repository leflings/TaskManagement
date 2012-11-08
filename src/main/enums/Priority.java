package main.enums;

public enum Priority {
	
	NONE(0), LOW(1), MEDIUM(2), HIGH(3);
	
	private int code;
	
	private Priority(int i) {
		code = i;
	}
	
	public int getCode() {
		return code;
	}

}
