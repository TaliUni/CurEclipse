package overview;

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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;




public class Browser extends JPanel implements TreeSelectionListener, Subject, MouseListener
{
	int click;
	//------------------------Observer Pattern--------------------------------------------
	private List<Observer> registeredObservers = new LinkedList<Observer>();
	
	
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
	
	//--------------------------------id to identify which browser is sending info etc--------------
	
	String id;
	
	//-------------------------curFile = the current file that focus is on-------------------------
	File curFile;


	public  Browser(String id)
    {
      
		
    	//-------------------------- layout --------------------------------------------------------
    	
    	super(new GridLayout(1,0));
    	
    //	MouseListener ml = new MouseListener();
    	
    	addMouseListener(this);
    	
    	
    	//-------------------------setup id----------------------------------------------------
    	this.id = id;
    	
  	  
    	//--------------------------setup initial path/entry point to file system----------------------
    	/*   
    	 //Works for windows, but need to have something generic.......??
    	   initialPathStr = "C:\\";
    	   
    	   //set up initial path
    	   initialPath = Paths.get(initialPathStr);
    	   */
    	   
    	//------------------------------setup initial top node ----------------------------------------
    	   
    	   initialPathStr = "very top node";
    	   
    	   //--------------------- setup Tree and nodes initially --------------------------------------
    	   
    	   
    	   
    	   	   
           //Create the nodes. Set up with just the layer below the top node showing, may change this to just have top node.
    	   // set up with initialCreateNodes taking in a path initialCreateNodes(initialPath);
    	   
    	   //setup initial nodes without taking in a path
    	   
    	   initialCreateNodes(initialPathStr);
    	   
           
           //Create a tree that allows one selection at a time.
    	   //with top as the top node
           myTree = new JTree(topNode);
          
           myTree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
    
           //Listen for when the selection changes.
           myTree.addTreeSelectionListener(this);
           
           myTree.setCellRenderer(new MyTreeRender());
           myTree.addMouseListener(this);
           
         //---------------------setup popups----------------------------------------------------------
          	
          	popup = new JPopupMenu();
          	menuItem = new JMenuItem("Mnu item");
          	//menuItem.addActionListener(this);
          	popup.add(menuItem);
          	menuItem = new JMenuItem("a diff men item");
          	//menuItem.addActionListener(this);
          	popup.add(menuItem);
          	
          	
       //   	MouseListener popupListener = new PopupListener();
          	//add in listener when decide what want to do with click on menu
         // 	myTree.addMouseListener(popupListener);
          	
       
            
      
           
           //-----------------------------------setup graphics: panels etc ----------------------------------
           
           //Create the scroll pane and add the tree to it. 
           JScrollPane treeView = new JScrollPane(myTree);
           Dimension minimumSize = new Dimension(100, 50);
           add(treeView);
    
           //Create the HTML viewing pane.
        /*   htmlPane = new JEditorPane();
           htmlPane.setEditable(false);
           
          
         
           JScrollPane htmlView = new JScrollPane(htmlPane);
           htmlView.add(treeView);
    
           //Add the scroll panes to a split pane.
        /*   JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
           splitPane.setTopComponent(treeView);
           splitPane.setBottomComponent(ta);*/
    
           
        /*   htmlView.setMinimumSize(minimumSize);
           treeView.setMinimumSize(minimumSize);
          /* splitPane.setDividerLocation(100); 
           splitPane.setPreferredSize(new Dimension(500, 300));*/
    
           //Add the split pane to this panel.
           //add(splitPane);
                 
           
   }
	
	//-----------------mouselistener--------------------------------------
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	//	popup.show(e.getComponent(), e.getX(), e.getY());
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	//	remove(popup);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		int click = e.getClickCount();
		
		if(click ==1)
		{
			System.out.println("one click brows line 223");
			//popup.show(e.getComponent(), e.getX(), e.getY());
		}
		if(click ==2)
		{
			System.out.println("brows 227 got here!!_");
			//remove(popup);
			
		}
		
