package Drive;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.io.FilenameUtils;

import Drive.Database.DatabaseSnapshotEntry;
import Drive.Database.DatabaseSyncConfigEntry;
import Drive.Database.PopulateDatabase;

public class Drive {

	boolean _databaseLoaded = false;
	private HashMap<String,ArrayList<DatabaseSnapshotEntry>> _database_schema = new HashMap<String, ArrayList<DatabaseSnapshotEntry>>();
	private ArrayList<DatabaseSnapshotEntry> _database_entrys = new ArrayList<DatabaseSnapshotEntry>();
	private DatabaseSyncConfigEntry _user_details = null;
	private ArrayList<DatabaseSnapshotEntry> _final_list = new ArrayList<DatabaseSnapshotEntry>();

	public Drive(String path){

		PopulateDatabase db = new PopulateDatabase(path);
		_databaseLoaded = db.getDbCreated();
		_database_schema = db.getDatabaseSchema();
		_database_entrys = db.getDatabaseEntrys();
		_user_details = db.getUserDetails();
	}
	
	public boolean getDatabaseLoaded(){
		return _databaseLoaded;
	}

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
	
	public List<DatabaseSnapshotEntry> getFilesWithinFolder(String path){
		String doc_id = getDirectoryDocIdByPath(path);
		List<DatabaseSnapshotEntry> files_list = _database_schema.get(doc_id);
		return files_list;
	}
	
	public String getUserMail(){
		return _user_details.getUserEmail();
	}
	
	public String getDriveVersion(){
		return _user_details.getVersion();
	}
	
	public static float getPercentage(int questions, int correct) {
	    float proportionCorrect = ((float) correct) / ((float) questions);
	    return Math.round(proportionCorrect * 100);
	}
	
	public HashMap<String, Integer> getTimes(){
		
		ArrayList<Integer> timestamps = new ArrayList<Integer>();
		HashMap<String,Integer> _times = new HashMap<String, Integer>();
		
		for(DatabaseSnapshotEntry entry: _database_entrys){
			timestamps.add(entry.getModified());
		}
		
		_times.put("Dawn",0);  //2h - 6h
		_times.put("Morning",0);  //6h - 12h
		_times.put("Afternoon",0); //12h - 17h
		_times.put("Evening",0); // 17h - 20h
		_times.put("Night",0); //20h - 2h
		
		int total = 0;
		for (Integer item : timestamps) {
			Date date = new Date(item*1000L);
			SimpleDateFormat hour = new SimpleDateFormat("H");
			
			if ((Integer.parseInt(hour.format(date)) >= 2) && (Integer.parseInt(hour.format(date))) < 6){
				_times.put("Dawn", _times.get("Dawn")+1);
			}
			
			if ((Integer.parseInt(hour.format(date)) >= 6) && (Integer.parseInt(hour.format(date))) < 12){
				_times.put("Morning", _times.get("Morning")+1);
			}
			
			if ((Integer.parseInt(hour.format(date)) >= 12) && (Integer.parseInt(hour.format(date))) < 17){
				_times.put("Afternoon", _times.get("Afternoon")+1);
			}
			
			if ((Integer.parseInt(hour.format(date)) >= 17) && (Integer.parseInt(hour.format(date))) < 20){
				_times.put("Evening", _times.get("Evening")+1);
			}
			
			if ((Integer.parseInt(hour.format(date)) >= 20) && (Integer.parseInt(hour.format(date))) < 24
					|| (Integer.parseInt(hour.format(date)) >= 0) && (Integer.parseInt(hour.format(date))) < 2){
				_times.put("Night", _times.get("Night")+1);
			}	
		}
		
		total = _times.get("Dawn") + _times.get("Morning") + _times.get("Afternoon") + _times.get("Evening") +
				_times.get("Night");
		
		System.out.println("Dawn :" + getPercentage(total, _times.get("Dawn")) + "%");
		System.out.println("Morning :" + getPercentage(total, _times.get("Morning")) + "%");
		System.out.println("Afternoon :" + getPercentage(total, _times.get("Afternoon")) + "%");
		System.out.println("Evening :" + getPercentage(total, _times.get("Evening")) + "%");
		System.out.println("Night :" + getPercentage(total, _times.get("Night")) + "%");
		
		return _times;
	}

	public String getPathFromTreePath(TreePath tp){
		
		String list = tp.toString();
		char[] list_ch = list.toCharArray();
		List<Character> path_ch = new ArrayList<Character>();
		int i = 0;
		int n = list_ch.length;
		while (i < n) {
			if(list_ch[i] == ','){
		    	path_ch.add('/');
		    	i++;
		    }
			else if(list_ch[i] == '['){
		    	i++;
		    	continue;
		    }
			else if(list_ch[i] == ']'){
		    	break;
		    }
			else{
				path_ch.add(list_ch[i]);
			}
			i++;
		}
		StringBuilder builder = new StringBuilder(path_ch.size());
	    for(Character ch: path_ch)
	    {
	        builder.append(ch);
	    }
	    String path = builder.toString();
	    return path;
	}

	public DefaultMutableTreeNode buildTree(){

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		return buildTreeAux("root", root);
	}

	public DefaultMutableTreeNode buildTreeAux (String parent_doc_id, 
			DefaultMutableTreeNode node){

		List<DatabaseSnapshotEntry> initial_list = _database_schema.get(parent_doc_id);
		for(DatabaseSnapshotEntry entry: initial_list){
			if(_database_schema.containsKey(entry.getDocId())){
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				buildTreeAux(entry.getDocId(),parent);
				node.add(parent);
			}
			else{
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				node.add(parent);
			}
		}
		return node;
	}
}