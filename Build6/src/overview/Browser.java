package overview;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
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




public class Browser extends JPanel implements TreeSelectionListener, SubjectFile, MouseListener, ActionListener, ObserverPopMenu
{
	//---------------------MenuItem----------------------------------------------------
	private String curMenuItem;
	private String toCommandLine;
	//--------------------MouseEvent variables-------------------------------------------
	private int collectClick;
	private MouseEvent currMseEvent;
	
	//------------------------Observer Pattern--------------------------------------------
	private List<ObserverFile> registeredObserversFile = new LinkedList<ObserverFile>();
	
	
	
	//------------------------------------GUI graphics--------------------------------------
	JPanel panel;
	JEditorPane htmlPane;
	
	
	//lineStyle s are "Angled" (the default), "Horizontal", and "None".
	private static String lineStyle = "Horizontal";
	
	

	//----------------------------------tree/nodes--------------------------------------------

	JTree myTree;
	
	DefaultMutableTreeNode topNode = null;
	DefaultMutableTreeNode directory = null;
	DefaultMutableTreeNode underDir = null;
	
//	File topFile;
	
	//array to contain directory paths
	File[] fileArray1;
	File[] fileArray2;
	
	//----------------------------- file system paths and entry point------------------------------
	
//	Path initialPath;
	String initialPathStr;
	
	//--------------------------------id to identify which browser is sending info (observer pattern) etc--------------
	
	String id;
	
	//-------------------------curFile = the current file that focus is on-------------------------
	File curFile;

	//pass in an id, for every browser created, to help identify which browser is sending info (observer pattern)
	public  Browser(String id)
    {
      
		
    	//-------------------------- layout --------------------------------------------------------
    	
    	super(new GridLayout(1,0));
    	
    	//--------------------------add listners-------------------------------------------------
    	
    	addMouseListener(this);
    	
    	
    	//-------------------------setup id----------------------------------------------------
    	this.id = id;
    	
  	  
    	
    	   
    	  
    	   
    	   //--------------------- setup Tree and nodes initially --------------------------------------
    	   
    	   
    	   
    	   	   
           //Create the nodes. Set up with just the layer below the top node showing, may change this to just have top node.
    	   // set up with initialCreateNodes taking in a path initialCreateNodes(initialPath);
    	   
    	   //setup initial nodes without taking in a path
    	 initialPathStr = "very top node";
    	   initialCreateNodes(initialPathStr);
    	   
           
           //Create a tree that allows one selection at a time.
    	   //with top as the top node
           myTree = new JTree(topNode);
          
           myTree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
    
           //Listen for when the selection changes.
           myTree.addTreeSelectionListener(this);
           
           myTree.setCellRenderer(new MyTreeRender());
           myTree.addMouseListener(this);
           
         
            
      
           
           //-----------------------------------setup graphics: panels etc ----------------------------------
           
           //Create the scroll pane and add the tree to it. 
           JScrollPane treeView = new JScrollPane(myTree);
           Dimension minimumSize = new Dimension(100, 50);
           add(treeView);
     
    
        //Modify, add in a scroll pane, and add tree to this.
                 
           
   }
	//-------------------------END of constructor-----------------------------
	
