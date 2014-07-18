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

import java.awt.event.*;




public class Build4 extends JFrame implements ActionListener
{
    //FileDialog is a standalone window and so can't go into a container
    //it stops application from doing anything else until it's finished
    FileDialog fileDil;
    JButton openFileDil;
    JPanel panel;
    TextArea ta;
    
    public  Build4()
    {
        
        
        setUpPanel();
        pack();
        setVisible(true);
        
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
      
     
        
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
    
    public void actionPerformed(ActionEvent e)
      {
        if(e.getSource()==openFileDil)
        {
            System.out.println("testing");
           setupFileDialog();
            ta.setText(getFileName());
        
        }
      }
    
    private void setupFileDialog()
    {
        fileDil = new FileDialog(this, "fileloader", FileDialog.LOAD);
        fileDil.getDirectory();
       fileDil.setVisible(true);
       
    }
    
     public String getFileName()
    {
        return fileDil.getFile();
    }

}
