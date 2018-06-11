package display.Audit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.EEN.Audit.R;
import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Strings;
import fsTablesClasses.EnvelopeInformation;
import fsTablesClasses.EquipmentInformation;
import fsTablesClasses.LightingInformation;
import fsTablesClasses.UtilityMeterInformation;
import fsTablesClasses.WaterInformation;
import fsTablesEnums.CoolingSource;
import fsTablesEnums.EquipmentType;
import fsTablesEnums.FanSpeedModulation;
import fsTablesEnums.Function;
import fsTablesEnums.HeatingSource;

/**
 * This class generates the equipment table fragment
 * 
 * @author Steven Wancewicz
 * @version 2014-1-29
 * 
 */
public class EquipmentTableFragment extends Fragment{
	
	View view;
	LayoutInflater inflater;
	Spinner equipmentSpinner;
	EquipmentAdapter baseTableAdapter;
	TableFixHeaders tableFixHeaders;
	boolean hasData;
	private Function f;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        try {
            MainActivity.db.getActiveBuilding();
            MainActivity.db.getActiveEquipmentTypeAndCount();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            ((MainActivity) this.getActivity()).showAddBuilding();
        } catch (IndexOutOfBoundsException iob) {
            iob.printStackTrace();
            ((MainActivity) this.getActivity()).showAddEquipment();
        }

		view = inflater.inflate(R.layout.table_master, container, false);

		tableFixHeaders = (TableFixHeaders) view.findViewById(R.id.table);
		
