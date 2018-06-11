package fsTablesClasses;

import android.util.Log;

/**
 * This class keeps track of building information
 * 
 * @author Steven Wancewicz
 * @version 2014-1-3
 * 
 */
public class Building {

	private int id;
	private String name;
	private String number;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String function;
	private double squareFoot;
	private int numFloors;
	private String schedule;
	private String hvacSchedule;
	private String yearBuilt;
	private String numPCs;
	private String roofType;
	private String windowType;
	private String wallType;
	private String foundationType;
	
	public Building() {}
	
	public Building(String name, String address, String city, String state, String zip, String function, double squareFoot, String schedule) {
		
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.function = function;
		this.squareFoot = squareFoot;
		this.schedule = schedule;
	}

	public static String CSV_HEADERS = "\"Building Name\",\"Building Description\",\"Address\""+
	",\"City\",\"State\",\"Zip\",\"Function\",\"Sq ft\",\"# of Floors\"" +
	",\"Occ. Schedule\",\"HVAC Schedule\",\"Year Built\",\"# of PCs\",\"Roof\",\"Window Type\",\"Wall Type\",\"Foundation Type\"\n";

	public String csvExport() {
		String csvValues;
		csvValues = "\"" + this.name+"\",";
		csvValues += "\"" + this.number+"\",";
		csvValues += "\"" + this.address+"\",";
		csvValues += "\"" + this.city +"\",";
		csvValues += "\"" + this.state +"\",";
		csvValues += "\"" + this.zip +"\",";
		csvValues += "\"" + this.function+"\",";
		csvValues += "\"" + this.squareFoot +"\",";
		csvValues +=  "\"" + this.numFloors +"\",";
		csvValues += "\"" + this.schedule + "\",";
		csvValues += "\"" + this.hvacSchedule+"\",";
		csvValues += "\"" + this.yearBuilt+"\",";
		csvValues += "\"" + this.numPCs+"\",";
		csvValues += "\"" + this.roofType+"\",";
		csvValues += "\"" + this.windowType+"\",";
		csvValues += "\"" + this.wallType+"\",";
		csvValues += "\"" + this.foundationType+"\",";
		csvValues += "\n";
		
		return csvValues;
	}
	//getters
	public String getBuildingName() { return this.name;}
	public String getBuildingNumber() {return this.number; }
	public String getBuildingAddress() { return this.address;}
	public String getBuildingCity() { return this.city;}
	public String getBuildingState() { return this.state;}
	public String getBuildingZip() { return this.zip;}
	public String getBuildingFunction() { return this.function;}
	public double getBuildingSquareFoot() { return this.squareFoot;}	
	public String getBuildingSchedule() { return this.schedule;}
	public int getBuildingFloors() {//Log.v(this.toString(), "get # Floors:" + this.numFloors); 
		return this.numFloors;}
	public int getBuildingID() {return this.id;}
	public String getHvacSchedule() {return this.hvacSchedule;}
	public String getYearBuilt() {return this.yearBuilt;}
	public String getNumPCs() {return this.numPCs;}
	public String getRoofType() {return this.roofType;}
	public String getWindowType() {return this.windowType;}
	public String getWallType() {return this.wallType;}
	public String getFoundationType() {return this.foundationType;}

	
	/**Returns a string of the building Name that can be used as part of a file name
	 * 
	 * @return
	 */
	public String getBuildingFileName() {
		String fileName;
		fileName = this.name;
		fileName = fileName.replace(" ","");
		fileName = fileName.replace("/", "-");
		
		return fileName;
	}
	
	//setters
	public void setBuildingName(String name) {this.name = name;}
	public void setBuildingNumber(String number) {this.number = number;}
	public void setBuildingAddress(String address) {this.address = address;}
	public void setBuildingCity(String city) {this.city = city;}
	public void setBuildingState(String state) {this.state = state;}
	public void setBuildingZip(String zip) {this.zip = zip;}
	public void setBuildingFunction(String function) {this.function = function;}
	public void setBuildingSquareFoot(double squareFoot) {this.squareFoot = squareFoot;}
	public void setBuildingSchedule(String schedule) {this.schedule = schedule;}
	public void setBuildingID(int id) {this.id = id;}
	public void setBuildingFloors(int floors) {this.numFloors = floors; }
	public void setHvacSchedule(String hvacSchedule) {this.hvacSchedule = hvacSchedule;}
	public void setYearBuilt(String yearBuilt) {this.yearBuilt = yearBuilt;}
	public void setNumPCs(String numPCs) {this.numPCs = numPCs;}
	public void setRoofType(String roofType) {this.roofType = roofType;}
	public void setWindowType(String windowType) {this.windowType = windowType;}
	public void setWallType(String wallType) {this.wallType = wallType;}
	public void setFoundationType(String foundationType) {this.foundationType = foundationType;}
	
	
}
