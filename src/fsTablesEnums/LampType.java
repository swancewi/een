package fsTablesEnums;

import android.util.Log;

public enum LampType {

	T84FT32W("4ft 32W T8"),
	T84FT28("4ft 28W T8"),
	T82FT17("2ft 17W T8"),
	T8UT("U-Tube T8"),
	T84FT25("4ft 25W T8"),
	T525W("4ft 25W T5"),
	T5HO54W("4ft 54W T5HO"),
	T1240W("4ft 40W T12"),
	T128FT80W("8ft 80W T12"),
	T12UT40W("U-Tube 40W T12"),
	T123FT30W("3ft 30W T12"),
	INC40("40W Incandescent"),
	INC60("60W Incandescent"),
	INC75("75W Incandescent"),
	INC100("100W Incandescent"),
	INC125("125W Incandescent"),
	INC150("150W Incandescent"),
	INC200("200W Incandescent"),
	INC300("300W Incandescent"),
	CFL7("7W CFL"),	
	CFL9("9W CFL"),
	CFL13("13W CFL"),
	CFL19("19W CFL"),
	CFL22("22W CFL"),	
	CFL26("26W CFL"),
	CFL32("32W CFL"),
	CFL34("34W CFL"),
	CFL36("36W CFL Biax"),
	CFL42("42W CFL"),
	CFL65("65W CFL"),
	HID("Unknown HID"),
	MV40("40W mercury vapor"),
	MV75("75W mercury vapor"),
	MV100("100W mercury vapor"),
	MV175("175W mercury vapor"),
	MV250("250W mercury vapor"),
	MV400("400W mercury vapor"),
	HPS50("50W HPS"),
	HPS70("70W HPS"),
	HPS100("100W HPS"),
	HPS150("150W HPS"),
	HPS75("75W HPS"),
	HPS250("250W HPS"),
	HPS400("400W HPS"),
	MH50("50W Metal Halide"),
	MH70("70W Metal Halide"),
	MH100("100W Metal Halide"),
	MH150("150W Metal Halide"),
	MH175("175W Metal Halide"),
	MH250("250W Metal Halide"),
	MH400("400W Metal Halide"),
	MH1000("1000W Metal Halide"),
	TUNG50("150W Tungsten"),
	HAL("Halogen"),
	LED("LED"),

	UNDEFINED("Unknown");
	
	private String lampName;
	
	LampType(String name) {
		this.lampName = name;
	}
	
	public String toString() {
		return this.lampName;
	}
	
	public static String[] toStringArray() {
		String[] makes = new String[LampType.values().length];
		int i = 0;
		
		for(LampType make:LampType.values()) {
			makes[i] = make.toString();
			i++;
		}
		
		return makes;
	}
	
	public static LampType getEnumType(String text) {
		
		for(LampType equipment:LampType.values()) {
			if(equipment.toString().equals(text))
				return equipment;
		}
		//Log.v("EquipmentType", text + "does not match");
		return LampType.UNDEFINED;
	}
	
}