		//add equipment spinner
		equipmentSpinner = (Spinner) (view.findViewById(R.id.spinnerEquipment2));
		ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item, MainActivity.db.getEquipmentCountListByType());
		equipmentSpinner.setAdapter(equipmentAdapter);
		
		//Log.v("EquipmentTableFragment", "Before adapter created");		
		
		try {
			baseTableAdapter = new EquipmentAdapter(this.getActivity(), inflater);
			tableFixHeaders.setAdapter(baseTableAdapter);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		equipmentSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (equipmentSpinner.getSelectedItem().equals(Strings.NO_EQUIPMENT) || equipmentSpinner.getSelectedItem().equals(Strings.NO_BUILDING)) {}		
				else {
					try {
						baseTableAdapter.setupColumns();
						baseTableAdapter.notifyDataSetChanged();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {			
			}
		});
		
		//Log.v("EquipmentTableFragment", "After adapter created");		

		return view;
	}
	
	public void redoTable() {

	}
	
	public EquipmentTableFragment() {
		//Log.v("EquipmentTableFragment", "Fragment created");
	}
	
	public class EquipmentAdapter extends BaseTableAdapter {

		private final float density;
		private List<EquipmentInformation> equipment;
		private List<LightingInformation> lights;
        private List<WaterInformation> waters;
        private List<EnvelopeInformation> envelopes;
        private List<UtilityMeterInformation> meters;
		private int numColumns;
		private ArrayList<String> headers = new ArrayList<String>();
		private int COLUMN_UNITID;
		private int COLUMN_QTY;
		private int COLUMN_MAKE;
		private int COLUMN_MODEL;
		private int COLUMN_SERIAL;
		private int COLUMN_INSTALLYEAR;
		private int COLUMN_LOCATION;
		private int COLUMN_SERVES;
		private int COLUMN_CONTROL;
		private int COLUMN_SCHEDULE;
		private int COLUMN_NOTES;
		
		private int LCOLUMN_LOCATION;
		private int LCOLUMN_FIXTURE_QTY;
		private int LCOLUMN_LAMPTYPE;
		private int LCOLUMN_LAMP_QTY;
		private int LCOLUMN_CONTROL;
		private int LCOLUMN_HOURS;
		private int LCOLUMN_NOTES;

        private int ECOLUMN_LOCATION;
        private int ECOLUMN_HEIGHT;
        private int ECOLUMN_RVALUE;
        private int ECOLUMN_WINDOW_TYPE;
        private int ECOLUMN_COLOR;
        private int ECOLUMN_NOTES;
        private int ECOLUMN_INSULALTION_TYPE;
        private int ECOLUMN_DOOR_TYPE;
        private int ECOLUMN_GLASS_TYPE;
        private int ECOLUMN_LENGTH;
        private int ECOLUMN_DOOR_MATERIAL;
        private int ECOLUMN_QUANTITY;
        private int ECOLUMN_FRAME_TYPE;

        private int WCOLUMN_SINK_FLOW_RATE;
        private int WCOLUMN_SINK_FLOW_RATE_2;
        private int WCOLUMN_SHOWER_FLOW_RATE;
        private int WCOLUMN_URINAL_FLOW_RATE;
        private int WCOLUMN_TOILET_FLOW_RATE;
        private int WCOLUMN_SINK_QTY;
        private int WCOLUMN_SINK_QTY_2;
        private int WCOLUMN_SHOWER_QTY;
        private int WCOLUMN_TOILET_QTY;
        private int WCOLUMN_URINAL_QTY;
        private int WCOLUMN_LOCATION;
        private int WCOLUMN_NOTES;

        private int UMCOLUMN_METER_LOCATION;
        private int UMCOLUMN_METER_ID;
        private int UMCOLUMN_METER_READ;
        private int UMCOLUMN_UTILITY_PROVIDER;
        private int UMCOLUMN_METER_UTILITY;
        private int UMCOLUMN_METER_UNITS;
        private int UMCOLUMN_NOTES;

        private int COLUMN_ONPEAKPERCENT;
		private int COLUMN_OFFPEAKPERCENT;
		private int COLUMN_ONPEAKDAYS;
		private int COLUMN_POWERFACTOR;
		private int COLUMN_REFRIGTEMP;
		private int COLUMN_MOTORTYPE;
		private int COLUMN_DHWTYPE;
		private int COLUMN_DHWENERGYFACTOR;
        private int COLUMN_DHWTANKSIZE;
		private int COLUMN_PUMPMODULATION;
		private int COLUMN_BOILERTYPE;
		private int COLUMN_BOILERMEDIUM;
		private int COLUMN_CHILLERTYPE;
		private int COLUMN_CHILLERCONDENSERTYPE;
		private int COLUMN_CHILLEREVAPORATORTYPE;
		private int COLUMN_AHU_ECONOMIZER;
		private int COLUMN_AHU_ECONOMIZERLOCKOUT;
		private int COLUMN_AHU_ECONOMIZERDAMPERCONTROL;
		private int COLUMN_AHU_PREHEATCOIL;
		private int COLUMN_AHU_HEATINGCOIL;
		private int COLUMN_AHU_COOLINGCOIL;
		private int COLUMN_AHU_TOTALCFM;
		private int COLUMN_REFRIGASSOCIATED;
		
		private int COLUMN_HEATING_SOURCE;
		private int COLUMN_HEATING_UTILITY;
		private int COLUMN_HEATING_CAPACITY;
		private int COLUMN_HEATING_CAPACITY_UNITS;
		private int COLUMN_HEATING_EFFICIENCY;
		private int COLUMN_HEATING_EFFICIENCY_UNITS;
		private int COLUMN_HEATING_HOURS;
		
		private int COLUMN_COOLING_SOURCE;
		private int COLUMN_COOLING_UTILITY;
		private int COLUMN_COOLING_CAPACITY;
		private int COLUMN_COOLING_CAPACITY_UNITS;
		private int COLUMN_COOLING_EFFICIENCY;
		private int COLUMN_COOLING_EFFICIENCY_UNITS;
		private int COLUMN_COOLING_HOURS;
		
		private int COLUMN_SUPPLY_SOURCE;
		private int COLUMN_SUPPLY_UTILITY;
		private int COLUMN_SUPPLY_CAPACITY;
		private int COLUMN_SUPPLY_CAPACITY_UNITS;
		private int COLUMN_SUPPLY_EFFICIENCY;
		private int COLUMN_SUPPLY_EFFICIENCY_UNITS;
		private int COLUMN_SUPPLY_HOURS;
		
		private int COLUMN_RETURN_SOURCE;
		private int COLUMN_RETURN_UTILITY;
		private int COLUMN_RETURN_CAPACITY;
		private int COLUMN_RETURN_CAPACITY_UNITS;
		private int COLUMN_RETURN_EFFICIENCY;
		private int COLUMN_RETURN_EFFICIENCY_UNITS;
		private int COLUMN_RETURN_HOURS;
		
		private int COLUMN_OTHER_SOURCE;
		private int COLUMN_OTHER_UTILITY;
		private int COLUMN_OTHER_CAPACITY;
		private int COLUMN_OTHER_CAPACITY_UNITS;
		private int COLUMN_OTHER_EFFICIENCY;
		private int COLUMN_OTHER_EFFICIENCY_UNITS;
		private int COLUMN_OTHER_HOURS;
		
		private LayoutInflater inflater;
		private EquipmentType equipmentType;
		private static final int NOT_A_HEADER = -10;
		
		public EquipmentAdapter(Context context, LayoutInflater inflater) throws IOException, Exception {
				//this.context = context;
				density = context.getResources().getDisplayMetrics().density;
				this.inflater = inflater;
				this.setupColumns();
			}

		/**This function will setup the column changes
		 * @throws Exception 
		 * 
		 */
		public void setupColumns() throws Exception {
			String selectedItem = (String) equipmentSpinner.getSelectedItem();
			int nextColumnNumber = EquipmentInformation.getNumRegularColumns();
			
			if (!headers.isEmpty())
				headers.clear();
			
			try {
				selectedItem = selectedItem.substring(0, selectedItem.indexOf("(")-1);
				//Log.v("Equipment Table Fragment","selectedItem: " + "'" +selectedItem + "'");
				equipmentType = (EquipmentType.getEnumType(selectedItem));
				switch(equipmentType.getBuildingItemType()) {
                    case LIGHT:
                        lights = MainActivity.db.getSpecificLightingInformation(equipmentType);
                        break;
                    case EQUIPMENT:
				    	equipment = MainActivity.db.getSpecificEquipmentInformation(equipmentType);
                        break;
                    case ENVELOPE:
                        envelopes = MainActivity.db.getSpecificEnvelopeInformation(equipmentType);
                        break;
                    case WATER_FIXTURE:
                        waters = MainActivity.db.getSpecificWaterInformation(equipmentType);
                        break;
                    case UTILITYMETER:
                        meters = MainActivity.db.getSpecificUtilityMeterInformation(equipmentType);
                        break;
				}
				COLUMN_UNITID = NOT_A_HEADER;
				COLUMN_QTY = NOT_A_HEADER;
				COLUMN_MAKE = NOT_A_HEADER;
				COLUMN_MODEL = NOT_A_HEADER;
				COLUMN_SERIAL = NOT_A_HEADER;
				COLUMN_INSTALLYEAR = NOT_A_HEADER;
				COLUMN_LOCATION = NOT_A_HEADER;
				COLUMN_SERVES = NOT_A_HEADER;
				COLUMN_CONTROL = NOT_A_HEADER;
				COLUMN_SCHEDULE = NOT_A_HEADER;
				COLUMN_NOTES = NOT_A_HEADER;
				
				LCOLUMN_LOCATION = NOT_A_HEADER;
				LCOLUMN_FIXTURE_QTY = NOT_A_HEADER;
				LCOLUMN_LAMPTYPE = NOT_A_HEADER;
				LCOLUMN_LAMP_QTY = NOT_A_HEADER;
				LCOLUMN_CONTROL = NOT_A_HEADER;
				LCOLUMN_HOURS = NOT_A_HEADER;
				LCOLUMN_NOTES = NOT_A_HEADER;

				COLUMN_ONPEAKPERCENT = NOT_A_HEADER;
				COLUMN_OFFPEAKPERCENT = NOT_A_HEADER;
				COLUMN_ONPEAKDAYS = NOT_A_HEADER;
				COLUMN_POWERFACTOR = NOT_A_HEADER;
				COLUMN_REFRIGTEMP = NOT_A_HEADER;
				COLUMN_MOTORTYPE = NOT_A_HEADER;
				COLUMN_DHWTYPE = NOT_A_HEADER;
				COLUMN_DHWENERGYFACTOR = NOT_A_HEADER;
                COLUMN_DHWTANKSIZE = NOT_A_HEADER;
				COLUMN_PUMPMODULATION = NOT_A_HEADER;
				COLUMN_BOILERTYPE = NOT_A_HEADER;
				COLUMN_BOILERMEDIUM = NOT_A_HEADER;
				COLUMN_CHILLERTYPE = NOT_A_HEADER;
				COLUMN_CHILLERCONDENSERTYPE = NOT_A_HEADER;
				COLUMN_CHILLEREVAPORATORTYPE = NOT_A_HEADER;
				COLUMN_AHU_ECONOMIZER = NOT_A_HEADER;
				COLUMN_AHU_ECONOMIZERLOCKOUT = NOT_A_HEADER;
				COLUMN_AHU_ECONOMIZERDAMPERCONTROL = NOT_A_HEADER;
				COLUMN_AHU_PREHEATCOIL = NOT_A_HEADER;
				COLUMN_AHU_HEATINGCOIL = NOT_A_HEADER;
				COLUMN_AHU_COOLINGCOIL = NOT_A_HEADER;
				COLUMN_AHU_TOTALCFM = NOT_A_HEADER;
				COLUMN_REFRIGASSOCIATED = NOT_A_HEADER;

                ECOLUMN_LOCATION = NOT_A_HEADER;
                ECOLUMN_HEIGHT = NOT_A_HEADER;
                ECOLUMN_RVALUE = NOT_A_HEADER;
                ECOLUMN_WINDOW_TYPE = NOT_A_HEADER;
                ECOLUMN_COLOR = NOT_A_HEADER;
                ECOLUMN_NOTES = NOT_A_HEADER;
                ECOLUMN_INSULALTION_TYPE = NOT_A_HEADER;
                ECOLUMN_DOOR_TYPE = NOT_A_HEADER;
                ECOLUMN_GLASS_TYPE = NOT_A_HEADER;
                ECOLUMN_LENGTH = NOT_A_HEADER;
                ECOLUMN_DOOR_MATERIAL = NOT_A_HEADER;
                ECOLUMN_QUANTITY = NOT_A_HEADER;
                ECOLUMN_FRAME_TYPE = NOT_A_HEADER;

                WCOLUMN_SINK_FLOW_RATE = NOT_A_HEADER;
                WCOLUMN_SINK_FLOW_RATE_2 = NOT_A_HEADER;
                WCOLUMN_SHOWER_FLOW_RATE = NOT_A_HEADER;
                WCOLUMN_URINAL_FLOW_RATE = NOT_A_HEADER;
                WCOLUMN_TOILET_FLOW_RATE = NOT_A_HEADER;
                WCOLUMN_SINK_QTY = NOT_A_HEADER;
                WCOLUMN_SINK_QTY_2 = NOT_A_HEADER;
                WCOLUMN_SHOWER_QTY = NOT_A_HEADER;
                WCOLUMN_TOILET_QTY = NOT_A_HEADER;
                WCOLUMN_URINAL_QTY = NOT_A_HEADER;
                WCOLUMN_LOCATION = NOT_A_HEADER;
                WCOLUMN_NOTES = NOT_A_HEADER;

                UMCOLUMN_METER_LOCATION = NOT_A_HEADER;
                UMCOLUMN_METER_ID = NOT_A_HEADER;
                UMCOLUMN_METER_READ = NOT_A_HEADER;
                UMCOLUMN_UTILITY_PROVIDER = NOT_A_HEADER;
                UMCOLUMN_METER_UTILITY = NOT_A_HEADER;
                UMCOLUMN_METER_UNITS = NOT_A_HEADER;
                UMCOLUMN_NOTES = NOT_A_HEADER;

                switch(equipmentType.getBuildingItemType()) {
                    case LIGHT:
                        LCOLUMN_LOCATION = -1;
                        LCOLUMN_FIXTURE_QTY = 0;
                        LCOLUMN_LAMPTYPE = 1;
                        LCOLUMN_LAMP_QTY = 2;
                        LCOLUMN_CONTROL = 3;
                        LCOLUMN_HOURS = 4;
                        LCOLUMN_NOTES = 5;

                        //set column headers
                        headers.add(getResources().getString(R.string.location));
                        headers.add(getResources().getString(R.string.fixtureQuantity));
                        headers.add(getResources().getString(R.string.lampType));
                        headers.add(getResources().getString(R.string.lampQuantity));
                        headers.add(getResources().getString(R.string.lampControl));
                        headers.add(getResources().getString(R.string.lightingHours));
                        headers.add(getResources().getString(R.string.notes));
                        nextColumnNumber = 6;
                        break;
                    case EQUIPMENT:
                        COLUMN_UNITID = -1;
                        COLUMN_QTY = 0;
                        COLUMN_MAKE = 1;
                        COLUMN_MODEL = 2;
                        COLUMN_SERIAL = 3;
                        COLUMN_INSTALLYEAR = 4;
                        COLUMN_LOCATION = 5;
                        COLUMN_SERVES = 6;
                        COLUMN_CONTROL = 7;
                        COLUMN_SCHEDULE = 8;
                        COLUMN_NOTES = 9;

                        //set column headers
                        headers.add(getResources().getString(R.string.unitId));
                        headers.add(getResources().getString(R.string.quantity));
                        headers.add(getResources().getString(R.string.manufacturer));
                        headers.add(getResources().getString(R.string.model));
                        headers.add(getResources().getString(R.string.serial));
                        headers.add(getResources().getString(R.string.installYear));
                        headers.add(getResources().getString(R.string.location));
                        headers.add(getResources().getString(R.string.serves));
                        headers.add(getResources().getString(R.string.control));
                        headers.add(getResources().getString(R.string.schedule));
                        headers.add(getResources().getString(R.string.notes));

                        //equipment specific columns
                        switch (equipmentType) {

                            case BOILER:
                                COLUMN_BOILERTYPE = nextColumnNumber;
                                COLUMN_BOILERMEDIUM = nextColumnNumber + 1;
                                nextColumnNumber += 2;
                                headers.add(getResources().getString(R.string.boilerType));
                                headers.add(getResources().getString(R.string.boilerMedium));
                                break;
                            case CHILLER:
                                COLUMN_CHILLERTYPE = nextColumnNumber;
                                COLUMN_CHILLERCONDENSERTYPE = nextColumnNumber + 1;
                                COLUMN_CHILLEREVAPORATORTYPE = nextColumnNumber + 2;
                                nextColumnNumber += 3;
                                headers.add(getResources().getString(R.string.chillerType));
                                headers.add(getResources().getString(R.string.chillerCondenserType));
                                headers.add(getResources().getString(R.string.chillerEvaporatorType));
                                break;
                            case WATERHEATER:
                                COLUMN_DHWTYPE = nextColumnNumber;
                                COLUMN_DHWTANKSIZE = nextColumnNumber + 1;
                                COLUMN_DHWENERGYFACTOR = nextColumnNumber + 2;
                                nextColumnNumber += 3;
                                headers.add(getResources().getString(R.string.dhwType));
                                headers.add(getResources().getString(R.string.dhwTankSize));
                                headers.add(getResources().getString(R.string.dhwEnergyFactor));
                                break;
                            case CW_PUMP:
                            case HW_PUMP:
                            case OTHER_PUMP:
                                COLUMN_PUMPMODULATION = nextColumnNumber;
                                nextColumnNumber += 1;
                                headers.add(getResources().getString(R.string.pumpModulation));
                                break;
                            case AIRHANDLER:
                                COLUMN_AHU_PREHEATCOIL = nextColumnNumber;
                                COLUMN_AHU_HEATINGCOIL = nextColumnNumber + 1;
                                COLUMN_AHU_COOLINGCOIL = nextColumnNumber + 2;
                                COLUMN_AHU_TOTALCFM = nextColumnNumber + 3;
                                headers.add(getResources().getString(R.string.AHU_PreheatCoil));
                                headers.add(getResources().getString(R.string.AHU_HeatingCoil));
                                headers.add(getResources().getString(R.string.AHU_CoolingCoil));
                                headers.add(getResources().getString(R.string.AHU_TotalCFM));
                                nextColumnNumber += 4;
                            case PACKAGEUNIT:
                                COLUMN_AHU_ECONOMIZER = nextColumnNumber;
                                COLUMN_AHU_ECONOMIZERLOCKOUT = nextColumnNumber + 1;
                                COLUMN_AHU_ECONOMIZERDAMPERCONTROL = nextColumnNumber + 2;
                                nextColumnNumber += 3;
                                headers.add(getResources().getString(R.string.AHU_Economizer));
                                headers.add(getResources().getString(R.string.AHU_EconomizerLockout));
                                headers.add(getResources().getString(R.string.AHU_EconomizerDamperControl));
                                break;
                            case TRANSFORMER:
                                COLUMN_ONPEAKPERCENT = nextColumnNumber;
                                COLUMN_OFFPEAKPERCENT = nextColumnNumber + 1;
                                COLUMN_ONPEAKDAYS = nextColumnNumber + 2;
                                COLUMN_POWERFACTOR = nextColumnNumber + 3;
                                nextColumnNumber += 4;
                                headers.add(getResources().getString(R.string.onPeakPercent));
                                headers.add(getResources().getString(R.string.offPeakPercent));
                                headers.add(getResources().getString(R.string.onPeakDays));
                                headers.add(getResources().getString(R.string.powerFactor));
                                break;
                            case REFRIGERATION_EVAPORATOR:
                                COLUMN_REFRIGTEMP = nextColumnNumber;
                                COLUMN_MOTORTYPE = nextColumnNumber + 1;
                                nextColumnNumber += 2;
                                headers.add(getResources().getString(R.string.refrigTemp));
                                headers.add(getResources().getString(R.string.motorType));
                                break;
                            case REFRIGERATION_CONDENSING:
                                COLUMN_REFRIGASSOCIATED = nextColumnNumber;
                                nextColumnNumber += 1;
                                headers.add(getResources().getString(R.string.refrigAssociated));
                                break;
                            default:
                        }
                        //determine if heating/cooling/supply/return/other columns are needed
                        if (equipmentType.getHeating()) {
                            COLUMN_HEATING_SOURCE = nextColumnNumber;
                            COLUMN_HEATING_UTILITY = nextColumnNumber + 1;
                            COLUMN_HEATING_CAPACITY = nextColumnNumber + 2;
                            COLUMN_HEATING_CAPACITY_UNITS = nextColumnNumber + 3;
                            COLUMN_HEATING_EFFICIENCY = nextColumnNumber + 4;
                            COLUMN_HEATING_EFFICIENCY_UNITS = nextColumnNumber + 5;
                            COLUMN_HEATING_HOURS = nextColumnNumber + 6;
                            nextColumnNumber += 7;
                            headers.add(getResources().getString(R.string.heatingSource));
                            headers.add(getResources().getString(R.string.heatingUtility));
                            headers.add(getResources().getString(R.string.heatingCapacity));
                            headers.add(getResources().getString(R.string.heatingCapacityUnits));
                            headers.add(getResources().getString(R.string.heatingEfficiency));
                            headers.add(getResources().getString(R.string.heatingEfficiencyUnits));
                            headers.add(getResources().getString(R.string.heatingRunHours));
                        } else {
                            COLUMN_HEATING_SOURCE = -10;
                            COLUMN_HEATING_UTILITY = -10;
                            COLUMN_HEATING_CAPACITY = -10;
                            COLUMN_HEATING_CAPACITY_UNITS = -10;
                            COLUMN_HEATING_EFFICIENCY = -10;
                            COLUMN_HEATING_EFFICIENCY_UNITS = -10;
                            COLUMN_HEATING_HOURS = -10;
                        }

                        if (equipmentType.getCooling()) {
                            COLUMN_COOLING_SOURCE = nextColumnNumber;
                            COLUMN_COOLING_UTILITY = nextColumnNumber + 1;
                            COLUMN_COOLING_CAPACITY = nextColumnNumber + 2;
                            COLUMN_COOLING_CAPACITY_UNITS = nextColumnNumber + 3;
                            COLUMN_COOLING_EFFICIENCY = nextColumnNumber + 4;
                            COLUMN_COOLING_EFFICIENCY_UNITS = nextColumnNumber + 5;
                            COLUMN_COOLING_HOURS = nextColumnNumber + 6;
                            nextColumnNumber += 7;
                            headers.add(getResources().getString(R.string.coolingSource));
                            headers.add(getResources().getString(R.string.coolingUtility));
                            headers.add(getResources().getString(R.string.coolingCapacity));
                            headers.add(getResources().getString(R.string.coolingCapacityUnits));
                            headers.add(getResources().getString(R.string.coolingEfficiency));
                            headers.add(getResources().getString(R.string.coolingEfficiencyUnits));
                            headers.add(getResources().getString(R.string.coolingRunHours));
                        } else {
                            COLUMN_COOLING_SOURCE = -10;
                            COLUMN_COOLING_UTILITY = -10;
                            COLUMN_COOLING_CAPACITY = -10;
                            COLUMN_COOLING_CAPACITY_UNITS = -10;
                            COLUMN_COOLING_EFFICIENCY = -10;
                            COLUMN_COOLING_EFFICIENCY_UNITS = -10;
                            COLUMN_COOLING_HOURS = -10;
                        }

                        if (equipmentType.getSupply()) {
                            COLUMN_SUPPLY_SOURCE = nextColumnNumber;
                            COLUMN_SUPPLY_UTILITY = nextColumnNumber + 1;
                            COLUMN_SUPPLY_CAPACITY = nextColumnNumber + 2;
                            COLUMN_SUPPLY_CAPACITY_UNITS = nextColumnNumber + 3;
                            COLUMN_SUPPLY_EFFICIENCY = nextColumnNumber + 4;
                            COLUMN_SUPPLY_EFFICIENCY_UNITS = nextColumnNumber + 5;
                            COLUMN_SUPPLY_HOURS = nextColumnNumber + 6;
                            nextColumnNumber += 7;
                            headers.add(getResources().getString(R.string.supplyFanModulation));
                            headers.add(getResources().getString(R.string.supplyUtility));
                            headers.add(getResources().getString(R.string.supplyCapacity));
                            headers.add(getResources().getString(R.string.supplyCapacityUnits));
                            headers.add(getResources().getString(R.string.supplyEfficiency));
                            headers.add(getResources().getString(R.string.supplyEfficiencyUnits));
                            headers.add(getResources().getString(R.string.supplyRunHours));
                        } else {
                            COLUMN_SUPPLY_SOURCE = -10;
                            COLUMN_SUPPLY_UTILITY = -10;
                            COLUMN_SUPPLY_CAPACITY = -10;
                            COLUMN_SUPPLY_CAPACITY_UNITS = -10;
                            COLUMN_SUPPLY_EFFICIENCY = -10;
                            COLUMN_SUPPLY_EFFICIENCY_UNITS = -10;
                            COLUMN_SUPPLY_HOURS = -10;
                        }

                        if (equipmentType.getReturn()) {
                            COLUMN_RETURN_SOURCE = nextColumnNumber;
                            COLUMN_RETURN_UTILITY = nextColumnNumber + 1;
                            COLUMN_RETURN_CAPACITY = nextColumnNumber + 2;
                            COLUMN_RETURN_CAPACITY_UNITS = nextColumnNumber + 3;
                            COLUMN_RETURN_EFFICIENCY = nextColumnNumber + 4;
                            COLUMN_RETURN_EFFICIENCY_UNITS = nextColumnNumber + 5;
                            COLUMN_RETURN_HOURS = nextColumnNumber + 6;
                            nextColumnNumber += 7;
                            headers.add(getResources().getString(R.string.returnFanModulation));
                            headers.add(getResources().getString(R.string.returnUtility));
                            headers.add(getResources().getString(R.string.returnCapacity));
                            headers.add(getResources().getString(R.string.returnCapacityUnits));
                            headers.add(getResources().getString(R.string.returnEfficiency));
                            headers.add(getResources().getString(R.string.returnEfficiencyUnits));
                            headers.add(getResources().getString(R.string.returnRunHours));
                        } else {
                            COLUMN_RETURN_SOURCE = -10;
                            COLUMN_RETURN_UTILITY = -10;
                            COLUMN_RETURN_CAPACITY = -10;
                            COLUMN_RETURN_CAPACITY_UNITS = -10;
                            COLUMN_RETURN_EFFICIENCY = -10;
                            COLUMN_RETURN_EFFICIENCY_UNITS = -10;
                            COLUMN_RETURN_HOURS = -10;
                        }

                        if (equipmentType.getOther()) {
                            COLUMN_OTHER_SOURCE = nextColumnNumber;
                            COLUMN_OTHER_UTILITY = nextColumnNumber + 1;
                            COLUMN_OTHER_CAPACITY = nextColumnNumber + 2;
                            COLUMN_OTHER_CAPACITY_UNITS = nextColumnNumber + 3;
                            COLUMN_OTHER_EFFICIENCY = nextColumnNumber + 4;
                            COLUMN_OTHER_EFFICIENCY_UNITS = nextColumnNumber + 5;
                            COLUMN_OTHER_HOURS = nextColumnNumber + 6;
                            nextColumnNumber += 7;
                            headers.add("Other");
                            headers.add(getResources().getString(R.string.otherUtility));
                            headers.add(getResources().getString(R.string.otherCapacity));
                            headers.add(getResources().getString(R.string.otherCapacityUnits));
                            headers.add(getResources().getString(R.string.otherEfficiency));
                            headers.add(getResources().getString(R.string.otherEfficiencyUnits));
                            headers.add(getResources().getString(R.string.otherRunHours));
                        } else {
                            COLUMN_OTHER_SOURCE = -10;
                            COLUMN_OTHER_UTILITY = -10;
                            COLUMN_OTHER_CAPACITY = -10;
                            COLUMN_OTHER_CAPACITY_UNITS = -10;
                            COLUMN_OTHER_EFFICIENCY = -10;
                            COLUMN_OTHER_EFFICIENCY_UNITS = -10;
                            COLUMN_OTHER_HOURS = -10;
                        }
                        break;
                    case ENVELOPE:
                        ECOLUMN_LOCATION = -1;
                        ECOLUMN_QUANTITY = 0;
                        ECOLUMN_LENGTH = 1;
                        ECOLUMN_HEIGHT = 2;
                        ECOLUMN_RVALUE = 3;
                        ECOLUMN_COLOR = 4;
                        ECOLUMN_INSULALTION_TYPE = 5;
                        ECOLUMN_NOTES = 6;

                        headers.add("Location");
                        headers.add(getResources().getString(R.string.envelopeQuantity));
                        headers.add(getResources().getString(R.string.envelopeWidth));
                        headers.add(getResources().getString(R.string.envelopeHeight));
                        headers.add(getResources().getString(R.string.envelopeRValue));
                        headers.add(getResources().getString(R.string.envelopeColor));
                        headers.add(getResources().getString(R.string.InsulationType));

                        switch(equipmentType) {
                            case WINDOW:
                                ECOLUMN_WINDOW_TYPE = 6;
                                ECOLUMN_GLASS_TYPE = 7;
                                ECOLUMN_FRAME_TYPE = 8;
                                headers.add(getResources().getString(R.string.windowType));
                                headers.add(getResources().getString(R.string.windowGlassType));
                                headers.add(getResources().getString(R.string.EnvelopeWindowOperatingType));
                                ECOLUMN_NOTES += 3;
                                break;
                            case DOOR:
                                ECOLUMN_DOOR_TYPE = 6;
                                ECOLUMN_DOOR_MATERIAL = 7;
                                ECOLUMN_NOTES += 2;
                                headers.add(getResources().getString(R.string.envelopeDoorType));
                                headers.add(getResources().getString(R.string.envelopeDoorMaterial));
                                break;
                        }

                        headers.add(getResources().getString(R.string.envelopeNotes));

                        nextColumnNumber = ECOLUMN_NOTES+1;
                        break;
                    case WATER_FIXTURE:
                        WCOLUMN_LOCATION = -1;
                        WCOLUMN_SINK_FLOW_RATE = 0;
                        WCOLUMN_SINK_QTY = 1;
                        WCOLUMN_SINK_FLOW_RATE_2 = 2;
                        WCOLUMN_SINK_QTY_2 = 3;
                        WCOLUMN_TOILET_FLOW_RATE = 4;
                        WCOLUMN_TOILET_QTY = 5;
                        WCOLUMN_URINAL_FLOW_RATE = 6;
                        WCOLUMN_URINAL_QTY = 7;
                        WCOLUMN_SHOWER_FLOW_RATE = 8;
                        WCOLUMN_SHOWER_QTY = 9;
                        WCOLUMN_NOTES = 10;
                        headers.add(getResources().getString(R.string.location));
                        headers.add(getResources().getString(R.string.sinkFlowRate));
                        headers.add(getResources().getString(R.string.sinkQty));
                        headers.add(getResources().getString(R.string.sinkFlowRate2));
                        headers.add(getResources().getString(R.string.sinkQty2));
                        headers.add(getResources().getString(R.string.toiletFlushRate));
                        headers.add(getResources().getString(R.string.toiletQty));
                        headers.add(getResources().getString(R.string.urinalFlushRate));
                        headers.add(getResources().getString(R.string.urinalQty));
                        headers.add(getResources().getString(R.string.showerFlowRate));
                        headers.add(getResources().getString(R.string.showerQty));
                        headers.add(getResources().getString(R.string.notes));
                        nextColumnNumber = WCOLUMN_NOTES+1;
                        break;
                    case UTILITYMETER:
                        UMCOLUMN_METER_UTILITY = -1;
                        UMCOLUMN_METER_LOCATION = 0;
                        UMCOLUMN_METER_ID = 1;
                        UMCOLUMN_METER_READ = 2;
                        UMCOLUMN_UTILITY_PROVIDER = 3;
                        UMCOLUMN_METER_UNITS = 4;
                        UMCOLUMN_NOTES = 5;
                        headers.add(getResources().getString(R.string.utilityType));
                        headers.add(getResources().getString(R.string.location));
                        headers.add(getResources().getString(R.string.meterSerial));
                        headers.add(getResources().getString(R.string.meterReading));
                        headers.add(getResources().getString(R.string.utilityProvider));
                        headers.add(getResources().getString(R.string.units));
                        headers.add(getResources().getString(R.string.notes));
                        nextColumnNumber = UMCOLUMN_NOTES+1;
                        break;
                }
			} catch (StringIndexOutOfBoundsException excep) {
				excep.printStackTrace();
			}

            this.numColumns = nextColumnNumber;
		}
		
		@Override
		public int getRowCount() {
			try {
				return MainActivity.db.getSpecificEquipmentCount(equipmentType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return 0;
		}

		@Override
		public int getColumnCount() {
			
			return this.numColumns;
		}

		@Override
		public View getView(int row, int column, View convertView,
				ViewGroup parent) {
			final View view;
			//Log.v("EquipmentTableFragment", "asked for view for " + row + "," + column);
			switch (getItemViewType(row, column)) {
				case 0:
					view = getFirstHeader(row, column, convertView, parent);
					break;
				case 1:
					view = getHeader(row, column, convertView, parent);
					break;
				case 2:
					view = getFirstBody(row, column, convertView, parent);
					break;
				case 3:
					view = getBody(row, column, convertView, parent);
					break;
				case 4:
					view = getFamilyView(row, column, convertView, parent);
					break;
				default:
					throw new RuntimeException("wtf?");
			}
			return view;
		}

		private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = this.inflater.inflate(R.layout.item_table_header_first, parent, false);
			}
			((TextView) convertView.findViewById(android.R.id.text1)).setText(headers.get(column+1));
			return convertView;
		}

		private View getHeader(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = this.inflater.inflate(R.layout.item_table_header, parent, false);
			}
			((TextView) convertView.findViewById(android.R.id.text1)).setText(headers.get(column+1));
			return convertView;
		}

		private View getFirstBody(int row, int column, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = this.inflater.inflate(R.layout.item_table_first, parent, false);
			}
			convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
			((TextView) convertView.findViewById(android.R.id.text1)).setText(getEquipmentInfo(row, column));
			return convertView;
		}

		private View getBody(int row, int column, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = this.inflater.inflate(R.layout.item_table, parent, false);
			}
			convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
			((TextView) convertView.findViewById(android.R.id.text1)).setText(getEquipmentInfo(row, column));
			return convertView;
		}
		
		private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = this.inflater.inflate(R.layout.item_table_family, parent, false);
			}
			final String string;
			if (column == -1) {
				string = "";
			} else {
				string = "";
			}
			((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
			return convertView;
		}
		
		private CharSequence getEquipmentInfo(int row, int column) {
			//Log.v("EquipmentTableFragment","Row is: " + row);
			//Log.v("EquipmentTableFragment", "Column is: " + column);
			switch (equipmentType.getBuildingItemType()) {
                case LIGHT:
                    if(column==  LCOLUMN_LOCATION) return lights.get(row).getLocation();
                    if(column==  LCOLUMN_FIXTURE_QTY) return "" + lights.get(row).getQuantity();
                    if(column==  LCOLUMN_LAMPTYPE) return lights.get(row).getLampType().toString();
                    if(column==  LCOLUMN_LAMP_QTY) return "" + lights.get(row).getLampQuantity();
                    if(column==  LCOLUMN_CONTROL) return lights.get(row).getControl();
                    if(column==  LCOLUMN_HOURS)  return "" + lights.get(row).getAnnualHours();
                    if(column==  LCOLUMN_NOTES)  return lights.get(row).getNotes();
                    break;
                case ENVELOPE:
                    if(column==ECOLUMN_LOCATION) return envelopes.get(row).getLocation();
                    if(column==ECOLUMN_QUANTITY) return "" +   envelopes.get(row).getQuantity();
                    if(column==ECOLUMN_LENGTH) return "" +   envelopes.get(row).getWidth();
                    if(column==ECOLUMN_HEIGHT) return "" +   envelopes.get(row).getHeight();
                    if(column==ECOLUMN_RVALUE) return "" +   envelopes.get(row).getRValue();
                    if(column==ECOLUMN_COLOR) return   envelopes.get(row).getColor();
                    if(column==ECOLUMN_INSULALTION_TYPE) return   envelopes.get(row).getInsulationType();
                    if(column==ECOLUMN_NOTES) return   envelopes.get(row).getNotes();
                    if(column==ECOLUMN_WINDOW_TYPE) return   envelopes.get(row).getWindowOperatingType();
                    if(column==ECOLUMN_GLASS_TYPE) return   envelopes.get(row).getGlassType();
                    if(column==ECOLUMN_FRAME_TYPE) return   envelopes.get(row).getFrameType();
                    if(column==ECOLUMN_DOOR_TYPE) return   envelopes.get(row).getDoorMaterial();
                    if(column==ECOLUMN_DOOR_MATERIAL) return   envelopes.get(row).getDoorMaterial();
                    break;
                case WATER_FIXTURE:
                    if(column== WCOLUMN_SINK_FLOW_RATE) return "" + waters.get(row).getSinkFlowRate();
                    if(column== WCOLUMN_SINK_FLOW_RATE_2) return "" + waters.get(row).getSink2FlowRate();
                    if(column== WCOLUMN_SHOWER_FLOW_RATE) return "" + waters.get(row).getShowerFlowRate();
                    if(column== WCOLUMN_URINAL_FLOW_RATE) return "" + waters.get(row).getUrinalFlowRate();
                    if(column== WCOLUMN_TOILET_FLOW_RATE) return "" + waters.get(row).getToiletFlowRate();
                    if(column== WCOLUMN_SINK_QTY) return "" + waters.get(row).getSinkQuantity();
                    if(column== WCOLUMN_SINK_QTY_2) return "" + waters.get(row).getSink2Quantity();
                    if(column== WCOLUMN_SHOWER_QTY) return "" + waters.get(row).getShowerQuantity();
                    if(column== WCOLUMN_TOILET_QTY) return "" + waters.get(row).getToiletQuantity();
                    if(column== WCOLUMN_URINAL_QTY) return "" + waters.get(row).getUrinalQuantity();
                    if(column== WCOLUMN_LOCATION) return waters.get(row).getLocation();
                    if(column== WCOLUMN_NOTES) return waters.get(row).getNotes();
                    break;
                case UTILITYMETER:
                    if(column==UMCOLUMN_METER_LOCATION) return   meters.get(row).getLocation();
                    if(column==UMCOLUMN_METER_ID) return   meters.get(row).getMeterSerial();
                    if(column==UMCOLUMN_METER_READ) return "" +   meters.get(row).getMeterReading();
                    if(column==UMCOLUMN_UTILITY_PROVIDER) return   meters.get(row).getMeterUtilityProvider();
                    if(column==UMCOLUMN_METER_UTILITY) return   meters.get(row).getMeterUtilityType();
                    if(column==UMCOLUMN_METER_UNITS) return   meters.get(row).getMeterUtilityUnits();
                    if(column==UMCOLUMN_NOTES) return   meters.get(row).getMeterNotes();
                    break;
                case EQUIPMENT:
                    if(column==COLUMN_UNITID) return equipment.get(row).getUnitID();
                    if(column==COLUMN_QTY) return "" + equipment.get(row).getQuantity();
                    if(column==COLUMN_MAKE) return equipment.get(row).getManufacturer();
                    if(column==COLUMN_MODEL) return equipment.get(row).getModelNumber();
                    if(column==COLUMN_SERIAL) return equipment.get(row).getSerialNumber();
                    if(column==COLUMN_INSTALLYEAR) return equipment.get(row).getInstallYear();
                    if(column==COLUMN_LOCATION) return equipment.get(row).getLocation();
                    if(column==COLUMN_SERVES) return equipment.get(row).getServes();
                    if(column==COLUMN_CONTROL) return equipment.get(row).getControl();
                    if(column==COLUMN_SCHEDULE) return equipment.get(row).getSchedule();
                    if(column==COLUMN_NOTES) return equipment.get(row).getNotes();
                    if(column== COLUMN_ONPEAKPERCENT) return "" + equipment.get(row).getOnPeakPercent();
                    if(column== COLUMN_OFFPEAKPERCENT) return "" + equipment.get(row).getOffPeakPercent();
                    if(column== COLUMN_ONPEAKDAYS) return "" + equipment.get(row).getOnPeakDays();
                    if(column== COLUMN_POWERFACTOR) return "" + equipment.get(row).getPowerFactor();
                    if(column== COLUMN_REFRIGTEMP) return equipment.get(row).getRefrigTemp();
                    if(column== COLUMN_MOTORTYPE) return equipment.get(row).getMotorType();
                    if(column== COLUMN_DHWTYPE) return equipment.get(row).getDhwType();
                    if(column== COLUMN_DHWTANKSIZE) return equipment.get(row).getDhwTankSize();
                    if(column== COLUMN_DHWENERGYFACTOR) return "" + equipment.get(row).getDhwEnergyFactor();
                    if(column== COLUMN_PUMPMODULATION) return equipment.get(row).getPumpModulation();
                    if(column== COLUMN_BOILERTYPE) return equipment.get(row).getBoilerType();
                    if(column== COLUMN_BOILERMEDIUM) return equipment.get(row).getBoilerMedium();
                    if(column== COLUMN_CHILLERTYPE) return equipment.get(row).getChillerType();
                    if(column== COLUMN_CHILLERCONDENSERTYPE) return equipment.get(row).getChillerCondenserType();
                    if(column== COLUMN_CHILLEREVAPORATORTYPE) return equipment.get(row).getChillerEvaporatorType();
                    if(column== COLUMN_AHU_ECONOMIZER) return equipment.get(row).getAHU_Economizer();
                    if(column== COLUMN_AHU_ECONOMIZERLOCKOUT) return equipment.get(row).getAHU_EconomizerLockout();
                    if(column== COLUMN_AHU_ECONOMIZERDAMPERCONTROL) return equipment.get(row).getAHU_EconomizerDamperControl();
                    if(column== COLUMN_AHU_PREHEATCOIL) return equipment.get(row).getAHU_PreheatCoil();
                    if(column== COLUMN_AHU_HEATINGCOIL) return equipment.get(row).getAHU_HeatingCoil();
                    if(column== COLUMN_AHU_COOLINGCOIL) return equipment.get(row).getAHU_CoolingCoil();
                    if(column== COLUMN_AHU_TOTALCFM) return equipment.get(row).getAHU_TotalCFM();
                    if(column== COLUMN_REFRIGASSOCIATED) return equipment.get(row).getRefrigAssociated();

                    f = Function.HEATING;
                    if(column == COLUMN_HEATING_SOURCE)
                        return equipment.get(row).getSource(f);
                    else if(column == COLUMN_HEATING_UTILITY)
                        return ((equipment.get(row).getSource(f).equals(HeatingSource.NO_HEATING.name())) ? Strings.NA : equipment.get(row).getFuelType(f).toString() );
                    else if(column == COLUMN_HEATING_CAPACITY)
                        return ((equipment.get(row).getSource(f).equals(HeatingSource.NO_HEATING.name())) ? Strings.NA : "" + equipment.get(row).getCapacityInUnits(f));
                    else if(column == COLUMN_HEATING_CAPACITY_UNITS)
                        return ((equipment.get(row).getSource(f).equals(HeatingSource.NO_HEATING.name())) ? Strings.NA : equipment.get(row).getCapacityUnits(f).toString());
                    else if(column == COLUMN_HEATING_EFFICIENCY)
                        return ((equipment.get(row).getSource(f).equals(HeatingSource.NO_HEATING.name())) ? Strings.NA : "" + equipment.get(row).getEfficiencyInUnits(f));
                    else if(column == COLUMN_HEATING_EFFICIENCY_UNITS)
                        return ((equipment.get(row).getSource(f).equals(HeatingSource.NO_HEATING.name())) ? Strings.NA : equipment.get(row).getEfficiencyUnits(f).toString());
                    else if(column == COLUMN_HEATING_HOURS)
                        return ((equipment.get(row).getSource(f).equals(HeatingSource.NO_HEATING.name())) ? Strings.NA : "" + equipment.get(row).getHours(f));

                    f = Function.COOLING;
                    if(column == COLUMN_COOLING_SOURCE)
                        return equipment.get(row).getSource(f);
                    else if(column == COLUMN_COOLING_UTILITY)
                        return ((equipment.get(row).getSource(f).equals(CoolingSource.NO_COOLING.name())) ? Strings.NA : equipment.get(row).getFuelType(f).toString());
                    else if(column == COLUMN_COOLING_CAPACITY)
                        return ((equipment.get(row).getSource(f).equals(CoolingSource.NO_COOLING.name())) ? Strings.NA : "" + equipment.get(row).getCapacityInUnits(f));
                    else if(column == COLUMN_COOLING_CAPACITY_UNITS)
                        return ((equipment.get(row).getSource(f).equals(CoolingSource.NO_COOLING.name())) ? Strings.NA : equipment.get(row).getCapacityUnits(f).toString());
                    else if(column == COLUMN_COOLING_EFFICIENCY)
                        return ((equipment.get(row).getSource(f).equals(CoolingSource.NO_COOLING.name())) ? Strings.NA : "" + equipment.get(row).getEfficiencyInUnits(f));
                    else if(column == COLUMN_COOLING_EFFICIENCY_UNITS)
                        return ((equipment.get(row).getSource(f).equals(CoolingSource.NO_COOLING.name())) ? Strings.NA : equipment.get(row).getEfficiencyUnits(f).toString());
                    else if(column == COLUMN_COOLING_HOURS)
                        return ((equipment.get(row).getSource(f).equals(CoolingSource.NO_COOLING.name())) ? Strings.NA : "" + equipment.get(row).getHours(f));

                    f = Function.SUPPLY_FAN;
                    if(column == COLUMN_SUPPLY_SOURCE)
                        return equipment.get(row).getSource(f).toString();
                    else if(column == COLUMN_SUPPLY_UTILITY)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : equipment.get(row).getFuelType(f).toString());
                    else if(column == COLUMN_SUPPLY_CAPACITY)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : "" + equipment.get(row).getCapacityInUnits(f));
                    else if(column == COLUMN_SUPPLY_CAPACITY_UNITS)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : equipment.get(row).getCapacityUnits(f).toString());
                    else if(column == COLUMN_SUPPLY_EFFICIENCY)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : "" + equipment.get(row).getEfficiencyInUnits(f));
                    else if(column == COLUMN_SUPPLY_EFFICIENCY_UNITS)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : equipment.get(row).getEfficiencyUnits(f).toString());
                    else if(column == COLUMN_SUPPLY_HOURS) {
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : "" + equipment.get(row).getHours(f));
                    }

                    f = Function.RETURN_FAN;
                    if(column == COLUMN_RETURN_SOURCE)
                        return equipment.get(row).getSource(f);
                    else if(column == COLUMN_RETURN_UTILITY)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : equipment.get(row).getFuelType(f).toString());
                    else if(column == COLUMN_RETURN_CAPACITY)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : "" + equipment.get(row).getCapacityInUnits(f));
                    else if(column == COLUMN_RETURN_CAPACITY_UNITS)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : equipment.get(row).getCapacityUnits(f).toString());
                    else if(column == COLUMN_RETURN_EFFICIENCY)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : "" + equipment.get(row).getEfficiencyInUnits(f));
                    else if(column == COLUMN_RETURN_EFFICIENCY_UNITS)
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : equipment.get(row).getEfficiencyUnits(f).toString());
                    else if(column == COLUMN_RETURN_HOURS) {
                        return ((equipment.get(row).getSource(f).equals(FanSpeedModulation.NO_FAN.name())) ? Strings.NA : "" + equipment.get(row).getHours(f));
                    }

                    f = Function.OTHER;
                    if(column == COLUMN_OTHER_SOURCE)
                        return "";
                    else if(column == COLUMN_OTHER_UTILITY)
                        return equipment.get(row).getFuelType(f).toString();
                    else if(column == COLUMN_OTHER_CAPACITY)
                        return "" + equipment.get(row).getCapacityInUnits(f);
                    else if(column == COLUMN_OTHER_CAPACITY_UNITS)
                        return equipment.get(row).getCapacityUnits(f).toString();
                    else if(column == COLUMN_OTHER_EFFICIENCY)
                        return "" + equipment.get(row).getEfficiencyInUnits(f);
                    else if(column == COLUMN_OTHER_EFFICIENCY_UNITS)
                        return equipment.get(row).getEfficiencyUnits(f).toString();
                    else if(column == COLUMN_OTHER_HOURS)
                        return "" + equipment.get(row).getHours(f);
                    break;
            }

            Log.v("EQUIPMENTTABLEFRAGMENT", "error column #: " + column);
            throw new RuntimeException("invalid column");
		}

		@Override
		public int getWidth(int column) {
			// TODO adjust based on how it looks, but later
			int width = 100;
			if (column == COLUMN_UNITID)
				width = 60;
			else if (column == COLUMN_INSTALLYEAR)
				width = 40;
			else if (column == COLUMN_NOTES ||column == LCOLUMN_NOTES)
				width = 200;
			return (int) (width*density);
		}

		@Override
		public int getHeight(int row) {

			final int height;
				if (row == -1) {
					height = 45;
				} else {
					height = 35;
				} 
				return Math.round(height * density);
		}

		@Override
		public int getItemViewType(int row, int column) {
			final int itemViewType;
			if (row == -1 && column == -1) {
				itemViewType = 0;
			} else if (row == -1) {
				itemViewType = 1;
			} else if (column == -1) {
				itemViewType = 2;
			} else {
				itemViewType = 3;
			}
			return itemViewType;
		}

		@Override
		public int getViewTypeCount() {
			//Log.v("EquipmentTableFragment","Is it here?");
			return 5;
		}
	}

	public static String getOrderNumber() {
		return "3";
	}
}
