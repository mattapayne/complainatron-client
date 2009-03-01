package ca.mattpayne.complainatronclient;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class Preferences extends AbstractActivity
{	
	private Spinner numberOfComplaints;
	private CheckBox submitAnonymously;
	private Button okButton;
	private Button cancelButton;
	private IMetadataHelper helper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
        numberOfComplaints = (Spinner)findViewById(R.id.numberComplaintsSpinner);
        submitAnonymously = (CheckBox)findViewById(R.id.anonymousCheckBox);
        okButton = (Button)findViewById(R.id.okPrefsButton);
        cancelButton = (Button)findViewById(R.id.cancelPrefsButton);
        
        helper = new MetadataHelperImpl(this);
        
        populateNumberOfComplaints();
        populateFromExistingPreferences();
        
        cancelButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v)
			{
				showMainView();
			}});
        
        okButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v)
			{
				Object obj = numberOfComplaints.getSelectedItem();
				Integer number = Integer.valueOf(obj.toString());
				helper.setNumberOfComplaintsToDisplay(number.intValue());
				helper.setSubmitAnonymously(submitAnonymously.isChecked());
				showMainView();
			}
        });
    }

	private void populateNumberOfComplaints()
	{
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.number_complaints, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numberOfComplaints.setAdapter(adapter);
	}

	private void populateFromExistingPreferences()
	{
		submitAnonymously.setChecked(helper.submitAnonymously());
		String number = String.valueOf(helper.numberOfComplaintsToDisplay());
		int position = 0;
		for(int i=0; i<numberOfComplaints.getCount(); i++)
		{
			if(numberOfComplaints.getItemAtPosition(i).toString().equals(number))
			{
				position =i;
				break;
			}
		}
		numberOfComplaints.setSelection(position);
	}
}
