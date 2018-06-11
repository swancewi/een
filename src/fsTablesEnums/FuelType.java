package fsTablesEnums;

import android.util.Log;
import conversion.EnergyUnits;

/**
 * This enum keeps track of utility types
 * 
 * @author Steven Wancewicz 
 */
public enum FuelType 
{
	ELECTRICITY("Electricity", EnergyUnits.KWH),
	NATURALGAS("Natural gas", EnergyUnits.THERM),
	PROPANE("Propane", EnergyUnits.PROPANE_GAL),
	CHW("Chilled Water", EnergyUnits.MMBTU),
	HHW("Heating Hot Water", EnergyUnits.MMBTU),
	DIESEL("Diesel", EnergyUnits.DIESEL_GAL),
	FUELOIL("Fuel oil", EnergyUnits.FUEL_OIL_GAL),
	STEAM("Steam", EnergyUnits.MMBTU),
	NOTSPECIFIED("Unknown", EnergyUnits.KWH),
	WATER("Water", EnergyUnits.KWH);
	
	String name;
	EnergyUnits defaultUnits;

	FuelType(String name, EnergyUnits defaultUnits)
	{
		this.name = name;
		this.defaultUnits = defaultUnits;
	}
	
	public String toString()
	{
		return this.name;
	}
	
	public static FuelType getEnumType(String fuelType) {
		FuelType fuelType2;
		
		if (fuelType.equals(FuelType.ELECTRICITY.toString()))
			fuelType2 = FuelType.ELECTRICITY;
		else if(fuelType.equals(FuelType.NATURALGAS.toString()))
			fuelType2 = FuelType.NATURALGAS;
		else if(fuelType.equals(FuelType.PROPANE.toString()))
			fuelType2 = FuelType.PROPANE;
		else if(fuelType.equals(FuelType.DIESEL.toString()))
			fuelType2 = FuelType.DIESEL;
		else if(fuelType.equals(FuelType.FUELOIL.toString()))
			fuelType2 = FuelType.FUELOIL;
		else
			fuelType2 = FuelType.NOTSPECIFIED;

		return fuelType2;
	}
	
	public EnergyUnits getDefaultUnits() { return this.defaultUnits; }

	public static String[] toStringArray() {
		String[] fuelTypes = new String[FuelType.values().length];
		int i = 0;
		
		for(FuelType fuelType:FuelType.values()) {
			fuelTypes[i] = fuelType.toString();
			i++;
		}
		
		return fuelTypes;
	}


}
