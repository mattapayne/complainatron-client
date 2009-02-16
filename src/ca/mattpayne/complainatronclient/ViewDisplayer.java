package ca.mattpayne.complainatronclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ViewDisplayer implements IViewDisplayer
{
	private Context context;
	
	public ViewDisplayer(Context ctx)
	{
		this.context = ctx;
	}
	
	public void showMainView()
	{
		start(new Intent(context, ComplainatronClient.class));
	}
	
	public void showMapView()
	{
		start(new Intent(context, MapComplaints.class));
	}

	public void showCreateComplaint()
	{
		start(new Intent(context, NewComplaint.class));
	}

	public void showPreferences()
	{
		start(new Intent(context, Preferences.class));
	}
	
	public void showCustomDialog(String title, String message)
	{
		LayoutInflater li = LayoutInflater.from(context);
		View detailsView = li.inflate(R.layout.error, null);
		TextView tv = (TextView)detailsView.findViewById(R.id.lblError);
		
		AlertDialog.Builder b = new AlertDialog.Builder(context);
		b.setTitle(title);
		tv.setText(message);
		b.setView(detailsView);
		b.setCancelable(true);
		b.setPositiveButton(R.string.close_button_text, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
				
			}});
		b.show();
	}
	
	private void start(Intent i)
	{
		this.context.startActivity(i);
	}

}
