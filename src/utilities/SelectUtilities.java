package utilities;

import java.util.Scanner;

public class SelectUtilities {

	public static void printSelectLine(int number, String text){
		System.out.format("[%-2d] : %s%n", number, text);
	}
	
	public static int selectChoice(){
		Scanner scan = new Scanner(System.in);
		System.out.format("Vælg fra ovenstående liste");
		int choice = scan.nextInt();
		
		return choice;
	}
	
}
