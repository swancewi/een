package database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import constants.GeneralFunctions;
import constants.Strings;
import conversion.EfficiencyUnits;
import conversion.PowerUnits;
import fsTablesClasses.AllocationInformation;
import fsTablesClasses.Building;
import fsTablesClasses.EnvelopeInformation;
import fsTablesClasses.EquipmentInformation;
import fsTablesClasses.Facility;
import fsTablesClasses.LightingInformation;
import fsTablesClasses.Measure;
import fsTablesClasses.MeasureSummary;
import fsTablesClasses.SiteMedia;
import fsTablesClasses.UtilityMeterInformation;
import fsTablesClasses.WaterInformation;
import fsTablesEnums.BuildingItemType;
import fsTablesEnums.EquipmentType;
import fsTablesEnums.FuelType;
import fsTablesEnums.Function;
import fsTablesEnums.LampType;
import display.subclasses.helper.Utils;

//import fsTablesEnums.AllocationCategory;

/**
 * This class creates a database to keep track of the audit information
 * 
 * @author Steven Wancewicz
 * @version 2
 * 
 */
public class DatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 4;
	
	private final String TAG = "DatabaseHandler";
	
	private static final String BUILDING_ITEM_TABLE = "buildingItem";
	private static final String EQUIPMENT_TABLE = "mEquipment";
	private static final String LIGHTING_TABLE = "lighting";
	private static final String ALLOCATION_TABLE = "allocation";
	private static final String UTILITY_TABLE = "utility";
	//private static final String UNIT_TABLE = "unit";
	private static final String BUILDING_TABLE = "building";
	private static final String ALLOCATION_SUMMARY_TABLE = "allocation_summary";
	private static final String FACILITY_TABLE = "facility";
	private static final String SETTINGS_TABLE = "settings";
	private static final String PHOTO_TABLE = "photos";
	private static final String MEASURE_TABLE = "measure";
	private static final String MEASURE_EQUIPMENT_TABLE = "measureEquipment";
	private static final String MEASURE_LIST_TABLE = "measureList";
	private static final String AUDIO_TABLE = "audioTable";
    private static final String WATER_TABLE = "mWater";
    private static final String ENVELOPE_TABLE = "envelope";
	private static final String UTILITY_METER_TABLE = "utilityMeter";
	
    private static final String DATABASE_NAME = "auditTables";
    
    private static final String KEY_BUILDINGITEM_ID = "overviewID";
	private static final String KEY_EQUIPMENT_TYPE = "equipmentType"; //KEY_NAME
	private static final String KEY_BUILDINGITEM_LOCATION = "location";
	private static final String KEY_BUILDING_FK_ID = "buildingFKID";
	private static final String KEY_BUILDINGITEM_EXISTS = "itemExists";
	
    //equipment table columns
	private static final String KEY_EQUIPMENT_ID = "equipmentID"; //KEY_ID
	private static final String KEY_MANUFACTURER = "manufacturer"; //KEY_PH_NO
	private static final String KEY_MODEL = "model";
	private static final String KEY_SERIAL_NUMBER = "serialNumber";
	private static final String KEY_INSTALL_YEAR = "installYear";
	private static final String KEY_HEATING_ALLOCATION_ID = Function.HEATING.name() + "AllocationID";
	private static final String KEY_COOLING_ALLOCATION_ID = Function.COOLING.name() + "AllocationID";
	private static final String KEY_SUPPLY_FAN_ALLOCATION_ID = Function.SUPPLY_FAN.name() + "AllocationID";
	private static final String KEY_RETURN_FAN_ALLOCATION_ID = Function.RETURN_FAN.name() + "AllocationID";
	private static final String KEY_OTHER_ALLOCATION_ID = Function.OTHER.name() + "AllocationID";
	private static final String KEY_UNIT_ID_NAME = "equipmentUnitID";
	private static final String KEY_EQUIPMENT_QTY = "quantity";
	//private static final String KEY_EQUIPMENT_LOCATION = "location";
	private static final String KEY_EQUIPMENT_SERVES = "serves";
	private static final String KEY_EQUIPMENT_CONTROL = "control";
	private static final String KEY_EQUIPMENT_SCHEDULE = "schedule";
	private static final String KEY_EQUIPMENT_NOTES = "notes";
	private static final String KEY_ONPEAKPERCENT = "onPeakPercent";
	private static final String KEY_OFFPEAKPERCENT = "offPeakPercent";
	private static final String KEY_ONPEAKDAYS = "onPeakDays";
	private static final String KEY_POWERFACTOR = "powerFactor";
	private static final String KEY_REFRIGTEMP = "refrigTemp";
	private static final String KEY_MOTORTYPE = "motorType";
	private static final String KEY_DHWTYPE = "dhwType";
	private static final String KEY_DHWENERGYFACTOR = "dhwEnergyFactor";
    private static final String KEY_DHWTANKSIZE = "dhwTankSize";
	private static final String KEY_PUMPMODULATION = "pumpModulation";
	private static final String KEY_BOILERTYPE = "boilerType";
	private static final String KEY_BOILERMEDIUM = "boilerMedium";
	private static final String KEY_CHILLERTYPE = "chillerType";
	private static final String KEY_CHILLERCONDENSERTYPE = "chillerCondenserType";
	private static final String KEY_CHILLEREVAPORATORTYPE = "chillerEvaporatorType";
	private static final String KEY_AHU_ECONOMIZER = "AHU_Economizer";
	private static final String KEY_AHU_ECONOMIZERLOCKOUT = "AHU_EconomizerLockout";
	private static final String KEY_AHU_ECONOMIZERDAMPERCONTROL = "AHU_EconomizerDamperControl";
	private static final String KEY_AHU_PREHEATCOIL = "AHU_PreheatCoil";
	private static final String KEY_AHU_HEATINGCOIL = "AHU_HeatingCoil";
	private static final String KEY_AHU_COOLINGCOIL = "AHU_CoolingCoil";
	private static final String KEY_AHU_TOTALCFM = "AHU_TotalCFM";
	private static final String KEY_REFRIGASSOCIATED = "refrigAssociated";
	
	//lighting table columns
	private static final String KEY_LIGHTING_ID = "lightingId"; 
	//private static final String KEY_LIGHTING_LOCATION = "lightLocation";
	private static final String KEY_FIXTURE_QTY = "fixtureQty";
	private static final String KEY_LAMP_TYPE = "lampType";
	private static final String KEY_LAMP_CONTROL = "lampControl";
	private static final String KEY_LAMP_HOURS = "lampHours";
	private static final String KEY_LAMP_KW = "lampKW";
	private static final String KEY_LAMP_QTY = "lampQty";
	private static final String KEY_LIGHT_NOTES = "lightNotes";

    //water item table columns
    private static final String KEY_WATER_ID = "waterId";
    private static final String KEY_WATER_SINK_FLOW_RATE = "sinkFlowRate";
    private static final String KEY_WATER_SINK_QTY = "sinkQty";
    private static final String KEY_WATER_SINK_FLOW_RATE_2 = "sinkFlowRate2";
    private static final String KEY_WATER_SINK_QTY_2 = "sinkQty2";
    private static final String KEY_WATER_SHOWER_FLOW_RATE = "showerFlowRate";
    private static final String KEY_WATER_SHOWER_QTY = "showerQty";
    private static final String KEY_WATER_TOILET_FLOW_RATE = "toiletFlowRate";
    private static final String KEY_WATER_TOILET_QTY = "toiletQty";
    private static final String KEY_WATER_URINAL_FLOW_RATE = "urinalFlowRate";
    private static final String KEY_WATER_URINAL_QTY = "urinalQty";
    private static final String KEY_WATER_NOTES = "waterNotes";
    //private static final String KEY_WATER_LOCATION = "waterLocation";

    //envelope table columns
    private static final String KEY_ENVELOPE_ID = "envelopeId";
    //private static final String KEY_ENVELOPE_LOCATION = "envelopeLocation";
    private static final String KEY_ENVELOPE_HEIGHT = "envelopeHeight";
    private static final String KEY_ENVELOPE_RVALUE = "envelopeRValue";
    private static final String KEY_ENVELOPE_WINDOW_TYPE = "envelopeWindowType";
    private static final String KEY_ENVELOPE_COLOR = "envelopeColor";
	private static final String KEY_ENVELOPE_INSULALTION_TYPE = "insulationType";
	private static final String KEY_ENVELOPE_DOOR_TYPE = "doorType";
	private static final String KEY_ENVELOPE_GLASS_TYPE = "glassType";
	private static final String KEY_ENVELOPE_LENGTH = "height";
	private static final String KEY_ENVELOPE_DOOR_MATERIAL = "doorMaterial";
	private static final String KEY_ENVELOPE_QUANTITY = "quantity";
	private static final String KEY_ENVELOPE_FRAME_TYPE = "frameType";
    private static final String KEY_ENVELOPE_NOTES = "notes";


	//allocation table columns	
	private static final String KEY_ALLOCATION_ID = "id";
	private static final String KEY_CAPACITY = "capacity";
	private static final String KEY_EFFICIENCY = "efficiency";
	private static final String KEY_UTILITY_FK_ID = "utilityID";
	//private static final String KEY_ALLOCATION_CATEGORY_FK_ID = "allocationCategoryID"; 
	private static final String KEY_RUN_HOURS = "runHours";
	private static final String KEY_DIVERSITY_FACTOR = "diversityFactor";
	private static final String KEY_EFFECIENCY_UNITS = "efficiencyUnits";
	private static final String KEY_CAPACITY_UNITS = "capacityUnits";
	private static final String KEY_SOURCE = "source";
	
	//utility table columns
	/*private static final String KEY_UTILITY_ID = "id";*/
	private static final String KEY_UTILITY_NAME = "utilityName";
	/*private static final String KEY_UNIT_FK_ID = "unitID";
	
	//unit table columns
	private static final String KEY_UNIT_ID = "id";
	private static final String KEY_UNIT_NAME = "unitName";*/
	
	//building table columns
	private static final String KEY_BUILDING_ID ="id";
	private static final String KEY_BUILDING_FACILITY_FK_ID = "facilityID";
	private static final String KEY_BUILDING_NAME = "buildingName";
	private static final String KEY_BUILDING_NUMBER = "buildingNumber";
	private static final String KEY_BUILDING_ADDRESS = "buildingAddress";
	private static final String KEY_BUILDING_CITY = "buildingCity";
	private static final String KEY_BUILDING_STATE = "buildingState"; 
	private static final String KEY_BUILDING_ZIP = "buildingZip";
	private static final String KEY_BUILDING_FUNCTION = "buildingFunction";
	private static final String KEY_BUILDING_AREA = "buildingft2";
	private static final String KEY_BUILDING_SCHEDULE = "buildingSchedule";
	private static final String KEY_BUILDING_FLOORS = "buildingFloors";
	private static final String KEY_BUILIDNG_HVAC_SCHEDULE = "hvacSchedule";
	private static final String KEY_BUILDING_YEAR_BUILT = "yearBuilt";
	private static final String KEY_BUILDING_ROOF_TYPE = "roofType";
	private static final String KEY_BUILDING_WINDOW_TYPE = "windowType";
	private static final String KEY_BUILDING_WALL_TYPE = "wallType";
	private static final String KEY_BUILDING_FOUNDATION_TYPE = "foundationType";
	private static final String KEY_BUILDING_NUM_PCS = "numberOfPCs";
	
	//photo table columns
	private static final String KEY_PHOTO_ID = "id";
	private static final String KEY_PHOTO_NAME = "photoName";
	//private static final String KEY_BUILDING_FK_ID = "buildingId"; //already added
	//private static final String KEY_EQUIPMENT_FK_ID = "equipmentId"; //already added
	private static final String KEY_PHOTO_TAG = "photoTag";
	//private static final String KEY_MEASURE_LIST_FK_ID;
	private static final String KEY_PHOTO_NOTES = "photoNotes";
    private static final String KEY_PHOTO_IS_NEW = "photoIsNew";
	
	//allocation category
	/*private static final String KEY_ALLOCATION_CATEGORY_ID = "_id";
	private static final String KEY_ALLOCATION_CATEGORY_NAME = "allocationName";	
	private static final String KEY_ALLOCATION_CAPACITY = "allocationCapacity";
	private static final String KEY_ALLOCATION_EFFICIENCY = "allocationEfficiency";
	//private static final String KEY_UTILITY_FK_ID = "utilityID"; already defined
	private static final String KEY_ALLOCATION_RUN_HOURS = "runHours";
	private static final String KEY_ALLOCATION_DIVERSITY_FACTOR = "diversityFactor";*/
	
	//facility columns
	private static final String KEY_FACILITY_ID = "_id";
	private static final String KEY_FACILITY_NAME = "facilityName";
	private static final String KEY_FACILITY_FK_BUILDING_ID = "buildingID";
	//private static final String KEY_BUILDINGITEM_FK_ID = "buildingItemFKID";
    private static final String KEY_FACILITY_BUILDINGITEM_FK_ID = "anyBuildingItemFKID";
	private static final String KEY_MEASURE_LIST_FK_ID = "measureListFKID";
	private static final String KEY_PHOTO_TAG_TEXT = "facilityPhotoTag";
	private static final String KEY_PHOTO_NOTES_TEXT = "facilityPhotoNotes";

	//settings columns
	private static final String KEY_SETTINGS_ID = "_id";
	private static final String KEY_FACILITY_FK_ID = "facilityID";
	
	//measure columns
	private static final String KEY_MEASURE_ID = "measureID";
	private static final String KEY_MEASURE_DESCRIPTION = "measureDescription";
	private static final String KEY_MEASURE_TYPE = "measureType";
	
	//measure_equipment columns
	private static final String KEY_MEASURE_EQUIPMENT_ID = "measureEquipmentID";
	//private static final String KEY_MEASURE_FK_ID;
	private static final String KEY_MEASURE_EQUIPMENT_TYPE = "equipmentType";
	
	//measure list columns
	private static final String KEY_MEASURE_LIST_ID = "measureListID";
	private static final String KEY_MEASURE_FK_ID = "measureFKID";
	//private static final String KEY_BUILDINGITEM_FK_ID = "buildingItemFKID";
	private static final String KEY_MEASURE_LIST_NOTES = "measureNotes";
	
	//audio table
	private static final String KEY_AUDIO_ID = "id";
	private static final String KEY_AUDIO_FILE = "audioName";
	private static final String KEY_BUILDINGITEM_FK_ID = "buildingItemFKID";
	private static final String KEY_AUDIO_TAG = "audioTag";
	//private static final String KEY_MEASURE_LIST_FK_ID;
	private static final String KEY_AUDIO_NOTES = "audioNotes";
	
	//constants
	private static final String HEATING = Function.HEATING.name();
	private static final String COOLING = Function.COOLING.name();
	private static final String SUPPLY_FAN = Function.SUPPLY_FAN.name();
	private static final String RETURN_FAN = Function.RETURN_FAN.name();
	private static final String OTHER = Function.OTHER.name();
	private static final String CREATE_TABLE = "CREATE TABLE "; //CREATE TABLE IF NOT EXISTS 
	private static final int SETTINGS_ID = 1;

	//utility meter table
	private static final String KEY_UM_ID = "_id";
	private static final String KEY_UM_METER_ID = "meterSerial";
	private static final String KEY_UM_METER_READ = "meterReading";
	private static final String KEY_UM_UTILITY_PROVIDER = "meterUtilityProvider";
	private static final String KEY_UM_METER_UTILITY = "meterUtilityType";
	private static final String KEY_UM_METER_UNITS = "meterUtilityUnits";
	private static final String KEY_UM_NOTES = "meterNotes";

	//private SQLiteDatabase db;
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) {
		
		String CREATE_SETTINGS_TABLE = CREATE_TABLE + SETTINGS_TABLE + "("
				+KEY_SETTINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+KEY_FACILITY_FK_ID + " INTEGER,"
				+ "FOREIGN KEY(" + KEY_FACILITY_FK_ID + ") REFERENCES " + FACILITY_TABLE + "("+ KEY_FACILITY_ID + ")"
				+ ")";              

	    String CREATE_EQUIPMENT_TABLE = CREATE_TABLE + EQUIPMENT_TABLE + "("
                + KEY_EQUIPMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
                + KEY_MANUFACTURER + " TEXT," 
	    		+ KEY_MODEL + " TEXT," 
                + KEY_SERIAL_NUMBER + " TEXT," 
                + KEY_INSTALL_YEAR + " TEXT," 
                + KEY_HEATING_ALLOCATION_ID + " INTEGER," 
                + KEY_COOLING_ALLOCATION_ID + " INTEGER," 
                + KEY_SUPPLY_FAN_ALLOCATION_ID + " INTEGER,"
                + KEY_RETURN_FAN_ALLOCATION_ID + " INTEGER," 
                + KEY_OTHER_ALLOCATION_ID + " INTEGER,"
                + KEY_UNIT_ID_NAME + " TEXT,"  
                + KEY_EQUIPMENT_QTY + " INTEGER,"
                //+ KEY_EQUIPMENT_LOCATION + " TEXT,"
	    		+ KEY_EQUIPMENT_SERVES + " TEXT,"
	    		+ KEY_EQUIPMENT_CONTROL + " TEXT,"
	    		+ KEY_EQUIPMENT_SCHEDULE + " TEXT,"
	    		+ KEY_EQUIPMENT_NOTES + " TEXT,"
	    		+ KEY_ONPEAKPERCENT + " REAL,"
				+ KEY_OFFPEAKPERCENT + " REAL,"
				+ KEY_ONPEAKDAYS + " REAL,"
				+ KEY_POWERFACTOR + " REAL,"
				+ KEY_REFRIGTEMP + " TEXT,"
				+ KEY_MOTORTYPE + " TEXT,"
				+ KEY_DHWTYPE + " TEXT,"
				+ KEY_DHWENERGYFACTOR + " REAL,"
                + KEY_DHWTANKSIZE + " TEXT,"
				+ KEY_PUMPMODULATION + " TEXT,"
				+ KEY_BOILERTYPE + " TEXT,"
				+ KEY_BOILERMEDIUM + " TEXT,"
				+ KEY_CHILLERTYPE + " TEXT,"
				+ KEY_CHILLERCONDENSERTYPE + " TEXT,"
				+ KEY_CHILLEREVAPORATORTYPE + " TEXT,"
				+ KEY_AHU_ECONOMIZER + " TEXT,"
				+ KEY_AHU_ECONOMIZERLOCKOUT + " TEXT,"
				+ KEY_AHU_ECONOMIZERDAMPERCONTROL + " TEXT,"
				+ KEY_AHU_PREHEATCOIL + " TEXT,"
				+ KEY_AHU_HEATINGCOIL + " TEXT,"
				+ KEY_AHU_COOLINGCOIL + " TEXT,"
				+ KEY_AHU_TOTALCFM + " TEXT,"
				+ KEY_REFRIGASSOCIATED + " TEXT,"
                + "FOREIGN KEY(" + KEY_HEATING_ALLOCATION_ID + ") REFERENCES " + ALLOCATION_TABLE + "("+ KEY_ALLOCATION_ID + "),"
                + "FOREIGN KEY(" + KEY_COOLING_ALLOCATION_ID + ") REFERENCES " + ALLOCATION_TABLE + "("+ KEY_ALLOCATION_ID + "),"
                + "FOREIGN KEY(" + KEY_SUPPLY_FAN_ALLOCATION_ID + ") REFERENCES " + ALLOCATION_TABLE + "("+ KEY_ALLOCATION_ID + "),"
                + "FOREIGN KEY(" + KEY_RETURN_FAN_ALLOCATION_ID + ") REFERENCES " + ALLOCATION_TABLE + "("+ KEY_ALLOCATION_ID + "),"
                + "FOREIGN KEY(" + KEY_OTHER_ALLOCATION_ID + ") REFERENCES " + ALLOCATION_TABLE + "("+ KEY_ALLOCATION_ID + ")"
                + ")";
	    
	    String CREATE_LIGHTING_TABLE = CREATE_TABLE + LIGHTING_TABLE + "("
	    		+ KEY_LIGHTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    		//+ KEY_LIGHTING_LOCATION + " TEXT,"
	    		+ KEY_FIXTURE_QTY + " INTEGER,"
	    		+ KEY_LAMP_QTY + " INTEGER,"
	    		+ KEY_LAMP_TYPE + " TEXT,"
	    		+ KEY_LAMP_CONTROL + " TEXT,"
	    		+ KEY_LAMP_HOURS + " REAL,"
	    		+ KEY_LAMP_KW + " REAL,"
	    		+ KEY_LIGHT_NOTES + " TEXT,"
	    		+ "FOREIGN KEY(" + KEY_LIGHTING_ID + ") REFERENCES " + BUILDING_ITEM_TABLE + "("+ KEY_BUILDINGITEM_ID + ")"     
	    		+ ")";

        String CREATE_WATER_TABLE = CREATE_TABLE + WATER_TABLE + "("
                + KEY_WATER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_WATER_URINAL_FLOW_RATE + " TEXT,"
                + KEY_WATER_URINAL_QTY + " INTEGER,"
                + KEY_WATER_TOILET_FLOW_RATE + " TEXT,"
                + KEY_WATER_SINK_QTY + " INTEGER,"
                + KEY_WATER_SINK_FLOW_RATE + " TEXT,"
                + KEY_WATER_SINK_QTY_2 + " INTEGER,"
                + KEY_WATER_SINK_FLOW_RATE_2 + " TEXT,"
                + KEY_WATER_SHOWER_QTY + " INTEGER,"
                + KEY_WATER_SHOWER_FLOW_RATE + " TEXT,"
                + KEY_WATER_TOILET_QTY + " INTEGER,"
                //+ KEY_WATER_LOCATION + " TEXT,"
                + KEY_WATER_NOTES + " TEXT,"
                + "FOREIGN KEY(" + KEY_WATER_ID + ") REFERENCES " + BUILDING_ITEM_TABLE + "("+ KEY_BUILDINGITEM_ID + ")"
                + ")";

        String CREATE_ENVELOPE_TABLE =  CREATE_TABLE + ENVELOPE_TABLE + "("
            + KEY_ENVELOPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            //+ KEY_ENVELOPE_LOCATION  + " TEXT,"
            + KEY_ENVELOPE_HEIGHT  + " REAL,"
            + KEY_ENVELOPE_RVALUE + " REAL,"
            + KEY_ENVELOPE_WINDOW_TYPE + " REAL,"
            + KEY_ENVELOPE_COLOR  + " TEXT,"
            + KEY_ENVELOPE_NOTES  + " TEXT,"
			+ KEY_ENVELOPE_INSULALTION_TYPE +" TEXT,"
			+ KEY_ENVELOPE_DOOR_TYPE +" TEXT,"
			+ KEY_ENVELOPE_GLASS_TYPE +" TEXT,"
			+ KEY_ENVELOPE_LENGTH +" REAL,"
			+ KEY_ENVELOPE_DOOR_MATERIAL +" TEXT,"
			+ KEY_ENVELOPE_QUANTITY +" INTEGER,"
			+ KEY_ENVELOPE_FRAME_TYPE +" TEXT,"
			+ "FOREIGN KEY(" + KEY_ENVELOPE_ID + ") REFERENCES " + BUILDING_ITEM_TABLE + "("+ KEY_BUILDINGITEM_ID + ")"
            + ")";

        String CREATE_BUILDING_TABLE = CREATE_TABLE + BUILDING_TABLE + "("
                + KEY_BUILDING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ KEY_BUILDING_NAME + " TEXT,"
				+ KEY_BUILDING_NUMBER + " TEXT,"
	    		+ KEY_BUILDING_ADDRESS + " TEXT,"	    		
	    		+ KEY_BUILDING_CITY + " TEXT,"	
	    		+ KEY_BUILDING_STATE + " TEXT,"	
	    		+ KEY_BUILDING_ZIP + " TEXT,"		
	    		+ KEY_BUILDING_FUNCTION + " TEXT,"		    		
	    		+ KEY_BUILDING_SCHEDULE + " TEXT,"	
	    		+ KEY_BUILDING_AREA + " INTEGER,"
	    		+ KEY_BUILDING_FLOORS + " INTEGER,"
	    		+ KEY_BUILIDNG_HVAC_SCHEDULE + " TEXT,"
	    		+ KEY_BUILDING_YEAR_BUILT + " TEXT,"
	    		+ KEY_BUILDING_NUM_PCS + " TEXT,"
	    		+ KEY_BUILDING_ROOF_TYPE + " TEXT,"
	        	+ KEY_BUILDING_WINDOW_TYPE + " TEXT,"
	        	+ KEY_BUILDING_WALL_TYPE + " TEXT,"
				+ KEY_BUILDING_FOUNDATION_TYPE + " TEXT,"
	    		+ KEY_BUILDING_FACILITY_FK_ID + " INTEGER,"
	    		+ "FOREIGN KEY(" + KEY_BUILDING_FACILITY_FK_ID +") REFERENCES " +FACILITY_TABLE +"(" + KEY_FACILITY_ID + ")" 
                + ")";
	    
	    String CREATE_ALLOCATION_TABLE = CREATE_TABLE + ALLOCATION_TABLE + "("
                + KEY_ALLOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ KEY_CAPACITY + " REAL,"	    		
				+ KEY_EFFICIENCY + " REAL,"
				+ KEY_UTILITY_FK_ID + " TEXT,"
				//+ KEY_ALLOCATION_CATEGORY_FK_ID + " INTEGER,"
				+ KEY_RUN_HOURS + " REAL,"
				+ KEY_DIVERSITY_FACTOR + " INTEGER,"
				+ KEY_EFFECIENCY_UNITS + " TEXT,"
				+ KEY_CAPACITY_UNITS + " TEXT,"
				+ KEY_SOURCE + " TEXT"
                //+ ",FOREIGN KEY(" + KEY_ALLOCATION_CATEGORY_FK_ID + ") REFERENCES " + ALLOCATION_SUMMARY_TABLE + "("+ KEY_ALLOCATION_CATEGORY_ID + ")"  				
                + ")";
	    
	   /*String CREATE_UTILITY_TABLE = CREATE_TABLE + UTILITY_TABLE + "("
                + KEY_UTILITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
                + KEY_UTILITY_NAME + " TEXT," 
                + "FOREIGN KEY(" + KEY_UNIT_FK_ID + ") REFERENCES " + UNIT_TABLE + "("+ KEY_UNIT_ID + ")"   				
                + ")";
	    		
	    String CREATE_UNIT_TABLE = CREATE_TABLE + UNIT_TABLE + "("
                + KEY_UNIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
                + KEY_UNIT_NAME + " TEXT"
                + ")";*/
	    		
	    /*String CREATE_ALLOCATION_CATEGORY_TABLE = CREATE_TABLE + ALLOCATION_SUMMARY_TABLE + "("
                + KEY_ALLOCATION_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
                + KEY_ALLOCATION_CATEGORY_NAME + " TEXT,"
  				+ KEY_ALLOCATION_CAPACITY + " REAL,"	    		
				+ KEY_ALLOCATION_EFFICIENCY + " REAL,"	    		
				+ KEY_UTILITY_FK_ID + " INTEGER,"              
				+ KEY_ALLOCATION_RUN_HOURS + " REAL,"	    		
				+ KEY_ALLOCATION_DIVERSITY_FACTOR + " REAL,"	    		    
                + "FOREIGN KEY(" + KEY_UTILITY_FK_ID + ") REFERENCES " + UTILITY_TABLE + "("+ KEY_UTILITY_ID + ")"   				
				+ ")";*/
	    
	    String CREATE_FACILITY_TABLE = CREATE_TABLE + FACILITY_TABLE + "("
	    		+ KEY_FACILITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
                + KEY_FACILITY_FK_BUILDING_ID + " INTEGER,"
                + KEY_FACILITY_NAME + " TEXT,"
                + KEY_BUILDINGITEM_FK_ID + " INTEGER, "		//active real item
                + KEY_FACILITY_BUILDINGITEM_FK_ID + " INTEGER, "  //active item real or not
				+ KEY_MEASURE_LIST_FK_ID + " INTEGER, "
				+ KEY_PHOTO_TAG_TEXT + " TEXT, "
				+ KEY_PHOTO_NOTES_TEXT + " TEXT, "
                + "FOREIGN KEY(" + KEY_FACILITY_FK_BUILDING_ID + ") REFERENCES " + BUILDING_TABLE + "("+ KEY_BUILDING_ID + "),"   	
                + "FOREIGN KEY(" + KEY_BUILDINGITEM_FK_ID + ") REFERENCES " + BUILDING_ITEM_TABLE + "("+ KEY_BUILDINGITEM_ID + "), "
                + "FOREIGN KEY(" + KEY_FACILITY_BUILDINGITEM_FK_ID + ") REFERENCES " + BUILDING_ITEM_TABLE + "("+ KEY_BUILDINGITEM_ID + "), "
				+ "FOREIGN KEY(" + KEY_MEASURE_LIST_FK_ID +") REFERENCES " + MEASURE_LIST_TABLE + "(" + KEY_MEASURE_LIST_ID + ")"
                + ")";
	    
	    String CREATE_PHOTO_TABLE = CREATE_TABLE + PHOTO_TABLE + "(" 
	    		+ KEY_PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_PHOTO_NAME + " TEXT, "
                + KEY_BUILDINGITEM_FK_ID + " INTEGER, "
				+ KEY_MEASURE_LIST_FK_ID + " INTEGER, "
				+ KEY_PHOTO_TAG + " TEXT, "
				+ KEY_PHOTO_NOTES + " TEXT, "
                + KEY_PHOTO_IS_NEW + " INTEGER, "
                + "FOREIGN KEY(" + KEY_BUILDINGITEM_FK_ID + ") REFERENCES " + BUILDING_ITEM_TABLE + "("+ KEY_BUILDINGITEM_ID + ")"
				+ "FOREIGN KEY(" + KEY_MEASURE_LIST_FK_ID + ") REFERENCES " + MEASURE_LIST_TABLE + "("+ KEY_MEASURE_LIST_ID + ")"
				+ ")";

		String CREATE_AUDIO_TABLE = CREATE_TABLE + AUDIO_TABLE + "("
				+ KEY_AUDIO_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_AUDIO_FILE  +  " TEXT, "
				+ KEY_BUILDINGITEM_FK_ID  +  " INTEGER, "
				+ KEY_MEASURE_LIST_FK_ID + " INTEGER, "
				+ KEY_AUDIO_TAG + " TEXT, "
				+ KEY_AUDIO_NOTES + " TEXT, "
				+ "FOREIGN KEY(" + KEY_BUILDINGITEM_FK_ID +") REFERENCES " + BUILDING_ITEM_TABLE +"(" + KEY_BUILDINGITEM_ID + ")"
				+ "FOREIGN KEY(" + KEY_MEASURE_LIST_FK_ID + ") REFERENCES " + MEASURE_LIST_TABLE + "("+ KEY_MEASURE_LIST_ID + ")"
				+ ")";

	    String CREATE_MEASURE_TABLE = CREATE_TABLE + MEASURE_TABLE + "("
	    		+ KEY_MEASURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_MEASURE_DESCRIPTION + " TEXT, "
	    		+ KEY_MEASURE_TYPE + " TEXT "
	    		+ ")";
	    
	    String CREATE_MEASURE_EQUIPMENT_TABLE = CREATE_TABLE + MEASURE_EQUIPMENT_TABLE + "("
	    		+ KEY_MEASURE_EQUIPMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_MEASURE_FK_ID +  " INTEGER, "
	    		+ KEY_MEASURE_EQUIPMENT_TYPE + " TEXT, "
	    		+ "FOREIGN KEY(" + KEY_MEASURE_FK_ID +") REFERENCES " + MEASURE_TABLE +"(" + KEY_MEASURE_ID + ")" 
	    		+ ")";
	    
	    String CREATE_MEASURE_LIST_TABLE = CREATE_TABLE + MEASURE_LIST_TABLE + "("
	    		+ KEY_MEASURE_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	    		+ KEY_MEASURE_FK_ID +  " INTEGER, "
                + KEY_BUILDINGITEM_FK_ID + " INTEGER, "
	    		+ KEY_MEASURE_LIST_NOTES + " TEXT, "
	    		+ "FOREIGN KEY(" + KEY_MEASURE_FK_ID +") REFERENCES " + MEASURE_TABLE +"(" + KEY_MEASURE_ID + ")," 
                + "FOREIGN KEY(" + KEY_BUILDINGITEM_FK_ID + ") REFERENCES " + BUILDING_ITEM_TABLE + "("+ KEY_BUILDINGITEM_ID + ")"
	    		+ ")";
	    
	    String CREATE_BUILDINGITEM_TABLE = CREATE_TABLE + BUILDING_ITEM_TABLE + "("
		    + KEY_BUILDINGITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_EQUIPMENT_TYPE + " TEXT, "
			+ KEY_BUILDING_FK_ID + " INTEGER, " 
			+ KEY_BUILDINGITEM_EXISTS + " INTEGER, "
			+ KEY_BUILDINGITEM_LOCATION + " TEXT, "
			+ "FOREIGN KEY(" + KEY_BUILDING_FK_ID + ") REFERENCES " + BUILDING_TABLE + "("+ KEY_BUILDING_ID + ")"
			+ ")";

		String CREATE_UTILITY_METER_TABLE = CREATE_TABLE + UTILITY_METER_TABLE + "("
				+ KEY_UM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_UM_METER_ID +" TEXT,"
				+ KEY_UM_METER_READ +" REAL,"
				+ KEY_UM_UTILITY_PROVIDER +" TEXT,"
				+ KEY_UM_METER_UTILITY +" TEXT,"
				+ KEY_UM_METER_UNITS +" TEXT,"
				+ KEY_UM_NOTES +" TEXT, "
                + "FOREIGN KEY(" + KEY_UM_ID + ") REFERENCES " + BUILDING_ITEM_TABLE + "("+ KEY_BUILDINGITEM_ID + ")"
                + ")";

		Log.v(TAG,CREATE_FACILITY_TABLE);

        Log.v(TAG,CREATE_BUILDING_TABLE);
		Log.v(TAG,CREATE_BUILDINGITEM_TABLE);
		Log.v(TAG,CREATE_ALLOCATION_TABLE);
		Log.v(TAG,CREATE_EQUIPMENT_TABLE);
		Log.v(TAG,CREATE_LIGHTING_TABLE);
		Log.v(TAG,CREATE_WATER_TABLE);
		Log.v(TAG,CREATE_ENVELOPE_TABLE);
		Log.v(TAG,CREATE_SETTINGS_TABLE);
		Log.v(TAG,CREATE_PHOTO_TABLE);
		Log.v(TAG,CREATE_AUDIO_TABLE);
		Log.v(TAG,CREATE_MEASURE_TABLE);
		Log.v(TAG,CREATE_MEASURE_EQUIPMENT_TABLE);
		Log.v(TAG,CREATE_MEASURE_LIST_TABLE);
		Log.v(TAG,CREATE_UTILITY_METER_TABLE);

		db.execSQL(CREATE_FACILITY_TABLE);
	    db.execSQL(CREATE_BUILDING_TABLE);
	    //db.execSQL(CREATE_UNIT_TABLE);
	    //db.execSQL(CREATE_UTILITY_TABLE);
	    //db.execSQL(CREATE_ALLOCATION_CATEGORY_TABLE);
	    db.execSQL(CREATE_BUILDINGITEM_TABLE);
	    db.execSQL(CREATE_ALLOCATION_TABLE);
	    db.execSQL(CREATE_EQUIPMENT_TABLE);
	    db.execSQL(CREATE_LIGHTING_TABLE);
        db.execSQL(CREATE_WATER_TABLE);
        db.execSQL(CREATE_ENVELOPE_TABLE);
	    db.execSQL(CREATE_SETTINGS_TABLE);
	    db.execSQL(CREATE_PHOTO_TABLE);
	    db.execSQL(CREATE_AUDIO_TABLE);
	    db.execSQL(CREATE_MEASURE_TABLE);
	    db.execSQL(CREATE_MEASURE_EQUIPMENT_TABLE);
	    db.execSQL(CREATE_MEASURE_LIST_TABLE);
		db.execSQL(CREATE_UTILITY_METER_TABLE);
	    
	    //build units table
	    /*ContentValues unitValues = new ContentValues();
	    for (EnergyUnits eUnits: EnergyUnits.values()) {
		    unitValues = new ContentValues();
	    	unitValues.put(KEY_UNIT_NAME, eUnits.toString());
		    db.insert(UNIT_TABLE, null, unitValues);
	    }
	    for (PowerUnits pUnits: PowerUnits.values()) {
		    unitValues = new ContentValues();    	
	    	unitValues.put(KEY_UNIT_NAME, pUnits.toString());
	    }*/

	    //build utility table
	    /*ContentValues utilityValues = new ContentValues();
	    for (FuelType fuelType : FuelType.values()) {
	    	utilityValues = new ContentValues();
	    	utilityValues.put(KEY_UTILITY_NAME, fuelType.toString());
	    	utilityValues.put(KEY_UNIT_FK_ID, fuelType.getDefaultUnits().getID());
		    db.insert(UTILITY_TABLE, null, utilityValues);
	    } */
	    
	    //build allocation category table
	    /*ContentValues allocationValues = new ContentValues();
	    for (AllocationCategory aCat: AllocationCategory.values()) {
	    	allocationValues = new ContentValues();
            allocationValues.put(KEY_ALLOCATION_CATEGORY_NAME, aCat.toString());   		
            allocationValues.put(KEY_UTILITY_FK_ID, aCat.getFuelType().getID());              
            db.insert(ALLOCATION_SUMMARY_TABLE,null,allocationValues);
	    }*/

		insertMeasuresIntoDatabase(db);

	}
	
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    	final String DROP = "DROP TABLE IF EXISTS ";
        // Drop older table if existed
        db.execSQL(DROP + EQUIPMENT_TABLE); //
        db.execSQL(DROP + ALLOCATION_TABLE);//         
        db.execSQL(DROP + UTILITY_TABLE);
        db.execSQL(DROP + BUILDING_ITEM_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + UNIT_TABLE);
        db.execSQL(DROP + BUILDING_TABLE); //         
        db.execSQL(DROP + ALLOCATION_SUMMARY_TABLE);   
        db.execSQL(DROP + LIGHTING_TABLE);
        db.execSQL(DROP + FACILITY_TABLE);
        db.execSQL(DROP + PHOTO_TABLE);
        db.execSQL(DROP + AUDIO_TABLE);
        db.execSQL(DROP + WATER_TABLE);
        db.execSQL(DROP + UTILITY_METER_TABLE);
        db.execSQL(DROP + ENVELOPE_TABLE);
        db.execSQL(DROP + SETTINGS_TABLE);
        db.execSQL(DROP + MEASURE_TABLE);
        db.execSQL(DROP + MEASURE_LIST_TABLE);
        db.execSQL(DROP + MEASURE_EQUIPMENT_TABLE);
        
        // Create tables again
        onCreate(db);
    }

    
