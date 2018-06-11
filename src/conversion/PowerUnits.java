package conversion;

/**
 * This enum has different units
 * 
 * @author Steven Wancewicz
 * @version 5-16-2013
 * 
 */
public enum PowerUnits {
	btuH(0.000293071, "btuH"),
	kBtuH(0.293071, "kBtuh"),
	kW(1, "kW"),
	W(0.001, "W"),
	ton(3.516852, "ton"),
	kVA(1,"kVA"),
	hp(0.745699872, "hp");
	
	private double conversionFactor;
	private String name;
	
	PowerUnits(double conversionFactor, String name)
	{
		this.conversionFactor = conversionFactor;
		this.name = name;
	}
	
	/** This function returns the conversion factor for 
	 * 
	 * @param input
	 * @param output
	 * @return conversionFactor ratio of input units over output units
	 */
	public double ConvertUnits(PowerUnits input, PowerUnits output)
	{
		double conversionRate = input.getkWhrate() / output.getkWhrate ();
		return conversionRate;
	}
	
	public double getkWhrate()
	{
		return this.conversionFactor;
	}
	
	public String toString(PowerUnits input)
	{
		return name;
	}
	
	public static String[] toStringArray() {
		String[] powerUnits = new String[PowerUnits.values().length];
		int i = 0;
		
		for(PowerUnits powerUnit:PowerUnits.values()) {
			powerUnits[i] = powerUnit.toString();
			i++;
		}
		return powerUnits;
	}
	
	public static PowerUnits getEnumType(String text) {
		
		for(PowerUnits equipment:PowerUnits.values()) {
			if(equipment.toString().equals(text))
				return equipment;
		}
		throw new IllegalArgumentException("Invalid units");
	}
}
