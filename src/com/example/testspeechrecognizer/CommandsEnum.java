package com.example.testspeechrecognizer;

public enum CommandsEnum {
	
	ADDNEW("Add New", true),
	PHOTO("Add Photo", false),
	AUDIO("Add Audio", false),
	MEASURE("Add Measure", true),
	UNITID("Add Unit ID", true),
	MAKE("Add Make", true),
	MODEL("Add Model", true),
	SERIAL("Add Serial", true),
	LOCATION("Add Location", true),
	INSTALLYEAR("Add Install Year", true),
	QUANTITY("Add Quantity", true),
	SERVES("Add Serves", true);
	
	String command;
	boolean requiresValue;
		
	CommandsEnum(String command, boolean requiresValue) {
		this.command = command;
		this.requiresValue = requiresValue;
	}
	
	public String getCommand() {
		return this.command;
	}
	
}
