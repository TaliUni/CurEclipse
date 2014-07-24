package overview;


/*
Build4
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
import java.io.*;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;




public class Build4_1 extends JPanel implements TreeSelectionListener
{

	//------------------------------------GUI graphics--------------------------------------
	JPanel panel;
	JEditorPane htmlPane;
	JTextArea ta;
	
	//lineStyle s are "Angled" (the default), "Horizontal", and "None".
	private static String lineStyle = "Horizontal";

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


	public  Build4_1()
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
    
    //----------------------CREATE AND SHOW GUI -----------------
    public void createAndShowGUI()
    {
  //Create and set up the window.
    JFrame frame = new JFrame("Tali File Browser");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Add content to the window.
    frame.add(new Build4_1());
    

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
    	 * checks to see if any of them are directories, and if they contain anything
    	 * if they do, then adds those files to those directories (so that the node shows as a
    	 * node, rather than a leaf)
    	 * SEE if better way of doing this, can I require a node to be displayed as a node
    	 * without having to add children to it.  This would be more efficient, as currently
    	 * when actually go into a node, I delete this info, before restarting.
    	 * 
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
    		
        	if(fileArray1[i].isDirectory()&&fileArray1[i].listFiles()!=null)
        	{
        		//add contained directories/files to directory, simply so that directory node shows as node
        		//rather than leaf
        		//NOTE can try playing about with renderer to set up so if a node is a directory renders as node rather than leaf
        		//http://stackoverflow.com/questions/13746880/making-a-jtree-leaf-appear-as-an-empty-directory-and-not-a-file
        		fileArray2 = fileArray1[i].listFiles();
	        	for(int j=0; j<fileArray2.length;j++)
	        	{
	        		underDir = new DefaultMutableTreeNode(new FileDisplay(fileArray2[j]));
	            	directory.add(underDir);
	        	}
        	}
        	
        	
    		
    	}
    	
    	
    	
    	
    	
    }
    //-------------------------------------new class for node, so that it contains all info of file, but only displays shortname---------
/*    //if simply added a File, then the full path would show (as File.toString() contains the full path. If I used File.getName() then
    //node would not recognise the node as a File, but as a string, so this overcomes that.
    private class FileDisplay
    {
    	private String shortName;
    	private String path;
    	private File file;
    	
    	public FileDisplay(File file)
    	{
    		this.file = file;
    		shortName = file.getName();
    		path = file.getPath();
    	}
    	public FileDisplay(String string)
    	{
    		this.file = new File(string);
    		shortName = file.getName();
    		path = file.getPath();
    	}
    	
    	public File getFile()
    	{
    		return file;
    	}
    	
    	public String getShortName()
    	{
    		return shortName;
    	}
    	public String getPath()
    	{
    		return path;
    	}
    	public String toString()
    	{
    		return shortName;
    	}
    }*/
    
    
    
    
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
 	   node.removeAllChildren();
        
        if(testFile.isDirectory()==true)
        {
        	for(int i = 0; i<testFile.listFiles().length; i++)
        	{
        		
	        	directory = new DefaultMutableTreeNode(new FileDisplay (testFile.listFiles()[i]));
	        	node.add(directory);
	        	//node.add(fileInfo);
	        	//check fileInfo.listFiles()[i] == directory
	        	//if it is
	        	//get it's list
	        	//add each on list to directory
	        	//directory.add(......)
	        	
	        	if(testFile.listFiles()[i].isDirectory()==true)
	        	{
	        		for(int j = 0; j<testFile.listFiles()[i].listFiles().length; j++)
	        		{
	        			
	        			underDir = new DefaultMutableTreeNode(testFile.listFiles()[i].listFiles()[j]);
	        			directory.add(underDir);
	        		}
	        	}
        	}
        }
        }
        
    }
    
   
     
     //---------------------------------additional classes-----------------------------------------------
     
     
     

}