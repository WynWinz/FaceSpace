/*
 * Authors:
 * Ryan Ching
 * Chris Corsi
 * Edwin Mellett
 *
 * Date Started:
 * 4/9/16
*/

import java.sql.*;  		//import the file containing definitions for the parts
import java.text.ParseException;
import oracle.jdbc.*;		//needed by java for database connection and manipulation

public class FaceSpace {
	
	int numUsers;
	int numGroups;
	
	private static Connection connection; //used to hold the jdbc connection to the DB
    private Statement statement; //used to create an instance of the connection
    private PreparedStatement prepStatement; //used to create a prepared statement, that will be later reused
    private ResultSet resultSet; //used to hold the result of your query (if one exists)
	private String query;
	
	public FaceSpace(){
		setupDatabase();
	}
	
	public void createUser(String name, String email, String dob) throws SQLException{

		try {
		    connection.setAutoCommit(false); //the default is true and every statement executed is considered a transaction.
	    	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		    statement = connection.createStatement();
	    
		    query = "update class set max_num_students = 5 where classid = 1";
		    int result = statement.executeUpdate(query); 
	    
		    //sleep for 5 seconds, so that we have time to switch to the other transaction
		    Thread.sleep(5000);
	    
		    query ="select * from CLASS";
		    ResultSet resultSet =statement.executeQuery(query);
	    	System.out.println("ClassID\tMAX_NUM_STUDENTS\tCUR_NUM_STUDENTS");
	  	  while(resultSet.next())
	  	  {
	 	   	System.out.println(resultSet.getLong(1)+"\t"+resultSet.getLong(2)+"\t"+resultSet.getDouble(3));
	 	   }
	    
	    /*
	     * Releases this ResultSet object's database and JDBC resources immediately instead of waiting for this to happen when it is automatically closed.
	     */
		    resultSet.close();
		    //now rollback to end the transaction and release the lock on data. 
	    	//You can use connection.commit() instead for this example, I just don't want to change the value
	  	  connection.rollback();
	  	  System.out.println("Transaction Rolled Back!");
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

	public void initiateFriendship(int profileID, int friendID){
		//determine if established and date established in here
	}
	
	public void establishFriendship(int profileID, int friendID){
		
	}
	
	public void displayFriends(int profileID){
	
	}
	
	public void createGroup(String name, String description, int memLimit){
	
	}
	
	public void addToGroup(int profileID, String groupName){
		//check mem limit of group
		
	}
	
	public void sendMessageToUser(String subject, String body, int recipientID, int senderID){
	
	}
	
	public void displayMessages(int profileID){
	
	}
	
	public void searchForUser(String userSearch){
	
	}
	
	public void threeDegrees(int userA, int userB){
	
	}
	
	public void topMessagers(){
	
	}
	
	public void dropUser(int profileID){
	
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
		finally
		{
			 /* NOTE: the connection should be created once and used through out the whole project;
			 * Is very expensive to open a connection therefore you should not close it after every operation on database */
			connection.close();
		}
	}
	
	public void closeConnection(){
		try {
			connection.close();
	    }
	    catch(Exception Ex)  {
			System.out.println("Error connecting to database.  Machine Error: " + Ex.toString());
	    }
	}
	
}





