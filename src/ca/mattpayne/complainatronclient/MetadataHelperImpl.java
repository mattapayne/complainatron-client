package ca.mattpayne.complainatronclient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
		return getSharedPreferences().getBoolean(SUBMIT_ANON, false);
	}
	
	public int numberOfComplaintsToDisplay()
	{
		return getSharedPreferences().getInt(NO_COMPLAINTS, 5);
	}
	
	public void setSubmitAnonymously(boolean anon)
	{
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putBoolean(SUBMIT_ANON, anon);
		editor.commit();
	}
	
	public void setNumberOfComplaintsToDisplay(int number)
	{
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putInt(NO_COMPLAINTS, number);
		editor.commit();
	}
	
	private SharedPreferences getSharedPreferences()
	{
		return context.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);
	}
}
