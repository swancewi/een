package fsTablesClasses;

public class SiteMedia {

	private String mFileName;
	private String mBuildingName;
	private String mBuildingNumber;
	private String mEquipmentName;
	private String mLocation;
	private String mEquipmentType;
	private String mPhotoTag;
	private String mMeasureTag;
	private int mMeasureID;
	private String mNotes;
	
	public String csvExport() {
		String csvValues;
		String photoFileOnly; //meant to contain just photo file, not the android path, too
		photoFileOnly = this.mFileName;
		
		if (photoFileOnly.contains("/")); {
			photoFileOnly = photoFileOnly.substring(photoFileOnly.lastIndexOf("/")+1);
		}

		//TODO update with new values
		csvValues = "\"" + photoFileOnly +"\","; 
		csvValues += "\"" + this.mBuildingName+"\",";
		//csvValues += "\"" + this.mBuildingNumber+"\",";
		csvValues += "\"" + this.mEquipmentName+"\",";
		csvValues += "\"" + this.mLocation + "\",";
		csvValues += "\"" + this.mEquipmentType+"\",";
		csvValues += "\"" + this.mPhotoTag+"\",";
		csvValues += "\"" + this.mMeasureTag+"\",";
		csvValues += "\"" + this.mNotes+"\"\n";


		return csvValues;
	}

	public static String csvHeaders = "\"File Name\",\"Building\",\"Equipment Name\",\"Location\",\"Equipment Type\",\"Photo Tag\",\"Measure Tag\",\"Notes\"\n";
	
	public void setPhotoName(String photoName) {this.mFileName = photoName;}
	public void setBuildingName(String mBuildingName) {this.mBuildingName = mBuildingName;}
	public void setBuildingNumber(String mBuildingNumber) {this.mBuildingNumber = mBuildingNumber;}
	public void setEquipmentName(String mEquipmentName) {this.mEquipmentName = mEquipmentName;}
	public void setEquipmentLocation(String mLocation) {this.mLocation = mLocation;}
	public void setEquipmentType(String mEquipmentType) {this.mEquipmentType = mEquipmentType;}
	public void setNotes(String mNotes) {	this.mNotes = mNotes;}
	public void setPhotoTag(String mPhotoTag) {this.mPhotoTag = mPhotoTag;	}
	public void setMeasureTag(String measureName) {this.mMeasureTag = measureName;}
	public void setMeasureID(int id) {this.mMeasureID = id;}

	public String getFileName() {		return mFileName;	}
	public String getBuildingName() {		return mBuildingName;	}
	public String getBuildingNumber() {		return mBuildingNumber;	}
	public String getEquipmentName() {		return mEquipmentName;	}
	public String getLocation() {		return mLocation;	}
	public String getEquipmentType() {		return mEquipmentType;	}
	public String getNotes() {		return mNotes;	}
	public String getPhotoTag() {return mPhotoTag;	}
	public String getMeasureTag() {	return mMeasureTag;	}
	public int getMeasureID() {return mMeasureID;}
}
