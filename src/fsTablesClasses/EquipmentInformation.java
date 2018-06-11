package fsTablesClasses;

import android.util.Log;

import java.io.IOException;

import conversion.EfficiencyUnits;
import conversion.PowerUnits;
import fsTablesEnums.AllocationCategory;
import fsTablesEnums.CoolingSource;
import fsTablesEnums.EquipmentType;
import fsTablesEnums.FanSpeedModulation;
import fsTablesEnums.FuelType;
import fsTablesEnums.Function;
import fsTablesEnums.HeatingSource;

/**
 * This class holds equipment information
 * 
 * @author Steven Wancewicz
 * @version 2013-5-27
 * 
 */
public class EquipmentInformation {

	//If updating values in class, need to add to "copy" constructor
	protected AllocationInformation heatingAI = new AllocationInformation(Function.HEATING);
	protected AllocationInformation coolingAI = new AllocationInformation(Function.COOLING);
	protected AllocationInformation supplyAI = new AllocationInformation(Function.SUPPLY_FAN);
	protected AllocationInformation returnAI = new AllocationInformation(Function.RETURN_FAN);
	protected AllocationInformation otherAI = new AllocationInformation(Function.OTHER);
	private String installYear;
	private String serialNumber;
	private String modelNumber;
	private String manufacturer;
	private int lookupCode; //100 is model result, 1s are serial results
	private String chillerType;  //options are Scroll, Compressor, Reciprocating, Absorption
	private EquipmentType equipmentType;
	private String unitID;
	private String serves;
	private String control;
	private String Schedule;
	private String location;
	private int quantity;
	private String notes;
	private static final int numColumns = 10;  //used for sql table column count
	private double onPeakPercent;
	private double offPeakPercent;
	private double onPeakDays;
	private double powerFactor;
	private String refrigTemp;
	private String motorType;
	private String dhwType;
	private double dhwEnergyFactor;
	private String dhwTankSize;
	private String pumpModulation;
	private String boilerType;
	private String boilerMedium;
	private String chillerCondenserType;
	private String chillerEvaporatorType;
	private String AHU_Economizer;
	private String AHU_EconomizerLockout;
	private String AHU_EconomizerDamperControl;
	private String AHU_PreheatCoil;
	private String AHU_HeatingCoil;
	private String AHU_CoolingCoil;
	private String AHU_TotalCFM;
	private String refrigAssociated;
	private final String CLASS_NAME = "EquipmentInformation";

	public static String csvHeaders = "\"Building\",\"Install Year\",\"Serial #\",\"Model #\",\"Manufacturer\",\"Lookup Code\",\"Chiller Type\",\"Equipment type\","
			+ "\"Unit ID\",\"Serves\",\"Control Type\",\"Schedule\","
            + "\"Heating Source\",\"Cooling Source\",\"Supply Fan\",\"Return Fan\","
			+ "\"Location\",\"Quantity\",\"Notes\",\"OnPeak Percent\",\"Off Peak Percent\",\"On Peak Days\",\"Power Factor\",\"Refrigerator Temp\","
			+ "\"Motor type\",\"DHW Type\",\"DHW Energy Factor\",\"Pump Modulation\",\"Boiler Type\",\"Boiler Medium\",\"Chiller Condenser Type\",\"Chiller Evaporator Type\","
			+ "\"Economizer Type\",\"Economizer Lockout\",\"Economizer Control\",\"Preheat Coil Type\",\"Heating Coil Type\",\"Cooling Coil Type\","
            + "\"AHU CFM\",\"Associated Unit\","
            + "\"Heating Fuel\",\"Heating Capacity\",\"Heating Efficiency\",\"Heating Hours\","
            + "\"Cooling Fuel\",\"Cooling Capacity\",\"Cooling Efficiency\",\"Cooling Hours\","
            + "\"Supply fan Fuel\",\"Supply fan Capacity\",\"Supply fan Efficiency\",\"Supply fan Hours\","
            + "\"Return fan Fuel\",\"Return fan Capacity\",\"Return fan Efficiency\",\"Return fan Hours\","
            + "\"Other Fuel\",\"Other Capacity\",\"Other Efficiency\",\"Other Hours\","
			+ "\n";

