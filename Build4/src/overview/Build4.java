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
    //FileDialog is a standalone window and so can't go into a container
    //it stops application from doing anything else until it's finished
    FileDialog fileDil;
    JButton openFileDil;
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
       /* setUpPanel();
        setUpTree(topNodeName);
        pack();
        setVisible(true);
        
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);*/
    	
    	
    	   super(new GridLayout(1,0));
    	   
    	   listDir = new ArrayList<String>();
    	   
    	   listDir.add("c");
    	   listDir.add("doc");
    	   listDir.add("uni");
    	   listDir.add("mine");
    	   listDir.add("those");
    	   listDir.add("any");
    	   listDir.add("last");
    	   
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
         //  initHelp();
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
    
    /*set up the tree with "top" as top node
     * then createNodes(top) method pulls in the nodes below the top node
     * top node will be working directory, so need to pass in working directory as parameter
     */
  
    public void setUpNodes(String topNodeName)
    {
    	DefaultMutableTreeNode top = new DefaultMutableTreeNode(topNodeName);
    	createNodes(top);
    }
    
   /* public void setUpPanel()
    {
        panel = new JPanel();
           
        ta = new TextArea(50,50);
        panel.add(ta);
        
        openFileDil = new JButton("open file dialog");
        openFileDil.addActionListener(this);
        panel.add(openFileDil);
        
        add(panel);
        
      
    }*/
    
  /*  private void setupFileDialog()
    {
        fileDil = new FileDialog(this, "fileloader", FileDialog.LOAD);
        fileDil.getDirectory();
       fileDil.setVisible(true);
       
    }*/
    
    //---------------------------------- node methods---------------------------
    
    public void createNodes(DefaultMutableTreeNode top)
    {
    	
    	/*directory = new DefaultMutableTreeNode("c");
    	top.add(directory);
    	underDir = new DefaultMutableTreeNode("doc");
    	directory.add(underDir);
    	directory = new DefaultMutableTreeNode("uni");
    	underDir.add(directory);
    	underDir = new DefaultMutableTreeNode("test");
    	directory.add(underDir);*/
    	
    	
        
       createNodeString();
        
        /*directory = new DefaultMutableTreeNode("Books for Java Programmers");
        top.add(directory);*/
      /*  
        createDir("Books for java");
        createUnderDir("underbooks");
        createUnderDir("other");
        
        
        createDir("testing");
        createUnderDir("furthertest");
        
       /* book = new DefaultMutableTreeNode(new BookInfo
            ("The Java Tutorial: A Short Course on the Basics",
            "tutorial.html"));
        category.add(book);*/
    	
    }
    
    private void createDir(String input)
    {
    	directory = new DefaultMutableTreeNode(input);
    	top.add(directory);
    }
    
    private void createUnderDir(String input)
    {
    	underDir = new DefaultMutableTreeNode(input);
    	directory.add(underDir);
    }
    
    private void createNodeString()//List listDir)
    {
    	
    		directory = new DefaultMutableTreeNode(listDir.get(0));
    		top.add(directory);
	    	//needs to be list as needs to be in order added (got from file system)
    		//index point 0 becomes directory and that directory gets added to "top" node
    		//so start for loop at 1
	    	for(int i = 1; i<listDir.size()-1;i=i+2) //while (listDir!=null)
	    	{
	    		underDir = new DefaultMutableTreeNode(listDir.get(i));//listDir next
	        	directory.add(underDir);
	    		directory = new DefaultMutableTreeNode(listDir.get(i+1));//listDir next
	    		underDir.add(directory);
	        	
	        	
	        	
	    		
	    	}
    	
    }
    
    
    
    //------------------------ listener methods -----------------------------------
   /* public void actionPerformed(ActionEvent e)
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
      /*  if (node.isLeaf()) {
            BookInfo book = (BookInfo)nodeInfo;
            displayURL(book.bookURL);
            if (DEBUG) {
                System.out.print(book.bookURL + ":  \n    ");
            }
        } else {
            displayURL(helpURL); 
        }*/
     /*   if (DEBUG) {
            System.out.println(nodeInfo.toString());
        }*/
    }
    
    //--------------------------------------- other methods--------------------------------------------
    
    
     public String getFileName()
    {
        return fileDil.getFile();
    }
     
     //---------------------------------additional classes-----------------------------------------------
     private class BookInfo 
     {
    	 public String bookName;
    	 public URL bookURL;
    	 public BookInfo(String book, String filename) 
    	 {
    		 bookName = book;
    		// bookURL = filename;
    	 }
    	 public String toString() 
    	 {
    		 return bookName;
    	 }
    	 
     }
     
     

}