//----------------------------------Building item --------------------------------------------------
    /**adds a building item... a light, an equipment, a water fixture
     * 
     * @param eType EquipmentType of equipment being added
     * @return id database primary key id of building item added
     */
    private long addBuildingItem(EquipmentType eType, String location, boolean isDefined) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	int exists;
    	
    	if (isDefined)
    		exists = 1;
    	else 
    		exists = 0;
    	
    	values.put(KEY_EQUIPMENT_TYPE, eType.toString());
		values.put(KEY_BUILDINGITEM_LOCATION, location);
		values.put(KEY_BUILDING_FK_ID, this.getActiveBuildingID());
		values.put(KEY_BUILDINGITEM_EXISTS, exists);
    	
    	long id = db.insert(BUILDING_ITEM_TABLE, null, values);

		if(isDefined) {
            Log.v(TAG, "active id: " + id);
			this.setBuildingItemIDSettings((int) id);
		} else {
			this.setBuildingItemRealOrNotSettings((int) id);
		}


    	return id;
    }

    private void updateLocation(String location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUILDINGITEM_LOCATION, location);
        db.update(BUILDING_ITEM_TABLE, values, KEY_BUILDINGITEM_ID + " = ?", new String[]{String.valueOf(getActiveBuildingItemID())});

    }
    
    public EquipmentType getActiveEquipmentType() throws NullPointerException {
    	String selectQuery = "SELECT * FROM " + BUILDING_ITEM_TABLE + " WHERE " + KEY_BUILDINGITEM_ID + " = " + this.getActiveBuildingItemID();
        //Log.v(TAG, selectQuery);
    	SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            EquipmentType eType = EquipmentType.getEnumType(cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_TYPE)));
            cursor.close();
        	return eType;
        }
        
        throw new IndexOutOfBoundsException("No active equipment Type Set");
        
    }
    
    public String getActiveEquipmentTypeAndCount() throws NullPointerException {
		String selectQuery = "SELECT " + KEY_EQUIPMENT_TYPE + ", COUNT(" +KEY_EQUIPMENT_TYPE + ") AS count FROM " + BUILDING_ITEM_TABLE + " WHERE " + KEY_BUILDING_FK_ID + " = " 
				+ this.getActiveBuildingID() + " AND " + KEY_BUILDINGITEM_EXISTS + " = 1 AND " + KEY_EQUIPMENT_TYPE + "='" + this.getActiveEquipmentType().toString() + "' group by "+ KEY_EQUIPMENT_TYPE;
        //Log.v(TAG, selectQuery);
    	SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            String text = cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_TYPE)) + " (" + cursor.getInt(cursor.getColumnIndex("count")) +")";
            cursor.close();
        	return text;
        }

        cursor.close();

        throw new IndexOutOfBoundsException("No active equipment Type Set");

    }
    
    //this is one function based on buildingItem table, now works for lighting and equipment
    public void setActiveBuildingItem(int index, int offset, EquipmentType eType) {
    	
        	String selectQuery = "SELECT * FROM " + BUILDING_ITEM_TABLE + " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID() + " AND " + KEY_EQUIPMENT_TYPE + "='" +eType.toString() + "'" + " AND " + KEY_BUILDINGITEM_EXISTS + "=1";
            //Log.v(TAG, selectQuery);
        	SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            int buildingItemID = 0;
            //Log.v(TAG, "index: " + index + " offset: " + offset);
            if (cursor.move(index - offset + 1)) {
    	       buildingItemID = cursor.getInt(cursor.getColumnIndex(KEY_BUILDINGITEM_ID));
               this.setBuildingItemIDSettings(buildingItemID);
            }
            //Log.v(TAG, "ID: " + buildingItemID);

            cursor.close();
    }

   public void setGeneralBuildingItem(EquipmentType eType) {
        String selectQuery = "SELECT * FROM " + BUILDING_ITEM_TABLE + " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID() + " AND " + KEY_EQUIPMENT_TYPE + "='" +eType.toString() + "'"+ " AND " + KEY_BUILDINGITEM_EXISTS + "=0";
        //Log.v(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
            this.setBuildingItemRealOrNotSettings(cursor.getInt(cursor.getColumnIndex(KEY_BUILDINGITEM_ID)));
        else
            this.setBuildingItemRealOrNotSettings((int) this.addBuildingItem(eType, null, false));
   }

	public String[] getLocationList() {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT DISTINCT " + KEY_BUILDINGITEM_LOCATION + " FROM " + BUILDING_ITEM_TABLE + " WHERE " + KEY_BUILDING_FK_ID + "=" + this.getActiveBuildingID();
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<String> locations = new ArrayList<String>();
		String locationString;

		while(cursor.moveToNext()) {
			locationString = cursor.getString(cursor.getColumnIndex(KEY_BUILDINGITEM_LOCATION));
			if(locationString != null)
				locations.add(locationString);
		}
		cursor.close();
		return GeneralFunctions.arrayListToStringArray(locations);
	}


