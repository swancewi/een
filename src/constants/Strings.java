package constants;

/**
 * This class keeps track of most strings used w/in the app
 * 
 * @author Steven Wancewicz
 * @version 2013-9-6
 * 
 */
public class Strings {

	public final static String[] controlOptions = {"Unknown", "Programmable thermostat", "DDC thermostat", "EMS", "Manual switch", "Manual thermostat", "Motion Sensor", "Network thermostat", "None", "Pneumatic thermostat", "Timeclock", "Unit thermostat"};
	public final static String[] buildingTypes = {"Adult Education", "Ambulatory Surgical Center", "Aquarium", "Automobile Dealership", "Banking/Financial Services Bank Branch*",
			"Bar/Nightclub", "Barracks ", "Bowling Alley", "Casino", "College/University", "Convenience Store",
			"Convention Center", "Courthouse*", "Data Center*", "Detailed Property Type",
			"Distribution Center*", "Drinking Water Treatment & Distribution", "Enclosed Mall", "Energy/Power Station",
			"Fast Food Restaurant", "Financial Office*", "Fire Station", "Fitness Center/Health Club/Gym",
			"Food Sales", "Food Service", "Healthcare", "Hospital", "Hotel*", "Ice/Curling Rink", "Indoor Arena", "K-12 School*",
			"Laboratory", "Library", "Lifestyle Center", "Mailing Center/Post Office", "Mall", "Manufacturing/Industrial Plant",
			"Medical Office*", "Mixed Use Property", "Movie Theater", "Multifamily Housing", "Museum", "Non-Refrigerated Warehouse*",
			"Office*", "Outpatient Rehabilitation/Physical Therapy", "Parking", "Performing Arts",
			"Personal Services (Health/Beauty, Dry Cleaning, etc) ", "Police Station", "Pre-school/Daycare", "Prison/Incarceration",
			"Race Track", "Refrigerated Warehouse*", "Repair Services (Vehicle, Shoe, Locksmith, etc)", "Residence Hall/Dormitory*",
			"Restaurant", "Restaurant/Bar", "Retail Store*", "Roller Rink", "Self-Storage Facility", "Senior Care Community*",
			"Single Family Home", "Stadium (Closed) ", "Stadium (Open) ", "Strip Mall", "Supermarket/Grocery Store*", "Swimming Pool",
			"Transportation Terminal/Station", "Urgent Care/Clinic/Other Outpatient", "Veterinary Office", "Vocational School",
			"Warehouse/Distribution Center", "Wastewater Treatment Plant*", "Wholesale Club/Supercenter*", "Worship Facility*", "Zoo"};
	public final static String NA = "N/A";
	public final static String[] motorType = {"Unknown", "Electronically commutated", "Permanent split capacitor", "Shaded pole"};
	public final static String[] dhwType = {"Unknown", "Tank", "Tankless", "Point of use, tank-type", "Point of use, tankless"};
	public final static String[] boilerType = {"Unknown", "Condensing", "Non-condensing", "Fire tube", "Water tube"};
	public final static String[] boilerMedium = {"Unknown", "Steam", "Water", "Low pressure steam", "Medium pressure steam", "High pressure steam"};
	public final static String[] chillerType = {"Unknown", "Centrifugal", "Scroll", "Screw", "Reciprocating", "Absorption"};
	public final static String[] chillerCondenserType = {"Unknown", "Air cooled", "Water cooled", "Evaporative"};
	public final static String[] chillerEvaporatorType = {"Unknown", "Direct expansion unit coils", "Flooded", "Direct expansion", "Shell and tube", "Plate and frame"};
	public final static String[] ahuEconomizerStatus = {"Unknown", "Working", "None", "Disabled", "Broken", "Fixed minimum", "Fixed closed", "Fixed maximum"};
	public final static String[] ahuEconomizerDamperControl = {"None", "Pneumatic", "Hybrid", "Electronic", "Disconnected", "Broken", "EMS", "Local controller"};
	public final static String[] COOLING_AHU_COIL = {"Unknown", "None", "CHW Electronic 3-way valve", "CHW Electronic 2-way valve", "CHW EMS 3-way valve", "CHW EMS 2-way valve", "CHW Hybrid 3-way valve", "CHW Hybrid 2-way valve",
            "CHW Local controller 3-way valve", "CHW Local controller 2-way valve", "CHW Pneumatic 3-way valve", "CHW Pneumatic 2-way valve", "DX Electronic 3-way valve", "DX Electronic 2-way valve", "DX EMS 3-way valve",
            "DX EMS 2-way valve", "DX Hybrid 3-way valve", "DX Hybrid 2-way valve", "DX Local controller 3-way valve", "DX Local controller 2-way valve", "DX Pneumatic 3-way valve", "DX Pneumatic 2-way valve"};
    public final static String[] HEATING_AHU_COIL = {"Unknown", "None", "Electronic 3-way valve", "Electronic 2-way valve", "EMS 3-way valve", "EMS 2-way valve", "Hybrid 3-way valve", "Hybrid 2-way valve",
            "Local controller 3-way valve", "Local controller 2-way valve", "Pneumatic 3-way valve", "Pneumatic 2-way valve"};
	public final static String NO_EQUIPMENT = "No equipment added for this building";
	public final static String NO_BUILDING = "No building added";
	public final static String NEW_EQUIPMENT = "--new--";
	public final static String[] lampControlType = {"Unknown", "Manual switch", "Timer", "Photocell", "PIR Occupancy Sensor", "Ultrasonic Occupancy Sensor", "Dual Technology Occupancy Sensor", "Passive DT Occupancy Sensor"};
	public final static String ERROR_PROCESSING_START = "Something went wrong when processing ";
	public static final String DEFAULT_FACILITY = "Facility1";
	public static final String FAILED_TO_SPECIFY = "n/a";
	public static final String[] PHOTO_TAG_TYPES = {"None", "Component", "Control Screenshot", "Issue", "Namemplate", "System"};
	public static final String GENERAL_TYPE = "General";
	public static final String BUILDING_ASSET_ERROR_MESSAGE = "Error adding building asset";
	public static final String[] WINDOW_GLASS_TYPES = {"Unknown", "Single Pane","Double Pane","Triple Pane"};
	public static final String[] WINDOW_FRAME_TYPES = {"Unknown", "Fiberglass","Vinyl","Aluminum","Wood"};
	public static final String[] DOOR_MATERIAL_TYPES = {"Unknown", "Wood", "Metal", "Wood and Glass", "Vinyl", "Metal and Glass"};
	public static final String[] WINDOW_TYPES = {"Unknown","Single hung", "Double hung", "Sliding", "Vertical pull-in", "Vertical push-out", "Horizontal pull-in", "horizontal push-out"};
	public static final String[] INSULATION_TYPES = {"Unknown", "Blanket: batts and rolls", "Concrete block insulation", "Insulating concrete forms","Foam board", "Loose fill / Blown-in", "Reflective system", "Sprayed foam", "Structural insulated panels"};
}