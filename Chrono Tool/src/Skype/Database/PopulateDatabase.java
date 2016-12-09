package Skype.Database;
import java.sql.*;
import java.util.*;

public class PopulateDatabase {
	
	private HashMap<String,DatabaseSharedLinksEntry> _shared_links = new HashMap<String,DatabaseSharedLinksEntry>();
	private HashMap<String,DatabaseSharedFilesStoredEntry> _shared_files = new HashMap<String,DatabaseSharedFilesStoredEntry>();
	private HashMap<String,DatabaseContactsEntry> _contacts = new HashMap<String,DatabaseContactsEntry>();
	private HashMap<String,ArrayList<DatabaseMessagesEntry>> _messages = new HashMap<String,ArrayList<DatabaseMessagesEntry>>();
	private HashMap<String,ArrayList<DatabaseCallsEntry>> _calls = new HashMap<String,ArrayList<DatabaseCallsEntry>>();
	private HashMap<String,DatabaseContactsEntry> _user_profile = new HashMap<String,DatabaseContactsEntry>();
	
	private Connection _sh_links = null;
	private Connection _sh_files = null;
	private Connection _contacts1 = null;
	private Connection _contacts2 = null;
	private Connection _contacts3 = null;
	private Connection _contacts4 = null;
	private Connection _msgs = null;
	private Connection _clls = null;
	
	private Statement _stmt1 = null;
	private Statement _stmt2 = null;
	private Statement _stmt3 = null;
	private Statement _stmt4 = null;
	private Statement _stmt5 = null;
	private Statement _stmt6 = null;
	private Statement _stmt7 = null;
	private Statement _stmt8 = null;
	private Statement _stmt9 = null;
	private Statement _stmt10 = null;
	
	private boolean _dbCreated = false;

