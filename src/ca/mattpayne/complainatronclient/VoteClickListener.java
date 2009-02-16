package ca.mattpayne.complainatronclient;

import ca.mattpayne.complainatronclient.data.IDataAccessor;
import ca.mattpayne.complainatronclient.models.Complaint;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class VoteClickListener implements OnClickListener
{
	private boolean isFor;
	private IDataAccessor dataAccessor;
	private AlertDialog dialog;
	private Complaint complaint;
	
	public VoteClickListener(IDataAccessor dataAccessor, AlertDialog dialog, 
			Complaint complaint, boolean isFor)
	{
		this.dataAccessor = dataAccessor;
		this.isFor = isFor;
		this.dialog = dialog;
		this.complaint = complaint;
	}
	
	public void onClick(View v)
	{
		try
		{
			dataAccessor.vote(complaint, isFor);
			dialog.dismiss();
		}
		catch(Exception e)
		{
			Log.e("Error", e.getMessage());
		}
	}
}
