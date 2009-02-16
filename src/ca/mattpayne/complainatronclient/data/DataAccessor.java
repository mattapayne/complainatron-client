package ca.mattpayne.complainatronclient.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ca.mattpayne.complainatronclient.models.Complaint;
import ca.mattpayne.complainatronclient.models.Constants;

public class DataAccessor implements IDataAccessor
{
	private Map<String, String> settings;
	private static String[] requiredSettings = {Constants.API_KEY, Constants.CATEGORIES_URL, 
		Constants.COMPLAINTS_URL, Constants.CREATE_URL, Constants.MAX_RESULTS, Constants.VOTE_URL};
	
	//Constructor
	public DataAccessor(Map<String, String> settings) throws Exception
	{
		this.settings = settings;
		//raise an exception if the settings are null
		if(this.settings == null)
		{
			throw new Exception("Invalid settings");
		}
		//raise an exception if any of the required settings are not present
		List<String> errors = new ArrayList<String>();
		for(String key : requiredSettings)
		{
			if(!this.settings.containsKey(key))
			{
				errors.add(key);
			}
		}
		if(errors.size() > 0)
		{
			StringBuilder sb = new StringBuilder();
			for(String error : errors)
			{
				sb.append(error + ", ");
			}
			sb.deleteCharAt(sb.length() - 1);
			throw new Exception("Invalid settings. The following keys/value pairs are missing: " + sb.toString());
		}
	}
	
