package fsTablesClasses;

/**
 * Created by Steven Wance on 5/3/2015.
 */
public class WaterInformation {

    private String sinkFlowRate;
    private String sink2FlowRate;
    private String urinalFlowRate;
    private String toiletFlowRate;
    private String showerFlowRate;
    private int sinkQuantity;
    private int sink2Quantity;
    private int urinalQuantity;
    private int toiletQuantity;
    private int showerQuantity;
    private String location;
    private String notes;

    public String getNotes() {return notes;    }
    public String getSinkFlowRate() {        return sinkFlowRate;    }
    public String getUrinalFlowRate() {        return urinalFlowRate;    }
    public String getToiletFlowRate() {        return toiletFlowRate;    }
    public int getSinkQuantity() {        return sinkQuantity;    }
    public int getUrinalQuantity() {        return urinalQuantity;    }
    public int getSink2Quantity() {return sink2Quantity;}
    public int getShowerQuantity() {return showerQuantity;}
    public String getSink2FlowRate() {return sink2FlowRate;}
    public String getShowerFlowRate() {return showerFlowRate;}
    public int getToiletQuantity() {        return toiletQuantity;    }
    public String getLocation() {        return location;    }

    public void setNotes(String notes) {       this.notes = notes;    }
    public void setSinkFlowRate(String sinkFlowRate) {       this.sinkFlowRate = sinkFlowRate;    }
    public void setSink2FlowRate(String sink2FlowRate) {this.sink2FlowRate = sink2FlowRate;}
    public void setShowerFlowRate(String showerFlowRate) {this.showerFlowRate = showerFlowRate;}
    public void setSink2Quantity(int quantity) {this.sink2Quantity = quantity;}
    public void setShowerQuantity(int quantity) {this.showerQuantity = quantity;}
    public void setUrinalFlowRate(String urinalFlowRate) {        this.urinalFlowRate = urinalFlowRate;}
    public void setToiletFlowRate(String toiletFlowRate) {        this.toiletFlowRate = toiletFlowRate;}
    public void setSinkQuantity(int sinkQuantity) {       this.sinkQuantity = sinkQuantity;    }
    public void setUrinalQuantity(int urinalQuantity) {        this.urinalQuantity = urinalQuantity;    }
    public void setToiletQuantity(int toiletQuantity) {        this.toiletQuantity = toiletQuantity;    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getDisplayName() {
        return "Location: " + this.getLocation() + " S: " + this.getSinkQuantity() + " T: " + this.toiletQuantity + " U: " + this.getUrinalQuantity();
    }

    public String csvExport(String buildingName) {
        String csvExport = "\"" + buildingName + "\"";

        csvExport += ",\"" + this.location + "\"";
        csvExport += ",\"" + this.sinkQuantity + "\"";
        csvExport += ",\"" + this.sinkFlowRate + "\"";
        csvExport += ",\"" + this.sink2Quantity + "\"";
        csvExport += ",\"" + this.sink2FlowRate + "\"";
        csvExport += ",\"" + this.urinalQuantity + "\"";
        csvExport += ",\"" + this.urinalFlowRate + "\"";
        csvExport += ",\"" + this.toiletQuantity + "\"";
        csvExport += ",\"" + this.toiletFlowRate + "\"";
        csvExport += ",\"" + this.showerQuantity + "\"";
        csvExport += ",\"" + this.showerFlowRate + "\"";
        csvExport += ",\"" + this.notes + "\"";
        csvExport += "\n";
        return csvExport;
    }

    public static String csvExportHeaders() {
        String csvHeaders = "\"Building\",\"Location\",\"Sink Qty\",\"Sink Flow Rate\",\"Sink2 Qty\",\"Sink2 Flow Rate\",\"Urinal Qty\",\"Urinal Flow Rate\",\"Toilet Qty\",\"Toilet Flow Rate\",\"Shower Qty\",\"Shower Flow Rate\",\"Notes\"\n";
        return csvHeaders;
    }

}
