package Drive;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.print.DocFlavor.CHAR_ARRAY;
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

	public ArrayList<DatabaseSnapshotEntry> getFilesByExtensionGivingPath(String path, String extension){

		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> extensions = new ArrayList<DatabaseSnapshotEntry>();

		for(DatabaseSnapshotEntry entry: _database_schema.get(doc_id)){

			if(entry.getResourceType().equals("file")){
				String filename = entry.getFilename();
				String file_extension = FilenameUtils.getExtension(filename);			
				if((extension.toLowerCase()).equals(file_extension)){
					extensions.add(entry);
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

	public ArrayList<DatabaseSnapshotEntry> getFilesByChildrenExtensionGivingPath(String path, String extension){

		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> extensions = new ArrayList<DatabaseSnapshotEntry>();
		ArrayList<DatabaseSnapshotEntry> children = getArrayOfChildren(doc_id);

		for(DatabaseSnapshotEntry entry: children){

			if(entry.getResourceType().equals("file")){
				String filename = entry.getFilename();
				String file_extension = FilenameUtils.getExtension(filename);			
				if((extension.toLowerCase()).equals(file_extension)){
					extensions.add(entry);
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

	public String getPathByDocId(String docId){
		String path = "";
		String parent = "";
		for(DatabaseSnapshotEntry entry: _database_entrys){
			if(entry.getDocId().equals(docId)){
				path = entry.getFilename();
				parent = entry.getParentDocId();
				while(!(parent.equals("root"))){
					for(DatabaseSnapshotEntry entry_1: _database_entrys){
						if(entry_1.getDocId().equals(parent)){
							path = entry_1.getFilename() + "/" + path;
							parent = entry_1.getParentDocId();
							break;
						}
					}
				}
			}
		}

		path = "root/" + path;

		return path;
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

	public float getPercentage(int questions, int correct) {
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

	public DefaultMutableTreeNode buildTreeOfDirs(){

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		return buildTreeOfDirsAux("root", root);
	}

	public DefaultMutableTreeNode buildTreeOfDirsAux (String parent_doc_id, 
			DefaultMutableTreeNode node){

		List<DatabaseSnapshotEntry> initial_list = _database_schema.get(parent_doc_id);
		for(DatabaseSnapshotEntry entry: initial_list){
			if(_database_schema.containsKey(entry.getDocId())){
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				buildTreeOfDirsAux(entry.getDocId(),parent);
				node.add(parent);
			}
			else if (entry.getResourceType().equals("folder")){
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				node.add(parent);
			}
		}
		return node;
	}

	public String getLocalDriveSize(){
		long bytes = 0L;

		for(DatabaseSnapshotEntry entry: _database_entrys){
			bytes = bytes + entry.getSize();
		}
		return getSizeCorrect(bytes);
	}

	public String getSizeCorrect(long bytes){
		long kilo = 1024L;
		long mega = 1048576L;
		long giga = 10737418424L;
		String size = null;
		if(bytes < 1024){
			size = "" + bytes + " bytes";
		}
		else if((kilo < bytes) && (bytes < mega)){
			size = "" + (bytes/1024) + " Kbytes";
		}
		else if((mega < bytes) && (bytes < giga)){
			size = "" + (bytes/(1024*1024)) + " Mbytes";
		}
		else{
			size = "" + (bytes/(1024*1024*1024)) + " Gbytes";
		}
		return size;
	}

	public String getFileCount(String type, boolean sharedFlag){
		String filecount = null;
		long files = 0L;
		long dirs = 0L;
		long docs = 0L;
		for(DatabaseSnapshotEntry entry: _database_entrys){
			if (sharedFlag == false){
				if (entry.getResourceType().equals("folder")){
					dirs++;
				}
				else if (entry.getResourceType().equals("document")){
					docs++;
				}
				else if (entry.getResourceType().equals("file")){
					files++;
				}
			}
			else{
				if (entry.getResourceType().equals("folder") && entry.getShared() == 1){
					dirs++;
				}
				else if (entry.getResourceType().equals("document") && entry.getShared() == 1){
					docs++;
				}
				else if (entry.getResourceType().equals("file") && entry.getShared() == 1){
					files++;
				}
			}

		}
		if (type.equals("Files")){
			filecount = "" + files;
		}
		else if (type.equals("Folders")){
			filecount = "" + dirs;
		}
		else if (type.equals("Docs")){
			filecount = "" + docs;
		}
		return filecount;
	}

	public HashMap <String,Integer> getChildrenExtensionsStatistics(String path){
		HashMap <String,Integer> top3 = new HashMap <String,Integer>();
		HashMap <String,Integer> extensions =  getChildrenExtensions("root");
		String first = "";
		String second = "";
		String third = "";
		Integer fst = 0;
		Integer scd = 0;
		Integer trd = 0;
		Integer total = 0;
		Iterator<Entry<String, Integer>> it =extensions.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			total = total + (Integer) pair.getValue();
			if (((Integer) pair.getValue())>fst){
				third = second;
				second = first;
				trd = scd;
				scd = fst;
				first = (String) pair.getKey();
				fst = (Integer) pair.getValue();
			}
			else if (((Integer) pair.getValue())<fst && ((Integer) pair.getValue())>scd){
				third = second;
				trd = scd;
				second = (String) pair.getKey();
				scd = (Integer) pair.getValue();
			}
			else if (((Integer) pair.getValue())<scd && ((Integer) pair.getValue())>trd){
				third = (String) pair.getKey();
				trd = (Integer) pair.getValue();
			}
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
		top3.put(first, fst);
		top3.put(second, scd);
		top3.put(third, trd);
		top3.put("total", total);
		System.out.println(first + " = " + fst);
		System.out.println(second + " = " + scd);
		System.out.println(third + " = " + trd);
		System.out.println("total = " + total);
		return top3;
	}

	public ArrayList<DatabaseSnapshotEntry> getFilesByKeyWord(String keyWord){

		ArrayList<DatabaseSnapshotEntry> finalList = new ArrayList<DatabaseSnapshotEntry>();
		char[] keyWordChar = keyWord.toLowerCase().toCharArray();
		for (DatabaseSnapshotEntry entry: _database_entrys){
			int e = 0;
			char[] fileChar = entry.getFilename().toLowerCase().toCharArray();
			for(int i = 0; i < fileChar.length; i++){
				if (fileChar[i] == keyWordChar[e]){
					e++;
				}
				else{
					e = 0;
				}
				if (e == keyWordChar.length){
					finalList.add(entry);
					break;
				}
			}
		}
		return finalList;
	}

	public ArrayList<DatabaseSnapshotEntry> getFilesByKeyWordGivePath(String keyWord, String path){

		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> finalList = new ArrayList<DatabaseSnapshotEntry>();
		char[] keyWordChar = keyWord.toLowerCase().toCharArray();
		for (DatabaseSnapshotEntry entry: _database_schema.get(doc_id)){
			int e = 0;
			char[] fileChar = entry.getFilename().toLowerCase().toCharArray();
			for(int i = 0; i < fileChar.length; i++){
				if (fileChar[i] == keyWordChar[e]){
					e++;
				}
				else{
					e = 0;
				}
				if (e == keyWordChar.length){
					finalList.add(entry);
					break;
				}
			}
		}
		return finalList;
	}
	
	public ArrayList<DatabaseSnapshotEntry> getFilesByKeyWordChildrenGivePath(String keyWord, String path){
		
		String doc_id = getDirectoryDocIdByPath(path);
		ArrayList<DatabaseSnapshotEntry> children = getArrayOfChildren(doc_id);
		ArrayList<DatabaseSnapshotEntry> finalList = new ArrayList<DatabaseSnapshotEntry>();
		char[] keyWordChar = keyWord.toLowerCase().toCharArray();
		for (DatabaseSnapshotEntry entry: children){
			int e = 0;
			char[] fileChar = entry.getFilename().toLowerCase().toCharArray();
			for(int i = 0; i < fileChar.length; i++){
				if (fileChar[i] == keyWordChar[e]){
					e++;
				}
				else{
					e = 0;
				}
				if (e == keyWordChar.length){
					finalList.add(entry);
					break;
				}
			}
		}
		return finalList;
	}

	public Integer getLastTime(){
		Integer last = 2147483647;
		for(DatabaseSnapshotEntry entry: _database_entrys){
			if(last>entry.getModified() && !entry.getDocId().equals("root")){
				last = entry.getModified();
			}
		}
		return last;
	}

	public DefaultMutableTreeNode buildTimedTree(Long timestamp){

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		return buildTimedTreeAux("root", root, timestamp);
	}

	public DefaultMutableTreeNode buildTimedTreeAux (String parent_doc_id, 
			DefaultMutableTreeNode node, Long timestamp){
		System.out.println("Entra aux");

		List<DatabaseSnapshotEntry> initial_list = _database_schema.get(parent_doc_id);
		for(DatabaseSnapshotEntry entry: initial_list){
			if(_database_schema.containsKey(entry.getDocId()) && (long) entry.getModified() < timestamp){
				System.out.println("Entra if");
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				buildTreeAux(entry.getDocId(),parent);
				node.add(parent);
			}
			else if((long) entry.getModified() < timestamp){
				System.out.println("Entra else if");
				DefaultMutableTreeNode parent = new DefaultMutableTreeNode(entry.getFilename());
				node.add(parent);
			}
		}
		return node;
	}

}