	//-----------------MouseListener override Methods--------------------------------------
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e){} 
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	
	//-----------------------MouseReleased Method, plus methods needed within----------------------
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
//##TO DO modify this from 3 to pick up machine indepent right button
/*##TO DO check whether the pickup from OS dble click interval works for linux?  If not find code for linux.
 * 
 * Mouse Right Single/Double click
 * Wanted:  Hover -> menu popup <- currently can't get to work well, so moved to
 * 
 * Single Click -> brings up popup menu; user can either go on to double click (has to move mouse, because popup retains mouse focus
 * and will not allow any more clicks until either 1. mse mvd or 2. menu clicked) OR user can click on menu item -> menu item plus file
 * dropped to command line
 * 
 * Double Click -> enters file direct into command line.
 * 
 * Problems:  MouseEvent - records single AND double click (whilst the mouseReleased() entered each time a click occurs).  If second (or more) clicks
 * occur within time frame of OS set double click time -> records second click as a double click
 * 
 * Popup grabs focus (on ALL mouseEvent methods), so if popup comes up on single click, and wish double click to overrule this.  There is no way to get the mouseReleased()
 * to listen for the second click, after popup has come up.
 * 
 * Solution:  Use a Timer.
 * 				Put Timer within mouseReleased()
 * 				Only start it on a clickCount of 1 click (otherwise will be started for every click)
 * 				Time interval = OS grabbed interval for double clicks OR +/- 10ms ????
 * 				Can grab this for windows need to check whether can grab for Linux.
 * 
 * When Timer finished:
 * 
 * Check number of clicks.
 * if clicks =1 -> action singleclick action (bring up popup etc)
 * if clicks >1 -> action doubleclick action (ie drop file to command line)
 * 
 * 
 * QUERY - can I use this to determine the speed of one/two clicks from OS if can't retrieve for linux..............??????????????
 * Could I set up an initial (or button determine) event where user can co-ordinate OS mouse click time with programme
 * 
 * 
 * mouseReleased(MouseEvent e)
 * 

 */
		final MouseEvent currMseEvent = e;
		//final int collectClick = e.getClickCount();
		collectClick = e.getClickCount();

		//get Operating System determined interval for double click - Works windows, check Linux
		int mouseClickSpeed = (int)Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");

		//only want timer to start if click ==1
		if(e.getButton() == 3&&e.getClickCount()==1)
		{
				Timer timer = new Timer();
				TimerTask	onTimeFinish = new TimerTask() 
				{
					public void run()
					{
						//Even though have requirement of collectClick being ==1 to come into here
						//because the programme checks after the end time of run, the situation may have
						//changed, which is what looking out for
						if(collectClick == 1)
						{
							rightSingleClick(currMseEvent);
						}
						if(collectClick>1)
						{
							rightDoubleClick(currMseEvent);
						}
					}
				};
				timer.schedule(onTimeFinish, new Date(System.currentTimeMillis()+mouseClickSpeed+10));
		}
	}
		
		
	public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Action event detected.  " + source.getText();
        System.out.println(s);
        curMenuItem = source.getText();
        //? change to an observer of File??
        notifyObserversFile();
        curMenuItem = "";
                   
    }


	public void callPopup(MouseEvent e)
	{/*
		JPopupMenu popup = new JPopupMenu();
      	JMenuItem menuItem = new JMenuItem("Mnu item");
      	menuItem.addActionListener(this);
      	popup.add(menuItem);
      	menuItem = new JMenuItem("a diff men item");
      	menuItem.addActionListener(this);
      	popup.add(menuItem);
      	
      	popup.show(e.getComponent(), e.getX(), e.getY());
      	return popup;*/
		Menu men = new Menu();
		men.registerObserverPopMenu(this);
		men.show(e);
	}
	
	public void highlightSelectedNode(MouseEvent e)
	{
		
		int selRow = myTree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = myTree.getPathForLocation(e.getX(), e.getY());
		myTree.clearSelection();
    	
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
    	Object nodeInfo = node.getUserObject();
        Path thisPath = Paths.get(nodeInfo.toString());//myTree.getLastSelectedPathComponent().toString());
        FileDisplay fileInfo = (FileDisplay)node.getUserObject();//new File(thisPath.toString());
        File testFile = fileInfo.getFile();//fileInfo.getFile();
        curFile = testFile;
        
        myTree.setSelectionPath(selPath);
	}
	
	public void rightDoubleClick(MouseEvent e)
	{
		
		int selRow = myTree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = myTree.getPathForLocation(e.getX(), e.getY());
       // if(selRow != -1)
      //clear all selections for the tree
    	myTree.clearSelection();
    	
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
    	Object nodeInfo = node.getUserObject();
        Path thisPath = Paths.get(nodeInfo.toString());
        FileDisplay fileInfo = (FileDisplay)node.getUserObject();
        File testFile = fileInfo.getFile();//fileInfo.getFile();
        curFile = testFile;
        
        myTree.setSelectionPath(selPath);
        notifyObserversFile();
	}
	
	public void rightSingleClick(MouseEvent e)
	{
		//1. call a method in Browser to create and show popup as per code below
		//2 call a method in Browser to get the File from given node
		//2.1 highlight that node
		//2.2 make selection that node
		//2.3 notifyObserversFile()
		
		//do I need a new popup within method, or can it be global to class?
		//change msevnt to getMouseEvent() method which needs created in Browser
		callPopup(e);//brows.msevnt);
		highlightSelectedNode(e);//(brows.msevnt);
		//need to retrive file info
		TreePath selPath = myTree.getPathForLocation(e.getX(), e.getY());
	       // if(selRow != -1)
	      //clear all selections for the tree
	    	myTree.clearSelection();
	    	
	    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
	    	Object nodeInfo = node.getUserObject();
	        Path thisPath = Paths.get(nodeInfo.toString());
	        FileDisplay fileInfo = (FileDisplay)node.getUserObject();
	        File testFile = fileInfo.getFile();//fileInfo.getFile();
	        curFile = testFile;
		//ta.setText("single event");
		//return pop;
	}
	//--------------------------End of MouseReleased and relevant methods-------------------------
	
	public File getCurFile()
	{
		return curFile;
	}
	public String getMenuItem()
	{
		return curMenuItem;
	}
    
    
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
    
    
    
    //------------------------------Observer Pattern methods------------------------------
	public void registerObserverFile(ObserverFile observer)
	{
		registeredObserversFile.add(observer);
	}
	
	public void removeObserverFile(ObserverFile observer)
	{
		registeredObserversFile.remove(observer);
	}
	
	public void notifyObserversFile()
	
	{
		for(ObserverFile obs : registeredObserversFile)
			obs.updateBrowserFile(this);
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
	@Override
	public void updatePopMenu(Menu menu) {
		System.out.println("Browser line 494");
		curMenuItem = menu.getCurMenuItem();
		notifyObserversFile();
		
		
		
	}

	
    
   
     
     //---------------------------------additional classes-----------------------------------------------
     
     
     

}

