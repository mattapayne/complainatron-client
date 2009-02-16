package ca.mattpayne.complainatronclient.data;

import java.util.HashMap;
import java.util.Map;
import ca.mattpayne.complainatronclient.R;
import ca.mattpayne.complainatronclient.models.Constants;
import android.content.Context;
import android.util.Log;

public class Constuctor implements IDataAccessorConstructor
{
	public IDataAccessor construct(Context c)
	{
		String createUrl = c.getString(R.string.create_complaint_url);
		String complaintsUrl = c.getString(R.string.get_complaints_url);
		String apiKey = c.getString(R.string.api_key);
		String voteUrl = c.getString(R.string.vote_url);
		String categoriesUrl = c.getString(R.string.get_categories_url);
		String maxResults = c.getString(R.string.max_results);
		IDataAccessor dataAccessor = null;
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
			Log.e("Error", e.getMessage());
		}
		return dataAccessor;
	}

}
