/**
	LocationID
	This class represents an object used to store the location id and location name used with the 
	DailyLoad app
	Author: R. LaChance
*/
package main.java;

public class LocationID{
	
	private int locationInt;
	private String locationName;

	public LocationID(int locationInt, String locationName){
		this.locationInt = locationInt;
		this.locationName = locationName;
	}

	public LocationID(){
		locationInt = 0;
		locationName = "";
	}

	public int getLocationInt(){
		return locationInt;
	}

	public void setLocationInt(int locationInt){
		this.locationInt = locationInt;
	}

	public String getLocationName(){
		return locationName;
	}

	public void setLocationName(String locationName){
		this.locationName = locationName;
	}
}