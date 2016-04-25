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
import java.text.ParseException;
import oracle.jdbc.*;
import java.util.Date;
import java.text.*;
import java.util.Calendar;
import java.util.*;

public class FaceSpace {
	
	int numUsers;
	int numGroups;
	
	private static Connection connection; //used to hold the jdbc connection to the DB
    private Statement statement; //used to create an instance of the connection
    private PreparedStatement prepStatement; //used to create a prepared statement, that will be later reused
    private ResultSet resultSet; //used to hold the result of your query (if one exists)
	private String query;
	
	public FaceSpace() throws SQLException{
		setupDatabase();
	}
	
	public void createUser(String fname, String lname, String email, String dob) throws SQLException{

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();

	    	query = "SELECT MAX(profile_ID) FROM Profiles";
	    	ResultSet resultSet =statement.executeQuery(query);
	    
			int maxID = -1;
			while(resultSet.next())
	  		{
				maxID = resultSet.getInt(1);
	 	   	}
			maxID++;

		    query = "INSERT INTO PROFILES (profile_ID, fname, lname, email, DOB, lastLogin) VALUES ("+maxID+",'"+fname+"','"+lname+"','"+email+"', DATE '"+dob+"', TIMESTAMP '"+ft.format(dNow)+"')";				

			int result = statement.executeUpdate(query);

			connection.commit();
			System.out.println("Profile Added.");
			Thread.sleep(1000);

		    resultSet.close();
		}	
		catch(Exception Ex)  
		{
			System.out.println("Machine Error: " + Ex.toString());
		}
		finally{
			try {
				if (statement!=null) statement.close();
			} catch (SQLException e) {
				System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}	
		
	}

	public void initiateFriendship(String userEmail, String friendEmail) throws SQLException{

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String todaysDate = format1.format(cal.getTime());
		int profileID = 0, friendID = 0;
		
		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();
			
			query = 	"SELECT profile_ID "
						+"FROM Profiles "
						+"WHERE email = '"+ userEmail +"'";

			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next())
	  		{
				profileID = resultSet.getInt(1);
	 	   	}

			query = 	"SELECT profile_ID "
						+"FROM Profiles "
						+"WHERE email = '"+ friendEmail +"'";

			resultSet = statement.executeQuery(query);
			while(resultSet.next())
	  		{
				friendID = resultSet.getInt(1);
	 	   	}			

			//check to make sure the friendship does not already exist
	    	query = "SELECT profile_ID FROM Friends WHERE profile_ID = "+profileID+" AND friend_ID = "+friendID;
	    	resultSet =statement.executeQuery(query);
			boolean alreadyExists = false;
			if(resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
				alreadyExists = true;
	 	   	}
			//check to make sure friendship does not already exist
			query = "SELECT profile_ID FROM Friends WHERE profile_ID = "+friendID+" AND friend_ID = "+profileID;
	    	resultSet =statement.executeQuery(query);
			if(resultSet.isBeforeFirst() && alreadyExists == false)			//returns true if there is data in result set
	  		{
				alreadyExists = true;
	 	   	}
			
			//if it doesn't exist, add it
			if(alreadyExists == false){
			    query = "INSERT INTO Friends (profile_ID, friend_ID, established, dateEstablished) VALUES ("+profileID+", "+friendID+", 0, DATE '"+todaysDate+"')";				
				int result = statement.executeUpdate(query);
				connection.commit();
				System.out.println();
				System.out.println(userEmail + "'s friendship with " + friendEmail + " now pending");
				Thread.sleep(1000);
			}
			else{
				System.out.println();
				System.out.println("Friendship already exists.");
			}
			
