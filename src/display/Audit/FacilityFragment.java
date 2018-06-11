package display.Audit;

import com.EEN.Audit.R;

import fsTablesClasses.Facility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * This class keeps track of the facility fragment display
 * 
 * @author Steven Wancewicz
 * @version 2014-9-9
 * 
 */

public class FacilityFragment extends Fragment {
	
	private View view;
	
	private EditText addFacilityEditText;
	private EditText updateFacilityEditText;
	private Spinner activeFacilitySpinner;
	private Button addFacilityButton;
	private Button updateFacilityButton;
	
	protected ArrayAdapter<String> facilityAdapter;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.facility_add, container, false);
		
		activeFacilitySpinner = (Spinner) view.findViewById(R.id.spinnerActiveFacility);
		updateFacilitySpinner();
		
		// define editTexts
		addFacilityEditText = (EditText) view.findViewById(R.id.addFacility);
		updateFacilityEditText = (EditText) view.findViewById(R.id.updateFacility);
				
		//add on item selected for added facility to set Text in updateFacilityEditText and set active facility
		activeFacilitySpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//set active facility, update building spinner, update updateFacility text
				setActiveFacility();			
				updateFacility();
			} //end item selected

			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {	
				//do nothing
			}
		});
		
		//make add facility button add new facility, and update activeFacilitySpinner
		addFacilityButton = (Button) view.findViewById(R.id.addFacilityButton);
		addFacilityButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//add new facility
				addFacility(getFacility());
				//update updateFacility & building list
				updateFacility();
			}
			
		});
		
		//make updateFacilityButton update facility, and update activeFacilitySpinner
		updateFacilityButton = (Button) view.findViewById(R.id.updateFacilityButton);
		updateFacilityButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateFacilityName();
				updateFacilitySpinner();
			}
			
		});
		
		this.activeFacilitySpinner.setSelection(this.facilityAdapter.getPosition(MainActivity.db.getActiveFacilityName()));
		
		return view;
	}
	
	private void setActiveFacility() {
		MainActivity.db.setActiveFacilityIndex(activeFacilitySpinner.getSelectedItemPosition());
	}
	
	private void addFacility(Facility facility) {
		MainActivity.db.addFacility(facility);
	}
	
	private Facility getFacility() {
		Facility facility = new Facility();
		
		facility.setFacilityName(addFacilityEditText.getText().toString());
		
		return facility;
	}
	
	private void updateFacility() {
		((MainActivity) this.getActivity()).updateBuildingList();
		this.updateFacilityEditText.setText(MainActivity.db.getActiveFacilityName());

	}
	
	private void updateFacilityName() {
		Facility facility = new Facility();
		facility.setFacilityName(updateFacilityEditText.getText().toString());
		MainActivity.db.updateFacility(facility);
	}
	
	private void updateFacilitySpinner() {
		this.facilityAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, MainActivity.db.getFacilityList());
		this.activeFacilitySpinner.setAdapter(facilityAdapter);
	}
	
	
}
