package main.utilities;

import java.util.Scanner;

public class SelectUtilities {
	
	private static Scanner scanner = new Scanner(System.in);

	public static void printSelectLine(int number, String text){
		System.out.format("[%2d] : %s%n", number, text);
	}

	public static String prompt(String promptText) {
		System.out.print(promptText + " > ");
		return scanner.nextLine();
	}

	public static boolean confirm(String text) {
		System.out.format("%s%n", text);
		System.out.println(	"[1] : Yes\n" +
							"[2] : No");
		String choice;
		while(!(choice = prompt("Indtast valg")).equals("1") && !choice.equals("2")) {
			System.out.print("Ugyldigt. ");
		}
		
		return choice.equals("1");
	}
}