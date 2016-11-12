package Drive.Database;

public class DatabaseSyncConfigEntry {

	private String _user_email = null;
	private String _version = null;

	public DatabaseSyncConfigEntry(String user_email, String version){
		
		_user_email = user_email;
		_version = version;
	}
	
	public String getUserEmail(){
		return _user_email;
	}
	
	public String getVersion(){
		return _version;
	}
}