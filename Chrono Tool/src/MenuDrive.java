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
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JScrollPane;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JList;
import java.awt.List;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenu;
import javax.swing.SwingConstants;

public class MenuDrive {

	private JFrame frame;

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

		JLabel lblDriveVersion = new JLabel("");
		lblDriveVersion.setForeground(Color.WHITE);
		lblDriveVersion.setBounds(45, 23, 585, 33);
		lblDriveVersion.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDriveVersion.setText("Drive version: " + drive.getDriveVersion());
		lblDriveVersion.setVisible(true);
		frame.getContentPane().add(lblDriveVersion);

		JLabel lblDriveUsername = new JLabel("");
		lblDriveUsername.setForeground(Color.WHITE);
		lblDriveUsername.setBounds(45, 53, 585, 33);
		lblDriveUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDriveUsername.setText("Drive username: " + drive.getUserMail());
		lblDriveUsername.setVisible(true);
		frame.getContentPane().add(lblDriveUsername);

		java.awt.List list = new java.awt.List();
		list.setFont(new Font("Arial", Font.BOLD, 12));
		list.setBounds(745, 100, 169, 113);
		list.setVisible(false);
		frame.getContentPane().add(list);

		java.awt.List list_values = new java.awt.List();
		list_values.setFont(new Font("Arial", Font.PLAIN, 12));
		list_values.setBounds(913, 100, 169, 113);
		list_values.setVisible(false);
		frame.getContentPane().add(list_values);

		HashMap <String,Integer> top3 = drive.getChildrenExtensionsStatistics("root");

		java.awt.List list_1 = new java.awt.List();
		list_1.setBounds(745, 315, 169, 212);
		list_1.setFont(new Font("Arial", Font.BOLD, 12));
		list_1.add("Local Drive Size");
		list_1.add("Folders");
		list_1.add("Shared Folders");
		list_1.add("Documents");
		list_1.add("Shared Documents");
		list_1.add("Files");
		list_1.add("Shared Files");
		Iterator<Entry<String, Integer>> it1 =top3.entrySet().iterator();
		while (it1.hasNext()) {
			Map.Entry pair = (Map.Entry)it1.next();
			if(!pair.getKey().equals("total")){
				list_1.add("TOP - " + pair.getKey());
			}
		}
		frame.getContentPane().add(list_1);

		java.awt.List list_2 = new java.awt.List();
		list_2.setBounds(913, 315, 169, 212);
		list_2.setFont(new Font("Arial", Font.PLAIN, 12));
		list_2.add(drive.getLocalDriveSize());
		list_2.add(drive.getFileCount("Folders",false));
		list_2.add(drive.getFileCount("Folders",true));
		list_2.add(drive.getFileCount("Docs", false));
		list_2.add(drive.getFileCount("Docs", true));
		list_2.add(drive.getFileCount("Files", false));
		list_2.add(drive.getFileCount("Files", true));
		Iterator<Entry<String, Integer>> it2 =top3.entrySet().iterator();
		while (it2.hasNext()) {
			Map.Entry pair = (Map.Entry)it2.next();
			if(!pair.getKey().equals("total")){
				list_2.add("" + drive.getPercentage(top3.get("total"), (Integer) pair.getValue())+ "%");
			}
		}
		frame.getContentPane().add(list_2);

		List list_3 = new List();
		list_3.setBounds(842, 533, 144, 131);
		list_3.setFont(new Font("Arial", Font.PLAIN, 12));
		Integer total = drive.getTimes().get("Dawn") + drive.getTimes().get("Morning") + drive.getTimes().get("Afternoon") + drive.getTimes().get("Evening") +
				drive.getTimes().get("Night");
		list_3.add("Dawn :" + drive.getPercentage(total, drive.getTimes().get("Dawn")) + "%");
		list_3.add("Morning :" + drive.getPercentage(total, drive.getTimes().get("Morning")) + "%");
		list_3.add("Afternoon :" + drive.getPercentage(total, drive.getTimes().get("Afternoon")) + "%");
		list_3.add("Evening :" + drive.getPercentage(total, drive.getTimes().get("Evening")) + "%");
		list_3.add("Night :" + drive.getPercentage(total, drive.getTimes().get("Night")) + "%");
		frame.getContentPane().add(list_3);

		Button button_1 = new Button("File Extensions");
		button_1.setFont(new Font("Arial", Font.PLAIN, 15));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.setBounds(1194, 96, 158, 39);
		frame.getContentPane().add(button_1);

		JLabel lblSearchesByPath = new JLabel("Searches by Path");
		lblSearchesByPath.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSearchesByPath.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchesByPath.setBounds(1194, 53, 158, 24);
		frame.getContentPane().add(lblSearchesByPath);

		JLabel lblSearchesByKeyword = new JLabel("Searches by");
		lblSearchesByKeyword.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchesByKeyword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSearchesByKeyword.setBounds(1194, 291, 158, 33);
		frame.getContentPane().add(lblSearchesByKeyword);

