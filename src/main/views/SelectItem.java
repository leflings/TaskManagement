package main.views;

import java.util.List;
import java.util.Scanner;

import main.dto.BaseModel;
import main.utilities.SelectUtilities;

public class SelectItem {

	private static Scanner scanner = new Scanner(System.in);
	
	public static <T extends BaseModel> T getSelection(List<T> items) {
		for (int i = 0; i < items.size(); i++) {
			SelectUtilities.printSelectLine(i+1, items.get(i).toString());
		}
		
		String answer;
		while(!(answer = ask()).equals("")) {
			int choice;
			try {
				choice = Integer.parseInt(answer);
			} catch (NumberFormatException e) {
				System.out.print("Ugyldigt valg. ");
				continue;
			}
			if(choice > 0 && choice <= items.size()) {
				return (T) items.get(choice-1);
			} else {
				System.out.print("Ugyldigt valg. ");
			}
		}
		return null;
	}
	
	private static String ask() {
		System.out.print("Vælg fra listen (angiv blankt input for at gå tilbage) >");
		return scanner.nextLine();
	}

}
