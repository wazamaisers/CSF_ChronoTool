import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class test {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
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
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
	    DefaultMutableTreeNode mercury = new DefaultMutableTreeNode("Mercury");
	    root.add(mercury);
	    DefaultMutableTreeNode venus = new DefaultMutableTreeNode("Venus");
	    root.add(venus);
	    DefaultMutableTreeNode mars = new DefaultMutableTreeNode("Mars");
	    root.add(mars);
	    
	    JTree tree = new JTree(root);
	    tree.setBounds(37, 71, 191, 163);
	   
	    frame.getContentPane().add(tree);
	    
	    JButton btnNewButton = new JButton("New button");
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
	    	    DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode) model.getRoot();
	    	    nodeToRemove.removeAllChildren();
	    	    model.nodeStructureChanged(nodeToRemove);
	    	    DefaultMutableTreeNode root1 = build((DefaultMutableTreeNode) model.getRoot());
	    	    model.reload(root1);
	    	}
	    });
	    btnNewButton.setBounds(113, 275, 89, 23);
	    frame.getContentPane().add(btnNewButton);
	}
	
	public DefaultMutableTreeNode build(DefaultMutableTreeNode root1){
		DefaultMutableTreeNode root = root1;
	    DefaultMutableTreeNode mercury = new DefaultMutableTreeNode("AAA");
	    root.add(mercury);
	    DefaultMutableTreeNode venus = new DefaultMutableTreeNode("BBB");
	    root.add(venus);
	    DefaultMutableTreeNode mars = new DefaultMutableTreeNode("CCC");
	    root.add(mars);
	    
	    return root;
	}

}
