package display.Audit;

import com.EEN.Audit.R;

import constants.GeneralFunctions;

import fsTablesClasses.Measure;
import fsTablesEnums.EquipmentType;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddCustomMeasureFragment extends Fragment{

	private View view;
	private Spinner equipmentTypeSpinner;
	private EditText measureDescriptionEditText;
	private Button submitButton;
	private ArrayAdapter<String> equipmentTypeAdapter;
	private EquipmentType eType;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.measure_add_custom, container, false);
		
		equipmentTypeSpinner = (Spinner) (view.findViewById(R.id.equipmentTypeSpinner));
		measureDescriptionEditText = (EditText) view.findViewById(R.id.measureDescription);
		
		equipmentTypeAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, EquipmentType.toStringArray());
		equipmentTypeSpinner.setAdapter(equipmentTypeAdapter);

		//submit button
		submitButton = (Button) (view.findViewById(R.id.submitButton));
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				addCustomMeasure();
			}
			
		});
		
		return view;
	}
	
	/**adds measure to database for future use
	 * 
	 */
	protected void addCustomMeasure() {
		
		Measure measure = new Measure();
		updateEquipmentType();
		
		measure.setDescription(measureDescriptionEditText.getText().toString());
		measure.setEquipmentType(this.eType);
		
		MainActivity.db.insertMeasure(measure);
		
		GeneralFunctions.DisplayToast(this.getActivity(), "Custom Measure Added");
		
		measureDescriptionEditText.setText("");
		
	}
	
	private void updateEquipmentType() {
		this.eType = EquipmentType.getEnumType((String) this.equipmentTypeSpinner.getSelectedItem());
	}

	

}
