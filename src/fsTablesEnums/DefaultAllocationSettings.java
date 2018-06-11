package fsTablesEnums;

import conversion.EfficiencyUnits;
import conversion.PowerUnits;

/**
 * This class will keep track of default units
 * 
 * @author Steven Wancewicz
 * @version 2014-2-4
 * 
 */
public enum DefaultAllocationSettings {

	MOTOR(PowerUnits.hp, EfficiencyUnits.PERCENT, FuelType.ELECTRICITY),
	BOILER(PowerUnits.kBtuH, EfficiencyUnits.PERCENT, FuelType.NATURALGAS),
	CHILLER(PowerUnits.ton, EfficiencyUnits.KW_PER_TON, FuelType.ELECTRICITY),
	SPLIT_COOLING(PowerUnits.kBtuH, EfficiencyUnits.SEER, FuelType.ELECTRICITY),
	HEAT_PUMP_HEATING(PowerUnits.kBtuH, EfficiencyUnits.HSPF, FuelType.ELECTRICITY),
	COOLING_TOWER(PowerUnits.ton,EfficiencyUnits.PERCENT, FuelType.WATER),
	TRANSFORMER(PowerUnits.kVA, EfficiencyUnits.PERCENT, FuelType.ELECTRICITY),	
	DEFAULT(PowerUnits.kBtuH, EfficiencyUnits.COP, FuelType.STEAM);
	
	PowerUnits pUnits;
	EfficiencyUnits eUnits;
	FuelType fType;
	
	DefaultAllocationSettings(PowerUnits pUnits, EfficiencyUnits eUnits, FuelType fType) {
		this.pUnits = pUnits;
		this.eUnits = eUnits;
		this.fType = fType;
	}
	
	public PowerUnits getPowerUnits() { return this.pUnits; }
	public EfficiencyUnits getEfficiencyUnits() {return this.eUnits;}
	public FuelType getFuelType() {return this.fType;}
}
