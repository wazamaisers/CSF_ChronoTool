import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Skype.Skype;

public class SkypeLists {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String listType, Skype skype) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SkypeLists window = new SkypeLists(listType, skype);
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
	public SkypeLists(String listType, Skype skype) {
		initialize(listType,skype);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String listType, Skype skype) {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		if(listType.equals("calls")){
			String[] columnNames = {"Name", "Skype Name", "Caller", "Answered", "Time", "Timestamp"};
			String[][] rowData = skype.getAllCalls();
			table = new JTable(rowData,columnNames){
				public boolean isCellEditable(int rowData, int columnNames){
					return false;
				}
			};
			table.setAutoCreateRowSorter(true);
			table.getRowSorter().toggleSortOrder(5);
			table.removeColumn(table.getColumnModel().getColumn(5));
			table.setBounds(46, 47, 700, 500);
			JScrollPane scroll = new JScrollPane(table);
			scroll.setBounds(46, 47, 700, 500);
			frame.getContentPane().add(scroll);
		}
		else if(listType.equals("messages")){
			String[] columnNames = {"Chat With", "Author Skype Name", "Author Name", "Message", "Time","Timestamps"};
			String[][] rowData = skype.getAllMessages();
			table = new JTable(rowData,columnNames){
				public boolean isCellEditable(int rowData, int columnNames){
					return false;
				}
			};
			table.setAutoCreateRowSorter(true);
			table.getRowSorter().toggleSortOrder(5);
			table.removeColumn(table.getColumnModel().getColumn(5));
			table.setBounds(46, 47, 700, 500);
			JScrollPane scroll = new JScrollPane(table);
			scroll.setBounds(46, 47, 700, 500);
			frame.getContentPane().add(scroll);
		}
		else if(listType.equals("links")){
			String[] columnNames = {"Link", "Type", "Time","Timestamps"};
			String[][] rowData = skype.getAllLinks();
			table = new JTable(rowData,columnNames){
				public boolean isCellEditable(int rowData, int columnNames){
					return false;
				}
			};
			table.setAutoCreateRowSorter(true);
			table.getRowSorter().toggleSortOrder(3);
			table.removeColumn(table.getColumnModel().getColumn(3));
			table.setBounds(46, 47, 700, 500);
			JScrollPane scroll = new JScrollPane(table);
			scroll.setBounds(46, 47, 700, 500);
			frame.getContentPane().add(scroll);
		}
		else if(listType.equals("files")){
			String[] columnNames = {"File Name", "Local Path", "User Sharing With", "Size"};
			String[][] rowData = skype.getAllFiles();
			table = new JTable(rowData,columnNames){
				public boolean isCellEditable(int rowData, int columnNames){
					return false;
				}
			};
			//table.setAutoCreateRowSorter(true);
			//table.getRowSorter().toggleSortOrder(4);
			table.setBounds(46, 47, 700, 500);
			JScrollPane scroll = new JScrollPane(table);
			scroll.setBounds(46, 47, 700, 500);
			frame.getContentPane().add(scroll);
		}
	}

}
