package test.utilities;

import java.util.Date;

public class UserIOUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date Input = main.utilities.UserIOUtil.askForDateAndTime();
		String date = main.utilities.UserIOUtil.printDate(Input);
System.out.println(date);
	}

}