///////////////////////////////////////////////////////////////
//Equipment------------------------------------------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////
    // Adding new equipment
    public void addEquipment(EquipmentInformation equipmentInformation) {
    	
    	ContentValues values = equipmentToContentValues(equipmentInformation, true);
    	values.put(KEY_EQUIPMENT_ID, addBuildingItem(equipmentInformation.getEquipmentType(), equipmentInformation.getLocation(), true));
    	SQLiteDatabase db = this.getWritableDatabase();
 
    	db.insert(EQUIPMENT_TABLE, null, values);
    	
    	////Log.v(TAG, "equipment added. id: " + id);
    	//db.close(); // Closing database connection
    }
    
    public void AddLightingEquipment(LightingInformation lightingInformation) throws IOException {
    	
    	ContentValues values = lightingToContentValues(lightingInformation);
    	values.put(KEY_LIGHTING_ID, addBuildingItem(lightingInformation.getIsInteriorEquipmentType(), lightingInformation.getLocation(), true));
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	db.insert(LIGHTING_TABLE, null, values);
    }

    /**
     *
     * @param lightingInformation light to be converted to contect values
     * @return ContentValues light that can be added to database
     */
    private ContentValues lightingToContentValues(LightingInformation lightingInformation) {
    	
    	ContentValues values = new ContentValues();
    	    	
    	////Log.v(TAG, lightingInformation.getIsInteriorEquipmentType().toString());
    	//values.put(KEY_LIGHTING_LOCATION, lightingInformation.getLocation());
    	values.put(KEY_FIXTURE_QTY, lightingInformation.getQuantity());
    	values.put(KEY_LAMP_QTY, lightingInformation.getLampQuantity());
    	values.put(KEY_LAMP_TYPE, lightingInformation.getLampType().toString());
    	values.put(KEY_LAMP_CONTROL, lightingInformation.getControl());
    	values.put(KEY_LAMP_HOURS, lightingInformation.getAnnualHours());
    	values.put(KEY_LAMP_KW, lightingInformation.getTotalKW());
    	values.put(KEY_LIGHT_NOTES, lightingInformation.getNotes());
    	
    	return values;
    }
    
    /**
     * 
     * @param equipmentInformation Information of equipment
     * @param isNew, will update the allocation categories if false, otherwise creates new
     * @return ContentValues to be added to EQUIPMENT_TABLE
     */
    private ContentValues equipmentToContentValues(EquipmentInformation equipmentInformation, boolean isNew) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	final String METHOD_NAME = "equipmentToContentValues";
    	  	
    	////Log.v(TAG, "Manufacturer: " + equipmentInformation.getManufacturer().toString());
    	values.put(KEY_MANUFACTURER, equipmentInformation.getManufacturer());
    	values.put(KEY_MODEL, equipmentInformation.getModelNumber()); 
    	values.put(KEY_SERIAL_NUMBER, equipmentInformation.getSerialNumber());
    	values.put(KEY_INSTALL_YEAR, equipmentInformation.getInstallYear());
     	////Log.v(TAG, "setting UnitID name in database..."+ equipmentInformation.getUnitID());
     	values.put(KEY_UNIT_ID_NAME, equipmentInformation.getUnitID());
     	values.put(KEY_EQUIPMENT_QTY, equipmentInformation.getQuantity());
     	//values.put(KEY_EQUIPMENT_LOCATION, equipmentInformation.getLocation());
     	values.put(KEY_EQUIPMENT_SERVES, equipmentInformation.getServes());
     	values.put(KEY_EQUIPMENT_CONTROL, equipmentInformation.getControl());
     	values.put(KEY_EQUIPMENT_SCHEDULE, equipmentInformation.getSchedule());
     	values.put(KEY_EQUIPMENT_NOTES, equipmentInformation.getNotes());

    	for(Function f:Function.values()) {
    		ContentValues allocationValues = new ContentValues();
    		allocationValues.put(KEY_CAPACITY, equipmentInformation.getCapacity(f));
    		allocationValues.put(KEY_UTILITY_FK_ID, equipmentInformation.getFuelType(f).toString());
    		allocationValues.put(KEY_RUN_HOURS, equipmentInformation.getHours(f));
    		allocationValues.put(KEY_DIVERSITY_FACTOR, equipmentInformation.getDF(f));
    		allocationValues.put(KEY_EFFICIENCY, equipmentInformation.getEfficiency(f));
    		allocationValues.put(KEY_EFFECIENCY_UNITS, equipmentInformation.getEfficiencyUnits(f).toString());
    		allocationValues.put(KEY_CAPACITY_UNITS, equipmentInformation.getCapacityUnits(f).toString());
    		allocationValues.put(KEY_SOURCE, equipmentInformation.getSource(f));
    		//Log.v(TAG + "/" + METHOD_NAME, f.name() + "Source: " + equipmentInformation.getSource(f));
        	if( isNew) {
            	long allocationID = db.insert(ALLOCATION_TABLE, null, allocationValues);
            	values.put(f.name() + "AllocationID", allocationID);
        	} else {
        		long allocationID = getAllocationIDByFunction(f);
        		//Log.v(TAG + "/" + METHOD_NAME, "Allocation ID: " +allocationID);
        		//Log.v(TAG + "/" + METHOD_NAME, "AllocationValues: " + allocationValues.toString());
        		db.update(ALLOCATION_TABLE, allocationValues, KEY_ALLOCATION_ID + " = ?",   new String[] { String.valueOf(allocationID) });
        	}
    	}
    	   	
		switch(equipmentInformation.getEquipmentType()) {

		case BOILER:
			values.put(KEY_BOILERTYPE, equipmentInformation.getBoilerType());
			values.put(KEY_BOILERMEDIUM, equipmentInformation.getBoilerMedium());
			break;
		case CHILLER:
			values.put(KEY_CHILLERTYPE, equipmentInformation.getChillerType());
			values.put(KEY_CHILLERCONDENSERTYPE, equipmentInformation.getChillerCondenserType());
			values.put(KEY_CHILLEREVAPORATORTYPE, equipmentInformation.getChillerEvaporatorType());
			break;
		case WATERHEATER:
			values.put(KEY_DHWTYPE, equipmentInformation.getDhwType());
			values.put(KEY_DHWENERGYFACTOR, equipmentInformation.getDhwEnergyFactor());
            values.put(KEY_DHWTANKSIZE, equipmentInformation.getDhwTankSize());
			break;
		case CW_PUMP:
		case HW_PUMP:
		case OTHER_PUMP:
			values.put(KEY_PUMPMODULATION, equipmentInformation.getPumpModulation());
			break;
		case AIRHANDLER:
			values.put(KEY_AHU_PREHEATCOIL, equipmentInformation.getAHU_PreheatCoil());
			values.put(KEY_AHU_HEATINGCOIL, equipmentInformation.getAHU_HeatingCoil());
			values.put(KEY_AHU_COOLINGCOIL, equipmentInformation.getAHU_CoolingCoil());
			values.put(KEY_AHU_TOTALCFM, equipmentInformation.getAHU_TotalCFM());
		case PACKAGEUNIT:
			values.put(KEY_AHU_ECONOMIZER, equipmentInformation.getAHU_Economizer());
			values.put(KEY_AHU_ECONOMIZERLOCKOUT, equipmentInformation.getAHU_EconomizerLockout());
			values.put(KEY_AHU_ECONOMIZERDAMPERCONTROL, equipmentInformation.getAHU_EconomizerDamperControl());
			break;
		case TRANSFORMER:
			values.put(KEY_ONPEAKPERCENT, equipmentInformation.getOnPeakPercent());
			values.put(KEY_OFFPEAKPERCENT, equipmentInformation.getOffPeakPercent());
			values.put(KEY_ONPEAKDAYS, equipmentInformation.getOnPeakDays());
			values.put(KEY_POWERFACTOR, equipmentInformation.getPowerFactor());
			break;
		case REFRIGERATION_EVAPORATOR:
			values.put(KEY_REFRIGTEMP, equipmentInformation.getRefrigTemp());
			values.put(KEY_MOTORTYPE, equipmentInformation.getMotorType());
			break;
		case REFRIGERATION_CONDENSING:
			values.put(KEY_REFRIGASSOCIATED, equipmentInformation.getRefrigAssociated());
			break;					
		default:
		}
   	
    	return values;
    }
    
    /**This function gets the allocation ID by function for a specific equipment type
     * 
     * @param function function to be added to database
     */
    private int getAllocationIDByFunction(Function function) {
    	EquipmentInformation equipment = getActiveEquipment();
    	
    	return equipment.getAllocationID(function);
    	
    }
    
    //get equipment information for one equipment type
    public List<EquipmentInformation> getSpecificEquipmentInformation(EquipmentType eType) throws Exception {

    			return getEquipmentInformation(" AND " + KEY_EQUIPMENT_TYPE + " = '" + eType.toString() + "'");
    }
    
    //get lighting information for one lighting type
    public List<LightingInformation> getSpecificLightingInformation(EquipmentType lType) {
		return getLightingInformation(" AND " + KEY_EQUIPMENT_TYPE + " ='" + lType.toString() + "'");
    }
    
    /**This function returns all equipment information for the active building
     * 
     * @return List<EquipmentInformation>
     * @throws Exception
     */
    public List<EquipmentInformation> getAllEquipmentInformation() throws Exception {
    	return getEquipmentInformation("");
    }
    
    public List<LightingInformation> getAllLightingInformation() {
    	return getLightingInformation("");
    }
    
    /**This function returns the equipment set in active equipmentID
     * 
     * @return long id of building item set in active equipmentID
     */
    public EquipmentInformation getActiveEquipment() {

    	String whereClause = " AND " +  EQUIPMENT_TABLE + "." + KEY_EQUIPMENT_ID + " = " + this.getActiveBuildingItemID();
    	////Log.v("DATABASE/specific equipment", whereClause);
    	List<EquipmentInformation> oneList = getEquipmentInformation(whereClause); //'
    	
    	return oneList.get(0);
    }
    
    public LightingInformation getActiveLight() {
    	String whereClause = " AND " + KEY_LIGHTING_ID + " = " + this.getActiveBuildingItemID();
    	////Log.v(TAG, whereClause);
    	List<LightingInformation> oneLight = getLightingInformation(whereClause);
    	
    	return oneLight.get(0);
    }
    
    /**This function is the general lighting query function
     * 
     * @param whereClause additional information to query on in addition to building type
     * @return List<LightingInformation> list of lights within building
     */
    private List<LightingInformation> getLightingInformation(String whereClause) {
    	List<LightingInformation> lightList = new ArrayList<LightingInformation>();
    	LightingInformation lightingInformation;
    	
    	String selectQuery = "SELECT * FROM " 
    			+ LIGHTING_TABLE 
    			+ " INNER JOIN " + BUILDING_ITEM_TABLE + " ON " + KEY_LIGHTING_ID + " = " + KEY_BUILDINGITEM_ID
        		+ " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID() + whereClause;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
               
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        	////Log.v("DATABASE EquipmentQuery", "EquipmentFound");
            do {
            	try {
            		lightingInformation = new LightingInformation();
            		lightingInformation.setIsInteriorEquipment(EquipmentType.getEnumType((cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_TYPE)))));
            		lightingInformation.setLocation(cursor.getString(cursor.getColumnIndex(KEY_BUILDINGITEM_LOCATION)));
            		lightingInformation.setQuantity(cursor.getInt(cursor.getColumnIndex(KEY_FIXTURE_QTY)));
            		lightingInformation.setLampQuantity(cursor.getInt(cursor.getColumnIndex(KEY_LAMP_QTY)));
            		lightingInformation.setLampType(LampType.getEnumType(cursor.getString(cursor.getColumnIndex(KEY_LAMP_TYPE))));
                	lightingInformation.setControl(cursor.getString(cursor.getColumnIndex(KEY_LAMP_CONTROL)));
                	lightingInformation.setAnnualHours(cursor.getDouble(cursor.getColumnIndex(KEY_LAMP_HOURS)));
                	////Log.v("Database Handler / getLightingInformation", "" + lightingInformation.getAnnualHours());
                	lightingInformation.setTotalKW(cursor.getDouble(cursor.getColumnIndex(KEY_LAMP_KW)));
                	lightingInformation.setNotes(cursor.getString(cursor.getColumnIndex(KEY_LIGHT_NOTES)));
            		lightList.add(lightingInformation);
            	} catch (Exception e) {
                		e.printStackTrace();
                }
                } while (cursor.moveToNext());
        }
            cursor.close();

    	return lightList;
    }
    
    /**This function is the general equipment query function
     * 
     * @param whereClause additional information to query on in addition to building type
     * @return List<EquipmentInformation> Equipment in active building
     */
    private List<EquipmentInformation> getEquipmentInformation(String whereClause) {
        List<EquipmentInformation> equipmentList = new ArrayList<EquipmentInformation>();
        //final String METHOD_NAME = "getEquipmentInformation()"; 
        // Select All Query
        String selectQuery = "SELECT " + 
        		HEATING + ALLOCATION_TABLE +"." + KEY_ALLOCATION_ID + " AS " + HEATING + KEY_ALLOCATION_ID 
        		+"," + HEATING + ALLOCATION_TABLE + "." + KEY_CAPACITY  + " AS "  + HEATING + KEY_CAPACITY
        		+"," + HEATING + ALLOCATION_TABLE + "." + KEY_EFFICIENCY  + " AS "  + HEATING + KEY_EFFICIENCY
        		+"," + HEATING + ALLOCATION_TABLE + "." + KEY_RUN_HOURS  + " AS "  + HEATING + KEY_RUN_HOURS       		
        		+"," + HEATING + ALLOCATION_TABLE + "." + KEY_DIVERSITY_FACTOR  + " AS "  + HEATING + KEY_DIVERSITY_FACTOR        		
        		+"," + HEATING + ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID  + " AS "  + HEATING + KEY_UTILITY_NAME        		
        		+"," + HEATING + ALLOCATION_TABLE + "." + KEY_EFFECIENCY_UNITS + " AS " + HEATING + KEY_EFFECIENCY_UNITS
        		+"," + HEATING + ALLOCATION_TABLE + "." + KEY_CAPACITY_UNITS + " AS " + HEATING + KEY_CAPACITY_UNITS
        		+"," + HEATING + ALLOCATION_TABLE + "." + KEY_SOURCE + " AS " + HEATING + KEY_SOURCE

        		+"," + COOLING + ALLOCATION_TABLE +"." + KEY_ALLOCATION_ID + " AS " + COOLING + KEY_ALLOCATION_ID         		
        		+"," + COOLING + ALLOCATION_TABLE + "." + KEY_CAPACITY  + " AS "  + COOLING + KEY_CAPACITY
        		+"," + COOLING + ALLOCATION_TABLE + "." + KEY_EFFICIENCY  + " AS "  + COOLING + KEY_EFFICIENCY
        		+"," + COOLING + ALLOCATION_TABLE + "." + KEY_RUN_HOURS  + " AS "  + COOLING + KEY_RUN_HOURS       		
        		+"," + COOLING + ALLOCATION_TABLE + "." + KEY_DIVERSITY_FACTOR  + " AS "  + COOLING + KEY_DIVERSITY_FACTOR        		
        		+"," + COOLING + ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID  + " AS "  + COOLING + KEY_UTILITY_NAME   
        		+"," + COOLING + ALLOCATION_TABLE + "." + KEY_EFFECIENCY_UNITS + " AS " + COOLING + KEY_EFFECIENCY_UNITS
        		+"," + COOLING + ALLOCATION_TABLE + "." + KEY_CAPACITY_UNITS + " AS " + COOLING + KEY_CAPACITY_UNITS
        		+"," + COOLING + ALLOCATION_TABLE + "." + KEY_SOURCE + " AS " + COOLING + KEY_SOURCE

        		+"," + SUPPLY_FAN + ALLOCATION_TABLE +"." + KEY_ALLOCATION_ID + " AS " + SUPPLY_FAN + KEY_ALLOCATION_ID         		        		
        		+"," + SUPPLY_FAN + ALLOCATION_TABLE + "." + KEY_CAPACITY  + " AS "  + SUPPLY_FAN + KEY_CAPACITY
        		+"," + SUPPLY_FAN + ALLOCATION_TABLE + "." + KEY_EFFICIENCY  + " AS "  + SUPPLY_FAN + KEY_EFFICIENCY
        		+"," + SUPPLY_FAN + ALLOCATION_TABLE + "." + KEY_RUN_HOURS  + " AS "  + SUPPLY_FAN + KEY_RUN_HOURS       		
        		+"," + SUPPLY_FAN + ALLOCATION_TABLE + "." + KEY_DIVERSITY_FACTOR  + " AS "  + SUPPLY_FAN + KEY_DIVERSITY_FACTOR        		
        		+"," + SUPPLY_FAN + ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID  + " AS "  + SUPPLY_FAN + KEY_UTILITY_NAME
        		+"," + SUPPLY_FAN + ALLOCATION_TABLE + "." + KEY_EFFECIENCY_UNITS + " AS " + SUPPLY_FAN + KEY_EFFECIENCY_UNITS
        		+"," + SUPPLY_FAN + ALLOCATION_TABLE + "." + KEY_CAPACITY_UNITS + " AS " + SUPPLY_FAN + KEY_CAPACITY_UNITS
        		+"," + SUPPLY_FAN + ALLOCATION_TABLE + "." + KEY_SOURCE + " AS " + SUPPLY_FAN + KEY_SOURCE

        		+"," + RETURN_FAN + ALLOCATION_TABLE +"." + KEY_ALLOCATION_ID + " AS " + RETURN_FAN + KEY_ALLOCATION_ID         		        		        		
        		+"," + RETURN_FAN + ALLOCATION_TABLE + "." + KEY_CAPACITY  + " AS "  + RETURN_FAN + KEY_CAPACITY
        		+"," + RETURN_FAN + ALLOCATION_TABLE + "." + KEY_EFFICIENCY  + " AS "  + RETURN_FAN + KEY_EFFICIENCY
        		+"," + RETURN_FAN + ALLOCATION_TABLE + "." + KEY_RUN_HOURS  + " AS "  + RETURN_FAN + KEY_RUN_HOURS       		
        		+"," + RETURN_FAN + ALLOCATION_TABLE + "." + KEY_DIVERSITY_FACTOR  + " AS "  + RETURN_FAN + KEY_DIVERSITY_FACTOR        		
        		+"," + RETURN_FAN + ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID  + " AS "  + RETURN_FAN + KEY_UTILITY_NAME
        		+"," + RETURN_FAN + ALLOCATION_TABLE + "." + KEY_EFFECIENCY_UNITS + " AS " + RETURN_FAN + KEY_EFFECIENCY_UNITS
        		+"," + RETURN_FAN + ALLOCATION_TABLE + "." + KEY_CAPACITY_UNITS + " AS " + RETURN_FAN + KEY_CAPACITY_UNITS
        		+"," + RETURN_FAN + ALLOCATION_TABLE + "." + KEY_SOURCE + " AS " + RETURN_FAN + KEY_SOURCE

        		+"," + OTHER + ALLOCATION_TABLE +"." + KEY_ALLOCATION_ID + " AS " + OTHER + KEY_ALLOCATION_ID         		        		        		        		
        		+"," + OTHER + ALLOCATION_TABLE + "." + KEY_CAPACITY  + " AS "  + OTHER + KEY_CAPACITY
        		+"," + OTHER + ALLOCATION_TABLE + "." + KEY_EFFICIENCY  + " AS "  + OTHER + KEY_EFFICIENCY
        		+"," + OTHER + ALLOCATION_TABLE + "." + KEY_RUN_HOURS  + " AS "  + OTHER + KEY_RUN_HOURS       		
        		+"," + OTHER + ALLOCATION_TABLE + "." + KEY_DIVERSITY_FACTOR  + " AS "  + OTHER + KEY_DIVERSITY_FACTOR        		
        		+"," + OTHER + ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID  + " AS "  + OTHER + KEY_UTILITY_NAME 
        		+"," + OTHER + ALLOCATION_TABLE + "." + KEY_EFFECIENCY_UNITS + " AS " + OTHER + KEY_EFFECIENCY_UNITS
        		+"," + OTHER + ALLOCATION_TABLE + "." + KEY_CAPACITY_UNITS + " AS " + OTHER + KEY_CAPACITY_UNITS
        		+"," + OTHER + ALLOCATION_TABLE + "." + KEY_SOURCE + " AS " + OTHER + KEY_SOURCE

        		+", * FROM " + EQUIPMENT_TABLE
        		+ " LEFT OUTER JOIN " + ALLOCATION_TABLE + " " + HEATING+ ALLOCATION_TABLE + " ON " + EQUIPMENT_TABLE + "." + KEY_HEATING_ALLOCATION_ID + " = " + HEATING + ALLOCATION_TABLE+ "." + KEY_ALLOCATION_ID  
        		+ " LEFT OUTER JOIN " + ALLOCATION_TABLE + " " + COOLING+ ALLOCATION_TABLE + " ON " + EQUIPMENT_TABLE + "." + KEY_COOLING_ALLOCATION_ID + " = " + COOLING + ALLOCATION_TABLE+ "." + KEY_ALLOCATION_ID         		
        		+ " LEFT OUTER JOIN " + ALLOCATION_TABLE + " " + SUPPLY_FAN+ ALLOCATION_TABLE + " ON " + EQUIPMENT_TABLE + "." + KEY_SUPPLY_FAN_ALLOCATION_ID + " = " + SUPPLY_FAN + ALLOCATION_TABLE+ "." + KEY_ALLOCATION_ID        		
        		+ " LEFT OUTER JOIN " + ALLOCATION_TABLE + " " + RETURN_FAN+ ALLOCATION_TABLE + " ON " + EQUIPMENT_TABLE + "." + KEY_RETURN_FAN_ALLOCATION_ID + " = " + RETURN_FAN + ALLOCATION_TABLE+ "." + KEY_ALLOCATION_ID
        		+ " LEFT OUTER JOIN " + ALLOCATION_TABLE + " " + OTHER+ ALLOCATION_TABLE + " ON " + EQUIPMENT_TABLE + "." + KEY_OTHER_ALLOCATION_ID + " = "  + OTHER + ALLOCATION_TABLE + "." + KEY_ALLOCATION_ID
        		/*+ " LEFT OUTER JOIN " + UTILITY_TABLE + " " + HEATING + UTILITY_TABLE + " ON " + HEATING+ ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID + " = "+ HEATING + UTILITY_TABLE + "." + KEY_UTILITY_ID
				+ " LEFT OUTER JOIN " + UTILITY_TABLE + " " + COOLING + UTILITY_TABLE + " ON " + COOLING+ ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID + " = "+ COOLING + UTILITY_TABLE + "." + KEY_UTILITY_ID
        		+ " LEFT OUTER JOIN " + UTILITY_TABLE + " " + SUPPLY_FAN + UTILITY_TABLE + " ON " + SUPPLY_FAN+ ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID + " = "+ SUPPLY_FAN + UTILITY_TABLE + "." + KEY_UTILITY_ID
        		+ " LEFT OUTER JOIN " + UTILITY_TABLE + " " + RETURN_FAN + UTILITY_TABLE + " ON " + RETURN_FAN+ ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID + " = "+ RETURN_FAN + UTILITY_TABLE + "." + KEY_UTILITY_ID
        		+ " LEFT OUTER JOIN " + UTILITY_TABLE + " " + OTHER + UTILITY_TABLE+ " ON " + RETURN_FAN+ ALLOCATION_TABLE + "." + KEY_UTILITY_FK_ID + " = "+ OTHER + UTILITY_TABLE + "." + KEY_UTILITY_ID 
        		/*+ " INNER JOIN " + ALLOCATION_SUMMARY_TABLE + " " +  " ON " + HEATING + "." + KEY_ALLOCATION_CATEGORY_FK_ID + " = " + ALLOCATION_SUMMARY_TABLE + "." + KEY_ALLOCATION_CATEGORY_ID
        		+ " INNER JOIN " + ALLOCATION_SUMMARY_TABLE + " " +  " ON " + COOLING + "." + KEY_ALLOCATION_CATEGORY_FK_ID + " = " + ALLOCATION_SUMMARY_TABLE + "." + KEY_ALLOCATION_CATEGORY_ID
        		+ " INNER JOIN " + ALLOCATION_SUMMARY_TABLE + " " +  " ON " + SUPPLY_FAN + "." + KEY_ALLOCATION_CATEGORY_FK_ID + " = " + ALLOCATION_SUMMARY_TABLE + "." + KEY_ALLOCATION_CATEGORY_ID
        		+ " INNER JOIN " + ALLOCATION_SUMMARY_TABLE + " " +  " ON " + RETURN_FAN + "." + KEY_ALLOCATION_CATEGORY_FK_ID + " = " + ALLOCATION_SUMMARY_TABLE + "." + KEY_ALLOCATION_CATEGORY_ID
        		+ " INNER JOIN " + ALLOCATION_SUMMARY_TABLE + " " +  " ON " + OTHER + "." + KEY_ALLOCATION_CATEGORY_FK_ID + " = " + ALLOCATION_SUMMARY_TABLE + "." + KEY_ALLOCATION_CATEGORY_ID*/
        		+ " INNER JOIN " + BUILDING_ITEM_TABLE + " ON " + KEY_BUILDINGITEM_ID + " = " + KEY_EQUIPMENT_ID
        		+ " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID() + whereClause;
        
        ////Log.v("DatabaseHandler/getEquipmentInformation","buildingID: " + this.getActiveBuildingID());
        	
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
               
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        	////Log.v("DATABASE EquipmentQuery", "EquipmentFound");
            do {
            	try {
                EquipmentInformation equipmentInformation = new EquipmentInformation();
                equipmentInformation.setEquipmentType(EquipmentType.getEnumType(cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_TYPE))));
                equipmentInformation.setManufactuer(cursor.getString(cursor.getColumnIndex(KEY_MANUFACTURER)));
                equipmentInformation.setModel(cursor.getString(cursor.getColumnIndex(KEY_MODEL)));
                equipmentInformation.setSerial(cursor.getString(cursor.getColumnIndex(KEY_SERIAL_NUMBER)));
                equipmentInformation.setInstallYear(cursor.getString(cursor.getColumnIndex(KEY_INSTALL_YEAR)));
             	equipmentInformation.setQuantity(cursor.getInt(cursor.getColumnIndex(KEY_EQUIPMENT_QTY)));
             	equipmentInformation.setLocation(cursor.getString(cursor.getColumnIndex(KEY_BUILDINGITEM_LOCATION)));
             	equipmentInformation.setServes(cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_SERVES)));
             	equipmentInformation.setControl(cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_CONTROL)));
             	equipmentInformation.setSchedule(cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_SCHEDULE)));
             	equipmentInformation.setNotes(cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_NOTES)));
             	////Log.v("UNITID", "retreiving UnitID name from database..." + cursor.getString(cursor.getColumnIndex(KEY_UNIT_ID_NAME)));
                equipmentInformation.setUnitID(cursor.getString(cursor.getColumnIndex(KEY_UNIT_ID_NAME)));

                for(Function f: Function.values()) {
                		AllocationInformation ai = new AllocationInformation(f);
	                	ai.setSource(cursor.getString(cursor.getColumnIndex(f.name() + KEY_SOURCE)));   
		               	ai.setAllocationID(cursor.getInt(cursor.getColumnIndex(f.name() + KEY_ALLOCATION_ID)));
		                ////Log.v(TAG + "/" + METHOD_NAME, f.name() + " allocationID: " + ai.getAllocationID() + " Column: " + cursor.getColumnIndex(f.name() + KEY_ALLOCATION_ID));
			            ai.setCapacity(cursor.getDouble(cursor.getColumnIndex(f.name() + KEY_CAPACITY)));
			            ai.setEfficiency(cursor.getDouble(cursor.getColumnIndex(f.name() + KEY_EFFICIENCY)));
			            ai.setFuelType(FuelType.getEnumType(cursor.getString(cursor.getColumnIndex(f.name() + KEY_UTILITY_NAME))));
			            ai.setHours(cursor.getDouble(cursor.getColumnIndex(f.name() + KEY_RUN_HOURS)));
			            ai.setPowerUnits(PowerUnits.getEnumType(cursor.getString(cursor.getColumnIndex(f.name() +KEY_CAPACITY_UNITS))));
			            ai.setEfficiencyUnits(EfficiencyUnits.getEnumType(cursor.getString(cursor.getColumnIndex(f.name() + KEY_EFFECIENCY_UNITS))));
			            equipmentInformation.setAllocation(f,ai);
			            ////Log.v(TAG + "/" + METHOD_NAME, f.name() +":" + equipmentInformation.getAllocationCSV(f));//equipmentInformation.setHeatingAllocationCategory(AllocationCategory.getEnumType(cursor.getString(cursor.getColumnIndex(KEY_ALLOCATION_CATEGORY_FK_ID))));
                }
               
        		switch(equipmentInformation.getEquipmentType()) {

        		case BOILER:
        			equipmentInformation.setBoilerType(cursor.getString(cursor.getColumnIndex(KEY_BOILERTYPE)));
        			equipmentInformation.setBoilerMedium(cursor.getString(cursor.getColumnIndex(KEY_BOILERMEDIUM)));
        			break;
        		case CHILLER:
        			equipmentInformation.setChillerType(cursor.getString(cursor.getColumnIndex(KEY_CHILLERTYPE)));
        			equipmentInformation.setChillerCondenserType(cursor.getString(cursor.getColumnIndex(KEY_CHILLERCONDENSERTYPE)));
        			equipmentInformation.setChillerEvaporatorType(cursor.getString(cursor.getColumnIndex(KEY_CHILLEREVAPORATORTYPE)));
        			break;
        		case WATERHEATER:
        			equipmentInformation.setDhwType(cursor.getString(cursor.getColumnIndex(KEY_DHWTYPE)));
        			equipmentInformation.setDhwEnergyFactor(cursor.getDouble(cursor.getColumnIndex(KEY_DHWENERGYFACTOR)));
                    equipmentInformation.setDhwTankSize(cursor.getString(cursor.getColumnIndex(KEY_DHWTANKSIZE)));
        			break;
        		case CW_PUMP:
        		case HW_PUMP:
        		case OTHER_PUMP:
        			equipmentInformation.setPumpModulation(cursor.getString(cursor.getColumnIndex(KEY_PUMPMODULATION)));
        			break;
        		case AIRHANDLER:
        			equipmentInformation.setAHU_PreheatCoil(cursor.getString(cursor.getColumnIndex(KEY_AHU_PREHEATCOIL)));
        			equipmentInformation.setAHU_HeatingCoil(cursor.getString(cursor.getColumnIndex(KEY_AHU_HEATINGCOIL)));
        			equipmentInformation.setAHU_CoolingCoil(cursor.getString(cursor.getColumnIndex(KEY_AHU_COOLINGCOIL)));
        			equipmentInformation.setAHU_TotalCFM(cursor.getString(cursor.getColumnIndex(KEY_AHU_TOTALCFM)));
        		case PACKAGEUNIT:
        			equipmentInformation.setAHU_Economizer(cursor.getString(cursor.getColumnIndex(KEY_AHU_ECONOMIZER)));
        			equipmentInformation.setAHU_EconomizerLockout(cursor.getString(cursor.getColumnIndex(KEY_AHU_ECONOMIZERLOCKOUT)));
        			equipmentInformation.setAHU_EconomizerDamperControl(cursor.getString(cursor.getColumnIndex(KEY_AHU_ECONOMIZERDAMPERCONTROL)));
        			break;
        		case TRANSFORMER:
        			equipmentInformation.setOnPeakPercent(cursor.getDouble(cursor.getColumnIndex(KEY_ONPEAKPERCENT)));
        			equipmentInformation.setOffPeakPercent(cursor.getDouble(cursor.getColumnIndex(KEY_OFFPEAKPERCENT)));
        			equipmentInformation.setOnPeakDays(cursor.getDouble(cursor.getColumnIndex(KEY_ONPEAKDAYS)));
        			equipmentInformation.setPowerFactor(cursor.getDouble(cursor.getColumnIndex(KEY_POWERFACTOR)));
        			break;
        		case REFRIGERATION_EVAPORATOR:
        			equipmentInformation.setRefrigTemp(cursor.getString(cursor.getColumnIndex(KEY_REFRIGTEMP)));
        			equipmentInformation.setMotorType(cursor.getString(cursor.getColumnIndex(KEY_MOTORTYPE)));
        			break;
        		case REFRIGERATION_CONDENSING:
        			equipmentInformation.setRefrigAssociated(cursor.getString(cursor.getColumnIndex(KEY_REFRIGASSOCIATED)));
        			break;					
        		default:
        		}
                
                // Adding equipment to list
                equipmentList.add(equipmentInformation);
                
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        //db.close();
        // return contact list
        return equipmentList;
    }
    
    private int getEquipmentCount(String Where) throws Exception {
        String countQuery = "SELECT * FROM " + BUILDING_ITEM_TABLE + " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID() + 
        		" AND " + KEY_BUILDINGITEM_EXISTS + " = 1" + Where;
        int count = 0;
        ////Log.v(TAG, countQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        try {
	        Cursor cursor = db.rawQuery(countQuery, null);
	        count = cursor.getCount();
            cursor.close();
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }

        return count;
    }

    //make one query
    public int getSpecificEquipmentCount(EquipmentType equipmentType) throws Exception {
    		return getEquipmentCount(" AND " + KEY_EQUIPMENT_TYPE + " = '" + equipmentType.toString() + "'");
    }
    
    //make one query
    public String[] getEquipmentCountListByType() {
        ArrayList<String> equipmentListAndCount = new ArrayList<String>();
    	String countQuery;
        SQLiteDatabase db = this.getReadableDatabase();
		
		try {
			countQuery = "SELECT " + KEY_EQUIPMENT_TYPE + ", COUNT(" +KEY_EQUIPMENT_TYPE + ") AS count FROM " + BUILDING_ITEM_TABLE + " WHERE " + KEY_BUILDING_FK_ID + " = " 
					+ this.getActiveBuildingID() + " AND " + KEY_BUILDINGITEM_EXISTS + " = 1 group by "+ KEY_EQUIPMENT_TYPE;
			Cursor cursor = db.rawQuery(countQuery, null);
		        ////Log.v(TAG, countQuery);
			if (cursor.moveToFirst()) {
				do {
		            	////Log.v(TAG, "row count");
					equipmentListAndCount.add(cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_TYPE))+" (" + cursor.getInt(cursor.getColumnIndex("count")) + ")");
				} while (cursor.moveToNext());
			}
			else {
				equipmentListAndCount.add(Strings.NO_EQUIPMENT);
			}
			cursor.close();

		} catch (Exception e1) {
			e1.printStackTrace();
			equipmentListAndCount.add(Strings.NO_BUILDING);
		}  //"

    	return GeneralFunctions.arrayListToStringArray(equipmentListAndCount);
    }

    /**Returns first line and string array list of all equipment for one specific type
     * 
     * @param eType, equipmentType
     * @param firstRowLine, string of text to add to first row
     * @return arraylist of equipment
     */
    public String[] getEquipmentSummaryList(EquipmentType eType, String firstRowLine ) {
    	
    	//Log.v(TAG, "getEquipmentSummaryList, eType: " + eType.toString());
		ArrayList<String> equipmentSummaryList = new ArrayList<String>();
		String whereClause = " AND " + KEY_EQUIPMENT_TYPE + "='" +eType.toString() + "'";
    	try { 
    		if (eType.getBuildingItemType().equals(BuildingItemType.LIGHT)) {

    			List<LightingInformation> lightList = getLightingInformation(whereClause);
    			if (firstRowLine != null) {
    				equipmentSummaryList.add(firstRowLine); 
    			}
    			for(LightingInformation light:lightList) {
    				////Log.v(TAG, "Hours: " + light.getAnnualHours());
    				equipmentSummaryList.add(light.getDisplayName());
    			}
    		}
            //water
            else if(eType.getBuildingItemType().equals(BuildingItemType.WATER_FIXTURE)) {
                List<WaterInformation> waterInformationList = getWaterInformation("");
                if(firstRowLine != null) {
                    equipmentSummaryList.add(firstRowLine);
                }
                for(WaterInformation water:waterInformationList) {
                    equipmentSummaryList.add(water.getDisplayName());
                }
            }
            else if(eType.getBuildingItemType().equals((BuildingItemType.ENVELOPE))) {
                List<EnvelopeInformation> envelopeInformationList = getEnvelopeInformation(whereClause);
                if(firstRowLine != null) {
                    equipmentSummaryList.add(firstRowLine);
                }
                for(EnvelopeInformation envelope:envelopeInformationList) {
                    equipmentSummaryList.add(envelope.getDisplayName());
                }
            } else if(eType.getBuildingItemType().equals((BuildingItemType.UTILITYMETER))) {
                List<UtilityMeterInformation> um = getUtilityMeterInformation(whereClause);
                if(firstRowLine != null)
                    equipmentSummaryList.add(firstRowLine);
                for(UtilityMeterInformation um1:um) {
                    equipmentSummaryList.add(um1.getDisplayName());
                }
            } else {

    			//Log.v(TAG, "getEquipmentSummaryList, Where: " + whereClause);
				List<EquipmentInformation> equipmentList = getEquipmentInformation(whereClause);
    			if (!(firstRowLine == null)) {
    				equipmentSummaryList.add(firstRowLine); 
    			}
				for(EquipmentInformation equipment:equipmentList) {
					equipmentSummaryList.add(equipment.getDisplayName());
				}
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
			equipmentSummaryList.add(Strings.NO_BUILDING);
		}

        return GeneralFunctions.arrayListToStringArray(equipmentSummaryList);
    }
    
    /**Returns the activeid.  Boolean determine whether to look in the lighting table or equipment table
     * 
     * @return id, primary id of "active" building item in active facility
     * @throws NullPointerException
     */
    private int getActiveBuildingItemID() throws NullPointerException {	return getActiveID(KEY_BUILDINGITEM_FK_ID); }

	private int getActiveID(String columnIndex) throws NullPointerException {
		String selectQuery = "SELECT " + columnIndex + " FROM " + FACILITY_TABLE + " WHERE " + KEY_FACILITY_ID + " = " + this.getActiveFacilityID();
		//Log.v(this.toString(), selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			//Log.v(this.toString() + "getActiveID()", cursor.toString());
			int test = cursor.getInt(cursor.getColumnIndex(columnIndex));
			cursor.close();
			//Log.v(TAG, "getActiveBuildingItemID, equipmentID: " + test);
			return test;
		}

		throw new NullPointerException("No equipment exists with this ID");
	}

	private int getActiveRealOrNotBuildingItemID() { return getActiveID(KEY_FACILITY_BUILDINGITEM_FK_ID); }
    
    public int getActiveEquipmentIndexByType(EquipmentType eType) { return getGeneralEquipmentIndexByType(eType, getActiveBuildingItemID()); }

    public int getActiveRealOrNotEquipmentIndexByType(EquipmentType eType) {

        return getGeneralEquipmentIndexByType(eType, getActiveBuildingItemID());

    }

    private int getGeneralEquipmentIndexByType(EquipmentType eType, int activeID) {
        final String METHOD_NAME = "getActiveEquipmentIndexByType, ";

        ////Log.v("DatabaseHandler/getActiveEquipmentIndexByType", "eType: " + eType.toString());

        ////Log.v("DatabaseHandler/getActiveEquipmentTypeByIndex", "id: " + activeID);
        int counter = 0;
        int isReal;
        String sql = "SELECT * FROM " + BUILDING_ITEM_TABLE + " WHERE " + KEY_BUILDING_FK_ID + "=" + this.getActiveBuildingID() + " AND " + KEY_EQUIPMENT_TYPE + "='" + eType.toString() +"'";
        SQLiteDatabase db = this.getReadableDatabase();


        //query parameters
        Cursor cursor = db.rawQuery(sql, null);

        //Log.v(TAG, METHOD_NAME + "Num Items Found: " +cursor.getCount());
        while(cursor.moveToNext()) {
            isReal = cursor.getInt(cursor.getColumnIndex(KEY_BUILDINGITEM_EXISTS));
            //Log.v(TAG, METHOD_NAME + "looping..." + "Active Index: " + cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_TYPE)));
            if(cursor.getInt(cursor.getColumnIndex(KEY_BUILDINGITEM_ID)) == activeID) {
                if(isReal==0)
                    return -1;
                else {
                    //Log.v(TAG, METHOD_NAME + "Match found! + index: " + counter);
                    return counter;
                }
            }
            else {
                //Log.v(TAG, METHOD_NAME + "adding..");
            }
            if(isReal==1)
                counter++;
        }

        throw new NullPointerException("No active equipment");
    }
              
    /**This function updates an existing equipment
     * It uses the activeEqupiment as the equipment to update
     * 
     * @param equipment, equipmentInformation
     */
    public void updateEquipment(EquipmentInformation equipment) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = equipmentToContentValues(equipment, false);
    	db.update(EQUIPMENT_TABLE, values,  KEY_EQUIPMENT_ID + " = ?", new String[] { String.valueOf(getActiveBuildingItemID()) });

        updateLocation(equipment.getLocation());
    }

	public void updateEquipmentType(EquipmentType eType) {
		if(eType.getBuildingItemType() == getActiveEquipmentType().getBuildingItemType()) {
			SQLiteDatabase db = this.getReadableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_EQUIPMENT_TYPE, eType.toString());
			db.update(BUILDING_ITEM_TABLE, values, KEY_BUILDINGITEM_ID + " =?", new String[]{String.valueOf(getActiveBuildingItemID())});
		}
		else
			throw new IllegalArgumentException("Cannot switch from " + getActiveEquipmentType().getBuildingItemType().toString() + " to " + eType.getBuildingItemType());

	}
    
    public void updateLighting(LightingInformation light) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = lightingToContentValues(light);
    	db.update(LIGHTING_TABLE, values, KEY_LIGHTING_ID + " = ?", new String[] {String.valueOf(getActiveBuildingItemID()) });

        updateLocation(light.getLocation());
    }

	private void deleteActiveBuildingItem() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(BUILDING_ITEM_TABLE, KEY_BUILDINGITEM_ID + " = ?",
				new String[]{String.valueOf(getActiveBuildingItemID())});

		//Set active building item to first
		Cursor cursor = db.query(BUILDING_ITEM_TABLE,null,KEY_BUILDING_FK_ID +"=" + this.getActiveBuildingID(),null,null,null,null);
		if(cursor.moveToFirst()) {
			setBuildingItemIDSettings(cursor.getInt(cursor.getColumnIndex(KEY_BUILDINGITEM_ID)));
		}
	}

	public void deleteEquipment() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EQUIPMENT_TABLE, KEY_EQUIPMENT_ID + " = ?",
				new String[]{String.valueOf(getActiveBuildingItemID())});
		deleteActiveBuildingItem();
    }
	
	public void deleteLight() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(LIGHTING_TABLE, KEY_LIGHTING_ID + " = ?", new String[] {String.valueOf(getActiveBuildingItemID()) });
		deleteActiveBuildingItem();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------UTILITY METER----------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////
	public void AddUtilityMeter(UtilityMeterInformation utilityMeterInformation) throws IOException {

		ContentValues values = UtilityMeterToContentValues(utilityMeterInformation);
		values.put(KEY_UM_ID, addBuildingItem(EquipmentType.UTILITY_METER, utilityMeterInformation.getLocation(),true));
		SQLiteDatabase db = this.getWritableDatabase();

		db.insert(UTILITY_METER_TABLE, null, values);

	}

	private ContentValues UtilityMeterToContentValues(UtilityMeterInformation utilityMeterInformation) {
		ContentValues values = new ContentValues();

		values.put(KEY_UM_METER_ID, utilityMeterInformation.getMeterSerial());
		values.put(KEY_UM_METER_READ, utilityMeterInformation.getMeterReading());
		values.put(KEY_UM_UTILITY_PROVIDER, utilityMeterInformation.getMeterUtilityProvider());
		values.put(KEY_UM_METER_UTILITY, utilityMeterInformation.getMeterUtilityType());
		values.put(KEY_UM_METER_UNITS, utilityMeterInformation.getMeterUtilityUnits());
		values.put(KEY_UM_NOTES, utilityMeterInformation.getMeterNotes());

		return values;
	}

	public UtilityMeterInformation getActiveUtilityMeter() {
		String whereClause = " AND " + KEY_UM_ID + " = " + this.getActiveBuildingItemID();
		////Log.v(TAG, whereClause);
		List<UtilityMeterInformation> oneMeter = getUtilityMeterInformation(whereClause);

		return oneMeter.get(0);
	}

	public List<UtilityMeterInformation> getSpecificUtilityMeterInformation(EquipmentType eType) throws Exception {

		return getUtilityMeterInformation("");
	}

	public List<UtilityMeterInformation> getAllUtilityMeterInformation() {
		return getUtilityMeterInformation("");
	}

	private List<UtilityMeterInformation> getUtilityMeterInformation(String whereClause) {

		List<UtilityMeterInformation> meterList = new ArrayList<UtilityMeterInformation>();
		//final String METHOD_NAME = "getEquipmentInformation()";
		// Select All Query
		String selectQuery = "SELECT * FROM "
				+ UTILITY_METER_TABLE
				+ " INNER JOIN " + BUILDING_ITEM_TABLE + " ON " + KEY_UM_ID + " = " + KEY_BUILDINGITEM_ID
				+ " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID() + whereClause;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		UtilityMeterInformation utilityMeterInformation;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			////Log.v("DATABASE EquipmentQuery", "EquipmentFound");
			do {
				try {
					utilityMeterInformation = new UtilityMeterInformation();
                    utilityMeterInformation.setLocation(cursor.getString(cursor.getColumnIndex(KEY_BUILDINGITEM_LOCATION)));
					utilityMeterInformation.setMeterSerial(cursor.getString(cursor.getColumnIndex(KEY_UM_METER_ID)));
					utilityMeterInformation.setMeterReading(cursor.getDouble(cursor.getColumnIndex(KEY_UM_METER_READ)));
					utilityMeterInformation.setMeterUtilityProvider(cursor.getString(cursor.getColumnIndex(KEY_UM_UTILITY_PROVIDER)));
					utilityMeterInformation.setMeterUtilityType(cursor.getString(cursor.getColumnIndex(KEY_UM_METER_UTILITY)));
					utilityMeterInformation.setMeterUtilityUnits(cursor.getString(cursor.getColumnIndex(KEY_UM_METER_UNITS)));
					utilityMeterInformation.setMeterNotes(cursor.getString(cursor.getColumnIndex(KEY_UM_NOTES)));
					meterList.add(utilityMeterInformation);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} while (cursor.moveToNext());

		}

		return meterList;
	}
	//update water
	public void updateUtilityMeter(UtilityMeterInformation utilityMeterInformation) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = UtilityMeterToContentValues(utilityMeterInformation);
		db.update(UTILITY_METER_TABLE, values, KEY_UM_ID + " = ?", new String[]{String.valueOf(getActiveBuildingItemID())});

        updateLocation(utilityMeterInformation.getLocation());

	}

	//delete water
	public void deleteUtilityMeter() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(UTILITY_METER_TABLE, KEY_UM_ID + " = ?", new String[]{String.valueOf(getActiveBuildingItemID())});
		deleteActiveBuildingItem();

	}


