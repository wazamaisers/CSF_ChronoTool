package Skype.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PopulateDatabase {
	
	private Connection _snapshot = null;
	private Statement _stmt1 = null;
	

	public PopulateDatabase(String username){
		
		try {
			Class.forName("org.sqlite.JDBC");
			_snapshot = DriverManager.getConnection("jdbc:sqlite:C:/Users/" + username + "/AppData/Local/Google/Drive/user_default/snapshot.db");
			_snapshot.setAutoCommit(false);
			_stmt1 = _snapshot.createStatement();
			
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
}