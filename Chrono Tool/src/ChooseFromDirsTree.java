import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.TreePath;

import Drive.Drive;

public class ChooseFromDirsTree {

	private JFrame frame;
	private String path = null;

	/**
	 * Launch the application.
	 */
	public static void main(Drive drive) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseFromDirsTree window = new ChooseFromDirsTree(drive);
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
	public ChooseFromDirsTree(Drive drive) {
		initialize(drive);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Drive drive) {
		frame = new JFrame();
		frame.setBounds(100, 100, 257, 300);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		//Creation of the tree of directories
		JTree tree = new JTree(drive.buildTreeOfDirs());
		tree.setBounds(0, 0, 241, 261);
		frame.getContentPane().add(tree);

		//Creation and adition of the scroll to the tree
		JScrollPane scroll = new JScrollPane(tree);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 0, 241, 261);
		frame.getContentPane().add(scroll);
		
		//Action done when an element of the tree is pressed twice
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2 && !me.isConsumed()) {
					path = doMouseClicked(me, tree, drive);
					me.consume();
					frame.setVisible(false);
				}
			}
		});
	}
	
	//Function that returns the path of a dir that has been cliced twice in the tree
	public String doMouseClicked(MouseEvent me, JTree tree, Drive drive) {
		TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
		System.out.println(tp.toString());
		return drive.getPathFromTreePath(tp);
	}
	
	public String getPath(){
		return path;
	}
}