		JLabel lblKeyword = new JLabel("KeyWord");
		lblKeyword.setHorizontalAlignment(SwingConstants.CENTER);
		lblKeyword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblKeyword.setBounds(1194, 310, 158, 33);
		frame.getContentPane().add(lblKeyword);

		Button button_2 = new Button("Filenames");
		button_2.setFont(new Font("Arial", Font.PLAIN, 15));
		button_2.setBounds(1194, 394, 158, 39);
		frame.getContentPane().add(button_2);

		Button button_3 = new Button("File Contents");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_3.setFont(new Font("Arial", Font.PLAIN, 15));
		button_3.setBounds(1194, 439, 158, 39);
		frame.getContentPane().add(button_3);

		Button button_4 = new Button("Files");
		button_4.setFont(new Font("Arial", Font.PLAIN, 15));
		button_4.setBounds(1194, 141, 158, 39);
		frame.getContentPane().add(button_4);

		Button button_5 = new Button("File Contents");
		button_5.setFont(new Font("Arial", Font.PLAIN, 15));
		button_5.setBounds(1194, 186, 158, 39);
		frame.getContentPane().add(button_5);

		Button button_6 = new Button("File Extensions");
		button_6.setFont(new Font("Arial", Font.PLAIN, 15));
		button_6.setBounds(1194, 349, 158, 39);
		frame.getContentPane().add(button_6);

		JLabel lblChronological = new JLabel("Chronological");
		lblChronological.setHorizontalAlignment(SwingConstants.CENTER);
		lblChronological.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblChronological.setBounds(1194, 549, 158, 33);
		frame.getContentPane().add(lblChronological);

		JLabel lblTimeline = new JLabel("Timeline");
		lblTimeline.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeline.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblTimeline.setBounds(1194, 566, 158, 33);
		frame.getContentPane().add(lblTimeline);

		Button button_7 = new Button("Start Chrono Tool");
		button_7.setFont(new Font("Arial", Font.PLAIN, 15));
		button_7.setBounds(1194, 607, 158, 57);
		frame.getContentPane().add(button_7);

		JLabel lblChronoMain = new JLabel("Chrono Tool");
		lblChronoMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblChronoMain.setForeground(Color.WHITE);
		lblChronoMain.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 50));
		lblChronoMain.setBounds(706, 100, 417, 90);
		lblChronoMain.setVisible(true);
		frame.getContentPane().add(lblChronoMain);

		JLabel lblDriveMain = new JLabel("Google Drive");
		lblDriveMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblDriveMain.setForeground(Color.WHITE);
		lblDriveMain.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 35));
		lblDriveMain.setBounds(706, 143, 417, 90);
		lblDriveMain.setVisible(true);
		frame.getContentPane().add(lblDriveMain);

		JLabel lblChronoSmall = new JLabel("Chrono Tool");
		lblChronoSmall.setHorizontalAlignment(SwingConstants.CENTER);
		lblChronoSmall.setForeground(Color.WHITE);
		lblChronoSmall.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 35));
		lblChronoSmall.setBounds(706, 11, 417, 62);
		lblChronoSmall.setVisible(false);
		frame.getContentPane().add(lblChronoSmall);

		JLabel lblDriveSmall = new JLabel("Google Drive");
		lblDriveSmall.setHorizontalAlignment(SwingConstants.CENTER);
		lblDriveSmall.setForeground(Color.WHITE);
		lblDriveSmall.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 25));
		lblDriveSmall.setBounds(706, 23, 417, 90);
		lblDriveSmall.setVisible(false);
		frame.getContentPane().add(lblDriveSmall);
		
		Button button = new Button("Close");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setVisible(false);
				list_values.setVisible(false);
				button.setVisible(false);
				lblChronoMain.setVisible(true);
				lblDriveMain.setVisible(true);
				lblChronoSmall.setVisible(false);
				lblDriveSmall.setVisible(false);
			}
		});
		button.setBounds(1029, 211, 53, 22);
		button.setVisible(false);
		frame.getContentPane().add(button);

		//create the tree by passing in the root node
		JTree tree = new JTree(drive.buildTree());
		tree.setBounds(45, 100, 585, 564);

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2 && !me.isConsumed()) {
					DatabaseSnapshotEntry entry = doMouseClicked(me, tree, drive);
					me.consume();
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
					list_values.add(entry.getFilename());
					list.add("File Size"); 
					list_values.add("" + entry.getSize());
					list.add("Is Shaerd"); 
					list_values.add("" + entry.getShared());
				}
			}
		});

		JScrollPane scroll = new JScrollPane(tree);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(45, 100, 585, 564);
		frame.getContentPane().add(scroll);
	}

	public DatabaseSnapshotEntry doMouseClicked(MouseEvent me, JTree tree, Drive drive) {
		TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
		System.out.println(tp.toString());
		System.out.println(drive.getPathFromTreePath(tp));
		String doc_id = drive.getDirectoryDocIdByPath(drive.getPathFromTreePath(tp));
		DatabaseSnapshotEntry entry = drive.getEntryByDocId(doc_id);
		return entry;
	}
}