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
					runDemo();
					break;
				case 14:
					System.out.println("Goodbye");
					fs.closeConnection();;
					System.exit(1);
					break;
			}
		}
	}

	private static void createUser() throws SQLException {
		String fname = promptString("Enter your first name");
		String lname = promptString("Enter your last name");
		String email = promptString("Enter your email");
		String dateOfBirth = promptString("Enter your date of birth (YYYY-MM-DD)");
		fs.createUser(fname, lname, email, dateOfBirth);
	}

	private static void initiateFriendship() throws SQLException {
		String userEmail = promptString("Enter your email");
		String friendEmail = promptString("Enter your friend's email");
		fs.initiateFriendship(userEmail, friendEmail);
	}

	private static void establishFriendship() throws SQLException {	
		String userEmail = promptString("Enter your email");
		String friendEmail = promptString("Enter your friend's email");
		fs.establishFriendship(userEmail, friendEmail);	
	}

	private static void runDemo() throws SQLException {
		ArrayList<String> emails = new ArrayList<String>();
		emails.add("j.grant@gmail.com");
		emails.add("funnyman12@yahoo.com");
		fs.setupDemo(emails);

		String fname = "Josh";
		String lname = "Grant";
		String email1 = emails.get(0);
		String dateOfBirth = "1996-04-17";
		fs.createUser(fname, lname, email1, dateOfBirth);

		fname = "Kevin";
		lname = "James";
		String email2 = emails.get(1);
		dateOfBirth = "1974-10-22";
		fs.createUser(fname, lname, email2, dateOfBirth);

		fs.initiateFriendship(email1, email2);
		fs.establishFriendship(email2, email1);
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









