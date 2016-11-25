import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Drive.Drive;
import Drive.Database.DatabaseSnapshotEntry;

import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;

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
		frame.setVisible(true);

		JLabel lblDriveVersion = new JLabel("");
		lblDriveVersion.setBounds(45, 65, 448, 33);
		lblDriveVersion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDriveVersion.setText("Drive version: " + drive.getDriveVersion());
		lblDriveVersion.setVisible(true);
		frame.getContentPane().add(lblDriveVersion);

		JLabel lblDriveUsername = new JLabel("");
		lblDriveUsername.setBounds(45, 106, 448, 33);
		lblDriveUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDriveUsername.setText("Drive username: " + drive.getUserMail());
		lblDriveUsername.setVisible(true);
		frame.getContentPane().add(lblDriveUsername);

		//create the tree by passing in the root node
		JTree tree = new JTree(drive.buildTree());
		tree.setBounds(45, 160, 585, 534);

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2 && !me.isConsumed()) {
					doMouseClicked(me, tree, drive);
				    me.consume(); 
					//handle double click event.
				}
			}
		});

		JScrollPane scroll = new JScrollPane(tree);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(45, 160, 585, 534);
		frame.getContentPane().add(scroll);
	}

	void doMouseClicked(MouseEvent me, JTree tree, Drive drive) {
		TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
		System.out.println(tp.toString());
		System.out.println(drive.getPathFromTreePath(tp));
		String doc_id = drive.getDirectoryDocIdByPath(drive.getPathFromTreePath(tp));
		DatabaseSnapshotEntry entry = drive.getEntryByDocId(doc_id);
		System.out.println(entry.getFilename());
		System.out.println(entry.getDocType());
		System.out.println(entry.getSize());

	}
}