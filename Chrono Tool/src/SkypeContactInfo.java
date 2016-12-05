import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Skype.Skype;

public class SkypeContactInfo {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String skypename, Skype skype) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SkypeContactInfo window = new SkypeContactInfo(skypename, skype);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SkypeContactInfo(String skypename, Skype skype) {
		initialize(skypename, skype);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String skypename, Skype skype) {
		frame = new JFrame();
		frame.setBounds(100, 100, 413, 543);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		List list = new List();
		list.setFont(new Font("Dialog", Font.BOLD, 12));
		list.setBounds(25, 137, 141, 329);
		list.add("Skype Name");
		list.add("Full Name");
		list.add("Display Name");
		list.add("Birthday");
		list.add("Gender");
		list.add("Languages");
		list.add("Country");
		list.add("Province");
		list.add("City");
		list.add("Home Phone");
		list.add("Office Phone");
		list.add("Mobile Phone");
		list.add("Emails");
		list.add("Homepage");
		list.add("About");
		list.add("Mood Text");
		list.add("Timezone");
		list.add("Profile Created");
		list.add("Last Online");
		
		frame.getContentPane().add(list);
		
		List list_1 = new List();
		list_1.setBounds(172, 137, 200, 329);
		list_1.add(skype.getContacts().get(skypename).getSkypeName());
		list_1.add(skype.getContacts().get(skypename).getFullName());
		list_1.add(skype.getContacts().get(skypename).getDisplayName());
		
		String birthday = skype.getContacts().get(skypename).getBirthday().toString();
		if(birthday.length()>2){
			list_1.add(birthday.substring(6, 8) + "-" + birthday.substring(4, 6) + "-" + birthday.substring(0, 4));
		}
		else{
			list_1.add("");
		}
		list_1.add(skype.getContacts().get(skypename).getGender());
		list_1.add(skype.getContacts().get(skypename).getLanguages());
		list_1.add(skype.getContacts().get(skypename).getCountry());
		list_1.add(skype.getContacts().get(skypename).getProvince());
		list_1.add(skype.getContacts().get(skypename).getCity());
		list_1.add(skype.getContacts().get(skypename).getPhoneHome());
		list_1.add(skype.getContacts().get(skypename).getPhoneOffice());
		list_1.add(skype.getContacts().get(skypename).getPhoneMobile());
		list_1.add(skype.getContacts().get(skypename).getEmails());
		list_1.add(skype.getContacts().get(skypename).getHomepage());
		list_1.add(skype.getContacts().get(skypename).getAbout());
		list_1.add(skype.getContacts().get(skypename).getMoodText());
		
		list_1.add(skype.getContacts().get(skypename).getTimezone().toString());
		
		Integer timestamp = skype.getContacts().get(skypename).getProfileTimestamp();
		java.util.Date time=new java.util.Date((long)timestamp*1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		if(timestamp==0){
			list_1.add("");
		}
		else{
			list_1.add(dateFormat.format(time));
		}
		
		timestamp = skype.getContacts().get(skypename).getLastOnlineTimestamp();
		time=new java.util.Date((long)timestamp*1000);
		if(timestamp==0){
			list_1.add("");
		}
		else{
			list_1.add(dateFormat.format(time));
		}
		frame.getContentPane().add(list_1);
		
		getAvatar(skype.getContacts().get(skypename).getSkypeName(),skype);
	}
	
	public void getAvatar(String skypeUsername, Skype skype){
		try {
			String path = skype.getAvatarPath(skypeUsername);
			URL url = new URL(path);
			BufferedImage image = ImageIO.read(url);
			JLabel label = new JLabel(new ImageIcon(image));
			label.setBounds(130, 10, 141, 119);
			label.setVisible(true);
			frame.getContentPane().add(label);
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