	public String csvExport(String buildingName) {
		String csvValues;
		csvValues = "\"" + buildingName +"\",";
		csvValues += "\"" + this.installYear+"\",";
		csvValues +="\"" + this.serialNumber + "\",";
        csvValues +="\"" + this.modelNumber+"\",";
        csvValues += "\"" + this.manufacturer+"\",";
		csvValues +="\"" + this.lookupCode + "\",";
        csvValues +="\"" + this.chillerType+"\",";
		csvValues += "\"" + this.equipmentType.toString()+"\",";
		csvValues +="\"" + this.unitID + "\",";
        csvValues +="\"" + this.serves+"\",";
		csvValues +="\"" + this.control+"\",";
		csvValues +="\"" + this.Schedule+"\",";
		csvValues +="\"" + this.getSource(Function.HEATING)+"\",";
		csvValues +="\"" + this.getSource(Function.COOLING).toString()+"\",";
		csvValues +="\"" + this.getSource(Function.SUPPLY_FAN).toString()+"\",";
		csvValues +="\"" + this.getSource(Function.RETURN_FAN).toString()+"\",";
		csvValues +="\"" + this.location+"\",";
		csvValues +="\"" + this.quantity+"\",";
		csvValues +="\"" + this.notes+"\",";
		csvValues +="\"" + this.onPeakPercent+"\",";
		csvValues +="\"" + this.offPeakPercent+"\",";
		csvValues +="\"" + this.onPeakDays+"\",";
		csvValues +="\"" + this.powerFactor+"\",";
		csvValues +="\"" + this.refrigTemp+"\",";
		csvValues +="\"" + this.motorType+"\",";
		csvValues +="\"" + this.dhwType+"\",";
		csvValues +="\"" + this.dhwEnergyFactor+"\",";
		csvValues +="\"" + this.pumpModulation+"\",";
		csvValues +="\"" + this.boilerType+"\",";
		csvValues +="\"" + this.boilerMedium+"\",";
		csvValues +="\"" + this.chillerCondenserType+"\",";
		csvValues +="\"" + this.chillerEvaporatorType+"\",";
		csvValues +="\"" + this.AHU_Economizer+"\",";
		csvValues +="\"" + this.AHU_EconomizerLockout+"\",";
		csvValues +="\"" + this.AHU_EconomizerDamperControl+"\",";
		csvValues +="\"" + this.AHU_PreheatCoil+"\",";
		csvValues +="\"" + this.AHU_HeatingCoil+"\",";
		csvValues +="\"" + this.AHU_CoolingCoil+"\",";
		csvValues +="\"" + this.AHU_TotalCFM+"\",";
		csvValues +="\"" + this.refrigAssociated+"\",";
		for(Function function:Function.values())
			csvValues += this.getAllocationInformation(function).toCSV() + ",";
		csvValues += "\n";
		
		return csvValues;
	}
	
	public EquipmentInformation() { 
		this.getAllocationInformation(Function.HEATING).setSource(HeatingSource.NO_HEATING.name());
		this.getAllocationInformation(Function.COOLING).setSource(CoolingSource.NO_COOLING.name());
		this.getAllocationInformation(Function.SUPPLY_FAN).setSource(FanSpeedModulation.NO_FAN.name());
		this.getAllocationInformation(Function.RETURN_FAN).setSource(FanSpeedModulation.NO_FAN.name());
	}
	
	public EquipmentInformation(String manufacturer, String modelNumber, String serialNumber)
	{
		this.manufacturer = manufacturer;
		this.serialNumber = serialNumber;
		this.modelNumber = modelNumber;
		this.equipmentType = EquipmentType.UNDEFINED;
	}
	
	public EquipmentInformation(EquipmentInformation equipmentInfo)
	{
		this.coolingAI = new AllocationInformation(equipmentInfo.coolingAI);
		this.heatingAI = new AllocationInformation(equipmentInfo.heatingAI);
		this.supplyAI = new AllocationInformation(equipmentInfo.supplyAI);
		this.returnAI = new AllocationInformation(equipmentInfo.returnAI);
		this.installYear = equipmentInfo.installYear;
		this.serialNumber = equipmentInfo.serialNumber;
		this.modelNumber = equipmentInfo.modelNumber;
		this.manufacturer = equipmentInfo.manufacturer;
		this.lookupCode = equipmentInfo.lookupCode;
		this.chillerType = equipmentInfo.chillerType;
		this.equipmentType = equipmentInfo.equipmentType;
	}

