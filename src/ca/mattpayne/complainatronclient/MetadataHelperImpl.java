package ca.mattpayne.complainatronclient;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class MetadataHelperImpl implements IMetadataHelper
{
	private Context context;
	private LocationManager manager;
	
	public MetadataHelperImpl(Context ctx)
	{
		this.context = ctx;
	}
	
	public Location getCurrentLocation()
	{
		if(manager == null)
		{
			manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		}
		return manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public boolean submitAnonymously()
	{
		return false;
	}
	
	public int numberOfComplaintsToDisplay()
	{
		return 1;
	}
}
