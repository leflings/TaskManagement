package main.enums;

public enum Priority implements IEnum {
	
	NONE(0, "Ingen"), LOW(1, "Lav"), MEDIUM(2, "Mellem"), HIGH(3, "Høj");
	
	private int code;
	private String text;
	
	private Priority(int i, String text) {
		code = i;
		this.text = text;
	}
	
	public int getCode() {
		return code;
	}
	
	public String toString() {
		return text;
	}
	
	public Priority fromCode(int code) {
		for(Priority pl : Priority.values()) {
			if(pl.getCode() == code) {
				return pl;
			}
		}
		throw new IllegalArgumentException("No Enum specified for this code");
	}

}