	//general getters
	public String getSerialNumber() 	{ return this.serialNumber; }
	public String getModelNumber() 	{ return this.modelNumber; }
	public String getManufacturer() 	{ return this.manufacturer; }
	public EquipmentType getEquipmentType() 	{ return this.equipmentType; }
	public int getLookupCode() 	{ return this.lookupCode; }
	public String getInstallYear() { return this.installYear; }
	public String getUnitID() { //Log.v("UNITID", this.unitID); 
		return this.unitID; 
	}
	public String getServes() {return this.serves; }
	public String getControl() {return this.control;}
	public String getSchedule() {return this.Schedule;}
	public String getLocation() {return this.location; }
	public int getQuantity() {return this.quantity; }
	public String getNotes() {return this.notes; }	
	
	//heating get functions
	/**Returns heating efficiency in %

	/*public double getHeatingEfficiency() { return this.heatingAI.getEfficiency(); }
	/**Returns heating capacity in kW
	 * @return double heatingCapacity
	 */
	/*public double getHeatingCapacity() 	{ 
		//Log.v("EquipmentInformation get", "heating capacity: " + this.heatingAI.getCapacity()); 
		return this.heatingAI.getCapacity(); }
	public FuelType getHeatingFuelType() {return this.heatingAI.getFuelType(); }
	public AllocationCategory getHeatingAllocationCategory() {return this.heatingAI.getAllocationCategory(); }
	public double getHeatingHours() {return this.heatingAI.getHours(); }
	public double getHeatingDF() {return this.heatingAI.getDF(); }
	public PowerUnits getHeatingCapacityUnits() {return this.heatingAI.getPowerUnits();}
	public EfficiencyUnits getHeatingEfficiencyUnits() {return this.heatingAI.getEfficiencyUnits();}
	public double getHeatingCapacityInUnits() {return this.heatingAI.getCapacityInUnits(); }
	public double getHeatingEfficiencyInUnits() {return this.heatingAI.getEfficiencyInUnits(); }*/
	
	//custom getter functions
	public double getOnPeakPercent() {return this.onPeakPercent;}
	public double getOffPeakPercent() {return this.offPeakPercent;}
	public double getOnPeakDays() {return this.onPeakDays;}
	public double getPowerFactor() {return this.powerFactor;}
	public String getRefrigTemp() {return this.refrigTemp;}
	public String getMotorType() {return this.motorType;}
	public String getDhwType() {return this.dhwType;}
	public double getDhwEnergyFactor() {return this.dhwEnergyFactor;}
	public String getDhwTankSize() {return this.dhwTankSize;}
	public String getPumpModulation() {return this.pumpModulation;}
	public String getBoilerType() {return this.boilerType;}
	public String getBoilerMedium() {return this.boilerMedium;}
	public String getChillerType() {return this.chillerType;}
	public String getChillerCondenserType() {return this.chillerCondenserType;}
	public String getChillerEvaporatorType() {return this.chillerEvaporatorType;}
	public String getAHU_Economizer() {return this.AHU_Economizer;}
	public String getAHU_EconomizerLockout() {return this.AHU_EconomizerLockout;}
	public String getAHU_EconomizerDamperControl() {return this.AHU_EconomizerDamperControl;}
	public String getAHU_PreheatCoil() {return this.AHU_PreheatCoil;}
	public String getAHU_HeatingCoil() {return this.AHU_HeatingCoil;}
	public String getAHU_CoolingCoil() {return this.AHU_CoolingCoil;}
	public String getAHU_TotalCFM() {return this.AHU_TotalCFM;}
	public String getRefrigAssociated() {return this.refrigAssociated;}
	
