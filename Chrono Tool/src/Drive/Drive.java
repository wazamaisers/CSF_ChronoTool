package Drive;

import java.util.*;
import org.apache.commons.io.FilenameUtils;

import Drive.Database.DatabaseSnapshotEntry;
import Drive.Database.DatabaseSyncConfigEntry;
import Drive.Database.PopulateDatabase;

public class Drive {

	private HashMap<String,ArrayList<DatabaseSnapshotEntry>> _database_schema = new HashMap<String, ArrayList<DatabaseSnapshotEntry>>();
	private ArrayList<DatabaseSnapshotEntry> _database_entrys = new ArrayList<DatabaseSnapshotEntry>();
	private DatabaseSyncConfigEntry _user_details = null;

	public Drive(String username){

		PopulateDatabase db = new PopulateDatabase(username);
		_database_schema = db.getDatabaseSchema();
		_database_entrys = db.getDatabaseEntrys();
		_user_details = db.getUserDetails();
	}

	private ArrayList<DatabaseSnapshotEntry> _final_list = new ArrayList<DatabaseSnapshotEntry>();

	public HashMap<String, Integer> getExtensions(String path){

		String doc_id = getDirectoryDocIdByPath(path);
		HashMap<String, Integer> extensions = new HashMap<String, Integer>();

		for(DatabaseSnapshotEntry entry: _database_schema.get(doc_id)){

			if(entry.getResourceType().equals("file")){
				String filename = entry.getFilename();
				String extension = FilenameUtils.getExtension(filename);
				if(!extension.equals("")){
					if(extensions.containsKey(extension)){
						extensions.put(extension, extensions.get(extension)+1);
					}
					else{
						extensions.put(extension, 1);
					}
				}
			}
		}
		return extensions;
	}

	public HashMap<String, Integer> getChildrenExtensions(String path){
		
		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> children = getArrayOfChildren(doc_id);

		HashMap<String, Integer> extensions = new HashMap<String, Integer>();

		for(DatabaseSnapshotEntry entry: children){

			if(entry.getResourceType().equals("file")){
				String filename = entry.getFilename();
				String extension = FilenameUtils.getExtension(filename);
				if(!extension.equals("")){
					if(extensions.containsKey(extension)){
						extensions.put(extension, extensions.get(extension)+1);
					}
					else{
						extensions.put(extension, 1);
					}
				}
			}
		}
		return extensions;
	}	

	public ArrayList<DatabaseSnapshotEntry> getArrayOfChildren (String parent_doc_id){

		ArrayList<DatabaseSnapshotEntry> empty_list = new ArrayList<DatabaseSnapshotEntry>();
		_final_list = empty_list;
		getArrayOfChildrenAux(parent_doc_id);
		return _final_list;
	}

	public void getArrayOfChildrenAux (String parent_doc_id){

		List<DatabaseSnapshotEntry> initial_list = _database_schema.get(parent_doc_id);
		for(DatabaseSnapshotEntry entry: initial_list){
			if(_database_schema.containsKey(entry.getDocId())){
				getArrayOfChildrenAux(entry.getDocId());
			}
			else{
				_final_list.add(entry);
			}
		}
		return;
	}

	public DatabaseSnapshotEntry getEntryByDocId(String doc_id){

		for(DatabaseSnapshotEntry entry: _database_entrys){
			if(doc_id.equals(entry.getDocId())){
				return entry;
			}
		}
		return null;
	}

	public String getDirectoryDocIdByPath(String path){
		String[] splited = path.split("/");
		List<DatabaseSnapshotEntry> possible_entrys = new ArrayList<DatabaseSnapshotEntry>();

		if(path.equals("root")){
			return "root";
		}

		for(DatabaseSnapshotEntry entry: _database_entrys){
			if(entry.getFilename().equals(splited[splited.length-1])){
				possible_entrys.add(entry);
			}
		}

		for(DatabaseSnapshotEntry entry1: possible_entrys){
			int i = splited.length - 1;
			DatabaseSnapshotEntry entry1_parent_doc = getEntryByDocId(entry1.getParentDocId());
			while(i>0){
				if(splited[i-1].equals(entry1_parent_doc.getFilename())){
					entry1_parent_doc = getEntryByDocId(entry1_parent_doc.getParentDocId());
					i--;
				}
				else{
					break;
				}
			}
			if(i==0){
				return entry1.getDocId();
			}
		}
		return null;
	}
}