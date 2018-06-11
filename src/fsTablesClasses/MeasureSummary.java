package fsTablesClasses;

/**
 * Created by Steven Wance on 4/28/2015.
 */
public class MeasureSummary {
    private String measureName;
    private int numEquipment;
    private int measureNumber;
    private String measureEquipment;
    private String measureBuildings;


    public MeasureSummary(String measureName, int numEquipment, int measureNumber, String measureEquipment, String measureBuildings) {
        this.measureName = measureName;
        this.numEquipment = numEquipment;
        this.measureNumber = measureNumber;
        this.measureEquipment = measureEquipment;
        this.measureBuildings = measureBuildings;
    }



    public MeasureSummary() {
    }

    public String getMeasureBuildings() {
        return measureBuildings;
    }

    public String getMeasureEquipment() {
        return measureEquipment;
    }

    public void setMeasureEquipment(String measureEquipment) {
        this.measureEquipment = measureEquipment;
    }
    public void setMeasureBuildings(String measureBuildings) {
        this.measureBuildings = measureBuildings;
    }
    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public int getNumEquipment() {
        return numEquipment;
    }

    public void setNumEquipment(int numEquipment) {
        this.numEquipment = numEquipment;
    }

    public int getMeasureNumber() {
        return measureNumber;
    }

    public void setMeasureNumber(int measureNumber) {
        this.measureNumber = measureNumber;
    }
}
