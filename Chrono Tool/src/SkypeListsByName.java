import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import Skype.Skype;

public class SkypeListsByName {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String listType, Skype skype, String name) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SkypeListsByName window = new SkypeListsByName(listType, skype, name);
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
	public SkypeListsByName(String listType, Skype skype, String skypeName) {
		initialize(listType,skype, skypeName);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String listType, Skype skype, String skypeName) {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		try {
			if(listType.equals("calls")){
				String[] columnNames = {"Name", "Skype Name", "Caller", "Answered", "Time", "Timestamp"};
				String[][] rowData = skype.getAllCallsByName(skypeName);
				table = new JTable(rowData,columnNames){
					public boolean isCellEditable(int rowData, int columnNames){
						return false;
					}
				};

				table.setAutoCreateRowSorter(true);
				table.getRowSorter().toggleSortOrder(5);
				table.removeColumn(table.getColumnModel().getColumn(5));

				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

				table.setBounds(46, 47, 700, 500);
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(46, 47, 700, 500);
				frame.getContentPane().add(scroll);
			}
			else if(listType.equals("messages")){
				String[] columnNames = {"Chat With", "Author Skype Name", "Author Name", "Message", "Time","Timestamps"};
				String[][] rowData = skype.getAllMessagesByName(skypeName);
				table = new JTable(rowData,columnNames){
					public boolean isCellEditable(int rowData, int columnNames){
						return false;
					}
				};
				table.setAutoCreateRowSorter(true);
				table.getRowSorter().toggleSortOrder(5);
				table.removeColumn(table.getColumnModel().getColumn(5));

				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

				table.setBounds(46, 47, 700, 500);
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(46, 47, 700, 500);
				frame.getContentPane().add(scroll);
			}
			else if(listType.equals("files")){
				String[] columnNames = {"File Name", "Local Path", "User Sharing With", "Size"};
				String[][] rowData = skype.getAllFilesByName(skypeName);
				table = new JTable(rowData,columnNames){
					public boolean isCellEditable(int rowData, int columnNames){
						return false;
					}
				};

				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

				table.setBounds(46, 47, 700, 500);
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(46, 47, 700, 500);
				frame.getContentPane().add(scroll);
			}
			
			if(table.getRowCount() == 0){
				frame.setVisible(false);
				JOptionPane.showMessageDialog(null, "The list is empty", "InfoBox: " + "Listing error", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			frame.setVisible(false);
			JOptionPane.showMessageDialog(null, "The list is empty", "InfoBox: " + "Listing error", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
	}

}
