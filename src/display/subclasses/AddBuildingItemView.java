package display.subclasses;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.EEN.Audit.R;

import constants.GeneralFunctions;
import constants.Strings;
import conversion.EfficiencyUnits;
import conversion.PowerUnits;
import display.Audit.MainActivity;
import fsTablesClasses.EnvelopeInformation;
import fsTablesClasses.EquipmentInformation;
import fsTablesClasses.LightingInformation;
import fsTablesClasses.UtilityMeterInformation;
import fsTablesClasses.WaterInformation;
import fsTablesEnums.CoolingSource;
import fsTablesEnums.DefaultAllocationSettings;
import fsTablesEnums.EquipmentType;
import fsTablesEnums.FanSpeedModulation;
import fsTablesEnums.FuelType;
import fsTablesEnums.Function;
import fsTablesEnums.HeatingSource;
import fsTablesEnums.LampType;
import fsTablesEnums.Make;

/**
 * Created by Steven Wance on 8/25/2015.
 * View to keep track of single building item input
 */
public class AddBuildingItemView extends LinearLayout {

    public static final String TAG = "AddBuildingItemView";

    private Spinner heatingUtilitySpinner;
    private Spinner coolingUtilitySpinner;
    private Spinner supplyUtilitySpinner;
    private Spinner returnUtilitySpinner;
    private Spinner otherUtilitySpinner;
    private Spinner heatingCapacityUnitsSpinner;
    private Spinner coolingCapacityUnitsSpinner;
    private Spinner supplyCapacityUnitsSpinner;
    private Spinner returnCapacityUnitsSpinner;
    private Spinner otherCapacityUnitsSpinner;
    private Spinner heatingEfficiencyUnitsSpinner;
    private Spinner coolingEfficiencyUnitsSpinner;
    private Spinner supplyEfficiencyUnitsSpinner;
    private Spinner returnEfficiencyUnitsSpinner;
    private Spinner otherEfficiencyUnitsSpinner;
    private Spinner heatingSourceSpinner;
    private Spinner coolingSourceSpinner;
    private Spinner supplyFanModulationSpinner;
    private Spinner returnFanModulationSpinner;
    protected Spinner addEditCopySpinner;

    private Spinner motorTypeSpinner;
    private Spinner dhwTypeSpinner;
    private Spinner pumpModulationSpinner;
    private Spinner boilerTypeSpinner;
    private Spinner boilerMediumSpinner;
    private Spinner chillerTypeSpinner;
    private Spinner chillerCondenserTypeSpinner;
    private Spinner chillerEvaporatorTypeSpinner;
    private Spinner AHUEconomizerStatusSpinner;
    private Spinner AHUEconomizerDamperControlSpinner;
    private Spinner AHUPreheatCoilSpinner;
    private Spinner AHUCoolingCoilSpinner;
    private Spinner AHUHeatingCoilSpinner;
    private Spinner refrigAssociatedSpinner;
    private Spinner lampControlSpinner;
    private Spinner lampTypeSpinner;

    private EditText onPeakPercentEditText;
    private EditText offPeakPercentEditText;
    private EditText onPeakDaysEditText;
    private EditText powerFactorEditText;
    private EditText refrigTempEditText;
    private EditText dhwEnergyFactorEditText;
    private EditText dhwTankSizeEditText;
    private EditText AHUEconomizerLockoutEditText;
    private EditText AHUTotalCFMEditText;
    private EditText lampQuantityEditText;
    private EditText lightingHoursEditText;
    private AutoCompleteTextView locationLightingEditText;
    private EditText fixtureQuantityEditText;

    private String[] manufacturers = Make.toStringArray();
    private EditText unitIdEditText;
    private EditText modelEditText;
    private EditText serialEditText;
    private EditText installYearEditText;
    private EditText quantityEditText;
    private AutoCompleteTextView locationEditText; //location
    private AutoCompleteTextView servesEditText;//serves
    private Spinner controlSpinner;//control
    private EditText scheduleEditText;//schedule
    private EditText notesEditText;//notes
    private EditText lightingNotesEditText;
    private EditText heatingCapacityEditText;
    private EditText heatingEfficiencyEditText;
    private EditText heatingHoursEditText;
    private EditText coolingCapacityEditText;
    private EditText coolingEfficiencyEditText;
    private EditText coolingHoursEditText;
    private EditText supplyCapacityEditText;
    private EditText supplyEfficiencyEditText;
    private EditText supplyHoursEditText;
    private EditText returnCapacityEditText;
    private EditText returnEfficiencyEditText;
    private EditText returnHoursEditText;
    private EditText otherCapacityEditText;
    private EditText otherEfficiencyEditText;
    private EditText otherHoursEditText;

    //protected boolean hasEquipment;

    protected AutoCompleteTextView makeTextView;

    protected View ahuView;
    protected View boilerView;
    protected View chillerView;
    protected View dhwView;
    protected View packageView;
    protected View pumpView;
    protected View refrigCondensingView;
    protected View refrigEvapView;
    protected View transformerView;

    protected View heatingView;
    protected View coolingView;
    protected View supplyView;
    protected View returnView;
    protected View otherView;
    protected View envelopeView;
    protected View waterView;
    protected View lightingView;
    protected View equipmentView;
    protected View view;

    protected LinearLayout doorView;
    protected LinearLayout windowView;

    protected LinearLayout equipmentUnsimple;
    protected LinearLayout lightingUnsimple;
    protected LinearLayout envelopeUnsimple;
    protected LinearLayout waterUnsimple;

    //Water fixture items
    protected CounterView sinkCounterView;
    protected CounterView sinkCounterView2;
    protected CounterView toiletCounterView;
    protected CounterView urinalCounterView;
    protected CounterView showerCounterView;
    protected AutoCompleteTextView waterLocation;
    protected EditText waterNotes;
    protected EditText urinalFlushRateText;
    protected EditText toiletFlushRateText;
    protected EditText sinkFlowRateText;
    protected EditText sinkFlowRateText2;
    protected EditText showerFlowRateText;

    //Envelope items
    protected AutoCompleteTextView envelopeLocationText;

    protected EditText envelopeHeight;
    protected EditText envelopeRValue;
    protected Spinner windowType;
    protected EditText envelopeColor;
    protected EditText envelopeNotes;
    protected Spinner InsulationType;
    protected Spinner envelopeDoorType;
    protected Spinner windowGlassType;
    protected EditText envelopeWidth;
    protected EditText envelopeDoorMaterial;
    protected EditText envelopeQuantity;
    protected Spinner EnvelopeWindowOperatingType;


    protected ArrayAdapter<String> utilityAdapter;
    protected ArrayAdapter<String> capacityUnitsAdapter;
    protected ArrayAdapter<String> efficiencyUnitsAdapter;
    protected ArrayAdapter<String> controlAdapter;
    protected ArrayAdapter<String> motorTypeAdapter;
    protected ArrayAdapter<String> dhwTypeAdapter;
    protected ArrayAdapter<String> boilerTypeAdapter;
    protected ArrayAdapter<String> boilerMediumAdapter;
    protected ArrayAdapter<String> chillerTypeAdapter;
    protected ArrayAdapter<String> chillerCondenserTypeAdapter;
    protected ArrayAdapter<String> chillerEvaporatorTypeAdapter;
    protected ArrayAdapter<String> AHUEconomizerStatusAdapter;
    protected ArrayAdapter<String> AHUEconomizerDamperAdapter;
    protected ArrayAdapter<String> AHUHeatingCoilAdapter;
    protected ArrayAdapter<String> AHUCoolingCoilAdapter;
    protected ArrayAdapter<String> refrigAssociatedAdapter;
    protected ArrayAdapter<String> fanModulationAdapter;
    protected ArrayAdapter<String> heatingSourceAdapter;
    protected ArrayAdapter<String> coolingSourceAdapter;
    protected ArrayAdapter<String> lampTypeAdapter;
    protected ArrayAdapter<String> lampControlAdapter;
    protected ArrayAdapter<String> windowAdapter;
    protected ArrayAdapter<String> windowTypeAdapter;
    protected ArrayAdapter<String> InsulationTypeAdapter;
    protected ArrayAdapter<String> envelopeDoorTypeAdapter;
    protected ArrayAdapter<String> windowGlassTypeAdapter;

