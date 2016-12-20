import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Drive.Drive;
import Drive.Database.DatabaseSnapshotEntry;

import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.TreePath;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JScrollPane;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Color;
import java.awt.List;
import javax.swing.SwingConstants;

public class MenuDrive {

	private JFrame frame;
	private DatabaseSnapshotEntry entrySelected;

	/**
	 * Launch the application.
	 */
	public static void main(Drive drive) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuDrive window = new MenuDrive(drive);
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
	public MenuDrive(Drive drive) {
		initialize(drive);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Drive drive) {
		frame = new JFrame("Chrono Tool - Google Drive");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		//// ELEMENTS OF THIS WINDOW ////

		JLabel lblDriveVersion = new JLabel("");
		lblDriveVersion.setForeground(Color.BLACK);
		lblDriveVersion.setBounds(45, 23, 585, 33);
		lblDriveVersion.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDriveVersion.setText("Drive version: " + drive.getDriveVersion());
		lblDriveVersion.setVisible(true);
		frame.getContentPane().add(lblDriveVersion);

		JLabel lblDriveUsername = new JLabel("");
		lblDriveUsername.setForeground(Color.BLACK);
		lblDriveUsername.setBounds(45, 53, 585, 33);
		lblDriveUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDriveUsername.setText("Drive username: " + drive.getUserMail());
		lblDriveUsername.setVisible(true);
		frame.getContentPane().add(lblDriveUsername);

		java.awt.List list = new java.awt.List();
		list.setFont(new Font("Arial", Font.BOLD, 12));
		list.setBounds(745, 100, 111, 113);
		list.setVisible(false);
		frame.getContentPane().add(list);

		java.awt.List list_values = new java.awt.List();
		list_values.setFont(new Font("Arial", Font.PLAIN, 12));
		list_values.setBounds(857, 100, 225, 113);
		list_values.setVisible(false);
		frame.getContentPane().add(list_values);

		HashMap <String,Integer> top3 = drive.getChildrenExtensionsStatistics("root");

		java.awt.List listSize1 = new java.awt.List();
		listSize1.setBounds(745, 315, 169, 25);
		listSize1.setFont(new Font("Arial", Font.BOLD, 12));
		listSize1.add("Local Drive Size");
		frame.getContentPane().add(listSize1);

		java.awt.List listSize2 = new java.awt.List();
		listSize2.setBounds(913, 315, 169, 25);
		listSize2.setFont(new Font("Arial", Font.PLAIN, 12));
		listSize2.add(drive.getLocalDriveSize());
		frame.getContentPane().add(listSize2);

		List listTimes = new List();
		listTimes.setBounds(842, 533, 144, 80);
		listTimes.setFont(new Font("Arial", Font.PLAIN, 12));
		Integer total = drive.getTimes().get("Dawn") + drive.getTimes().get("Morning") + drive.getTimes().get("Afternoon") + drive.getTimes().get("Evening") +
				drive.getTimes().get("Night");
		listTimes.add("Dawn :        " + drive.getPercentage(total, drive.getTimes().get("Dawn")) + "%");
		listTimes.add("Morning :    " + drive.getPercentage(total, drive.getTimes().get("Morning")) + "%");
		listTimes.add("Afternoon : " + drive.getPercentage(total, drive.getTimes().get("Afternoon")) + "%");
		listTimes.add("Evening :    " + drive.getPercentage(total, drive.getTimes().get("Evening")) + "%");
		listTimes.add("Night :         " + drive.getPercentage(total, drive.getTimes().get("Night")) + "%");
		frame.getContentPane().add(listTimes);

		Button buttonPath1 = new Button("File Extensions");
		buttonPath1.setFont(new Font("Arial", Font.PLAIN, 15));
		buttonPath1.setBounds(1194, 96, 158, 39);
		frame.getContentPane().add(buttonPath1);

		Button buttonPath2 = new Button("File Extensions (children)");
		buttonPath2.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonPath2.setBounds(1194, 141, 158, 39);
		frame.getContentPane().add(buttonPath2);

		JLabel lblSearchesByPath = new JLabel("Searches by Path");
		lblSearchesByPath.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSearchesByPath.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchesByPath.setBounds(1194, 53, 158, 24);
		frame.getContentPane().add(lblSearchesByPath);

		JLabel lblSearchesByKeyword = new JLabel("Searches by");
		lblSearchesByKeyword.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchesByKeyword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSearchesByKeyword.setBounds(1194, 297, 158, 33);
		frame.getContentPane().add(lblSearchesByKeyword);

		JLabel lblKeyword = new JLabel("KeyWord");
		lblKeyword.setHorizontalAlignment(SwingConstants.CENTER);
		lblKeyword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblKeyword.setBounds(1194, 316, 158, 33);
		frame.getContentPane().add(lblKeyword);

		Button button_2 = new Button("Filenames");
		button_2.setFont(new Font("Arial", Font.PLAIN, 15));
		button_2.setBounds(1194, 355, 158, 39);
		frame.getContentPane().add(button_2);

		Button button_3 = new Button("File Contents");
		button_3.setFont(new Font("Arial", Font.PLAIN, 15));
		button_3.setBounds(1194, 400, 158, 39);
		frame.getContentPane().add(button_3);

		Button button_6 = new Button("Filenames");
		button_6.setFont(new Font("Arial", Font.PLAIN, 15));
		button_6.setBounds(1194, 185, 158, 39);
		frame.getContentPane().add(button_6);

		JLabel lblChronological = new JLabel("Chronological");
		lblChronological.setHorizontalAlignment(SwingConstants.CENTER);
		lblChronological.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblChronological.setBounds(1194, 474, 158, 33);
		frame.getContentPane().add(lblChronological);

		JLabel lblTimeline = new JLabel("Timeline");
		lblTimeline.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeline.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblTimeline.setBounds(1194, 491, 158, 33);
		frame.getContentPane().add(lblTimeline);

		Button button_7 = new Button("Start Chrono Tool");
		button_7.setFont(new Font("Arial", Font.PLAIN, 15));
		button_7.setBounds(1194, 532, 158, 57);
		frame.getContentPane().add(button_7);

		Button button_1 = new Button("Filenames (children)");
		button_1.setFont(new Font("Arial", Font.PLAIN, 12));
		button_1.setBounds(1194, 230, 158, 39);
		frame.getContentPane().add(button_1);

		JLabel lblChronoMain = new JLabel("Chrono Tool");
		lblChronoMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblChronoMain.setForeground(Color.BLACK);
		lblChronoMain.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 50));
		lblChronoMain.setBounds(706, 100, 417, 90);
		lblChronoMain.setVisible(true);
		frame.getContentPane().add(lblChronoMain);

		JLabel lblDriveMain = new JLabel("Google Drive");
		lblDriveMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblDriveMain.setForeground(Color.BLACK);
		lblDriveMain.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 35));
		lblDriveMain.setBounds(706, 143, 417, 90);
		lblDriveMain.setVisible(true);
		frame.getContentPane().add(lblDriveMain);

		JLabel lblChronoSmall = new JLabel("Chrono Tool");
		lblChronoSmall.setHorizontalAlignment(SwingConstants.CENTER);
		lblChronoSmall.setForeground(Color.BLACK);
		lblChronoSmall.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 35));
		lblChronoSmall.setBounds(706, 11, 417, 62);
		lblChronoSmall.setVisible(false);
		frame.getContentPane().add(lblChronoSmall);

		JLabel lblDriveSmall = new JLabel("Google Drive");
		lblDriveSmall.setHorizontalAlignment(SwingConstants.CENTER);
		lblDriveSmall.setForeground(Color.BLACK);
		lblDriveSmall.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 25));
		lblDriveSmall.setBounds(706, 23, 417, 90);
		lblDriveSmall.setVisible(false);
		frame.getContentPane().add(lblDriveSmall);

		Button button = new Button("Close");
		button.setBounds(1029, 211, 53, 22);
		button.setVisible(false);
		frame.getContentPane().add(button);

		Button btnFileContents = new Button("File contents");
		btnFileContents.setBounds(745, 210, 111, 23);
		btnFileContents.setVisible(false);
		frame.getContentPane().add(btnFileContents);

		JTree tree = new JTree(drive.buildTree());
		tree.setBounds(45, 100, 585, 564);
		
		JScrollPane scroll = new JScrollPane(tree);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(45, 100, 585, 564);
		frame.getContentPane().add(scroll);

		List listFileStats1 = new List();
		listFileStats1.setFont(new Font("Arial", Font.BOLD, 12));
		listFileStats1.setBounds(745, 349, 169, 95);
		listFileStats1.add("Folders");
		listFileStats1.add("Shared Folders");
		listFileStats1.add("Documents");
		listFileStats1.add("Shared Documents");
		listFileStats1.add("Files");
		listFileStats1.add("Shared Files");
		frame.getContentPane().add(listFileStats1);

		List listFileStats2 = new List();
		listFileStats2.setFont(new Font("Arial", Font.PLAIN, 12));
		listFileStats2.setBounds(913, 349, 169, 95);
		listFileStats2.add(drive.getFileCount("Folders",false));
		listFileStats2.add(drive.getFileCount("Folders",true));
		listFileStats2.add(drive.getFileCount("Docs", false));
		listFileStats2.add(drive.getFileCount("Docs", true));
		listFileStats2.add(drive.getFileCount("Files", false));
		listFileStats2.add(drive.getFileCount("Files", true));
		frame.getContentPane().add(listFileStats2);

		List list_6 = new List();
		list_6.setFont(new Font("Arial", Font.BOLD, 12));
		list_6.setBounds(745, 456, 169, 50);
		Iterator<Entry<String, Integer>> it1 =top3.entrySet().iterator();
		int i = 1;
		while (it1.hasNext()) {
			Map.Entry pair = (Map.Entry)it1.next();
			if(!pair.getKey().equals("total")){
				list_6.add("TOP " + i +" - " + pair.getKey());
				i++;
			}
		}
		frame.getContentPane().add(list_6);

		List list_7 = new List();
		list_7.setFont(new Font("Arial", Font.PLAIN, 12));
		list_7.setBounds(913, 456, 169, 50);
		Iterator<Entry<String, Integer>> it2 =top3.entrySet().iterator();
		while (it2.hasNext()) {
			Map.Entry pair = (Map.Entry)it2.next();
			if(!pair.getKey().equals("total")){
				list_7.add("" + drive.getPercentage(top3.get("total"), (Integer) pair.getValue())+ "%");
			}
		}
		frame.getContentPane().add(list_7);
		
		Button button_5 = new Button("Main Menu");
		button_5.setBounds(1282, 655, 70, 22);
		frame.getContentPane().add(button_5);
		
		//// ACTIONS PREFORMED IN THE WINDOW ELEMENTS ////
		
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new App();
				frame.dispose();
			}
		});

		buttonPath1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DriveExtensionsByPath(drive, false);
			}
		});

		buttonPath2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DriveExtensionsByPath(drive, true);
			}
		});

		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DriveFilenamesByKw(drive);
			}
		});

		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DriveFileContentsByKw(drive, null);
			}
		});

		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DriveFilenamesByPath(drive, false);
			}
		});

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DriveFilenamesByPath(drive, true);
			}
		});

		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DriveChrono(drive);
			}
		});

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2 && !me.isConsumed()) {
					entrySelected = doMouseClicked(me, tree, drive);
					me.consume();
					btnFileContents.setVisible(true);
					button.setVisible(true);
					list.setVisible(true);
					lblChronoMain.setVisible(false);
					lblDriveMain.setVisible(false);
					lblChronoSmall.setVisible(true);
					lblDriveSmall.setVisible(true);
					list.removeAll();
					list_values.setVisible(true);
					list_values.removeAll();
					list.add("File Name"); 
					list_values.add(entrySelected.getFilename());
					if (!drive.getSizeCorrect(entrySelected.getSize()).startsWith("0")){
						list.add("File Size"); 
						list_values.add("" + drive.getSizeCorrect(entrySelected.getSize()));
					}
					list.add("Is Shaerd");
					if (entrySelected.getShared() == 1){
						list_values.add("Yes");
					}
					if (entrySelected.getShared() == 0){
						list_values.add("No");
					}
					list.add("Last Modified");
					java.util.Date time=new java.util.Date((long)entrySelected.getModified()*1000);
					list_values.add("" + time);
				}
			}
		});

		btnFileContents.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				String drivePath = System.getenv("HOMEPATH") + "/Google Drive";
				String path = drivePath + drive.getPathByDocId(entrySelected.getDocId()).substring(4);
				System.out.println(path);
				drive.showFileContent(path,drive);
			}
		});


		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setVisible(false);
				list_values.setVisible(false);
				button.setVisible(false);
				lblChronoMain.setVisible(true);
				lblDriveMain.setVisible(true);
				lblChronoSmall.setVisible(false);
				lblDriveSmall.setVisible(false);
				btnFileContents.setVisible(false);
			}
		});
	}

	// Function that returns the entry when the user clicks twice in an element of the tree
	public DatabaseSnapshotEntry doMouseClicked(MouseEvent me, JTree tree, Drive drive) {
		TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
		String doc_id = drive.getDirectoryDocIdByPath(drive.getPathFromTreePath(tp));
		DatabaseSnapshotEntry entry = drive.getEntryByDocId(doc_id);
		return entry;
	}
}