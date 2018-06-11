package display.Audit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import constants.Strings;

/**
 * This class will display a fragment that allows a user to edit an already existing equipment
 * 
 * @author Steven Wancewicz
 * @version 2014-8-14
 * 
 */

public class EditEquipmentFragment extends AddEquipmentFragment {

	//private int activeIndex = 0; //used for active equipment
	private static final String TAG = "EditEquipmentFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		//this.getActiveEquipmentProperties();
		//this.updateEquipmentSpinner();
		//this.updateAddEditSpinner();
		//Log.v(TAG, "finished super call");
		equipmentPicker.allowEditEquipment();
		//Log.v(TAG, "allowed edit equipment");
		equipmentPicker.setTrackActiveEquipment(true);
		//Log.v(TAG, "tracking active equipment");
		equipmentPicker.setFirstLineAddEditSpinner(null);
		//Log.v(TAG, "set first line to null");
		try {
			equipmentPicker.setShowAllEquipmentTypes(false);
		} catch (IndexOutOfBoundsException iout) {
			((MainActivity) this.getActivity()).showAddEquipment();
		}
		//Log.v(TAG,"set equipment spinner display");

		return view;
	}
	
	//override add equipment on item selected listener function (this is where delete / update button are made visible)
	@Override
	protected void setButtonVisibility() {
		this.updateButton.setVisibility(View.VISIBLE);
		this.deleteButton.setVisibility(View.VISIBLE);
		this.submitButton.setVisibility(View.GONE);
		//Log.v(TAG, "overridenButton Visibility");
		//Log.v(TAG, "Visibility int: " + View.VISIBLE + "," + this.updateButton.getVisibility());
		
	}
	
	//update addeditcopyspinner adapter to not show "new" when new equipment is added
	//protected void updateAddEditSpinner() {
		//Log.v("EditEquipmentFragment/updateAddEditSpinner", "this.equipmentType: " + this.equipmentType.toString());
		/*try {
			//ArrayAdapter<String> addEditAdapter = new ArrayAdapter<String> (this.getActivity(), android.R.layout.simple_dropdown_item_1line, MainActivity.db.getEquipmentSummaryList(this.equipmentType));
			//addEditCopySpinner.setAdapter(addEditAdapter);
			//addEditCopySpinner.setSelection(MainActivity.db.getActiveEquipmentIndexByType(this.equipmentType));
		} catch (NullPointerException npe) {
		}*/
	//}
	
	/*private void getActiveEquipmentProperties() {
		try {
			this.equipmentType = MainActivity.db.getActiveEquipmentType();
			//this.activeIndex = MainActivity.db.getActiveEquipmentIndexByType(this.equipmentType)+1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	@Override
	protected void changeEquipmentView() {

		//Log.v(this.toString(), equipmentSpinner.getSelectedItem().toString());
		String equipmentType = equipmentPicker.getActiveEquipmentString();

		if (equipmentType.equals(Strings.NO_EQUIPMENT) || (equipmentType.equals(Strings.NO_BUILDING))) {

			//message saying no equipment
			String message;
			String title;

			//if (equipmentType.equals(Strings.NO_EQUIPMENT)) {
				message = "No equipment added for this building.";
				title = "No equipment";
			/*} else {
				message = "No building added for this facility";
				title = "No building";
			}*/
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(message);
			builder.setTitle(title);
			builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
			AlertDialog dialog = builder.create();
			//Log.v(this.toString(), title);
			dialog.show();	

			//change fragment to add equipment
			//if (equipmentType.equals(Strings.NO_EQUIPMENT))
				((MainActivity) this.getActivity()).showAddEquipment();
			/*else
				((MainActivity) this.getActivity()).showAddBuilding();*/
		}	

	}

}
