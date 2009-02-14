package ca.mattpayne.complainatronclient.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.ParseException;

public class Complaint
{
	private String complaint;
	private String category;
	private String latitude;
	private String longitude;
	private Date dateSubmitted;
	private String votesFor;
	private String votesAgainst;
	private String submittedBy;
	private String id;
	
	private static final int MAX_LENGTH = 20;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final String NULL = "null";
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getComplaint()
	{
		return complaint;
	}
	
	public void setComplaint(String complaint)
	{
		this.complaint = complaint;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public void setCategory(String category)
	{
		this.category = category;
	}
	
	public String getLatitude()
	{
		if(this.latitude == null || this.latitude.equals("") || this.latitude.equals(NULL))
		{
			return "0.0";
		}
		return latitude;
	}
	
	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}
	
	public String getLongitude()
	{
		if(this.longitude == null || this.longitude.equals("") || this.longitude.equals(NULL))
		{
			return "0.0";
		}
		return longitude;
	}
	
	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}
	
	public Date getDateSubmitted()
	{
		return dateSubmitted;
	}
	
	public void setDateSubmitted(String dateSubmitted)
	{
		if(dateSubmitted != null && dateSubmitted.length() > 0)
		{
			try
			{
				setDateSubmitted(new Date(Date.parse(dateSubmitted)));
			}
			catch(ParseException pe)
			{
				//swallow - bad format
			}
		}
	}
	
	public void setDateSubmitted(Date dateSubmitted)
	{
		this.dateSubmitted = dateSubmitted;
	}
	
	public String getVotesFor()
	{
		return votesFor;
	}
	
	public void setVotesFor(String votesFor)
	{
		if(votesFor == null || votesFor.equals("null"))
		{
			this.votesFor = "0";
		}
		else
		{
			this.votesFor = votesFor;
		}
	}
	
	public String getVotesAgainst()
	{
		return votesAgainst;
	}
	
	public void setVotesAgainst(String votesAgainst)
	{
		if(votesAgainst == null || votesAgainst.equals("null"))
		{
			this.votesAgainst = "0";
		}
		else
		{
			this.votesAgainst = votesAgainst;
		}
	}
	
	public String getSubmittedBy()
	{
		return submittedBy;
	}
	
	public void setSubmittedBy(String submittedBy)
	{
		this.submittedBy = submittedBy;
	}
	
	public String toDetailedString()
	{
		return this.getFormattedDateSubmitted() + "\nCategory: " + this.getCategory() + "\nComplaint: " +
			this.getComplaint() + "\nSubmitted By: " + this.getSubmittedBy() + "\nVotes For: " + this.getVotesFor() +
			"\nVotes Against: " + this.getVotesAgainst();
	}
	
	public String getSummary()
	{
		String shortComplaint = this.getComplaint();
		if(shortComplaint.length() > MAX_LENGTH)
		{
			shortComplaint = shortComplaint.substring(0, MAX_LENGTH - 3);
			shortComplaint += "...";
		}
		return this.getDateSubmitted() + ": " + shortComplaint;
	}
	
	@Override
	public String toString()
	{
		return this.getSummary();
	}
	
	private String getFormattedDateSubmitted()
	{
		return dateFormat.format(this.getDateSubmitted());
	}
}
