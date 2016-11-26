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
import java.util.Iterator;
import java.util.Map;

import javax.swing.JScrollPane;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JList;

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
		
		Button button = new Button("Close");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setVisible(false);
			    list_values.setVisible(false);
			    button.setVisible(false);
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
		frame.getContentPane().add(list_2);
		
		drive.getChildrenExtensionsStatistics("root");
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