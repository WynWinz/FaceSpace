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
	
	public void displayFriends(int profileID) throws SQLException{
	
	}
	
	public void createGroup(String name, String description, int memLimit) throws SQLException{
	
	}
	
	public void addToGroup(int profileID, String groupName) throws SQLException{
		//check mem limit of group
		
	}
	
	public void sendMessageToUser(String subject, String body, int recipientID, int senderID) throws SQLException{
	
	}
	
	public void displayMessages(int profileID) throws SQLException{
	
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
	
	public void setupDemo(ArrayList<String> emails) throws SQLException {
		
		connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	   	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	    statement = connection.createStatement();
	    ResultSet resultSet = null;
	
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
		resultSet.close();
 	   	connection.commit();		
		
	}
	
}