/////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------WATER----------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////
    //addWater
    public void AddWaterEquipment(WaterInformation waterInformation) throws IOException {

        ContentValues values = WaterToContentValues(waterInformation);
        values.put(KEY_WATER_ID, addBuildingItem(EquipmentType.WATER_ROOM, waterInformation.getLocation(), true));
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(WATER_TABLE, null, values);

        ////Log.v(TAG, "light added. id: " + id);

    }

    //TODO getWaterInformation (multiple functions)
	public WaterInformation getActiveWater() {
		String whereClause = " AND " + KEY_WATER_ID + " = " + this.getActiveBuildingItemID();
		////Log.v(TAG, whereClause);
		List<WaterInformation> oneWater = getWaterInformation(whereClause);

		return oneWater.get(0);
	}


	//waterToContentValues
	private ContentValues WaterToContentValues(WaterInformation waterInformation) {
		ContentValues values = new ContentValues();

		values.put(KEY_WATER_SINK_FLOW_RATE, waterInformation.getSinkFlowRate());
		values.put(KEY_WATER_SINK_QTY, waterInformation.getSinkQuantity());
        values.put(KEY_WATER_SINK_FLOW_RATE_2, waterInformation.getSink2FlowRate());
        values.put(KEY_WATER_SINK_QTY_2, waterInformation.getSink2Quantity());
        values.put(KEY_WATER_SHOWER_FLOW_RATE, waterInformation.getShowerFlowRate());
        values.put(KEY_WATER_SHOWER_QTY, waterInformation.getShowerQuantity());
		values.put(KEY_WATER_TOILET_FLOW_RATE, waterInformation.getToiletFlowRate());
		values.put(KEY_WATER_TOILET_QTY, waterInformation.getToiletQuantity());
		values.put(KEY_WATER_URINAL_FLOW_RATE, waterInformation.getUrinalFlowRate());
		values.put(KEY_WATER_URINAL_QTY, waterInformation.getUrinalQuantity());
		values.put(KEY_WATER_NOTES, waterInformation.getNotes());

		return values;
	}


	public List<WaterInformation> getSpecificWaterInformation(EquipmentType eType) throws Exception {

        return getWaterInformation("");
    }

    public List<WaterInformation> getAllWaterInformation() {
        return getWaterInformation("");
    }

    private List<WaterInformation> getWaterInformation(String whereClause) {

        List<WaterInformation> waterList = new ArrayList<WaterInformation>();
        //final String METHOD_NAME = "getEquipmentInformation()";
        // Select All Query
        String selectQuery = "SELECT * FROM "
                + WATER_TABLE
                + " INNER JOIN " + BUILDING_ITEM_TABLE + " ON " + KEY_WATER_ID + " = " + KEY_BUILDINGITEM_ID
                + " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID() + whereClause;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        WaterInformation waterInformation;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            ////Log.v("DATABASE EquipmentQuery", "EquipmentFound");
            do {
                try {
                    waterInformation = new WaterInformation();
                    waterInformation.setLocation(cursor.getString(cursor.getColumnIndex(KEY_BUILDINGITEM_LOCATION)));
                    waterInformation.setSinkFlowRate(cursor.getString(cursor.getColumnIndex(KEY_WATER_SINK_FLOW_RATE)));
                    waterInformation.setSinkQuantity(cursor.getInt(cursor.getColumnIndex(KEY_WATER_SINK_QTY)));
                    waterInformation.setSink2FlowRate(cursor.getString(cursor.getColumnIndex(KEY_WATER_SINK_FLOW_RATE_2)));
                    waterInformation.setSink2Quantity(cursor.getInt(cursor.getColumnIndex(KEY_WATER_SINK_QTY_2)));
                    waterInformation.setShowerFlowRate(cursor.getString(cursor.getColumnIndex(KEY_WATER_SHOWER_FLOW_RATE)));
                    waterInformation.setShowerQuantity(cursor.getInt(cursor.getColumnIndex(KEY_WATER_SHOWER_QTY)));
                    waterInformation.setToiletFlowRate(cursor.getString(cursor.getColumnIndex(KEY_WATER_TOILET_FLOW_RATE)));
                    waterInformation.setToiletQuantity(cursor.getInt(cursor.getColumnIndex(KEY_WATER_TOILET_QTY)));
                    waterInformation.setUrinalFlowRate(cursor.getString(cursor.getColumnIndex(KEY_WATER_URINAL_FLOW_RATE)));
                    waterInformation.setUrinalQuantity(cursor.getInt(cursor.getColumnIndex(KEY_WATER_URINAL_QTY)));
                    waterInformation.setNotes(cursor.getString(cursor.getColumnIndex(KEY_WATER_NOTES)));
                    waterList.add(waterInformation);
                } catch (Exception e) {
                        e.printStackTrace();
                    }

                } while (cursor.moveToNext());

        }

        return waterList;
    }
    //update water
    public void updateWater(WaterInformation water) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = WaterToContentValues(water);
        db.update(WATER_TABLE, values, KEY_WATER_ID + " = ?", new String[] {String.valueOf(getActiveBuildingItemID()) });

        updateLocation(water.getLocation());

    }

    //delete water
    public void deleteWater() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WATER_TABLE, KEY_WATER_ID + " = ?", new String[] {String.valueOf(getActiveBuildingItemID()) });
		deleteActiveBuildingItem();
    }
