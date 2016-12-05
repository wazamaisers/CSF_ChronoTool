import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import Drive.Drive;
import Skype.Skype;
import Skype.Database.DatabaseContactsEntry;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JList;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.awt.Choice;

public class MenuSkype {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(Skype skype) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuSkype window = new MenuSkype(skype);
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
	public MenuSkype(Skype skype) {
		initialize(skype);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Skype skype) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		//		try {
		//            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(
		//            		new File(System.getProperty("user.dir") + "\\image.jpg")))));
		//        } catch (IOException e) {
		//            e.printStackTrace();
		//        }
		//        frame.pack();
		frame.setVisible(true);
		
		JLabel lblSkypeName = new JLabel("");
		lblSkypeName.setForeground(Color.WHITE);
		lblSkypeName.setBounds(45, 23, 585, 33);
		lblSkypeName.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSkypeName.setText("Skype Name: " + skype.getSkypeName());
		lblSkypeName.setVisible(true);
		frame.getContentPane().add(lblSkypeName);

		JLabel lblSkypeUsername = new JLabel("");
		lblSkypeUsername.setForeground(Color.WHITE);
		lblSkypeUsername.setBounds(45, 53, 585, 33);
		lblSkypeUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSkypeUsername.setText("Skype username: " + skype.getSkypeUserName());
		lblSkypeUsername.setVisible(true);
		frame.getContentPane().add(lblSkypeUsername);
		
