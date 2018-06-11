package com.example.testspeechrecognizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import constants.GeneralFunctions;
import display.Audit.MainActivity;
import fsTablesClasses.EquipmentInformation;
import fsTablesClasses.LightingInformation;
import fsTablesEnums.EquipmentType;
import media.photos.PhotoService;


public class Translator {
	
	private static Activity activity;
	private final static String TAG = "Translator";
	private static SpeechRecognizer sr;


	public void setActivity(Activity activity) {
		Translator.activity = activity;
	}
	
	
	/**This method takes the processed text and guides it to process the action 
	 *
	 */
	public static void ListenThenRespond() {
		
        sr = SpeechRecognizer.createSpeechRecognizer(activity);       
        sr.setRecognitionListener(new Listener());        

		
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);        
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
        intent.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES, true);

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5); 
        sr.startListening(intent);

	}
	
	public static void Respond(ArrayList<String> heardPhrase) {
		CommandsEnum whatToDo;

		try {
			whatToDo = translateCommand(heardPhrase.get(0));
			//Log.v(TAG, "command translated");
			processAction(heardPhrase.get(0), whatToDo);
			//Log.v(TAG, "action translated");
		} catch (IllegalArgumentException iae) {
			
		}
	}
	
	private static EquipmentType translateEquipmentType(String heardText) throws IllegalArgumentException {
		EquipmentType eType = null;
		
		for(AddableTranslateEnum t:AddableTranslateEnum.values()) {
			if(t.getHeardWord().contains(heardText.toUpperCase(Locale.US))) {
				eType = t.getRealWord();
				break;
			}
		}
		
		if(eType == null)
			throw new IllegalArgumentException("No match for audio found");
		
		return eType;
	}
	
	private static CommandsEnum translateCommand(String heardText) throws IllegalArgumentException {
		CommandsEnum returnText = null;
		
		for(CommandsTranslateEnum t:CommandsTranslateEnum.values()) {
			if(t.getHeardWord().contains(heardText.toUpperCase(Locale.US))) {
				returnText = t.getCommandEnum();
				break;
			}
		}
		
		if(returnText == null)
			throw new IllegalArgumentException("No match for audio found");
		
		return returnText;

	}
	
	/**This function figures out what to do with the command
	 * 
	 */
	private static void processAction(String value, CommandsEnum whatToDo) {
		switch(whatToDo) {
		case ADDNEW:
			addNew(value);
			break;
		case PHOTO:
			takePhoto(value);
			break;
		case AUDIO:
			recordAudio(value);
			break;
		case UNITID:
		case MAKE:
		case MODEL:
		case SERIAL:
		case LOCATION:
		case INSTALLYEAR:
		case QUANTITY:
		case SERVES:
			updateEquipmentInformation(value, whatToDo);
			break;
		case MEASURE:
			addMeasure(value);
			break;
		default:
			break;
		}
	}
	
	/**For adding a new building or building item
	 * @throws IOException 
	 * 
	 */
	private static void addNew(String value) {
		
		final String METHOD_NAME = "addNew";
		
		//Look for addable object
		try {
			EquipmentType eType = translateEquipmentType(value);
            switch(eType.getBuildingItemType()) {
                case WATER_FIXTURE:
                case LIGHT:
                    LightingInformation lInfo = new LightingInformation();
                    lInfo.setIsInteriorEquipment(eType);
                    MainActivity.db.AddLightingEquipment(lInfo);
                case ENVELOPE:
                case EQUIPMENT:
                    EquipmentInformation eInfo = new EquipmentInformation();
                    eInfo.setEquipmentType(eType);
                    MainActivity.db.addEquipment(eInfo);
                    break;
            }

		} catch (IllegalArgumentException iLE) {
			//It's a building, so add new building
			//TODO change active building
		} catch (IOException iOE) {
			//Log.v(TAG + METHOD_NAME, "Error adding light");
		}
		
		//Add new object
	}
	
	/**Starts the photo intent
	 * 
	 */
	private static void takePhoto(String value) {

		//Open Camera
		GeneralFunctions.DisplayToast(activity, CommandsEnum.PHOTO.getCommand());
		PhotoService.startCamera(activity);
		//PhotoService.checkPhotos(activity);
		
	}
	
	/**starts the record audio intent
	 * 
	 */
	private static void recordAudio(String value) {
		//TODO
		//Open Audio Recorder
	}
	
	/**adds information specific to an equipment
	 * 
	 */
	private static void updateEquipmentInformation(String value, CommandsEnum whatToAdd) {
		//TODO need to process value
		//Figure out which equipmentInformation object
		//Figure out value to add

		EquipmentInformation eInfo = MainActivity.db.getActiveEquipment();
		switch(whatToAdd) {
		case UNITID:
			eInfo.setUnitID(value);
			break;
		case MAKE:
			eInfo.setManufactuer(value);
			break;
		case MODEL:
			eInfo.setModel(value);
			break;
		case SERIAL:
			eInfo.setSerial(value);
			break;
		case LOCATION:
			eInfo.setLocation(value);
			break;
		case INSTALLYEAR:
			eInfo.setInstallYear(value);
			break;
		case QUANTITY:
			eInfo.setQuantity(Integer.parseInt(value));
			break;
		case SERVES:
			eInfo.setServes(value);
			break;
		default:
			break;
		}
		
		MainActivity.db.updateEquipment(eInfo);
		
	}
	
	/**adds lighting specific information
	 * 
	 */
	private static void addLightingInformation(String value) {
		//TODO
	}
	
	private static void addMeasure(String value) {
		//TODO
	}
	
}

class Listener implements RecognitionListener          
{
	private static String TAG = "listener";
	private static ArrayList<String> mumblings;
	
         public void onReadyForSpeech(Bundle params)
         {
                  Log.d(TAG, "onReadyForSpeech");
         }
         public void onBeginningOfSpeech()
         {
                  Log.d(TAG, "onBeginningOfSpeech");
         }
         public void onRmsChanged(float rmsdB)
         {
                  Log.d(TAG, "onRmsChanged");
         }
         public void onBufferReceived(byte[] buffer)
         {
                  Log.d(TAG, "onBufferReceived");
         }
         public void onEndOfSpeech()
         {
                  Log.d(TAG, "onEndofSpeech");
         }
         public void onError(int error)
         {
                  Log.d(TAG,  "error " +  error);
         }
         public void onResults(Bundle results)                   
         {
                  String str = new String();
                  Log.d(TAG, "onResults " + results);
                  this.mumblings = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                  for (int i = 0; i < this.mumblings.size(); i++)
                  {
                            Log.d(TAG, "result " + this.mumblings.get(i));
                            str += this.mumblings.get(i);
                  }
                  Translator.Respond(this.mumblings);
         }
         public void onPartialResults(Bundle partialResults)
         {
                  Log.d(TAG, "onPartialResults");
         }
         public void onEvent(int eventType, Bundle params)
         {
                  Log.d(TAG, "onEvent " + eventType);
         }
         public ArrayList<String> getResults() {
        	 return this.mumblings;
         }
}