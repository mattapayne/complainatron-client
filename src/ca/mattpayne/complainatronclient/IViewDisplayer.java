package ca.mattpayne.complainatronclient;

public interface IViewDisplayer
{
	public void showMainView();
	public void showMapView();
	public void showCreateComplaint();
	public void showPreferences();
	public void showCustomDialog(String title, String message);
}
