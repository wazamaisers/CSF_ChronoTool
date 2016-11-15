package Skype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Skype.Database.DatabaseCallsEntry;
import Skype.Database.DatabaseContactsEntry;
import Skype.Database.DatabaseMessagesEntry;
import Skype.Database.DatabaseSharedFilesStoredEntry;
import Skype.Database.DatabaseSharedLinksEntry;
import Skype.Database.PopulateDatabase;

public class Skype {
	
	private HashMap<String,DatabaseSharedLinksEntry> _shared_links = new HashMap<String,DatabaseSharedLinksEntry>();
	private HashMap<String,DatabaseSharedFilesStoredEntry> _shared_files = new HashMap<String,DatabaseSharedFilesStoredEntry>();
	private HashMap<String,DatabaseContactsEntry> _contacts = new HashMap<String,DatabaseContactsEntry>();
	private HashMap<Integer,DatabaseMessagesEntry> _messages = new HashMap<Integer,DatabaseMessagesEntry>();
	private HashMap<String,ArrayList<DatabaseCallsEntry>> _calls = new HashMap<String,ArrayList<DatabaseCallsEntry>>();
	
	public Skype(String username, String skypeusername){
		PopulateDatabase db = new PopulateDatabase(username, skypeusername);
		_shared_links = db.getSharedLinks();
		_shared_files = db.getSharedFiles();
		_contacts = db.getContacts();
		_messages = db.getMessages();
		_calls = db.getCalls();
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
	}
}