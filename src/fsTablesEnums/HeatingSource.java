package fsTablesEnums;

import android.util.Log;

/**
 * This program will output a table of all items input for 5 items
 * 
 * @author Steven Wancewicz, S01938415
 * @version 2013-9-6, CSC-241 Assignment 4, Exercise 18.9
 * 
 */
public enum HeatingSource {
	
	NO_HEATING("None"),
	DXCOMPRESSOR("DX compressor"),
	ELEC_DUCT_HEATER("Electric duct heater"),
	ELEC_RESISTANCE("Electric resistance"),
	NATURAL_GAS("Natural gas-fired"),
	PROPANE("Propane-fired");
	
	private String name;
	
	HeatingSource(String name) 
	{
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
	
	public static HeatingSource getEnumType(String text) {
		
		for(HeatingSource make:HeatingSource.values()) {
			if(make.toString().equals(text))
				return make;
		}
		//Log.v("Heating Source Enum", "No enum found");
		return HeatingSource.NO_HEATING;
	}
	
	public static String[] toStringArray() {
		String[] makes = new String[HeatingSource.values().length];
		int i = 0;
		
		for(HeatingSource make:HeatingSource.values()) {
			makes[i] = make.toString();
			i++;
		}
		
		return makes;
	}	

}
