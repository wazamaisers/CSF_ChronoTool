import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Drive.Drive;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;

public class DriveChrono {

	private JFrame frame;
	private Integer last;
	private JTree tree;
	private JScrollPane scroll;
	private HashMap<Integer,Long> _table1 = new HashMap<Integer,Long>();
	private HashMap<Integer,Long> _table2 = new HashMap<Integer,Long>();
	private HashMap<Integer,Long> _table3 = new HashMap<Integer,Long>();

	/**
	 * Launch the application.
	 */
	public static void main(Drive drive) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriveChrono window = new DriveChrono(drive);
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
	public DriveChrono(Drive drive) {
		initialize(drive);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Drive drive) {
		frame = new JFrame("Google Drive - Chronology");
		frame.setBounds(100, 100, 450, 300);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		///////////////////////////////////////////////////////////////////////

		last = drive.getLastTime();
		java.util.Date time=new java.util.Date((long)last*1000);
		java.util.Date now = new java.util.Date();
		LocalDate time1 = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate now1 = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.print("Last dt " + time + "\n");
		System.out.print("Last lcd " + time1 + "\n");
		System.out.print("Last " + last + "\n");
		System.out.print("Last " + (long) Timestamp.valueOf(time1.atStartOfDay()).getTime()/1000 + "\n");
		System.out.print("Agora " + now + "\n");

		long totalWeeks = ChronoUnit.WEEKS.between(time1, now1);
		System.out.print(totalWeeks + "\n");
		long totalMonths = ChronoUnit.MONTHS.between(time1, now1);
		System.out.print(totalMonths + "\n");
		long totalYears = ChronoUnit.YEARS.between(time1, now1);
		System.out.print(totalMonths + "\n");

		/////////////////////////////////// WEEK SLIDER ///////////////////////////////////

		LocalDate week = now1;
		System.out.print(week + "\n");
		Hashtable<Integer, JLabel> table1 = new Hashtable<Integer, JLabel>();
		JSlider weeksSlider = new JSlider(JSlider.HORIZONTAL,0,(int)totalWeeks,(int)totalWeeks);
		weeksSlider.setPaintLabels(true);
		int i=0;
		int monthValue = 0;
		while(week.isAfter(time1)){
			if(!(week.getMonthValue()==monthValue)){
				monthValue = week.getMonthValue();
				if(week.getMonthValue()==1){
					table1.put((int)totalWeeks-i, new JLabel("Y" + week.getYear()%100));
				}
				else{
					table1.put((int)totalWeeks-i, new JLabel("" + monthValue));
				}
				System.out.print(week + " "+ i + "\n\n");
			}
			_table1.put((int)totalWeeks-i, (long) Timestamp.valueOf(week.atStartOfDay()).getTime()/1000);
			week = week.minus(1, ChronoUnit.WEEKS);
			System.out.print(week + " "+ i + "\n");
			i++;
		}
		weeksSlider.setLabelTable (table1);
		weeksSlider.setBounds(33, 619, 1268, 26);
		weeksSlider.setVisible(false);
		frame.getContentPane().add(weeksSlider);

		///////////////////////// MONTHS SLIDER //////////////////////////////////

		LocalDate month = now1;
		System.out.print(month + "\n");
		Hashtable<Integer, JLabel> table2 = new Hashtable<Integer, JLabel>();
		JSlider monthsSlider = new JSlider(JSlider.HORIZONTAL,0,(int)totalMonths,(int)totalMonths);
		monthsSlider.setPaintLabels(true);
		i=0;
		monthValue = 0;
		while(month.isAfter(time1)){
			monthValue = month.getMonthValue();
			if(month.getMonthValue()==1){
				table2.put((int)totalMonths-i, new JLabel("Y" + month.getYear()%100));
			}
			else{
				table2.put((int)totalMonths-i, new JLabel("" + monthValue));
			}
			_table2.put((int)totalMonths-i, (long) Timestamp.valueOf(month.atStartOfDay()).getTime()/1000);
			month = month.minus(1, ChronoUnit.MONTHS);
			System.out.print(month + " "+ i + "\n");
			i++;
		}
		monthsSlider.setLabelTable (table2);
		monthsSlider.setBounds(33, 619, 1268, 26);
		monthsSlider.setVisible(false);
		frame.getContentPane().add(monthsSlider);

		///////////////////////// YEARS SLIDER ///////////////////////////////

		LocalDate year = now1;
		System.out.print(year + "\n");
		Hashtable<Integer, JLabel> table3 = new Hashtable<Integer, JLabel>();
		JSlider yearsSlider = new JSlider(JSlider.HORIZONTAL,0,(int)totalYears,(int)totalYears);
		yearsSlider.setPaintLabels(true);
		i=0;
		int yearValue = 0;
		while(year.isAfter(time1)){
			yearValue = year.getYear();
			table3.put((int)totalYears-i, new JLabel("" + yearValue));
			_table3.put((int)totalYears-i, (long) Timestamp.valueOf(year.atStartOfDay()).getTime()/1000);
			year = year.minus(1, ChronoUnit.YEARS);
			System.out.print(year + " "+ i + "\n");
			i++;
		}
		yearsSlider.setLabelTable (table3);
		yearsSlider.setBounds(33, 619, 1268, 26);
		yearsSlider.setVisible(false);
		frame.getContentPane().add(yearsSlider);
		
		/////////////////////////////////////////////////////////////////////////
		
		JRadioButton rdbtnWeekly = new JRadioButton("Weekly");
		rdbtnWeekly.setBounds(33, 30, 109, 23);
		frame.getContentPane().add(rdbtnWeekly);
		
		JRadioButton rdbtnMonthly = new JRadioButton("Monthly");
		rdbtnMonthly.setBounds(33, 56, 109, 23);
		frame.getContentPane().add(rdbtnMonthly);
		
		JRadioButton rdbtnYearly = new JRadioButton("Yearly");
		rdbtnYearly.setBounds(33, 82, 109, 23);
		frame.getContentPane().add(rdbtnYearly);
		
		rdbtnWeekly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				weeksSlider.setVisible(true);
				monthsSlider.setVisible(false);
				yearsSlider.setVisible(false);
				rdbtnMonthly.setSelected(false);
				rdbtnYearly.setSelected(false);
			}
		});
		
		rdbtnMonthly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				weeksSlider.setVisible(false);
				monthsSlider.setVisible(true);
				yearsSlider.setVisible(false);
				rdbtnWeekly.setSelected(false);
				rdbtnYearly.setSelected(false);
			}
		});
		
		rdbtnYearly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				weeksSlider.setVisible(false);
				monthsSlider.setVisible(false);
				yearsSlider.setVisible(true);
				rdbtnMonthly.setSelected(false);
				rdbtnWeekly.setSelected(false);
			}
		});
		
		
		tree = new JTree(drive.buildTree());
		tree.setBounds(148, 30, 463, 566);
		scroll = new JScrollPane(tree);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(148, 30, 463, 566);
		frame.getContentPane().add(scroll);
		
		weeksSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Long timestamp = _table1.get(source.getValue());
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
	    	    DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode) model.getRoot();
	    	    nodeToRemove.removeAllChildren();
	    	    model.nodeStructureChanged(nodeToRemove);
	    	    DefaultMutableTreeNode root1 = 
	    	    		drive.buildTimedTree(timestamp,(DefaultMutableTreeNode) model.getRoot());
	    	    model.reload(root1);
				System.out.println(source.getValue());
				System.out.println(timestamp);
			}
		});
		
		monthsSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Long timestamp = _table2.get(source.getValue());
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
	    	    DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode) model.getRoot();
	    	    nodeToRemove.removeAllChildren();
	    	    model.nodeStructureChanged(nodeToRemove);
	    	    DefaultMutableTreeNode root1 = 
	    	    		drive.buildTimedTree(timestamp,(DefaultMutableTreeNode) model.getRoot());
	    	    model.reload(root1);
				System.out.println(source.getValue());
				System.out.println(timestamp);
			}
		});
		
		yearsSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Long timestamp = _table3.get(source.getValue());
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
	    	    DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode) model.getRoot();
	    	    nodeToRemove.removeAllChildren();
	    	    model.nodeStructureChanged(nodeToRemove);
	    	    DefaultMutableTreeNode root1 = 
	    	    		drive.buildTimedTree(timestamp,(DefaultMutableTreeNode) model.getRoot());
	    	    model.reload(root1);
				System.out.println(source.getValue());
				System.out.println(timestamp);
			}
		});
	}
}
