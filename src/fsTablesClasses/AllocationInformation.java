package fsTablesClasses;

import java.io.IOException;

import android.util.Log;
import conversion.EfficiencyUnits;
import conversion.PowerUnits;
import fsTablesEnums.AllocationCategory;
import fsTablesEnums.FuelType;
import fsTablesEnums.Function;

/**
 * This program keeps track of basic information used in end-use breakdown
 * Capacity & Units
 * Efficiency & Units
 * Fuel Type
 * 
 * @author Steven Wancewicz
 * @version 2013-5-30
 * 
 */
public class AllocationInformation extends Object {

	//If updating values contained in class, need to update both constructors 
	private String source;
	private double capacity;
	private double efficiency;
	private FuelType fuelType;
	private AllocationCategory allocationCategory;
	private Function function;
	private double runHours;
	private double diversityFactor;
	private PowerUnits capacityUnits;
	private EfficiencyUnits efficiencyUnits;
	private int id;
	
	public String toCSV() {
		String csv;
		
		csv =  "\"" + this.getFuelType().toString() +"\",";
		csv += "\"" + this.getCapacityInUnits() + " " + this.capacityUnits.toString() +"\",";
		csv += "\"" + this.getEfficiencyInUnits() + " " + this.efficiencyUnits.toString() +"\",";
		csv += this.runHours;
		
		return csv;
	}
	
	public AllocationInformation(Function function)
	{
		this.capacity = 0;
		this.efficiency = 0;
		this.fuelType = FuelType.NOTSPECIFIED;
		this.function = function;
		this.runHours = 0;
		this.diversityFactor = 1;
		this.capacityUnits = PowerUnits.W;
		this.efficiencyUnits = EfficiencyUnits.PERCENT;
	}
	
	public AllocationInformation(AllocationInformation aI)
	{
		this.capacity = aI.capacity;
		this.efficiency = aI.efficiency;
		this.fuelType = aI.fuelType;
		this.allocationCategory = aI.allocationCategory;
		this.runHours = aI.runHours;
		this.diversityFactor = aI.diversityFactor;
		this.capacityUnits = aI.capacityUnits;
		this.efficiencyUnits = aI.efficiencyUnits;
	}
	
	public void setInformation(double heatingCapacity, PowerUnits capacityUnits, double efficiency, EfficiencyUnits efficiencyUnits, FuelType fuelType) throws IOException
	{
		try 
		{
			this.setCapacity(heatingCapacity, capacityUnits);
			this.setEfficiency(efficiency, efficiencyUnits);
			this.setFuelType(fuelType);
		} catch (IOException e) 
		{
			throw new IOException();
		}
	}
	
	public void setAllocation(AllocationInformation aI) {
		this.source = aI.source;
		this.id = aI.id;
		this.capacity = aI.capacity;
		this.efficiency = aI.efficiency;
		this.fuelType = aI.fuelType;
		this.allocationCategory = aI.allocationCategory;
		this.runHours = aI.runHours;
		this.diversityFactor = aI.diversityFactor;
		this.capacityUnits = aI.capacityUnits;
		this.efficiencyUnits = aI.efficiencyUnits;	
	}
	
	public void setCapacity(double capacity, PowerUnits energyUnits) throws IOException
	{
		if (capacity >= 0) {
			this.capacity = capacity*energyUnits.getkWhrate();
			this.capacityUnits = energyUnits;
			//Log.v("Allocation Information", "Capacity: " + capacity + " " + energyUnits.toString());
			//Log.v("Allocation Information", "Capacity: " + this.capacity + " kW");
		}
		else
			throw new IOException("negative capacity");
	}
	
	public void setCapacity(double btuCapacity) throws IOException
	{
		if (btuCapacity >= 0) {
			this.capacity = btuCapacity;
			this.capacityUnits = PowerUnits.btuH;
		}
		else 
			throw new IOException("negative capacity");
	}
	
	public void setPowerUnits(PowerUnits powerUnits) {
		this.capacityUnits = powerUnits; 
		//Log.v("Allocation Information", "Capacity units set to: " + this.capacityUnits.toString());
		}
	
	public void setAllocationID(int id) {
		this.id = id;
	}
		
	public void setEfficiency(double efficiency, EfficiencyUnits efficiencyUnits) throws IOException
	{
		if (efficiency >= 0) {
			this.efficiency = efficiencyUnits.convertToCOP(efficiency);
			//Log.v("AllocationInformation", "Efficiency(COP): " + this.efficiency + "Efficiency("+efficiencyUnits.toString()+"): " + efficiency);
			this.efficiencyUnits = efficiencyUnits;
		}
		else
			throw new IOException("negative efficiency");
	}
	
	public void setEfficiency(double COPefficiency) throws IOException
	{
		if (COPefficiency >= 0)
			this.efficiency = COPefficiency;
		else
			throw new IOException("negative efficiency");
	}
	
	public void setEfficiencyUnits(EfficiencyUnits efficiencyUnits) {
		//Log.v("Allocation Information", "EF units: " + this.efficiencyUnits); 
		this.efficiencyUnits = efficiencyUnits;
	}
	
	public void setFuelType(FuelType fuelType) { this.fuelType = fuelType; }
	
	public void setHours(double hours) throws IOException {
		if (hours >= 0) {
			this.runHours = hours;
			//Log.v("AllocationInformation","Run hours set to: " + this.runHours);
		}
		else 
			throw new IOException("negative run hours");
	}
	
	public void setAllocationCategory (AllocationCategory aCat) {this.allocationCategory = aCat; }
	public void setDF(double DF) throws IOException {
		if (DF>=0)
			this.diversityFactor = DF;
		else
			throw new IOException ("negative diversity factor");
	}
	
	public void setSource(String source) {this.source = source;	}

	public double getCapacity() { //Log.v("Allocation Information", "Capacity: " + this.capacity + "" );
		return this.capacity; 
	}
	public double getEfficiency() {return this.efficiency; }
	public double getCapacityInUnits() {//Log.v("Allocation Info Get", "Capacity: " + this.capacity/this.capacityUnits.getkWhrate() + " " + this.getPowerUnits().toString() ); 
		return this.capacity/this.capacityUnits.getkWhrate();
	}
	public double getEfficiencyInUnits() {return this.efficiencyUnits.convertFromCOP(this.efficiency); }
	public FuelType getFuelType() { return this.fuelType; }	
	public double getHours() {return this.runHours; }	
	public double getDF() {return this.diversityFactor; }	
	public AllocationCategory getAllocationCategory() {return this.allocationCategory; }
	public PowerUnits getPowerUnits() {return this.capacityUnits; }
	public EfficiencyUnits getEfficiencyUnits() {return this.efficiencyUnits; }
	public int getAllocationID() {return this.id; }
	public String getSource() {return this.source; }
	
	public String OutputString()
	{
		String output =	String.format("%s Information%nCapacity (Btu/hr): %.0f%nEfficiency (COP): %.3f%nUtility Type: %s%n", 
				this.allocationCategory.toString(), this.capacity, this.efficiency, this.fuelType.toString());
		return output;
	}

}
