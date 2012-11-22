package main.enums;

public enum Status implements IEnum {
	
	NONE(0, "Ingen"), ACCEPTED(1, "Accepteret"), IN_PROGRESS(2, "Undervejs"), FINISHED(3, "Færdig"), DECLINED(4, "Afvist");
	
	private int code;
	private String text;
	
	private Status(int i, String text) {
		code = i;
		this.text = text;
	}
	
	public int getCode() {
		return code;
	}
	
	public String toString() {
		return text;
	}
	
	public Status fromCode(int code) {
		for(Status pl : Status.values()) {
			if(pl.getCode() == code) {
				return pl;
			}
		}
		throw new IllegalArgumentException("No Enum specified for this code");
	}

}
