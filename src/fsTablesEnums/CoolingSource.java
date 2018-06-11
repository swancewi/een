package fsTablesEnums;

/**
 * This class keeps track of all cooling sources
 * 
 * @author Steven Wancewicz
 * @version 2014-2-1
 * 
 */
public enum CoolingSource {

	NO_COOLING("No cooling"),
	CENTRAL_CHW("CHW from Central plant"), 
	LOCAL_CHW("CHW from chiller"),
	DX_COMPRESSOR("DX Compressor"),
	WATER("Water");
	
	private String name;
	
	CoolingSource(String name) 
	{
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
	
	public static CoolingSource getEnumType(String text) {
		
		for(CoolingSource make:CoolingSource.values()) {
			if(make.toString().equals(text))
				return make;
		}
		return CoolingSource.NO_COOLING;
	}
	
	public static String[] toStringArray() {
		String[] makes = new String[CoolingSource.values().length];
		int i = 0;
		
		for(CoolingSource make:CoolingSource.values()) {
			makes[i] = make.toString();
			i++;
		}
		
		return makes;
	}	

}