		    resultSet.close();
		}	
		catch(Exception Ex)  
		{
			System.out.println("Machine Error: " + Ex.toString());
		}
		finally{
			try {
				if (statement!=null) statement.close();
			} catch (SQLException e) {
				System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}	
		
		
	}
	
	public void establishFriendship(String userEmail, String friendEmail) throws SQLException{

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String todaysDate = format1.format(cal.getTime());
		int profileID = 0, friendID = 0;
		
		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();
			
		    query = 	"SELECT profile_ID "
						+"FROM Profiles "
						+"WHERE email = '"+ userEmail +"'";

			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next())
	  		{
				profileID = resultSet.getInt(1);
	 	   	}

			query = 	"SELECT profile_ID "
						+"FROM Profiles "
						+"WHERE email = '"+ friendEmail +"'";

			resultSet = statement.executeQuery(query);
			while(resultSet.next())
	  		{
				friendID = resultSet.getInt(1);
	 	   	}

			//check to make sure the friendship does not already exist
	    	query = "SELECT profile_ID FROM Friends WHERE profile_ID = "+profileID+" AND friend_ID = "+friendID;
	    	resultSet =statement.executeQuery(query);
			boolean alreadyExists = false;
			if(resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
				alreadyExists = true;
	 	   	}
			//check to make sure friendship does not already exist
			query = "SELECT profile_ID FROM Friends WHERE profile_ID = "+friendID+" AND friend_ID = "+profileID;
	    	resultSet =statement.executeQuery(query);
			if(resultSet.isBeforeFirst() && alreadyExists == false)			//returns true if there is data in result set
	  		{
				alreadyExists = true;
	 	   	}
			
			//if it does exist, update it
			if(alreadyExists != false){
			    query = "UPDATE Friends SET established = 1, dateEstablished= DATE '"+todaysDate+"' WHERE profile_ID =" +profileID+ "AND friend_ID ="+friendID;				
				int result = statement.executeUpdate(query);
				connection.commit();

				query = "UPDATE Friends SET established = 1, dateEstablished= DATE '"+todaysDate+"' WHERE profile_ID =" +friendID+ "AND friend_ID ="+profileID;				
				result = statement.executeUpdate(query);
				connection.commit();

				System.out.println();
				System.out.println(userEmail + "'s friendship with " + friendEmail + " confirmed");
				Thread.sleep(1000);
			}
			else{
				System.out.println();
				System.out.println("Friendship isn't pending -- please request friendship using initiate friendship");
			}
			
		    resultSet.close();
		}	
		catch(Exception Ex)  
		{
			System.out.println("Machine Error: " + Ex.toString());
		}
		finally{
			try {
				if (statement!=null) statement.close();
			} catch (SQLException e) {
				System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}	
		
		
	}
	
	public void displayFriends(String email) throws SQLException{
		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();
			
	    	query = "SELECT profile_ID, fname, lname FROM Profiles WHERE email = '"+email+"'";
	    	ResultSet resultSet =statement.executeQuery(query);    
			int profileID = -1;
			String profileFName=null, profileLName=null;
			while(resultSet.next())
	  		{
				profileID = resultSet.getInt(1);
				profileFName = resultSet.getString(2).trim();		//remove whitespace on names
				profileLName = resultSet.getString(3).trim();
	 	   	}
			
			ArrayList<Integer> friendIDs = new ArrayList<Integer>();
			
			query = "SELECT friend_ID FROM friends WHERE profile_ID = "+profileID;
			resultSet =statement.executeQuery(query);    
			while(resultSet.next())
	  		{
				friendIDs.add(resultSet.getInt(1));
	 	   	}	
			
			query = "SELECT profile_ID FROM friends WHERE friend_ID = "+profileID;
			resultSet =statement.executeQuery(query);    
			while(resultSet.next())
	  		{
				friendIDs.add(resultSet.getInt(1));
	 	   	}
			System.out.println();
			System.out.println(profileFName+" "+profileLName+"'s friends: ");
			for(int i=0; i<friendIDs.size(); i++){
				query = "SELECT fname, lname, email FROM profiles WHERE profile_ID = "+friendIDs.get(i);
				resultSet =statement.executeQuery(query);    
				while(resultSet.next())
		  		{
					System.out.println((i+1)+". "+resultSet.getString(1) + " " +resultSet.getString(2) + "email: "+resultSet.getString(3));
	 	   		}
				
			}


			Thread.sleep(1000);

		    resultSet.close();
		}	
		catch(Exception Ex)  
		{
			System.out.println("Machine Error: " + Ex.toString());
		}
		finally{
			try {
				if (statement!=null) statement.close();
			} catch (SQLException e) {
				System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}	
	
	
	
	}
	
	public void createGroup(String name, String description, int memLimit) throws SQLException{
		
		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();

			query = "SELECT groupName FROM Groups WHERE groupName = '"+name+"'";
	    	resultSet =statement.executeQuery(query);
			if(resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			System.out.println();
				System.out.println("Group name already taken.");
				return;
	 	   	}

	    	query = "SELECT MAX(group_ID) FROM groups";
	    	ResultSet resultSet =statement.executeQuery(query);
	    
			int maxID = -1;
			while(resultSet.next())
	  		{
				maxID = resultSet.getInt(1);
	 	   	}
			maxID++;

		    query = "INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES ("+maxID+",'"+name+"','"+description+"',"+memLimit+",0)";				
			int result = statement.executeUpdate(query);

			connection.commit();
			System.out.println("Group created.");
			Thread.sleep(1000);
		    resultSet.close();
		}	
		catch(Exception Ex)  
		{
			System.out.println("Machine Error: " + Ex.toString());
		}
		finally{
			try {
				if (statement!=null) statement.close();
			} catch (SQLException e) {
				System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}			
	}
	
	public void addToGroup(String email, String groupName) throws SQLException{
		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();

			//Get group info and check membership limit
			query = "SELECT group_ID, memberLimit, numMembers FROM Groups WHERE groupName = '"+groupName+"'";
	    	resultSet =statement.executeQuery(query);
			if(!resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			System.out.println();
				System.out.println("A group with this name does not exist.");
				return;
	 	   	}
			int groupID=-1, memLimit=-1, numMembers=-1, profileID=-1;
			
			while(resultSet.next())
	  		{
				groupID = resultSet.getInt(1);
				memLimit = resultSet.getInt(2);
				numMembers = resultSet.getInt(3);
	 	   	}
			
			//need to use trigger instead
			/*if(memLimit == numMembers){
				System.out.println();
				System.out.println("Sorry, this group is full.");
				return;
			}*/

			//Get member info
			query = "SELECT profile_ID FROM profiles WHERE email = '"+email+"'";
	    	resultSet =statement.executeQuery(query);
			if(!resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			System.out.println();
				System.out.println("User with that email does not exist.");
				return;
	 	   	}
			while(resultSet.next()){
				profileID = resultSet.getInt(1);
			}
			
			//Insert member into group
		    query = "INSERT INTO Members (group_ID,profile_ID) VALUES ("+groupID+", "+profileID+")";			
			int result = statement.executeUpdate(query);

			//increment group number of members
			numMembers++;
			query = "UPDATE Groups SET numMembers = "+numMembers+" WHERE group_ID =" +groupID;
			result = statement.executeUpdate(query);

			connection.commit();
			System.out.println("Member added.");
			Thread.sleep(1000);
		    resultSet.close();
		}	
		catch(Exception Ex)  
		{
			System.out.println("Machine Error: " + Ex.toString());
		}
		finally{
			try {
				if (statement!=null) statement.close();
			} catch (SQLException e) {
				System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}			
		
	}
	
	public void sendMessageToUser(String userEmail, String recipientEmail, String subject, String body) throws SQLException{
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		
		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();

			int msgID = 1;
			//Get group info and check membership limit
			query = "SELECT MAX(msg_ID) FROM Messages";
	    	resultSet = statement.executeQuery(query);
			
			while(resultSet.next())
	  		{
				msgID = resultSet.getInt(1);
	 	   	}
	 	   	msgID++;
			
			int userID = -1, recipientID = -1;
			
			//Get member info
			query = "SELECT profile_ID FROM profiles WHERE email = '"+userEmail+"'";
	    	resultSet =statement.executeQuery(query);
			if(!resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			System.out.println();
				System.out.println("User with sender's email does not exist.");
				return;
	 	   	}
			while(resultSet.next()){
				userID = resultSet.getInt(1);
			}
			
			query = "SELECT profile_ID FROM profiles WHERE email = '"+recipientEmail+"'";
	    	resultSet =statement.executeQuery(query);
			if(!resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			System.out.println();
				System.out.println("User with recipient's email does not exist.");
				return;
	 	   	}
			while(resultSet.next()){
				recipientID = resultSet.getInt(1);
			}
			
			//Insert message into messges
		    query = "INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES("+msgID+","+userID+","+recipientID+",'"+subject+"','"+body+"', TIMESTAMP '"+ft.format(dNow)+"')";
			int result = statement.executeUpdate(query);

			connection.commit();
			System.out.println("Message Sent.");
			Thread.sleep(1000);
		    resultSet.close();
		}	
		catch(Exception Ex)  
		{
			System.out.println("Machine Error: " + Ex.toString());
		}
		finally{
			try {
				if (statement!=null) statement.close();
			} catch (SQLException e) {
				System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}
	
	}
	
	public void displayMessages(String userEmail) throws SQLException{
	
		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();
		
			ArrayList<Integer> senderIDs = new ArrayList<Integer>();
			ArrayList<String> subjects = new ArrayList<String>();
			ArrayList<String> texts = new ArrayList<String>();
			ArrayList<String> times = new ArrayList<String>();
			
			int userID = -1;
			
			//Get user info
			query = "SELECT profile_ID FROM profiles WHERE email = '"+userEmail+"'";
	    	resultSet =statement.executeQuery(query);
			if(!resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			System.out.println();
				System.out.println("User that email does not exist.");
				return;
	 	   	}
			while(resultSet.next()){
				userID = resultSet.getInt(1);
			}
			
			//get messages info
			query = "SELECT sender_ID, subject, msgText, timeSent FROM messages WHERE recipient_ID = '"+userID+"'";
	    	resultSet =statement.executeQuery(query);
			if(!resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			System.out.println();
				System.out.println("This user does not have any messages.");
				return;
	 	   	}
			while(resultSet.next()){
				senderIDs.add(resultSet.getInt("sender_ID"));
				subjects.add(resultSet.getString("subject"));
				texts.add(resultSet.getString("msgText"));
				times.add(resultSet.getString("timeSent"));
			}
			
			//get sender names and print messages
			for(int i=0; i<senderIDs.size(); i++){
				query = "SELECT fname, lname FROM profiles WHERE profile_ID = "+senderIDs.get(i);
				resultSet =statement.executeQuery(query);
				String senderFName = null, senderLName = null;
				while(resultSet.next()){
					senderFName = resultSet.getString("fname");
					senderLName = resultSet.getString("lname");
				}
				System.out.println();
				System.out.println("Message "+(i+1)+": ");
				System.out.println("From: "+senderFName+" "+senderLName);
				System.out.println("Subject: "+subjects.get(i));
				System.out.println("Body: "+texts.get(i));
				System.out.println("Time sent: "+times.get(i));
			}

			connection.commit();
			Thread.sleep(1000);
		    resultSet.close();
		}	
		catch(Exception Ex)  
		{
			System.out.println("Machine Error: " + Ex.toString());
		}
		finally{
			try {
				if (statement!=null) statement.close();
			} catch (SQLException e) {
				System.out.println("Cannot close Statement. Machine error: "+e.toString());
			}
		}
	
	
	
	}
	
	public void searchForUser(String userSearch) throws SQLException{
	
	}
	
	public void threeDegrees(int userA, int userB) throws SQLException{
	
	}
	
	public void topMessagers() throws SQLException{
	
	}
	
	public void dropUser(int profileID) throws SQLException{
	
	}
	
	public void setupDatabase() throws SQLException{
		
		String username, password;
		username = "edm34"; //This is your username in oracle
		password = "3913516"; //This is your password in oracle
		try{
			System.out.println("Registering DB..");
		    // Register the oracle driver.  
		    DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());

		    System.out.println("Set url..");
		    //This is the location of the database.  This is the database in oracle
		    //provided to the class
		    String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass"; 
		    
		    System.out.println("Connect to DB..");
		    //create a connection to DB on class3.cs.pitt.edu
		    connection = DriverManager.getConnection(url, username, password);
		   	

 
		}
		catch(Exception Ex)  {
		    System.out.println("Error connecting to database.  Machine Error: " +
	      	Ex.toString());
		}
		/*finally
		{
			 /* NOTE: the connection should be created once and used through out the whole project;
			 * Is very expensive to open a connection therefore you should not close it after every operation on database */
			/*connection.close();
		}*/
	}
	
	public void closeConnection() throws SQLException{
		try {
			connection.close();
	    }
	    catch(Exception Ex)  {
			System.out.println("Error connecting to database.  Machine Error: " + Ex.toString());
	    }
	}
	
	public void setupDemo(ArrayList<String> emails, String groupName, ArrayList<String> subjects) throws SQLException {
		
		connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	   	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	    statement = connection.createStatement();
	    ResultSet resultSet = null;
	
		//clear demo profiles
		for(String e : emails){
			
			query = "SELECT profile_ID FROM profiles WHERE email = '"+ e +"' ";
	    	resultSet =statement.executeQuery(query);
			if(resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			while(resultSet.next()) {
					int profID = resultSet.getInt(1);
		 	   		query = "DELETE FROM profiles WHERE profile_ID = "+profID;
			 	   	int result = statement.executeUpdate(query);
		 	   	}	
	 	   	}	
		}

		//clear demo group
		query = "SELECT group_ID FROM Groups WHERE groupName = '" + groupName + "' ";
		resultSet = statement.executeQuery(query);
		while(resultSet.next()) {
			int groupID = resultSet.getInt(1);
			query = "DELETE FROM Groups WHERE group_ID = " + groupID;
			int result = statement.executeUpdate(query);
		}

		//clear demo messages
		for(String s : subjects) {
			query = "SELECT msg_ID FROM Messages WHERE subject = '" + s + "' ";	
			resultSet = statement.executeQuery(query);
			if(resultSet.isBeforeFirst()) {
				while(resultSet.next()) {
					int msgID = resultSet.getInt(1);
					query = "DELETE FROM Messages WHERE msg_ID = " +msgID;
					int result = statement.executeUpdate(query);
				}
			}
		}	

		resultSet.close();
 	   	connection.commit();		
		
	}
	
}





