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


*/
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
/*import javax.swing.JTree;

import javax.swing.tree.TreeSelectionModel;

*/

import java.awt.event.*;




public class Build4 extends JFrame implements ActionListener, TreeSelectionListener
{
    //FileDialog is a standalone window and so can't go into a container
    //it stops application from doing anything else until it's finished
    FileDialog fileDil;
    JButton openFileDil;
    JPanel panel;
    TextArea ta;
    
    //lineStyle s are "Angled" (the default), "Horizontal", and "None".
    private static String lineStyle = "Horizontal";
    
    JTree myTree;
    
    
    public  Build4()
    {
        setUpPanel();
        pack();
        setVisible(true);
        
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
   }
    
    //----------------------------- setup methods --------------------------------------
    
    public void setUpTree()
    {
    	DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Java Series");
    	createNodes(top);
    }
    
    public void setUpPanel()
    {
        panel = new JPanel();
           
        ta = new TextArea(50,50);
        panel.add(ta);
        
        openFileDil = new JButton("open file dialog");
        openFileDil.addActionListener(this);
        panel.add(openFileDil);
        
        add(panel);
        
      
    }
    
    private void setupFileDialog()
    {
        fileDil = new FileDialog(this, "fileloader", FileDialog.LOAD);
        fileDil.getDirectory();
       fileDil.setVisible(true);
       
    }
    
    //---------------------------------- node methods---------------------------
    
    public void createNodes(DefaultMutableTreeNode top)
    {
    	DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;
        
        category = new DefaultMutableTreeNode("Books for Java Programmers");
        top.add(category);
 
        
       /* book = new DefaultMutableTreeNode(new BookInfo
            ("The Java Tutorial: A Short Course on the Basics",
            "tutorial.html"));
        category.add(book);*/
    	
    }
    
    //------------------------ listener methods -----------------------------------
    public void actionPerformed(ActionEvent e)
    {
      if(e.getSource()==openFileDil)
      {
          System.out.println("testing");
         setupFileDialog();
          ta.setText(getFileName());
      
      }
    }
    //---------------------------- required methods-------------------------------
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) 
    {
      /*  DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
 
        if (node == null) return;
 
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            BookInfo book = (BookInfo)nodeInfo;
            displayURL(book.bookURL);
            if (DEBUG) {
                System.out.print(book.bookURL + ":  \n    ");
            }
        } else {
            displayURL(helpURL); 
        }
        if (DEBUG) {
            System.out.println(nodeInfo.toString());
        }*/
    }
    
    //--------------------------------------- other methods--------------------------------------------
    
    
     public String getFileName()
    {
        return fileDil.getFile();
    }
     
     //---------------------------------additional classes-----------------------------------------------
    /* private class BookInfo 
     {
    	 public String bookName;
    	 public URL bookURL;
    	 public BookInfo(String book, String filename) 
    	 {
    		 bookName = book;
    		 bookURL = getClass().getResource(filename);
    		 if (bookURL == null) 
    		 {
    			 System.err.println("Couldn't find file: " + filename;
    		 }
    	         
    	 }
    	 public String toString() 
    	 {
    		 return bookName;
    	 }
    	 
     }*/
     
     

}