////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------Envelope categories---------------------------------------------
/////////////////////////////////////////////////////////////////////////////////////////////////
public void AddEnvelopeEquipment(EnvelopeInformation envelopeInformation) throws IOException {

    ContentValues values = EnvelopeToContentValues(envelopeInformation);
    values.put(KEY_ENVELOPE_ID, addBuildingItem(envelopeInformation.getEquipmentType(), envelopeInformation.getLocation(), true));
    SQLiteDatabase db = this.getWritableDatabase();

    db.insert(ENVELOPE_TABLE, null, values);

}

//TODO get envelopeInfo (multiple public functions)

    /**Returns active envelope item
     *
     * @return EnvelopeInformation active object
     */
    public EnvelopeInformation getActiveEnvelope() {
        String whereClause = " AND " + KEY_ENVELOPE_ID + " = " + this.getActiveBuildingItemID();
        ////Log.v(TAG, whereClause);
        List<EnvelopeInformation> oneEnvelope = getEnvelopeInformation(whereClause);

        return oneEnvelope.get(0);
    }

    public List<EnvelopeInformation> getAllEnvelopeInformation() {
        return getEnvelopeInformation("");
    }

    public List<EnvelopeInformation> getSpecificEnvelopeInformation(EquipmentType eType) throws Exception {

            return getEnvelopeInformation(" AND " + KEY_EQUIPMENT_TYPE +" = '" + eType.toString()+"'");
    }

    private List<EnvelopeInformation> getEnvelopeInformation(String whereClause) {

        List<EnvelopeInformation> envelopeList = new ArrayList<EnvelopeInformation>();
        //final String METHOD_NAME = "getEquipmentInformation()";
        // Select All Query
        String selectQuery = "SELECT * FROM "
                + ENVELOPE_TABLE
                + " INNER JOIN " + BUILDING_ITEM_TABLE + " ON " + KEY_ENVELOPE_ID + " = " + KEY_BUILDINGITEM_ID
                + " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID() + whereClause;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        EnvelopeInformation envelopeInformation;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            ////Log.v("DATABASE EquipmentQuery", "EquipmentFound");
            do {
                try {
                    envelopeInformation = new EnvelopeInformation();
					envelopeInformation.setLocation(cursor.getString(cursor.getColumnIndex(KEY_BUILDINGITEM_LOCATION)));
					envelopeInformation.setHeight(cursor.getDouble(cursor.getColumnIndex(KEY_ENVELOPE_HEIGHT)));
					envelopeInformation.setRValue(cursor.getDouble(cursor.getColumnIndex(KEY_ENVELOPE_RVALUE)));
					envelopeInformation.setWindowOperatingType(cursor.getString(cursor.getColumnIndex(KEY_ENVELOPE_WINDOW_TYPE)));
					envelopeInformation.setColor(cursor.getString(cursor.getColumnIndex(KEY_ENVELOPE_COLOR)));
					envelopeInformation.setNotes(cursor.getString(cursor.getColumnIndex(KEY_ENVELOPE_NOTES)));
					envelopeInformation.setInsulationType(cursor.getString(cursor.getColumnIndex(KEY_ENVELOPE_INSULALTION_TYPE)));
					envelopeInformation.setDoorMaterial(cursor.getString(cursor.getColumnIndex(KEY_ENVELOPE_DOOR_TYPE)));
					envelopeInformation.setGlassType(cursor.getString(cursor.getColumnIndex(KEY_ENVELOPE_GLASS_TYPE)));
					envelopeInformation.setWidth(cursor.getDouble(cursor.getColumnIndex(KEY_ENVELOPE_LENGTH)));
					envelopeInformation.setDoorMaterial(cursor.getString(cursor.getColumnIndex(KEY_ENVELOPE_DOOR_MATERIAL)));
					envelopeInformation.setQuantity(cursor.getInt(cursor.getColumnIndex(KEY_ENVELOPE_QUANTITY)));
					envelopeInformation.setFrameType(cursor.getString(cursor.getColumnIndex(KEY_ENVELOPE_FRAME_TYPE)));
					envelopeList.add(envelopeInformation);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());

        }

        return envelopeList;
    }

    private ContentValues EnvelopeToContentValues(EnvelopeInformation envelopeInformation) {
        ContentValues values = new ContentValues();

		values.put(KEY_ENVELOPE_HEIGHT, envelopeInformation.getHeight());
		values.put(KEY_ENVELOPE_RVALUE, envelopeInformation.getRValue());
		values.put(KEY_ENVELOPE_WINDOW_TYPE, envelopeInformation.getWindowOperatingType());
		values.put(KEY_ENVELOPE_COLOR, envelopeInformation.getColor());
		values.put(KEY_ENVELOPE_NOTES, envelopeInformation.getNotes());
		values.put(KEY_ENVELOPE_INSULALTION_TYPE, envelopeInformation.getInsulationType());
		values.put(KEY_ENVELOPE_DOOR_TYPE, envelopeInformation.getDoorMaterial());
		values.put(KEY_ENVELOPE_GLASS_TYPE, envelopeInformation.getGlassType());
		values.put(KEY_ENVELOPE_LENGTH, envelopeInformation.getWidth());
		values.put(KEY_ENVELOPE_DOOR_MATERIAL, envelopeInformation.getDoorMaterial());
		values.put(KEY_ENVELOPE_QUANTITY, envelopeInformation.getQuantity());
		values.put(KEY_ENVELOPE_FRAME_TYPE, envelopeInformation.getFrameType());

        return values;
    }

    //update envelope info
    public void updateEnvelope(EnvelopeInformation envelope) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = EnvelopeToContentValues(envelope);
        db.update(ENVELOPE_TABLE, values, KEY_ENVELOPE_ID + " = ?", new String[] {String.valueOf(getActiveBuildingItemID()) });
        updateLocation(envelope.getLocation());

    }

    //delete envelope
    public void deleteEnvelope() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ENVELOPE_TABLE, KEY_ENVELOPE_ID + " = ?", new String[] {String.valueOf(getActiveBuildingItemID()) });
		deleteActiveBuildingItem();
    }