	//generic functions for allocation information
	public FuelType getFuelType(Function f) {return this.getAllocationInformation(f).getFuelType();}
	public AllocationCategory getAllocationCategory(Function f) {return this.getAllocationInformation(f).getAllocationCategory();}
	public double getHours(Function f) {return this.getAllocationInformation(f).getHours();}
	public double getDF(Function f) {return this.getAllocationInformation(f).getDF();}
	public PowerUnits getCapacityUnits(Function f) {return this.getAllocationInformation(f).getPowerUnits();}
	public EfficiencyUnits getEfficiencyUnits(Function f) {return this.getAllocationInformation(f).getEfficiencyUnits();}
	public double getCapacityInUnits(Function f) {return this.getAllocationInformation(f).getCapacityInUnits();}
	public double getEfficiencyInUnits(Function f) {return this.getAllocationInformation(f).getEfficiencyInUnits();}
	public double getEfficiency(Function f) {return this.getAllocationInformation(f).getEfficiency();}
	public double getCapacity(Function f) {return this.getAllocationInformation(f).getCapacity();}
	public String getSource(Function f) {return this.getAllocationInformation(f).getSource();}
	
	//general setters
	public void setLookupCode(int lookupCode) { this.lookupCode = lookupCode; }
	public void setEquipmentType(EquipmentType equip) { this.equipmentType = equip; }
	public void setInstallYear(String string) { this.installYear = string; }
	public void setManufactuer(String make) {this.manufacturer = make; }
	public void setModel(String model) {this.modelNumber = model; }
	public void setSerial(String serial) {this.serialNumber = serial; }
	public void setUnitID(String unitID) {
		this.unitID = unitID;
		//Log.v("UNITID", this.unitID);
	}
	public void setServes(String serves) {this.serves = serves; }
	public void setControl(String control) {this.control = control;}
	public void setSchedule(String schedule) {this.Schedule =schedule;}
	public void setLocation(String location) {this.location = location; }
	public void setQuantity(int quantity) {this.quantity = quantity; }
	public void setNotes(String notes) {this.notes = notes; }
	
	//custom setters
	public void setOnPeakPercent(double onPeakPercent) {this.onPeakPercent = onPeakPercent;}
	public void setOffPeakPercent(double offPeakPercent) {this.offPeakPercent = offPeakPercent;}
	public void setOnPeakDays(double onPeakDays) {this.onPeakDays = onPeakDays;}
	public void setPowerFactor(double powerFactor) {this.powerFactor = powerFactor;}
	public void setRefrigTemp(String refrigTemp) {this.refrigTemp = refrigTemp;}
	public void setMotorType(String motorType) {this.motorType = motorType;}
	public void setDhwType(String dhwType) {this.dhwType = dhwType;}
	public void setDhwEnergyFactor(double dhwEnergyFactor) {this.dhwEnergyFactor = dhwEnergyFactor;}
	public void setDhwTankSize(String tankSize) {this.dhwTankSize = tankSize;}
	public void setPumpModulation(String string) {this.pumpModulation = string;}
	public void setBoilerType(String boilerType) {this.boilerType = boilerType;}
	public void setBoilerMedium(String boilerMedium) {this.boilerMedium = boilerMedium;}
	public void setChillerType(String chillerType) {this.chillerType = chillerType;}
	public void setChillerCondenserType(String chillerCondenserType) {this.chillerCondenserType = chillerCondenserType;}
	public void setChillerEvaporatorType(String chillerEvaporatorType) {this.chillerEvaporatorType = chillerEvaporatorType;}
	public void setAHU_Economizer(String AHU_Economizer) {this.AHU_Economizer = AHU_Economizer;}
	public void setAHU_EconomizerLockout(String AHU_EconomizerLockout) {this.AHU_EconomizerLockout = AHU_EconomizerLockout;}
	public void setAHU_EconomizerDamperControl(String AHU_EconomizerDamperControl) {this.AHU_EconomizerDamperControl = AHU_EconomizerDamperControl;}
	public void setAHU_PreheatCoil(String AHU_PreheatCoil) {this.AHU_PreheatCoil = AHU_PreheatCoil;}
	public void setAHU_HeatingCoil(String AHU_HeatingCoil) {this.AHU_HeatingCoil = AHU_HeatingCoil;}
	public void setAHU_CoolingCoil(String AHU_CoolingCoil) {this.AHU_CoolingCoil = AHU_CoolingCoil;}
	public void setAHU_TotalCFM(String AHU_TotalCFM) {this.AHU_TotalCFM = AHU_TotalCFM;}
	public void setRefrigAssociated(String refrigAssociated) {this.refrigAssociated = refrigAssociated;}
	
