package overview;

public interface SubjectFile {
	
	public void registerObserverFile(ObserverFile obs);
	public void removeObserverFile(ObserverFile obs);
	public void notifyObserversFile();

}
