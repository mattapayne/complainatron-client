package ca.mattpayne.complainatronclient;

import ca.mattpayne.complainatronclient.models.Complaint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

//TODO - do not have text in the areas where people type. it's annoying to delete it before typing
public class NewComplaint extends AbstractActivity
{
	private Spinner categoriesSpinner;
	private EditText complaintText;
	private EditText userNameText;
	private EditText categoryText;
	private ArrayAdapter<String> sAdapter;
	private IMetadataHelper helper;
	
	public NewComplaint()
	{
		helper = new MetadataHelperImpl(this);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_complaint);
        hookupControls();
    }

	private void hookupControls()
	{
		categoriesSpinner = (Spinner)findViewById(R.id.categorySpinner);
		sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
				dataAccessor.fetchCategories());
		categoriesSpinner.setAdapter(sAdapter);
		Button ok = (Button)findViewById(R.id.okButton);
		Button cancel = (Button)findViewById(R.id.cancelButton);
		complaintText = (EditText)findViewById(R.id.complaintText);
		categoryText = (EditText)findViewById(R.id.complaintCategoryText);
		
		userNameText = (EditText)findViewById(R.id.complaintUserNameText);
		TextView userNameLabel = (TextView)findViewById(R.id.complaintUserNameLabel);
		
		if(helper.submitAnonymously())
		{
			userNameLabel.setVisibility(View.GONE);
			userNameText.setVisibility(View.GONE);
		}
		
		ok.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0)
			{
				createComplaint();
				
			}});
		
		cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0)
			{
				showMainView();
			}});
	}
	
	private void createComplaint()
	{
		Location loc = helper.getCurrentLocation();
		String lat = String.valueOf(loc.getLatitude());
		String lng = String.valueOf(loc.getLongitude());
		Complaint c = new Complaint();
		c.setCategory(getCategory());
		c.setComplaint(complaintText.getText().toString());
		
		if(!helper.submitAnonymously())
		{
			c.setSubmittedBy(userNameText.getText().toString()); 
		}
		else
		{
			c.setSubmittedBy("Anonymous");
		}
		c.setLatitude(lat);
		c.setLongitude(lng); 
		
		try
		{
			dataAccessor.create(c);
			showMainView();
		}
		catch(Exception e)
		{
			Log.e("Error", e.getMessage());
			showCustomDialog("Error", "An error occurred while attempting to create the complaint!\n" + e.getMessage());
		}
	}
	
	private String getCategory()
	{
		String category = "";
		if(categoryText.getText() != null && categoryText.getText().length() > 0)
		{
			category = categoryText.getText().toString();
		}
		else if(categoriesSpinner.getSelectedItem() != null)
		{
			category = categoriesSpinner.getSelectedItem().toString();
		}
		return category;
	}
}
