package utilities;

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
	
}