	//generic function setters for AI
	public void setSource(Function f, String source) {
		this.getAllocationInformation(f).setSource(source);
		//Log.v(this.CLASS_NAME, f.name() + " source to set: " + source + "is: " + this.getAllocationInformation(f).getSource());
	}
	public void setAllocation(Function f, AllocationInformation ai) {
		switch (f) {
		case HEATING:
			this.heatingAI.setAllocation(ai);
			//Log.v(this.CLASS_NAME + "/" + "setAllocation", "Heating: " + this.heatingAI.toCSV());
			break;
		case COOLING: 
			this.coolingAI.setAllocation(ai);
			break;
		case SUPPLY_FAN:
			this.supplyAI.setAllocation(ai);
			//Log.v(this.CLASS_NAME + "/" + "setAllocation", "Heating: " + this.supplyAI.toCSV());
			break;
		case RETURN_FAN:
			this.returnAI.setAllocation(ai);
			break;
		case OTHER:
			this.otherAI.setAllocation(ai);
			break;
	}
	
	}
	public void setCapacity(Function f, double btuCapacity) throws IOException {this.getAllocationInformation(f).setCapacity(btuCapacity);}
	public void setCapacity(Function f, double capacity, PowerUnits pUnits) throws IOException {this.getAllocationInformation(f).setCapacity(capacity, pUnits); }
	public void setCapacityUnits(Function f, PowerUnits pUnits) {this.getAllocationInformation(f).setPowerUnits(pUnits);}
	public void setEfficiency(Function f, double efficiencyCOP) throws IOException {this.getAllocationInformation(f).setEfficiency(efficiencyCOP);}
	public void setEfficiency(Function f, double efficiency, EfficiencyUnits eUnits) throws IOException {this.getAllocationInformation(f).setEfficiency(efficiency, eUnits);}	
	public void setEfficiencyUnits(Function f, EfficiencyUnits eUnits) {this.getAllocationInformation(f).setEfficiencyUnits(eUnits);}
	public void setUtilityType(Function f, FuelType utilityType) {this.getAllocationInformation(f).setFuelType(utilityType);}
	public void setHours(Function f, double hours) throws IOException {this.getAllocationInformation(f).setHours(hours);}
	public void setAllocationCategory(Function f, AllocationCategory aCat) {this.getAllocationInformation(f).setAllocationCategory(aCat);}
	public void setDF(Function f, double DF) throws IOException {this.getAllocationInformation(f).setDF(DF);}
	
	//all allocation setters
	public void setAllocationID(Function function, int id) {
		this.getAllocationInformation(function).setAllocationID(id);
	}
	
	public int getAllocationID(Function function) {
		return this.getAllocationInformation(function).getAllocationID();
	}
	
	public String getAllocationCSV(Function function) {
		return this.getAllocationInformation(function).toCSV();
	}
	
	
	private AllocationInformation getAllocationInformation(Function function) {
		
		switch (function) {
			case HEATING:
				return this.heatingAI;
			case COOLING: 
				return this.coolingAI;
			case SUPPLY_FAN:
				return this.supplyAI;
			case RETURN_FAN:
				return this.returnAI;
			case OTHER:
				return this.otherAI;
		}
		
		throw new NullPointerException("Invalid function type");
	}
	/*
	public void PrintMessageBox()
	{
		String message = String.format("Manufacturer: %s%nModelNumber: %s%nSerial Number: %s%nInstall Year: %s%nEquipment Type: %s%n",
				manufacturer.toString(), modelNumber, serialNumber, installYear, equipmentType.toString()); 
		switch (this.equipmentType)
		{
		case AIRHANDLER:
		case PACKAGEUNIT:
		case CHILLER:
			message = message + coolingAI.OutputString();
			break;
		case SPLITSYSTEM_OUTDOOR:
			message = message + coolingAI.OutputString();
			break;
		case SPLITSYSTEM_HEATPUMP:
		case COOLINGTOWER:
		case BOILER:
		case WATERHEATER:
		case FURNACE:
		case UNITHEATER:
		case WINDOWAC:
		case CW_PUMP:
	
		case UNDEFINED:
			message = "No equipment type";
		}
		Toast.makeText(null, message, Toast.LENGTH_SHORT).show();
	}*/

    public String getDisplayName() {
        return "Unit ID: " +this.getUnitID()+"/Location: "+this.getLocation();
    }
	
	public static int getNumRegularColumns() {
		return numColumns;
	}
}
