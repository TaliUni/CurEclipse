package overview;

import java.util.ArrayList;

public class Favourites {
	
	private static Favourites instance = new Favourites();
	
	ArrayList<String> menuList;
	
	private Favourites()
	{
		menuList = new ArrayList<String>();
	}
	
	public static Favourites getInstance()
	{
		return instance;
	}
	
	public void addList(String s)
	{
		menuList.add(s);
	}
	public void removeList(String s)
	{
		menuList.remove(s);
	}
	public ArrayList<String> getList()
	{
		return menuList;
	}
	
}
