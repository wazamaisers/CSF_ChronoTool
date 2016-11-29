import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Drive.Drive;
import Drive.Database.DatabaseSnapshotEntry;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.List;
import java.awt.Button;
import java.awt.Choice;
import javax.swing.JList;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Label;

public class DriveExtensionsByPath extends JFrame {

	private JFrame frame;
	private JTextField textField;
	private JLabel lblTypeAnExtension;
	private List list;
	private ChooseFromDirsTree choose;
	private Choice choice;
	private JLabel lblPathSelected;
	private HashMap<Integer, DatabaseSnapshotEntry> _list = new HashMap<Integer, DatabaseSnapshotEntry>();

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
		
		Label label = new Label("New label");
		label.setAlignment(Label.CENTER);
		label.setBounds(350, 30, 400, 23);
		label.setVisible(false);
		frame.getContentPane().add(label);
		
		Label label_1 = new Label("New label");
		label_1.setAlignment(Label.CENTER);
		label_1.setBounds(350, 60, 400, 23);
		label_1.setVisible(false);
		frame.getContentPane().add(label_1);
		
		Label label_2 = new Label("New label");
		label_2.setAlignment(Label.CENTER);
		label_2.setBounds(350, 90, 400, 23);
		label_2.setVisible(false);
		frame.getContentPane().add(label_2);
		
		Label label_3 = new Label("New label");
		label_3.setAlignment(Label.CENTER);
		label_3.setBounds(350, 120, 400, 23);
		label_3.setVisible(false);
		frame.getContentPane().add(label_3);
		
		Label label_4 = new Label("New label");
		label_4.setAlignment(Label.CENTER);
		label_4.setBounds(350, 150, 400, 23);
		label_4.setVisible(false);
		frame.getContentPane().add(label_4);
		
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
				Integer i = 0;
				if(!children){
					ArrayList<DatabaseSnapshotEntry> files = drive.getFilesByExtensionGivingPath(path, extension);
					for (DatabaseSnapshotEntry entry: files){
						list.add(entry.getFilename());
						_list.put(i, entry);
						i++;
					}
				}
				else{
					ArrayList<DatabaseSnapshotEntry> files = drive.getFilesByChildrenExtensionGivingPath(path, extension);
					for (DatabaseSnapshotEntry entry: files){
						list.add(entry.getFilename());
						_list.put(i, entry);
						i++;
					}
				}
				frame.getContentPane().add(list);
				
				list.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if(me.getClickCount() == 2 && !me.isConsumed()) {
							me.consume();
							DatabaseSnapshotEntry entry = _list.get(list.getSelectedIndex());
							label.setText(entry.getFilename());
							label.setVisible(true);
							label_1.setText(drive.getPathByDocId(entry.getDocId()));
							label_1.setVisible(true);
							if (!drive.getSizeCorrect(entry.getSize()).startsWith("0")){
								label_2.setText(drive.getSizeCorrect(entry.getSize()));
								label_2.setVisible(true);
								if (entry.getShared() == 1){
									label_3.setText("Yes");
									label_3.setVisible(true);
								}
								if (entry.getShared() == 0){
									label_3.setText("No");
									label_3.setVisible(true);
								}
								java.util.Date time=new java.util.Date((long)entry.getModified()*1000);
								label_4.setText("" + time);
								label_4.setVisible(true);
							}
							else{
								if (entry.getShared() == 1){
									label_2.setText("Yes");
									label_2.setVisible(true);
								}
								if (entry.getShared() == 0){
									label_2.setText("No");
									label_2.setVisible(true);
								}
								java.util.Date time=new java.util.Date((long)entry.getModified()*1000);
								label_3.setText("" + time);
								label_3.setVisible(true);
							}
							
							
							System.out.println(list.getSelectedIndex());
							System.out.println(drive.getPathByDocId(
									_list.get(list.getSelectedIndex()).getDocId()));
						}
					}
				});
			}
		});
		
		btnSearch.setBounds(172, 208, 89, 23);
		frame.getContentPane().add(btnSearch);
	}
}
