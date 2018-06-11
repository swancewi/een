package display.Audit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.EEN.Audit.R;

import constants.GeneralFunctions;
import constants.Strings;
import display.subclasses.AddBuildingItemView;
import display.subclasses.OnCustomEventListener;
import display.subclasses.SelectActiveEquipmentView;
import fsTablesClasses.EnvelopeInformation;
import fsTablesClasses.EquipmentInformation;
import fsTablesClasses.LightingInformation;
import fsTablesClasses.UtilityMeterInformation;
import fsTablesClasses.WaterInformation;
import fsTablesEnums.EquipmentType;

/**This class allows a user to add a new equipment 
 * 
 * @author Steven Wancewicz
 *
 */
public class AddEquipmentFragment extends Fragment {
	
	private final String CLASS_NAME = "AddEquipmentFragment";
	
	protected SelectActiveEquipmentView equipmentPicker;

	protected Button updateButton;
	protected Button deleteButton;
	protected Button submitButton;
	
	protected int offset; //keeps track of number of extra rows in specific equipment index

	private AddBuildingItemView buildingItemView;

	public AddEquipmentFragment() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		offset = 1;
		
		View view = inflater.inflate(R.layout.add_building_item, container, false);
		buildingItemView = (AddBuildingItemView) view.findViewById(R.id.add_building_item_view);

		equipmentPicker = (SelectActiveEquipmentView) view.findViewById(R.id.selectActEquipment);
		equipmentPicker.setActiveEquipmentListener(new OnCustomEventListener() {
			@Override
			public void onEvent() {
				switch(getActiveEquipmentType().getBuildingItemType()) {
					case LIGHT:
						GeneralFunctions.DisplayToast(getActivity(),"Lighting selected");
						LightingInformation existingLighting = MainActivity.db.getActiveLight();
                        buildingItemView.setupExistingLighting(existingLighting);
						break;
					case EQUIPMENT:
						GeneralFunctions.DisplayToast(getActivity(),"Equipment selected");
						EquipmentInformation existingEquipment = MainActivity.db.getActiveEquipment();
                        buildingItemView.setupExistingEquipment(existingEquipment);
						break;
					case ENVELOPE:
						GeneralFunctions.DisplayToast(getActivity(), "Envelope selected");
						EnvelopeInformation existingEnvelope = MainActivity.db.getActiveEnvelope();
                        buildingItemView.setupExistingEnvelope(existingEnvelope);
						break;
					case WATER_FIXTURE:
						GeneralFunctions.DisplayToast(getActivity(), "Water selected");
						WaterInformation existingWater = MainActivity.db.getActiveWater();
                        buildingItemView.setupExistingWater(existingWater);
						break;
					case UTILITYMETER:
						GeneralFunctions.DisplayToast(getActivity(), "Water selected");
                        UtilityMeterInformation um = MainActivity.db.getActiveUtilityMeter();
                        buildingItemView.setupExistingMeter(um);
						break;
				}
			}
		});

		equipmentPicker.setActiveEquipmentTypeListener(new OnCustomEventListener() {
			@Override
			public void onEvent() {
				//if (setEquipmentType())
				setupViews();
				equipmentPicker.updateAddEditSpinner();

			}
		});

		equipmentPicker.setFirstLineAddEditSpinner(Strings.NEW_EQUIPMENT);
		//adds dropdown for equipment type
		//updateEquipmentSpinner();

		
		//setup submit button
		this.submitButton = (Button) view.findViewById(R.id.submitButton);
		this.submitButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				EquipmentType eType;
				