		System.out.println("brows line 220 " + click);
	}
		/*
		int click;
		Timer timer = new Timer();
		TimerTask checkClickCount = new TimerTask() 
		{
			public void run()
			{
				//click = e.getClickCount();
			}
			int click = e.getClickCount();
		};
		timer.schedule(checkClickCount, new Date(System.currentTimeMillis()+5*1000));
		 click = e.getClickCount();
		if (click ==1)
		{
			
			click = e.getClickCount();
			System.out.println("brow 222 click count " + click);
		}
	}*/
		
		/*
		int click = e.getClickCount();
		try{
		Thread.sleep(500);}
		catch(InterruptedException exc){}
		System.out.println("got here");
		
		System.out.println("Brows line 297 clickcount" + click);
		int selRow = myTree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = myTree.getPathForLocation(e.getX(), e.getY());
       // if(selRow != -1) 
		if(e.getButton()==3&&click == 2) 
        {
        	//clear all selections for the tree
        	myTree.clearSelection();
        	System.out.println("Brows line 186 ");// + curFile.getName());
        //	DefaultMutableTreeNode node = (DefaultMutableTreeNode) myTree.getLastSelectedPathComponent();
        	//myTree.
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
        	Object nodeInfo = node.getUserObject();
            Path thisPath = Paths.get(nodeInfo.toString());//myTree.getLastSelectedPathComponent().toString());
            FileDisplay fileInfo = (FileDisplay)node.getUserObject();//new File(thisPath.toString());
            File testFile = fileInfo.getFile();//fileInfo.getFile();
            curFile = testFile;
            System.out.println("brows line 188 " + testFile.getName());
            System.out.println("brows line 189 " + selRow);
            System.out.println("brows line 190 " + selPath);
            myTree.setSelectionPath(selPath);
            notifyObservers();
        	
        }
         if(e.getButton()==3&&click == 1) 
        {
        	//bring up popup menu
        	//select that node, and remove selection from elsewhere
        	//clear all selections for the tree
        	myTree.clearSelection();
        	System.out.println("Brows line 203 ");// + curFile.getName());
        //	DefaultMutableTreeNode node = (DefaultMutableTreeNode) myTree.getLastSelectedPathComponent();
        	//myTree.
        	DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
        	Object nodeInfo = node.getUserObject();
            Path thisPath = Paths.get(nodeInfo.toString());//myTree.getLastSelectedPathComponent().toString());
            FileDisplay fileInfo = (FileDisplay)node.getUserObject();//new File(thisPath.toString());
            File testFile = fileInfo.getFile();//fileInfo.getFile();
            curFile = testFile;
            System.out.println("brows line 212 " + testFile.getName());
            System.out.println("brows line 213 " + selRow);
            System.out.println("brows line 215 " + selPath);
            myTree.setSelectionPath(selPath);
          //  popup.show(e.getComponent(), e.getX(), e.getY());
        //    notifyObservers();
        }*/
		
	//}//
	//class---------
/*	class PopupListener extends MouseAdapter
	{
		public void mouseEntered(MouseEvent e)
		{
			maybeShowPopup(e);
			//maybeHidePopup(e);
		}
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
		
			
		
	}*/
	
	public File getCurFile()
	{
		return curFile;
	}
    
    //----------------------CREATE AND SHOW GUI -----------------
 /*   public void createAndShowGUI()
    {
  //Create and set up the window.
    JFrame frame = new JFrame("Tali File Browser");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Add content to the window.
    frame.add(new Build5_1());
    

    //Display the window.
    frame.pack();
    frame.setVisible(true);
    }*/
	
	//-------------------------getName method-----------------------------------------
	
	public String getName()
	{
		return id;
	}
	
	//--------------------------getFile method for mouselistener-------------------------
	public File getFile()
	{
		return curFile;
	}
    
    //----------------------------- setup methods --------------------------------------
    
    //------------------------------Observer Pattern methods------------------------------
	public void registerObserver(Observer observer)
	{
		registeredObservers.add(observer);
	}
	
	public void removeObserver(Observer observer)
	{
		registeredObservers.remove(observer);
	}
	
	public void notifyObservers()
	
	{
		for(Observer obs : registeredObservers)
			obs.updateBrowser(this);
	}
    
    
    
    //---------------------------------- node methods---------------------------
    
    
    
    
    
    private void initialCreateNodes(String string)
    {
    	//creates top node with name established in constructor
 	   topNode = new DefaultMutableTreeNode(string);
    	
 	   //set up first array of files to have all the roots of the system
    	fileArray1 = File.listRoots();
    	int i;
		    
    	for(i = 0; i<fileArray1.length;i++) 
    	{
    		//add all contained files/directories to topnode
    		
    		directory = new DefaultMutableTreeNode(new FileDisplay(fileArray1[i]));
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

    	if(node == topNode)
    	{
    		node.removeAllChildren();
    		fileArray1 = File.listRoots();
    		int i;

    		for(i = 0; i<fileArray1.length;i++) 
    		{
    			//add all contained files/directories to topnode

    			directory = new DefaultMutableTreeNode(new FileDisplay(fileArray1[i]));
    			topNode.add(directory);





    		}
    	}
    	//if node is not top node then do all the stuff
    	else
    	{
    		node.removeAllChildren();

    		//  Object nodeInfo = node.getUserObject();

    		//check to see if all nodes within are files or directories
    		//if files leave
    		//if directories, add their own children to them

    		Object nodeInfo = node.getUserObject();
    		Path thisPath = Paths.get(nodeInfo.toString());//myTree.getLastSelectedPathComponent().toString());
    		FileDisplay fileInfo = (FileDisplay)node.getUserObject();//new File(thisPath.toString());
    		File testFile = fileInfo.getFile();//fileInfo.getFile();



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

