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
	private ArrayList<Integer> optPath;
	private int minimumSize;
	
	public FaceSpace() throws SQLException{
		setupDatabase();
	}
	
	public void createUser(String fname, String lname, String email, String dob) throws SQLException{

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
			try{			
				//increment group number of members
				numMembers++;
				query = "UPDATE Groups SET numMembers = "+numMembers+" WHERE group_ID =" +groupID;
				int result = statement.executeUpdate(query);
				
				//update membership
				query = "INSERT INTO Members (group_ID,profile_ID) VALUES ("+groupID+", "+profileID+")";			
				result = statement.executeUpdate(query);
				
				System.out.println("Member added.");
			}
			catch(SQLException e){
				System.out.println(e.getMessage());
			}
			
			connection.commit();

			Thread.sleep(1000);
		    resultSet.close();
		}	
		catch(Exception Ex)  
		{
			System.out.println("Machine Error: " + Ex.getMessage());
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
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
		//search areas: fname, lname, email, DOB
		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();

		    ArrayList<String> names = new ArrayList<String>();
		    String search[] = userSearch.split(" ");
		    StringBuilder fullName = new StringBuilder();

		    String searchAreas[] = {"fname", "lname", "email", "DOB"};
		    //search one keyword at a time
		    for(int i = 0; i < search.length; i++) {
		    	for(int j = 0; j < searchAreas.length; j++) {
		    		String curSearch = searchAreas[j];
			    	query = "SELECT fname, lname "
			    			+ "FROM Profiles "
			    			+ "WHERE " + curSearch +" LIKE '%"+ search[i] +"%'";
			    	resultSet = statement.executeQuery(query);    
					String profileFName=null, profileLName=null;
					while(resultSet.next())
			  		{
						profileFName = resultSet.getString(1).trim();		//remove whitespace on names
						profileLName = resultSet.getString(2).trim();
						fullName.append(profileFName);
						fullName.append(" ");
						fullName.append(profileLName);
						names.add(fullName.toString());
						fullName.setLength(0);
			 	   	}
		 	   	}	
		    }
			
	    	//print out names with some match
	    	System.out.println("The following people had matches in a significant field:");
	    	for(String n : names) {
	    		System.out.println(n);
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
	
	/*
	 * Steps:
	 * 1. get A's friends
	 * 2. check if B is one of rA's friends (1 hop)
	 * 3. get friends of A's friends
	 * 4. check if B is a friend of A's friends (2 hops)
	 * 5. get friends of friends of A's friends
	 * 6. check if B is friend of a friend of A's friends (3 hops)
	 * Concerns: -Did I say that right? haha
	 *			 -Do we need to do B to A as well?
	 *			 -Could do it with a graph but then we have to build 
	 *			  a graph of everyone in our database
	 */
	public void threeDegrees(String userAEmail, String userBEmail) throws SQLException{
		
		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();	
			
			//get user A info
	    	query = "SELECT profile_ID, fname, lname FROM Profiles WHERE email = '"+userAEmail+"'";
	    	ResultSet resultSet =statement.executeQuery(query);    
			int userAID = -1;
			String userAFName=null, userALName=null;
			while(resultSet.next())
	  		{
				userAID = resultSet.getInt(1);
				userAFName = resultSet.getString(2).trim();		//remove whitespace on names
				userALName = resultSet.getString(3).trim();
	 	   	}
	 	   	
	 	   	//get user B info
	 	   	query = "SELECT profile_ID, fname, lname FROM Profiles WHERE email = '"+userBEmail+"'";
	    	resultSet =statement.executeQuery(query);    
			int userBID = -1;
			String userBFName=null, userBLName=null;
			while(resultSet.next())
	  		{
				userBID = resultSet.getInt(1);
				userBFName = resultSet.getString(2).trim();		//remove whitespace on names
				userBLName = resultSet.getString(3).trim();
	 	   	}
	 	   	
			//User recursive backtracking to find a path between user a and b
			ArrayList<Integer> friendIDs = new ArrayList<Integer>();
			ArrayList<Integer> path = new ArrayList<Integer>();
			path.add(userAID);
			int numHops = 0;
			minimumSize = Integer.MAX_VALUE;
			optPath = new ArrayList<Integer>();
			path = findPath(path, numHops, userBID);
			numHops = 0;
			minimumSize = Integer.MAX_VALUE;
			
			//check if optimal path contains user B's ID
			if(!optPath.contains(userBID)){
				System.out.println("There is no path between these users");
			}
			else{
				//Get the user's names and print the path between user A and user B
				System.out.println("Shortest path between these users: \n");
				for(int i :optPath){
					query = "SELECT fname, lname FROM Profiles WHERE profile_ID = "+i;
					resultSet =statement.executeQuery(query);    
				
					while(resultSet.next())
					{
						if(i != userBID)
							System.out.print(resultSet.getString(1).trim()+" "+resultSet.getString(2).trim()+", ");
						else
							System.out.print(resultSet.getString(1).trim()+" "+resultSet.getString(2).trim()+".");
					}
				}

			}
			System.out.println();
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
	
	public ArrayList<Integer> findPath(ArrayList<Integer> path, int numHops, int userBID) throws SQLException{
		
		//Base case- we found user B and the path is shorter than our shortest path
		if(path.contains(userBID) && path.size() < minimumSize){
			minimumSize = path.size();
			optPath = new ArrayList<Integer>();
			for(int i : path){
				optPath.add(i);
			}
			return path;
		}
		//If 3 or more hops, backtrack
		else if(numHops > 2){
			return path;
		}
		//else, iterate through all the friends of this user	
		else{
			int currentID = path.get(path.size()-1);	
			
			//get list of current user's established friends
			ArrayList<Integer> friends = new ArrayList<Integer>();
			query = "SELECT friend_ID, established FROM friends WHERE profile_ID = "+currentID;
			resultSet =statement.executeQuery(query);   
			int established = 0, friendID = -1; 
			while(resultSet.next())
	  		{
				friendID = resultSet.getInt(1);
				established = resultSet.getInt(2);
				if(established == 1 && !path.contains(friendID)){
					friends.add(friendID);
				} 
	 	   	}	
			query = "SELECT profile_ID, established FROM friends WHERE friend_ID = "+currentID;
			resultSet =statement.executeQuery(query);    
			while(resultSet.next())
	  		{
				friendID = resultSet.getInt(1);
				established = resultSet.getInt(2);
				if(established == 1 && !path.contains(friendID)){
					friends.add(friendID);
				}
	 	   	}
	 	   	
	 	   	//iterate through friends
	 	   	for(int i=0; i<friends.size(); i++){
	 	   		path.add(friends.get(i));
	 	   		numHops++;
	 	   		int sizeBefore = path.size()-1;
	 	   		//recurse with path with new friend added
	 	   		path = findPath(path,numHops,userBID);
				//restore back to previous state 
				numHops--;
				for(int k=sizeBefore; k<path.size(); k++){
					path.remove(k);
				}
	 	   	}

			return path;
		}	
	}
	
	public void topMessagers(int numberofUsers, int months) throws SQLException{
		/*Display the top k users who have sent or received the highest number of messages during 
		the past x months. x and k should be an input parameters to this function.
		*/
	//generates current date -- then the date we are looking back to
	try {

			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		   	Date referenceDate = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(referenceDate); 
			c.add(Calendar.MONTH, (-months));

			String dateTo = ft.format(c.getTime());

		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	//    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();
		
			ArrayList<String> topSenders = new ArrayList<String>();
			ArrayList<String> topReceivers = new ArrayList<String>();

			//get top senders
			query = "SELECT sender_ID, COUNT(*) as numberOfSends FROM MESSAGES WHERE timeSent >= TIMESTAMP '"+ dateTo +"' GROUP BY sender_ID ORDER BY numberOfSends DESC";
	    	resultSet =statement.executeQuery(query);
			if(!resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			System.out.println();
				System.out.println("No senders...");
				return;
	 	   	}
	 	   	while(resultSet.next()){
	 	   		String add = resultSet.getInt("sender_ID") + "," + resultSet.getInt("numberOfSends");
				topSenders.add(add);
				System.out.println(add);
			}
			//get top receivers
			query = "SELECT recipient_ID, COUNT(*) as numberOfReceives FROM MESSAGES WHERE timeSent >= TIMESTAMP '"+ dateTo + "' GROUP BY recipient_ID ORDER BY numberOfReceives DESC";
	    	resultSet =statement.executeQuery(query);
			if(!resultSet.isBeforeFirst())			//returns true if there is data in result set
	  		{
	  			System.out.println();
				System.out.println("No receivers...");
				return;
	 	   	}
			while(resultSet.next()){
	 	   		String add = resultSet.getInt("recipient_ID") + "," + resultSet.getInt("numberOfReceives");
				topReceivers.add(add);
				System.out.println(add);

			}

			ArrayList<String> finalResults = new ArrayList<String>();
			//get sender names and print messages
			for(int i=0; i<numberofUsers; i++){
				finalResults.add(topSenders.get(i));
				//System.out.println(topSenders.get(i));
			}

			for(int i=0; i<numberofUsers; i++){
				for(int j=0; j<topReceivers.size(); j++)
				{
					String[] finList = finalResults.get(i).split(",");
					String[] rec = topReceivers.get(j).split(",");

					int finListInt = Integer.parseInt(finList[1]);
					int recInt = Integer.parseInt(rec[1]);

					if(finListInt<recInt)
					{
						finalResults.add(i,topReceivers.get(j));
						topReceivers.remove(j);
					}
					else if(j==0)
					{
						break;
					}
				}
			}

			for(int i=0; i<numberofUsers; i++)
			{
				System.out.println(finalResults.get(i));
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
	

//	SELECT sender_ID, COUNT(*) as numberOfSends FROM MESSAGES WHERE timeSent >= TIMESTAMP dateTo GROUP BY sender_ID ORDER BY numberOfSends DESC;

//	SELECT recipient_ID, COUNT(*) as numberOfReceives FROM MESSAGES WHERE timeSent >= TIMESTAMP '2005-05-05 10:10:10' GROUP BY recipient_ID ORDER BY numberOfReceives DESC;

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
//	   	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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

