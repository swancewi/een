package display.Audit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.EEN.Audit.R;

import constants.GeneralFunctions;
import display.subclasses.OnCustomEventListener;
import display.subclasses.SelectActiveEquipmentView;

public class AddMeasureTagFragment extends Fragment {
	private View view;
	private Spinner measureNameSpinner;
	private EditText measureNotesEditText;
	private Button submitButton;
	private SelectActiveEquipmentView equipmentPicker;
	@SuppressWarnings("unused")
	private final String CLASS_NAME = "AddMeasureTagFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.measure_add_tag, container, false);

        try {
            MainActivity.db.getActiveBuilding();
            MainActivity.db.getActiveEquipmentTypeAndCount();


            //define spinners
            measureNameSpinner = (Spinner) (view.findViewById(R.id.measureNameSpinner));
            measureNotesEditText = (EditText) (view.findViewById(R.id.measureNotes));
            equipmentPicker = (SelectActiveEquipmentView) view.findViewById(R.id.equipmentViewMeasure);

            //add listener
            equipmentPicker.setShowAllEquipmentTypes(false);
            equipmentPicker.setFirstLineAddEditSpinner(null);
            equipmentPicker.setActiveEquipmentTypeListener(new OnCustomEventListener() {
                @Override
                public void onEvent() {
                    updateMeasureTagSpinner();
                }
            });
            equipmentPicker.setAllowAddSimpleEquipment(true);
            equipmentPicker.setTrackActiveEquipment(true);

        //submit button
            submitButton = (Button) (view.findViewById(R.id.submitButton));
            submitButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    addMeasureTag();
                }

            });

        } catch (NullPointerException npe) {
            npe.printStackTrace();
            ((MainActivity) this.getActivity()).showAddBuilding();
        } catch (IndexOutOfBoundsException iob) {
            iob.printStackTrace();
            ((MainActivity) this.getActivity()).showAddEquipment();
        }

		return view;

	}

	protected void updateMeasureTagSpinner() {
        try {
            ArrayAdapter<String> measureNameAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, MainActivity.db.getGeneralEquipmentMeasures(equipmentPicker.getActiveEquipmentType()));
            this.measureNameSpinner.setAdapter(measureNameAdapter);
        } catch (StringIndexOutOfBoundsException sie) {
            sie.printStackTrace();
        }
	}
	
	protected void addMeasureTag() {
			MainActivity.db.addMeasure(measureNameSpinner.getSelectedItemPosition(), equipmentPicker.getActiveEquipmentType(), measureNotesEditText.getText().toString());
			GeneralFunctions.DisplayToast(getActivity(), "Measure Tag Added!");
			measureNotesEditText.setText("");
	}
}
