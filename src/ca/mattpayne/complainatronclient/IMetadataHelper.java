package ca.mattpayne.complainatronclient;

import android.location.Location;

public interface IMetadataHelper
{
	public Location getCurrentLocation();
	public boolean submitAnonymously();
	public int numberOfComplaintsToDisplay();
}
