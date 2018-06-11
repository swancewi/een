package constants;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class GeneralFunctions {

	public static int StringToInt(String theInt, String errorMessage) throws IllegalArgumentException {
		int newInt = 0;
		
		try {
			if (theInt.length()>0)
				newInt = Integer.parseInt(theInt);
		} catch (Exception e) {
			DisplayToast(null, errorMessage);
		}
		
		return newInt;
	}
	
	public static double StringToDouble(String theDouble, String errorMessage) {
		double newInt = 0;
		try {
		if (theDouble.length()>0)
            //Check for multiplication, division, addition, subtraction symbols
            if(theDouble.contains("/") || theDouble.contains("*")) {
                newInt = interpretSymbols(theDouble);
            } else {
                newInt = Double.parseDouble(theDouble);
            }
		} catch (Exception e) {
			DisplayToast(null, errorMessage);
		}
		
		return newInt;
	}
	
	public static void DisplayToast(Context context, String unitID) {
		Toast.makeText(context, unitID, Toast.LENGTH_SHORT).show();
	}

    /**Function will look and apply multiplication and division
     *
     * @return
     */
    private static double interpretSymbols(String theDouble) {

        //figure out how many are included than loop through calculation
        int startCharacter = 0;
        int breakCharacter = 0;
        double theAnswer = 1;
        double theNumber = 0;
        String placeholder;
        boolean divisionMode = false;
        boolean nextCalcDivision = false;
        int divideSymbolIndex = theDouble.indexOf("/");
        int multiplySymbolIndex = theDouble.indexOf("*");

        //break up the characters into operations
        while(divideSymbolIndex > 0 || multiplySymbolIndex > 0) {
            if (divideSymbolIndex > 0) {
                if (multiplySymbolIndex > 0) {
                    //figure out which one to break up first
                    if(divideSymbolIndex > multiplySymbolIndex) {
                        divisionMode = false;
                        breakCharacter = multiplySymbolIndex;
                    }
                    else {
                        divisionMode = true;
                        breakCharacter = divideSymbolIndex;
                    }
                } else {
                    breakCharacter = divideSymbolIndex;
                    divisionMode = true;
                }
            } else if (multiplySymbolIndex > 0) {
                breakCharacter = multiplySymbolIndex;
                divisionMode = false;
            }

            //get next number
            placeholder = theDouble.substring(startCharacter,breakCharacter);
            theNumber = Double.parseDouble(placeholder);
            if(nextCalcDivision)
                theAnswer = theAnswer / theNumber;
            else
                theAnswer = theAnswer * theNumber;

            if(divisionMode)
                divideSymbolIndex = theDouble.indexOf("/", breakCharacter+1);
            else
                multiplySymbolIndex = theDouble.indexOf("*", breakCharacter+1);

            //get new division / multiply symbols
            nextCalcDivision = divisionMode;
            startCharacter = breakCharacter + 1;

        }

        placeholder = theDouble.substring(startCharacter);
        theNumber = Double.parseDouble(placeholder);
        if(nextCalcDivision)
            theAnswer = theAnswer / theNumber;
        else
            theAnswer = theAnswer * theNumber;

        return theAnswer;
    }

    public static String[] arrayListToStringArray(ArrayList<String> arrayList) {

        Object[] ObjectList = arrayList.toArray();
        String[] stringArray = Arrays.copyOf(ObjectList, ObjectList.length, String[].class);

        return stringArray;
    }
	
}
