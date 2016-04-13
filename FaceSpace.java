/*
 * Authors:
 * Ryan Ching
 * Chris Corsi
 * Edwin Mellett
 *
 * Date Started:
 * 4/9/16
*/

public class FaceSpace {
	
	int numUsers;
	int numGroups;
	
	private static Connection connection; //used to hold the jdbc connection to the DB
    private Statement statement; //used to create an instance of the connection
    private PreparedStatement prepStatement; //used to create a prepared statement, that will be later reused
    private ResultSet resultSet; //used to hold the result of your query (if one exists)
	
	public FaceSpace(){
		setupDatabase();
	}
	
	public void createUser(String name, String email, String dob){
			
		
		
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
	
	public void setupDatabase(){
		
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





