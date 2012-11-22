package main.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextMenu extends TextMenuItem {
	
	private static final int MENU_COLUMN_WIDTH = 30;
	private boolean exitLoop = false;
	
	public void setExitLoop(boolean bool) {
		this.exitLoop = bool;
	}
	
	public boolean isExitLoop() {
		return exitLoop;
	}

	private static final TextMenuItem quit = new TextMenuItem("Afslut", new Runnable() {
		public void run() {
			System.exit(0);
		}
	});

	private static final TextMenuItem back = new TextMenuItem("Tilbage");

	List<TextMenuItem> items;

	public TextMenu(String title, TextMenuItem... items) {
		this(title, false, true, items);
	}

	public TextMenu(String title, boolean addBack, boolean addQuit, TextMenuItem... items) {
		super(title);
		setExec(this);

		initialize(addBack, addQuit, items);
	}

	private void initialize(boolean addBack, boolean addQuit, TextMenuItem... items) {

		this.items = new ArrayList<TextMenuItem>(Arrays.asList(items));
		if (addBack) {
			this.items.add(back);

		}
		if (addQuit) {
			this.items.add(quit);
		}
	}

	private void display() {

		int columnLength = items.size() / 3 + (items.size() % 3 == 0 ? 0 : 1);
		int rows = Math.min(Math.max(3, columnLength), items.size());
		String rowFormat = "[%2d] : %-"+MENU_COLUMN_WIDTH+"s";
		System.out.println("\n== " + getTitle() + " ==");
		for (int i = 0; i < rows; i++) {
			StringBuilder sb = new StringBuilder();
			if (items.size() != 0) {
				sb.append(String.format(rowFormat, i, getTruncatedTitle(i)));
			}
			if (items.size() > i + rows) {
				// two columns
				sb.append("  " + String.format(rowFormat, rows + i, getTruncatedTitle(rows + i)));
			}
			if (items.size() > i + (2 * rows)) {
				// three columns
				sb.append("  " + String.format(rowFormat, 2 * rows + i, getTruncatedTitle(2 * rows + i)));
			}
			String output = sb.toString();
			System.out.println(output);
		}

		System.out.print("Indtast valg: ");
		System.out.flush();
	}
	
	private String getTruncatedTitle(int itemId) {
		String title = items.get(itemId).getTitle();
		int cutoff = Math.min(MENU_COLUMN_WIDTH-1, title.length());
		return title.substring(0, cutoff);
	}

	private TextMenuItem prompt() throws IOException {

		if(isExitLoop()) {
			return new TextMenuItem("Return");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {

			display();

			String line = br.readLine();
			try {
				int option = Integer.parseInt(line);
				if (option >= 0 && option < items.size()) {
					return items.get(option);
				}
			} catch (NumberFormatException e) {
			}

			System.out.println("Ikke et gyldigt valg: " + line);
		}
	}

	public void addItems(TextMenuItem... menuItems) {
		for (TextMenuItem textMenuItem : menuItems) {
			items.add(textMenuItem);
		}
	}

	public void run() {

		try {
			for (TextMenuItem item = prompt(); item.isExec(); item = prompt()) {
				item.run();

			}
		} catch (Throwable t) {
			t.printStackTrace(System.out);
		}
	}
}
