package Drive;
import java.sql.*;

public class UserDetails {

	private Connection _sync_config = null;
	private Statement _stmt = null;
	private String _email = null;
	private String _version = null;

	public UserDetails(String username){

		try {
			Class.forName("org.sqlite.JDBC");
			_sync_config = DriverManager.getConnection("jdbc:sqlite:C:/Users/" + username + "/AppData/Local/Google/Drive/user_default/sync_config.db");
			_sync_config.setAutoCommit(false);
			_stmt = _sync_config.createStatement();

			ResultSet rs = _stmt.executeQuery("SELECT data_value FROM data WHERE entry_key = 'user_email';");
			_email = rs.getString("data_value");

			ResultSet rs1 = _stmt.executeQuery("SELECT data_value FROM data WHERE entry_key = 'highest_app_version';");
			_version = rs1.getString("data_value");
			
			System.out.println("The user email is: " + _email);
			System.out.println("The version of Google Drive is: " + _version);

			rs.close();
			rs1.close();
			_stmt.close();
			_sync_config.close();

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	public String getUserMail(){
		return _email;
	}
	
	public String getDriveVersion(){
		return _version;
	}
}