    protected AddUtilityMeterView addUtilityMeterView;

    protected ArrayAdapter<String> EnvelopeWindowOperatingTypeAdapter;


    protected boolean mSimpleView = false;

    public AddBuildingItemView(Context context) {
        super(context);
        init(context);
    }

    public AddBuildingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddBuildingItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    
    private void init(Context context) {

        view =  View.inflate(context, R.layout.add_building_item_view, this);

        lightingView = view.findViewById(R.id.add_lighting_included);
        equipmentView = view.findViewById(R.id.add_equipment_equipment_included);
        envelopeView = view.findViewById(R.id.add_envelope_included);
        waterView = view.findViewById(R.id.add_water_included);

        lightingView.setVisibility(View.INVISIBLE);
        equipmentView.setVisibility(View.INVISIBLE);
        envelopeView.setVisibility(View.INVISIBLE);
        waterView.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, manufacturers);
        makeTextView = (AutoCompleteTextView) view.findViewById(R.id.autoEquipment);
        makeTextView.setThreshold(2);
        makeTextView.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, MainActivity.db.getLocationList());
        locationEditText = (AutoCompleteTextView) view.findViewById(R.id.location);
        locationEditText.setAdapter(adapter2);
        locationEditText.setThreshold(2);

        equipmentUnsimple = (LinearLayout) view.findViewById(R.id.add_equipment_equipment_unsimple);
        envelopeUnsimple = (LinearLayout) view.findViewById(R.id.envelopeUnsimple);
        lightingUnsimple = (LinearLayout) view.findViewById(R.id.lightingUnsimple);
        waterUnsimple = (LinearLayout) view.findViewById(R.id.waterUnsimple);

        windowView = (LinearLayout) view.findViewById(R.id.envelopeWindow);
        doorView = (LinearLayout) view.findViewById(R.id.envelopeDoor);

        //add regular equipment info edit texts
        addEditCopySpinner = (Spinner) view.findViewById(R.id.spinnerAddEditCopy);
        unitIdEditText = (EditText) view.findViewById(R.id.unitId);
        modelEditText = (EditText) view.findViewById(R.id.model);
        serialEditText = (EditText) view.findViewById(R.id.serial);
        installYearEditText = (EditText) view.findViewById(R.id.installYear);
        quantityEditText = (EditText) view.findViewById(R.id.quantity);
        servesEditText = (AutoCompleteTextView) view.findViewById(R.id.serves);
        scheduleEditText = (EditText) view.findViewById(R.id.schedule);
        notesEditText = (EditText) view.findViewById(R.id.notes);
        lightingNotesEditText = (EditText) view.findViewById(R.id.lightingNotes);

        servesEditText.setAdapter((adapter2));
        servesEditText.setThreshold(2);

        //set default
        quantityEditText.setText("1");

        //setup special equipment spinners	
        motorTypeSpinner = (Spinner) view.findViewById(R.id.motorType);
        dhwTypeSpinner = (Spinner) view.findViewById(R.id.dhwType);
        pumpModulationSpinner = (Spinner) view.findViewById(R.id.pumpModulation);
        boilerTypeSpinner = (Spinner) view.findViewById(R.id.boilerType);
        boilerMediumSpinner = (Spinner) view.findViewById(R.id.boilerMedium);
        chillerTypeSpinner = (Spinner) view.findViewById(R.id.chillerType);
        chillerCondenserTypeSpinner = (Spinner) view.findViewById(R.id.chillerCondenserType);
        chillerEvaporatorTypeSpinner = (Spinner) view.findViewById(R.id.chillerEvaporatorType);
        AHUEconomizerStatusSpinner = (Spinner) view.findViewById(R.id.AHU_Economizer);
        AHUEconomizerDamperControlSpinner = (Spinner) view.findViewById(R.id.AHU_EconomizerDamperControl);
        AHUPreheatCoilSpinner = (Spinner) view.findViewById(R.id.AHU_PreheatCoil);
        AHUCoolingCoilSpinner =  (Spinner) view.findViewById(R.id.AHU_CoolingCoil);
        AHUHeatingCoilSpinner = (Spinner) view.findViewById(R.id.AHU_HeatingCoil);
        refrigAssociatedSpinner = (Spinner) view.findViewById(R.id.refrigAssociated);
        lampControlSpinner = (Spinner) view.findViewById(R.id.lampControl);
        lampTypeSpinner = (Spinner) view.findViewById(R.id.spinnerlampType);

        motorTypeAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.motorType);
        dhwTypeAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.dhwType);
        boilerTypeAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.boilerType);
        boilerMediumAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.boilerMedium);
        chillerTypeAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.chillerType);
        chillerCondenserTypeAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.chillerCondenserType);
        chillerEvaporatorTypeAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.chillerEvaporatorType);
        AHUEconomizerStatusAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.ahuEconomizerStatus);
        AHUEconomizerDamperAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.ahuEconomizerDamperControl);
        AHUCoolingCoilAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.COOLING_AHU_COIL);
        AHUHeatingCoilAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.HEATING_AHU_COIL);
        //TODO make this query evap equipment id
        refrigAssociatedAdapter = new ArrayAdapter<String> (context,android.R.layout.simple_dropdown_item_1line, Strings.COOLING_AHU_COIL);
        fanModulationAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, FanSpeedModulation.toStringArray());
        lampControlAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, Strings.lampControlType);
        lampTypeAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, LampType.toStringArray());

        //set adapters
        motorTypeSpinner.setAdapter(motorTypeAdapter);
        dhwTypeSpinner.setAdapter(dhwTypeAdapter);
        pumpModulationSpinner.setAdapter(fanModulationAdapter);
        boilerTypeSpinner.setAdapter(boilerTypeAdapter);
        boilerMediumSpinner.setAdapter(boilerMediumAdapter);
        chillerTypeSpinner.setAdapter(chillerTypeAdapter);
        chillerCondenserTypeSpinner.setAdapter(chillerCondenserTypeAdapter);
        chillerEvaporatorTypeSpinner.setAdapter(chillerEvaporatorTypeAdapter);
        AHUEconomizerStatusSpinner.setAdapter(AHUEconomizerStatusAdapter);
        AHUEconomizerDamperControlSpinner.setAdapter(AHUEconomizerDamperAdapter);
        AHUPreheatCoilSpinner.setAdapter(AHUHeatingCoilAdapter);
        AHUCoolingCoilSpinner.setAdapter(AHUCoolingCoilAdapter);
        AHUHeatingCoilSpinner.setAdapter(AHUHeatingCoilAdapter);
        refrigAssociatedSpinner.setAdapter(refrigAssociatedAdapter);
        lampControlSpinner.setAdapter(lampControlAdapter);
        lampTypeSpinner.setAdapter(lampTypeAdapter);

        //setup special equipment editTexts
        onPeakPercentEditText = (EditText) view.findViewById(R.id.onPeakPercent);
        offPeakPercentEditText = (EditText) view.findViewById(R.id.offPeakPercent);
        onPeakDaysEditText = (EditText) view.findViewById(R.id.onPeakDays);
        powerFactorEditText = (EditText) view.findViewById(R.id.powerFactor);
        refrigTempEditText = (EditText) view.findViewById(R.id.refrigTemp);
        dhwEnergyFactorEditText  = (EditText) view.findViewById(R.id.dhwEnergyFactor);
        dhwTankSizeEditText = (EditText) view.findViewById(R.id.dhwTankSize);
        AHUEconomizerLockoutEditText  = (EditText) view.findViewById(R.id.AHU_EconomizerLockout);
        AHUTotalCFMEditText = (EditText) view.findViewById(R.id.AHU_TotalCFM);
        locationLightingEditText = (AutoCompleteTextView) view.findViewById(R.id.locationLighting);
        locationLightingEditText.setThreshold(2);
        locationLightingEditText.setAdapter(adapter2);

        fixtureQuantityEditText = (EditText) view.findViewById(R.id.fixtureQuantity);
        lampQuantityEditText = (EditText) view.findViewById(R.id.lampQuantity);
        lightingHoursEditText = (EditText) view.findViewById(R.id.lightingHours);

        //setup equipment categories for hiding
        ahuView = view.findViewById(R.id.add_equipment_ahu);
        boilerView = view.findViewById(R.id.add_equipment_boiler);
        chillerView = view.findViewById(R.id.add_equipment_chiller);
        dhwView  = view.findViewById(R.id.add_equipment_dhw);
        packageView  = view.findViewById(R.id.add_equipment_package);
        pumpView  = view.findViewById(R.id.add_equipment_pump);
        refrigCondensingView  = view.findViewById(R.id.add_equipment_refrig_condensing);
        refrigEvapView  = view.findViewById(R.id.add_equipment_refrig_evapoporator);
        transformerView = view.findViewById(R.id.add_equipment_transformer);

        //setup allocation categories for hiding
        heatingView = view.findViewById(R.id.add_allocation_heating);
        coolingView = view.findViewById(R.id.add_allocation_cooling);
        supplyView = view.findViewById(R.id.add_allocation_supply);
        returnView = view.findViewById(R.id.add_allocation_return);
        otherView = view.findViewById(R.id.add_allocation_other);

        //setup allocation EditText's
        heatingCapacityEditText = (EditText) view.findViewById(R.id.heatingCapacity);
        heatingEfficiencyEditText = (EditText) view.findViewById(R.id.heatingEfficiency);
        heatingHoursEditText = (EditText) view.findViewById(R.id.heatingRunHours);
        coolingCapacityEditText = (EditText) view.findViewById(R.id.coolingCapacity);
        coolingEfficiencyEditText = (EditText) view.findViewById(R.id.coolingEfficiency);
        coolingHoursEditText = (EditText) view.findViewById(R.id.coolingRunHours);
        supplyCapacityEditText = (EditText) view.findViewById(R.id.supplyCapacity);
        supplyEfficiencyEditText = (EditText) view.findViewById(R.id.supplyEfficiency);
        supplyHoursEditText = (EditText) view.findViewById(R.id.supplyRunHours);
        returnCapacityEditText = (EditText) view.findViewById(R.id.returnCapacity);
        returnEfficiencyEditText = (EditText) view.findViewById(R.id.returnEfficiency);
        returnHoursEditText = (EditText) view.findViewById(R.id.returnRunHours);
        otherCapacityEditText = (EditText) view.findViewById(R.id.otherCapacity);
        otherEfficiencyEditText = (EditText) view.findViewById(R.id.otherEfficiency);
        otherHoursEditText = (EditText) view.findViewById(R.id.otherRunHours);

        //populate utility types
       final String[] utilityTypes = FuelType.toStringArray();
        heatingUtilitySpinner = (Spinner) (view.findViewById(R.id.spinnerHeatingUtility));
        coolingUtilitySpinner = (Spinner) (view.findViewById(R.id.spinnerCoolingUtility));
        supplyUtilitySpinner = (Spinner) (view.findViewById(R.id.spinnerSupplyUtility));
        returnUtilitySpinner = (Spinner) (view.findViewById(R.id.spinnerReturnUtility));
        otherUtilitySpinner = (Spinner) (view.findViewById(R.id.spinnerOtherUtility));

        utilityAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, utilityTypes);
        heatingUtilitySpinner.setAdapter(utilityAdapter);
        coolingUtilitySpinner.setAdapter(utilityAdapter);
        supplyUtilitySpinner.setAdapter(utilityAdapter);
        returnUtilitySpinner.setAdapter(utilityAdapter);
        otherUtilitySpinner.setAdapter(utilityAdapter);

        //populate capacity units
        final String[] capacityUnitsTypes = PowerUnits.toStringArray();
        heatingCapacityUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerHeatingCapacityUnits));
        coolingCapacityUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerCoolingCapacityUnits));
        supplyCapacityUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerSupplyCapacityUnits));
        returnCapacityUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerReturnCapacityUnits));
        otherCapacityUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerOtherCapacityUnits));

        capacityUnitsAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, capacityUnitsTypes);
        heatingCapacityUnitsSpinner.setAdapter(capacityUnitsAdapter);
        coolingCapacityUnitsSpinner.setAdapter(capacityUnitsAdapter);
        supplyCapacityUnitsSpinner.setAdapter(capacityUnitsAdapter);
        returnCapacityUnitsSpinner.setAdapter(capacityUnitsAdapter);
        otherCapacityUnitsSpinner.setAdapter(capacityUnitsAdapter);

        //populate efficiency units
        final String[] efficiencyUnitsTypes = EfficiencyUnits.toStringArray();
        heatingEfficiencyUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerHeatingEfficiencyUnits));
        coolingEfficiencyUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerCoolingEfficiencyUnits));
        supplyEfficiencyUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerSupplyEfficiencyUnits));
        returnEfficiencyUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerReturnEfficiencyUnits));
        otherEfficiencyUnitsSpinner = (Spinner) (view.findViewById(R.id.spinnerOtherEfficiencyUnits));

        efficiencyUnitsAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, efficiencyUnitsTypes);
        heatingEfficiencyUnitsSpinner.setAdapter(efficiencyUnitsAdapter);
        coolingEfficiencyUnitsSpinner.setAdapter(efficiencyUnitsAdapter);
        supplyEfficiencyUnitsSpinner.setAdapter(efficiencyUnitsAdapter);
        returnEfficiencyUnitsSpinner.setAdapter(efficiencyUnitsAdapter);
        otherEfficiencyUnitsSpinner.setAdapter(efficiencyUnitsAdapter);

        //populate heating source notes
        heatingSourceSpinner = (Spinner) view.findViewById(R.id.spinnerHeatingSource);
        heatingSourceAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, HeatingSource.toStringArray());
        heatingSourceSpinner.setAdapter(heatingSourceAdapter);

        //populate cooling source notes
        coolingSourceSpinner = (Spinner) view.findViewById(R.id.spinnerCoolingSource);
        coolingSourceAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, CoolingSource.toStringArray());
        coolingSourceSpinner.setAdapter(coolingSourceAdapter);

        //populate fan modulation notes
        supplyFanModulationSpinner = (Spinner) view.findViewById(R.id.spinnerSupplyFanModulation);
        returnFanModulationSpinner = (Spinner) view.findViewById(R.id.spinnerReturnFanModulation);
        supplyFanModulationSpinner.setAdapter(fanModulationAdapter);
        returnFanModulationSpinner.setAdapter(fanModulationAdapter);

        //populate control options
        controlSpinner = (Spinner) view.findViewById(R.id.spinnerControl);
        controlAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, Strings.controlOptions);
        controlSpinner.setAdapter(controlAdapter);

        //Water fixture items
        sinkCounterView = (CounterView) view.findViewById(R.id.add_water_sink);
        sinkCounterView2 = (CounterView) view.findViewById(R.id.add_water_sink_2);
        showerCounterView = (CounterView) view.findViewById(R.id.add_water_shower);
        toiletCounterView = (CounterView) view.findViewById(R.id.add_water_toilet);
        urinalCounterView = (CounterView) view.findViewById(R.id.add_water_urinal);
        sinkCounterView.setCurrentName(context.getResources().getString(R.string.sinkQty));
        toiletCounterView.setCurrentName(context.getResources().getString(R.string.toiletQty));
        urinalCounterView.setCurrentName(context.getResources().getString(R.string.urinalQty));
        sinkCounterView2.setCurrentName(context.getResources().getString(R.string.sinkQty2));
        showerCounterView.setCurrentName(context.getResources().getString(R.string.showerQty));

        waterLocation = (AutoCompleteTextView) view.findViewById(R.id.waterLocation);
        waterLocation.setThreshold(2);
        waterLocation.setAdapter(adapter2);

        waterNotes = (EditText) view.findViewById(R.id.waterNotes);
        urinalFlushRateText = (EditText) view.findViewById(R.id.urinalRateEditText);
        toiletFlushRateText = (EditText) view.findViewById(R.id.toiletRateEditText);
        sinkFlowRateText = (EditText) view.findViewById(R.id.sinkRateEditText);
        sinkFlowRateText2 = (EditText) view.findViewById(R.id.sinkRateEditText2);
        showerFlowRateText = (EditText) view.findViewById(R.id.showerRateEditText);

        //Envelope items
        envelopeLocationText = (AutoCompleteTextView) view.findViewById(R.id.locationEnvelope);
        envelopeLocationText.setThreshold(2);
        envelopeLocationText.setAdapter(adapter2);

        envelopeHeight =  (EditText) view.findViewById(R.id.envelopeHeight);
        envelopeRValue =  (EditText) view.findViewById(R.id.envelopeRValue);
        windowType =  (Spinner) view.findViewById(R.id.windowType);
        envelopeColor =  (EditText) view.findViewById(R.id.envelopeColor);
        envelopeNotes =  (EditText) view.findViewById(R.id.envelopeNotes);
        InsulationType =  (Spinner) view.findViewById(R.id.InsulationType);
        envelopeDoorType =  (Spinner) view.findViewById(R.id.envelopeDoorType);
        windowGlassType =  (Spinner) view.findViewById(R.id.windowGlassType);
        envelopeWidth =  (EditText) view.findViewById(R.id.envelopeWidth);
        envelopeDoorMaterial =  (EditText) view.findViewById(R.id.envelopeDoorMaterial);
        envelopeQuantity =  (EditText) view.findViewById(R.id.envelopeQuantity);
        EnvelopeWindowOperatingType =  (Spinner) view.findViewById(R.id.EnvelopeWindowOperatingType);

        windowTypeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Strings.WINDOW_TYPES);
        InsulationTypeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Strings.INSULATION_TYPES);
        envelopeDoorTypeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Strings.DOOR_MATERIAL_TYPES);
        windowGlassTypeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Strings.WINDOW_GLASS_TYPES);
        EnvelopeWindowOperatingTypeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Strings.WINDOW_FRAME_TYPES);

        windowType.setAdapter(windowTypeAdapter);
        InsulationType.setAdapter(InsulationTypeAdapter);
        envelopeDoorType.setAdapter(envelopeDoorTypeAdapter);
        windowGlassType.setAdapter(windowGlassTypeAdapter);
        EnvelopeWindowOperatingType.setAdapter(EnvelopeWindowOperatingTypeAdapter);

        addUtilityMeterView = (AddUtilityMeterView) view.findViewById(R.id.addmeter);

    }

    public void setSimpleView(boolean simpleView) {
        this.mSimpleView = simpleView;
    }

    public EquipmentInformation getEquipmentInput(EquipmentType eType) {

        EquipmentInformation newEquipment = new EquipmentInformation();
        Function f;

        newEquipment.setEquipmentType(eType);
        newEquipment.setUnitID(unitIdEditText.getText().toString());
        int quantityEquipment;
        try {
            quantityEquipment = Integer.parseInt(quantityEditText.getText().toString());
        } catch (Exception e) {
            quantityEquipment = 1;
            //throw new IllegalArgumentException("Something went wrong when processing quantity");
        }
        newEquipment.setQuantity(quantityEquipment);
        newEquipment.setLocation(locationEditText.getText().toString());

        if(!mSimpleView) {
            newEquipment.setManufactuer(makeTextView.getText().toString());
            newEquipment.setModel(modelEditText.getText().toString());
            newEquipment.setSerial(serialEditText.getText().toString());
            newEquipment.setInstallYear(installYearEditText.getText().toString());
            newEquipment.setServes(servesEditText.getText().toString());
            newEquipment.setControl((String) controlSpinner.getSelectedItem());
            newEquipment.setSchedule(scheduleEditText.getText().toString());
            newEquipment.setNotes(notesEditText.getText().toString());

            //heating ai
            //capacity
            try {
                if (heatingView.getVisibility() == View.VISIBLE) {
                    //source
                    f = Function.HEATING;
                    newEquipment.setSource(f, (String) heatingSourceSpinner.getSelectedItem());
                    if (!(newEquipment.getSource(f).equals(HeatingSource.NO_HEATING.toString()))) {
                        //power
                        double hours = 0;
                        hours = GeneralFunctions.StringToDouble(heatingCapacityEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "heating capacity");
                        newEquipment.setCapacity(f, hours, PowerUnits.getEnumType((String) heatingCapacityUnitsSpinner.getSelectedItem()));
                        //efficiency
                        hours = GeneralFunctions.StringToDouble(heatingEfficiencyEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "heating efficiency");
                        newEquipment.setEfficiency(f, hours, EfficiencyUnits.getEnumType((String) heatingEfficiencyUnitsSpinner.getSelectedItem()));
                        //hours
                        hours = GeneralFunctions.StringToDouble((String) heatingHoursEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "heating hours");
                        newEquipment.setHours(f, hours);
                        //utility
                        newEquipment.setUtilityType(f, FuelType.getEnumType((String) heatingUtilitySpinner.getSelectedItem()));
                    }
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Error when adding heating equipment: " + e.getMessage());
            }
            //cooling ai
            //capacity
            try {
                if (coolingView.getVisibility() == View.VISIBLE) {
                    //source
                    f = Function.COOLING;
                    newEquipment.setSource(f, (String) coolingSourceSpinner.getSelectedItem());
                    if (!(newEquipment.getSource(f).equals(CoolingSource.NO_COOLING.toString()))) {
                        double hours = 0;
                        //hours
                        hours = GeneralFunctions.StringToDouble(coolingCapacityEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "cooling capacity");
                        newEquipment.setCapacity(f, hours, PowerUnits.getEnumType((String) coolingCapacityUnitsSpinner.getSelectedItem()));
                        //efficiency
                        hours = GeneralFunctions.StringToDouble(coolingEfficiencyEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "cooling efficiency");
                        newEquipment.setEfficiency(f, hours, EfficiencyUnits.getEnumType((String) coolingEfficiencyUnitsSpinner.getSelectedItem()));
                        //hours
                        hours = GeneralFunctions.StringToDouble((String) coolingHoursEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "cooling hours");
                        newEquipment.setHours(f, hours);
                        //utility
                        newEquipment.setUtilityType(f, FuelType.getEnumType((String) coolingUtilitySpinner.getSelectedItem()));
                    }
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Error when adding cooling equipment: " + e.getMessage());
            }
            //supply fan ai
            //capacity
            try {
                if (supplyView.getVisibility() == View.VISIBLE) {
                    //modulation
                    f = Function.SUPPLY_FAN;
                    newEquipment.setSource(f, (String) supplyFanModulationSpinner.getSelectedItem());
                    if (!(newEquipment.getSource(f).equals(FanSpeedModulation.NO_FAN.toString()))) {
                        double hours = 0;
                        //power
                        hours = GeneralFunctions.StringToDouble(supplyCapacityEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "supply fan power");
                        newEquipment.setCapacity(f, hours, PowerUnits.getEnumType((String) supplyCapacityUnitsSpinner.getSelectedItem()));
                        //efficiency
                        hours = GeneralFunctions.StringToDouble(supplyEfficiencyEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "supply fan efficiency");
                        newEquipment.setEfficiency(f, hours, EfficiencyUnits.getEnumType((String) supplyEfficiencyUnitsSpinner.getSelectedItem()));
                        //hours
                        hours = GeneralFunctions.StringToDouble((String) supplyHoursEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "supply fan hours");
                        newEquipment.setHours(f, hours);
                        //utility
                        newEquipment.setUtilityType(f, FuelType.getEnumType((String) supplyUtilitySpinner.getSelectedItem()));
                        //Log.v("AddEquipmentFragment/toEquipment", "SF Utility: " + newEquipment.getSupplyFuelType().toString());
                    }
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Supply fan adding error: " + e.getMessage());
            }
            //return fan ai
            //capacity
            try {
                if (returnView.getVisibility() == View.VISIBLE) {
                    f = Function.RETURN_FAN;
                    newEquipment.setSource(f, (String) returnFanModulationSpinner.getSelectedItem());

                    if (!(newEquipment.getSource(f).equals(FanSpeedModulation.NO_FAN.toString()))) {
                        double hours = 0;
                        //Log.v(TAG, newEquipment.getSource(f) + " is " + FanSpeedModulation.NO_FAN.toString() + ": " + newEquipment.getSource(f).equals(FanSpeedModulation.NO_FAN.name()));
                        //return fan power
                        hours = GeneralFunctions.StringToDouble(returnCapacityEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "return fan power");
                        newEquipment.setCapacity(f, hours, PowerUnits.getEnumType((String) returnCapacityUnitsSpinner.getSelectedItem()));
                        //efficiency
                        hours = GeneralFunctions.StringToDouble(returnEfficiencyEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "return fan efficiency");
                        newEquipment.setEfficiency(f, hours, EfficiencyUnits.getEnumType((String) returnEfficiencyUnitsSpinner.getSelectedItem()));
                        //hours
                        hours = GeneralFunctions.StringToDouble((String) returnHoursEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "return fan hours");
                        newEquipment.setHours(f, hours);
                        //utility
                        newEquipment.setUtilityType(f, FuelType.getEnumType((String) returnUtilitySpinner.getSelectedItem()));
                        //modulation
                        //Log.v(this.toString() + "/" + "getEquipmentInput()", f.name() + ":" + newEquipment.getAllocationCSV(f));
                    }
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Return fan adding error: " + e.getMessage());
            }
            //other ai
            //capacity
            try {
                if (otherView.getVisibility() == View.VISIBLE) {
                    double hours = 0;
                    f = Function.OTHER;
                    //power
                    hours = GeneralFunctions.StringToDouble(otherCapacityEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "other power");
                    newEquipment.setCapacity(f, hours, PowerUnits.getEnumType((String) otherCapacityUnitsSpinner.getSelectedItem()));

                    //efficiency
                    hours = GeneralFunctions.StringToDouble(otherEfficiencyEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "Other Efficiency");
                    newEquipment.setEfficiency(f, hours, EfficiencyUnits.getEnumType((String) otherEfficiencyUnitsSpinner.getSelectedItem()));

                    //hours
                    hours = GeneralFunctions.StringToDouble((String) otherHoursEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "OtherHours");
                    newEquipment.setHours(f, hours);
                    //utility
                    newEquipment.setUtilityType(f, FuelType.getEnumType((String) otherUtilitySpinner.getSelectedItem()));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Other equipment adding error: " + e.getMessage());
            }

            switch (eType) {

                case BOILER:
                    newEquipment.setBoilerType((String) boilerTypeSpinner.getSelectedItem());
                    newEquipment.setBoilerMedium((String) boilerMediumSpinner.getSelectedItem());
                    break;
                case CHILLER:
                    newEquipment.setChillerType((String) chillerTypeSpinner.getSelectedItem());
                    newEquipment.setChillerCondenserType((String) chillerCondenserTypeSpinner.getSelectedItem());
                    newEquipment.setChillerEvaporatorType((String) chillerEvaporatorTypeSpinner.getSelectedItem());
                    break;
                case WATERHEATER:
                    newEquipment.setDhwType((String) dhwTypeSpinner.getSelectedItem());
                    newEquipment.setDhwEnergyFactor(GeneralFunctions.StringToDouble(dhwEnergyFactorEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "DHW EnergyFactor"));
                    newEquipment.setDhwTankSize(dhwTankSizeEditText.getText().toString());
                    break;
                case CW_PUMP:
                case HW_PUMP:
                case OTHER_PUMP:
                    newEquipment.setPumpModulation((String) pumpModulationSpinner.getSelectedItem());
                    break;
                case AIRHANDLER:
                    newEquipment.setAHU_PreheatCoil((String) AHUPreheatCoilSpinner.getSelectedItem());
                    newEquipment.setAHU_HeatingCoil((String) AHUHeatingCoilSpinner.getSelectedItem());
                    newEquipment.setAHU_CoolingCoil((String) AHUCoolingCoilSpinner.getSelectedItem());
                    newEquipment.setAHU_TotalCFM(AHUTotalCFMEditText.getText().toString());
                case PACKAGEUNIT:
                    newEquipment.setAHU_Economizer((String) AHUEconomizerStatusSpinner.getSelectedItem());
                    newEquipment.setAHU_EconomizerLockout(AHUEconomizerLockoutEditText.getText().toString());
                    newEquipment.setAHU_EconomizerDamperControl((String) AHUEconomizerDamperControlSpinner.getSelectedItem());
                    break;
                case TRANSFORMER:
                    newEquipment.setOnPeakPercent(GeneralFunctions.StringToDouble(onPeakPercentEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "onPeakPercent"));
                    newEquipment.setOffPeakPercent(GeneralFunctions.StringToDouble(offPeakPercentEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "offpeakpercent"));
                    newEquipment.setOnPeakDays(GeneralFunctions.StringToDouble(onPeakDaysEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "onpeakDays"));
                    newEquipment.setPowerFactor(GeneralFunctions.StringToDouble(powerFactorEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "powerFactor"));
                    break;
                case REFRIGERATION_EVAPORATOR:
                    newEquipment.setRefrigTemp(refrigTempEditText.getText().toString());
                    newEquipment.setMotorType((String) motorTypeSpinner.getSelectedItem());
                    break;
                case REFRIGERATION_CONDENSING:
                    newEquipment.setRefrigAssociated((String) refrigAssociatedSpinner.getSelectedItem());
                    break;
                default:
            }
        }

        return newEquipment;
    }

    public LightingInformation getLightingInput(EquipmentType interior) {

        LightingInformation lightingEquipment = new LightingInformation();

        lightingEquipment.setIsInteriorEquipment(interior);
        lightingEquipment.setLocation(locationLightingEditText.getText().toString());
        lightingEquipment.setLampType(LampType.getEnumType((String) lampTypeSpinner.getSelectedItem()));
        lightingEquipment.setQuantity(GeneralFunctions.StringToInt(fixtureQuantityEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "Fixture Quantity"));
        lightingEquipment.setLampQuantity(GeneralFunctions.StringToInt(lampQuantityEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "Lamps Per Fixture"));

        if(!mSimpleView) {
            lightingEquipment.setControl((String) lampControlSpinner.getSelectedItem());
            lightingEquipment.setAnnualHours(GeneralFunctions.StringToDouble(lightingHoursEditText.getText().toString(), Strings.ERROR_PROCESSING_START + "LightingHours"));
            lightingEquipment.setNotes(lightingNotesEditText.getText().toString());
        }

        return lightingEquipment;

    }

    /**This function puts values of all the lighting fields when a specifc light is selected
     *
     * @param lightingEquipment
     */
    public void setupExistingLighting(LightingInformation lightingEquipment) {

        int position;

        locationLightingEditText.setText(lightingEquipment.getLocation());
        fixtureQuantityEditText.setText("" + lightingEquipment.getQuantity());

        position = lampTypeAdapter.getPosition(lightingEquipment.getLampType().toString());
        lampTypeSpinner.setSelection(position);
        lampQuantityEditText.setText("" + lightingEquipment.getLampQuantity());

        position = lampControlAdapter.getPosition(lightingEquipment.getControl());
        lampControlSpinner.setSelection(position);
        lightingHoursEditText.setText("" + lightingEquipment.getAnnualHours());

        lightingNotesEditText.setText(lightingEquipment.getNotes());
    }

    /**This functions sets all the equipment up when a specific equipment is selected
     *
     */
    public void setupExistingEquipment(EquipmentInformation existingEquipment) {
        Function f;
        int position;

        //unit id
        unitIdEditText.setText(existingEquipment.getUnitID());
        //Location
        locationEditText.setText(existingEquipment.getLocation());
        //qty
        quantityEditText.setText(""+existingEquipment.getQuantity());
        //make
        makeTextView.setText(existingEquipment.getManufacturer());
        //model
        modelEditText.setText(existingEquipment.getModelNumber());
        //serail
        serialEditText.setText(existingEquipment.getSerialNumber());
        //install year
        installYearEditText.setText(existingEquipment.getInstallYear());

        //Serves
        servesEditText.setText(existingEquipment.getServes());
        //Control (spinner)
        position = controlAdapter.getPosition(existingEquipment.getControl());
        controlSpinner.setSelection(position);
        //schedule
        scheduleEditText.setText(existingEquipment.getSchedule());

        notesEditText.setText(existingEquipment.getNotes());

        //equipment specific
        switch(existingEquipment.getEquipmentType()) {

            case BOILER:
                position = boilerTypeAdapter.getPosition(existingEquipment.getBoilerType()); boilerTypeSpinner.setSelection(position);
                position = boilerMediumAdapter.getPosition(existingEquipment.getBoilerMedium()); boilerMediumSpinner.setSelection(position);
                break;
            case CHILLER:
                position = chillerTypeAdapter.getPosition(existingEquipment.getChillerType()); chillerTypeSpinner.setSelection(position);
                position = chillerCondenserTypeAdapter.getPosition(existingEquipment.getChillerCondenserType()); chillerCondenserTypeSpinner.setSelection(position);
                position = chillerEvaporatorTypeAdapter.getPosition(existingEquipment.getChillerEvaporatorType()); chillerEvaporatorTypeSpinner.setSelection(position);
                break;
            case WATERHEATER:
                position = dhwTypeAdapter.getPosition(existingEquipment.getDhwType()); dhwTypeSpinner.setSelection(position);
                dhwEnergyFactorEditText.setText("" + existingEquipment.getDhwEnergyFactor());
                dhwTankSizeEditText.setText(""+existingEquipment.getDhwTankSize());
                break;
            case CW_PUMP:
            case HW_PUMP:
            case OTHER_PUMP:
                position = fanModulationAdapter.getPosition(existingEquipment.getPumpModulation()); pumpModulationSpinner.setSelection(position);
                break;
            case AIRHANDLER:
                position = AHUHeatingCoilAdapter.getPosition(existingEquipment.getAHU_PreheatCoil());
                AHUPreheatCoilSpinner.setSelection(position);
                position = AHUHeatingCoilAdapter.getPosition(existingEquipment.getAHU_HeatingCoil());
                AHUHeatingCoilSpinner.setSelection(position);
                position = AHUCoolingCoilAdapter.getPosition(existingEquipment.getAHU_CoolingCoil());
                AHUCoolingCoilSpinner.setSelection(position);
                AHUTotalCFMEditText.setText(existingEquipment.getAHU_TotalCFM());
            case PACKAGEUNIT:
                position = AHUEconomizerStatusAdapter.getPosition(existingEquipment.getAHU_Economizer()); AHUEconomizerStatusSpinner.setSelection(position);
                AHUEconomizerLockoutEditText.setText(existingEquipment.getAHU_EconomizerLockout());
                position = AHUEconomizerDamperAdapter.getPosition(existingEquipment.getAHU_EconomizerDamperControl()); AHUEconomizerDamperControlSpinner.setSelection(position);
                break;
            case TRANSFORMER:
                onPeakPercentEditText.setText(""+existingEquipment.getOnPeakPercent());
                offPeakPercentEditText.setText(""+existingEquipment.getOffPeakPercent());
                onPeakDaysEditText.setText(""+existingEquipment.getOnPeakDays());
                powerFactorEditText.setText(""+existingEquipment.getPowerFactor());
                break;
            case REFRIGERATION_EVAPORATOR:
                refrigTempEditText.setText(existingEquipment.getRefrigTemp());
                position = motorTypeAdapter.getPosition(existingEquipment.getMotorType()); motorTypeSpinner.setSelection(position);
                break;
            case REFRIGERATION_CONDENSING:
                position = refrigAssociatedAdapter.getPosition(existingEquipment.getRefrigAssociated()); refrigAssociatedSpinner.setSelection(position);
                break;
            default:
        }

        //heating
        f = Function.HEATING;
        //source
        position = heatingSourceAdapter.getPosition(existingEquipment.getSource(f).toString()); heatingSourceSpinner.setSelection(position);
        position = utilityAdapter.getPosition(existingEquipment.getFuelType(f).toString()); heatingUtilitySpinner.setSelection(position);
        heatingCapacityEditText.setText("" + existingEquipment.getCapacityInUnits(f));
        position = capacityUnitsAdapter.getPosition(existingEquipment.getCapacityUnits(f).toString()); heatingCapacityUnitsSpinner.setSelection(position);
        heatingEfficiencyEditText.setText("" + existingEquipment.getEfficiencyInUnits(f));
        //efficiency units
        position = efficiencyUnitsAdapter.getPosition(existingEquipment.getEfficiencyUnits(f).toString()); heatingEfficiencyUnitsSpinner.setSelection(position);
        //hours
        heatingHoursEditText.setText("" + existingEquipment.getHours(f));

        //cooling
        f = Function.COOLING;
        position = coolingSourceAdapter.getPosition(existingEquipment.getSource(f).toString());
        coolingSourceSpinner.setSelection(position);
        position = utilityAdapter.getPosition(existingEquipment.getFuelType(f).toString());
        coolingUtilitySpinner.setSelection(position);
        coolingCapacityEditText.setText("" + existingEquipment.getCapacityInUnits(f));
        position = capacityUnitsAdapter.getPosition(existingEquipment.getCapacityUnits(f).toString());
        coolingCapacityUnitsSpinner.setSelection(position);
        coolingEfficiencyEditText.setText("" + existingEquipment.getEfficiencyInUnits(f));
        position = efficiencyUnitsAdapter.getPosition(existingEquipment.getEfficiencyUnits(f).toString());
        coolingEfficiencyUnitsSpinner.setSelection(position);
        coolingHoursEditText.setText("" + existingEquipment.getHours(f));

        //supply
        f = Function.SUPPLY_FAN;
        position = fanModulationAdapter.getPosition(existingEquipment.getSource(f).toString());
        supplyFanModulationSpinner.setSelection(position);
        position = utilityAdapter.getPosition(existingEquipment.getFuelType(f).toString());
        supplyUtilitySpinner.setSelection(position);
        supplyCapacityEditText.setText("" + existingEquipment.getCapacityInUnits(f));
        position = capacityUnitsAdapter.getPosition(existingEquipment.getCapacityUnits(f).toString());
        supplyCapacityUnitsSpinner.setSelection(position);
        supplyEfficiencyEditText.setText("" + existingEquipment.getEfficiencyInUnits(f));
        position = efficiencyUnitsAdapter.getPosition(existingEquipment.getEfficiencyUnits(f).toString());
        supplyEfficiencyUnitsSpinner.setSelection(position);
        supplyHoursEditText.setText("" + existingEquipment.getHours(f));

        //return
        f = Function.RETURN_FAN;
        position = fanModulationAdapter.getPosition(existingEquipment.getSource(f).toString());
        returnFanModulationSpinner.setSelection(position);
        position = utilityAdapter.getPosition(existingEquipment.getFuelType(f).toString());
        returnUtilitySpinner.setSelection(position);
        returnCapacityEditText.setText("" + existingEquipment.getCapacityInUnits(f));
        position = capacityUnitsAdapter.getPosition(existingEquipment.getCapacityUnits(f).toString());
        returnCapacityUnitsSpinner.setSelection(position);
        returnEfficiencyEditText.setText("" + existingEquipment.getEfficiencyInUnits(f));
        position = efficiencyUnitsAdapter.getPosition(existingEquipment.getEfficiencyUnits(f).toString());
        returnEfficiencyUnitsSpinner.setSelection(position);
        returnHoursEditText.setText("" + existingEquipment.getHours(f));

        //other
        f = Function.OTHER;
        position = utilityAdapter.getPosition(existingEquipment.getFuelType(f).toString());
        otherUtilitySpinner.setSelection(position);
        otherCapacityEditText.setText("" + existingEquipment.getCapacityInUnits(f));
        position = capacityUnitsAdapter.getPosition(existingEquipment.getCapacityUnits(f).toString());
        otherCapacityUnitsSpinner.setSelection(position);
        otherEfficiencyEditText.setText("" + existingEquipment.getEfficiencyInUnits(f));
        position = efficiencyUnitsAdapter.getPosition(existingEquipment.getEfficiencyUnits(f).toString());
        otherEfficiencyUnitsSpinner.setSelection(position);
        otherHoursEditText.setText("" + existingEquipment.getHours(f));
    }

    //sets view with envelope information provided
    public void setupExistingEnvelope(EnvelopeInformation envelopeInformation) {

        envelopeLocationText.setText(envelopeInformation.getLocation());

        envelopeHeight.setText("" + envelopeInformation.getHeight());
        envelopeRValue.setText("" + envelopeInformation.getRValue());
        windowType.setSelection(windowTypeAdapter.getPosition(envelopeInformation.getWindowOperatingType()));
        envelopeColor.setText("" + envelopeInformation.getColor());
        envelopeNotes.setText("" + envelopeInformation.getNotes());
        InsulationType.setSelection(InsulationTypeAdapter.getPosition(envelopeInformation.getInsulationType()));
        envelopeDoorType.setSelection(envelopeDoorTypeAdapter.getPosition(envelopeInformation.getDoorMaterial()));
        windowGlassType.setSelection(windowGlassTypeAdapter.getPosition(envelopeInformation.getGlassType()));
        envelopeWidth.setText("" + envelopeInformation.getWidth());
        envelopeDoorMaterial.setText("" + envelopeInformation.getDoorMaterial());
        envelopeQuantity.setText("" + envelopeInformation.getQuantity());
        EnvelopeWindowOperatingType.setSelection(EnvelopeWindowOperatingTypeAdapter.getPosition(envelopeInformation.getFrameType()));

    }
    //getExistingEnvelope
    public EnvelopeInformation getEnvelopeInput(EquipmentType equipmentType) {
        EnvelopeInformation envelopeInformation = new EnvelopeInformation();

        envelopeInformation.setEquipmentType(equipmentType);
        envelopeInformation.setLocation(envelopeLocationText.getText().toString());
        envelopeInformation.setNotes(envelopeNotes.getText().toString());

        if (!mSimpleView) {

            envelopeInformation.setHeight(GeneralFunctions.StringToDouble(envelopeHeight.getText().toString(),Strings.ERROR_PROCESSING_START + "Envelope Height"));
            envelopeInformation.setRValue(GeneralFunctions.StringToDouble(envelopeRValue.getText().toString(),Strings.ERROR_PROCESSING_START + "Envelope R Value"));
            envelopeInformation.setWindowOperatingType((String) windowType.getSelectedItem());
            envelopeInformation.setColor(envelopeColor.getText().toString());
            envelopeInformation.setNotes(envelopeNotes.getText().toString());
            envelopeInformation.setInsulationType((String) InsulationType.getSelectedItem());
            envelopeInformation.setDoorMaterial((String) envelopeDoorType.getSelectedItem());
            envelopeInformation.setGlassType((String) windowGlassType.getSelectedItem());
            envelopeInformation.setWidth(GeneralFunctions.StringToDouble(envelopeWidth.getText().toString(), Strings.ERROR_PROCESSING_START + "Envelope Length"));
            envelopeInformation.setDoorMaterial(envelopeDoorMaterial.getText().toString());
            envelopeInformation.setQuantity(GeneralFunctions.StringToInt(envelopeQuantity.getText().toString(), Strings.ERROR_PROCESSING_START + "Envelope Qty"));
            envelopeInformation.setFrameType((String) EnvelopeWindowOperatingType.getSelectedItem());
        }

        return envelopeInformation;
    }

    //sets water information with water information provided
    public void setupExistingWater(WaterInformation waterInformation) {

        sinkCounterView.setCurrentNumber(waterInformation.getSinkQuantity());
        toiletCounterView.setCurrentNumber(waterInformation.getToiletQuantity());
        urinalCounterView.setCurrentNumber(waterInformation.getUrinalQuantity());
        sinkCounterView2.setCurrentNumber(waterInformation.getSink2Quantity());
        showerCounterView.setCurrentNumber(waterInformation.getShowerQuantity());
        waterLocation.setText(waterInformation.getLocation());
        waterNotes.setText(waterInformation.getNotes());
        urinalFlushRateText.setText("" + waterInformation.getUrinalFlowRate());
        toiletFlushRateText.setText("" + waterInformation.getToiletFlowRate());
        sinkFlowRateText.setText("" + waterInformation.getSinkFlowRate());
        sinkFlowRateText2.setText("" + waterInformation.getSink2FlowRate());
        showerFlowRateText.setText("" + waterInformation.getShowerFlowRate());

    }

    //getExistingWater
    public WaterInformation getWaterInput() {

        WaterInformation waterInformation = new WaterInformation();
        waterInformation.setLocation(waterLocation.getText().toString());

        if(!mSimpleView) {
            waterInformation.setSinkQuantity(sinkCounterView.getCurrentNumber());
            waterInformation.setSink2Quantity(sinkCounterView2.getCurrentNumber());
            waterInformation.setShowerQuantity(showerCounterView.getCurrentNumber());
            waterInformation.setToiletQuantity(toiletCounterView.getCurrentNumber());
            waterInformation.setUrinalQuantity(urinalCounterView.getCurrentNumber());
            waterInformation.setNotes(waterNotes.getText().toString());
            waterInformation.setUrinalFlowRate((urinalFlushRateText.getText().toString()));
            waterInformation.setToiletFlowRate((toiletFlushRateText.getText().toString()));
            waterInformation.setSinkFlowRate(sinkFlowRateText.getText().toString());
            waterInformation.setSink2FlowRate(sinkFlowRateText2.getText().toString());
            waterInformation.setShowerFlowRate(showerFlowRateText.getText().toString());
        }

        return waterInformation;
    }

    public void setupExistingMeter(UtilityMeterInformation um) {
        addUtilityMeterView.setupExistingUtilityMeter(um);
    }

    public UtilityMeterInformation getMeter() {
        return addUtilityMeterView.getUtilityMeterInput();
    }

    /**This function clears all text from the active window
     *
     */
    public void clearEquipmentWindow() {

        heatingCapacityEditText.setText("");
        heatingEfficiencyEditText.setText("");
        heatingHoursEditText.setText("");
        coolingCapacityEditText.setText("");
        coolingEfficiencyEditText.setText("");
        coolingHoursEditText.setText("");
        supplyCapacityEditText.setText("");
        supplyEfficiencyEditText.setText("");
        supplyHoursEditText.setText("");
        returnCapacityEditText.setText("");
        returnEfficiencyEditText.setText("");
        returnHoursEditText.setText("");
        otherCapacityEditText.setText("");
        otherEfficiencyEditText.setText("");
        otherHoursEditText.setText("");
        unitIdEditText.setText("");
        modelEditText.setText("");
        serialEditText.setText("");
        installYearEditText.setText("");
        quantityEditText.setText("1");
        locationEditText.setText("");
        servesEditText.setText("");
        scheduleEditText.setText("");
        notesEditText.setText("");
        makeTextView.setText("");
        dhwTankSizeEditText.setText("");
        dhwEnergyFactorEditText.setText("");
    }

    public void clearLightingWindow() {
        locationLightingEditText.setText("");
        fixtureQuantityEditText.setText("");
        lampQuantityEditText.setText("");
        lightingHoursEditText.setText("");
        lightingNotesEditText.setText("");
    }

    public void clearEnvelopeWindow() {

        envelopeLocationText.setText("");
        envelopeHeight.setText("");
        envelopeRValue.setText("");
        envelopeColor.setText("");
        envelopeNotes.setText("");
        envelopeWidth.setText("");
        envelopeDoorMaterial.setText("");
        envelopeQuantity.setText("1");

    }

    public void clearWaterWindow() {

        sinkCounterView.setCurrentNumber(0);
        toiletCounterView.setCurrentNumber(0);
        urinalCounterView.setCurrentNumber(0);
        waterLocation.setText("");
        waterNotes.setText("");
        urinalFlushRateText.setText("");
        toiletFlushRateText.setText("");
        sinkFlowRateText.setText("");
    }

    public void clearMeterWindow() {
        addUtilityMeterView.clearUtilityMeterWindow();
    }

    public void setupView(EquipmentType eType) {

       equipmentView.setVisibility(View.GONE);
        lightingView.setVisibility(View.GONE);
        ahuView.setVisibility(View.GONE);
        boilerView.setVisibility(View.GONE);
        chillerView.setVisibility(View.GONE);
        dhwView.setVisibility(View.GONE);
        packageView.setVisibility(View.GONE);
        pumpView.setVisibility(View.GONE);
        refrigCondensingView.setVisibility(View.GONE);
        refrigEvapView.setVisibility(View.GONE);
        transformerView.setVisibility(View.GONE);
        envelopeView.setVisibility(View.GONE);
        waterView.setVisibility(View.GONE);
        addUtilityMeterView.setVisibility(View.GONE);
        heatingView.setVisibility(View.GONE);
        coolingView.setVisibility(View.GONE);
        supplyView.setVisibility(View.GONE);
        returnView.setVisibility(View.GONE);
        otherView.setVisibility(View.GONE);

        switch (eType.getBuildingItemType()) {
            case EQUIPMENT:
                equipmentView.setVisibility(View.VISIBLE);
                if (mSimpleView) {
                    //set most stuff invisible
                    equipmentUnsimple.setVisibility(View.GONE);
                } else {
                    switch (eType) {
                        case BOILER:
                            boilerView.setVisibility(View.VISIBLE);
                            break;
                        case CHILLER:
                            chillerView.setVisibility(View.VISIBLE);
                            break;
                        case WATERHEATER:
                            dhwView.setVisibility(View.VISIBLE);
                            break;
                        case CW_PUMP:
                        case HW_PUMP:
                        case OTHER_PUMP:
                            pumpView.setVisibility(View.VISIBLE);
                            break;
                        case AIRHANDLER:
                            ahuView.setVisibility(View.VISIBLE);
                        case PACKAGEUNIT:
                            packageView.setVisibility(View.VISIBLE);
                            break;
                        case TRANSFORMER:
                            transformerView.setVisibility(View.VISIBLE);
                            break;
                    } //end switch

                    for (Function function : Function.values()) {
                        if (eType.getHasByFunction(function) && !mSimpleView) {
                            DefaultAllocationSettings dAS = eType.getSettings(function);
                            int position = utilityAdapter.getPosition(dAS.getFuelType().toString());
                            int position2 = capacityUnitsAdapter.getPosition(dAS.getPowerUnits().toString());
                            int position3 = efficiencyUnitsAdapter.getPosition(dAS.getEfficiencyUnits().toString());
                            switch (function) {
                                case HEATING:
                                    heatingView.setVisibility(View.VISIBLE);
                                    heatingUtilitySpinner.setSelection(position);
                                    heatingCapacityUnitsSpinner.setSelection(position2);
                                    heatingEfficiencyUnitsSpinner.setSelection(position3);
                                    break;
                                case COOLING:
                                    coolingView.setVisibility(View.VISIBLE);
                                    coolingUtilitySpinner.setSelection(position);
                                    coolingCapacityUnitsSpinner.setSelection(position2);
                                    coolingEfficiencyUnitsSpinner.setSelection(position3);
                                    break;
                                case SUPPLY_FAN:
                                    supplyView.setVisibility(View.VISIBLE);
                                    supplyUtilitySpinner.setSelection(position);
                                    supplyCapacityUnitsSpinner.setSelection(position2);
                                    supplyEfficiencyUnitsSpinner.setSelection(position3);
                                    break;
                                case RETURN_FAN:
                                    returnView.setVisibility(View.VISIBLE);
                                    returnUtilitySpinner.setSelection(position);
                                    returnCapacityUnitsSpinner.setSelection(position2);
                                    returnEfficiencyUnitsSpinner.setSelection(position3);
                                    break;
                                case OTHER:
                                    otherView.setVisibility(View.VISIBLE);
                                    otherUtilitySpinner.setSelection(position);
                                    otherCapacityUnitsSpinner.setSelection(position2);
                                    otherEfficiencyUnitsSpinner.setSelection(position3);
                                    break;
                            }
                        }
                    } //end for
                }
                break;
            case LIGHT:
                lightingView.setVisibility(View.VISIBLE);
                if (mSimpleView) {
                    //make stuff invisible
                    lightingUnsimple.setVisibility(View.GONE);
                }
                break;
            case ENVELOPE:
                envelopeView.setVisibility(View.VISIBLE);
                doorView.setVisibility(View.GONE);
                windowView.setVisibility(View.GONE);
                switch(eType) {
                    case DOOR:
                        doorView.setVisibility(View.VISIBLE);
                        break;
                    case WINDOW:
                        windowView.setVisibility(View.VISIBLE);
                        break;
                }
                if (mSimpleView) {
                    //make stuff invisible
                    envelopeUnsimple.setVisibility(View.GONE);
                }
                break;
            case WATER_FIXTURE:
                waterView.setVisibility(View.VISIBLE);
                if (mSimpleView) {
                    //make stuff invisible
                    waterUnsimple.setVisibility(View.GONE);
                }
                break;
            case UTILITYMETER:
                addUtilityMeterView.setVisibility(View.VISIBLE);
                if (mSimpleView) {
                    addUtilityMeterView.setSimpleView(true);
                }
            default:
        }

        //set allocation categories to GONE if they shouldn't be shown and show them if they should be
        //set default units

    }

}
