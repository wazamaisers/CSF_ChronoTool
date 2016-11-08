package Drive;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

	private HashMap<String,ArrayList<DatabaseEntry>> _database_schema = new HashMap<String, ArrayList<DatabaseEntry>>();
	private ArrayList<DatabaseEntry> _database_entrys = new ArrayList<DatabaseEntry>();
	private Connection _snapshot = null;
	private Statement _stmt1 = null;
	private Statement _stmt2 = null;
	private Statement _stmt3 = null;
	private String _parent_doc_id = "null";

	public Database(String username){
		
		try {
			Class.forName("org.sqlite.JDBC");
			_snapshot = DriverManager.getConnection("jdbc:sqlite:C:/Users/" + username + "/AppData/Local/Google/Drive/user_default/snapshot.db");
			_snapshot.setAutoCommit(false);
			_stmt1 = _snapshot.createStatement();
			_stmt2 = _snapshot.createStatement();
			_stmt3 = _snapshot.createStatement();
			
			ResultSet rs1 = _stmt1.executeQuery("SELECT * FROM cloud_entry;");
			ResultSet rs2 = _stmt2.executeQuery("SELECT * FROM cloud_relations;");
			while (rs1.next() && rs2.next()) {
				
				String doc_id = rs1.getString("doc_id");
				String filename = rs1.getString("filename");
				Integer modified = rs1.getInt("modified");
				Integer created = rs1.getInt("created");
				Integer acl_role = rs1.getInt("acl_role");
				Integer doc_type = rs1.getInt("doc_type");
				Integer removed = rs1.getInt("removed");
				Integer size = rs1.getInt("size");
				String checksum = rs1.getString("checksum");
				Integer shared = rs1.getInt("shared");
				String resource_type = rs1.getString("resource_type");
				Integer original_size = rs1.getInt("original_size");
				String original_checksum = rs1.getString("original_checksum");
				
				if (doc_id.equals(null)){
					doc_id = "null";
				}
				
				if (filename.equals(null)){
					filename = "null";
				}

				if (modified.equals(0)){
					modified = -1;
				}
				
				if (created.equals(0)){
					created = -1;
				}
								
				if (acl_role.equals(0)){
					acl_role = -1;
				}
				
				if (doc_type.equals(0)){
					doc_type = -1;
				}
				
				if (removed.equals(0)){
					removed = -1;
				}
				
				if (size.equals(0)){
					size = -1;
				}
				
				if (checksum == null){
					checksum = "null";
					
				}
				
				if (shared.equals(0)){
					shared = -1;
				}
				
				if (resource_type == null){
					resource_type = "null";
				}
				
				if (original_size.equals(0)){
					original_size = -1;
				}
				
				if (original_checksum == null){
					original_checksum = "null";
				}
				
				
				if(!"root".equals(doc_id)){
					ResultSet rs3 = _stmt3.executeQuery("SELECT * FROM cloud_relations WHERE child_doc_id = '" +
							rs1.getString("doc_id") + "';");
							
					_parent_doc_id = rs3.getString("parent_doc_id");
					rs3.close();
				}
				
				if("root".equals(doc_id)){
					_parent_doc_id = "null";
				}
				
				//System.out.println(rs2.getString("child_doc_id") + " " + rs1.getString("doc_id"));
				//System.out.println(rs2.getString("parent_doc_id") + " " + parent_doc_id);
				//System.out.println(doc_id+filename+modified+created+acl_role+doc_type+removed+size+checksum+shared+
						//resource_type+original_size+original_checksum+parent_doc_id);
				DatabaseEntry database_entry = new DatabaseEntry(doc_id,filename,modified,created,acl_role,
						doc_type, removed,size, checksum,shared, resource_type,original_size,original_checksum,
						_parent_doc_id);
				
				_database_entrys.add(database_entry);
				if(_database_schema.containsKey(_parent_doc_id)){
					ArrayList<DatabaseEntry> dir = _database_schema.get(_parent_doc_id);
					dir.add(database_entry);
					_database_schema.put(_parent_doc_id, dir);
				}
				
				if(!_database_schema.containsKey(_parent_doc_id)){
					ArrayList<DatabaseEntry> dir = new ArrayList<DatabaseEntry>();
					dir.add(database_entry);
					_database_schema.put(_parent_doc_id, dir);
				}
			}
			
			rs1.close();
			rs2.close();
			_stmt1.close();
			_stmt2.close();
			_snapshot.close();

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	public HashMap<String,ArrayList<DatabaseEntry>> getDatabaseSchema(){
		return _database_schema;
	}
	
	public ArrayList<DatabaseEntry> getDatabaseEntrys(){
		return _database_entrys;
	}
}