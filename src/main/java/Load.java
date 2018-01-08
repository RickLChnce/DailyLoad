/**
	Class that represents a load that a driver hauled... 
	Author: R. LaChance
*/

package main.java;


import java.util.*;

public class Load{
	
	private int loadID;
	private String loadNumber;
	private Date loadDate;
	private int driverID;
	private int tractorID;
	private int trailerID;
	private int pickupID;
	private int destinationID;
	private int deliveryID;

	public Load(String loadNumber, Date loadDate, int driverID, int tractorID,
		int trailerID, int pickupID, int destinationID, int deliveryID){

		this.loadNumber = loadNumber;
		this.loadDate = loadDate;
		this.driverID = driverID;
		this.tractorID = tractorID;
		this.trailerID = trailerID;
		this.pickupID = pickupID;
		this.destinationID = destinationID;
		this.deliveryID = deliveryID;

		System.out.println("Load Date in Load: " + this.loadDate);
	}

	public Load(int loadID, String loadNumber, Date loadDate, int driverID, int tractorID,
		int trailerID, int pickupID, int destinationID, int deliveryID){

		this.loadID = loadID;
		this.loadNumber = loadNumber;
		this.loadDate = loadDate;
		this.driverID = driverID;
		this.tractorID = tractorID;
		this.trailerID = trailerID;
		this.pickupID = pickupID;
		this.destinationID = destinationID;
		this.deliveryID = deliveryID;
	}

	public int getLoadID(){
		return loadID;
	}

	public void setLoadID(int loadID){
		this.loadID = loadID;
	}

	public String getLoadNumber(){
		return loadNumber;
	}

	public void setLoadNumber(String loadNumber){
		this.loadNumber = loadNumber;
	}

	public Date getLoadDate(){
		return loadDate;
	}

	public void setLoadDate(Date loadDate){
		this.loadDate = loadDate;
	}

	public int getDriverID(){
		return driverID;
	}

	public void setDriverID(int driverID){
		this.driverID = driverID;
	}

	public int getTractorID(){ 
		return tractorID;
	}

	public void setTractorID(int tractorID){
		this.tractorID = tractorID;
	}

	public int getTrailerID(){
		return trailerID;
	}

	public void setTrailerID(int trailerID){
		this.trailerID = trailerID;
	}

	public int getPickupID(){
		return pickupID;
	}

	public void setPickupID(int pickupID){
		this.pickupID = pickupID;
	}

	public int getDestinationID(){
		return destinationID;
	}

	public void setDestinationID(int destinationID){
		this.destinationID = destinationID;
	}

	public int getDeliveryID(){
		return deliveryID;
	}

	public void setDeliveryID(int deliveryID){
		this.deliveryID = deliveryID;
	}
}