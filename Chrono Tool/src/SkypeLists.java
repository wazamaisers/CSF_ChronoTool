import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import Skype.Skype;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;

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
		
		try {
			if(listType.equals("calls")){
				String[] columnNames = {"Name", "Skype Name", "Caller", "Answered", "Time", "Timestamp"};
				String [][] rowData = skype.getAllCalls();
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
				String [][] rowData = skype.getAllMessages();
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
				
				frame.setBounds(100, 100, 1200, 600);
				table.setBounds(46, 47, 800, 500);
				
				TableColumnModel tcm = table.getColumnModel();
				tcm.getColumn(0).setPreferredWidth(120);
				tcm.getColumn(1).setPreferredWidth(120);
				tcm.getColumn(2).setPreferredWidth(120);
				tcm.getColumn(3).setPreferredWidth(320);
				tcm.getColumn(4).setPreferredWidth(120);
				
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(46, 47, 800, 500);
				frame.getContentPane().add(scroll);
				
				JLabel lblMessage = new JLabel("Message:");
				lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
				lblMessage.setFont(new Font("Tahoma", Font.BOLD, 15));
				lblMessage.setBounds(890, 60, 122, 28);
				frame.getContentPane().add(lblMessage);
				
				JTextArea textArea = new JTextArea();
				textArea.setBounds(890, 90, 211, 300);
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				frame.getContentPane().add(textArea);
				
				JScrollPane scroll1 = new JScrollPane(textArea);
				scroll1.setBounds(890, 90, 211, 300);
				frame.getContentPane().add(scroll1);
				
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						String message = "";
						message = rowData[table.getRowSorter().convertRowIndexToModel(table.getSelectedRow())][3];
						textArea.setText(message);
					}
				});
			}
			else if(listType.equals("links")){
				String[] columnNames = {"Link", "Type", "Time","Timestamps"};
				String [][] rowData = skype.getAllLinks();
				table = new JTable(rowData,columnNames){
					public boolean isCellEditable(int rowData, int columnNames){
						return false;
					}
				};
				table.setAutoCreateRowSorter(true);
				table.getRowSorter().toggleSortOrder(3);
				table.removeColumn(table.getColumnModel().getColumn(3));
				
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
				table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
				
				table.setBounds(46, 47, 700, 500);
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(46, 47, 700, 500);
				frame.getContentPane().add(scroll);
				
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if(me.getClickCount() == 2 && !me.isConsumed()) {
							String link = "";
							link = rowData[table.getRowSorter().convertRowIndexToModel(table.getSelectedRow())][0];
							skype.links(link);
							table.getSelectedRow();
						}
					}
				});
			}
			else if(listType.equals("files")){
				String[] columnNames = {"File Name", "Local Path", "User Sharing With", "Size"};
				String [][] rowData = skype.getAllFiles();
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
				table.setAutoCreateRowSorter(true);
				
				table.setBounds(46, 47, 700, 500);
				JScrollPane scroll = new JScrollPane(table);
				scroll.setBounds(46, 47, 700, 500);
				frame.getContentPane().add(scroll);
				
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if(me.getClickCount() == 2 && !me.isConsumed()) {
							String path = "";
							path = rowData[table.getRowSorter().convertRowIndexToModel(table.getSelectedRow())][1];
							skype.showFileContent(path);
							table.getSelectedRow();
						}
					}
				});
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