				try {
					eType =  getActiveEquipmentType();
                    switch (eType.getBuildingItemType()) {
                        case LIGHT:
                            MainActivity.db.AddLightingEquipment(buildingItemView.getLightingInput(eType));
                            buildingItemView.clearLightingWindow();
                            break;
                        case EQUIPMENT:
                            MainActivity.db.addEquipment(buildingItemView.getEquipmentInput(eType));
                            buildingItemView.clearEquipmentWindow();
                            break;
                        case ENVELOPE:
                            MainActivity.db.AddEnvelopeEquipment(buildingItemView.getEnvelopeInput(eType));
                            buildingItemView.clearEnvelopeWindow();
                            break;
                        case WATER_FIXTURE:
                            MainActivity.db.AddWaterEquipment(buildingItemView.getWaterInput());
                            buildingItemView.clearWaterWindow();
                            break;
						case UTILITYMETER:
							MainActivity.db.AddUtilityMeter(buildingItemView.getMeter());
                            buildingItemView.clearMeterWindow();
							break;
                    }

					GeneralFunctions.DisplayToast(getActivity(), getActiveEquipmentType() + " Added");
					equipmentPicker.updateAddEditSpinner();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		this.updateButton = (Button) view.findViewById(R.id.updateButton);
		this.updateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EquipmentType eType;
				
				eType =  getActiveEquipmentType();
                switch (eType.getBuildingItemType()) {
                    case LIGHT:
                        MainActivity.db.updateLighting(buildingItemView.getLightingInput(eType));
                        break;
                    case EQUIPMENT:
                        MainActivity.db.updateEquipment(buildingItemView.getEquipmentInput(eType));
                        break;
                    case ENVELOPE:
                        MainActivity.db.updateEnvelope(buildingItemView.getEnvelopeInput(eType));
                        break;
                    case WATER_FIXTURE:
                        MainActivity.db.updateWater(buildingItemView.getWaterInput());
                        break;
                    case UTILITYMETER:
                        MainActivity.db.updateUtilityMeter(buildingItemView.getMeter());
                        break;
                }
				GeneralFunctions.DisplayToast(getActivity(), getActiveEquipmentType() + " Updated");
				equipmentPicker.zeroOutSelection();
				equipmentPicker.updateAddEditSpinner();
			}
			
		});
		
		this.deleteButton = (Button) view.findViewById(R.id.deleteButton);
		this.deleteButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Are you sure you want to delete this equipment?");
				builder.setTitle("Delete?");
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
                            EquipmentType eType;
                            eType =  getActiveEquipmentType();

                            switch (eType.getBuildingItemType()) {
                                case LIGHT:
                                    MainActivity.db.deleteLight();
                                    buildingItemView.clearLightingWindow();
                                    break;
                                case EQUIPMENT:
                                    MainActivity.db.deleteEquipment();
                                    buildingItemView.clearEquipmentWindow();
                                    break;
                                case ENVELOPE:
                                    MainActivity.db.deleteEnvelope();
                                    buildingItemView.clearEnvelopeWindow();
                                    break;
                                case WATER_FIXTURE:
                                    MainActivity.db.deleteWater();
                                    buildingItemView.clearWaterWindow();
                                    break;
                                case UTILITYMETER:
                                    MainActivity.db.deleteUtilityMeter();
                                    buildingItemView.clearMeterWindow();
							}

						   GeneralFunctions.DisplayToast(getActivity(), eType.toString() + " Deleted");
						   equipmentPicker.updateEquipmentSpinner();
                            //equipmentPicker.zeroOutSelection();
						    //equipmentPicker.updateAddEditSpinner();
			           }
	            });

		        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GeneralFunctions.DisplayToast(getActivity(), getActiveEquipmentType() + " Not Deleted");
                    }
                });
				
                AlertDialog dialog = builder.create();
                dialog.show();
	        }
		});

		this.setButtonVisibility();

		return view;
	}

	protected void setButtonVisibility() {
		this.updateButton.setVisibility(View.GONE);
		this.deleteButton.setVisibility(View.GONE);
		this.submitButton.setVisibility(View.VISIBLE);
		//Log.v(this.toString(), "regular setButtonVisibility");
	}

	private EquipmentType getActiveEquipmentType() {
		return equipmentPicker.getActiveEquipmentType();
	}
	
	protected void setupViews() {
		try {
			GeneralFunctions.DisplayToast(getActivity(), equipmentPicker.getActiveEquipmentString() + " selected.");
			buildingItemView.setupView(equipmentPicker.getActiveEquipmentType());
		} catch (NullPointerException npe) {

		} catch (IllegalArgumentException iae) {
			changeEquipmentView();
		}
	}

	protected void changeEquipmentView() {
		//do nothing
	}
}
