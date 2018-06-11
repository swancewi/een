package display.subclasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.EEN.Audit.R;

import java.io.IOException;

import constants.GeneralFunctions;
import constants.Strings;
import display.Audit.MainActivity;
import fsTablesEnums.EquipmentType;

/**
 * Created by Steven Wance on 8/27/2015.
 * View to keep track of active equipment view
 */
public class SelectActiveEquipmentView extends LinearLayout {

    public static final String TAG = "SelectActEquipmentView";
    private OnCustomEventListener activeEquipmentListener;
    private OnCustomEventListener activeEquipmentTypeListener;
    private Spinner equipmentSpinner;
    private Spinner addEditCopySpinner;
    private ImageButton editEquipmentButton;
    private ImageButton addSimpleEquipmentButton;
    private Context mContext;

    private int offset = 0; //number of "extra" pieces in active equipment display
    private boolean allEquipmentInList = true; //shows all equipment vs those that have equipment
    private boolean trackActiveEquipment = false;
    private String firstLineAddEditSpinner = null; //firstLineDisplayed in addEditSpinner
    private boolean allowFakeAssets; //things like drawings, building, etc. that aren't assets but we still want to photograph

    public SelectActiveEquipmentView(Context context) {
        super(context);
        init(context);
    }

    public SelectActiveEquipmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelectActiveEquipmentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setActiveEquipmentListener(OnCustomEventListener event) { activeEquipmentListener = event; }

    public void setActiveEquipmentTypeListener(OnCustomEventListener event) { activeEquipmentTypeListener = event;    }

    public void setFirstLineAddEditSpinner(String firstLine) {
        this.firstLineAddEditSpinner = firstLine;
        if(firstLine == null)
            this.offset = 0;
        else
            this.offset = 1;

        updateAddEditSpinner();

    }

    public void setFirstLineAddEditSpinner(String first, boolean allowGenerics) {
        this.setFirstLineAddEditSpinner(first);
    }

    public void setAllowFakeAssets(boolean allowFakeAssets) {
        this.allowFakeAssets = allowFakeAssets;
        this.updateEquipmentSpinner();
    }
    public void setTrackActiveEquipment(boolean toTrack) {
        trackActiveEquipment = toTrack;
        updateAddEditSpinner();
    }

    /**Determines
     *
     * @param showOnlyAddedEquipment, boolean
     */
    public void setShowAllEquipmentTypes(boolean showOnlyAddedEquipment) {
        allEquipmentInList = showOnlyAddedEquipment;
        updateEquipmentSpinner();
    }

    public void setAllowAddSimpleEquipment(boolean allowAddSimple) {

        if(allowAddSimple)
            addSimpleEquipmentButton.setVisibility(View.VISIBLE);
        else
            addSimpleEquipmentButton.setVisibility(View.INVISIBLE);
        setupViews();
    }

