package Skype.Database;

public class DatabaseSharedFilesStoredEntry {

	private String _local_path = null;
	private Integer _size = null;
	private String _user_name = null;
	private String _uri = null;
	private String _file_name = null;

	public DatabaseSharedFilesStoredEntry(String local_path, Integer size, String user_name, String uri, 
			String file_name){
		
		_local_path = local_path;
		_size = size;
		_user_name = user_name;
		_uri = uri;
		_file_name = file_name;
	}
	
	public String getLocalPath(){
		return _local_path;
	}
	
	public Integer getSize(){
		return _size;
	}
	
	public String getUserName(){
		return _user_name;
	}
	
	public String getUri(){
		return _uri;
	}
	
	public String getFileName(){
		return _file_name;
	}
}