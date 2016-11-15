package Skype.Database;

public class DatabaseMessagesEntry {

	private Integer _convo_id = null;
	private String _chatname = null;
	private String _author = null;
	private String _from_dispname = null;
	private Integer _timestamp = null;
	private String _body_xml = null;
	private String _identities = null;
	private String _reason = null;

	public DatabaseMessagesEntry(Integer convo_id, String chatname, String author, String from_dispname, 
			Integer timestamp, String body_xml, String identities, String reason){
		
		_convo_id = convo_id;
		_chatname = chatname;
		_author = author;
		_from_dispname = from_dispname;
		_timestamp = timestamp;
		_body_xml = body_xml;
		_identities = identities;
		_reason = reason;
	}
	
	public Integer getConvoId(){
		return _convo_id;
	}
	
	public String getChatname(){
		return _chatname;
	}
	
	public String getAuthor(){
		return _author;
	}
	
	public String getFromDispname(){
		return _from_dispname;
	}
	
	public Integer getTimestamp(){
		return _timestamp;
	}
	
	public String getMessage(){
		return _body_xml;
	}
	
	public String getIdentities(){
		return _identities;
	}
	
	public String getReason(){
		return _reason;
	}
}