	//create a new complaint
	public void create(Complaint complaint) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.CATEGORY_ATTR, complaint.getCategory());
		params.put(Constants.COMPLAINT_ATTR, complaint.getComplaint());
		params.put(Constants.SUBMITTED_BY_ATTR, complaint.getSubmittedBy());
		params.put(Constants.LATITUDE_ATTR, complaint.getLatitude());
		params.put(Constants.LONGITUDE_ATTR, complaint.getLongitude());
		
		if(!post(getCreateUrl(), params))
		{
			throw new Exception("Error posting");
		}
	}

	//fetch all complaints
	public List<Complaint> fetchComplaints(int page)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.PAGE_PARAM, Integer.toString(page));
		params.put(Constants.MAX_PARAM, getMaxResults());
		String results = get(getComplaintsUrl(), params);
		if(results != null && results.length() > 0)
		{
			return parseComplaints(results);
		}
		return null;
	}

	//vote for or against a complaint
	public void vote(Complaint complaint, boolean isFor) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.VOTE_FOR_PARAM, Boolean.toString(isFor));
		params.put(Constants.ID_ATTR, complaint.getId());
		if(!post(getVoteUrl(), params))
		{
			throw new Exception("Error posting");
		}
		Integer votes = isFor ? Integer.parseInt(complaint.getVotesFor()) : Integer.parseInt(complaint.getVotesAgainst());
		votes++;
		if(isFor)
		{
			complaint.setVotesFor(votes.toString());
		}
		else
		{
			complaint.setVotesAgainst(votes.toString());
		}
	}

	//get all categories
	public List<String> fetchCategories()
	{
		String result = get(getCategoriesUrl(), null);
		if(result != null && result.length() > 0)
		{
			return parseCategories(result);
		}
		return null;
	}

	//get all complaints within a given zoom area
	public List<Complaint> fetchComplaintsForMap(int zoomLevel)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.ZOOM_PARAM, Integer.toString(zoomLevel));
		String results = get(getComplaintsUrl(), params);
		if(results != null && results.length() > 0)
		{
			return parseComplaints(results);
		}
		return null;
	}
	
	private List<Complaint> parseComplaints(String results)
	{
		List<Complaint> complaints = new ArrayList<Complaint>();
		try
		{
			JSONArray objects = new JSONArray(results);
			for(int i=0; i<objects.length(); i++)
			{
				JSONObject o = (JSONObject)objects.get(i);
				Complaint c = new Complaint();
				c.setId(o.getString(Constants.ID_ATTR));
				c.setCategory(o.getString(Constants.CATEGORY_ATTR));
				c.setComplaint(o.getString(Constants.COMPLAINT_ATTR));
				c.setDateSubmitted(o.getString(Constants.DATE_ATTR));
				c.setLatitude(o.getString(Constants.LATITUDE_ATTR));
				c.setLongitude(o.getString(Constants.LONGITUDE_ATTR));
				c.setSubmittedBy(o.getString(Constants.SUBMITTED_BY_ATTR));
				c.setVotesAgainst(o.getString(Constants.VOTE_AGAINST_ATTR));
				c.setVotesFor(o.getString(Constants.VOTE_FOR_ATTR));
				complaints.add(c);
			}
		}
		catch(JSONException je)
		{
			System.out.println(je.getMessage());
			//swallow the error
		}
		return complaints;
	}

	private boolean post(String url, Map<String, String> params)
	{		
		try
		{
			DefaultHttpClient client = new DefaultHttpClient();
			UrlEncodedFormEntity encodedParams = getEncodedParams(params);
			HttpPost method = new HttpPost(new URI(url));
			method.setHeader(Constants.API_KEY_HEADER, getApiKey());
			method.setEntity(encodedParams);
			HttpResponse response = client.execute(method);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED)
			{
				return true;
			}
		}
		catch(Exception e)
		{
			//ignore it.
		}
		
		return false;
	}
	
	private String get(String url, Map<String, String> params)
	{
		String result = null;
		try
		{
			String queryString = getEncodedQueryString(params);
			String fullUrl = url + "?" + queryString;
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet method = new HttpGet(new URI(fullUrl));
			HttpResponse response = client.execute(method);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				HttpEntity e = response.getEntity();
				if(e != null)
				{	
					result = convertStreamToString(e.getContent());
				}
			}
		}
		catch(Exception e)
		{
			
		}
		return result;
	}
	
	private String convertStreamToString(InputStream stream)
	{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		try
		{
			while((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			try
			{
				if(reader != null)
				{
					reader.close();
				}
				if(stream != null)
				{
					stream.close();
				}
			}
			catch(Exception ex)
			{
				
			}
		}
		return sb.toString();
	}
	
	private List<String> parseCategories(String result)
	{
		List<String> categories = new ArrayList<String>();
		try
		{
			JSONArray objects = new JSONArray(result);
			for(int i=0; i<objects.length(); i++)
			{
				JSONObject o = (JSONObject)objects.get(i);
				categories.add(o.getString(Constants.CATEGORY_ATTR));
			}
		}
		catch(JSONException je)
		{
			//swallow the error
		}
		return categories;
	}
	
	private String getEncodedQueryString(Map<String, String> params)
	{
		if(params == null || params.size() == 0)
		{
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> e : params.entrySet())
		{
			sb.append(e.getKey() + "=" + e.getValue());
		}
		
		return URLEncoder.encode(sb.toString());
	}
	
	private UrlEncodedFormEntity getEncodedParams(Map<String, String> params) 
		throws UnsupportedEncodingException
	{
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
	
		for(Entry<String, String> e : params.entrySet())
		{
			pairs.add(new BasicNameValuePair(e.getKey(), e.getValue()));
		}
		
		return new UrlEncodedFormEntity(pairs);
	}
	
	private String getCreateUrl()
	{
		return this.settings.get(Constants.CREATE_URL);
	}
	
	private String getComplaintsUrl()
	{
		return this.settings.get(Constants.COMPLAINTS_URL);
	}
	
	private String getVoteUrl()
	{
		return this.settings.get(Constants.VOTE_URL);
	}
	
	private String getCategoriesUrl()
	{
		return this.settings.get(Constants.CATEGORIES_URL);
	}
	
	private String getApiKey()
	{
		return this.settings.get(Constants.API_KEY);
	}
	
	private String getMaxResults()
	{
		return this.settings.get(Constants.MAX_RESULTS);
	}
}
