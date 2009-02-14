package ca.mattpayne.complainatronclient;

import java.util.HashMap;
import java.util.Map;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import ca.mattpayne.complainatronclient.data.DataAccessor;
import ca.mattpayne.complainatronclient.data.IDataAccessor;
import ca.mattpayne.complainatronclient.models.Constants;
import com.google.android.maps.MapActivity;

public abstract class AbstractMapActivity extends MapActivity
{
	protected IDataAccessor dataAccessor;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        hookupDataAccessor();
    }

	@Override
	protected boolean isRouteDisplayed()
	{
		return false;
	}
	
	protected void showMapView()
	{
		Intent i = new Intent(AbstractMapActivity.this, MapComplaints.class);
		startActivity(i);
	}

	protected void showCreateComplaint()
	{
		Intent i = new Intent(AbstractMapActivity.this, NewComplaint.class);
		startActivity(i);
	}

	protected void showPreferences()
	{
		Intent i = new Intent(AbstractMapActivity.this, Preferences.class);
		startActivity(i);
	}
	
	protected void showCustomDialog(String title, String message)
	{
		
	}
	
	public IDataAccessor getDataAccessor()
	{
		return dataAccessor;
	}
	
	protected void hookupDataAccessor()
	{
		String createUrl = getString(R.string.create_complaint_url);
		String complaintsUrl = getString(R.string.get_complaints_url);
		String apiKey = getString(R.string.api_key);
		String voteUrl = getString(R.string.vote_url);
		String categoriesUrl = getString(R.string.get_categories_url);
		String maxResults = getString(R.string.max_results);
		
		try
		{
			Map<String, String> settings = new HashMap<String, String>();
			settings.put(Constants.CATEGORIES_URL, categoriesUrl);
			settings.put(Constants.API_KEY, apiKey);
			settings.put(Constants.CREATE_URL, createUrl);
			settings.put(Constants.VOTE_URL, voteUrl);
			settings.put(Constants.COMPLAINTS_URL, complaintsUrl);
			settings.put(Constants.MAX_RESULTS, maxResults);
			dataAccessor = new DataAccessor(settings);
		} 
		catch (Exception e)
		{
			showCustomDialog("Error", "Cannot contact server\n" + e.getMessage());
			Log.e("Error", e.getMessage());
		}
	}

}
