package main.utilities;

import java.util.Scanner;

public class SelectUtilities {

	public static void printSelectLine(int number, String text){
		System.out.format("[%2d] : %s%n", number, text);
	}

	public static int selectChoice(String text){
		Scanner scan = new Scanner(System.in);
		System.out.format("%s", text);
		int choice = scan.nextInt();

		return choice;
	}

	public static String inputEdit(String text) {
		Scanner scan = new Scanner(System.in);
		System.out.format("%s", text);
		String edit = scan.nextLine();

		return edit;
	}

	public static boolean confirm(String text) {		//TODO evt lav denne metode om
		Scanner scan = new Scanner(System.in);
		System.out.format("s%", text);
		System.out.println(	"[1] : Yes\n" +
							"[2] : No\n");
		int choice = scan.nextInt();
		if (choice == 1)
			return true;
		else if (choice == 0)
			return false;
		else
			return confirm("Invalid choice, try again: ");
		
	}
}