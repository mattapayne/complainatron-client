package ca.mattpayne.complainatronclient;

import android.location.Location;

public interface IMetadataHelper
{
	public static final String PREFS = "complainatronClientPrefs";
	public static final String NO_COMPLAINTS = "number_complaints";
	public static final String SUBMIT_ANON = "submit_anonymously";
	
	public Location getCurrentLocation();
	public boolean submitAnonymously();
	public int numberOfComplaintsToDisplay();
	public void setSubmitAnonymously(boolean anon);
	public void setNumberOfComplaintsToDisplay(int number);
}
