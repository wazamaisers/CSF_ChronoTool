import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Drive.Drive;
import Drive.Database.DatabaseSnapshotEntry;

import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.event.ChangeEvent;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import java.awt.List;
import java.awt.Color;

public class DriveChrono {

	private JFrame frame;
	private Integer last;
	private JTree tree;
	private JScrollPane scroll;
	private HashMap<Integer,Long> _table1 = new HashMap<Integer,Long>();
	private HashMap<Integer,Long> _table2 = new HashMap<Integer,Long>();
	private HashMap<Integer,Long> _table3 = new HashMap<Integer,Long>();
	private DatabaseSnapshotEntry entrySelected;
	private long timestamp1;
	private long timestamp2;

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
		
		//// ELEMENTS OF THIS WINDOW ////

		java.awt.List list = new java.awt.List();
		list.setBackground(Color.LIGHT_GRAY);
		list.setFont(new Font("Arial", Font.BOLD, 12));
		list.setBounds(675, 100, 111, 113);
		frame.getContentPane().add(list);

		java.awt.List list_values = new java.awt.List();
		list_values.setBackground(Color.LIGHT_GRAY);
		list_values.setFont(new Font("Arial", Font.PLAIN, 12));
		list_values.setBounds(787, 100, 421, 113);
		frame.getContentPane().add(list_values);

		Button button = new Button("Clear");
		button.setBounds(1155, 211, 53, 22);
		button.setVisible(false);
		frame.getContentPane().add(button);

		Button btnFileContents = new Button("File contents");
		btnFileContents.setBounds(675, 210, 111, 23);
		btnFileContents.setVisible(false);
		frame.getContentPane().add(btnFileContents);

		JLabel lblPeriod = new JLabel("Period:");
		lblPeriod.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeriod.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPeriod.setBounds(697, 572, 89, 26);
		lblPeriod.setVisible(false);
		frame.getContentPane().add(lblPeriod);

		JLabel label = new JLabel("");
		label.setBounds(857, 572, 319, 26);
		frame.getContentPane().add(label);
		
		tree = new JTree(drive.buildTree());
		tree.setBounds(148, 30, 463, 566);
		scroll = new JScrollPane(tree);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(148, 30, 463, 566);
		frame.getContentPane().add(scroll);

		List list_updates = new List();
		list_updates.setBounds(676, 289, 617, 277);
		list_updates.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(list_updates);

		Button button_1 = new Button("Update");
		button_1.setBounds(1219, 261, 70, 22);
		frame.getContentPane().add(button_1);
		
		JRadioButton rdbtnWeekly = new JRadioButton("Weekly");
		rdbtnWeekly.setBounds(33, 30, 109, 23);
		frame.getContentPane().add(rdbtnWeekly);

		JRadioButton rdbtnMonthly = new JRadioButton("Monthly");
		rdbtnMonthly.setBounds(33, 56, 109, 23);
		frame.getContentPane().add(rdbtnMonthly);

		JRadioButton rdbtnYearly = new JRadioButton("Yearly");
		rdbtnYearly.setBounds(33, 82, 109, 23);
		frame.getContentPane().add(rdbtnYearly);

		//// INITIALIZATION OF THE SLIDERS ////

		last = drive.getLastTime();
		java.util.Date time=new java.util.Date((long)last*1000);
		java.util.Date now = new java.util.Date();
		LocalDate time1 = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate now1 = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		long totalWeeks = ChronoUnit.WEEKS.between(time1, now1);
		System.out.print(totalWeeks + "\n");
		long totalMonths = ChronoUnit.MONTHS.between(time1, now1);
		System.out.print(totalMonths + "\n");
		long totalYears = ChronoUnit.YEARS.between(time1, now1);
		System.out.print(totalMonths + "\n");

		//// WEEK SLIDER CREATION ////

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

		//// MONTHS SLIDER CREATION ////

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

		//// YEARS SLIDER CREATION ////

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

		//// ACTIONS PREFORMED IN THE WINDOW ELEMENTS ////