		JLabel lblChronoMain = new JLabel("Chrono Tool");
		lblChronoMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblChronoMain.setForeground(Color.WHITE);
		lblChronoMain.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 45));
		lblChronoMain.setBounds(726, 0, 417, 90);
		lblChronoMain.setVisible(true);
		frame.getContentPane().add(lblChronoMain);

		JLabel lblDriveMain = new JLabel("Skype");
		lblDriveMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblDriveMain.setForeground(Color.WHITE);
		lblDriveMain.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 30));
		lblDriveMain.setBounds(726, 43, 417, 90);
		lblDriveMain.setVisible(true);
		frame.getContentPane().add(lblDriveMain);
		
		String[] columnNames = {"Name", "Skype Name", "Gender", "Last Online"};
		String[][] rowData = skype.getBasicContactInfo();
		JTable table = new JTable(rowData,columnNames){
			public boolean isCellEditable(int rowData, int columnNames){
				return false;
			}
		};
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2 && !me.isConsumed()) {
					String skypeName = "";
					skypeName = rowData[table.getRowSorter().convertRowIndexToModel(table.getSelectedRow())][1];
					new SkypeContactInfo(skypeName, skype);
				}
			}
		});
		
		table.setBounds(79, 53, 463, 586);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(45, 97, 658, 580);
		frame.getContentPane().add(scroll);
		
		JLabel lblSearchesByPath = new JLabel("Full Lists");
		lblSearchesByPath.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSearchesByPath.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchesByPath.setBounds(1194, 53, 158, 24);
		frame.getContentPane().add(lblSearchesByPath);
		
		List list = new List();
		list.setFont(new Font("Dialog", Font.BOLD, 12));
		list.setBounds(726, 239, 141, 245);
		list.add("Skype Name");
		list.add("Full Name");
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
		list.add("About");
		list.add("Timezone");
		list.add("Profile Created");
		list.add("Last Online");
		frame.getContentPane().add(list);
		
		List list_1 = new List();
		list_1.setBounds(873, 239, 270, 245);
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getSkypeName());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getFullName());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getBirthday().toString());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getGender());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getLanguages());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getCountry());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getProvince());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getCity());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getPhoneHome());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getPhoneOffice());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getPhoneMobile());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getEmails());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getAbout());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getTimezone().toString());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getProfileTimestamp().toString());
		list_1.add(skype.getUserProfile().get(skype.getSkypeUserName()).getLastOnlineTimestamp().toString());
		frame.getContentPane().add(list_1);
		
		getAvatar(skype.getUserProfile().get(skype.getSkypeUserName()).getSkypeName(),skype);
		
		List list_2 = new List();
		list_2.setFont(new Font("Dialog", Font.BOLD, 12));
		list_2.setBounds(726, 497, 141, 82);
		list_2.add("# Contacts");
		list_2.add("# Calls");
		list_2.add("# Exchanged Messages");
		list_2.add("# Exchanged Links");
		list_2.add("# Exchanged Files");
		frame.getContentPane().add(list_2);
		
		List list_3 = new List();
		list_3.setBounds(873, 497, 37, 82);
		list_3.add(""+skype.getTotalContacts());
		list_3.add(""+skype.getTotalCalls());
		list_3.add(""+skype.getTotalMessages());
		list_3.add(""+skype.getTotalLinks());
		list_3.add(""+skype.getTotalFiles());
		frame.getContentPane().add(list_3);
		
		List list_4 = new List();
		list_4.setFont(new Font("Dialog", Font.BOLD, 12));
		list_4.setBounds(916, 497, 88, 50);
		list_4.add("TOP 1 - Calls");
		list_4.add("TOP 2 - Calls");
		list_4.add("TOP 3 - Calls");
		frame.getContentPane().add(list_4);
		
		List list_5 = new List();
		list_5.setBounds(1010, 497, 133, 50);
		HashMap <String,HashMap<String,Integer>> map = skype.getTop("calls");
		HashMap <String,Integer> first = map.get("First");
		for(Map.Entry<String, Integer> fst: first.entrySet()){
			list_5.add(fst.getKey());
		}
		HashMap <String,Integer> second = map.get("Second");
		for(Map.Entry<String, Integer> fst: second.entrySet()){
			list_5.add(fst.getKey());
		}
		HashMap <String,Integer> third = map.get("Third");
		for(Map.Entry<String, Integer> fst: third.entrySet()){
			list_5.add(fst.getKey());
		}
		frame.getContentPane().add(list_5);
		
		List list_6 = new List();
		list_6.setFont(new Font("Dialog", Font.BOLD, 12));
		list_6.setBounds(726, 591, 141, 35);
		list_6.add("Last Week Calls");
		list_6.add("Last Week Messages");
		frame.getContentPane().add(list_6);
		
		List list_7 = new List();
		list_7.setBounds(873, 591, 37, 35);
		list_7.add("" + skype.getLastCallsByPeriod("weeks"));
		list_7.add("" + skype.getLastMessagesByPeriod("weeks"));
		frame.getContentPane().add(list_7);
		
		List list_8 = new List();
		list_8.setFont(new Font("Dialog", Font.BOLD, 12));
		list_8.setBounds(916, 556, 88, 50);
		list_8.add("TOP 1 - Msgs.");
		list_8.add("TOP 2 - Msgs.");
		list_8.add("TOP 3 - Msgs.");
		frame.getContentPane().add(list_8);
		
		List list_9 = new List();
		list_9.setBounds(1010, 556, 133, 50);
		map = skype.getTop("messages");
		first = map.get("First");
		for(Map.Entry<String, Integer> fst: first.entrySet()){
			list_9.add(fst.getKey());
		}
		second = map.get("Second");
		for(Map.Entry<String, Integer> fst: second.entrySet()){
			list_9.add(fst.getKey());
		}
		third = map.get("Third");
		for(Map.Entry<String, Integer> fst: third.entrySet()){
			list_9.add(fst.getKey());
		}
		frame.getContentPane().add(list_9);
		
		List list_10 = new List();
		list_10.setFont(new Font("Dialog", Font.BOLD, 12));
		list_10.setBounds(726, 642, 141, 35);
		list_10.add("Last Month Calls");
		list_10.add("Last Month Messages");
		frame.getContentPane().add(list_10);
		
		List list_11 = new List();
		list_11.setBounds(873, 642, 37, 35);
		list_11.add("" + skype.getLastCallsByPeriod("months"));
		list_11.add("" + skype.getLastMessagesByPeriod("months"));
		frame.getContentPane().add(list_11);
		
		Button button = new Button("Messages");
		button.setFont(new Font("Dialog", Font.PLAIN, 12));
		button.setBounds(1194, 135, 158, 39);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SkypeLists("messages",skype);
			}
		});
		frame.getContentPane().add(button);
		
		Button button_1 = new Button("Calls");
		button_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		button_1.setBounds(1194, 90, 158, 39);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SkypeLists("calls",skype);
			}
		});
		frame.getContentPane().add(button_1);
		
		Button button_2 = new Button("Links");
		button_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		button_2.setBounds(1194, 180, 158, 39);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SkypeLists("links",skype);
			}
		});
		frame.getContentPane().add(button_2);
		
		Button button_3 = new Button("Files");
		button_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		button_3.setBounds(1194, 225, 158, 39);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SkypeLists("files",skype);
			}
		});
		frame.getContentPane().add(button_3);
		
		////////////////////////// BOTÕES POR UTILIZADOR //////////////////////
		
		Choice choice = new Choice();
		choice.setBounds(1194, 341, 158, 20);
		for(String name: skype.getSkypeNamesList()){
			choice.add(name);
		}
		frame.getContentPane().add(choice);
		
		JLabel lblListsByUser = new JLabel("Lists By User");
		lblListsByUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblListsByUser.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblListsByUser.setBounds(1194, 311, 158, 24);
		frame.getContentPane().add(lblListsByUser);
		
		Button button_5 = new Button("Calls");
		button_5.setFont(new Font("Dialog", Font.PLAIN, 12));
		button_5.setBounds(1194, 367, 158, 39);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SkypeListsByName("calls",skype,choice.getSelectedItem());
			}
		});
		frame.getContentPane().add(button_5);
		
		Button button_6 = new Button("Messages");
		button_6.setFont(new Font("Dialog", Font.PLAIN, 12));
		button_6.setBounds(1194, 412, 158, 39);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SkypeListsByName("messages",skype,choice.getSelectedItem());
			}
		});
		frame.getContentPane().add(button_6);
		
		Button button_8 = new Button("Files");
		button_8.setFont(new Font("Dialog", Font.PLAIN, 12));
		button_8.setBounds(1194, 457, 158, 39);
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SkypeListsByName("files",skype,choice.getSelectedItem());
			}
		});
		frame.getContentPane().add(button_8);
		
		List list_12 = new List();
		list_12.setBounds(1149, 497, 37, 50);
		map = skype.getTop("calls");
		first = map.get("First");
		for(Map.Entry<String, Integer> fst: first.entrySet()){
			if(!(fst.getValue()==0)){
				list_12.add(""+fst.getValue());
			}
			else{
				list_12.add("");
			}
		}
		second = map.get("Second");
		for(Map.Entry<String, Integer> fst: second.entrySet()){
			if(!(fst.getValue()==0)){
				list_12.add(""+fst.getValue());
			}
			else{
				list_12.add("");
			}
		}
		third = map.get("Third");
		for(Map.Entry<String, Integer> fst: third.entrySet()){
			if(!(fst.getValue()==0)){
				list_12.add(""+fst.getValue());
			}
			else{
				list_12.add("");
			}
		}
		frame.getContentPane().add(list_12);
		
		List list_13 = new List();
		list_13.setBounds(1149, 555, 37, 51);
		map = skype.getTop("messages");
		first = map.get("First");
		for(Map.Entry<String, Integer> fst: first.entrySet()){
			if(!(fst.getValue()==0)){
				list_13.add(""+fst.getValue());
			}
			else{
				list_13.add("");
			}
		}
		second = map.get("Second");
		for(Map.Entry<String, Integer> fst: second.entrySet()){
			if(!(fst.getValue()==0)){
				list_13.add(""+fst.getValue());
			}
			else{
				list_13.add("");
			}
		}
		third = map.get("Third");
		for(Map.Entry<String, Integer> fst: third.entrySet()){
			if(!(fst.getValue()==0)){
				list_13.add(""+fst.getValue());
			}
			else{
				list_13.add("");
			}
		}
		frame.getContentPane().add(list_13);
	}
	
	public void getAvatar(String skypeUsername, Skype skype){
		try {
			String path = skype.getUserAvatarPath(skypeUsername);
			URL url = new URL(path);
			BufferedImage image = ImageIO.read(url);
			JLabel label = new JLabel(new ImageIcon(image));
			label.setBounds(857, 114, 141, 119);
			label.setVisible(true);
			frame.getContentPane().add(label);
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
