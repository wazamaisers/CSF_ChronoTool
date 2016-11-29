import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;

import javax.swing.JFrame;

import Drive.Drive;
import Drive.Database.DatabaseSnapshotEntry;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class DriveFilenamesByKw {

	private JFrame frame;
	private JTextField textField;
	private JButton button;

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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		button = new JButton("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblIntroduceAWord.setVisible(false);
				textField.setVisible(false);
				button.setVisible(false);
				frame.setBounds(100, 100, 750, 500);
				List list = new List();
				list.setBounds(35, 10, 257, 241);
				list.setVisible(true);
				frame.getContentPane().add(list);
				ArrayList<DatabaseSnapshotEntry> filesList = drive.getFilesByKeyWord(textField.getText());
				for (DatabaseSnapshotEntry entry: filesList){
					list.add(entry.getFilename());
				}
				list.setVisible(true);
			}
		});
		button.setBounds(172, 162, 89, 23);
		frame.getContentPane().add(button);
	}

}
