package fsTablesClasses;

import fsTablesEnums.EquipmentType;

/**
 * Created by Steven Wance on 5/14/2015.
 */
public class EnvelopeInformation {

    private String location;
    private double width;

    private double rValue;
    private String frameType;
    private String color;
    private String notes;
    private EquipmentType equipmentType;
    private String insulationType;
    private String doorMaterial;
    private String glassType;
    private double height;
    private String doorType;
    private int quantity;
    private String windowOperatingType;

    public String getWindowOperatingType() {
        return windowOperatingType;
    }

    public void setWindowOperatingType(String windowOperatingType) {
        this.windowOperatingType = windowOperatingType;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getInsulationType() {
        return insulationType;
    }

    public void setInsulationType(String insulationType) {
        this.insulationType = insulationType;
    }

    public String getDoorMaterial() {
        return doorMaterial;
    }

    public void setDoorMaterial(String doorMaterial) {
        this.doorMaterial = doorMaterial;
    }

    public String getGlassType() {
        return glassType;
    }

    public void setGlassType(String glassType) {
        this.glassType = glassType;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getDoorType() {
        return doorType;
    }

    public void setDoorType(String doorType) {
        this.doorType = doorType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /*public double getSquareFoot() {
        return height*width;
    }*/

    public double getRValue() {
        return rValue;
    }

    public void setRValue(double rValue) {
        this.rValue = rValue;
    }

    public String getFrameType() {
        return this.frameType;
    }

    public void setFrameType(String windowType) {
        this.frameType = windowType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDisplayName() {
        return "Location: " + this.location;
    }

    public String csvExport(String buildingName) {
        String csvExport = "\"" + buildingName + "\"";

        csvExport += ",\"" +this.location + "\"";
        csvExport += ",\"" + this.height + "\"";
        csvExport += ",\"" + this.width + "\"";
        csvExport += ",\"" + this.rValue + "\"";
        csvExport += ",\"" + this.frameType + "\"";
        csvExport += ",\"" + this.color + "\"";
        csvExport += ",\"" + this.notes + "\"";
        csvExport += ",\"" + this.insulationType + "\"";
        csvExport += ",\"" + this.doorMaterial + "\"";
        csvExport += ",\"" + this.glassType + "\"";
        csvExport += ",\"" + this.height + "\"";
        csvExport += ",\"" + this.doorType + "\"";
        csvExport += ",\"" + this.quantity + "\"";
        csvExport += ",\"" + this.windowOperatingType + "\"";
        csvExport += "\n";

        return csvExport;
    }

    public static String csvExportHeaders() {
        String csvHeaders = "\"Building\",\"Location\",\"Height\",\"Width\",\"R value\",\"Frame Type\",\"color\",\"notes\",\"Insulation Type\",\"Door Material\",\"Glass Type\",\"Height\",\"Door Type\",\"Quantity\",\"Window Operating Type\"\n";
        return csvHeaders;
    }
}
