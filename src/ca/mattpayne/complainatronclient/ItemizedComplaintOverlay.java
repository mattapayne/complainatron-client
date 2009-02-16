package ca.mattpayne.complainatronclient;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ca.mattpayne.complainatronclient.data.IDataAccessor;
import ca.mattpayne.complainatronclient.models.Complaint;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class ItemizedComplaintOverlay extends ItemizedOverlay<OverlayItem>
{
	private List<Complaint> complaints;
	private Context context;
	private IDataAccessor dataAccessor;

	public ItemizedComplaintOverlay(Drawable marker, List<Complaint> complaints, 
			Context ctx, IDataAccessor dataAccessor)
	{
		super(marker);
		this.context = ctx;
		this.complaints = complaints;
		this.dataAccessor = dataAccessor;
		populate();
	}

	@Override
	protected OverlayItem createItem(int arg0)
	{
		Complaint c = this.complaints.get(arg0);
		Double lat = Double.parseDouble(c.getLatitude()) * 1E6;
		Double lng = Double.parseDouble(c.getLongitude()) * 1E6;
		GeoPoint point = new GeoPoint(lat.intValue(), lng.intValue());
		return new OverlayItem(point, "Marker", "Marker Text");
	}

	@Override
	public int size()
	{
		return this.complaints.size();
	}
	
	@Override
	protected boolean onTap(int index)
	{
		createComplaintAlert(this.complaints.get(index));
		
		return super.onTap(index);
	}
	
	private void createComplaintAlert(Complaint selectedComplaint)
	{
		final Complaint selected = selectedComplaint;
		LayoutInflater li = LayoutInflater.from(this.context);
		View detailsView = li.inflate(R.layout.complaint_details, null);
		
		Button voteFor = (Button)detailsView.findViewById(R.id.voteForButton);
		Button voteAgainst = (Button)detailsView.findViewById(R.id.voteAgainstButton);
		TextView txt = (TextView)detailsView.findViewById(R.id.complaintDetailsTextView);
		txt.setText(selected.toDetailedString());
		
		AlertDialog.Builder b = new AlertDialog.Builder(this.context);
		b.setTitle("Complaint Details");
		b.setView(detailsView);
		b.setCancelable(true);
		b.setPositiveButton(R.string.close_button_text, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
				
			}});
		
		final AlertDialog d = b.create();
		
		voteFor.setOnClickListener(new VoteClickListener(dataAccessor, d, selected, true));
		voteAgainst.setOnClickListener(new VoteClickListener(dataAccessor, d, selected, false));
		
		d.show();
	}
}
