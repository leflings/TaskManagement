package main.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserIOUtil {

	private static Scanner sc = new Scanner(System.in);
	
	public static String join(Object[] items, String seperator) {
		StringBuilder sb = new StringBuilder();
		seperator += " ";
		boolean isFirst = true;
		for(Object o : items) {
			if(!isFirst) {
				sb.append(seperator);
			}
			sb.append(o.toString());
			isFirst = false;
		}
		
		return sb.toString();
	}
	
	public static String getNullableString(String prompt) {
		System.out.format("%s > ", prompt);
		String input;
		input = sc.nextLine();
		return (input.length() == 0) ? null : input;
	}
	
	public static String getNonEmptyString(String prompt) {
		System.out.format("%s > ", prompt);
		String input;
		input = sc.nextLine();
		while(input.length() == 0) {
			System.out.format("Skal udfyldes. %s > ", prompt);
			input = sc.nextLine();
		}
		return input;
	}
	

	public static Date askForDate() {
		Date convertedDate = null;

		System.out
				.println("Indtast dato på formen dd/MM/yyyy, tryk 'enter' for blank: ");
		String inputString = sc.nextLine();
		if (inputString.equals("")) {
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {
			convertedDate = dateFormat.parse(inputString);
		} catch (ParseException e) {
			System.err.println("Forkert input, prøv igen: ");
			return askForDate();
			// e.printStackTrace();

		}
		return convertedDate;
	}

	/**
	 * Returns time as minutes
	 * 
	 * @return int minutes
	 * **/
	public static int askForDuration() {
		float hours = 0;
		System.out.println("Indtast tidsforbrug i timer: ");
		String input = sc.nextLine();

		try {
			hours = Float.parseFloat(input);
		} catch (NumberFormatException e) {
			System.err.println("Der er sket en fejl, prøv igen:");
			return askForDuration();
		}
		int minutes = (int) (hours * 60);

		return minutes;

	}

	public static Date askForDateAndTime() {
		Date combined = null;

		System.out
				.println("Indtast dato på formen dd/MM/yyyy, tryk 'enter' for blank: ");
		String date = sc.nextLine();
		if (date.equals("")) {
			return null;
		}
		System.out.println("Indtast tidspunkt på formen HH:mm: ");
		String time = sc.nextLine();

		String combinedTimeDate = date + " " + time;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		try {
			combined = dateFormat.parse(combinedTimeDate);
		} catch (ParseException e) {
			System.err.println("Der er sket en fejl, prøv igen: ");
			return askForDateAndTime();
	//		e.printStackTrace();
		}

		return combined;

	}

	public static String printDate(Date date) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = dateFormat.format(date);

		return formattedDate;
	}
	/**
	 * Returnerer kun kl. som HH.mm
	 * @param date
	 * @return String formattedDate
	 */
	public static String printTime(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH.mm");
		String formattedDate = dateFormat.format(date);
		
		return formattedDate;
	}
	
	public static String printTimeAndDate(Date date){
		String formattedDate = printDate(date);
		String formattedTime = printTime(date);
		
		String combined = formattedTime + " " + formattedDate;
		return combined;
	}

}
