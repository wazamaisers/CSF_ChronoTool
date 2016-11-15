package Skype;

import Skype.Database.PopulateDatabase;

public class Skype {
	
	public Skype(String username, String skypeusername){
		PopulateDatabase db = new PopulateDatabase(username, skypeusername);
	}
}