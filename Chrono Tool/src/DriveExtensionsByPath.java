import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Drive.Drive;
import Drive.Database.DatabaseSnapshotEntry;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.List;
import java.awt.Button;
import java.awt.Choice;

public class DriveExtensionsByPath extends JFrame {

	private JFrame frame;
	private JTextField textField;
	private JLabel lblTypeAnExtension;
	private List list;
	private ChooseFromDirsTree choose;
	private Choice choice;
	private JLabel lblPathSelected;

	/**
	 * Launch the application.
	 */
	public static void main(Drive drive, boolean children) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriveExtensionsByPath window = new DriveExtensionsByPath(drive, children);
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
	public DriveExtensionsByPath(Drive drive, boolean children) {
		initialize(drive, children);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Drive drive, boolean children) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		lblTypeAnExtension = new JLabel("Select an extension");
		lblTypeAnExtension.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypeAnExtension.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTypeAnExtension.setBounds(88, 110, 257, 40);
		frame.getContentPane().add(lblTypeAnExtension);
		
		lblPathSelected = new JLabel("Path Selected");
		lblPathSelected.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPathSelected.setHorizontalAlignment(SwingConstants.CENTER);
		lblPathSelected.setBounds(100, 30, 223, 60);
		lblPathSelected.setVisible(false);
		frame.getContentPane().add(lblPathSelected);
		
		Button button = new Button("Select a path");
		button.setFont(new Font("Dialog", Font.BOLD, 16));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choose = new ChooseFromDirsTree(drive);
				button.setVisible(false);
				lblPathSelected.setVisible(true);
			}
		});
		
		choice = new Choice();
		choice.setBounds(124, 159, 185, 20);
		
		ArrayList<String> sortedKeys=new ArrayList<String>(drive.getChildrenExtensions("root").keySet());
		Collections.sort(sortedKeys);
		for (String key: sortedKeys){
			if(key.equals("0")){
				choice.add("No extension");
			}
			else{
				choice.add(key);
			}
		}
		frame.getContentPane().add(choice);
		
		button.setBounds(124, 30, 185, 60);
		frame.getContentPane().add(button);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String extension = choice.getSelectedItem();
				String path = choose.getPath();
				lblTypeAnExtension.setVisible(false);
				btnSearch.setVisible(false);
				choice.setVisible(false);
				lblPathSelected.setVisible(false);
				frame.setBounds(100, 100, 750, 500);
				list = new List();
				list.setBounds(35, 10, 257, 241);
				frame.getContentPane().add(list);
				if(!children){
					ArrayList<DatabaseSnapshotEntry> files = drive.getFilesByExtensionGivingPath(path, extension);
					for (DatabaseSnapshotEntry entry: files){
						list.add(entry.getFilename());
					}
				}
				else{
					ArrayList<DatabaseSnapshotEntry> files = drive.getFilesByChildrenExtensionGivingPath(path, extension);
					for (DatabaseSnapshotEntry entry: files){
						list.add(entry.getFilename());
					}
				}
			}
		});
		btnSearch.setBounds(172, 208, 89, 23);
		frame.getContentPane().add(btnSearch);	
	}
}
