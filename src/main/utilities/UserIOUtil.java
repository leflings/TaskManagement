package main.utilities;

import java.util.Scanner;

public class UserIOUtil {
	
	private static Scanner sc = new Scanner(System.in);

    private static int getValidIntegerChoice(int min, int max) {
        System.out.format("(%d - %d): ", min, max);
        int choice = getNextInteger();
        while ((choice < min) || (choice > max)) {
            System.out.format("Ugyldigt valg, prøv igen: ");
            choice = getNextInteger();
        }

        return choice;

    }

    public static int getNextInteger() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
        	System.out.print("Ikke et gyldigt heltal, prøv igen: ");
        	return getNextInteger();

        }
    }

}
