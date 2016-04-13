/*
 * Authors:
 * Ryan Ching
 * Chris Corsi
 * Edwin Mellett
 *
 * Date Started:
 * 4/9/16
*/

import java.sql.*;  		
import java.util.*;
import java.io.*;	

public class FaceSpaceDriver {

	private static FaceSpace fs;
	private static Scanner in = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		fs = new FaceSpace();
		run();	
	}


	private static void run() throws SQLException {
		while(true) {
			int option = getOption();
			switch(option) {
				case 1:
					createUser();
					break;
				case 2:
					initiateFriendship();
					break;
				case 3: 
					establishFriendship();
					break;
				case 13:
					demo();
					break;
				case 14:
					System.out.println("Goodbye");
					System.exit(1);
					break;
			}
		}
	}

	private static void createUser() throws SQLException {
		//System.out.println("You chose create user.");
		String fname = promptString("Enter your first name");
		String lname = promptString("Enter your last name");
		String email = promptString("Enter your email");
		String dateOfBirth = promptString("Enter your date of birth (YYYY-MM-DD)");
		fs.createUser(fname, lname, email, dateOfBirth);
	}

	private static void initiateFriendship() {
		int profileID = promptInt("Enter your profile ID");
		int friendID = promptInt("Enter your friend's ID");
		fs.initiateFriendship(profileID, friendID);
	}

	private static void establishFriendship() {
		int profileID = promptInt("Enter your profile ID");
		int friendID = promptInt("Enter your friend's ID");
		fs.establishFriendship(profileID, friendID);		
	}

	private static void demo() {
		System.out.println("You chose to demo the project");
	}

	private static int getOption() {
		presentMenu();
		return promptInt("Pick one");
	}

	private static void presentMenu() {
		System.out.println();
		System.out.println("Please select an option.");
		System.out.println("1. Create a user");
		System.out.println("2. Initiate a friendship");
		System.out.println("3. Establish a friendship");
		System.out.println("13. Demo all functions");
		System.out.println("14. Exit");
		System.out.println();
	}
	
	private static int promptInt(String message) {
	    int value;
	    System.out.print(message + ": ");
	    value = in.nextInt();
	    in.nextLine();
	    return value;
  	}

  	private static String promptString(String message) {
  		String value;
  		System.out.print(message + ": ");
  		value = in.nextLine();
  		return value;
  	}
	
}









