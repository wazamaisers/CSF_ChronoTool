package Skype.Database;

public class DatabaseSharedLinksEntry {

	private String _key = null;
	private String _sub_key = null;
	private Integer _access_time = null;
	private Integer _size = null;

	public DatabaseSharedLinksEntry(String key, String sub_key, Integer access_time, Integer size){
		
		_key = key;
		_sub_key = sub_key;
		_access_time = access_time;
		_size = size;
	}
	
	public String getLink(){
		return _key;
	}
	
	public String getType(){
		return _sub_key;
	}
	
	public Integer getTimestamp(){
		return _access_time;
	}

	public Integer getSize(){
		return _size;
	}
}