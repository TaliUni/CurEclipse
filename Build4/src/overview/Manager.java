package overview;

public class Manager {
    
    
    
    Build4 buildMy;
    
    public Manager()
    {
        
    }
    
    public void run()
    {
       buildMy = new Build4(); 
       buildMy.createAndShowGUI();
    }
}
