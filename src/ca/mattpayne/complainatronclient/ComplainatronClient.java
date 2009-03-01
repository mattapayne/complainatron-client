package ca.mattpayne.complainatronclient;

import java.util.List;
import ca.mattpayne.complainatronclient.models.Complaint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ComplainatronClient extends AbstractActivity
{
	private ListView complaintsListView;
	private ArrayAdapter<Complaint> arrayAdapter;
	private List<Complaint> currentComplaints;
	private Complaint selectedComplaint;
	private static final int COMPLAINT_DETAILS = 1;
	private static final int MENU_PREFS = Menu.FIRST;
	private static final int MENU_MAP = MENU_PREFS + 1;
	private static final int MENU_NEW = MENU_MAP + 1;
	private IMetadataHelper helper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        helper = new MetadataHelperImpl(this);
        currentComplaints = dataAccessor.fetchComplaints(helper.numberOfComplaintsToDisplay());
        hookupControls();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, MENU_PREFS, Menu.NONE, R.string.preferences_label);
    	menu.add(0, MENU_NEW, Menu.NONE, R.string.new_complaint_label);
    	menu.add(0, MENU_MAP, Menu.NONE, R.string.map_label);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	super.onOptionsItemSelected(item);
    	//Show other views
    	switch(item.getItemId())
    	{
    		case MENU_PREFS :
    			showPreferences();
    			break;
    		case MENU_NEW :
    			showCreateComplaint();
    			break;
    		case MENU_MAP : 
    			showMapView();
    			break;
    	}
    	return false;
    }

	@Override
    public Dialog onCreateDialog(int id)
    {
    	switch(id)
    	{
    		case COMPLAINT_DETAILS:
    			return createComplaintAlert();
    	}
    	
    	return null;
    }
    
    @Override
    public void onPrepareDialog(int id, Dialog d)
    {
    	switch(id)
    	{
    		case(COMPLAINT_DETAILS):
    			setUpComplaintDetailsDialog(d);
    			break;
    	}
    }
    
    private void setUpComplaintDetailsDialog(Dialog d)
    {
		TextView txt = (TextView)d.findViewById(R.id.complaintDetailsTextView);
		txt.setText(selectedComplaint.toDetailedString());
    }

	private Dialog createComplaintAlert()
	{
		LayoutInflater li = LayoutInflater.from(this);
		View detailsView = li.inflate(R.layout.complaint_details, null);
		
		Button voteFor = (Button)detailsView.findViewById(R.id.voteForButton);
		Button voteAgainst = (Button)detailsView.findViewById(R.id.voteAgainstButton);
		
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Complaint Details");
		b.setView(detailsView);
		b.setCancelable(true);
		b.setPositiveButton(R.string.close_button_text, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
				
			}});
		
		final AlertDialog d = b.create();
		voteFor.setOnClickListener(new VoteClickListener(dataAccessor, d, selectedComplaint, true));
		voteAgainst.setOnClickListener(new VoteClickListener(dataAccessor, d, selectedComplaint, false));
		return d;
	}

	private void hookupControls()
	{
		complaintsListView = (ListView)findViewById(R.id.complaintsListView);
		int layoutId = android.R.layout.simple_list_item_1;
		arrayAdapter = new ArrayAdapter<Complaint>(this, layoutId, currentComplaints);
		complaintsListView.setAdapter(arrayAdapter);
		complaintsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> av, View v, int index,
					long other)
			{
				selectedComplaint = currentComplaints.get(index);
				showDialog(COMPLAINT_DETAILS);
			}});
	}
}