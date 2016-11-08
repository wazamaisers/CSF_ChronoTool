package Drive;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Timestamps {

	private ArrayList<Integer> _tmstp = new ArrayList<Integer>();
	private HashMap<String,Integer> _times = new HashMap<String, Integer>();
	private Connection _snapshot = null;
	private Statement _stmt = null;

	public Timestamps(String username){
		
		_times.put("Dawn",0);  //2h - 6h
		_times.put("Morning",0);  //6h - 12h
		_times.put("Afternoon",0); //12h - 17h
		_times.put("Evening",0); // 17h - 20h
		_times.put("Night",0); //20h - 2h
		
		try {
			Class.forName("org.sqlite.JDBC");
			_snapshot = DriverManager.getConnection("jdbc:sqlite:C:/Users/" + username + "/AppData/Local/Google/Drive/user_default/snapshot.db");
			_snapshot.setAutoCommit(false);
			_stmt = _snapshot.createStatement();
			
			ResultSet rs = _stmt.executeQuery("SELECT * FROM cloud_entry;");
			while (rs.next() ) {
		         _tmstp.add(rs.getInt("modified"));
			}
			
			rs.close();
			_stmt.close();
			_snapshot.close();

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	public ArrayList<Integer> getTimestamps(){
		return _tmstp;
	}
	
	public static float getPercentage(int questions, int correct) {
	    float proportionCorrect = ((float) correct) / ((float) questions);
	    return Math.round(proportionCorrect * 100);
	}
	
	public HashMap<String, Integer> getTimes(ArrayList<Integer> timestamps){
		
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
}