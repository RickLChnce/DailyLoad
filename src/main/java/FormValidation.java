/**
	Class that provides validation for form input
	Author: R. LaChance
*/

package main.java;


import javax.swing.*;
import javax.swing.text.JTextComponent;


public class FormValidation{

	private static String inputFieldLengths[][] = {{"firstNameTextField","20"},{"lastNameTextField","20"},
													{"streetTextField","30"},{"cityTextField","20"},
													{"stateTextField","2"},{"locationNameTextField","25"}};

	private static String stateAbbrevList[] = {"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL",
												"IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT",
												"NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI",
												"SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
												

	// To validate whether input is present
	public boolean isPresent(JTextComponent comp, String label, String userInput){
        if (userInput.length() == 0){
            showMessage(comp, label + " is a required field.");
            return false;
        }
        return true;
    }

	// Validation for input specifically related to tractors
	public boolean validTractorNumber(JTextComponent comp, String userInput){
		if(isInt(comp, userInput)){
			int validInt = Integer.parseInt(comp.getText());

			if(validInt < 10000)
				return true;
		}else{
			showMessage(comp, userInput + " isn't a valid tractor unit number.");
		}

		return false;
	} 

//  Need to complete this.. Should be able to use for all equipment..
	// All equipment is required to be no more than 10 years old... From current adte
	public boolean validTractorYear(JTextComponent comp, String userInput){
		return true;
	}

	public boolean validTractorMake(JTextComponent comp, String userInput){
		if(comp.getText().length() <= 15){
			return true;
		}else{
			showMessage(comp, userInput + " not a valid make of tractor.");
            return false;
		}
	}

	// Validation for input specifically related to trailers
	public boolean validTrailerNumber(JTextComponent comp, String userInput){
		if(isInt(comp, userInput)){
			int validInt = Integer.parseInt(comp.getText());

			if(validInt < 10000000)
				return true;
		}else{
			showMessage(comp, userInput + " isn't a valid trailer unit number.");
		}

		return false;
	}

	// Validation for input related specifically to input of both company and
	// customer locations
	//public boolean (JTextComponent comp, String userInput){

	//}

	// A variety of methods required for various validation purposes
	
	// Binary search that assumes the data array is sorted, and case is accounted for
	// Returns the index of item if found...
	public int binaryStringSearch(String[] data, String key, int itemCount){
		int middle;
		int low = 0;
		int high = itemCount - 1;

		while(high >= low){
			middle = (low + high) / 2;

			if(data[middle].equals(key))
				return middle;

			if(data[middle].compareTo(key) < 0)
				low = middle + 1;
			else
				high = middle - 1;
		} 

		return -1;
	}

	public boolean isInt(JTextComponent comp, String userInput){
        try{
            int validInt = Integer.parseInt(comp.getText());
            return true;
        }catch (NumberFormatException e){
            showMessage(comp, userInput + " must be a valid integer value.");
            return false;
        }
    }

	private void showMessage(JTextComponent comp, String errorMessage){
            JOptionPane.showMessageDialog(comp, errorMessage, "Invalid Entry",
                JOptionPane.INFORMATION_MESSAGE);
    }
}