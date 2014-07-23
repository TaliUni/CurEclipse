package overview;


/*
Build4
Creating file browser
Spec: create a file browser
Terms: FileVisitor FileTree, walking the filetree, visitor pattern
File Browser GUI
FileBro
JTree / ModelTree
youtube - java swing tutorial JTree
oracle documentation How to use trees

Tree nodes:
setup the very top node (here = "top")
then add nodes to it
can add nodes to those nodes
I will need to run through filesystem and count the number of directories/files etc
Then add that number of different nodes
Then add the directories/files etc to them auto

Listing a Directory's Contents

You can list all the contents of a directory by using the newDirectoryStream(Path) method. This method returns an object that implements the DirectoryStream interface. The class that implements the DirectoryStream interface also implements Iterable, so you can iterate through the directory stream, reading all of the objects. This approach scales well to very large directories.

Remember: The returned DirectoryStream is a stream. If you are not using a try-with-resources statement, don't forget to close the stream in the finally block. The try-with-resources statement takes care of this for you.
The following code snippet shows how to print the contents of a directory:

Path dir = ...;
try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
    for (Path file: stream) {
        System.out.println(file.getFileName());
    }
} catch (IOException | DirectoryIteratorException x) {
    // IOException can never be thrown by the iteration.
    // In this snippet, it can only be thrown by newDirectoryStream.
    System.err.println(x);
}


*/
import javax.swing.*;

import java.awt.*;
import java.io.*;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;




import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;




public class Build4 extends JPanel implements TreeSelectionListener
{
   
    JPanel panel;
    TextArea ta;
    //will be changed to link to working directory, but with name
   String topNodeName = "Top Node Name";
   JEditorPane htmlPane; 
   DefaultMutableTreeNode top = null;
   DefaultMutableTreeNode directory = null;
   DefaultMutableTreeNode underDir = null;
   ArrayList<String> listDir;
    
    //lineStyle s are "Angled" (the default), "Horizontal", and "None".
    private static String lineStyle = "Horizontal";
    
    JTree myTree;
    
    
    public  Build4()
    {
      
    	
    	
    	   super(new GridLayout(1,0));
    	   
    	   listDir = new ArrayList<String>();
    	   
    	   listDir.add("c");
    	   listDir.add("doc");
    	   listDir.add("uni");
    	   listDir.add("mine");
    	   listDir.add("those");
    	   listDir.add("any");
    	 //  listDir.add("last");
    	   
           //Create the nodes.
    	   top = new DefaultMutableTreeNode(topNodeName);
    	   directory = new DefaultMutableTreeNode("testDir");
          	underDir = new DefaultMutableTreeNode("testUnderDir");
           createNodes(top);
           
           
           //Create a tree that allows one selection at a time.
           myTree = new JTree(top);
           myTree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
    
           //Listen for when the selection changes.
           myTree.addTreeSelectionListener(this);
           
           //Create the scroll pane and add the tree to it. 
           JScrollPane treeView = new JScrollPane(myTree);
    
           //Create the HTML viewing pane.
           htmlPane = new JEditorPane();
           htmlPane.setEditable(false);
         
           JScrollPane htmlView = new JScrollPane(htmlPane);
    
           //Add the scroll panes to a split pane.
           JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
           splitPane.setTopComponent(treeView);
           splitPane.setBottomComponent(htmlView);
    
           Dimension minimumSize = new Dimension(100, 50);
           htmlView.setMinimumSize(minimumSize);
           treeView.setMinimumSize(minimumSize);
           splitPane.setDividerLocation(100); 
           splitPane.setPreferredSize(new Dimension(500, 300));
    
           //Add the split pane to this panel.
           add(splitPane);
           
           
   }
    
    //----------------------CREATE AND SHOW GUI -----------------
    public void createAndShowGUI()
    {
  //Create and set up the window.
    JFrame frame = new JFrame("TreeDemo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Add content to the window.
    frame.add(new Build4());

    //Display the window.
    frame.pack();
    frame.setVisible(true);
    }
    
    //----------------------------- setup methods --------------------------------------
    
    
    
    //---------------------------------- node methods---------------------------
    
    public void createNodes(DefaultMutableTreeNode top)
    {
    	createNodeString();
    }
    
    
    
    private void createNodeString()//List listDir)
    {
    		int i;
    		directory = new DefaultMutableTreeNode(listDir.get(0));
    		top.add(directory);
	    	//needs to be list as needs to be in order added (got from file system)
    		//index point 0 becomes directory and that directory gets added to "top" node
    		//so start for loop at 1
	    	for(i = 1; i<listDir.size()-1;i=i+2) //while (listDir!=null)
	    	{
	    		underDir = new DefaultMutableTreeNode(listDir.get(i));//listDir next
	        	directory.add(underDir);
	    		directory = new DefaultMutableTreeNode(listDir.get(i+1));//listDir next
	    		underDir.add(directory);
	    	}
	    	System.out.println("i = " + i);
	    	if(i != listDir.size())
	    	{
	    		System.out.println("got here");
	    		underDir = new DefaultMutableTreeNode(listDir.get(i));
	    		directory.add(underDir);
	    	}
    	
    }
    
    
    
    //------------------------ listener methods -----------------------------------
   /* For any buttons leave as will be putting buttons in later
    * public void actionPerformed(ActionEvent e)
    {
      if(e.getSource()==openFileDil)
      {
          System.out.println("testing");
         setupFileDialog();
          ta.setText(getFileName());
      
      }
    }*/
    //---------------------------- required methods-------------------------------
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) 
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) myTree.getLastSelectedPathComponent();
 
        if (node == null) return;
 
        Object nodeInfo = node.getUserObject();
      
    }
    
   
     
     //---------------------------------additional classes-----------------------------------------------
     
     
     

}
