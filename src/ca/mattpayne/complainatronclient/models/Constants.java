package ca.mattpayne.complainatronclient.models;

public interface Constants
{
	public static final String COMPLAINTS_URL = "complaints_url";
	public static final String CATEGORIES_URL = "categories_url";
	public static final String VOTE_URL = "vote_url";
	public static final String CREATE_URL = "create_url";
	public static final String API_KEY = "api_key";
	
	//query string params
	public static final String PAGE_PARAM = "page";
	public static final String MAX_PARAM = "max";
	public static final String ZOOM_PARAM = "zoom";
	public static final String VOTE_FOR_PARAM = "vote_for";
	
	//post headers
	public static final String API_KEY_HEADER = "api-key";
	
	//complaint attributes
	public static final String ID_ATTR = "id";
	public static final String CATEGORY_ATTR = "category";
	public static final String COMPLAINT_ATTR = "complaint";
	public static final String LONGITUDE_ATTR = "longitude";
	public static final String LATITUDE_ATTR = "latitude";
	public static final String DATE_ATTR = "date_submitted";
	public static final String SUBMITTED_BY_ATTR = "submitted_by";
	public static final String VOTE_FOR_ATTR = "votes_for";
	public static final String VOTE_AGAINST_ATTR = "votes_against";
}