		rdbtnWeekly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				weeksSlider.setVisible(true);
				monthsSlider.setVisible(false);
				yearsSlider.setVisible(false);
				rdbtnMonthly.setSelected(false);
				rdbtnYearly.setSelected(false);
				lblPeriod.setVisible(true);
			}
		});

		rdbtnMonthly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				weeksSlider.setVisible(false);
				monthsSlider.setVisible(true);
				yearsSlider.setVisible(false);
				rdbtnWeekly.setSelected(false);
				rdbtnYearly.setSelected(false);
				lblPeriod.setVisible(true);
			}
		});

		rdbtnYearly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				weeksSlider.setVisible(false);
				monthsSlider.setVisible(false);
				yearsSlider.setVisible(true);
				rdbtnMonthly.setSelected(false);
				rdbtnWeekly.setSelected(false);
				lblPeriod.setVisible(true);
			}
		});

		weeksSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Long timestamp = _table1.get(source.getValue());
				if(!(source.getValue()==0)){
					Long timestampLast = _table1.get(source.getValue()-1);
					java.util.Date time1=new java.util.Date(timestamp*1000);
					java.util.Date time2=new java.util.Date(timestampLast*1000);
					SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
					label.setText(dt.format(time2) + " to " + dt.format(time1));
					list_updates.removeAll();
					list_updates.setBackground(Color.LIGHT_GRAY);
					timestamp1 = timestampLast;
					timestamp2 = timestamp;
				}
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
				if(!(source.getValue()==0)){
					Long timestampLast = _table2.get(source.getValue()-1);
					java.util.Date time1=new java.util.Date(timestamp*1000);
					java.util.Date time2=new java.util.Date(timestampLast*1000);
					SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
					label.setText(dt.format(time2) + " to " + dt.format(time1));
					list_updates.removeAll();
					list_updates.setBackground(Color.LIGHT_GRAY);
					timestamp1 = timestampLast;
					timestamp2 = timestamp;
				}
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
				if(!(source.getValue()==0)){
					Long timestampLast = _table3.get(source.getValue()-1);
					java.util.Date time1=new java.util.Date(timestamp*1000);
					java.util.Date time2=new java.util.Date(timestampLast*1000);
					SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
					label.setText(dt.format(time2) + " to " + dt.format(time1));
					list_updates.removeAll();
					list_updates.setBackground(Color.LIGHT_GRAY);
					timestamp1 = timestampLast;
					timestamp2 = timestamp;
				}
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
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				list_updates.setBackground(Color.WHITE);
				HashMap<Integer,DatabaseSnapshotEntry> hash = drive.getFilesBetweenTwoDates(timestamp1, timestamp2);
				list_updates.removeAll();
				SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
				Map<Integer,DatabaseSnapshotEntry> map = new TreeMap<Integer,DatabaseSnapshotEntry>(hash);
				for(Entry<Integer, DatabaseSnapshotEntry> entry: map.entrySet()){
					java.util.Date time=new java.util.Date((long)entry.getValue().getModified()*1000);
					list_updates.add("Date: " + dt.format(time));
					list_updates.add("File Added: " + entry.getValue().getFilename() + " " + drive.getPathByDocId(entry.getValue().getDocId()));
					list_updates.add("Path:  " + drive.getPathByDocId(entry.getValue().getDocId()));
					list_updates.add("");
				}
			}
		});

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2 && !me.isConsumed()) {
					entrySelected = doMouseClicked(me, tree, drive);
					me.consume();
					btnFileContents.setVisible(true);
					button.setVisible(true);
					list.setBackground(Color.WHITE);
					list.setVisible(true);
					list.removeAll();
					list_values.setBackground(Color.WHITE);
					list_values.setVisible(true);
					list_values.removeAll();
					list.add("File Name"); 
					list_values.add(entrySelected.getFilename());
					if (!drive.getSizeCorrect(entrySelected.getSize()).startsWith("0")){
						list.add("File Size"); 
						list_values.add("" + drive.getSizeCorrect(entrySelected.getSize()));
					}
					list.add("Is Shaerd");
					if (entrySelected.getShared() == 1){
						list_values.add("Yes");
					}
					if (entrySelected.getShared() == 0){
						list_values.add("No");
					}
					list.add("Last Modified");
					java.util.Date time=new java.util.Date((long)entrySelected.getModified()*1000);
					list_values.add("" + time);
				}
			}
		});

		btnFileContents.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				String drivePath = System.getenv("HOMEPATH") + "/Google Drive";
				String path = drivePath + drive.getPathByDocId(entrySelected.getDocId()).substring(4);
				System.out.println(path);
				drive.showFileContent(path,drive);
			}
		});


		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.removeAll();
				list_values.removeAll();
				list.setBackground(Color.LIGHT_GRAY);
				list_values.setBackground(Color.LIGHT_GRAY);
				button.setVisible(false);
				btnFileContents.setVisible(false);
			}
		});
	}

	public DatabaseSnapshotEntry doMouseClicked(MouseEvent me, JTree tree, Drive drive) {
		TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
		System.out.println(tp.toString());
		System.out.println(drive.getPathFromTreePath(tp));
		String doc_id = drive.getDirectoryDocIdByPath(drive.getPathFromTreePath(tp));
		DatabaseSnapshotEntry entry = drive.getEntryByDocId(doc_id);
		return entry;
	}
}
