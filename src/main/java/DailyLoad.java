/**
	An attempt at creating my daily load app for keeping track of my work delivering trailer loads of car parts...
	All code related to creation of forms and dialogs is listed last in this file.
	Author - R.LaChance
*/

package main.java;

import java.awt.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.text.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;

public class DailyLoad extends JFrame{
	// Declarations and object creation
	String aboutFileLocation = "about.txt";
	String generalFileLocation = "general.txt";

	private JPanel enterNewLoadPanel;
	private JPanel loadButtonPanel;
	private JMenuBar menuBar;

	// Button variable declarations
	private JButton exitButton;
	private JButton resetButton;
	private JButton addLoadButton;

	// Load panel variable declarations
	private JComboBox driversComboBox;
	private JTextField loadNumberTextField;
	private JSpinner dateSpinner;
	private JComboBox tractorComboBox;
	private JComboBox trailerComboBox;
	private JComboBox pickupComboBox;
	private JComboBox intendedDelComboBox;
	private JComboBox droppedAtComboBox;
	private JComboBox updateDriverComboBox;
	private JSpinner updateDateSpinner;
	private JComboBox updateTractorComboBox;
	private JComboBox updateTrailerComboBox;
	private JComboBox updatePickupComboBox;
	private JComboBox updateIntendedDelComboBox;
	private JComboBox updateDroppedAtComboBox;

	// Other stufff
	private JTextField unitTextField;
	private JTextField makeTextField;
	private JTextField yearTextField;

	private JTextField nameTextField;
	private JTextField streetTextField;
	private JTextField cityTextField;
	private JTextField stateTextField;
	private JTextField zipTextField;

	private JTextField firstNameTextField;
	private JTextField lastNameTextField;

	private Vector<Integer> tractorList;
	private Vector<Integer> trailerList;
	private Vector<String> locationList;
	private Vector<String> driverList;
	private Vector<LocationID> locationData;
	private Vector<Load> searchResults;

	
	public DailyLoad(String title){
		super(title);
		initFrame();
	}

