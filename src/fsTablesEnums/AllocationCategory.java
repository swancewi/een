package fsTablesEnums;


/**
 * This enum keeps track of all DEFAULT allocation categories and assignment 
 * 
 * @author Steven Wancewicz
 * @version 2014-1-3
 * 
 */
public enum AllocationCategory {
	
	//electric
	DX_COOLING("DXCooling",FuelType.ELECTRICITY),
	CHILLERS("Chillers",FuelType.ELECTRICITY),
	COOLING_TOWER_FANS("Cooling Tower Fans",FuelType.ELECTRICITY),
	DXHEATING("DXHeating",FuelType.ELECTRICITY),
	CENTRALHEATING("Central Heating",FuelType.ELECTRICITY),
	ELECTRICRESISTANCEHEATING("Electric Resistance Heating",FuelType.ELECTRICITY),
	AIRHANDLINGUNITS("Air Handling Units",FuelType.ELECTRICITY),
	PACKAGEUNITFANS("Package Unit Fans",FuelType.ELECTRICITY),
	HOTWATERPUMPS("Hot Water Pumps",FuelType.ELECTRICITY),
	CHILLEDWATERPUMPS("Chilled Water Pumps",FuelType.ELECTRICITY),
	COOLINGTOWERWATERPUMPS("Cooling Tower Water Pumps",FuelType.ELECTRICITY),
	DOMESTICWATERHEATER("Domestic Hot Water Heater",FuelType.ELECTRICITY),
	DOMESTICHOTWATERPUMP("Domestic Hot Water Pump",FuelType.ELECTRICITY),
	OTHERPUMPS("Other Pumps",FuelType.ELECTRICITY),
	FANS("Fans",FuelType.ELECTRICITY),
	WALKINREFRIGERATORS("Walk-in Refrigerators",FuelType.ELECTRICITY),
	INTERIORLIGHTING("Interior Lighting",FuelType.ELECTRICITY),
	EXTERIORLIGHTING("Exterior Lighting",FuelType.ELECTRICITY),
	EVAPORATIVECOOLING("Evaporative Cooling",FuelType.ELECTRICITY),
	TRANSFORMERS("Transformers",FuelType.ELECTRICITY),
	
	//natural gas
	CENTRAL_SPACE_HEATING_NG("Central Space Heating",FuelType.NATURALGAS),
	SPACE_HEATING_NG("Space Heating",FuelType.NATURALGAS),
	ABSORPTION_COOLING_NG("Absorption Cooling",FuelType.NATURALGAS),
	DOMESTICWATERHEATER_NG("Domestic Water Heater",FuelType.NATURALGAS),

	//propane
	CENTRAL_SPACE_HEATING_PROP("Central Space Heating",FuelType.PROPANE),
	SPACE_HEATING_PROP("Space Heating",FuelType.PROPANE),
	DOMESTIC_WATER_HEATER_PROP("Domestic Water Heater",FuelType.PROPANE),
	
	//none
	NULL("Null", FuelType.NOTSPECIFIED);
	
	private String name;
	private FuelType fuelType;
	
	AllocationCategory(String name, FuelType fuelType) {
		this.name = name;
		this.fuelType = fuelType;
	}
	
	public String toString() {return this.name;}
	public FuelType getFuelType() {return this.fuelType;}
	
	public static AllocationCategory getEnumType(String aCat) {
		for(AllocationCategory aCategory:AllocationCategory.values()) {
			if(aCategory.toString().equals(aCat))
				return aCategory;
		}
		
		return AllocationCategory.NULL;
	}
}
