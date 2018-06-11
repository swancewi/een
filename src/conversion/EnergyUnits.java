package conversion;

/**
 * This enum has different units
 * 
 * @author Steven Wancewicz
 * @version 5-16-2013
 * 
 */
public enum EnergyUnits {
	KWH(1, "kWh", 1),
	THERM(0.0341295634, "therm", 2),
	CCF(0.0334524, "ccf", 3),
	MCF(0.0033452, "mcf", 4),
	PROPANE_GAL(0.0372912, "gal", 5),
	DIESEL_GAL(0.0263486, "gal", 6),
	FUEL_OIL_GAL(0.0243724, "gal", 7),
	MMBTU(293.07107, "MMBtu", 8);
	
	private double conversionFactor;
	private String name;
	private int id;
	
	EnergyUnits(double conversionFactor, String name, int id)
	{
		this.conversionFactor = conversionFactor;
		this.name = name;
		this.id = id;
	}
	
	/** This function returns the conversion factor for 
	 * 
	 * @param input
	 * @param output
	 * @return conversionFactor ratio of input units over output units
	 */
	public double ConvertUnits(EnergyUnits input, EnergyUnits output)
	{
		double conversionRate = input.getkWhrate() / output.getkWhrate ();
		return conversionRate;
	}
	
	public double getkWhrate() { return this.conversionFactor; }
	
	public String toString(EnergyUnits input) { return name; }
	
	public int getID() {return this.id; }

    public static String[] toStringArray() {
        String[] fuelTypes = new String[EnergyUnits.values().length];
        int i = 0;

        for(EnergyUnits fuelType:EnergyUnits.values()) {
            fuelTypes[i] = fuelType.name;
            i++;
        }

        return fuelTypes;
    }
}