    private void init(Context context) {
        View.inflate(context, R.layout.add_equipment_top, this);

        this.mContext = context;
        this.equipmentSpinner = (Spinner) (findViewById(R.id.spinnerEquipment));
        try {
            updateEquipmentSpinner();
        } catch (IndexOutOfBoundsException ioube) {
            //do nothing.
        }

        equipmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                //Log.v(this.toString(), "Equipment Selected");
                setupViews();
                updateAddEditSpinner();

            } //end item selected
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //do nothing
            }
        });

        editEquipmentButton = (ImageButton) findViewById(R.id.editEquipmentType);
        editEquipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditEquipmentPopup();
            }
        });
        editEquipmentButton.setVisibility(View.INVISIBLE);

        addSimpleEquipmentButton = (ImageButton) findViewById(R.id.addSimpleEquipment);
        addSimpleEquipmentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSimpleEquipmentPopup();
            }
        });
        addSimpleEquipmentButton.setVisibility(View.INVISIBLE);

        //add regular equipment info edit texts
        addEditCopySpinner = (Spinner) findViewById(R.id.spinnerAddEditCopy);

        //set up equipment list spinner w/ existing equipment in the building
        addEditCopySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int index = arg0.getSelectedItemPosition();
                //Log.v(this.toString(), "Offset: " + offset);
                if(index >= offset) {
                        setActiveEquipment(index);
                        setupEquipmentViews();
                } else {
                    MainActivity.db.setGeneralBuildingItem(getActiveEquipmentType());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //do nothing
            }
        });
        if(isInEditMode()) {
            updateAddEditSpinner();
        }
    }

    public void updateAddEditSpinner() {
        ArrayAdapter<String> addEditAdapter;
        try {
            EquipmentType equipmentType = getActiveEquipmentType();
            if (!isInEditMode()) {
                addEditAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, MainActivity.db.getEquipmentSummaryList(equipmentType, firstLineAddEditSpinner));
                addEditCopySpinner.setAdapter(addEditAdapter);

                if (trackActiveEquipment) {
                    try {
                        //Log.v(TAG, "eType: " + getActiveEquipmentType().toString());
                        addEditCopySpinner.setSelection(MainActivity.db.getActiveEquipmentIndexByType(getActiveEquipmentType())+this.offset);
                    } catch (NullPointerException npe) {
                        //npe.printStackTrace();
                    }
                }
            }
        } catch (IllegalArgumentException iae) {
            //iae.printStackTrace();
        }

    }

    public void zeroOutSelection() {
        addEditCopySpinner.setSelection(0);
    }

    public void allowEditEquipment() {
        editEquipmentButton.setVisibility(View.VISIBLE);
    }

    protected void setActiveEquipment(int index) {
        try {
            MainActivity.db.setActiveBuildingItem(index, this.offset, getActiveEquipmentType());
        }catch (Exception e) {
            GeneralFunctions.DisplayToast(mContext, "Something went wrong when setting the active equipment");
        }
    }

    public void updateEquipmentSpinner() {

        ArrayAdapter<String> equipmentAdapter;
        String[] equipmentTypes;

        if(allEquipmentInList) {
            if(allowFakeAssets) {
                equipmentTypes = EquipmentType.toPhotoStringArray();
            } else {
                equipmentTypes = EquipmentType.toStringArray();
            }
            equipmentAdapter = new ArrayAdapter<String>(this.mContext, android.R.layout.simple_spinner_dropdown_item, equipmentTypes);
        } else {
            equipmentAdapter = new ArrayAdapter<String>(this.mContext,android.R.layout.simple_spinner_dropdown_item, MainActivity.db.getEquipmentCountListByType());
        }

        this.equipmentSpinner.setAdapter(equipmentAdapter);


            String equipment;
            try {
                if (allEquipmentInList) {
                    equipment = MainActivity.db.getActiveEquipmentType().toString();
                } else {
                    equipment = MainActivity.db.getActiveEquipmentTypeAndCount();
                }
                //Log.v(TAG, "equipment: " + equipment);
                int position = equipmentAdapter.getPosition(equipment);
                equipmentSpinner.setSelection(position);

            } catch (NullPointerException npe) {
                //Log.v(TAG, "No equipment added");
            }
    }

    protected void EditEquipmentPopup() {
            //Log.v(TAG, "editEquipmentPopup");
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View spinner = inflater.inflate(R.layout.edit_equipment_type, null);

            final Spinner equipChangeSpinner = (Spinner) spinner.findViewById(R.id.editSpinner);
            final ArrayAdapter<String> buildingItemAdapter = new ArrayAdapter<String> (mContext,android.R.layout.simple_dropdown_item_1line, EquipmentType.getBuildingItemList(getActiveEquipmentType()));
            equipChangeSpinner.setAdapter(buildingItemAdapter);

            builder.setView(spinner);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //update activeEquipmentType
                    EquipmentType eType = EquipmentType.getEnumType((String) equipChangeSpinner.getSelectedItem());
                    //Log.v(TAG, eType.toString());
                    MainActivity.db.updateEquipmentType(eType);
                    updateEquipmentSpinner();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.setTitle("Select updated equipment type");

            builder.show();
    }

    //Purpose is to add a simple equipment view
    protected void AddSimpleEquipmentPopup() {
        //Log.v(TAG, "AddSimpleEquipmentPopup");
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View buildingItemView = inflater.inflate(R.layout.add_equipment_simple, null);


        final AddBuildingItemView buildingItem = (AddBuildingItemView) buildingItemView.findViewById(R.id.add_building_item_view);

        buildingItem.setSimpleView(true);
        buildingItem.setupView(this.getActiveEquipmentType());

        builder.setView(buildingItemView);
        builder.setPositiveButton(getResources().getString(R.string.submit), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //update activeEquipmentType
                EquipmentType equipmentType = getActiveEquipmentType();
                try {
                    switch (equipmentType.getBuildingItemType()) {
                        case LIGHT:
                            MainActivity.db.AddLightingEquipment(buildingItem.getLightingInput(equipmentType));
                            break;
                        case EQUIPMENT:
                            MainActivity.db.addEquipment(buildingItem.getEquipmentInput(equipmentType));
                            break;
                        case ENVELOPE:
                            MainActivity.db.AddEnvelopeEquipment(buildingItem.getEnvelopeInput(equipmentType));
                            break;
                        case WATER_FIXTURE:
                            MainActivity.db.AddWaterEquipment(buildingItem.getWaterInput());
                            break;
                        case UTILITYMETER:
                            MainActivity.db.AddUtilityMeter(buildingItem.getMeter());
                            break;
                    }
                    updateAddEditSpinner();
                } catch (IOException ioe) {
                    GeneralFunctions.DisplayToast(mContext, Strings.BUILDING_ASSET_ERROR_MESSAGE);
                }
            }

        });
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("Add Simple Building Asset");

        builder.show();
    }

    private void setupViews() {
        if(activeEquipmentTypeListener != null)
            activeEquipmentTypeListener.onEvent() ;
    }

    private void setupEquipmentViews() {
        if(activeEquipmentListener != null)
            activeEquipmentListener.onEvent();
    }

    public EquipmentType getActiveEquipmentType() throws IllegalArgumentException {

        String selectedItem;

        if (allEquipmentInList) {
            selectedItem = (String) equipmentSpinner.getSelectedItem();
        } else {
            selectedItem = (String) this.equipmentSpinner.getSelectedItem();
            //Log.v(TAG, "Selected Item: " + selectedItem);
            selectedItem = selectedItem.substring(0, selectedItem.indexOf("(")-1);
        }

        return EquipmentType.getEnumType(selectedItem);
    }

    public String getActiveEquipmentString() {
        return (String) this.equipmentSpinner.getSelectedItem();
    }
}
