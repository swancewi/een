package display.Audit;

import com.EEN.Audit.R;

import constants.GeneralFunctions;
import constants.Strings;
import fsTablesClasses.Building;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This class is used for adding a building to the database
 * 
 * @author Steven Wancewicz
 * @version 2014-1-23
 * 
 */
public class AddBuildingFragment extends Fragment {

	protected Spinner buildingTypeSpinner;
	protected ArrayAdapter<String> buildingTypeAdapter;
	protected Button submitButton;
	
	protected EditText buildingName;
	protected EditText buildingNumber;
	protected EditText buildingAddress;
	protected EditText buildingCity;
	protected EditText buildingState;
	protected EditText buildingZip;
	protected EditText buildingFloors;
	protected EditText buildingSqFt;
	protected EditText buildingSchedule;
	protected EditText hvacSchedule;
	protected EditText yearBuilt;
	protected EditText numPCs;
	protected EditText roofType;
	protected EditText windowType;
	protected EditText wallType;
	protected EditText foundationType;
	
	View view;
	
	protected String BUILDING_SUBMITTED = "Building Submitted ";
	
	//private DatabaseHandler db;
	
	public AddBuildingFragment() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.add_building, container, false);
		
		try {
		
		//Building type spinner
		buildingTypeSpinner = (Spinner) view.findViewById(R.id.spinnerBuildingType);
		buildingTypeAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, Strings.buildingTypes);
		buildingTypeSpinner.setAdapter(buildingTypeAdapter);
		
		}
		
		catch (Exception e) {
			
		}
		
		//define edit texts
		buildingName = (EditText) view.findViewById(R.id.buildingName);
		buildingNumber = (EditText) view.findViewById(R.id.buildingNumber);
		buildingAddress = (EditText) view.findViewById(R.id.buildingAddress);
		buildingCity = (EditText) view.findViewById(R.id.buildingCity);
		buildingState = (EditText) view.findViewById(R.id.buildingState);
		buildingZip = (EditText) view.findViewById(R.id.buildingZip);
		buildingFloors = (EditText) view.findViewById(R.id.buildingFloors);
		buildingSqFt = (EditText) view.findViewById(R.id.buildingSqFt);
		buildingSchedule = (EditText) view.findViewById(R.id.buildingSchedule);
		hvacSchedule = (EditText) view.findViewById(R.id.hvacSchedule);
		yearBuilt = (EditText) view.findViewById(R.id.yearBuilt);
		numPCs = (EditText) view.findViewById(R.id.numPCs);
		roofType = (EditText) view.findViewById(R.id.roofType);
		windowType = (EditText) view.findViewById(R.id.windowType);
		wallType = (EditText) view.findViewById(R.id.wallType);
		foundationType = (EditText) view.findViewById(R.id.foundationType);

		//submit button
		submitButton = (Button) view.findViewById(R.id.submitBuildingButton);
		submitButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Building newBuilding = new Building();
				
				try {
				
				newBuilding.setBuildingName(buildingName.getText().toString());
				newBuilding.setBuildingNumber(buildingNumber.getText().toString());
				newBuilding.setBuildingAddress(buildingAddress.getText().toString());
				newBuilding.setBuildingCity(buildingCity.getText().toString());				
				newBuilding.setBuildingState(buildingState.getText().toString());				
				newBuilding.setBuildingZip(buildingZip.getText().toString());
				newBuilding.setBuildingFloors(GeneralFunctions.StringToInt(buildingFloors.getText().toString(),Strings.ERROR_PROCESSING_START + " # of Building Floors"));
				newBuilding.setBuildingSquareFoot(GeneralFunctions.StringToDouble(buildingSqFt.getText().toString(), Strings.ERROR_PROCESSING_START + getResources().getString(R.id.buildingSqFt)));
				newBuilding.setBuildingFunction((String) buildingTypeSpinner.getSelectedItem());
				newBuilding.setBuildingSchedule(buildingSchedule.getText().toString());	
				newBuilding.setHvacSchedule(hvacSchedule.getText().toString()); 
				newBuilding.setYearBuilt(yearBuilt.getText().toString()); 
				newBuilding.setNumPCs(numPCs.getText().toString()); 
				newBuilding.setRoofType(roofType.getText().toString()); 
				newBuilding.setWindowType(windowType.getText().toString()); 
				newBuilding.setWallType(wallType.getText().toString()); 
				newBuilding.setFoundationType(foundationType.getText().toString()); 

				long id = addBuilding(newBuilding);
				
				DisplayToast(BUILDING_SUBMITTED + id);
				} catch (Exception e) {
					e.printStackTrace();
					//Log.v("NumFloors", buildingFloors.getText().toString());
					DisplayToast("ERROR " + e.getMessage() + e.getStackTrace());
				}
			}
		});
	
		return view;
	}
	
	protected long addBuilding(Building newBuilding) throws Exception {
		long id = MainActivity.db.addBuilding(newBuilding); 
		((MainActivity) this.getActivity()).updateBuildingList();
		this.clearBuildingFields();
		return id;
	}
	
	private void DisplayToast(String unitID) {
		Toast.makeText(getActivity(), unitID, Toast.LENGTH_SHORT).show();
	}
	
	private void clearBuildingFields() {
		buildingName.setText("");
		buildingNumber.setText("");
		buildingAddress.setText("");
		buildingCity.setText("");				
		buildingState.setText("");				
		buildingZip.setText("");
		buildingFloors.setText("");
		buildingSqFt.setText("");
		buildingSchedule.setText("");
		hvacSchedule.setText("");
		yearBuilt.setText("");
		numPCs.setText("");
		roofType.setText("");
		windowType.setText("");
		wallType.setText("");
		foundationType.setText("");		

	}

}
