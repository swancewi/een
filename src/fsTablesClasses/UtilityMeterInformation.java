package fsTablesClasses;

import fsTablesEnums.FuelType;

/**
        * Created by Steven on 9/15/2015.
        * Modified by Patrick on 11/30/2015.
        */
public class UtilityMeterInformation {

    private String meterSerial;
    private String location;
    private double meterReading;
    private String meterUtilityProvider;
    private String meterUtilityType;
    private String meterUtilityUnits;
    private String meterNotes;

    public static String headers = "\"Building\",\"Location\",\"Meter Serial #\",\"Utility Provider\", \"Utility Type\", \"Units\",\"Notes\"\n";

    public String csvExport(String building) {
        String csvValues;
        csvValues = "\"" + building +"\",";
        csvValues += "\"" + this.location+"\",";
        csvValues += "\"" + this.meterSerial+"\",";
        csvValues += "\"" + this.meterUtilityProvider +"\",";
        csvValues += "\"" + this.meterUtilityType+"\",";
        csvValues += "\"" + this.meterReading+"\",";
        csvValues += "\"" + this.meterUtilityUnits +"\",";
        csvValues += "\"" + this.meterNotes + "\""+ "\n";

        return csvValues;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeterSerial() {
        return meterSerial;
    }

    public void setMeterSerial(String meterSerial) {
        this.meterSerial = meterSerial;
    }

    public double getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(double meterReading) {
        this.meterReading = meterReading;
    }

    public String getMeterUtilityProvider() {
        return meterUtilityProvider;
    }

    public void setMeterUtilityProvider(String meterUtilityProvider) {
        this.meterUtilityProvider = meterUtilityProvider;
    }

    public String getMeterUtilityType() {
        return meterUtilityType;
    }

    public void setMeterUtilityType(String meterUtilityType) {
        this.meterUtilityType = meterUtilityType;
    }

    public String getMeterUtilityUnits() {
        return meterUtilityUnits;
    }

    public void setMeterUtilityUnits(String meterUtilityUnits) {
        this.meterUtilityUnits = meterUtilityUnits;
    }

    public String getMeterNotes() {
        return meterNotes;
    }

    public void setMeterNotes(String meterNotes) {
        this.meterNotes = meterNotes;
    }

    public String getDisplayName() {
        return "Utility: " + this.getMeterUtilityType() + " / Meter No: " + this.getMeterSerial();
    }
}
