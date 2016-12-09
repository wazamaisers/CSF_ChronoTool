package Drive;
import java.awt.EventQueue;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DriveFileContents {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String string, ArrayList<String> array, Image image) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriveFileContents window = new DriveFileContents(string, array, image);
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
	public DriveFileContents(String string, ArrayList<String> array, Image image) {
		initialize(string, array, image);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String string, ArrayList<String> array, Image image) {
		frame = new JFrame("Google Drive - File Contents");
		frame.setBounds(50, 50, 1200, 650);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		if(!(string==null) || !(array==null)){
			JTextArea textArea = new JTextArea();
			textArea.setBounds(58, 37, 1100, 560);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			textArea.setEditable(false);
			
			JScrollPane scroll = new JScrollPane(textArea);
			scroll.setBounds(58, 37, 1100, 560);
			frame.getContentPane().add(scroll);
			
			if(!(string==null)){
				textArea.setText(string);
			}
			else{
				String str2 = "";
				for(String str: array){
					str2 = str2 + str;
				}
				textArea.setText(str2);
			}
		}
		else{
			JLabel label = new JLabel(new ImageIcon(image));
			label.setBounds(58, 37, 1100, 560);
			
			JScrollPane scroll = new JScrollPane(label);
			scroll.setBounds(58, 37, 1100, 560);
			frame.getContentPane().add(scroll);
		}		
	}
}
