package ca.mattpayne.complainatronclient.data;

import java.util.List;

import ca.mattpayne.complainatronclient.models.Complaint;

public interface IDataAccessor
{
	List<Complaint> fetchComplaints(int quantity);
	List<Complaint> fetchComplaints(int page, int quantity);
	void create(Complaint complaint) throws Exception;
	List<Complaint> fetchComplaintsForMap(int zoomLevel);
	void vote(Complaint complaint, boolean isFor) throws Exception;
	List<String> fetchCategories();
}
