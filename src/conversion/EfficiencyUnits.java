package conversion;

import java.lang.IllegalArgumentException;

/**
 * This enum contains all efficiency units
 * 
 * @author Steven Wancewicz
 * @version 2013-5-6
 * 
 */
public enum EfficiencyUnits 
{
	EER(0.29307107, "EER"),
	KW_PER_TON(3.51685284,"kW/ton"),
	SEER(0.256437186, "SEER"),
	COP(1, "COP"),
	PERCENT(0.01, "%"),
	HSPF(0.29307107,"HSPF");
	
	private double conversionFactor;
	private String name;
	private static final double BTU_CONVERSION = 3.4121416351;
	private static final double HSPF_CONVERSION = 0.293;
	private static final double TON_PER_KBTU_CONVERSION = 12;
	private static final double SEER_PER_EER_CONVERSION = 0.875;
	private static final double PERCENT_PER_COP_CONVERSION = 100;
	
	EfficiencyUnits(double conversionFactor, String name)
	{
		this.conversionFactor = conversionFactor;
		this.name = name;
	}
	
	/**returns the conversion factor between input and output units of Efficiency
	 *  
	 * @param input type EfficiencyUnits, units to convert to
	 * @param output type EfficiencyUnits, units converting from
	 * @return conversion ratio
	 */
	public double GetEfficiencyConversion(EfficiencyUnits input, EfficiencyUnits output)
	{
		return input.EfficiencyPerCOP() / output.EfficiencyPerCOP();
	}
	
	/**returns conversion factor to COP (i.e. units per COP)
	 * 
	 * @param input
	 * @return Efficiency conversion factor to COP
	 */
	public double EfficiencyPerCOP()
	{
		return this.conversionFactor;
	}
	
	/**Returns string of efficiency units
	 * 
	 * @param input enum efficiency
	 * @return name of unit as a string
	 */
	public String toString()
	{
		return this.name;
	}
	
	public static String[] toStringArray() {
		String[] efficiencyUnits = new String[EfficiencyUnits.values().length];
		int i = 0;
		
		for(EfficiencyUnits efficiencyUnit:EfficiencyUnits.values()) {
			efficiencyUnits[i] = efficiencyUnit.toString();
			i++;
		}
		
		return efficiencyUnits;
	}
	
	public double convertToCOP(double OtherEfficiency) {
    
		double validateEfficiency = 0;

		switch(this) {
		
		case EER:
			validateEfficiency = OtherEfficiency / BTU_CONVERSION;
	        break;
		case KW_PER_TON:
			validateEfficiency = TON_PER_KBTU_CONVERSION / OtherEfficiency / BTU_CONVERSION;
        	break;
		case SEER:
			validateEfficiency = (OtherEfficiency * SEER_PER_EER_CONVERSION) / BTU_CONVERSION;
        	break;
		case COP:
			validateEfficiency = OtherEfficiency;
	        break;
		case PERCENT:
			validateEfficiency = OtherEfficiency/PERCENT_PER_COP_CONVERSION;
	        break;
		case HSPF:
			validateEfficiency = HSPF_CONVERSION * OtherEfficiency;
		}
		
		return validateEfficiency;
	}
	
	public double convertFromCOP(double COPEfficiency) {
		double validateEfficiency = 0;
		
		switch(this) {
		
		case EER:
			validateEfficiency = COPEfficiency * BTU_CONVERSION;
	        break;
		case KW_PER_TON:
			validateEfficiency = TON_PER_KBTU_CONVERSION / (COPEfficiency * BTU_CONVERSION);
        	break;
		case SEER:
			validateEfficiency = (COPEfficiency / SEER_PER_EER_CONVERSION) * BTU_CONVERSION;
        	break;
		case COP:
			validateEfficiency = COPEfficiency;
	        break;			
		case PERCENT:
			validateEfficiency = COPEfficiency*PERCENT_PER_COP_CONVERSION;
	        break;
		case HSPF:
			validateEfficiency = COPEfficiency / HSPF_CONVERSION;
		}
		
		return validateEfficiency;
	}
	
	public static EfficiencyUnits getEnumType(String text) {
		
		for(EfficiencyUnits equipment:EfficiencyUnits.values()) {
			if(equipment.toString().equals(text))
				return equipment;
		}
		throw new IllegalArgumentException("Invalid efficiency");
	}
}
