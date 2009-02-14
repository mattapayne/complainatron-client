package ca.mattpayne.complainatronclient;

import ca.mattpayne.complainatronclient.models.Complaint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewComplaint extends AbstractActivity
{
	private Spinner categoriesSpinner;
	private EditText complaintText;
	private ArrayAdapter<String> sAdapter;
	
	public NewComplaint()
	{
		
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
		Complaint c = new Complaint();
		c.setCategory(this.categoriesSpinner.getSelectedItem().toString());
		c.setComplaint(complaintText.getText().toString());
		c.setSubmittedBy("Android App");
		
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
}