	public PopulateDatabase(String path){
		
		try {
			Class.forName("org.sqlite.JDBC");
			
			////////////////////////////////////// SHARED LINKS DATABASE ////////////////////////////////////
			
			_sh_links = DriverManager.getConnection("jdbc:sqlite:"+ path + "/media_messaging/media_cache_v3/asyncdb/cache_db.db");
			_sh_links.setAutoCommit(false);
			_stmt1 = _sh_links.createStatement();
			
			ResultSet rs1 = _stmt1.executeQuery("SELECT * FROM assets;");
			
			while (rs1.next()) {
				String key = rs1.getString("key");
				String sub_key = rs1.getString("sub_key");
				Integer access_time = Integer.parseInt((rs1.getString("access_time")).substring(0, 10));
				Integer size = rs1.getInt("actual_size");
				
				DatabaseSharedLinksEntry database_entry = new DatabaseSharedLinksEntry(key,sub_key,access_time,size);
				_shared_links.put(key, database_entry);
			}
			
			_stmt1.close();
			_sh_links.close();
			rs1.close();
			
			////////////////////////////////////// SHARED FILES STORED DATABASE ////////////////////////////////////
			
			_sh_files = DriverManager.getConnection("jdbc:sqlite:"+ path +"/media_messaging/storage_db/asyncdb/storage_db.db");
			_sh_files.setAutoCommit(false);
			
			_stmt2 = _sh_files.createStatement();
			_stmt3 = _sh_files.createStatement();
			_stmt4 = _sh_files.createStatement();
			
			ResultSet rs2 = _stmt2.executeQuery("SELECT * FROM contents;");
			ResultSet rs3 = _stmt3.executeQuery("SELECT * FROM document_permissions;");
			ResultSet rs4 = _stmt4.executeQuery("SELECT * FROM documents;");
			
			
			while (rs2.next() && rs3.next() && rs4.next()) {
				String local_path = rs2.getString("local_path");
				Integer size = rs2.getInt("content_size");
				String user_name = rs3.getString("user_name");
				String uri = rs4.getString("uri");
				String file_name = rs4.getString("file_name");
				
				DatabaseSharedFilesStoredEntry database_entry = new DatabaseSharedFilesStoredEntry(local_path, size, user_name, uri, file_name);
				_shared_files.put(local_path, database_entry);
			}
			
			_stmt2.close();
			_stmt3.close();
			_stmt4.close();
			_sh_files.close();
			rs2.close();
			rs3.close();
			rs4.close();
			
			
			////////////////////////////////////// CONTACTS DATABASE ////////////////////////////////////
			
			_contacts1 = DriverManager.getConnection("jdbc:sqlite:"+ path + "/main.db");
			_contacts1.setAutoCommit(false);
			
			_contacts2 = DriverManager.getConnection("jdbc:sqlite:"+ path + "/eascache.db");
			_contacts2.setAutoCommit(false);
			
			_stmt5 = _contacts1.createStatement();
			_stmt6 = _contacts2.createStatement();
			
			ResultSet rs5 = _stmt5.executeQuery("SELECT * FROM Contacts;");
			
			while (rs5.next()) {
				String skypename = rs5.getString("skypename");
				String fullname = rs5.getString("fullname");
				Integer birthday = rs5.getInt("birthday");
				Integer gend = rs5.getInt("gender");
				
				String gender = "";
				if (gend.equals(1)){
					gender = "Male";
				}
				
				if (gend.equals(2)){
					gender = "Female";
				}
				
				
				String languages = rs5.getString("languages");
				String country = rs5.getString("country");
				String province = rs5.getString("province");
				String city = rs5.getString("city");
				String phone_home = rs5.getString("phone_home");
				String phone_office = rs5.getString("phone_office");
				String phone_mobile = rs5.getString("phone_mobile");
				String emails = rs5.getString("emails");
				String hashed_emails = rs5.getString("hashed_emails");
				String homepage = rs5.getString("homepage");
				String about = rs5.getString("about");
				
				ResultSet rs6 = _stmt6.executeQuery("SELECT * FROM fullobjects WHERE NickName = '" + skypename + "';");
				String avatar_image = "";
				if(!rs6.next()){
					avatar_image = null;
				}
				else{
					avatar_image = rs6.getString("UserTileUrlM");
				}
				rs6.close();
				
				String mood_text = rs5.getString("mood_text");
				String rich_mood_text = rs5.getString("rich_mood_text");
				Integer timezone = rs5.getInt("timezone");
				Integer profile_timestamp = rs5.getInt("profile_timestamp");
				Integer last_online_timestamp = rs5.getInt("lastonline_timestamp");
				String displayname = rs5.getString("displayname");
				String external_id = rs5.getString("external_id");
				
				DatabaseContactsEntry database_entry = new DatabaseContactsEntry(skypename, fullname, birthday, gender, languages,
						country, province, city, phone_home, phone_office, phone_mobile, emails, hashed_emails, homepage, about,
						avatar_image, mood_text, rich_mood_text, timezone, profile_timestamp, last_online_timestamp, displayname, 
						external_id);
				_contacts.put(skypename, database_entry);
				
			}
			
			_stmt5.close();
			_stmt6.close();
			_contacts1.close();
			_contacts2.close();
			rs5.close();
			
			////////////////////////////////////// MESSAGES DATABASE ////////////////////////////////////
			
			_msgs = DriverManager.getConnection("jdbc:sqlite:"+ path + "/main.db");
			_msgs.setAutoCommit(false);
			
			_stmt7 = _msgs.createStatement();
			ResultSet rs7 = _stmt7.executeQuery("SELECT * FROM Messages ORDER BY timestamp ASC;");
			
			while (rs7.next()) {
				Integer convo_id = rs7.getInt("convo_id");
				String chatname = rs7.getString("chatname");
				if(String.valueOf(chatname.charAt(0)).equals("#")){
					chatname = rs7.getString("dialog_partner");
				}
				String author = rs7.getString("author");
				String from_dispname = rs7.getString("from_dispname");
				Integer timestamp = rs7.getInt("timestamp");
				String body_xml = rs7.getString("body_xml");
				String identities = rs7.getString("identities");
				String reason = rs7.getString("reason");
				
				DatabaseMessagesEntry database_entry = new DatabaseMessagesEntry(convo_id, chatname, author, from_dispname,
						timestamp, body_xml, identities, reason);
				
				if(_messages.containsKey(chatname)){
					ArrayList<DatabaseMessagesEntry> list = _messages.get(chatname);
					list.add(database_entry);
					_messages.put(chatname, list);
				}
				
				if(!_messages.containsKey(chatname)){
					ArrayList<DatabaseMessagesEntry> list = new ArrayList<DatabaseMessagesEntry>();
					list.add(database_entry);
					_messages.put(chatname, list);
				}
			}
			
			_stmt7.close();
			_msgs.close();
			rs7.close();
			
			////////////////////////////////////// CALLS DATABASE ////////////////////////////////////
			
			_clls = DriverManager.getConnection("jdbc:sqlite:"+ path + "/main.db");
			_clls.setAutoCommit(false);
			
			_stmt8 = _clls.createStatement();
			ResultSet rs8 = _stmt8.executeQuery("SELECT * FROM CallMembers;");
			
			while (rs8.next()) {
				if(rs8.getInt("is_permanent")==0){
					continue;
				}
				String identity = rs8.getString("identity");
				String dispname = rs8.getString("dispname");
				String type = rs8.getString("type");
				String guid = rs8.getString("guid");
				Integer start_timestamp = rs8.getInt("start_timestamp");
				Integer creation_timestamp = rs8.getInt("creation_timestamp");
				
				DatabaseCallsEntry database_entry = new DatabaseCallsEntry(identity, dispname, type, guid, start_timestamp, 
						creation_timestamp);
				
				if(_calls.containsKey(identity)){
					ArrayList<DatabaseCallsEntry> list = _calls.get(identity);
					list.add(database_entry);
					_calls.put(identity, list);
				}
				
				if(!_calls.containsKey(identity)){
					ArrayList<DatabaseCallsEntry> list = new ArrayList<DatabaseCallsEntry>();
					list.add(database_entry);
					_calls.put(identity, list);
				}
			}
			
			_stmt8.close();
			_clls.close();
			rs8.close();
			
			//////////////////////////////////////USER PROFILE DATABASE ////////////////////////////////////
			_contacts3 = DriverManager.getConnection("jdbc:sqlite:"+ path + "/main.db");
			_contacts3.setAutoCommit(false);
			
			_stmt9 = _contacts3.createStatement();
			
			ResultSet rs9 = _stmt9.executeQuery("SELECT * FROM Accounts;");
			
			_contacts4 = DriverManager.getConnection("jdbc:sqlite:"+ path + "/eascache.db");
			_contacts4.setAutoCommit(false);
			
			_stmt10 = _contacts4.createStatement();
			
			while (rs9.next()) {
				String skypename = rs9.getString("skypename");
				String fullname = rs9.getString("fullname");
				Integer birthday = rs9.getInt("birthday");
				Integer gend = rs9.getInt("gender");
				
				String gender = "";
				if (gend.equals(1)){
					gender = "Male";
				}
				
				if (gend.equals(2)){
					gender = "Female";
				}
				
				
				String languages = rs9.getString("languages");
				String country = rs9.getString("country");
				String province = rs9.getString("province");
				String city = rs9.getString("city");
				String phone_home = rs9.getString("phone_home");
				String phone_office = rs9.getString("phone_office");
				String phone_mobile = rs9.getString("phone_mobile");
				String emails = rs9.getString("emails");
				String homepage = rs9.getString("homepage");
				String about = rs9.getString("about");				
				Integer timezone = rs9.getInt("timezone");
				Integer profile_timestamp = rs9.getInt("profile_timestamp");
				Integer last_online_timestamp = rs9.getInt("lastonline_timestamp");
				
				ResultSet rs10 = _stmt10.executeQuery("SELECT * FROM fullobjects WHERE NickName = '" + skypename + "';");
				String avatar_image = "";
				if(!rs10.next()){
					avatar_image = null;
				}
				else{
					avatar_image = rs10.getString("UserTileUrlL");
				}
				rs10.close();
				
				DatabaseContactsEntry database_entry = new DatabaseContactsEntry(skypename, fullname, birthday, gender, languages,
						country, province, city, phone_home, phone_office, phone_mobile, emails, null, homepage, about,
						avatar_image, null, null, timezone, profile_timestamp, last_online_timestamp, null, null);
				_user_profile.put(skypename, database_entry);
			}
			
			_stmt9.close();
			_stmt10.close();
			_contacts3.close();
			_contacts4.close();
			rs9.close();

			System.out.println("passa2");
			_dbCreated = true;
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			//System.exit(0);
		}
	}
	
	public boolean getDbCreated(){
		return _dbCreated;
	}
	
	public HashMap<String,DatabaseSharedLinksEntry> getSharedLinks(){
		return _shared_links;
	}
	
	public HashMap<String,DatabaseSharedFilesStoredEntry> getSharedFiles(){
		return _shared_files;
	}
	
	public HashMap<String,DatabaseContactsEntry> getContacts(){
		return _contacts;
	}
	
	public HashMap<String,ArrayList<DatabaseMessagesEntry>> getMessages(){
		return _messages;
	}
	
	public HashMap<String,ArrayList<DatabaseCallsEntry>> getCalls(){
		return _calls;
	}
	
	public HashMap<String,DatabaseContactsEntry> getUserProfile(){
		return _user_profile;
	}
}