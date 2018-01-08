/**
	Class that represents a trailer... Required to with a tractor to deliver a load of parts
	Author: R. LaChance
*/

package main.java;

public class Trailer{
	
	private int unitNumber;
	private int modelYear;
	private String make;

	public Trailer(int unitNumber, int modelYear, String make){
		this.unitNumber = unitNumber;
		this.modelYear = modelYear;
		this.make= make;
	}

	public int getUnitNumber(){
		return unitNumber;
	}

	public void setUnitNumber(int unitNumber){
		this.unitNumber = unitNumber;
	}

	public int getModelYear(){
		return modelYear;
	}

	public void setModelYear(int modelYear){
		this.modelYear = modelYear;
	}

	public String getMake(){
		return make;
	}

	public void setMake(String make){
		this.make = make;
	}
}