/**
	Class that represents a driver
	Author: R. LaChance
*/

package main.java;

public class Driver{
	
	private String lastName;
	private String firstName;
	private String streetAddress;
	private String city;
	private String state;
	private int zip;

	public Driver(String lastName, String firstName, String streetAddress, String city, String state, int zip){
		this.lastName = lastName;
		this.firstName = firstName;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getLastName(){
		return lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
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