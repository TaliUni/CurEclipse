package overview;


/*
Build4_3
Already done:


Creating file browser
Spec: create a file browser

create a tree that opens up to show files and directories.
DONE 23rd July 2014

Gummphy notes to keep-----------------------------------------------------
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
        
    }
} catch (IOException | DirectoryIteratorException x) {
    // IOException can never be thrown by the iteration.
    // In this snippet, it can only be thrown by newDirectoryStream.
    System.err.println(x);
}


*/
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;




public class Build4_3 extends JPanel implements TreeSelectionListener
{

	//------------------------------------GUI graphics--------------------------------------
	JPanel panel;
	JEditorPane htmlPane;
	JTextArea ta;
	
	//lineStyle s are "Angled" (the default), "Horizontal", and "None".
	private static String lineStyle = "Horizontal";
	
	//--------------------------------- actions: ie popup's----------------------------------
	
	JPopupMenu popup;
	JMenuItem menuItem;

	//----------------------------------tree/nodes--------------------------------------------

	JTree myTree;
	
	DefaultMutableTreeNode topNode = null;
	DefaultMutableTreeNode directory = null;
	DefaultMutableTreeNode underDir = null;
	
	File topFile;
	
	//array to contain directory paths
	File[] fileArray1;
	File[] fileArray2;
	
	//----------------------------- file system paths and entry point------------------------------
	
	Path initialPath;
	String initialPathStr;


	public  Build4_3()
    {
      
    	//-------------------------- layout --------------------------------------------------------
    	
    	super(new GridLayout(1,0));
    	
    	
    	
    	
    	
    	
    	
  	  
    	//--------------------------setup initial path/entry point to file system----------------------
    	   
    	 //Works for windows, but need to have something generic.......??
    	   initialPathStr = "C:\\";
    	   
    	   //set up initial path
    	   initialPath = Paths.get(initialPathStr);
    	   
    	   //--------------------- setup Tree and nodes initially --------------------------------------
    	   
    	   
    	   
    	   	   
           //Create the nodes. Set up with just the layer below the top node showing, may change this to just have top node.
    	   initialCreateNodes(initialPath);
           
           //Create a tree that allows one selection at a time.
    	   //with top as the top node
           myTree = new JTree(topNode);
          
           myTree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
    
           //Listen for when the selection changes.
           myTree.addTreeSelectionListener(this);
           
           myTree.setCellRenderer(new MyTreeRender());
           
         //---------------------setup popups----------------------------------------------------------
          	
          	popup = new JPopupMenu();
          	menuItem = new JMenuItem("Mnu item");
          	//menuItem.addActionListener(this);
          	popup.add(menuItem);
          	menuItem = new JMenuItem("a diff men item");
          	//menuItem.addActionListener(this);
          	popup.add(menuItem);
          	
          	MouseListener popupListener = new PopupListener();
         // 	myTree.addMouseListener(popupListener);
          	
          	MouseListener ml = new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                	System.out.println(e.getButton() + " line 164");
                    int selRow = myTree.getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = myTree.getPathForLocation(e.getX(), e.getY());
                    if(selRow != -1) {
                        if(e.getClickCount() == 3) {
                         //   mySingleClick(selRow, selPath);
                        }
                        
                        else if(e.getButton()==1){//e.getClickCount() == 2) {
                          //  myDoubleClick(selRow, selPath);
                      //  {
                        	ta.setText(selPath.toString());
                        }
                    }
                }
            };
            
            myTree.addMouseListener(ml);
            
      //    	directory.addMouseListener(popupListener);
        //  	underDir.addMouseListener(popupListener);
          	//topNode.addMouseListener(popupListener);
          	
          	//menuBar.addMouseListener(popupListener);
           
           //-----------------------------------setup graphics: panels etc ----------------------------------
           
           //Create the scroll pane and add the tree to it. 
           JScrollPane treeView = new JScrollPane(myTree);
    
           //Create the HTML viewing pane.
           htmlPane = new JEditorPane();
           htmlPane.setEditable(false);
           
           ta = new JTextArea(50,50);
           ta.setText("testing");
         
           JScrollPane htmlView = new JScrollPane(htmlPane);
    
           //Add the scroll panes to a split pane.
           JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
           splitPane.setTopComponent(treeView);
           splitPane.setBottomComponent(ta);
    
           Dimension minimumSize = new Dimension(100, 50);
           htmlView.setMinimumSize(minimumSize);
           treeView.setMinimumSize(minimumSize);
           splitPane.setDividerLocation(100); 
           splitPane.setPreferredSize(new Dimension(500, 300));
    
           //Add the split pane to this panel.
           add(splitPane);
           
           
   }
	//class---------
	class PopupListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			maybeShowPopup(e);
		}
		public void mouseReleased (MouseEvent e)
		{
			maybeShowPopup(e);
		}
		public void maybeShowPopup(MouseEvent e)
		{
			if (e.isPopupTrigger())
			{
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
			
		
	}
    
    //----------------------CREATE AND SHOW GUI -----------------
    public void createAndShowGUI()
    {
  //Create and set up the window.
    JFrame frame = new JFrame("Tali File Browser");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Add content to the window.
    frame.add(new Build4_3());
    

    //Display the window.
    frame.pack();
    frame.setVisible(true);
    }
    
    //----------------------------- setup methods --------------------------------------
    
    
    
    
    
    //---------------------------------- node methods---------------------------
    
    
    
    
    
    private void initialCreateNodes(Path path)
    {
    	/*Runs through the directories and files contained within the top node
    	 * adds then to the tree
    	 */
    	//make the "topFile" a File from "myPath"
 	   topFile = new File(path.toString());
 	   //make node "topNode" equal to a FileDisplay of File "topFile"
 	   topNode = new DefaultMutableTreeNode(new FileDisplay(topFile));
    	
//why printing twice?? 	   
 	   System.out.println(topFile + " line 202");
    	
    	fileArray1 = topFile.listFiles();
    	int i;
		    
    	for(i = 0; i<fileArray1.length;i++) 
    	{
    		//add all contained files/directories to topnode
    		
    		directory = new DefaultMutableTreeNode(new FileDisplay (fileArray1[i]));
        	topNode.add(directory);
    		
       
        	
        	
    		
    	}
    	
    	
    	
    	
    	
    }
    
    
    
    
    //------------------------ listener methods -----------------------------------
   /* For any buttons leave as will be putting buttons in later
    * public void actionPerformed(ActionEvent e)
    {
      if(e.getSource()==openFileDil)
      {
          
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
        
        
        	
        
 
      //  Object nodeInfo = node.getUserObject();
        
        //check to see if all nodes within are files or directories
        //if files leave
        //if directories, add their own children to them
       
        Object nodeInfo = node.getUserObject();
        Path thisPath = Paths.get(nodeInfo.toString());//myTree.getLastSelectedPathComponent().toString());
        FileDisplay fileInfo = (FileDisplay)node.getUserObject();//new File(thisPath.toString());
        File testFile = fileInfo.getFile();//fileInfo.getFile();
        /*
         * File f = (File)node;
         */
        if(testFile.isDirectory()==false)
        {
        	ta.setText(testFile.getName());
        }
 	   
        else
        {
 	
        
        if(testFile.isDirectory()==true)
        {
        	for(int i = 0; i<testFile.listFiles().length; i++)
        	{
        		
	        	directory = new DefaultMutableTreeNode(new FileDisplay (testFile.listFiles()[i]));
	        	node.add(directory);
	  
        	}
        }
        }
        
    }
    
   
     
     //---------------------------------additional classes-----------------------------------------------
     
     
     

}
