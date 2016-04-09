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

	private static Connection connection; 		//used to hold the jdbc connection to the DB
    private Statement statement; 				//used to create an instance of the connection
    private PreparedStatement prepStatement; 	//used to create a prepared statement, that will be later reused
    private ResultSet resultSet; 				//used to hold the result of your query (if one exists)
    private String query;  						//this will hold the query we are using

	FaceSpace fs;

	public static void main(String[] args) {
		fs = new FaceSpace();
		setupDatabase();
		
	}
	
	public void setupDatabase(){
		
		String username, password;
		username = "username"; //This is your username in oracle
		password = "password"; //This is your password in oracle
	
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
	
}









