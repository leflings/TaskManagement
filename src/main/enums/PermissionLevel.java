package main.enums;

public enum PermissionLevel {
	
	User(1), Supervisor(2), Admin(3);
	
	private int code;
	
	private PermissionLevel(int i) {
		code = i;
	}
	
	public int getCode() {
		return code;
	}
}