	private void initFrame(){
		enterNewLoadPanel = getNewLoadPanel();
		loadButtonPanel = getButtonPanel();
		menuBar = getLoadMenuBar();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setJMenuBar(menuBar);
		Container contentPane = this.getContentPane();

		// Add the tabbed pane to the content pane
		contentPane.add(enterNewLoadPanel, BorderLayout.CENTER);
		contentPane.add(loadButtonPanel, BorderLayout.SOUTH);	
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				DailyLoad dlFrame = new DailyLoad("Driver Load Log...");
				dlFrame.setSize(400,375);
				dlFrame.setVisible(true);
			}
		});
	}

	private void addCompanyLocation(){
		FormValidation fv = new FormValidation();
		String message;

		int choice = JOptionPane.showOptionDialog(null,getNewLocation(),"Enter New Company Location",JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE,null,null,null);

		//locationPane.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		if((choice == JOptionPane.OK_OPTION)&&(fv.isPresent(nameTextField,"Location Name",nameTextField.getText()))&&
			(fv.isPresent(streetTextField,"Street",streetTextField.getText()))&&
			(fv.isPresent(cityTextField,"City",cityTextField.getText()))&&
			(fv.isPresent(stateTextField,"State",stateTextField.getText()))&&
			(fv.isPresent(zipTextField,"Zip",zipTextField.getText()))){

			String locationName = nameTextField.getText();
			String address = streetTextField.getText();
			String city = cityTextField.getText();
			String state = stateTextField.getText();
			int zip = Integer.parseInt(zipTextField.getText());

			String locationMessage = ("Location Name: " + locationName + "\n" + "Address: " + address + "\n" +
										"City: " + city + "\n" + "State: " + state + "\n" + "Zip: " + zip);

			int confirm = confirmMessage(locationMessage);

			if(confirm == JOptionPane.OK_OPTION){
				Location newLocation = new Location(locationName,address,city,state,zip);
				DriverLoadsDB.addLocation(newLocation,"COMPANY_LOCATIONS");
				message = "Company location added!";
				showMessage(message, "Location Added?");
				locationList.add(locationName);
				dispose();
			}else{
				message = "Company location not added!";
				showMessage(message, "Location Added?");
			}
		}
	}

	private void addCustomerLocation(){
		FormValidation fv = new FormValidation();
		String message;

		int choice = JOptionPane.showOptionDialog(null,getNewLocation(),"Enter New Customer Location",JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE,null,null,null);

		if((choice == JOptionPane.OK_OPTION)&&(fv.isPresent(nameTextField,"Location Name",nameTextField.getText()))&&
			(fv.isPresent(streetTextField,"Street",streetTextField.getText()))&&
			(fv.isPresent(cityTextField,"City",cityTextField.getText()))&&
			(fv.isPresent(stateTextField,"State",stateTextField.getText()))&&
			(fv.isPresent(zipTextField,"Zip",zipTextField.getText()))){

			String locationName = nameTextField.getText();
			String address = streetTextField.getText();
			String city = cityTextField.getText();
			String state = stateTextField.getText();
			int zip = Integer.parseInt(zipTextField.getText());

			String locationMessage = ("Location Name: " + locationName + "\n" + "Address: " + address + "\n" +
										"City: " + city + "\n" + "State: " + state + "\n" + "Zip: " + zip);

			int confirm = confirmMessage(locationMessage);

			if(confirm == JOptionPane.OK_OPTION){
				Location newLocation = new Location(locationName,address,city,state,zip);
				DriverLoadsDB.addLocation(newLocation,"CUSTOMER_LOCATIONS");
				message = "Customer location added!";
				showMessage(message, "Location Added?");
				locationList.add(locationName);
			}else{
				message = "Customer location not added!";
				showMessage(message, "Location Added?");
			}
		}
	}

	private void addLoadAction(){
		FormValidation fv = new FormValidation();
		String message = "";

		if(fv.isPresent(loadNumberTextField, "Load Number", loadNumberTextField.getText())){
			String driverName = (String) driversComboBox.getSelectedItem();
			String loadNumber = loadNumberTextField.getText();
			Date loadDate =  ((Date) dateSpinner.getValue());
			int tractorNumber = (int) tractorComboBox.getSelectedItem();
			int trailerNumber = (int) trailerComboBox.getSelectedItem();
			String pickupLocation = (String) pickupComboBox.getSelectedItem();
			String intendedLocation = (String) intendedDelComboBox.getSelectedItem();
			String actualDropLocation = (String) droppedAtComboBox.getSelectedItem();

			String loadMessage = ("Driver Name:  " + driverName + "\n" + 
									"Load Number:  " + loadNumber + "\n" + 
									"Date Hauled:  " + loadDate.toString() + "\n" + 
									"Tractor:	   " + tractorNumber + "\n" +
									"Trailer:	   " + trailerNumber + "\n" +
									"Load Origin:  " + pickupLocation + "\n" +
									"Destination:  " + intendedLocation + "\n" +
									"Delivered To: " + actualDropLocation);

			int confirmLoad = confirmMessage(loadMessage);

			if(confirmLoad == JOptionPane.OK_OPTION){
				Load newLoad = new Load(loadNumber.toUpperCase(), loadDate, DriverLoadsDB.getDriverID(driverName), tractorNumber, trailerNumber,
					getIDNumber(pickupLocation), getIDNumber(intendedLocation), getIDNumber(actualDropLocation));

				if(DriverLoadsDB.addLoadToDB(newLoad)){
					message = "Load successfully added!";
					showMessage(message, "Load Added?");
					resetFormAction();
				}else{
					message = "Load not added...";
					showMessage(message, "Load Added?");
				}
			}
		}
	}

	private void addNewDriver(){
		FormValidation fv = new FormValidation();
		String message;

		int choice = JOptionPane.showOptionDialog(null,getNewDriver(),"Enter New Driver Information",JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE,null,null,null);

		if((choice == JOptionPane.OK_OPTION)&&(fv.isPresent(lastNameTextField,"Last Name",lastNameTextField.getText()))&&
			(fv.isPresent(firstNameTextField,"First Name",firstNameTextField.getText()))&&
			(fv.isPresent(streetTextField,"Street",streetTextField.getText()))&&
			(fv.isPresent(cityTextField,"City",cityTextField.getText()))&&
			(fv.isPresent(stateTextField,"State",stateTextField.getText()))&&
			(fv.isPresent(zipTextField,"Zip",zipTextField.getText()))){

			String lastName = lastNameTextField.getText();
			String firstName = firstNameTextField.getText();
			String address = streetTextField.getText();
			String city = cityTextField.getText();
			String state = stateTextField.getText();
			int zip = Integer.parseInt(zipTextField.getText());

			String driverMessage = ("First Name: " + firstName + "\n" + "Last Name: " + lastName + "\n" + "Address: " + address + "\n" +
										"City: " + city + "\n" + "State: " + state + "\n" + "Zip: " + zip);

			int confirm = confirmMessage(driverMessage);

			if(confirm == JOptionPane.OK_OPTION){
				Driver newDriver = new Driver(lastName,firstName,address,city,state,zip);
				DriverLoadsDB.addDriver(newDriver);
				message = "Driver added!";
				showMessage(message, "Driver Added?");
				driverList.add(firstName);
			}else{
				message = "Driver not added!";
				showMessage(message, "Driver Added?");
			}
		}
	}

	private void addTractorAction(){
		FormValidation fv = new FormValidation();
		String message = "";
		
		int choice = JOptionPane.showOptionDialog(null,getNewEquipmentPanel(),"Enter New Tractor",JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE,null,null,null);
		
		if((choice == JOptionPane.OK_OPTION)&&(fv.isPresent(unitTextField,"Unit Number",unitTextField.getText()))&&
			(fv.isPresent(yearTextField,"Model Year",yearTextField.getText()))&&
			(fv.isPresent(makeTextField,"Model Make",makeTextField.getText()))){

			int unitNumber = Integer.parseInt(unitTextField.getText());
			int unitYear = Integer.parseInt(yearTextField.getText());
			String modelMake = makeTextField.getText();

			String makeMessage = ("Unit Number: " + unitNumber + "\n" +
									"Unit Year: " + unitYear + "\n" + 
									"Make: " + modelMake);
			
			int confirm = confirmMessage(makeMessage);
			
			if(confirm == JOptionPane.OK_OPTION){
				Tractor newTractor = new Tractor(unitNumber,unitYear,modelMake);
				DriverLoadsDB.addTractor(newTractor);
				message = "Tractor added!";
				showMessage(message,"Tractor Added?");
				tractorList.add(unitNumber);
			}else{
				message = "Tractor not added!";
				showMessage(message,"Tractor Added?");
			}
		}
	}

	private void addTrailerAction(){
		FormValidation fv = new FormValidation();
		String message = "";

		int choice = JOptionPane.showOptionDialog(null,getNewEquipmentPanel(),"Enter New Trailer",JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE,null,null,null);

		if((choice == JOptionPane.OK_OPTION)&&(fv.isPresent(unitTextField,"Unit Number",unitTextField.getText()))&&
			(fv.isPresent(yearTextField,"Model Year",yearTextField.getText()))&&
			(fv.isPresent(makeTextField,"Model Make",makeTextField.getText()))){

			int unitNumber = Integer.parseInt(unitTextField.getText());
			int unitYear = Integer.parseInt(yearTextField.getText());
			String modelMake = makeTextField.getText();

			String makeMessage = ("Unit Number: " + unitNumber + "\n" +
									"Unit Year: " + unitYear + "\n" + 
									"Make: " + modelMake);
			
			int confirm = confirmMessage(makeMessage);
			
			if(confirm == JOptionPane.OK_OPTION){
				Trailer newTrailer = new Trailer(unitNumber,unitYear,modelMake);
				DriverLoadsDB.addTrailer(newTrailer);
				message = "Trailer added!";
				showMessage(message,"Trailer Added?");
				trailerList.add(unitNumber);
			}else{
				message = "Trailer not added!";
				showMessage(message,"Trailer Added?");
			}
		}
	}

	private int confirmMessage(String loadData){
		int selection = JOptionPane.showConfirmDialog(null, loadData, "Confirm Load Data",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		return selection;
	}

	private void deleteLoad(Load loadToDelete){
		String deleteMessage;

		if(DriverLoadsDB.deleteLoad(loadToDelete)){
			deleteMessage = "Load successfully deleted!";
		}else{
			deleteMessage = "Failed to delete load...";
		}

		showMessage(deleteMessage, "Deleted?");
	}

	private void exitAction(){
		System.exit(0);
	}

	private String formatDate(Date loadDate){
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		String formattedDate = dateFormat.format(loadDate);

		return formattedDate;
	}

	private int getIDNumber(String locationName){
		int requiredID = 0;

		for(int s = 0; s < locationData.size(); s++){	
			if(locationName.equals(locationData.elementAt(s).getLocationName())){
				requiredID = locationData.elementAt(s).getLocationInt();
				break;
			}
		}

		return requiredID;
	}

	private String getLocationName(int locationID){
		String requiredName = "";

		for(int s = 0; s < locationData.size(); s++){	
			if(locationID == locationData.elementAt(s).getLocationInt()){
				requiredName = locationData.elementAt(s).getLocationName();
				break;
			}
		}

		return requiredName;
	}

	private String getMessage(String messageType){
		String message = "";
		Path aboutPath = Paths.get(messageType);

		if(Files.exists(aboutPath)){
			File aboutFile = aboutPath.toFile();

			try(BufferedReader reader = new BufferedReader(
							new FileReader(aboutFile))){
				String line = reader.readLine();

				while(line != null){
					message += "\n" + line;
					line = reader.readLine();
				}

				reader.close();
				
			}catch(IOException e){
				showMessage("Could not read file!", "I/O Error");
			}
		}else{
			showMessage("File not found!", "File Error");
		}

		return message;
	}

	private String getSearchString(String title, String question){ 
		return JOptionPane.showInputDialog(null,title,question,JOptionPane.QUESTION_MESSAGE);
	}

	private String rearrangeDateString(String currentDate){
		//System.out.println("Enter the re-arrange...");
		int month = (Integer.parseInt(currentDate.substring(5,7)));
		int day = Integer.parseInt(currentDate.substring(8,10));
		int year = (Integer.parseInt(currentDate.substring(0,4))) ;
		
		//System.out.println("In rearrange date... Month: " + month + " Day: " + day + " Year: " + year);

		return  month + "/" + day + "/" + year;
	}

	private void resetFormAction(){
		// Reset form to initial values
		driversComboBox.setSelectedIndex(0);
		loadNumberTextField.setText("");

		Date currentDate = new Date();
		dateSpinner.setValue(currentDate);

		tractorComboBox.setSelectedIndex(0);
		trailerComboBox.setSelectedIndex(0);
		pickupComboBox.setSelectedIndex(0);
		intendedDelComboBox.setSelectedIndex(0);
		droppedAtComboBox.setSelectedIndex(0);
	}

	private void searchByDateAction(){
		String searchString = getSearchString("Date To Search (yyyy-mm-dd): ","Date Search");

		searchResults = DriverLoadsDB.searchForLoadDate(searchString);
		searchDecision();
	}

	private void searchDecision(){
		if(searchResults.size() > 0){
			showSearchResults(searchResults);
			searchResults.clear();
		}else{
			showMessage("No results found!","Results");
		}
	}

	private void searchLoadNumberAction(){
		String searchString = getSearchString("Desired Load Number","Load Number Search"); 

		searchResults = DriverLoadsDB.searchForLoadString(searchString.toUpperCase());
		searchDecision();
	}

	private int searchMessage(String loadMessage){
		Object[] options = {"More Results","Update/Delete","Close"};

		return JOptionPane.showOptionDialog(null,loadMessage,"Search Results",JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
	}

	private void setLocationData(){
		locationData = DriverLoadsDB.getLocationData();
	}

	private void showAboutInfo(){
		String message = getMessage(aboutFileLocation);
		showMessage(message, "About");
	}

	private void showGeneralInfo(){
		String message = getMessage(generalFileLocation);
		showMessage(message, "General Information");
	}

	private void showMessage(String message, String title){
            JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

	private void showSearchResults(Vector<Load> results){
		boolean showMore = true;

		for(int s = 0; s < results.size(); s++){
			String driverName = DriverLoadsDB.getDriverFirstName(results.elementAt(s).getDriverID());
			String loadNumber = results.elementAt(s).getLoadNumber();
			Date loadDate = results.elementAt(s).getLoadDate();
			int tractorNumber = results.elementAt(s).getTractorID();
			int trailerNumber = results.elementAt(s).getTrailerID();
			String pickupLocation = getLocationName(results.elementAt(s).getPickupID());
			String intendedLocation = getLocationName(results.elementAt(s).getDestinationID());
			String actualDropLocation = getLocationName(results.elementAt(s).getDeliveryID());

			String loadMessage = ("Driver Name:  " + driverName + "\n" + 
									"Load Number:  " + loadNumber + "\n" + 
									"Date Hauled:  " + loadDate.toString() + "\n" + 
									"Tractor:	   " + tractorNumber + "\n" +
									"Trailer:	   " + trailerNumber + "\n" +
									"Load Origin:  " + pickupLocation + "\n" +
									"Destination:  " + intendedLocation + "\n" +
									"Delivered To: " + actualDropLocation);

			int searchMenu = searchMessage(loadMessage);

			if(searchMenu == JOptionPane.CANCEL_OPTION){
				showMore = false;
				break;
			}else if(searchMenu == JOptionPane.NO_OPTION){
				int updateMenu = updateDeleteMessage();

				if(updateMenu == JOptionPane.YES_OPTION){
					updateLoad(results.elementAt(s));
				}else if(updateMenu == JOptionPane.NO_OPTION){
					deleteLoad(results.elementAt(s));
				}
				
			}
		}

		if(showMore == true)
			showMessage("No additional search results found!","Results?");
	}

	private int updateDeleteMessage(){
		Object[] options = {"Update","Delete","Cancel"};

		return JOptionPane.showOptionDialog(null,"Update or Delete Load","Choose from Options",JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
	}
	
	private void updateLoad(Load currentLoad){
		String message = "Load not updated";

		int choice = JOptionPane.showOptionDialog(null,getUpdatePanel(currentLoad),"Update",JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE,null,null,null);

		if(choice == JOptionPane.OK_OPTION){
			currentLoad.setDriverID(DriverLoadsDB.getDriverID((String) updateDriverComboBox.getSelectedItem()));
			currentLoad.setLoadDate((Date) updateDateSpinner.getValue());
			currentLoad.setTractorID((int) updateTractorComboBox.getSelectedItem());
			currentLoad.setTrailerID((int) updateTrailerComboBox.getSelectedItem());
			currentLoad.setPickupID(getIDNumber((String) updatePickupComboBox.getSelectedItem()));
			currentLoad.setDestinationID(getIDNumber((String) updateIntendedDelComboBox.getSelectedItem()));
			currentLoad.setDeliveryID(getIDNumber((String) updateDroppedAtComboBox.getSelectedItem()));

			if(DriverLoadsDB.updateLoad(currentLoad)){
				message = "Load was updated";
			}
		}

		showMessage(message,"Updated Load?");
	}

	// Code related to creation of panels and dialogs is located below
	private JMenu getActionsMenu(){
		JMenu actionsMenu = new JMenu("Actions");
		actionsMenu.setMnemonic(KeyEvent.VK_A);

		// Create the menu items 
		JMenu newMenu = getNewMenu();
		JMenuItem searchMenu = getSearchMenu();
		JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		exitMenuItem.addActionListener(e -> exitAction());
		
		actionsMenu.add(newMenu);
		actionsMenu.add(searchMenu);
		actionsMenu.add(new JSeparator());
		actionsMenu.add(exitMenuItem);

		return actionsMenu;
	}

	private JPanel getAddress(){
		JPanel newAddress = new JPanel();
		newAddress.setLayout(new BorderLayout());

		JPanel streetPanel = new JPanel();
		JPanel cityPanel = new JPanel();
		JPanel stateZipPanel = new JPanel();

		JLabel streetLabel = new JLabel("Street:");
		streetTextField = new JTextField("",20);
		streetPanel.add(streetLabel);
		streetPanel.add(streetTextField);

		JLabel cityLabel = new JLabel("City:");
		cityTextField = new JTextField("",20);
		cityPanel.add(cityLabel);
		cityPanel.add(cityTextField);

		JLabel stateLabel = new JLabel("State:");
		stateTextField = new JTextField("",2);
		stateZipPanel.add(stateLabel);
		stateZipPanel.add(stateTextField);

		JLabel zipLabel = new JLabel("Zip:");
		zipTextField = new JTextField("",5);
		stateZipPanel.add(zipLabel);
		stateZipPanel.add(zipTextField);

		newAddress.add(streetPanel,BorderLayout.NORTH);
		newAddress.add(cityPanel,BorderLayout.CENTER);
		newAddress.add(stateZipPanel,BorderLayout.SOUTH);

		return newAddress;
	}

	private JPanel getButtonPanel(){
		JPanel newLoadButtons = new JPanel();
		resetButton = new JButton("Reset Form");
		resetButton.setMnemonic(KeyEvent.VK_R);
		resetButton.addActionListener(e -> resetFormAction());

		addLoadButton = new JButton("Add Load");
		addLoadButton.setMnemonic(KeyEvent.VK_D);
		addLoadButton.addActionListener(e -> addLoadAction());
		
		exitButton = new JButton("Exit");
		exitButton.setMnemonic(KeyEvent.VK_X);
		exitButton.addActionListener(e -> exitAction());

		newLoadButtons.add(resetButton);
		newLoadButtons.add(addLoadButton);
		newLoadButtons.add(exitButton);

		return newLoadButtons;
	}

	private JMenu getHelpMenu(){
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem generalMenuItem = new JMenuItem("General", KeyEvent.VK_G);
		generalMenuItem.addActionListener(e -> showGeneralInfo());
		JMenuItem aboutMenuItem = new JMenuItem("About", KeyEvent.VK_A);
		aboutMenuItem.addActionListener(e -> showAboutInfo());

		helpMenu.add(generalMenuItem);
		helpMenu.add(aboutMenuItem);

		return helpMenu;
	}

	private JMenuBar getLoadMenuBar(){
		JMenuBar menuBar = new JMenuBar();

		JMenu actionsMenu = getActionsMenu();
		JMenu helpMenu = getHelpMenu();

		menuBar.add(actionsMenu);
		menuBar.add(helpMenu);

		return menuBar;
	}

	private JPanel getNewDriver(){
		JPanel newDriver = new JPanel();
		newDriver.setLayout(new BorderLayout());

		JPanel firstPanel = new JPanel();
		JPanel lastPanel = new JPanel();

		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameTextField = new JTextField("",20);
		firstPanel.add(firstNameLabel);
		firstPanel.add(firstNameTextField);

		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameTextField = new JTextField("",20);
		lastPanel.add(lastNameLabel);
		lastPanel.add(lastNameTextField);

		newDriver.add(firstPanel, BorderLayout.NORTH);
		newDriver.add(lastPanel, BorderLayout.CENTER);
		newDriver.add(getAddress(), BorderLayout.SOUTH);

		return newDriver;
	}

	private JPanel getNewEquipmentPanel(){
		JPanel newEquipment = new JPanel();
		newEquipment.setLayout(new BorderLayout());

		JPanel unitPanel = new JPanel();
		JPanel yearPanel = new JPanel();
		JPanel makePanel = new JPanel();

		JLabel unitLabel = new JLabel("Unit Number: ");
		unitTextField = new JTextField("",10);
		unitPanel.add(unitLabel);
		unitPanel.add(unitTextField);

		JLabel makeLabel = new JLabel("Make: ");
		makeTextField = new JTextField("",15);
		makePanel.add(makeLabel);
		makePanel.add(makeTextField);

		JLabel yearLabel = new JLabel("Model Year: ");
		yearTextField = new JTextField("",10);
		yearPanel.add(yearLabel);
		yearPanel.add(yearTextField);

		newEquipment.add(unitPanel,BorderLayout.NORTH);
		newEquipment.add(yearPanel,BorderLayout.CENTER);
		newEquipment.add(makePanel,BorderLayout.SOUTH);

		return newEquipment;
	}

	private JPanel getNewLoadPanel(){
		JPanel newLoadPanel = new JPanel();
		newLoadPanel.setLayout(new GridLayout(0,2,0,10));

		JLabel driverIDLabel = new JLabel("Driver Name: ");
		driverList = DriverLoadsDB.getData("drivers");
		driversComboBox = new JComboBox<String>(driverList);

		JLabel loadNumberLabel = new JLabel("Load Number: ");
		loadNumberTextField = new JTextField("",20);

		JLabel loadDateLabel = new JLabel("Date Load Hauled (dd/mm/yyyy): ");
		Calendar cal = Calendar.getInstance();
		cal.set(2008,1,1);
		Date minDate = cal.getTime();
		cal.set(2025,1,1);
		Date maxDate = cal.getTime();
		Date currentDate = new Date();
		int steps = Calendar.DAY_OF_WEEK_IN_MONTH;
		SpinnerDateModel spinnerModel = new SpinnerDateModel(currentDate, minDate, maxDate, steps);
		dateSpinner = new JSpinner(spinnerModel);
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner,"dd/MM/yyyy"));

		JLabel tractorLabel = new JLabel("Tractor: ");
		tractorList = DriverLoadsDB.getData("tractors");
		tractorComboBox = new JComboBox<Integer>(tractorList);

		JLabel trailerLabel = new JLabel("Trailer Used: ");
		trailerList = DriverLoadsDB.getData("trailers");
		trailerComboBox = new JComboBox<Integer>(trailerList);

		// Add code to initialize the locationData vector holds int location Id and string name
		setLocationData();

		JLabel pickupLabel = new JLabel("Pickup Location: ");
		locationList = DriverLoadsDB.getAllLocations();
		pickupComboBox = new JComboBox<String>(locationList);

		JLabel intendedDeliveryLocationLabel = new JLabel("Delivery Location: ");
		intendedDelComboBox = new JComboBox<String>(locationList);

		JLabel droppedAtLocationLabel = new JLabel("Dropped at Location: ");
		droppedAtComboBox = new JComboBox<String>(locationList);

		newLoadPanel.add(driverIDLabel);
		newLoadPanel.add(driversComboBox);

		newLoadPanel.add(loadNumberLabel);
		newLoadPanel.add(loadNumberTextField);

		newLoadPanel.add(loadDateLabel);
		newLoadPanel.add(dateSpinner);

		newLoadPanel.add(tractorLabel);
		newLoadPanel.add(tractorComboBox);

		newLoadPanel.add(trailerLabel);
		newLoadPanel.add(trailerComboBox);

		newLoadPanel.add(pickupLabel);
		newLoadPanel.add(pickupComboBox);

		newLoadPanel.add(intendedDeliveryLocationLabel);
		newLoadPanel.add(intendedDelComboBox);

		newLoadPanel.add(droppedAtLocationLabel);
		newLoadPanel.add(droppedAtComboBox);

		return newLoadPanel;
	}

	private JPanel getNewLocation(){
		JPanel newLocation = new JPanel();
		newLocation.setLayout(new BorderLayout());

		JPanel namePanel = new JPanel();

		JLabel nameLabel = new JLabel("Location Name:");
		nameTextField = new JTextField("",20);

		namePanel.add(nameLabel);
		namePanel.add(nameTextField);
		
		newLocation.add(namePanel,BorderLayout.NORTH);
		newLocation.add(getAddress(),BorderLayout.CENTER);

		return newLocation;
	}

	private JMenu getNewMenu(){
		JMenu newMenu = new JMenu("New");
		newMenu.setMnemonic(KeyEvent.VK_N);

		JMenuItem companyLocationMenuItem = new JMenuItem("Company Location", KeyEvent.VK_C);
		companyLocationMenuItem.addActionListener(e -> addCompanyLocation());
		JMenuItem customerLocationMenuItem = new JMenuItem("Customer Location", KeyEvent.VK_U);
		customerLocationMenuItem.addActionListener(e -> addCustomerLocation());
		JMenuItem tractorMenuItem = new JMenuItem("Tractor", KeyEvent.VK_T);
		tractorMenuItem.addActionListener(e -> addTractorAction());
		JMenuItem trailerMenuItem = new JMenuItem("Trailer", KeyEvent.VK_R);
		trailerMenuItem.addActionListener(e -> addTrailerAction());
		JMenuItem driverMenuItem = new JMenuItem("Driver", KeyEvent.VK_D);
		driverMenuItem.addActionListener(e -> addNewDriver());

		newMenu.add(companyLocationMenuItem);
		newMenu.add(customerLocationMenuItem);
		newMenu.add(tractorMenuItem);
		newMenu.add(trailerMenuItem);
		newMenu.add(driverMenuItem);

		return newMenu;
	}

	private JMenu getSearchMenu(){
		JMenu searchMenu = new JMenu("Search");
		searchMenu.setMnemonic(KeyEvent.VK_S);

		JMenuItem byDateMenuItem = new JMenuItem("By Date", KeyEvent.VK_A);
		byDateMenuItem.addActionListener(e -> searchByDateAction());
		JMenuItem byLoadNumberMenuItem = new JMenuItem("By Load Number", KeyEvent.VK_N);
		byLoadNumberMenuItem.addActionListener(e -> searchLoadNumberAction());

		searchMenu.add(byDateMenuItem);
		searchMenu.add(byLoadNumberMenuItem);

		return searchMenu;
	}

	private JPanel getUpdatePanel(Load updateLoad){
		JPanel updatePanel = new JPanel();
		updatePanel.setLayout(new BorderLayout());

		JPanel generalInfoPanel = new JPanel();
		generalInfoPanel.setLayout(new BorderLayout());
		JPanel updateEquipmentPanel = new JPanel();
		updateEquipmentPanel.setLayout(new BorderLayout());
		JPanel updateLocationPanel = new JPanel();
		updateLocationPanel.setLayout(new BorderLayout());

		JPanel messagePanel = new JPanel();
		JLabel messageLabel = new JLabel("Update Load Number: " + updateLoad.getLoadNumber());
		messagePanel.add(messageLabel);

		JPanel namePanel = new JPanel();
		JLabel nameLabel = new JLabel("Name: ");
		updateDriverComboBox = new JComboBox(driverList);
		namePanel.add(nameLabel);
		namePanel.add(updateDriverComboBox);
		updateDriverComboBox.setSelectedItem(DriverLoadsDB.getDriverFirstName(updateLoad.getDriverID()));

		JPanel datePanel = new JPanel();
		JLabel dateLabel = new JLabel("Date (yyyy-mm-dd): ");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2008,1,1);
		Date minDate = cal.getTime();
		cal.set(2025,1,1);
		Date maxDate = cal.getTime();
		Date currentDate = new Date();
		int steps = Calendar.DAY_OF_WEEK_IN_MONTH;
		SpinnerDateModel spinnerModel = new SpinnerDateModel(currentDate, minDate, maxDate, steps);
		updateDateSpinner = new JSpinner(spinnerModel);
		updateDateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner,"dd/MM/yyyy"));


		datePanel.add(dateLabel);
		datePanel.add(updateDateSpinner);

		generalInfoPanel.add(messagePanel,BorderLayout.NORTH);
		generalInfoPanel.add(namePanel,BorderLayout.CENTER);
		generalInfoPanel.add(datePanel,BorderLayout.SOUTH);

		JPanel tractorPanel = new JPanel();
		JLabel tractorLabel = new JLabel("Tractor: ");
		updateTractorComboBox = new JComboBox(tractorList);
		tractorPanel.add(tractorLabel);
		tractorPanel.add(updateTractorComboBox);
		updateTractorComboBox.setSelectedItem(updateLoad.getTractorID());

		JPanel trailerPanel = new JPanel();
		JLabel trailerLabel = new JLabel("Trailer: ");
		updateTrailerComboBox = new JComboBox(trailerList);
		trailerPanel.add(trailerLabel);
		trailerPanel.add(updateTrailerComboBox);
		updateTrailerComboBox.setSelectedItem(updateLoad.getTrailerID());

		updateEquipmentPanel.add(tractorPanel,BorderLayout.NORTH);
		updateEquipmentPanel.add(trailerPanel,BorderLayout.CENTER);


		JPanel pickupPanel = new JPanel();
		JLabel pickupLabel = new JLabel("Pickup Location: ");
		updatePickupComboBox = new JComboBox(locationList);
		pickupPanel.add(pickupLabel);
		pickupPanel.add(updatePickupComboBox);
		updatePickupComboBox.setSelectedItem(getLocationName(updateLoad.getPickupID()));

		JPanel intendedPanel = new JPanel();
		JLabel intendedDeliveryLocationLabel = new JLabel("Delivery Location: ");
		updateIntendedDelComboBox = new JComboBox(locationList);
		intendedPanel.add(intendedDeliveryLocationLabel);
		intendedPanel.add(updateIntendedDelComboBox);
		updateIntendedDelComboBox.setSelectedItem(getLocationName(updateLoad.getDestinationID()));

		JPanel droppedPanel = new JPanel();
		JLabel droppedAtLocationLabel = new JLabel("Dropped at Location: ");
		updateDroppedAtComboBox = new JComboBox(locationList);
		droppedPanel.add(droppedAtLocationLabel);
		droppedPanel.add(updateDroppedAtComboBox);
		updateDroppedAtComboBox.setSelectedItem(getLocationName(updateLoad.getDeliveryID()));

		updateLocationPanel.add(pickupPanel,BorderLayout.NORTH);
		updateLocationPanel.add(intendedPanel,BorderLayout.CENTER);
		updateLocationPanel.add(droppedPanel,BorderLayout.SOUTH);

		updatePanel.add(generalInfoPanel,BorderLayout.NORTH);
		updatePanel.add(updateEquipmentPanel,BorderLayout.CENTER);
		updatePanel.add(updateLocationPanel,BorderLayout.SOUTH);

		return updatePanel;
	}
} 