//////////////////////////////////////////////////////
//------------------------------Allocation categories
//////////////////////////////////////////////////////
   /* private int getAllocationID(AllocationCategory aCat) throws Exception {
        String selectQuery = "SELECT  * FROM " + ALLOCATION_SUMMARY_TABLE + "WHERE " + KEY_ALLOCATION_CATEGORY_NAME + " LIKE " + aCat.toString();
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            return cursor.getInt(1);
        }
        
        throw new Exception();
	}*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//--------------------------------------------------buildings-------------------------------------------------------------------------
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// add new building
    public long addBuilding(Building building) {

    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	
     	values.put(KEY_BUILDING_NAME, building.getBuildingName());
     	values.put(KEY_BUILDING_NUMBER, building.getBuildingNumber());
     	values.put(KEY_BUILDING_ADDRESS, building.getBuildingAddress());   
     	values.put(KEY_BUILDING_CITY, building.getBuildingCity());   
     	values.put(KEY_BUILDING_STATE, building.getBuildingState());   
     	values.put(KEY_BUILDING_ZIP, building.getBuildingZip());   
     	values.put(KEY_BUILDING_FUNCTION, building.getBuildingFunction());   
     	values.put(KEY_BUILDING_SCHEDULE, building.getBuildingSchedule());  
     	values.put(KEY_BUILDING_AREA, building.getBuildingSquareFoot());
     	values.put(KEY_BUILDING_FACILITY_FK_ID, this.getActiveFacilityID());
     	////Log.v(TAG, "# of floors: " + building.getBuildingFloors());
     	values.put(KEY_BUILDING_FLOORS, building.getBuildingFloors());
     	values.put(KEY_BUILIDNG_HVAC_SCHEDULE,building.getHvacSchedule());
     	values.put(KEY_BUILDING_YEAR_BUILT,building.getYearBuilt());
     	values.put(KEY_BUILDING_NUM_PCS,building.getNumPCs());
     	values.put(KEY_BUILDING_ROOF_TYPE,building.getRoofType());
     	values.put(KEY_BUILDING_WINDOW_TYPE,building.getWindowType());
     	values.put(KEY_BUILDING_WALL_TYPE,building.getWallType());
     	values.put(KEY_BUILDING_FOUNDATION_TYPE,building.getFoundationType());
    	
    	long id = db.insert(BUILDING_TABLE, null, values);
    	
    	//make this building the most current building
    	this.setBuildingIDSettings((int) id);
    	
    	return id;
    	//db.close(); // Closing database connection    	
    }
    
    public Building getActiveBuilding() throws NullPointerException {
    	Building building = new Building();
    	String selectQuery = "SELECT * FROM " + BUILDING_TABLE + " WHERE " + KEY_BUILDING_ID + " = " + this.getActiveBuildingID();
        
    	SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if(cursor.moveToFirst()) {
        	building.setBuildingName(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_NAME)));
        	building.setBuildingNumber(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_NUMBER)));
        	building.setBuildingAddress(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_ADDRESS)));
        	building.setBuildingCity(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_CITY)));
        	building.setBuildingState(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_STATE)));
        	building.setBuildingZip(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_ZIP)));
        	building.setBuildingFunction(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_FUNCTION)));
        	building.setBuildingSquareFoot(cursor.getInt(cursor.getColumnIndex(KEY_BUILDING_AREA)));
        	building.setBuildingSchedule(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_SCHEDULE)));
        	building.setBuildingFloors(cursor.getInt(cursor.getColumnIndex(KEY_BUILDING_FLOORS)));
        	building.setHvacSchedule(cursor.getString(cursor.getColumnIndex(KEY_BUILIDNG_HVAC_SCHEDULE)));
        	building.setYearBuilt(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_YEAR_BUILT)));
        	building.setNumPCs(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_NUM_PCS)));
        	building.setRoofType(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_ROOF_TYPE)));
        	building.setWindowType(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_WINDOW_TYPE)));
        	building.setWallType(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_WALL_TYPE)));
        	building.setFoundationType(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_FOUNDATION_TYPE)));

            return building;
        }
        
        throw new NullPointerException();
    }
    
    public String[] getBuildingList() {
    	
    	ArrayList<String> buildingList = new ArrayList<String>();
    	
    	try {

    	String selectQuery = "SELECT * FROM " + BUILDING_TABLE + " WHERE " + KEY_BUILDING_FACILITY_FK_ID +"=" + this.getActiveFacilityID();
    
    	SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	buildingList.add(cursor.getString(cursor.getColumnIndex(KEY_BUILDING_NAME)));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
    	} 	catch (Exception e) {
    		////Log.v(TAG,""+e.getMessage());
    	}
        
        if (buildingList.isEmpty())
        	buildingList.add(Strings.NO_BUILDING);

    	return GeneralFunctions.arrayListToStringArray(buildingList);
    }
    
    public int updateBuilding(Building building) {
    	return updateBuilding(building, getActiveBuildingID());
    }
    
    private int updateBuilding(Building building, int buildingID) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();     
     	values.put(KEY_BUILDING_NAME, building.getBuildingName());
        values.put(KEY_BUILDING_NUMBER, building.getBuildingNumber());
     	values.put(KEY_BUILDING_ADDRESS, building.getBuildingAddress());   
     	values.put(KEY_BUILDING_CITY, building.getBuildingCity());   
     	values.put(KEY_BUILDING_STATE, building.getBuildingState());   
     	values.put(KEY_BUILDING_ZIP, building.getBuildingZip());   
     	values.put(KEY_BUILDING_FUNCTION, building.getBuildingFunction());   
     	values.put(KEY_BUILDING_SCHEDULE, building.getBuildingSchedule());  
     	values.put(KEY_BUILDING_AREA, building.getBuildingSquareFoot()); 
     	values.put(KEY_BUILDING_FLOORS, building.getBuildingFloors());
        values.put(KEY_BUILIDNG_HVAC_SCHEDULE,building.getHvacSchedule());
        values.put(KEY_BUILDING_YEAR_BUILT,building.getYearBuilt());
        values.put(KEY_BUILDING_NUM_PCS,building.getNumPCs());
        values.put(KEY_BUILDING_ROOF_TYPE,building.getRoofType());
        values.put(KEY_BUILDING_WINDOW_TYPE,building.getWindowType());
        values.put(KEY_BUILDING_WALL_TYPE,building.getWallType());
        values.put(KEY_BUILDING_FOUNDATION_TYPE,building.getFoundationType());
     
        // updating row
        return db.update(BUILDING_TABLE, values, KEY_BUILDING_ID + " = ?",
                new String[] { String.valueOf(buildingID) });
    }
//////////////////////////////////////////////////////////////////////////////////////////////
//---------------------------------Facility---------------------------------------------
//////////////////////////////////////////////////////////////////////////////////////////////
    public void addFacility(Facility facility) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();     
     	values.put(KEY_FACILITY_NAME, facility.getFacilityName());
     	
     	long id = db.insert(FACILITY_TABLE, null, values);
     	
     	setActiveFacility((int) id);

    }
    
    public void updateFacility(Facility facility) {

    	ContentValues values = new ContentValues();
    	values.put(KEY_FACILITY_NAME, facility.getFacilityName());
    	values.put(KEY_FACILITY_ID, this.getActiveFacilityID());

		updateActiveFacility(values);

    }
    
    public int getActiveBuildingID() {
        String selectQuery = "SELECT  * FROM " + FACILITY_TABLE + " WHERE " + KEY_FACILITY_ID + " = " + this.getActiveFacilityID();
    	
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
        	return cursor.getInt(cursor.getColumnIndex(KEY_FACILITY_FK_BUILDING_ID));
        }
        
        throw new NullPointerException("No building added???");
    }
    
    /**sets the act. building with the building index
     * 
     * @param index, is number selected in dropdown
     */
    public void setActiveBuildingID(int index) {
 
        String selectQuery = "SELECT * FROM " + BUILDING_TABLE + " WHERE " + KEY_BUILDING_FACILITY_FK_ID +"=" + this.getActiveFacilityID();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int buildingID = 2;
        
        if (cursor.move(index + 1)) {
	       buildingID = cursor.getInt(cursor.getColumnIndex(KEY_BUILDING_ID));
	    }

        //Log.v(TAG, "setActiveBuildingID, Building ID: " + buildingID);
        
        this.setBuildingIDSettings(buildingID);
    }
    
    /**sets the active building with the building id
     * 
     * @param id, sets the active building id
     */
    private void setBuildingIDSettings(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String strFilter = KEY_FACILITY_ID +"=" + this.getActiveFacilityID();
        ////Log.v(TAG, "..." + id );
        
        ContentValues values = new ContentValues();     
      	values.put(KEY_FACILITY_FK_BUILDING_ID, id);
  		//values.put(KEY_FACILITY_ID, this.getActiveFacilityID());

      	//Log.v("contentValues",values.toString());
        // update or insert row
        db.update(FACILITY_TABLE, values, strFilter, null);

    }
    
    //function returns index of active building
    public int getActiveBuildingIndex() throws NullPointerException {
        String selectQuery;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        int buildingID;
       
        buildingID = this.getActiveBuildingID();

        //need to add function to count number of buildings where that is the id
        selectQuery = "SELECT * FROM " + BUILDING_TABLE + " WHERE " + KEY_BUILDING_FACILITY_FK_ID +"="+ this.getActiveFacilityID();
        cursor = db.rawQuery(selectQuery, null);
        int counter = 0;
        
        while(cursor.moveToNext()) {
        	if (buildingID == cursor.getInt(cursor.getColumnIndex(KEY_BUILDING_ID))) {
        		break;
        	}
        	else
        		counter++;
        }
        
        cursor.close();
        //returns 1, b/c it has a default value if no buildings

        ////Log.v(TAG, "1483, counter: " + counter);
        return counter;

    }
    
    /** Function returns array list off all facilities
     * 
     */
    public ArrayList<String> getFacilityList() {

    	return facilityList("");
    }
    
    private ArrayList<String> facilityList(String whereArgs) {
    	ArrayList<String> facilityList  = new ArrayList<String>();
    	
        String selectQuery;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String facilityName;
        
        selectQuery = "SELECT * FROM " + FACILITY_TABLE + whereArgs;
        cursor = db.rawQuery(selectQuery, null);
        
        while(cursor.moveToNext()) {
        	facilityName = cursor.getString(cursor.getColumnIndex(KEY_FACILITY_NAME));
        	facilityList.add(facilityName);
        }
        
        //add default facility
        if (facilityList.isEmpty()) {
        	Facility facility = new Facility();
        	facility.setFacilityName(Strings.DEFAULT_FACILITY);
        	this.addFacility(facility);
        	facilityList.add(Strings.DEFAULT_FACILITY);
        }
        ////Log.v(TAG, facilityList.get(0).toString());
    	return facilityList;

    }
    
    public String getActiveFacilityName() {
    	return facilityList(" WHERE " + KEY_FACILITY_ID + "=" +this.getActiveFacilityID()).get(0);
    }
    
    public void output() {
    	List<EquipmentInformation> equipmentList;
		try {
			equipmentList = this.getAllEquipmentInformation();
	    	for(EquipmentInformation equipment: equipmentList) {
	    		System.out.println(equipment.toString());
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    /**This function returns # of buildings in the active facility
     * 
     * @return buildingCount, int
     */
    public int getActiveFacilityBuildingCount() {
    	String[] buildings = getBuildingList();
    	
    	return buildings.length+1;
    }
    
    private void setBuildingItemIDSettings(int id) {

        ContentValues values = new ContentValues();  
     	values.put(KEY_BUILDINGITEM_FK_ID, id);
		values.put(KEY_FACILITY_BUILDINGITEM_FK_ID, id);
    	values.put(KEY_FACILITY_ID, this.getActiveFacilityID());

		updateActiveFacility(values);

    }

	private void setBuildingItemRealOrNotSettings(int id) {

		ContentValues values = new ContentValues();
		values.put(KEY_FACILITY_BUILDINGITEM_FK_ID, id);
		values.put(KEY_FACILITY_ID, this.getActiveFacilityID());

		updateActiveFacility(values);
	}

	public void setActivePhotoProperties(SiteMedia mediaInfo) {
		ContentValues values = new ContentValues();
		values.put(KEY_FACILITY_ID, this.getActiveFacilityID());
		try {
			values.put(KEY_MEASURE_LIST_FK_ID, this.getMeasureIdByActiveEquipmentAndName(mediaInfo));
		} catch (NullPointerException npe) {
			values.put(KEY_MEASURE_LIST_FK_ID, (String) null);
		}
		values.put(KEY_PHOTO_TAG_TEXT, mediaInfo.getPhotoTag());
		values.put(KEY_PHOTO_NOTES_TEXT, mediaInfo.getNotes());

		updateActiveFacility(values);
	}

	public SiteMedia getActivePhotoProperties() {
		SQLiteDatabase db = this.getReadableDatabase();
		String strFilter = KEY_FACILITY_ID +"=" + this.getActiveFacilityID();
		SiteMedia media = new SiteMedia();

		Cursor cursor = db.query(FACILITY_TABLE, null, strFilter, null, null, null, null);

		if(cursor.moveToFirst()) {
			media.setNotes(cursor.getString(cursor.getColumnIndex(KEY_PHOTO_NOTES_TEXT)));
			media.setMeasureID(cursor.getInt(cursor.getColumnIndex(KEY_MEASURE_LIST_FK_ID)));
			media.setPhotoTag(cursor.getString(cursor.getColumnIndex(KEY_PHOTO_TAG_TEXT)));
		}

		return media;
	}

	private void updateActiveFacility(ContentValues values) {
		SQLiteDatabase db = this.getWritableDatabase();
		String strFilter = KEY_FACILITY_ID +"=" + this.getActiveFacilityID();

        //either one:
        db.update(FACILITY_TABLE, values, strFilter, null);
		//Log.v(TAG, "update id: " + db.update(FACILITY_TABLE, values, strFilter, null));

	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//--------------------------------------------PHOTO SECTION----------------------------------------------------
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**This function is to insert one photo into the database
     * 
     * @param photoName filename of photo
     */
    public void insertPhoto(String photoName, Context mContext) {

		SQLiteDatabase db = this.getWritableDatabase();

		//Log.v(TAG, "photoName: " + photoName);

		SiteMedia media = this.getActivePhotoProperties();

		ContentValues values = new ContentValues();
		values.put(KEY_PHOTO_NAME, photoName);
		values.put(KEY_BUILDINGITEM_FK_ID, this.getActiveRealOrNotBuildingItemID());
		values.put(KEY_PHOTO_TAG, media.getPhotoTag());
		values.put(KEY_PHOTO_NOTES, media.getNotes());
		values.put(KEY_MEASURE_LIST_FK_ID, media.getMeasureID());
        values.put(KEY_PHOTO_IS_NEW, 1);

     	Long id = db.insert(PHOTO_TABLE, null, values);

        Intent photoAddedIntent = new Intent();
        photoAddedIntent.setAction("TEST_PHOTO_ADDED");
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(photoAddedIntent);

        Log.d(TAG, "Photo inserted" + id);
     	//db.close();
    }

    /**
     *
     * @return photoFiles list of Photo filenames
     */
    public ArrayList<String> getBuildingPhotos() {
        String selectQuery = "SELECT * FROM " + PHOTO_TABLE
        		+ " LEFT OUTER JOIN " + BUILDING_ITEM_TABLE + " ON " + PHOTO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + BUILDING_ITEM_TABLE + "." + KEY_BUILDINGITEM_ID
        		+ " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID();  

    	return getPhotoFileNames(selectQuery);
    }

    /**Returns a string list of photos for the active equipment
     *
     * @return photoFiles array list of string filenames of each photo
     */
    public ArrayList<String> getEquipmentPhotos() {
        String selectQuery = "SELECT * FROM " + PHOTO_TABLE
                + " LEFT OUTER JOIN " + BUILDING_ITEM_TABLE + " ON " + PHOTO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + BUILDING_ITEM_TABLE + "." + KEY_BUILDINGITEM_ID
                + " WHERE " + KEY_BUILDINGITEM_ID + " = " + this.getActiveRealOrNotBuildingItemID();

        return getPhotoFileNames(selectQuery);

    }

    public ArrayList<SiteMedia> getNewPhotos() {

        return getPhotoClass(" WHERE " + KEY_PHOTO_IS_NEW + " = 1");

    }

    public void setNewPhotosOld() {
        SQLiteDatabase db = this.getWritableDatabase();
        String strFilter = KEY_PHOTO_IS_NEW +"=?";

        ContentValues values = new ContentValues();
        values.put(KEY_PHOTO_IS_NEW, 0);

        ArrayList<SiteMedia> test = getPhotoClass("");

        // update or insert row
        db.update(PHOTO_TABLE, values, strFilter, new String[] { "1" });

        test = getPhotoClass("");
        test = test;
    }

    /**Returns a list of file names for a given
     *
     * @param selectQuery, filter query for table filter
     * @return arrayList of photo files
     */
    private ArrayList<String> getPhotoFileNames(String selectQuery) {
        ArrayList<String> photoFiles = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String photoName;
        Cursor cursor;

        cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            photoName = cursor.getString(cursor.getColumnIndex(KEY_PHOTO_NAME));
            if(photoExists(photoName)) {
                photoFiles.add(photoName);
            }
            else {
                deletePhoto(cursor.getInt(cursor.getColumnIndex(KEY_PHOTO_ID)));
            }
        }

        return photoFiles;
    }
    
    public ArrayList<SiteMedia> getBuildingSitePhotos() {
    	return getPhotoClass(" WHERE " + BUILDING_TABLE +"." + KEY_FACILITY_FK_ID + " = " + this.getActiveFacilityID());
    }

    public int getBuildingPhotosCount() {
        return getPhotosCount(" WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID());
    }

    public int getEquipmentPhotosCount() {
        return getPhotosCount(" WHERE " + BUILDING_ITEM_TABLE + "." + KEY_BUILDINGITEM_ID + " = " + this.getActiveRealOrNotBuildingItemID());
    }

    public int getPhotosCount(String whereArgs) {
        String selectQuery = "Select * " //count(" + KEY_PHOTO_ID + ") AS counter"
                + " FROM " + PHOTO_TABLE
                + " LEFT OUTER JOIN " + BUILDING_ITEM_TABLE + " ON " + PHOTO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + BUILDING_ITEM_TABLE + "." + KEY_BUILDINGITEM_ID
                + whereArgs;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor.getCount();

    }

    public ArrayList<SiteMedia> getBuildingMediaPhotos() {
        return getPhotoClass(" WHERE " + BUILDING_ITEM_TABLE + "." + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID());
    }
    
    private ArrayList<SiteMedia> getPhotoClass(String whereArgs) {
    	ArrayList<SiteMedia> photos = new ArrayList<SiteMedia>();
        String selectQuery = "SELECT *"
        + " FROM " + PHOTO_TABLE
		+ " LEFT OUTER JOIN " + BUILDING_ITEM_TABLE + " ON " + PHOTO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + BUILDING_ITEM_TABLE + "." + KEY_BUILDINGITEM_ID
        + " LEFT OUTER JOIN " + EQUIPMENT_TABLE + " ON " + PHOTO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + EQUIPMENT_TABLE+ "." + KEY_EQUIPMENT_ID
		+ " LEFT OUTER JOIN " + BUILDING_TABLE + " ON " + BUILDING_ITEM_TABLE + "." + KEY_BUILDING_FK_ID + " = " + BUILDING_TABLE + "." + KEY_BUILDING_ID	
		+ " LEFT OUTER JOIN " + LIGHTING_TABLE + " ON " + PHOTO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + LIGHTING_TABLE + "." + KEY_LIGHTING_ID
		+ " LEFT OUTER JOIN " + ENVELOPE_TABLE + " ON " + PHOTO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + ENVELOPE_TABLE + "." + KEY_ENVELOPE_ID
		+ " LEFT OUTER JOIN " + WATER_TABLE + " ON " + PHOTO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + WATER_TABLE + "." + KEY_WATER_ID
		+ " LEFT OUTER JOIN " + MEASURE_LIST_TABLE + " ON " + PHOTO_TABLE + "." + KEY_MEASURE_LIST_FK_ID + " = " + MEASURE_LIST_TABLE + "." + KEY_MEASURE_LIST_ID
		+ " LEFT OUTER JOIN " + MEASURE_TABLE + " ON " + MEASURE_LIST_TABLE + "." + KEY_MEASURE_FK_ID + " = " + MEASURE_TABLE + "." + KEY_MEASURE_ID
		+ whereArgs;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);        
        
        String buildingName;
        String buildingNumber;
        String equipmentID = "";
        String equipmentType;

        while (cursor.moveToNext()) {
        	SiteMedia photo = new SiteMedia();
        	photo.setPhotoName(cursor.getString(cursor.getColumnIndex(KEY_PHOTO_NAME)));
        	buildingName = cursor.getString(cursor.getColumnIndex(KEY_BUILDING_NAME));
        	buildingNumber = cursor.getString(cursor.getColumnIndex(KEY_BUILDING_NUMBER));
        	photo.setBuildingName(buildingName);
        	photo.setBuildingNumber(buildingNumber);
        	equipmentType = cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_TYPE));
        	////Log.v(this.toString() + "getSiteMedia()","EquipmentType: " + equipmentType);
        	photo.setEquipmentType(equipmentType);
			photo.setEquipmentLocation(cursor.getString(cursor.getColumnIndex(KEY_BUILDINGITEM_LOCATION)));

			switch(EquipmentType.getEnumType(equipmentType).getBuildingItemType()) {
                case LIGHT:
					//Log.v(TAG, "Light photo");
                    equipmentID = cursor.getString(cursor.getColumnIndex(KEY_LAMP_TYPE));
                    break;
                case EQUIPMENT:
					//Log.v(TAG, "Equipment Photo");
                    equipmentID = cursor.getString(cursor.getColumnIndex(KEY_UNIT_ID_NAME));
                    break;
                case WATER_FIXTURE:
					//Log.v(TAG, "Water fixture photo");
                    equipmentID = "Water";
                    break;
                case OTHER:
                case ENVELOPE:
					//Log.v(TAG, "Envelope photo");
                    equipmentID = cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_TYPE));
					break;
                default:
                    equipmentID = "?";
        	}
			////Log.v(TAG, equipmentID);
        	photo.setEquipmentName(equipmentID);
			photo.setMeasureTag(cursor.getString(cursor.getColumnIndex(KEY_MEASURE_DESCRIPTION)));
			photo.setPhotoTag(cursor.getString(cursor.getColumnIndex(KEY_PHOTO_TAG)));
			photo.setNotes(cursor.getString(cursor.getColumnIndex(KEY_PHOTO_NOTES)));
        	photos.add(photo);
        }
        
        ////Log.v(TAG, "#ofphotos: " + photos.size());
        
        return photos;
    }
    
    /**checks that the file name still exists, if not photo is deleted
     * 
     * @param photo
     * 		name of photo in database
     * @return photoExists
     */
    private boolean photoExists(String photo) {
    	
    	if (Utils.getCameraImages().contains(photo)) {
    		return true;
    	}
    	
    	return false;
    }
    
    /**deletes photo from photo table
     * 
     * @param id, database id of photo to delete from database
     */
    private void deletePhoto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PHOTO_TABLE, KEY_PHOTO_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////
