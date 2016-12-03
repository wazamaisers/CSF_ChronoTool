import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import Drive.Drive;
import Skype.Skype;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
		
		String[] columnNames = {"Name", "Skype Name", "Gender", "Friends Since"};
		String[][] rowData = skype.getBasicContactInfo();
		JTable table = new JTable(rowData,columnNames){
			public boolean isCellEditable(int rowData, int columnNames){
				return false;
			}
		};
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		table.setBounds(79, 53, 463, 586);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(79, 53, 463, 586);
		frame.getContentPane().add(scroll);
	}
}
