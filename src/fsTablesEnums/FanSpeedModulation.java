package fsTablesEnums;

/**
 * This class keeps track of fan speed modulation options
 * 
 * @author Steven Wancewicz
 * @version 2014-2-1
 * 
 */
public enum FanSpeedModulation {
	
	NO_FAN("No motor"),
	CV("Constant volume"),
	VFD("VFD"),
	CYCLES("Cycles"),
	HYDRAULIC("Hydraulic coupling"),
	MAGNETIC("Magnetic coupling"),
	TWO_SPEED("Two-speed motor");
	
	private String name;
	
	FanSpeedModulation(String name) 
	{
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
	
	public static FanSpeedModulation getEnumType(String text) {
		
		for(FanSpeedModulation make:FanSpeedModulation.values()) {
			if(make.toString().equals(text))
				return make;
		}
		return FanSpeedModulation.NO_FAN;
	}
	
	public static String[] toStringArray() {
		String[] makes = new String[FanSpeedModulation.values().length];
		int i = 0;
		
		for(FanSpeedModulation make:FanSpeedModulation.values()) {
			makes[i] = make.toString();
			i++;
		}
		
		return makes;
	}	

}
