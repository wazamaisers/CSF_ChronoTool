import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.List;

import javax.swing.JFrame;

import Drive.Drive;
import Drive.Database.DatabaseSnapshotEntry;

import javax.swing.JLabel;
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
	private List list;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
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
		
		button = new JButton("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblIntroduceAWord.setVisible(false);
				textField.setVisible(false);
				button.setVisible(false);
				frame.setBounds(100, 100, 750, 500);
				list.setVisible(true);
				frame.getContentPane().add(list);
				Integer i = 0;
				ArrayList<DatabaseSnapshotEntry> filesList = drive.getFilesByKeyWord(textField.getText());
				for (DatabaseSnapshotEntry entry: filesList){
					list.add(entry.getFilename());
					_list.put(i, entry);
					i++;
				}
				list.setVisible(true);
			}
		});
		button.setBounds(172, 162, 89, 23);
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
						label_4.setVisible(false);
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

}
