package Skype;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Skype.Database.DatabaseCallsEntry;
import Skype.Database.DatabaseContactsEntry;
import Skype.Database.DatabaseMessagesEntry;
import Skype.Database.DatabaseSharedFilesStoredEntry;
import Skype.Database.DatabaseSharedLinksEntry;
import Skype.Database.PopulateDatabase;

public class Skype {

	boolean _databaseLoaded = false;
	private HashMap<String,DatabaseSharedLinksEntry> _shared_links = new HashMap<String,DatabaseSharedLinksEntry>();
	private HashMap<String,DatabaseSharedFilesStoredEntry> _shared_files = new HashMap<String,DatabaseSharedFilesStoredEntry>();
	private HashMap<String,DatabaseContactsEntry> _contacts = new HashMap<String,DatabaseContactsEntry>();
	private HashMap<String,ArrayList<DatabaseMessagesEntry>> _messages = new HashMap<String,ArrayList<DatabaseMessagesEntry>>();
	private HashMap<String,ArrayList<DatabaseCallsEntry>> _calls = new HashMap<String,ArrayList<DatabaseCallsEntry>>();
	private HashMap<String,DatabaseContactsEntry> _user_profile = new HashMap<String,DatabaseContactsEntry>();

	public Skype(String path){
		PopulateDatabase db = new PopulateDatabase(path);
		_databaseLoaded = db.getDbCreated();
		_shared_links = db.getSharedLinks();
		_shared_files = db.getSharedFiles();
		_contacts = db.getContacts();
		_messages = db.getMessages();
		_calls = db.getCalls();
		_user_profile = db.getUserProfile();
	}

	public boolean getDatabaseLoaded(){
		return _databaseLoaded;
	}

	public void testSkype(){
		System.out.println("\n - - - Testes de links partilhados - - -\n");

		for(Map.Entry<String,DatabaseSharedLinksEntry> entry: _shared_links.entrySet()){
			System.out.println("Link: " + entry.getValue().getLink());
			System.out.println("Time: " + entry.getValue().getTimestamp() + "\n");
		}

		System.out.println(" - - - Testes de ficheiros partilhados - - -\n");

		for(Map.Entry<String,DatabaseSharedFilesStoredEntry> entry: _shared_files.entrySet()){
			System.out.println("File Name: " + entry.getValue().getFileName());
			System.out.println("Local Path: " + entry.getValue().getLocalPath());
			System.out.println("User shared with: " + entry.getValue().getUserName());
		}

		System.out.println(" - - - Testes de contactos - - - \n");

		for(Map.Entry<String,DatabaseContactsEntry> entry: _contacts.entrySet()){
			System.out.println("Name: " + entry.getValue().getFullName());
			System.out.println("Skypename: " + entry.getValue().getSkypeName());
			System.out.println("Gender: " + entry.getValue().getGender());
			System.out.println("City: " + entry.getValue().getCity());
			System.out.println("Birthday: " + entry.getValue().getBirthday() + "\n");
		}

		System.out.println(" - - - Testes de chamadas - - - \n");

		for(Map.Entry<String,ArrayList<DatabaseCallsEntry>> list: _calls.entrySet()){
			for(DatabaseCallsEntry entry: list.getValue()){
				System.out.println("Call with: " + entry.getDispname());
				System.out.println("Skypename: " + entry.getIdentity());
				System.out.println("Time: " + entry.getStartTimestamp() + "\n");
			}
		}
		System.out.println(" - - - Testes de mensagens - - - \n");

		for(Map.Entry<String,ArrayList<DatabaseMessagesEntry>> list: _messages.entrySet()){
			System.out.println("Messages with: " + list.getKey());
			for(DatabaseMessagesEntry entry: list.getValue()){
				System.out.println("From: " + entry.getAuthor());
				System.out.println("Time: " + entry.getTimestamp());
				System.out.println("Message: " + entry.getMessage() + "\n");
			}
		}
	}

	public String[][] getBasicContactInfo(){
		String[][] arrays = new String[_contacts.entrySet().size()][4];
		int i = 0;
		for(Map.Entry<String,DatabaseContactsEntry> entry: _contacts.entrySet()){
			try{
				arrays[i][0] = entry.getValue().getFullName().toString();
			}
			catch(Exception e){
				arrays[i][0] = "";
			}

			try{
				arrays[i][1] = entry.getValue().getSkypeName().toString();
			}
			catch(Exception e){
				arrays[i][1] = "";
			}

			try{
				arrays[i][2] = entry.getValue().getGender().toString();
			}
			catch(Exception e){
				arrays[i][2] = "";
			}

			try{
				java.util.Date time=new java.util.Date((long)entry.getValue().getProfileTimestamp()*1000);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				arrays[i][3] = dateFormat.format(time);
			}
			catch(Exception e){
				arrays[i][3] = "";
			}
			i++;	
		}
		return arrays;
	}