//--------------------------------------------AUDIO SECTION----------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////
    /**This function is to insert one photo into the database
     * 
     * @param fileName, fileName of audio file
     */
    public void insertAudio(String fileName) {
        SQLiteDatabase db = this.getWritableDatabase();
        SiteMedia media = this.getActivePhotoProperties();

        ContentValues values = new ContentValues();     
     	values.put(KEY_AUDIO_FILE, fileName);
   		values.put(KEY_BUILDINGITEM_FK_ID, this.getActiveRealOrNotBuildingItemID());
		values.put(KEY_AUDIO_TAG, media.getPhotoTag());
		values.put(KEY_AUDIO_NOTES, media.getNotes());
		values.put(KEY_MEASURE_LIST_FK_ID, media.getMeasureID());

     	db.insert(AUDIO_TABLE, null, values);
    }
    
/*    public ArrayList<String> getBuildingAudio() {
    	ArrayList<String> photoFiles = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + AUDIO_TABLE 
        		+ " LEFT OUTER JOIN " + BUILDING_ITEM_TABLE + " ON " + AUDIO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + BUILDING_ITEM_TABLE + "." + KEY_BUILDINGITEM_ID
                + " WHERE " + KEY_BUILDING_FK_ID + " = " + this.getActiveBuildingID();
        SQLiteDatabase db = this.getReadableDatabase();
        String photoName;
        Cursor cursor;
       
        cursor = db.rawQuery(selectQuery, null);
        
        while (cursor.moveToNext()) {
        	photoName = cursor.getString(cursor.getColumnIndex(KEY_AUDIO_FILE));
        	if(photoExists(photoName)) {
        		photoFiles.add(photoName);
        	}
        	else {
        		deletePhoto(cursor.getInt(cursor.getColumnIndex(KEY_AUDIO_ID)));
        	}
        }

        //db.close();
    	return photoFiles;
    }
*/
    public ArrayList<SiteMedia> getBuildingSiteAudio() {
    	return getAudioClass(" WHERE " + BUILDING_TABLE +"." + KEY_FACILITY_FK_ID + " = " + this.getActiveFacilityID());

    }

    private ArrayList<SiteMedia> getAudioClass(String whereArgs) {
    	ArrayList<SiteMedia> audios = new ArrayList<SiteMedia>();
        String selectQuery = "SELECT *"
        + " FROM " + AUDIO_TABLE 
		+ " LEFT OUTER JOIN " + BUILDING_ITEM_TABLE + " ON " + AUDIO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + BUILDING_ITEM_TABLE + "." + KEY_BUILDINGITEM_ID
        + " LEFT OUTER JOIN " + EQUIPMENT_TABLE + " ON " + AUDIO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + EQUIPMENT_TABLE+ "." + KEY_EQUIPMENT_ID
		+ " LEFT OUTER JOIN " + BUILDING_TABLE + " ON " + BUILDING_ITEM_TABLE + "." + KEY_BUILDING_FK_ID + " = " + BUILDING_TABLE + "." + KEY_BUILDING_ID	
		+ " LEFT OUTER JOIN " + LIGHTING_TABLE + " ON " + AUDIO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + LIGHTING_TABLE + "." + KEY_LIGHTING_ID
		+ " LEFT OUTER JOIN " + MEASURE_LIST_TABLE + " ON " + AUDIO_TABLE + "." + KEY_MEASURE_LIST_FK_ID + " = " + MEASURE_LIST_TABLE + "." + KEY_MEASURE_LIST_ID
		+ " LEFT OUTER JOIN " + MEASURE_TABLE + " ON " + MEASURE_LIST_TABLE + "." + KEY_MEASURE_FK_ID + " = " + MEASURE_TABLE + "." + KEY_MEASURE_ID
		+ " LEFT OUTER JOIN " + ENVELOPE_TABLE + " ON " + AUDIO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + ENVELOPE_TABLE + "." + KEY_ENVELOPE_ID
		+ " LEFT OUTER JOIN " + WATER_TABLE + " ON " + AUDIO_TABLE + "." + KEY_BUILDINGITEM_FK_ID + " = " + WATER_TABLE + "." + KEY_WATER_ID
				+ whereArgs;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);        
        
        String buildingName;
        String buildingNumber;
        String equipmentID = "";
        String equipmentType;

        while (cursor.moveToNext()) {
        	SiteMedia audio = new SiteMedia();
        	audio.setPhotoName(cursor.getString(cursor.getColumnIndex(KEY_AUDIO_FILE)));
        	buildingName = cursor.getString(cursor.getColumnIndex(KEY_BUILDING_NAME));
        	buildingNumber = cursor.getString(cursor.getColumnIndex(KEY_BUILDING_NUMBER));
        	audio.setBuildingName(buildingName);
			audio.setBuildingNumber(buildingNumber);
			equipmentType = cursor.getString(cursor.getColumnIndex(KEY_EQUIPMENT_TYPE));
        	//Log.v(this.toString() + "getAudioClass()", "EquipmentType: " + equipmentType);
        	audio.setEquipmentType(equipmentType);
			audio.setEquipmentLocation(cursor.getString(cursor.getColumnIndex(KEY_BUILDINGITEM_LOCATION)));
			switch(EquipmentType.getEnumType(equipmentType).getBuildingItemType()) {
                case LIGHT:
                    equipmentID = cursor.getString(cursor.getColumnIndex(KEY_LAMP_TYPE));
                    break;
                case EQUIPMENT:
                    equipmentID = cursor.getString(cursor.getColumnIndex(KEY_UNIT_ID_NAME));
                    break;
                case WATER_FIXTURE:
                    equipmentID = "";
                    break;
                case ENVELOPE:
					equipmentID = "";
                    break;
                default:
					equipmentID = "?";
			}
			audio.setEquipmentName(equipmentID);
			audio.setNotes(cursor.getString(cursor.getColumnIndex(KEY_AUDIO_NOTES)));
			audio.setPhotoTag(cursor.getString(cursor.getColumnIndex(KEY_AUDIO_TAG)));
			audio.setMeasureTag(cursor.getString(cursor.getColumnIndex(KEY_MEASURE_DESCRIPTION)));

        	audios.add(audio);
        }
        
        ////Log.v(TAG, "#ofphotos: " + photos.size());
        
        return audios;
    }
    
    //TODO add utils function
    /**checks that the file name still exists, if not file is deleted from database
     * 
     * @param audioFile
     * 		name of file in database
     * @return
     */
    private boolean audioExists(String audioFile) {
    	
    	if (Utils.getAudioFiles().contains(audioFile)) {
    		return true;
    	}
    	
    	return false;
    }
    
    /**deletes photo from photo table
     * 
     * param id, primary id of audio record to delete
     */
/*    private void deleteAudio(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AUDIO_TABLE, KEY_AUDIO_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
	*/
