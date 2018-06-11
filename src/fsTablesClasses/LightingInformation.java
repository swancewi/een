package fsTablesClasses;

import fsTablesEnums.EquipmentType;
import fsTablesEnums.LampType;

/**This class keeps track of lighting in a location
 * 
 * @author Steven Wance
 *
 */

@SuppressWarnings("unused")
public class LightingInformation {

	private String location;
	private int quantity;
	private LampType lampType;
	private int lampQuantity;
	private String control;
	private double annualHours;
	private double totalKW;
	private boolean isInterior;
	private String notes;

	public static String headers = "\"Building\",\"Location\",\"Fixture Quantity\",\"Fixture description\",\"Control\",\"Annual hours\",\"Interior or Exterior lighting\",\"Notes\"\n";
	
	public String csvExport(String building) {
		String csvValues;
		csvValues = "\"" + building +"\",";
		csvValues += "\"" + this.location+"\",";
		csvValues += "\"" + this.quantity+"\",";
		csvValues += "\"" + this.lampQuantity+"L " + this.lampType.toString() +"\",";
		csvValues += "\"" + this.control+"\",";
		csvValues += "\"" + this.annualHours +"\",";
		csvValues +=  "\"" + this.getIsInteriorEquipmentType().toString() +"\",";
		csvValues += "\"" + this.notes + "\""+ "\n";
		
		return csvValues;
	}
	
	public void setLocation(String location) {this.location = location;}
	public void setQuantity(int quantity) {this.quantity = quantity;}
	public void setLampType(LampType lampType) {this.lampType = lampType;}
	public void setLampQuantity(int lampQuantity) {this.lampQuantity = lampQuantity;}
	public void setControl(String control) {this.control = control;}
	public void setAnnualHours(double annualHours) {this.annualHours = annualHours;}
	public void setTotalKW(double totalKW) {this.totalKW = totalKW;}
	public void setIsInterior(boolean isInterior) {this.isInterior = isInterior; }
	public void setIsInteriorEquipment(EquipmentType interior) {
		this.isInterior = interior.equals(EquipmentType.INTERIORLIGHTING);
		//Log.v("Lighting Information", interior.toString() + " " + this.isInterior);
	}
	public void setIsInteriorInt(int number) {this.isInterior = number > 0 ? true : false; } 
	public void setNotes(String notes) {this.notes = notes;}

	public String getLocation() {return this.location;}
	public int getQuantity() {return this.quantity;}
	public LampType getLampType() {return this.lampType;}
	public int getLampQuantity() {return this.lampQuantity;}
	public String getControl() {return this.control;}
	public double getAnnualHours() {return this.annualHours;}
	public double getTotalKW() {return this.totalKW;}	
	public boolean getIsInteriorBoolean() {return this.isInterior;}
	public EquipmentType getIsInteriorEquipmentType() {return this.isInterior ? EquipmentType.INTERIORLIGHTING : EquipmentType.EXTERIORLIGHTING; }
	public String getNotes() {return this.notes;}

    public String getDisplayName() {
        return "Location: " + this.getLocation() +"/LampType: " + this.getLampType().toString();
    }
	
}
