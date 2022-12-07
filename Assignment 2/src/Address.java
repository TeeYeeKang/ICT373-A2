//Title     : FT MUR T122 ICT373 B – Assignment 2 (Address class)
//Author    : Tee Yee Kang
//Date      : 27/Mar/2022
//File Name : FTB-34315323-Assignment 02
//Purpose  	: This is the Address class. The street number, street name,
//            suburb and post code are the member variables of this
//            class. The purpose of this class is to represent the address of the customer
//            with all member variables and methods.

/**
 * @author      TeeYeeKang    yeekang88@gmail.com
 * @version     1.1          
 */

public class Address implements java.io.Serializable{

	/**
	 * The long streetNumber
	 */
	private long streetNumber;
	
	/**
	 * The String streetName
	 */
	private String streetName;
	
	/**
	 * The String suburb
	 */
	private String suburb;
	
	/**
	 * The long postcode
	 */
	private long postcode;

	// default constructor
	/**
	 * Default Constructor of Address class                          
	 * <p>
	 * This class is used to represent the default value of streetName, suburb (with String type) and
	 * streetNumber, post code (with long type).
	 * </p>
	 * Precondition - Nil  <br>
	 * Postcondition - A Address object is created with the default value of all member variables 
	 */
	public Address() {
		streetNumber = 0;
		streetName = "Unknown";
		suburb = "Unknown";
		postcode = 0;
	}

	//constructor with parameters
	/**
	 * Constructor of Address class                          
	 * <p>
	 * This method is used to represent street name, suburb (with String type) and
	 * street number, post code (with long type).
	 * </p>
	 * Precondition - The value of stNumber and newPostcode must be a long value and String type of
	 * stName and newSuburb.<br>
	 * Postcondition - A Address object is created with the passed in value
	 * @param  stNumber Street number
	 * @param  stName Street name 
	 * @param  newSuburb Suburb 
	 * @param  newPostcode Post code 
	 */
	public Address(long stNumber, String stName, String newSuburb, long newPostcode) {
		streetNumber = stNumber;
		streetName = stName;
		suburb = newSuburb;
		postcode = newPostcode;
	}

	// copy constructor
	/**
	 * Copy constructor of Address class                          
	 * <p>
	 * This method is used to create an Address object with value of another Address object.
	 * </p>
	 * Precondition - The data type newAddress must be user define Address.<br>
	 * Postcondition - A Address object is created with the value of passed in Address object
	 * @param  newAddress Address object
	 */
	public Address(Address newAddress) {
		streetNumber = newAddress.streetNumber;
		streetName = newAddress.streetName;
		suburb = newAddress.suburb;
		postcode = newAddress.postcode;
	}

	/**
	 * This method is used to get the street number of the Address object                        
	 * <p>
	 * Precondition: street number is initialised at the Constructor <br>
	 * Postcondition: The street number of the Address object is returned.
	 * </p>   
	 * @return streetNumber Return the Address object's street number    
	 */
	public long GetStreeNumber() {
		return streetNumber;
	}

	/**
	 * This method is used to get the street name of the Address object                        
	 * <p>
	 * Precondition: street name is initialised at the Constructor <br>
	 * Postcondition: The street name of the Address object is returned.
	 * </p>   
	 * @return streetName Return the Address object's street name    
	 */
	public String GetStreetName() {
		return streetName;
	}

	/**
	 * This method is used to get the suburb of the Address object                        
	 * <p>
	 * Precondition: suburb is initialised at the Constructor <br>
	 * Postcondition: The suburb of the Address object is returned.
	 * </p>   
	 * @return suburb Return the Address object's suburb  
	 */
	public String GetSuburb() {
		return suburb;
	}

	/**
	 * This method is used to get the post code of the Address object                        
	 * <p>
	 * Precondition: post code is initialised at the Constructor <br>
	 * Postcondition: The post code of the Address object is returned.
	 * </p>   
	 * @return postcode Return the Address object's postcode  
	 */
	public long GetPostcode() {
		return postcode;
	}

	/**
	 * This method is used to set the street number of the Address class object                        
	 * <p>
	 * Precondition: Take in a street number of Address object with long value and cannot be null <br>
	 * Postcondition: The street number of the Address object is set.
	 * </p>   
	 * @param newStNumber Street number of Address object      
	 */
	public void SetStreetNumber(long newStNumber) {
		streetNumber = newStNumber;
	}

	/**
	 * This method is used to set the street name of the Address class object                        
	 * <p>
	 * Precondition: Take in a street name of Address object with String value and cannot be null <br>
	 * Postcondition: The street name of the Address object is set.
	 * </p>   
	 * @param newStName Street name of Address object      
	 */
	public void SetStreetName(String newStName) {
		streetName = newStName;
	}

	/**
	 * This method is used to set the suburb of the Address class object                        
	 * <p>
	 * Precondition: Take in a suburb of Address object with String value and cannot be null <br>
	 * Postcondition: The suburb of the Address object is set.
	 * </p>   
	 * @param newSuburb Suburb of Address object      
	 */
	public void SetSuburb(String newSuburb) {
		suburb = newSuburb;
	}

	/**
	 * This method is used to set the post code of the Address class object                        
	 * <p>
	 * Precondition: Take in a post code of Address object with long value and cannot be null <br>
	 * Postcondition: The post code of the Address object is set.
	 * </p>   
	 * @param newPostcode Post code of Address object      
	 */
	public void SetPostcode(long newPostcode) { 
		postcode = newPostcode;
	}
	
	/**
	 * This method is used to display customer's address information              
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: Display all information of customer address
	 * </p>   
	 */
	public void WriteOutput() {
		System.out.println("Street No: " + streetNumber);
		System.out.println("Street Name: " + streetName);
		System.out.println("Suburb: " + suburb);
		System.out.println("Postcode: " + postcode);
	}
	
	/**
	 * This method is used to return customer's address information in String type            
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: Return all information of customer address in String typr
	 * </p>   
	 */
	public String toString() {
		//String address = Math.round(streetNumber) + ", " + streetName + ", " + suburb + ", " + Math.round(postcode) + ".";
		String address = streetNumber + ", " + streetName + ", " + suburb + ", " + postcode;
		return address;
	}
	
	/*
	//testing purpose
	public static void main(String[] args) {
		
		Address a1 = new Address();
		a1.SetStreetNumber(12345);
		a1.SetStreetName("StreetOne");
		a1.SetSuburb("NewCity");
		a1.SetPostcode(1278);
		//a1.WriteOutput();
		
		//test for copy constructor
		Address a2 = new Address(a1);
		//a2.WriteOutput();
		
		a2.SetStreetName("StreetTwo");
		a2.WriteOutput();	
	}
	*/
}
