package overview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Menu implements ActionListener, SubjectPopMenu
{
	
	private JPopupMenu popup;
	private String curMenuItem;
	//------------------------Observer Pattern--------------------------------------------
	private List<ObserverPopMenu> registeredObserversPopMenu = new LinkedList<ObserverPopMenu>();
	
	public Menu()
	{
		
		
		
	popup = new JPopupMenu();
	
  	JMenuItem menuItem = new JMenuItem("Mnu item");
  	menuItem.addActionListener(this);
  	popup.add(menuItem);
  	menuItem = new JMenuItem("a diff men item");
  	menuItem.addActionListener(this);
  	popup.add(menuItem);
	}
	
	public String getCurMenuItem()
	{
		return curMenuItem;
	}
	public void show(MouseEvent e)
	{
		popup.show(e.getComponent(), e.getX(), e.getY());
	}
	
	public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Menu line 46 Action event detected.  " + source.getText();
        System.out.println(s);
        curMenuItem = source.getText();
        //? change to an observer of File??
       // notifyObserversFile();
        notifyObserversPopMenu();
        curMenuItem = "";
     //   return curMenuItem;
        
                   
    }

	@Override
	public void registerObserverPopMenu(ObserverPopMenu obs) {
		
		registeredObserversPopMenu.add(obs);
	}

	@Override
	public void removeObserverPopMenu(ObserverPopMenu obs) {
		registeredObserversPopMenu.remove(obs);
		
	}

	@Override
	public void notifyObserversPopMenu() {
		for(ObserverPopMenu obs : registeredObserversPopMenu)
			obs.updatePopMenu(this);
		
	}
  	
  	
	

/*	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}*/

}
