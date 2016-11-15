package Skype.Database;

public class DatabaseCallsEntry {

	private String _identity = null;
	private String _dispname = null;
	private String _type = null;
	private String _guid = null;
	private Integer _start_timestamp = null;
	private Integer _creation_timestamp = null;

	public DatabaseCallsEntry(String identity, String dispname, String type, String guid, 
			Integer start_timestamp, Integer creation_timestamp){
		
		_identity = identity;
		_dispname = dispname;
		_type = type;
		_guid = guid;
		_start_timestamp = start_timestamp;
		_creation_timestamp = creation_timestamp;
	}
	
	public String getIdentity(){
		return _identity;
	}
	
	public String getDispname(){
		return _dispname;
	}
	
	public String getWhoCalls(){
		return _type;
	}
	
	public String getGuid(){
		return _guid;
	}
	
	public Integer getStartTimestamp(){
		return _start_timestamp;
	}
	
	public Integer getCreationTimestamp(){
		return _creation_timestamp;
	}
}