/**
	Class that represents a location.. Can be a company or customer location
	Author: R. LaChance
*/

package main.java;

public class Location{

	private String locationName;
	private String streetAddress;
	private String city;
	private String state;
	private int zip;

	public Location(String locationName, String streetAddress, String city, String state, int zip){
		this.locationName = locationName;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getLocationName(){
		return locationName;
	}

	public void setLocationName(String locationName){
		this.locationName = locationName;
	}

	public String getStreetAddress(){
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress){
		this.streetAddress = streetAddress;
	}

	public String getCity(){
		return city;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getState(){
		return state;
	} 

	public void setState(String state){
		this.state = state;
	}

	public int getZip(){
		return zip;
	}

	public void setZip(int zip){
		this.zip = zip;
	}
}