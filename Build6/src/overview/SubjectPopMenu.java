package overview;

public interface SubjectPopMenu {
	
	public void registerObserverPopMenu(ObserverPopMenu obs);
	public void removeObserverPopMenu(ObserverPopMenu obs);
	public void notifyObserversPopMenu();

}
