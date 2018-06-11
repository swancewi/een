package fsTablesClasses;

import fsTablesEnums.EquipmentType;

public class Measure {
	
	private String description;
	private EquipmentType eType;
	private String equipmentName;
	private int equipmentID;
	private String notes;
	
	public void setDescription(String description) {this.description = description;}
	public void setEquipmentType(EquipmentType eType) {this.eType = eType;}
	public void setEquipmentName(String eName) {this.equipmentName = eName;}
	public void setEquipmentID(int id) {this.equipmentID = id;}
	public void setNotes(String notes) {this.notes = notes; }
	
	public String getDescription() {return this.description;}
	public EquipmentType getEquipmentType() {return this.eType;}
	public String getEquipmentName() {return this.equipmentName;}
	public int getEquipmentID() {return this.equipmentID;}
	public String getNotes() {return this.notes;}

	public static String CSV_HEADERS = "\"Database Building Asset ID\",\"Description\",\"Equipment Type\",\"Asset Description\",\"Notes\"\n";

	public String csvExport(String buildingName) {
		String csvValues;
		csvValues = "\"" + this.equipmentID + "\",";
		csvValues += "\"" + buildingName + "\",";
		csvValues += "\"" + this.description +"\",";
		csvValues += "\"" + this.eType +"\",";
		csvValues += "\"" + this.equipmentName + "\",";
		csvValues += "\"" + this.notes + "\""+ "\n";
		
		return csvValues;
	}
}
