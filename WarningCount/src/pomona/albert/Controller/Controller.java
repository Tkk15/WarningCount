package pomona.albert.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pomona.albert.model.*;

public class Controller {

	public static Controller controller;



	public static final String DB_NAME = "warned_people.db";
	private static final String TABLE_NAME = "ppl";
	public static final String[] FIELD_NAME ={"_id","license_number","license_plate","description"};
	public static final String[] FIELD_TYPE ={"INTEGER PRIMARY KEY","TEXT","TEXT","TEXT"};
	

	private static final String USER_TABLE_NAME = "user";
	private static final String[] USER_FIELD_NAMES = { "_id", "name", "email", "password"};
	private static final String[] USER_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "TEXT"};
	
	private User mCurrentUser;
	private DBModel mDB;
	private DBModel mUserDB;
	
	//int iD, String name, String middleName, String lastName, String licenseNO, VehicleType vehicleType,
	//String licensePlate, String make, String model, int year, String color
	private Controller(){}
	
	private ObservableList<PersonBeingWarned> mAllWarningsList;
	private ObservableList<User> mAllUsersList;
	
	public static ObservableList<PersonBeingWarned> getAllWarningsList()
	{
		return controller.mAllWarningsList;
	}

	public static ObservableList<User> getAllUserList()
	{
		return	controller.mAllUsersList;
	}
	
	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
			controller.mAllWarningsList = FXCollections.observableArrayList();
			controller.mAllUsersList = FXCollections.observableArrayList();
			try {
				
				controller.mUserDB = new DBModel(DB_NAME, USER_TABLE_NAME, USER_FIELD_NAMES, USER_FIELD_TYPES);
				ArrayList<ArrayList<String>> userRS = controller.mUserDB.getAllRecords();
				for (ArrayList<String> values : userRS) {
					int id = Integer.parseInt(values.get(0));
					String name = values.get(1);
					String email = values.get(2);

					controller.mAllUsersList.add(new User(id, name, email));
				}
					
				controller.mDB = new DBModel(DB_NAME, TABLE_NAME, FIELD_NAME, FIELD_TYPE);

				ArrayList<ArrayList<String>> rs = controller.mDB.getAllRecords();
				for (ArrayList<String> values : rs) {
					System.out.println(values);
					int id = Integer.parseInt(values.get(0));
					String licenseNO = values.get(1);

					String licensePlate = values.get(2);

					String description = values.get(3);
					controller.mAllWarningsList.add(new PersonBeingWarned(id, licenseNO, licensePlate, description));
				}

				
				
			} catch (SQLException e) {
				System.out.println(e.getSQLState());
				System.out.println(e.getMessage());
			}

		}

		return controller;
	}

	public boolean isValidPassword(String password)
	{
		// Valid password must contain (see regex below):
		// At least one digit
		// At least one lower case letter
		// At least one upper case letter
		// At least one special character !@#$%^&*()_+\-=[]{};':"\|,.<>/?
		// At least 8 characters long, but no more than 16
		return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\\\|,.<>\\/?]).{8,16}$");
	}

	public boolean isValidEmail(String email)
	{
		return email.matches(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	public String signUpUser(String name, String email, String password)
	{
		// Check email to see if valid
	    if (!isValidEmail(email))
	        return "Email address not valid.  Please try different address.";

	    // Check to see if email is already used
	    // Loop through all users list
	    for (User u : controller.mAllUsersList)
	        if (email.equalsIgnoreCase(u.getEmail()))
	            return "Email address already used.  Please sign in or use different address.";

	    // Check password to see if valid
	    	if (!isValidPassword(password))
	    		return "Password must be at least 8 characters, including 1 upper case letter, 1 number and 1 symbol.";

	    // Made it through all the checks, create the new user in the database
	    String[] values = {name, email, password};
	    // Insert the new user into the database
	    try
        {
	        // Store the new id
            int id = controller.mUserDB.createRecord(
                    Arrays.copyOfRange(USER_FIELD_NAMES, 1, USER_FIELD_NAMES.length), values);
            // Save the new user as the current user
            controller.mCurrentUser = new User(id, name, email);
            // Add the new user to the observable list
            controller.mAllUsersList.add(controller.mCurrentUser);
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "Error creating user, please try again.";
        }



		return "SUCCESS";
	}
	public String signInUser(String email, String password) {
		//TODO: Implement this method
	    // Loop through the list of all users
		System.out.println("INSIDE SIGNIN USER");
	    for (User u : controller.mAllUsersList)
	    {		

	        if (u.getEmail().equalsIgnoreCase(email))
	        {
	            // Go into database to retrieve password:
	            try
                {
                    ArrayList<ArrayList<String>> userResults = controller.mUserDB.getRecord(String.valueOf(u.getId()));
                    
                    String storedPassword = userResults.get(0).get(3);
            
                    //"_id", "name", "email", "password"
                    // Check the passwords
                    if (password.equals(storedPassword))
                    {
                        mCurrentUser = u;
                        return "SUCCESS";
                    }
                    else
                        break;

                }
                catch (SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
	        }
	    }

	    return "Incorrect email and/or password.  Please try again.";
	}

	public boolean addPerson( String licenseNO,String licensePlate,String description ) {
		
	        //Lets use this information to insert a record in the database:
	        //Use mDB
	        //fields = predefined
	        //cosntruct a values array
	    String [] values = {licenseNO,licensePlate,description};

		try {
			int id = mDB.createRecord(Arrays.copyOfRange(FIELD_NAME, 1, FIELD_NAME.length), values);
			System.out.println(id);
			
			PersonBeingWarned p = new PersonBeingWarned(id, licenseNO, licensePlate, description);
			System.out.println(p);
			controller.mAllWarningsList.add(p);
		} catch (SQLException e) {
			System.out.println(e.getSQLState());
			return false;
		}

		return true;
	}

	public boolean deletePerson(PersonBeingWarned p) {
		System.out.println(p);
		if (p == null)
			return false;
		controller.mAllWarningsList.remove(p); 
		try {
			controller.mDB.deleteRecord(String.valueOf(p.getID()));
		} catch (SQLException e) {
			System.out.println(e.getSQLState());
			return false;
		}
		return true;
	}

	
	
	
	
	
	
}
