package Skype.Database;

public class DatabaseContactsEntry {

	private String _skypename = null;
	private String _fullname = null;
	private Integer _birthday = null;
	private String _gender = null;
	private String _languages = null;
	private String _country = null;
	private String _province = null;
	private String _city = null;
	private String _phone_home = null;
	private String _phone_office = null;
	private String _phone_mobile = null;
	private String _emails = null;
	private String _hashed_emails = null;
	private String _homepage = null;
	private String _about = null;
	private String _avatar_image = null;
	private String _mood_text = null;
	private String _rich_mood_text = null;
	private Integer _timezone = null;
	private Integer _profile_timestamp = null;
	private Integer _last_online_timestamp = null;
	private String _displayname = null;
	private String _external_id = null;

	public DatabaseContactsEntry(String skypename, String fullname, Integer birthday, String gender, 
			String languages, String country, String province, String city, String phone_home, String phone_office,
			String phone_mobile, String emails, String hashed_emails, String homepage, String about,
			String avatar_image, String mood_text, String rich_mood_text, Integer timezone, 
			Integer profile_timestamp, Integer last_online_timestamp, String displayname, String external_id){
		
		_skypename = skypename;
		_fullname = fullname;
		_birthday = birthday;
		_gender = gender;
		_languages = languages;
		_country = country;
		_province = province;
		_city = city;
		_phone_home = phone_home;
		_phone_office = phone_office;
		_phone_mobile = phone_mobile;
		_emails = emails;
		_hashed_emails = hashed_emails;
		_homepage = homepage;
		_about = about;
		_avatar_image = avatar_image;
		_mood_text = mood_text;
		_rich_mood_text = rich_mood_text;
		_timezone = timezone;
		_profile_timestamp = profile_timestamp;
		_last_online_timestamp = last_online_timestamp;
		_displayname = displayname;
		_external_id = external_id;
	}
	
	public String getSkypeName(){
		return _skypename;
	}
	
	public String getFullName(){
		return _fullname;
	}
	
	//Se calhar aqui podia já cagar uma date
	public Integer getBirthday(){
		return _birthday;
	}
	
	public String getGender(){
		return _gender;
	}
	
	public String getLanguages(){
		return _languages;
	}
	
	public String getCountry(){
		return _country;
	}
	
	public String getProvince(){
		return _province;
	}
	
	public String getCity(){
		return _city;
	}
	
	public String getPhoneHome(){
		return _phone_home;
	}
	
	public String getPhoneOffice(){
		return _phone_office;
	}
	
	public String getPhoneMobile(){
		return _phone_mobile;
	}
	
	public String getEmails(){
		return _emails;
	}
	
	public String getHashedEmails(){
		return _hashed_emails;
	}
	
	public String getHomepage(){
		return _homepage;
	}
	
	public String getAbout(){
		return _about;
	}
	
	public String getAvatarImage(){
		return _avatar_image;
	}
	
	public String getMoodText(){
		return _mood_text;
	}
	
	public String getRichMoodText(){
		return _rich_mood_text;
	}
	
	public Integer getTimezone(){
		return _timezone;
	}
	
	public Integer getProfileTimestamp(){
		return _profile_timestamp;
	}
	
	public Integer getLastOnlineTimestamp(){
		return _last_online_timestamp;
	}
	
	public String getDisplayName(){
		return _displayname;
	}
	
	public String getExternalId(){
		return _external_id;
	}
}