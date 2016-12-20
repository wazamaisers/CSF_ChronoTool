import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.List;

import javax.swing.JFrame;

import Drive.Drive;
import Drive.Database.DatabaseSnapshotEntry;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class DriveFilenamesByKw {

	private JFrame frame;
	private JTextField textField;
	private JButton button;
	private HashMap<Integer, DatabaseSnapshotEntry> _list = new HashMap<Integer, DatabaseSnapshotEntry>();
	private ArrayList<DatabaseSnapshotEntry> _array_list = new ArrayList<DatabaseSnapshotEntry>();
	private List list;
	private DatabaseSnapshotEntry entrySelected = null;

	/**
	 * Launch the application.
	 */
	public static void main(Drive drive) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriveFilenamesByKw window = new DriveFilenamesByKw(drive);
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
	public DriveFilenamesByKw(Drive drive) {
		initialize(drive);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Drive drive) {
		frame = new JFrame("Google Drive - File Names by Key Word");
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		//// ELEMENTS OF THIS WINDOW ////

		JLabel lblIntroduceAWord = new JLabel("Introduce a word or expression");
		lblIntroduceAWord.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntroduceAWord.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblIntroduceAWord.setBounds(10, 47, 414, 40);
		frame.getContentPane().add(lblIntroduceAWord);

		textField = new JTextField();
		textField.setBounds(85, 131, 264, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		list = new List();
		list.setBounds(35, 10, 257, 241);
		list.setVisible(false);
		
		JButton btnKeyWordSearch = new JButton("Search by Keyword");
		btnKeyWordSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnKeyWordSearch.setBounds(90, 270, 160, 42);
		btnKeyWordSearch.setVisible(false);
		frame.getContentPane().add(btnKeyWordSearch);

		Label label = new Label();
		label.setBounds(450, 30, 550, 23);
		label.setVisible(false);
		frame.getContentPane().add(label);

		Label label_1 = new Label();
		label_1.setBounds(450, 60, 550, 23);
		label_1.setVisible(false);
		frame.getContentPane().add(label_1);

		Label label_2 = new Label();
		label_2.setBounds(450, 90, 550, 23);
		label_2.setVisible(false);
		frame.getContentPane().add(label_2);

		Label label_3 = new Label();
		label_3.setBounds(450, 120, 550, 23);
		label_3.setVisible(false);
		frame.getContentPane().add(label_3);

		Label label_4 = new Label();
		label_4.setBounds(450, 150, 550, 23);
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
		
		JLabel lblKeyWord = new JLabel("Key Word:");
		lblKeyWord.setHorizontalAlignment(SwingConstants.CENTER);
		lblKeyWord.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblKeyWord.setBounds(74, 339, 110, 23);
		lblKeyWord.setVisible(false);
		frame.getContentPane().add(lblKeyWord);
		
		JLabel lblKw = new JLabel();
		lblKw.setHorizontalAlignment(SwingConstants.LEFT);
		lblKw.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblKw.setBounds(185, 339, 815, 23);
		lblKw.setVisible(false);
		frame.getContentPane().add(lblKw);
		
		JButton btnSeeFileContent = new JButton("File Content");
		btnSeeFileContent.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSeeFileContent.setBounds(350, 200, 130, 42);
		btnSeeFileContent.setVisible(false);
		frame.getContentPane().add(btnSeeFileContent);
		
		button = new JButton("Search");
		button.setBounds(172, 162, 89, 23);
		frame.getContentPane().add(button);
		
		//// ACTIONS PREFORMED IN THE WINDOW ELEMENTS ////
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblIntroduceAWord.setVisible(false);
				lblKeyWord.setVisible(true);
				lblKw.setText(textField.getText());
				lblKw.setVisible(true);
				textField.setVisible(false);
				button.setVisible(false);
				frame.setBounds(100, 100, 1150, 450);
				list.setVisible(true);
				frame.getContentPane().add(list);
				Integer i = 0;
				ArrayList<DatabaseSnapshotEntry> filesList = drive.getFilesByKeyWord(textField.getText());
				for (DatabaseSnapshotEntry entry: filesList){
					list.add(entry.getFilename());
					_list.put(i, entry);
					_array_list.add(entry);
					i++;
				}
				
				try {
					list.getItem(0);
				} catch (Exception ex) {
					frame.setVisible(false);
					JOptionPane.showMessageDialog(null, "The list is empty", "InfoBox: " + "Listing error", JOptionPane.INFORMATION_MESSAGE);
					ex.printStackTrace();
				}
				btnKeyWordSearch.setVisible(true);
				list.setVisible(true);
			}
		});
		
		btnSeeFileContent.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				String drivePath = System.getenv("HOMEPATH") + "/Google Drive";
				String path = drivePath + drive.getPathByDocId(entrySelected.getDocId()).substring(4);
				System.out.println(path);
				drive.showFileContent(path,drive);
			}
		});
		
		btnKeyWordSearch.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				new DriveFileContentsByKw(drive, _array_list);
			}
		});

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2 && !me.isConsumed()) {
					me.consume();
					btnSeeFileContent.setVisible(true);
					entrySelected = _list.get(list.getSelectedIndex());
					label_9.setVisible(false);
					label_4.setVisible(false);
					label.setText(entrySelected.getFilename());
					label.setVisible(true);
					label_5.setText("File Name");
					label_5.setVisible(true);
					label_1.setText(drive.getPathByDocId(entrySelected.getDocId()));
					label_1.setVisible(true);
					label_6.setText("Path");
					label_6.setVisible(true);
					if (!drive.getSizeCorrect(entrySelected.getSize()).startsWith("0")){
						label_2.setText(drive.getSizeCorrect(entrySelected.getSize()));
						label_2.setVisible(true);
						label_7.setText("File Size");
						label_7.setVisible(true);
						if (entrySelected.getShared() == 1){
							label_3.setText("Yes");
						}
						if (entrySelected.getShared() == 0){
							label_3.setText("No");
						}
						label_3.setVisible(true);
						label_8.setText("Is shared");
						label_8.setVisible(true);
						java.util.Date time=new java.util.Date((long)entrySelected.getModified()*1000);
						label_4.setText("" + time);
						label_4.setVisible(true);
						label_9.setText("Last Modified");
						label_9.setVisible(true);
					}
					else{
						if (entrySelected.getShared() == 1){
							label_2.setText("Yes");
						}
						if (entrySelected.getShared() == 0){
							label_2.setText("No");;
						}
						label_2.setVisible(true);
						label_7.setText("Is shared");
						label_7.setVisible(true);
						java.util.Date time=new java.util.Date((long)entrySelected.getModified()*1000);
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
}
