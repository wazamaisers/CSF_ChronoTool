package Drive;

import java.util.*;

import org.apache.commons.io.FilenameUtils;

public class DatabaseAnalisis {
	
	private ArrayList<DatabaseEntry> _final_list = new ArrayList<DatabaseEntry>();
	
	public HashMap<String, Integer> getExtensions(ArrayList<DatabaseEntry> folder){
		
		HashMap<String, Integer> extensions = new HashMap<String, Integer>();
		
		for(DatabaseEntry entry: folder){
			
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
	
	public ArrayList<DatabaseEntry> getArrayOfChildren (String parent_doc_id, 
			HashMap <String, ArrayList<DatabaseEntry>> database_schema){
		
		ArrayList<DatabaseEntry> empty_list = new ArrayList<DatabaseEntry>();
		_final_list = empty_list;
		getArrayOfChildrenAux(parent_doc_id,database_schema);
		return _final_list;
	}
	
	public void getArrayOfChildrenAux (String parent_doc_id, 
			HashMap <String, ArrayList<DatabaseEntry>> database_schema){
		
		List<DatabaseEntry> initial_list = database_schema.get(parent_doc_id);
		for(DatabaseEntry entry: initial_list){
			if(database_schema.containsKey(entry.getDocId())){
				getArrayOfChildrenAux(entry.getDocId(), database_schema);
			}
			else{
				_final_list.add(entry);
			}
		}
		return;
	}
	
	public DatabaseEntry getEntryByDocId(String doc_id, ArrayList<DatabaseEntry> database_entrys){
		for(DatabaseEntry entry: database_entrys){
			if(doc_id.equals(entry.getDocId())){
				return entry;
			}
		}
		return null;
	}
	
	public String getDirectoryDocIdByPath(String path,ArrayList<DatabaseEntry> database_entrys){
		String[] splited = path.split("/");
		List<DatabaseEntry> possible_entrys = new ArrayList<DatabaseEntry>();
		
		if(path.equals("root")){
			return "root";
		}
		
		for(DatabaseEntry entry: database_entrys){
			if(entry.getFilename().equals(splited[splited.length-1])){
				possible_entrys.add(entry);
			}
		}
		
		for(DatabaseEntry entry1: possible_entrys){
			int i = splited.length - 1;
			while(i>0){
				DatabaseEntry entry1_parent_doc = getEntryByDocId(entry1.getParentDocId(),database_entrys);
				if(splited[i-1].equals(entry1_parent_doc.getFilename())){
					entry1_parent_doc = getEntryByDocId(entry1_parent_doc.getParentDocId(),database_entrys);
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