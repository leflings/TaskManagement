package main.enums;

public enum PermissionLevel implements IEnum {
	
	USER(1, "Bruger"), SUPERVISOR(2, "Supervisor"), ADMIN(3, "Adminstrator"), OWNER(4, "Ejer");
	
	private int code;
	private String text;
	
	private PermissionLevel(int i, String text) {
		code = i;
		this.text = text;
	}
	
	public int getCode() {
		return code;
	}
	
	public String toString() {
		return text;
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
