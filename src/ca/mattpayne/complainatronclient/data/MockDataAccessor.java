package ca.mattpayne.complainatronclient.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mattpayne.complainatronclient.models.Complaint;

public class MockDataAccessor implements IDataAccessor
{
	public void create(Complaint complaint) throws Exception
	{
		//do nothing
	}
	
	private List<Complaint> complaints()
	{
		List<Complaint> complaints = new ArrayList<Complaint>();
		for(int i=0; i<10; i++)
		{
			Complaint c = new Complaint();
			c.setCategory("A category");
			c.setComplaint("A Random Bitch about a whole lotta nothing, just like most people will probably end up writing");
			c.setDateSubmitted("01/31/2009");
			c.setLatitude("51.00999");
			c.setLongitude("-101.454");
			if(i % 2 == 0)
			{
				c.setSubmittedBy("Matt Payne");
			}
			c.setVotesAgainst(Integer.toString(i));
			c.setVotesFor(Integer.toString(i + 2));
			complaints.add(c);
		}
		return complaints;
	}

	public void vote(Complaint complaint, boolean isFor) throws Exception
	{
		// TODO Auto-generated method stub
	}

	public List<String> fetchCategories()
	{
		return Arrays.asList("people", "politics");
	}

	public List<Complaint> fetchComplaintsForMap(int zoomLevel)
	{
		return complaints();
	}

	public List<Complaint> fetchComplaints(int quantity)
	{
		return complaints();
	}
	
	public List<Complaint> fetchComplaints(int page, int quantity)
	{
		return complaints();
	}
}
