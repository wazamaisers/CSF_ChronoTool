package Drive;

public class DatabaseEntry {

	private String _doc_id = null;
	private String _filename = null;
	private Integer _modified = null;
	private Integer _created = null;
	private Integer _acl_role = null;
	private Integer _doc_type = null;
	private Integer _removed = null;
	private Integer _size = null;
	private String _checksum = null;
	private Integer _shared = null;
	private String _resource_type = null;
	private Integer _original_size = null;
	private String _original_checksum = null;
	private String _parent_doc_id = null;

	public DatabaseEntry(String doc_id, String filename, Integer modified, Integer created, Integer acl_role, 
			Integer doc_type, Integer removed, Integer size, String checksum, Integer shared, String resource_type, 
			Integer original_size, String original_checksum, String parent_doc_id){
		
		_doc_id = doc_id;
		_filename = filename;
		_modified = modified;
		_created = created;
		_acl_role = acl_role;
		_doc_type = doc_type;
		_removed = removed;
		_size = size;
		_checksum = checksum;
		_shared = shared;
		_resource_type = resource_type;
		_original_size = original_size;
		_parent_doc_id = parent_doc_id;
	}
	
	public String getDocId(){
		return _doc_id;
	}
	
	public String getFilename(){
		return _filename;
	}
	
	public int getModified(){
		return _modified;
	}
	
	public int getCreated(){
		return _created;
	}
	
	public int getAclRole(){
		return _acl_role;
	}
	
	public int getDocType(){
		return _doc_type;
	}
	
	public int getRemoved(){
		return _removed;
	}
	
	public int getSize(){
		return _size;
	}
	
	public String getChecksum(){
		return _checksum;
	}
	
	public int getShared(){
		return _shared;
	}

	public String getResourceType(){
		return _resource_type;
	}
	
	public int getOriginalSize(){
		return _original_size;
	}
	
	public String getOriginalChecksum(){
		return _original_checksum;
	}
	
	public String getParentDocId(){
		return _parent_doc_id;
	}
}