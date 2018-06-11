package fsTablesEnums;

import android.util.Log;

import java.util.ArrayList;

import constants.GeneralFunctions;

/*
 * This enum keeps track of all equipment types
 * 
 * @author Steven Wancewicz
 * @version 2013-5-10
 * 
 */
public enum EquipmentType 
{
	INTERIORLIGHTING("Interior Lighting",BuildingItemType.LIGHT,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	EXTERIORLIGHTING("Exterior Lighting",BuildingItemType.LIGHT,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	AIRHANDLER("Air Handling Unit",BuildingItemType.EQUIPMENT,false,false,true,true,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT),
	BOILER("Boiler",BuildingItemType.EQUIPMENT,true,false,true,false,false,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	BUILDING(true,"Building"),
	CEILING("Ceiling",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	CHILLER("Chiller",BuildingItemType.EQUIPMENT,false,true,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.CHILLER,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	COMPRESSED_AIR("Compressed Air",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	CW_PUMP("CHW Pump",BuildingItemType.EQUIPMENT,false,true,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	COOLINGTOWER("Cooling Tower",BuildingItemType.EQUIPMENT,false,true,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.COOLING_TOWER,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	DOOR("Door",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	DRAWINGS(true, "Drawings"),
	DHW_PUMP("DHW Pump",BuildingItemType.EQUIPMENT,false,false,false,false,true,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR),
	WATERHEATER("Domestic Water Heater",BuildingItemType.EQUIPMENT,false,false,false,false,true,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.BOILER),
	EXHAUST_FAN("Exhaust Fan",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	ELECTRIC_HEATER("Electric Heater",BuildingItemType.EQUIPMENT,true,false,true,false,false,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	FURNACE("Furnace",BuildingItemType.EQUIPMENT,true,false,true,false,false,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	HW_PUMP("HHW Pump",BuildingItemType.EQUIPMENT,true,false,false,false,false,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	SPLITSYSTEM_INDOOR("Indoor Split System",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	UTILITY_METER("Meter",BuildingItemType.UTILITYMETER,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	MISC_FAN("Misc Fan",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	PACKAGEUNIT("Package Unit",BuildingItemType.EQUIPMENT,true,true,true,true,false,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT),
	OTHER_PUMP("Pump",BuildingItemType.EQUIPMENT,false,false,false,false,true,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR),
	REFRIGERATION_CONDENSING("Refrigeration Condensing Unit",BuildingItemType.EQUIPMENT,false,true,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	REFRIGERATION_EVAPORATOR("Refrigeration Evaporator Unit",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	REFRIGERATION_RESIDENTIAL("Refrigerator--Residential",BuildingItemType.EQUIPMENT,false,false,false,false,true,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR),
	ROOF("Roof",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	SPLITSYSTEM_OUTDOOR("Split System Condensing Unit",BuildingItemType.EQUIPMENT,false,true,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	SPLITSYSTEM_HEATPUMP("Split System Heat Pump",BuildingItemType.EQUIPMENT,true,true,true,false,false,DefaultAllocationSettings.HEAT_PUMP_HEATING,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	TERMINAL_UNITS(true,"Terminal Units"),
	THERMOSTAT(true,"Thermostat"),
	TRANSFORMER("Transformer",BuildingItemType.EQUIPMENT,false,false,false,false,true,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.TRANSFORMER),
	UNITHEATER("Unit Heater",BuildingItemType.EQUIPMENT,true,false,true,false,false,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	UNDEFINED("Other",BuildingItemType.EQUIPMENT,true,true,true,true,true,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.MOTOR),
	WALL("Wall",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	WATER_ROOM("Water Room",BuildingItemType.WATER_FIXTURE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	WSHP("Water Source Heat Pump",BuildingItemType.EQUIPMENT,true,true,true,false,false,DefaultAllocationSettings.HEAT_PUMP_HEATING,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	WINDOW("Window",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	WINDOWAC("Window AC Unit",BuildingItemType.EQUIPMENT,true,true,true,false,false,DefaultAllocationSettings.HEAT_PUMP_HEATING,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT);
/*
	INTERIORLIGHTING("Interior Lighting",BuildingItemType.LIGHT,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    EXTERIORLIGHTING("Exterior Lighting",BuildingItemType.LIGHT,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    AIRHANDLER("Air Handling Unit",BuildingItemType.EQUIPMENT,false,false,true,true,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT),
    BOILER("Boiler",BuildingItemType.EQUIPMENT,true,false,true,false,false,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    BUILDING(true,"Building"),
    CEILING("Ceiling",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    CHILLER("Chiller",BuildingItemType.EQUIPMENT,false,true,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.CHILLER,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    CW_PUMP("CHW Pump",BuildingItemType.EQUIPMENT,false,true,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	COMPRESSED_AIR("Compressed Air",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
	COOLINGTOWER("Cooling Tower",BuildingItemType.EQUIPMENT,false,true,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.COOLING_TOWER,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    DOOR("Door",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    DRAWINGS(true, "Drawings"),
    DHW_PUMP("DHW Pump",BuildingItemType.EQUIPMENT,false,false,false,false,true,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR),
    WATERHEATER("Domestic Water Heater",BuildingItemType.EQUIPMENT,false,false,false,false,true,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.BOILER),
    EXHAUST_FAN("Exhaust Fan",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    FURNACE("Furnace",BuildingItemType.EQUIPMENT,true,false,true,false,false,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    HW_PUMP("HHW Pump",BuildingItemType.EQUIPMENT,true,false,false,false,false,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    SPLITSYSTEM_INDOOR("Indoor Split System",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    MISC_FAN("Misc Fan",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    PACKAGEUNIT("Package Unit",BuildingItemType.EQUIPMENT,true,true,true,true,false,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT),
    OTHER_PUMP("Pump",BuildingItemType.EQUIPMENT,false,false,false,false,true,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR),
    REFRIGERATION_CONDENSING("Refrigeration Condensing Unit",BuildingItemType.EQUIPMENT,false,true,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    REFRIGERATION_EVAPORATOR("Refrigeration Evaporator Unit",BuildingItemType.EQUIPMENT,false,false,true,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    ROOF("Roof",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    SPLITSYSTEM_OUTDOOR("Split System Condensing Unit",BuildingItemType.EQUIPMENT,false,true,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    SPLITSYSTEM_HEATPUMP("Split System Heat Pump",BuildingItemType.EQUIPMENT,true,true,true,false,false,DefaultAllocationSettings.HEAT_PUMP_HEATING,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    TERMINAL_UNITS(true,"Terminal Units"),
    TRANSFORMER("Transformer",BuildingItemType.EQUIPMENT,false,false,false,false,true,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.TRANSFORMER),
    UNITHEATER("Unit Heater",BuildingItemType.EQUIPMENT,true,false,true,false,false,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    UNDEFINED("Other",BuildingItemType.EQUIPMENT,true,true,true,true,true,DefaultAllocationSettings.BOILER,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.MOTOR),
    WALL("Wall",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    WATER_ROOM("Water Room",BuildingItemType.WATER_FIXTURE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    WSHP("Water Source Heat Pump",BuildingItemType.EQUIPMENT,true,true,true,false,false,DefaultAllocationSettings.HEAT_PUMP_HEATING,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    WINDOW("Window",BuildingItemType.ENVELOPE,false,false,false,false,false,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT),
    WINDOWAC("Window AC Unit",BuildingItemType.EQUIPMENT,true,true,true,false,false,DefaultAllocationSettings.HEAT_PUMP_HEATING,DefaultAllocationSettings.SPLIT_COOLING,DefaultAllocationSettings.MOTOR,DefaultAllocationSettings.DEFAULT,DefaultAllocationSettings.DEFAULT);

/*
*/
	Boolean isNotEquipment;
    BuildingItemType buildingItemType;
	String name;
	boolean hasHeating;
	boolean hasCooling;
	boolean hasSupply;
	boolean hasReturn;
	boolean hasOther;
	DefaultAllocationSettings heatingSettings;
	DefaultAllocationSettings coolingSettings;
	DefaultAllocationSettings supplySettings;
	DefaultAllocationSettings returnSettings;
	DefaultAllocationSettings otherSettings;
	
	EquipmentType(boolean isActualEquipment, String name) {
		this.isNotEquipment = true;
		this.name = name;
		this.hasHeating = false;
		this.hasCooling = false;
		this.hasSupply = false;
		this.hasReturn = false;
		this.hasOther = false;
		this.heatingSettings = DefaultAllocationSettings.DEFAULT;
		this.coolingSettings = DefaultAllocationSettings.DEFAULT;
		this.supplySettings = DefaultAllocationSettings.DEFAULT;
		this.returnSettings = DefaultAllocationSettings.DEFAULT;
		this.otherSettings = DefaultAllocationSettings.DEFAULT;
        this.buildingItemType = BuildingItemType.OTHER;
	}
	
	EquipmentType(String name, BuildingItemType bIType, boolean hasHeating, boolean hasCooling, boolean hasSupply, boolean hasReturn, boolean hasOther, 	DefaultAllocationSettings heatingSettings,
	DefaultAllocationSettings coolingSettings,
	DefaultAllocationSettings supplySettings,
	DefaultAllocationSettings returnSettings,
	DefaultAllocationSettings otherSettings)
	{
		this.isNotEquipment = false;
		this.name = name;
        this.buildingItemType =bIType;
		this.hasHeating = hasHeating;
		this.hasCooling = hasCooling;
		this.hasSupply = hasSupply;
		this.hasReturn = hasReturn;
		this.hasOther = hasOther;
		this.heatingSettings = heatingSettings;
		this.coolingSettings = coolingSettings;
		this.supplySettings = supplySettings;
		this.returnSettings = returnSettings;
		this.otherSettings = otherSettings;
	}
	
	public DefaultAllocationSettings getSettings(Function function) {
		switch (function) {
		case HEATING:
			return this.heatingSettings;
		case COOLING:
			return this.coolingSettings;
		case SUPPLY_FAN:
			return this.supplySettings;
		case RETURN_FAN:
			return this.returnSettings;
		case OTHER:
			return this.otherSettings;
		default:
			return this.heatingSettings;
		}
	}
	
	public String toString()
	{
		return this.name;
	}
	
	public static EquipmentType getEnumType(String text) {
		
		for(EquipmentType equipment:EquipmentType.values()) {
			if(equipment.toString().equals(text)) {
				//Log.v("Equipment Type", text + " has enum of " + equipment.toString());
				return equipment;

			}
		}
		//Log.v("EquipmentType", text + "does not match");
		return EquipmentType.UNDEFINED;
	}

	public static String[] getBuildingItemList(EquipmentType equipmentType) {

		ArrayList<String> buildingItemList = new ArrayList<String>();

		for(EquipmentType equipment:EquipmentType.values()) {
			if(equipment.getBuildingItemType() == equipmentType.getBuildingItemType()) {
				buildingItemList.add(equipment.toString());
			}
		}

		return GeneralFunctions.arrayListToStringArray(buildingItemList);
	}

	public static String[] toStringArray() {

        int count = 0;

        for(EquipmentType make:EquipmentType.values()) {
            if (make.isNotEquipment) {
                count++;
            }
        }

		String[] equipmentStrings = new String[EquipmentType.values().length-count];
		int i = 0;
		
		for(EquipmentType equipment:EquipmentType.values()) {
			if (!equipment.isNotEquipment) {
                equipmentStrings[i] = equipment.toString();
				i++;
			}
		}
		
		return equipmentStrings;
	}
	
	public static String[] toPhotoStringArray() {
		String[] makes = new String[EquipmentType.values().length];
		int i = 0;
		
		for(EquipmentType make:EquipmentType.values()) {
			makes[i] = make.toString();
			i++;
		}
		
		return makes;
	}
	
	public boolean getHeating() {return this.hasHeating;}
	public boolean getCooling() {return this.hasCooling;}
	public boolean getSupply() {return this.hasSupply;}
	public boolean getReturn() {return this.hasReturn;}
	public boolean getOther() {return this.hasOther;}
	
	public boolean getHasByFunction(Function function) {
		switch (function) {
			case HEATING:
				return this.hasHeating;
			case COOLING:
				return this.hasCooling;
			case SUPPLY_FAN:
				return this.hasSupply;
			case RETURN_FAN:
				return this.hasReturn;
			case OTHER:
				return this.hasOther;
			default:
				return false;
		}
	}
	
/*	public boolean isLighting() {
		
		if (this.buildingItemType.equals(BuildingItemType.LIGHT))
			return true;
		else
			return false;
	}*/

    public BuildingItemType getBuildingItemType() {
        return this.buildingItemType;
    }
	
	public boolean isPhysicalEquipment() {
		return !this.isNotEquipment;
	}
/*Not sure if this was used, but I broke it by adding isNotEquipment	
	public static EquipmentType indexToENum(int index) {
		int i = 0;
		
		for(EquipmentType make:EquipmentType.values()) {
			if (i == index) {
				Log.v("EquipmentType", make.toString());
				return make;
			}
			else
				i++;
		}
		
		return EquipmentType.UNDEFINED;
	}
	*/
}
