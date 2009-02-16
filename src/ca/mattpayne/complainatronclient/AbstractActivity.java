package ca.mattpayne.complainatronclient;

import ca.mattpayne.complainatronclient.data.Constuctor;
import ca.mattpayne.complainatronclient.data.IDataAccessor;
import android.app.Activity;
import android.os.Bundle;

public class AbstractActivity extends Activity implements IViewDisplayer
{
	protected IDataAccessor dataAccessor;
	protected IViewDisplayer viewDisplayer;
	
	public AbstractActivity()
	{
		viewDisplayer = new ViewDisplayer(this);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState); 
        dataAccessor = new Constuctor().construct(this);
    }
    
	public void showMainView()
	{
		viewDisplayer.showMainView();
	}
	
	public void showMapView()
	{
		viewDisplayer.showMapView();
	}

	public void showCreateComplaint()
	{
		viewDisplayer.showCreateComplaint();
	}

	public void showPreferences()
	{
		viewDisplayer.showPreferences();
	}
	
	public void showCustomDialog(String title, String message)
	{
		viewDisplayer.showCustomDialog(title, message);
	}
}
