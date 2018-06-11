package fsTablesEnums;

import android.util.Log;

public enum FragmentOrder {
	ADD_BUILDING("Add Building", 0),
	EDIT_BUILDING("Edit Building", 1),
	ADD_EQUIPMENT("Add Bldg Asset", 2),
	EDIT_EQUIPMENT("Edit Bldg Asset", 3),
	SHOW_EQUIPMENT("Show Bldg Asset", 4),
	ADD_PHOTOS("Add Photo(s)", 5),
	DISPLAY_PHOTOS("Show Photo(s)", 6),
	ADD_AUDIO("Record Audio", 7),
	ADD_MEASURE_TAG("Add Measure Tag", 8),
	ADD_CUSTOM_MEASURE("Add Custom Measure", 9),
    SHOW_MEASURES("Show Measures", 10),
	FACILITY("Add/Edit Facility", 11);
	
	
	private int order;
	private String name;
	
	FragmentOrder(String name, int order) {
		this.name = name;
		this.order = order;
	}
	
	public int getOrderNumber() {
		return this.order;
	}
	
	public String toString() {
		return this.name;
	}
	
	public static String[] getStringArray() {
		String[] array = new String[FragmentOrder.count()];
		int i = 0;
		
		for(FragmentOrder f:FragmentOrder.values()) {
			array[i] = f.toString();
			//Log.v("FragOrder", i + f.toString());
			i++;
		}
		return array;
		
	}
	
	private static int count() {
		
		int i = 0;
		
		for(FragmentOrder f:FragmentOrder.values()) {
			i++;
		}
		
		return i;
	}
}
