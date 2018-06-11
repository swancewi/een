package fsTablesEnums;

/**
 * This enum keeps track of functions
 * 
 * @author Steven Wancewicz
 * @version 2013-5-30
 * 
 */
public enum Function 
{
	HEATING("Heating"),
	COOLING("Cooling"),
	SUPPLY_FAN("SupplyFan"),
	RETURN_FAN("ReturnFan"),
	OTHER("General");
	
	private String name;
	
	Function(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return this.name;
	}
}
