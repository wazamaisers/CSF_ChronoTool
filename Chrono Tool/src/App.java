import java.awt.EventQueue;

import javax.swing.JFrame;
import Drive.Drive;
import Skype.Skype;

import javax.swing.JFileChooser;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class App {

	private JFrame frame;
	private String fileNameInp;
	private Drive _drive = null;
	private Skype _skype = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
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
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		inicialMenu();
	}

	public void mainMenuDrive(Drive drive){
		MenuDrive menuDrive = new MenuDrive(drive);
	}

	public void inicialMenu(){
		//Window
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
//		try {
//			frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(
//					new File(System.getProperty("user.dir") + "\\image.jpg")))));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		frame.pack();
		frame.setVisible(true);

		//Chrono Tool Title
		JLabel lblChronoTool = new JLabel("Chrono Tool");
		lblChronoTool.setForeground(Color.WHITE);
		lblChronoTool.setHorizontalAlignment(SwingConstants.CENTER);
		lblChronoTool.setFont(new Font("Snap ITC", Font.BOLD, 50));
		lblChronoTool.setBounds(468, 65, 417, 90);
		frame.getContentPane().add(lblChronoTool);

		//Google Drive Folder Selection Button
		JButton btnDrive = new JButton("Drive");
		btnDrive.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnDrive.setBounds(252, 287, 389, 130);
		frame.getContentPane().add(btnDrive);

		//Skype Folder Selection Button
		JButton btnSkype = new JButton("Skype");
		btnSkype.setFont(new Font("Tahoma", Font.PLAIN, 40));
		btnSkype.setBounds(712, 287, 389, 130);
		frame.getContentPane().add(btnSkype);

		//Text To Appear when Drive path is chosen
		JLabel lblDrive = new JLabel("Drive");
		lblDrive.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
		lblDrive.setForeground(Color.WHITE);
		lblDrive.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrive.setBounds(252, 287, 389, 130);
		frame.getContentPane().add(lblDrive);
		lblDrive.setVisible(false);

		//Text To Appear when Skype path is chosen
		JLabel lblSkype = new JLabel("Skype");
		lblSkype.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 20));
		lblSkype.setForeground(Color.WHITE);
		lblSkype.setHorizontalAlignment(SwingConstants.CENTER);
		lblSkype.setBounds(712, 287, 389, 130);
		frame.getContentPane().add(lblSkype);
		lblSkype.setVisible(false);

		//Start application button
		JButton btnStartApplication = new JButton("Start Application");
		btnStartApplication.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnStartApplication.setBounds(540, 511, 273, 47);
		frame.getContentPane().add(btnStartApplication);
		btnStartApplication.setVisible(false);

		btnDrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = fileChooser(e);
				_drive = new Drive(fileName);
				if(_drive.getDatabaseLoaded()){
					btnDrive.setVisible(false);
					lblDrive.setText("Google Drive database loaded!");
					lblDrive.setVisible(true);
					if(!btnStartApplication.isVisible()){
						btnStartApplication.setVisible(true);
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "The path given for Google Drive folder is not correct", "InfoBox: " + "Error loadig database", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		btnSkype.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = fileChooser(e);
				_skype = new Skype(fileName);
				if(_skype.getDatabaseLoaded()){
					btnSkype.setVisible(false);
					lblSkype.setText("Skype database loaded!");
					lblSkype.setVisible(true);
					if(!btnStartApplication.isVisible()){
						btnStartApplication.setVisible(true);
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "The path given for Skype folder is not correct", "InfoBox: " + "Error loadig database", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		btnStartApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblSkype.isVisible() && !lblDrive.isVisible()){
					System.out.println("Abre o Skype");
				}
				else if (!lblSkype.isVisible() && lblDrive.isVisible()){
					System.out.println("Abre o Drive");
					frame.setVisible(false);
					mainMenuDrive(_drive);
				}
				else {
					System.out.println("Abre os dois");
				}
				lblSkype.setVisible(false);
				btnSkype.setVisible(false);
				lblDrive.setVisible(false);
				btnDrive.setVisible(false);
				btnStartApplication.setVisible(false);
			}
		});
	}

	public String fileChooser(ActionEvent e){
		String fileName = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setBounds(79, 184, 582, 397);

		int returnVal = fileChooser.showOpenDialog((Component)e.getSource());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				fileNameInp = file.toString();
				fileName = fileNameInp.replace(Character.toString ((char) 92),"/");
				System.out.println(fileName);
			} catch (Exception ex) {
				System.out.println("problem accessing file"+file.getAbsolutePath());
			}
		} 
		else {
			System.out.println("File access cancelled by user.");
		}
		return fileName;
	}
}
