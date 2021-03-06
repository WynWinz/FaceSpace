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
				case 4:
					displayFriends();
					break;
				case 5:
					createGroup();
					break;
				case 6:
					addToGroup();
					break;
				case 7: 
					sendMessageToUser();
					break;
				case 8: 
					displayMessages();
					break;
				case 9:
					searchForUser();
					break;
				case 10:
					threeDegrees();
					break;
				case 11: 
					topMessagers();
					break;
				case 12: 
					dropUser();
					break;
				case 13:
					runDemo();
					break;
				case 14:
					System.out.println("Goodbye");
					fs.closeConnection();
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

	private static void displayFriends() throws SQLException {
		String userEmail = promptString("Enter your email");
		fs.displayFriends(userEmail);
	}
	
	private static void createGroup() throws SQLException {
		String name = promptString("Enter a group name");
		String description = promptString("Enter a group description");
		int memLimit = promptInt("Enter a membership limit");
		fs.createGroup(name, description, memLimit);
	}
	
	private static void addToGroup() throws SQLException {
		String email = promptString("Enter your email");
		String groupName = promptString("Enter the group name");
		fs.addToGroup(email, groupName);
	}

	private static void sendMessageToUser() throws SQLException {
		String userEmail = promptString("Enter your email");
		String recipientEmail = promptString("Enter recipient's email");
		String subject = promptString("Enter the message subject");
		String body = promptString("Enter the message body");
		fs.sendMessageToUser(userEmail, recipientEmail, subject, body);
	}
	
	private static void displayMessages() throws SQLException {
		String userEmail = promptString("Enter your email");
		fs.displayMessages(userEmail);
	}

	private static void searchForUser() throws SQLException {
		String userSearch = promptString("Enter your search");
		fs.searchForUser(userSearch);
	}

	private static void threeDegrees() throws SQLException {
		String userAEmail = promptString("Enter user A's email");
		String userBEmail = promptString("Enter user B's email");
		fs.threeDegrees(userAEmail, userBEmail);
	}

	/* Display the top k users who have sent or received the highest number of messages during 
	 * the past x months. x and k should be an input parameters to this function.
	 */
	private static void topMessagers() throws SQLException{
		int numberOfUsers = promptInt("How many top users would you like to see");
		int months = promptInt("Enter the number of months you'd like you go back");
		fs.topMessagers(numberOfUsers, months);
	}

	private static void dropUser() throws SQLException{
		String email = promptString("Enter the email of the user to be deleted");
		fs.dropUser(email);
	}

	private static void runDemo() throws SQLException {
		ArrayList<String> emails = new ArrayList<String>();
		emails.add("j.grant@gmail.com");
		emails.add("funnyman12@yahoo.com");
		emails.add("wynwinz@hotmail.com");
		emails.add("chrisc11@gmail.com");
		emails.add("ryanching7@firefox.net");
		String groupName = "CS Juniors";
		ArrayList<String> subjects = new ArrayList<String>();
		subjects.add("Long Time");
		subjects.add("Concert");
		fs.setupDemo(emails, groupName, subjects);

		System.out.println("");

		//demo create users
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

		fname = "Wyn";
		lname = "Mellett";
		String email3 = emails.get(2);
		dateOfBirth = "1994-03-06";
		fs.createUser(fname, lname, email3, dateOfBirth);		

		fname = "Chris";
		lname = "Corsi";
		String email4 = emails.get(3);
		dateOfBirth = "1994-12-16";
		fs.createUser(fname, lname, email4, dateOfBirth);

		fname = "Ryan";
		lname = "Ching";
		String email5 = emails.get(4);
		dateOfBirth = "1994-10-10";
		fs.createUser(fname, lname, email5, dateOfBirth);		

		//demo initiate & establish friendship
		fs.initiateFriendship(email1, email2);
		fs.establishFriendship(email2, email1);

		fs.initiateFriendship(email3, email2);
		fs.establishFriendship(email2, email3);

		//demo display friends
		fs.displayFriends(email1);
		fs.displayFriends(email2);

		System.out.println("");
		
		//demo create group
		String description = "junior cs students at Pitt";
		int memLimit = 25;
		fs.createGroup(groupName, description, memLimit);

		System.out.println("");

		//demo add to group
		for(String email : emails) {
			fs.addToGroup(email, groupName);
		}

		System.out.println("");

		//demo send messages
		String subject = subjects.get(0);
		String body = "Hi Kevin, how are you?";
		fs.sendMessageToUser(email1, email2, subject, body);

		subject = subjects.get(1);
		body = "I have an extra ticket to the daft punk concert, you in?";
		fs.sendMessageToUser(email3, email2, subject, body);

		//demo display messages
		fs.displayMessages(email2);	

		System.out.println("");
		//demo user search
		System.out.println("Searching users with .com emails");
		fs.searchForUser(".com");

		//demo three degrees
		fs.initiateFriendship(email4, email5);
		fs.establishFriendship(email5, email4);
		fs.initiateFriendship(email4, email2);
		fs.establishFriendship(email2, email4);
		System.out.println("");

		fs.threeDegrees(email2, email5);
		System.out.println("");

		//demo top messages
		fs.topMessagers(3, 6);

		System.out.println("");
		//demo drop user
		fs.dropUser(email4);
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
		System.out.println("4. Display friends");
		System.out.println("5. Create group");
		System.out.println("6. Add to group");
		System.out.println("7. Send message to user");
		System.out.println("8. Display messages");
		System.out.println("9. Search for user");
		System.out.println("10. Three Degrees");
		System.out.println("11. Top Messagers");
		System.out.println("12. Drop User");
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









