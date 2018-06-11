package display.subclasses;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.EEN.Audit.R;

import constants.GeneralFunctions;
import constants.Strings;
import conversion.EnergyUnits;
import display.Audit.MainActivity;
import fsTablesClasses.UtilityMeterInformation;
import fsTablesEnums.EquipmentType;
import fsTablesEnums.FuelType;

/**
 * Created by Steven on 10/17/2015.
 */
/**
 * Created by Patrick on 11/30/2015.
 */
public class AddUtilityMeterView extends LinearLayout {

    private AutoCompleteTextView meterLocationText;
    private EditText serialText;
    private EditText readingText;
    private Spinner meterUtilityUnitsSpinner;
    private Spinner meterUtilityTypeSpinner;
    private EditText providerText;
    private EditText meterNotesText;
    private ArrayAdapter<String> utilityTypeAdapter;
    private ArrayAdapter<String> utilityUnitsAdapter;

    private View view;

    private boolean mSimpleView = false;

    public AddUtilityMeterView(Context context) {
        super(context);
        init(context);
    }

    public AddUtilityMeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddUtilityMeterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {

        view = View.inflate(context, R.layout.add_meter, this);
        meterLocationText = (AutoCompleteTextView) view.findViewById(R.id.locationMeter);
        meterLocationText.setThreshold(2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, MainActivity.db.getLocationList());
        meterLocationText.setAdapter(adapter2);

        serialText= (EditText) view.findViewById(R.id.meterSerial);
        readingText= (EditText) view.findViewById(R.id.meterReading);
        meterUtilityUnitsSpinner = (Spinner) view.findViewById(R.id.meterUtilityUnits);
        meterUtilityTypeSpinner = (Spinner) view.findViewById(R.id.meterUtilityType);
        utilityTypeAdapter =  new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, FuelType.toStringArray());
        utilityUnitsAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, EnergyUnits.toStringArray());

        meterUtilityUnitsSpinner.setAdapter(utilityUnitsAdapter);
        meterUtilityTypeSpinner.setAdapter(utilityTypeAdapter);

        providerText= (EditText) view.findViewById(R.id.meterProvider);
        meterNotesText= (EditText) view.findViewById(R.id.meterNotes);

    }

    public void setupExistingUtilityMeter(UtilityMeterInformation meterInformation) {

        meterLocationText.setText(meterInformation.getLocation());
        meterUtilityTypeSpinner.setSelection(utilityTypeAdapter.getPosition(meterInformation.getMeterUtilityType()));
        serialText.setText("" + meterInformation.getMeterSerial());
        readingText.setText("" + meterInformation.getMeterReading());
        meterUtilityUnitsSpinner.setSelection(utilityUnitsAdapter.getPosition(meterInformation.getMeterUtilityUnits()));
        providerText.setText(meterInformation.getMeterUtilityProvider());
        meterNotesText.setText(meterInformation.getMeterNotes());

    }

    public UtilityMeterInformation getUtilityMeterInput() {

        //TODO-Done has to match utility meter information class
        UtilityMeterInformation meterInformation = new UtilityMeterInformation();

        //meterInformation.setEquipmentType(equipmentType);
        meterInformation.setLocation(meterLocationText.getText().toString());
        meterInformation.setMeterNotes(meterNotesText.getText().toString()); //TODO-Done correct to setMeterNotes

        //TODO-define mSimpleView
        if (!mSimpleView) {
            //2) TODO-done correct setter functions here
            meterInformation.setMeterSerial(serialText.getText().toString());
            meterInformation.setMeterReading(GeneralFunctions.StringToDouble(readingText.getText().toString(), Strings.ERROR_PROCESSING_START + " Meter Reading"));
            meterInformation.setMeterUtilityType((String) meterUtilityTypeSpinner.getSelectedItem());
            meterInformation.setMeterUtilityUnits((String) meterUtilityUnitsSpinner.getSelectedItem());
            meterInformation.setMeterUtilityProvider(providerText.getText().toString());

        }

        return meterInformation;
    }

    public void clearUtilityMeterWindow() {

        meterLocationText.setText("");
        serialText.setText("");
        readingText.setText("");
        providerText.setText("");
        meterNotesText.setText("");

    }

    public void setSimpleView(boolean simpleView) {
        this.mSimpleView = simpleView;
    }

}
