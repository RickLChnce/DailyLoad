/**
	Class that represents a tractor... A truck, when combined with a trailer, is 
	used to deliver a load of car parts..
	Author: R. LaChance
*/

package main.java;

public class Tractor{
	
	private int unitNumber;
	private int modelYear;
	private String make;

	public Tractor(int unitNumber, int modelYear, String make){
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