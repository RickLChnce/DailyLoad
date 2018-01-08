 /**
	Class that handles the SQL for retrieval of data to populate the form
	Author: R. LaChance
*/

package main.java;

import java.util.*;
import java.util.Date;
import java.sql.*;
import java.io.*;
import javax.swing.*;


public class DriverLoadsDB {
	private static String usernameDB = "Richard";
	private static String userPassWord = "Gavin";
	private static String url = "jdbc:mysql://localhost:3306/driverloads?autoReconnect=true&useSSL=false";

	private static String sqlStatements[][] = {{"drivers","SELECT FIRST_NAME FROM DRIVERS;"},
						{"tractors","SELECT UNIT_NUMBER FROM TRACTORS;"},
						{"trailers","SELECT UNIT_NUMBER FROM TRAILERS;"},
						{"companyLocations","SELECT LOCATION_NAME FROM COMPANY_LOCATIONS;"}, 
						{"customerLocaitons","SELECT LOCATION_NAME FROM CUSTOMER_LOCATIONS;"}};


	public static boolean addDriver(Driver newDriver){
		boolean added = false;

		String lastName = newDriver.getLastName();
		String firstName = newDriver.getFirstName();
		String address = newDriver.getStreetAddress();
		String city = newDriver.getCity();
		String state = newDriver.getState();
		int zip = newDriver.getZip();

		String driverSQL = ("INSERT INTO DRIVERS (LAST_NAME,FIRST_NAME,STREET,CITY,STATE,ZIP) VALUES (?,?,?,?,?,?);");

		try(Connection connection = getConnection();
			PreparedStatement st = connection.prepareStatement(driverSQL)){

			st.setString(1,lastName);
			st.setString(2,firstName);
			st.setString(3,address);
			st.setString(4,city);
			st.setString(5,state);
			st.setInt(6,zip);

			st.execute();

			added = true;

			st.close();

		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
		}

		return added;
	}

	public static boolean addLoadToDB(Load newLoad){
		boolean added = false;

		int driverID = newLoad.getDriverID();
		String loadNumber = newLoad.getLoadNumber();
		Date date = newLoad.getLoadDate();
		int tractorID = newLoad.getTractorID();
		int trailerID = newLoad.getTrailerID();
		int originID = newLoad.getPickupID();
		int deliveryID = newLoad.getDestinationID();
		int dropLocationID = newLoad.getDeliveryID();

		String loadSQL = ("INSERT INTO LOADS (LOAD_NUMBER,LOAD_DATE,DRIVER_ID,TRACTOR_ID,TRAILER_ID,PICKUP_ID,DESTINATION_ID," +
							"DELIVERY_ID) VALUES (?,?,?,?,?,?,?,?);");

		try(Connection connection = getConnection();
			PreparedStatement st = connection.prepareStatement(loadSQL)){

			st.setString(1,loadNumber);
			st.setDate(2,new java.sql.Date(date.getTime()));
			st.setInt(3,driverID);
			st.setInt(4,tractorID);
			st.setInt(5,trailerID);
			st.setInt(6,originID);
			st.setInt(7,deliveryID);
			st.setInt(8,dropLocationID);

			st.execute();

			added = true;

			st.close();

		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
		}

		return added;
	}

	public static boolean addLocation(Location newLocation, String locationType){
		boolean added = false;

		String locationName = newLocation.getLocationName();
		String address = newLocation.getStreetAddress();
		String city = newLocation.getCity();
		String state = newLocation.getState();
		int zip = newLocation.getZip();

		String locationSQL = ("INSERT INTO " + locationType + " (LOCATION_NAME,STREET,CITY,STATE,ZIP) VALUES (?,?,?,?,?);");

		try(Connection connection = getConnection();
			PreparedStatement st = connection.prepareStatement(locationSQL)){

			st.setString(1,locationName);
			st.setString(2,address);
			st.setString(3,city);
			st.setString(4,state);
			st.setInt(5,zip);

			st.execute();

			st.close();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
		}

		return added;
	}

	public static boolean addTractor(Tractor newTractor){
		boolean added = false;

		int unitNumber = newTractor.getUnitNumber();
		int modelYear = newTractor.getModelYear();
		String make = newTractor.getMake();

		String tractorSQL = ("INSERT INTO TRACTORS (UNIT_NUMBER,MODEL_YEAR,MAKE) VALUES (?,?,?);");

		try(Connection connection = getConnection();
			PreparedStatement st = connection.prepareStatement(tractorSQL)){

			st.setInt(1,unitNumber);
			st.setInt(2,modelYear);
			st.setString(3,make);

			st.execute();

			added = true;

			st.close();

		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
		}

		return added;
	}

