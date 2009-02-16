package ca.mattpayne.complainatronclient;

import android.os.Bundle;
import ca.mattpayne.complainatronclient.data.Constuctor;
import ca.mattpayne.complainatronclient.data.IDataAccessor;
import com.google.android.maps.MapActivity;

public abstract class AbstractMapActivity extends MapActivity implements IViewDisplayer
{
	protected IDataAccessor dataAccessor;
	protected IViewDisplayer viewDisplayer;
	
	public AbstractMapActivity()
	{
		viewDisplayer = new ViewDisplayer(this);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        dataAccessor = new Constuctor().construct(this);
    }

	@Override
	protected boolean isRouteDisplayed()
	{
		return false;
	}
	
	public void showMapView()
	{
		viewDisplayer.showMapView();
	}
	
	public void showMainView()
	{
		viewDisplayer.showMainView();
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