	public String getSkypeName(){
		String skypeName = "";
		for(Map.Entry<String,DatabaseContactsEntry> entry: _user_profile.entrySet()){
			skypeName = entry.getValue().getFullName();
		}
		return skypeName;
	}

	public String getSkypeUserName(){
		String skypeUserName = "";
		for(Map.Entry<String,DatabaseContactsEntry> entry: _user_profile.entrySet()){
			skypeUserName = entry.getValue().getSkypeName();
		}
		return skypeUserName;
	}

	public HashMap<String,DatabaseContactsEntry> getUserProfile(){
		return _user_profile;
	}

	public String[][] getAllCalls(){
		int size = 0;
		for(Map.Entry<String,ArrayList<DatabaseCallsEntry>> entry: _calls.entrySet()){
			for(DatabaseCallsEntry entry1: entry.getValue()){
				size++;
			}
		}
		String[][] arrays = new String[size][5];
		int i = 0;
		for(Map.Entry<String,ArrayList<DatabaseCallsEntry>> entry: _calls.entrySet()){
			for(DatabaseCallsEntry entry1: entry.getValue()){
				try{
					arrays[i][0] = entry1.getDispname();
				}
				catch(Exception e){
					arrays[i][0] = "";
				}

				try{
					arrays[i][1] = entry1.getIdentity();
				}
				catch(Exception e){
					arrays[i][1] = "";
				}

				try{
					arrays[i][2] = entry1.getWhoCalls();
				}
				catch(Exception e){
					arrays[i][2] = "";
				}

				try{
					java.util.Date time=new java.util.Date((long)entry1.getStartTimestamp()*1000);
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					arrays[i][3] = dateFormat.format(time);
				}
				catch(Exception e){
					arrays[i][3] = "";
				}
				arrays[i][4] = ""+(i+1);
				i++;
			}
		}
		return arrays;
	}

	public String[][] getAllMessages(){
		int size = 0;
		for(Map.Entry<String,ArrayList<DatabaseMessagesEntry>> entry: _messages.entrySet()){
			for(DatabaseMessagesEntry entry1: entry.getValue()){
				size++;
			}
		}
		String[][] arrays = new String[size][5];
		int i = 0;
		for(Map.Entry<String,ArrayList<DatabaseMessagesEntry>> entry: _messages.entrySet()){
			for(DatabaseMessagesEntry entry1: entry.getValue()){
				try{
					arrays[i][0] = entry1.getChatname();
				}
				catch(Exception e){
					arrays[i][0] = "";
				}

				try{
					arrays[i][1] = entry1.getAuthor();
				}
				catch(Exception e){
					arrays[i][1] = "";
				}

				try{
					arrays[i][2] = entry1.getFromDispname();
				}
				catch(Exception e){
					arrays[i][2] = "";
				}

				try{
					arrays[i][3] = entry1.getMessage();
				}
				catch(Exception e){
					arrays[i][3] = "";
				}

				try{
					java.util.Date time=new java.util.Date((long)entry1.getTimestamp()*1000);
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					arrays[i][4] = dateFormat.format(time);
				}
				catch(Exception e){
					arrays[i][4] = "";
				}
				i++;
			}
		}
		return arrays;
	}

	public String[][] getAllLinks(){
		String[][] arrays = new String[_shared_links.entrySet().size()][3];
		int i = 0;
		for(Map.Entry<String,DatabaseSharedLinksEntry> entry: _shared_links.entrySet()){
			try{
				arrays[i][0] = entry.getValue().getLink();
			}
			catch(Exception e){
				arrays[i][0] = "";
			}

			try{
				arrays[i][1] = entry.getValue().getType();
			}
			catch(Exception e){
				arrays[i][1] = "";
			}
			try{
				java.util.Date time=new java.util.Date((long)entry.getValue().getTimestamp()*1000);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				arrays[i][2] = dateFormat.format(time);
			}
			catch(Exception e){
				arrays[i][2] = "";
			}
			i++;
		}
		return arrays;
	}

	public String[][] getAllFiles(){
		String[][] arrays = new String[_shared_files.entrySet().size()][4];
		int i = 0;
		for(Map.Entry<String,DatabaseSharedFilesStoredEntry> entry: _shared_files.entrySet()){
			try{
				arrays[i][0] = entry.getValue().getFileName();
			}
			catch(Exception e){
				arrays[i][0] = "";
			}

			try{
				arrays[i][1] = entry.getValue().getLocalPath();
			}
			catch(Exception e){
				arrays[i][1] = "";
			}
			
			try{
				arrays[i][2] = entry.getValue().getUserName();
			}
			catch(Exception e){
				arrays[i][2] = "";
			}
			
			try{
				arrays[i][3] = entry.getValue().getSize().toString();
			}
			catch(Exception e){
				arrays[i][3] = "";
			}
			i++;
		}
		return arrays;
	}
}