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

public class DriveFilenamesByPath extends JFrame {

	private JFrame frame;
	private JTextField textField;
	private List list;
	private HashMap<Integer, DatabaseSnapshotEntry> _list = new HashMap<Integer, DatabaseSnapshotEntry>();
	private ChooseFromDirsTree choose;

	/**
	 * Launch the application.
	 */
	public static void main(Drive drive, boolean children) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriveFilenamesByPath window = new DriveFilenamesByPath(drive, children);
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
	public DriveFilenamesByPath(Drive drive, boolean children) {
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
		
		JLabel lblPathSelected = new JLabel("Path Selected");
		lblPathSelected.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPathSelected.setHorizontalAlignment(SwingConstants.CENTER);
		lblPathSelected.setBounds(100, 30, 223, 60);
		lblPathSelected.setVisible(false);
		frame.getContentPane().add(lblPathSelected);
		
		JLabel lblIntroduceAWord = new JLabel("Introduce a word or expression");
		lblIntroduceAWord.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntroduceAWord.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblIntroduceAWord.setBounds(10, 116, 414, 40);
		frame.getContentPane().add(lblIntroduceAWord);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(83, 178, 264, 20);
		frame.getContentPane().add(textField);
		
		JButton button = new JButton("Search");
		button.setBounds(170, 209, 89, 23);
		frame.getContentPane().add(button);
		
		Button button_1 = new Button("Select a path");
		button_1.setFont(new Font("Dialog", Font.BOLD, 16));
		button_1.setBounds(124, 30, 185, 60);
		frame.getContentPane().add(button_1);
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choose = new ChooseFromDirsTree(drive);
				button_1.setVisible(false);
				lblPathSelected.setVisible(true);
			}
		});
		
		list = new List();
		list.setBounds(35, 10, 257, 241);
		list.setVisible(false);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = choose.getPath();
				lblIntroduceAWord.setVisible(false);
				textField.setVisible(false);
				button.setVisible(false);
				lblPathSelected.setVisible(false);
				frame.setBounds(100, 100, 750, 500);
				list.setVisible(true);
				frame.getContentPane().add(list);
				Integer i = 0;
				if(!children){
					ArrayList<DatabaseSnapshotEntry> files = drive.getFilesByKeyWordGivePath(textField.getText(), path);
					for (DatabaseSnapshotEntry entry: files){
						list.add(entry.getFilename());
						_list.put(i, entry);
						i++;
					}
				}
				else{
					ArrayList<DatabaseSnapshotEntry> files = drive.getFilesByKeyWordChildrenGivePath(textField.getText(), path);
					for (DatabaseSnapshotEntry entry: files){
						list.add(entry.getFilename());
						_list.put(i, entry);
						i++;
					}
				}
				list.setVisible(true);
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2 && !me.isConsumed()) {
					me.consume();
					DatabaseSnapshotEntry entry = _list.get(list.getSelectedIndex());
					label_9.setVisible(false);
					label_4.setVisible(false);
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
}
