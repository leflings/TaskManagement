package main.menu;

public class TextMenuItem implements Runnable {

	private String title;
	protected Runnable exec;
	
	protected TextMenuItem(String title) {
		this(title, null);
	}
	
	public TextMenuItem(String title, Runnable exec) {
		this.title = title;
		this.exec = exec;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isExec() {
		return exec != null;
	}
	
	protected void setExec(Runnable exec) {
		this.exec = exec;
	}
	
	public void run() {
		try {
			exec.run();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
