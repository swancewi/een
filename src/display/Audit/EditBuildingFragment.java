package display.Audit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fsTablesClasses.Building;

/**
 * This class updates an existing building
 * If no building exists, it will add a new building
 * 
 * @author Steven Wancewicz
 * @version 2014-7-30
 * 
 */
public class EditBuildingFragment extends AddBuildingFragment {
	
	String BUILDING_SUBMITTED = "Building Updated ";
    public static final String TAG = "EditBuildingFragment";
	boolean updateBuilding=true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		submitButton.setText("Update");
		populateBuildingFields();
		
		return view;
	}

	private void populateBuildingFields() {
		try {
			Building building = MainActivity.db.getActiveBuilding();
			
			buildingName.setText(building.getBuildingName());
			buildingNumber.setText(building.getBuildingNumber());
			buildingAddress.setText(building.getBuildingAddress());
			buildingCity.setText(building.getBuildingCity());
			buildingState.setText(building.getBuildingState());
			buildingZip.setText(building.getBuildingZip());
			buildingFloors.setText(""+building.getBuildingFloors());
			buildingSqFt.setText(""+building.getBuildingSquareFoot());
			buildingSchedule.setText(building.getBuildingSchedule());
			hvacSchedule.setText(building.getHvacSchedule());
			yearBuilt.setText(building.getYearBuilt());
			numPCs.setText(building.getNumPCs());
			roofType.setText(building.getRoofType());
			windowType.setText(building.getWindowType());
			wallType.setText(building.getWallType());
			foundationType.setText(building.getFoundationType());
			
			int position = buildingTypeAdapter.getPosition(building.getBuildingFunction());
			buildingTypeSpinner.setSelection(position);
			updateBuilding = true;
		} catch (NullPointerException npe) {
			updateBuilding = false;
		}

        //Log.v(TAG, "Populate Building Fields, updateBuilding " + updateBuilding);
	}

	@Override
	protected long addBuilding(Building newBuilding) throws Exception {
		if (updateBuilding) {
            //Log.v(TAG, "adding Building");
			int id = MainActivity.db.updateBuilding(newBuilding);
			((MainActivity) this.getActivity()).updateBuildingList();
			return id;
		}
		else
			return MainActivity.db.addBuilding(newBuilding);
	}
}
