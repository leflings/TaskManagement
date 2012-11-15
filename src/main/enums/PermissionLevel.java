package main.enums;

public enum PermissionLevel {
	
	USER(1), SUPERVISOR(2), ADMIN(3);
	
	private int code;
	
	private PermissionLevel(int i) {
		code = i;
	}
	
	public int getCode() {
		return code;
	}
	
	public PermissionLevel fromCode(int code) {
		for(PermissionLevel pl : PermissionLevel.values()) {
			if(pl.getCode() == code) {
				return pl;
			}
		}
		throw new IllegalArgumentException("No Enum specified for this code");
	}
}