///////////////////////////////////////////////////////////////////////
//-----------------------SETTINGS--------------------------------------
/////////////////////////////////////////////////////////////////////////
    public void setActiveFacilityIndex(int index) throws IllegalArgumentException {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String selectQuery = "SELECT * FROM " + FACILITY_TABLE;
    	Cursor cursor;
    	
    	////Log.v(TAG, "index: " + index);
    	cursor = db.rawQuery(selectQuery, null);
    	if (cursor.move(index+1))
    		setActiveFacility(cursor.getInt(cursor.getColumnIndex(KEY_FACILITY_ID)));
    	else 
    		throw new IllegalArgumentException("No such facility");
    }
       
    private void setActiveFacility(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strFilter = KEY_SETTINGS_ID +"="+ SETTINGS_ID;
        
        ContentValues values = new ContentValues();     
      	values.put(KEY_FACILITY_FK_ID, id);

        // update or insert row
        if (db.update(SETTINGS_TABLE, values, strFilter, null)==0) {
      		values.put(KEY_SETTINGS_ID, SETTINGS_ID);
      		db.insert(SETTINGS_TABLE, null, values);
      	}
    }
    
    private int getActiveFacilityID() {
        int facilityID;
    	String selectQuery = "SELECT * FROM " + SETTINGS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
       
        ////Log.v(TAG, "1700");
        cursor = db.rawQuery(selectQuery, null);
        try {
	        if (cursor.moveToNext()) {
	        	facilityID = cursor.getInt(cursor.getColumnIndex(KEY_FACILITY_FK_ID));
	        	return facilityID;
	        }
        } catch (IllegalStateException ie) {
			ie.printStackTrace();
        }
        //Log.v(TAG, "getActiveFacilityID, new Facility Added");
    	Facility facility = new Facility();
    	facility.setFacilityName("Facility1");
    	this.addFacility(facility);
    	return this.getActiveFacilityID();
        
        //db.close();
    }

  //-----------------------MEASURES--------------------------------------
  /**Returns a string array of all possible measures for that equipment type
   * 
   * @param eType, equipmentType
   * @return avaialableMeasureTags, string array of measures for the measure type
   */
  public String[] getGeneralEquipmentMeasures(EquipmentType eType) {
	  
	  ArrayList<String> measures = new ArrayList<String>();
	  Cursor cursor;
	  
	  cursor = 	  this.GeneralMeasureQuery(KEY_MEASURE_EQUIPMENT_TYPE + " = '" + eType.toString()+"'");

	  //Log.v(TAG, "CURSOR COUNT: " + cursor.getCount());
	  
	  while (cursor.moveToNext()) {
		measures.add(cursor.getString(cursor.getColumnIndex(KEY_MEASURE_DESCRIPTION)));
	  }
	  
      Object[] ObjectList = measures.toArray();
      String[] measureArray = Arrays.copyOf(ObjectList, ObjectList.length,String[].class);
      
      return measureArray;
      
  }
  
  /**Returns measure_id of  based on index by equipment type
   * 
   */
  private int getMeasureIndex(int index, EquipmentType eType) {
	  
	  Cursor cursor = this.GeneralMeasureQuery(KEY_MEASURE_EQUIPMENT_TYPE + " = '" + eType.toString()+"'");
	  
	  //Log.v(TAG, "index: " + index);
	  
	  if(cursor.move(index)) {
		  return cursor.getInt(cursor.getColumnIndex(KEY_MEASURE_FK_ID));
	  }
	  
	  throw new IllegalArgumentException("No measure with that index and equipment type");
	  
  }

	/**Resturns list of measure tagged for the active equipment
	 *
	 * @return
	 */
   public ArrayList<Measure> getMeasuresByActiveEquipment() {

	   ArrayList<Measure> measures = new ArrayList<Measure>();
	   Cursor cursor = MeasureListQuery(KEY_BUILDINGITEM_FK_ID + "=" + getActiveBuildingItemID());

	   Measure measure = new Measure();
	   measure.setDescription(Strings.FAILED_TO_SPECIFY);
	   measures.add(measure);

	   try {
		   while (cursor.moveToNext()) {
			   measure = new Measure();
			   measure.setNotes(cursor.getString(cursor.getColumnIndex(KEY_MEASURE_LIST_NOTES)));
			   measure.setDescription(cursor.getString(cursor.getColumnIndex(KEY_MEASURE_DESCRIPTION)));
			   measures.add(measure);
		   }
		   return measures;
	   } catch (IllegalStateException ise) {
		   ise.printStackTrace();
		   return null;
	   }

   }

	private int getMeasureIdByActiveEquipmentAndName(SiteMedia photoTags) {
		Cursor cursor = MeasureListQuery(KEY_BUILDINGITEM_FK_ID + "=" + getActiveBuildingItemID() + " AND " + KEY_MEASURE_DESCRIPTION + " = '" + photoTags.getMeasureTag() +"'");

			if(cursor.moveToFirst())
				return cursor.getInt(cursor.getColumnIndex(KEY_MEASURE_LIST_ID));
			else
				throw new NullPointerException("No Measure With That Name Exists");
	}

	private Cursor MeasureListQuery(String whereArgs) {
	  String selectQuery = "SELECT * FROM " + MEASURE_LIST_TABLE
			  + " INNER JOIN " + MEASURE_TABLE + " ON " + MEASURE_LIST_TABLE +"." + KEY_MEASURE_FK_ID + " = " + MEASURE_TABLE + "." + KEY_MEASURE_ID
			  + " WHERE " + whereArgs;
	  SQLiteDatabase db = this.getReadableDatabase();

	  return db.rawQuery(selectQuery, null);

  }

	private Cursor GeneralMeasureQuery(String whereArgs) {
		String selectQuery = "SELECT * FROM " + MEASURE_EQUIPMENT_TABLE
				+ " INNER JOIN " + MEASURE_TABLE + " ON " + KEY_MEASURE_FK_ID + " = " + KEY_MEASURE_ID
				+ " WHERE " + whereArgs;
		SQLiteDatabase db = this.getReadableDatabase();

		return db.rawQuery(selectQuery, null);
	}
  
  /** insert generic measure for tagging
   * 
   */
  public void insertMeasure(Measure measure) {
	  SQLiteDatabase db = this.getWritableDatabase();
	  ContentValues measureValue = new ContentValues();
	  
	  measureValue.put(KEY_MEASURE_DESCRIPTION, measure.getDescription());
	  long id = db.insert(MEASURE_TABLE, null, measureValue);

	  measureValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_EQUIPMENT_TYPE, measure.getEquipmentType().toString());
	  measureValue.put(KEY_MEASURE_FK_ID, id);
	  
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureValue);
  }
  
  /**Inserts measures into SQL
   * 
   */
  private void insertMeasuresIntoDatabase(SQLiteDatabase db) {
	  
	  ContentValues measureValue = new ContentValues();
	  Long id;
	  ContentValues measureEquipmentValue = new ContentValues();

	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Decentralize Steam");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement CHW Reset");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Chiller");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement CW Reset");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Cooling Tower");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement DCV");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement DCV");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement HHW Reset");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement Optimum Start/Stop");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement Optimum Start/Stop");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement SAT Reset");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement SAT Reset");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement Unoccupied Setback/Setup");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement Unoccupied Setback/Setup");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Aquastat/Timer to Control DHW Pump");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"DHW Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Ceiling Insulation");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Envelope");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Chiller Plant Optimization Software");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Chiller");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Condensing Boiler");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install EC Motor / Install Frigitek Controllers");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Refrigeration Condensing Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install EC Motor / Install Frigitek Controllers");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Refrigeration Evaporator Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Heat Recovery Wheel");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Heat Recovery Wheel");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Low-Flow Aerators");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Water Room");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Modular Steam Boilers");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Occupancy Sensors to Control Lighting");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Interior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Occupancy Sensors to Control Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Exterior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install PC Management Software");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Other");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Plug Load Timer");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Other");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install POU Tankless Water Heater");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Domestic Water Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Programmable Thermostat");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Furnace");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Indoor Split System");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Unit Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Split System Heat Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Split System Condensing Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Strip Curtains");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Refrigeration Condensing Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Strip Curtains");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Refrigeration Evaporator Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Synchronous Belts");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Synchronous Belts");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Thermostat to Control Exhaust Fan");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Exhaust Fan");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Timer to Control Sprinklers");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Water Room");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Twist Timer to Control Window AC");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Window AC Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install VendingMiser Controller");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Other");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install VFD to Control Exhaust Fan");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Exhaust Fan");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install VFDs on Supply and/or Return Fan");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install VFDs on Supply and/or Return Fan");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install VFDs to Control Cooling Tower Fans");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Cooling Tower");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Weather Stripping");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Envelope");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Insulate CHW Piping");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Chiller");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Insulate DHW Piping");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Domestic Water Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Insulate HHW Piping");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Insulate Steam Piping");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Reduce DHW Setpoint to 120F");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Domestic Water Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Reduce Steam Pressure Setpoint");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair Economizer Operation");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair Economizer Operation");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair or Install Photocells");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Exterior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Replace Electric Tank Water Heater with Natural Gas Water Heater");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Domestic Water Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Replace Exhaust Fan Motor with Premium Efficiency Motor");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Exhaust Fan");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Replace Failed Steam Traps");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Replace Manual Decoupler Bridge Valve with DDC Valve and Control Strategy");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Chiller");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Reprogram Programmable Thermostat");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Furnace");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Reprogram Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Indoor Split System");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Reprogram Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Reprogram Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Unit Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Reprogram Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Split System Heat Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Reprogram Programmable Thermostat");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Split System Condensing Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Schedule Exhaust Fan");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Exhaust Fan");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade 32W T8 to 28W T8");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Interior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade CW Pumps to Variable Flow");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"CHW Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Fan Motor(s) to Premium Efficiency");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Fan Motor(s) to Premium Efficiency");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade HHW Loop to Variable Flow");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"HHW Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade High Bay Lighting to T5HO");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Interior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Incandescents to LEDs");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Interior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Incandescents to LEDs");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Interior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Motor to Premium Efficiency");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"HHW Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Motor to Premium Efficiency");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Pole Lighting to LEDs");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Exterior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Primary CHW Loop to Variable Flow");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"CHW Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Pump Motor to High Efficiency");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"CHW Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Refrigerators to Energy Star Refrigerators");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Other");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Secondary CHW Loop to Variable Flow");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"CHW Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade T12 with Magnetic Ballasts to LEDs");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Interior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade to High-Efficiency Magnetic Bearing Chillers");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Chiller");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Low-Flow Toilets");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Water Room");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade to VAV");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade to VAV");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Transformer to High-Efficiency Transformers");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Transformer");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Wallpack Fixtures to LEDs");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Exterior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Windows to Double Pane");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Window");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade to Bi-Level Lighting");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Exterior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade to Bi-Level Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Interior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Daylight Harvesting");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Interior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Low-Flow Urinals");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Water Room");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Low-Flow Showerheads");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Water Room");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade to Linkageless Burner");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade to Linkageless Burner");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Domestic Water Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Outdoor Air Intake for Compressor");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Compressed Air");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install High Efficiency Screw Air Compressor");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Compressed Air");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Turbocor Compressor");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Chiller");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Implement Water-side Economizer Cooling");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Chiller");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install a Motorized Flue Damper");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install a Motorized Flue Damper");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Domestic Water Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Insulate Domestic Hot Water Tank");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Domestic Water Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Exit Signs to LED");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Interior Lighting");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Roof Insulation");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Roof");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Upgrade Wall Insulation");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Wall");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Airseal Ductwork");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Air Handling Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Airseal Ductwork");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Airseal Ductwork");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Split System Heat Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Airseal Ductwork");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Furnace");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Airseal Envelope");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Wall");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Insulate Condensate Tank");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair Steam Leak");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Boiler Blowdown Heat Recovery");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Boiler Stack Economizer");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Use Refrigeration Waste Heat to Make DHW");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Domestic Water Heater");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install High-Efficiency Package Unit");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install High-Efficiency Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Split System Heat Pump");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install a Condensing Furnace");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Furnace");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Convert Electric Heat to Gas");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Furnace");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Convert Electric Heat to Gas");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Boiler");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Convert Electric Heat to Gas");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Package Unit");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Improve Computer Server Cooling");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Indoor Split System");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Raise Server Room Temperature Setpoint");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Indoor Split System");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair Compressed Air Leaks");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Compressed Air");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Reduce Compressed Air Supply Pressure");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Compressed Air");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Install Thermal Energy Storage System");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"Chiller");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"INTERIORLIGHTING");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"EXTERIORLIGHTING");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"AIRHANDLER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"BOILER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"CEILING");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"CHILLER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"COMPRESSED_AIR");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"CW_PUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"COOLINGTOWER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"DOOR");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"DHW_PUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WATERHEATER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"EXHAUST_FAN");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"ELECTRIC_HEATER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"FURNACE");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"HW_PUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"SPLITSYSTEM_INDOOR");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"MISC_FAN");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"PACKAGEUNIT");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"OTHER_PUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"REFRIGERATION_CONDENSING");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"REFRIGERATION_EVAPORATOR");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"REFRIGERATION_RESIDENTIAL");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"ROOF");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"SPLITSYSTEM_OUTDOOR");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"SPLITSYSTEM_HEATPUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"THERMOSTAT");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"TRANSFORMER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"UNITHEATER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"UNDEFINED");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WALL");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WATER_ROOM");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WSHP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WINDOW");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "Repair");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WINDOWAC");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureValue = new ContentValues();
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  id = db.insert(MEASURE_TABLE, null, measureValue);
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"INTERIORLIGHTING");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"EXTERIORLIGHTING");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"AIRHANDLER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"BOILER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"CHILLER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"COMPRESSED_AIR");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"CW_PUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"COOLINGTOWER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"DHW_PUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WATERHEATER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"EXHAUST_FAN");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"ELECTRIC_HEATER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"FURNACE");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"HW_PUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"SPLITSYSTEM_INDOOR");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"MISC_FAN");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"PACKAGEUNIT");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"OTHER_PUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"REFRIGERATION_CONDENSING");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"REFRIGERATION_EVAPORATOR");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"REFRIGERATION_RESIDENTIAL");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"ROOF");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"SPLITSYSTEM_OUTDOOR");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"SPLITSYSTEM_HEATPUMP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"THERMOSTAT");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"TRANSFORMER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"UNITHEATER");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"UNDEFINED");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WALL");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WATER_ROOM");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WSHP");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WINDOW");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);
	  measureEquipmentValue = new ContentValues();
	  measureValue.put(KEY_MEASURE_DESCRIPTION, "RCx");
	  measureEquipmentValue.put(KEY_MEASURE_EQUIPMENT_TYPE,"WINDOWAC");
	  measureEquipmentValue.put(KEY_MEASURE_FK_ID, id);
	  db.insert(MEASURE_EQUIPMENT_TABLE, null, measureEquipmentValue);

  }  
    
  //-----------------------MEASURE LIST--------------------------------------
  
  /**Adds measure tag for active equipment
   * 
   * @param index, number in list of measures by equipment type
   * @param eType, equipmentType enum
   */
  public void addMeasure(int index, EquipmentType eType, String notes) {

	  SQLiteDatabase db = this.getWritableDatabase();
	  ContentValues values = new ContentValues();
	  
	  values.put(KEY_MEASURE_FK_ID, this.getMeasureIndex(index+1, eType));
	  values.put(KEY_BUILDINGITEM_FK_ID, this.getActiveBuildingItemID());
	  
	  values.put(KEY_MEASURE_LIST_NOTES, notes);
	  
	  Long id = db.insert(MEASURE_LIST_TABLE, null, values);
	  
	  //Log.v(TAG, "Inserted id: " + id);
  }

  /** return measure list of things ;) 
   * 
   * @return measures, arraylist of measures
   */
  public ArrayList<Measure> getMeasureList() {
	  ArrayList<Measure> measures = new ArrayList<Measure>();
	  
	  SQLiteDatabase db = this.getReadableDatabase();
	  Cursor cursor;
	  String selectQuery = "SELECT * FROM " + MEASURE_LIST_TABLE + 
			  " INNER JOIN " + MEASURE_TABLE + " ON " + KEY_MEASURE_FK_ID + " = " + KEY_MEASURE_ID +
			  " LEFT OUTER JOIN " + BUILDING_ITEM_TABLE + " ON " + KEY_BUILDINGITEM_FK_ID + " = " + KEY_BUILDINGITEM_ID + 
			  " LEFT OUTER JOIN " + EQUIPMENT_TABLE + " ON " + KEY_BUILDINGITEM_FK_ID + " = " + KEY_EQUIPMENT_ID +
			  " LEFT OUTER JOIN " + LIGHTING_TABLE + " ON " + KEY_BUILDINGITEM_FK_ID + " = " + KEY_LIGHTING_ID + 
			  " LEFT OUTER JOIN " + BUILDING_TABLE + " ON " + KEY_BUILDING_FK_ID + " + " + KEY_BUILDING_ID +
			  " WHERE " + KEY_FACILITY_FK_ID + " = " + this.getActiveFacilityID();
	  
	  cursor = db.rawQuery(selectQuery, null);
	  
	  while(cursor.moveToNext()) {
		  Measure measure = new Measure();
		  
		  measure.setDescription(cursor.getString(cursor.getColumnIndex(KEY_MEASURE_DESCRIPTION)));
		  measure.setEquipmentName(	"Unit ID: " +cursor.getString(cursor.getColumnIndex(KEY_UNIT_ID_NAME))+"/Location: "+ cursor.getString(cursor.getColumnIndex(KEY_BUILDINGITEM_LOCATION)));
		  measure.setEquipmentID(cursor.getInt(cursor.getColumnIndex(KEY_EQUIPMENT_ID)));
		  measure.setNotes(cursor.getString(cursor.getColumnIndex(KEY_MEASURE_LIST_NOTES)));
		  measures.add(measure);
	  }
	  
	  return measures;
  }

  public ArrayList<MeasureSummary> getMeasureSummaryList() {
      String BUILDING_CONCAT = "bConcat";
      String EQUIPMENT_CONCAT = "eConcat";
      String EQUIPMENT_SUM = "EquipmentCount";
      String LIGHTING_SUM = "FixtureCount";

      ArrayList<MeasureSummary> measures = new ArrayList<MeasureSummary>();

      int measureNumber = 1;
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor;
      String selectQuery = "SELECT " + KEY_MEASURE_DESCRIPTION + ", SUM(" + KEY_EQUIPMENT_QTY + ") AS " + EQUIPMENT_SUM
              + ", SUM(" + KEY_FIXTURE_QTY + ") AS " + LIGHTING_SUM
              + ", GROUP_CONCAT(DISTINCT " +KEY_BUILDING_NAME + ") AS " + BUILDING_CONCAT + ", GROUP_CONCAT( DISTINCT " + KEY_UNIT_ID_NAME +") AS " + EQUIPMENT_CONCAT +
              " FROM " + MEASURE_LIST_TABLE +
              " INNER JOIN " + MEASURE_TABLE + " ON " + KEY_MEASURE_FK_ID + " = " + KEY_MEASURE_ID +
              " LEFT OUTER JOIN " + BUILDING_ITEM_TABLE + " ON " + KEY_BUILDINGITEM_FK_ID + " = " + KEY_BUILDINGITEM_ID +
              " LEFT OUTER JOIN " + EQUIPMENT_TABLE + " ON " + KEY_BUILDINGITEM_FK_ID + " = " + KEY_EQUIPMENT_ID +
              " LEFT OUTER JOIN " + LIGHTING_TABLE + " ON " + KEY_BUILDINGITEM_FK_ID + " = " + KEY_LIGHTING_ID +
              " LEFT OUTER JOIN " + BUILDING_TABLE + " ON " + KEY_BUILDING_FK_ID + " = " + KEY_BUILDING_ID +
              " WHERE " + KEY_FACILITY_FK_ID + " = " + this.getActiveFacilityID() +
              " GROUP BY " + KEY_MEASURE_FK_ID;

      //Log.v(TAG, selectQuery);
      cursor = db.rawQuery(selectQuery, null);

      while(cursor.moveToNext()) {
          MeasureSummary measure = new MeasureSummary();

          measure.setMeasureName(cursor.getString(cursor.getColumnIndex(KEY_MEASURE_DESCRIPTION)));
          measure.setMeasureNumber(measureNumber);
          measure.setMeasureBuildings(cursor.getString(cursor.getColumnIndex(BUILDING_CONCAT)));
          measure.setMeasureEquipment(cursor.getString(cursor.getColumnIndex(EQUIPMENT_CONCAT)));
          measure.setNumEquipment(Math.max(cursor.getInt(cursor.getColumnIndex(EQUIPMENT_SUM)), cursor.getInt(cursor.getColumnIndex(LIGHTING_SUM))));
          measures.add(measure);

          measureNumber++;
      }

      if(measures.size() == 0) {
          MeasureSummary measure = new MeasureSummary();
          measure.setMeasureName("No measures added");
          measure.setMeasureNumber(0);
          measure.setNumEquipment(0);
      }

      return measures;

  }
}

