package overview;

public interface SubjectTime {
	public void registerObserverTime(ObserverTime obs);
	public void removeObserverTime(ObserverTime obs);
	public void notifyObserversTime();

}