	public static boolean addTrailer(Trailer newTrailer){
		boolean added = false;

		int unitNumber = newTrailer.getUnitNumber();
		int modelYear = newTrailer.getModelYear();
		String make = newTrailer.getMake();

		String trailerSQL = ("INSERT INTO TRAILERS (UNIT_NUMBER,MODEL_YEAR,MAKE) VALUES (?,?,?);");

		try(Connection connection = getConnection();
			PreparedStatement st = connection.prepareStatement(trailerSQL)){

			st.setInt(1,unitNumber);
			st.setInt(2,modelYear);
			st.setString(3,make);

			st.execute();

			added = true;

			st.close();

		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
		}

		return added;
	}

	public static boolean deleteLoad(Load loadToDelete){
		boolean isDeleted = false;
		int loadID = loadToDelete.getLoadID();

		String sqlString = ("DELETE FROM LOADS WHERE LOAD_ID = (?);");

		try(Connection connection = getConnection();
			PreparedStatement st = connection.prepareStatement(sqlString)){

			st.setInt(1,loadID);
			st.execute();
			st.close();

			isDeleted = true;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
		}

		return isDeleted;
	}

	public static Vector<String> getAllLocations(){		
		Vector<String> locationNames = getData("companyLocations");
		locationNames.addAll(getData("customerLocaitons"));

		return locationNames;
	}

	public static <T> Vector getData(String dataToGet){
		
		String sqlToExecute = getSQLString(dataToGet);
		Vector dataVector;

		try(Connection connection = getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sqlToExecute)){

			ResultSetMetaData rsmd = rs.getMetaData();
			int dataType = rsmd.getColumnType(1);

			if (dataType == Types.INTEGER)
				dataVector = new Vector<Integer>();
			else
				dataVector = new Vector<String>();

			while(rs.next()){
				if (dataType == Types.INTEGER)
					dataVector.add(rs.getInt(1));
				else
					dataVector.add(rs.getString(1));
			}

			rs.close();

			return dataVector;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
	}

	public static String getDriverFirstName(int desiredID){
		String desiredName = "";
		String sqlString = ("SELECT FIRST_NAME FROM DRIVERS WHERE DRIVER_ID = " + "\"" + desiredID + "\";");

		try(Connection connection = getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sqlString)){

			while(rs.next()){
				desiredName = rs.getString(1);
			}

			rs.close();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
			//return null;
		}

