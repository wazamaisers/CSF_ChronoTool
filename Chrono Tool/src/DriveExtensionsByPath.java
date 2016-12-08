import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;

import Drive.Drive;
import Drive.Database.DatabaseSnapshotEntry;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
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
import javax.swing.JOptionPane;

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
		
		Label label = new Label();
		label.setBounds(450, 30, 400, 23);
		label.setVisible(false);
		frame.getContentPane().add(label);
		
		Label label_1 = new Label();
		label_1.setBounds(450, 60, 400, 23);
		label_1.setVisible(false);
		frame.getContentPane().add(label_1);
		
		Label label_2 = new Label();
		label_2.setBounds(450, 90, 400, 23);
		label_2.setVisible(false);
		frame.getContentPane().add(label_2);
		
		Label label_3 = new Label();
		label_3.setBounds(450, 120, 400, 23);
		label_3.setVisible(false);
		frame.getContentPane().add(label_3);
		
		Label label_4 = new Label();
		label_4.setBounds(450, 150, 400, 23);
		label_4.setVisible(false);
		frame.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(350, 30, 100, 23);
		label_5.setVisible(false);
		frame.getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(350, 60, 100, 23);
		label_6.setVisible(false);
		frame.getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel("");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(350, 90, 100, 23);
		label_7.setVisible(false);
		frame.getContentPane().add(label_7);
		
		JLabel label_8 = new JLabel("");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setBounds(350, 120, 100, 23);
		label_8.setVisible(false);
		frame.getContentPane().add(label_8);
		
		JLabel label_9 = new JLabel("");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setBounds(350, 150, 100, 23);
		label_9.setVisible(false);
		frame.getContentPane().add(label_9);
		
		JButton btnSeeFileContent = new JButton("File Content");
		btnSeeFileContent.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSeeFileContent.setBounds(350, 200, 130, 42);
		btnSeeFileContent.setVisible(false);
		frame.getContentPane().add(btnSeeFileContent);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String extension = choice.getSelectedItem();
				String path = choose.getPath();
				lblTypeAnExtension.setVisible(false);
				btnSearch.setVisible(false);
				choice.setVisible(false);
				lblPathSelected.setVisible(false);
				frame.setBounds(100, 100, 1000, 500);
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
				
				try {
					list.getItem(0);
				} catch (Exception e) {
					frame.setVisible(false);
					JOptionPane.showMessageDialog(null, "The list is empty", "InfoBox: " + "Listing error", JOptionPane.INFORMATION_MESSAGE);
					e.printStackTrace();
				}
				frame.getContentPane().add(list);
				
				list.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if(me.getClickCount() == 2 && !me.isConsumed()) {
							me.consume();
							btnSeeFileContent.setVisible(true);
							DatabaseSnapshotEntry entry = _list.get(list.getSelectedIndex());
							btnSeeFileContent.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent me) {
									String drivePath = System.getenv("HOMEPATH") + "/Google Drive";
									String path = drivePath + drive.getPathByDocId(entry.getDocId()).substring(4);
									System.out.println(path);
									showFileContent(path,drive);
								}
							});
							label.setText(entry.getFilename());
							label.setVisible(true);
							label_5.setText("File Name");
							label_5.setVisible(true);
							label_1.setText(drive.getPathByDocId(entry.getDocId()));
							label_1.setVisible(true);
							label_6.setText("Path");
							label_6.setVisible(true);
							if (!drive.getSizeCorrect(entry.getSize()).startsWith("0")){
								label_2.setText(drive.getSizeCorrect(entry.getSize()));
								label_2.setVisible(true);
								label_7.setText("File Size");
								label_7.setVisible(true);
								if (entry.getShared() == 1){
									label_3.setText("Yes");
								}
								if (entry.getShared() == 0){
									label_3.setText("No");
								}
								label_3.setVisible(true);
								label_8.setText("Is shared");
								label_8.setVisible(true);
								java.util.Date time=new java.util.Date((long)entry.getModified()*1000);
								label_4.setText("" + time);
								label_4.setVisible(true);
								label_9.setText("Last Modified");
								label_9.setVisible(true);
							}
							else{
								if (entry.getShared() == 1){
									label_2.setText("Yes");
								}
								if (entry.getShared() == 0){
									label_2.setText("No");;
								}
								label_2.setVisible(true);
								label_7.setText("Is shared");
								label_7.setVisible(true);
								java.util.Date time=new java.util.Date((long)entry.getModified()*1000);
								label_3.setText("" + time);
								label_3.setVisible(true);
								label_8.setText("Last Modified");
								label_8.setVisible(true);
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
	
	public void showFileContent(String path, Drive drive){
		String file_extension = FilenameUtils.getExtension(path);
		if(file_extension.equals("docx")){
			String result = drive.readDocx(path);
			System.out.println(result);
			new DriveFileContents(result,null,null);
		}
		else if(file_extension.equals("doc")){
			String result = drive.readDoc(path);
			System.out.println(result);
			new DriveFileContents(result,null,null);

		}
		else if(file_extension.equals("xls")){
			ArrayList<String> result = drive.readExcel(path,"xls");
			for(String s: result){
				System.out.println(s);
			}
			new DriveFileContents(null,result,null);
		}
		else if(file_extension.equals("xlsx")){
			ArrayList<String> result = drive.readExcel(path,"xlsx");
			for(String s: result){
				System.out.println(s);
			}
			new DriveFileContents(null,result,null);
		}
		else{
			try {
				String result = drive.readFile(path);
				if(result.equals("")){
					try {
						Image image = drive.readImage(path);
						new DriveFileContents(null,null,image);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
						System.out.println("not readable");
					}
				}
				else{
					new DriveFileContents(result,null,null);
				}
			} catch (Exception e) {
				System.out.println("not readable");
				JOptionPane.showMessageDialog(null, "File is not readable", "InfoBox: " + "Error reading file", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
