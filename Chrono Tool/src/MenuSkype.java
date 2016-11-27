import java.awt.EventQueue;

import javax.swing.JFrame;

import Drive.Drive;
import Skype.Skype;

public class MenuSkype {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(Skype skype) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuSkype window = new MenuSkype(skype);
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
	public MenuSkype(Skype skype) {
		initialize(skype);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Skype skype) {
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
	}

}