		return desiredName;
	}

	public static int getDriverID(String driverFName){
		int driverID = 0;
		String sqlString = ("SELECT DRIVER_ID FROM DRIVERS WHERE FIRST_NAME = " + "\"" + driverFName + "\";");

		try(Connection connection = getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sqlString)){

			while(rs.next()){
				driverID = rs.getInt(1);
			}

			rs.close();

		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
			return -1;
		}

		return driverID;
	}

	public static <T> Vector getLocationData(){
		String sqlToExecute[] = {("SELECT LOCATION_ID, LOCATION_NAME FROM COMPANY_LOCATIONS;"),
								("SELECT LOCATION_ID, LOCATION_NAME FROM CUSTOMER_LOCATIONS;")};
		Vector<LocationID> dataVector = new Vector<LocationID>();
		LocationID newID;
		int requiredID;
		String locationName;

		for(int r = 0; r < 2; r++){
			try(Connection connection = getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(sqlToExecute[r])){

				while(rs.next()){
					requiredID = rs.getInt(1);
					locationName = rs.getString(2);
					newID = new LocationID(requiredID, locationName);
					dataVector.add(newID);
				}

				rs.close();
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
				return null;
			}
		}

		return dataVector;
	}

	// Is this needed if changes function as required?!?
	private static int getLocationID(String locationName){
		int locationID = 0;
		String sqlLocationStrings[] = {("SELECT LOCATION_ID FROM COMPANY_LOCATIONS WHERE LOCATION_NAME = " + "\"" + locationName + "\";"),
									("SELECT LOCATION_ID FROM CUSTOMER_LOCATIONS WHERE LOCATION_NAME = " + "\"" + locationName + "\";")};

		for(int s = 0; s <= 1; s++){
			try(Connection connection = getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(sqlLocationStrings[s])){

				while(rs.next()){
					locationID = rs.getInt(1);
				}

				if(locationID > 0)
					break;

				rs.close();

			}catch(SQLException e){
				JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}
		}

		return locationID;
	}

	private static String getSQLString(String stringToGet){
		int correctStatement = 0;

		for (int i = 0; i < sqlStatements.length; i++){
			if(sqlStatements[i][0] == stringToGet){
				correctStatement = i;
			}
		}

		return sqlStatements[correctStatement][1];
	}

	public static <T> Vector searchForLoadDate(String searchDate){
		Load load;
		Vector<Load> foundLoads = new Vector<Load>();
		String sqlString = ("SELECT LOAD_ID, LOAD_NUMBER, LOAD_DATE, DRIVER_ID, TRACTOR_ID, TRAILER_ID" +
		 					", PICKUP_ID, DESTINATION_ID, DELIVERY_ID FROM LOADS WHERE LOAD_DATE = " + "\"" + searchDate + "\";");


		try(Connection connection = getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sqlString)){

			while(rs.next()){
				load = new Load(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
								 rs.getInt(7), rs.getInt(8), rs.getInt(9));
				foundLoads.add(load);
			}

			rs.close();

		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
			return null;
		}

		return foundLoads;
	}

	public static <T> Vector searchForLoadString(String searchLoad){
		Load load;
		Vector<Load> foundLoads = new Vector<Load>();
		String sqlString = ("SELECT LOAD_ID, LOAD_NUMBER, LOAD_DATE, DRIVER_ID, TRACTOR_ID, TRAILER_ID" +
		 					", PICKUP_ID, DESTINATION_ID, DELIVERY_ID FROM LOADS WHERE LOAD_NUMBER = " + "\"" + searchLoad + "\";");


		try(Connection connection = getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sqlString)){

			while(rs.next()){
				load = new Load(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
								 rs.getInt(7), rs.getInt(8), rs.getInt(9));
				foundLoads.add(load);
			}

			rs.close();

		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
			return null;
		}

		return foundLoads;
	}

	private static Date stringToDate(String stringDate){
		int month = (Integer.parseInt(stringDate.substring(0,2)) - 1);
		int day = Integer.parseInt(stringDate.substring(3,5));
		int year = (Integer.parseInt(stringDate.substring(6)) - 1900) ;

		// For testing
		//System.out.println("Month: " + month + " Day: " + day + " Year: " + year);

		Date date = new Date(year, month, day); 

		// For testing
		//System.out.println("Date Object: " + date);

		return date;
	}

	public static boolean updateLoad(Load currentLoad){
		boolean isUpdated = false;
		String sqlString = ("UPDATE LOADS SET LOAD_DATE = ?, DRIVER_ID = ?, TRACTOR_ID = ?, TRAILER_ID = ?, " + 
							"PICKUP_ID = ?, DESTINATION_ID = ?, DELIVERY_ID = ? WHERE LOAD_NUMBER = ?");

		String loadNumber = currentLoad.getLoadNumber();
		Date loadDate = currentLoad.getLoadDate();
		int driverID = currentLoad.getDriverID();
		int tractorID = currentLoad.getTractorID();
		int trailerID = currentLoad.getTrailerID();
		int pickupID = currentLoad.getPickupID();
		int destinationID = currentLoad.getDestinationID();
		int deliveryID = currentLoad.getDeliveryID();

		try(Connection connection = getConnection();
			PreparedStatement st = connection.prepareStatement(sqlString)){

			st.setDate(1,new java.sql.Date(loadDate.getTime()));
			st.setInt(2,driverID);
			st.setInt(3,tractorID);
			st.setInt(4,trailerID);
			st.setInt(5,pickupID);
			st.setInt(6,destinationID);
			st.setInt(7,deliveryID);
			st.setString(8,loadNumber);
			st.execute();
			st.close();

			isUpdated = true;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e,"SQL Execution Error",JOptionPane.INFORMATION_MESSAGE);
		}

		return isUpdated;
	}

	private static Connection getConnection(){
		Connection connection = null;

		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url,usernameDB,userPassWord);

			return connection;
		}catch (Exception e){
			JOptionPane.showMessageDialog(null,e,"Database Connection Error",JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
			return null;
		}
	}
}