package overview;

public class Manager {
    
    Build4_2 buildMy4_2;
    Build4_1 buildMy4_1;
    Build4 buildMy4;
    
    public Manager()
    {
        
    }
    
    public void run()
    {
    /*   buildMy4 = new Build4(); 
       buildMy4.createAndShowGUI();
       */
    /*	
       buildMy4_1 = new Build4_1();
       buildMy4_1.createAndShowGUI();
       */
    	
    	buildMy4_2 = new Build4_2();
    	buildMy4_2.createAndShowGUI();
    }
}
