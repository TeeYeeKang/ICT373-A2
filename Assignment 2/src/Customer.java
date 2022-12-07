//Title     : FT MUR T122 ICT373 B – Assignment 2 (Customer class)
//Author    : Tee Yee Kang
//Date      : 27/Mar/2022
//File Name : FTB-34315323-Assignment 02
//Purpose  	: This is the Customer class. The name of the customer, email, address
//            and a list of supplement are the member variables of this
//            class. The purpose of this class is to represent the customer with
//            all member variables and methods. 

import java.util.*;

/**
 * @author      TeeYeeKang    yeekang88@gmail.com
 * @version     1.1          
 */

public abstract class Customer implements java.io.Serializable{
	
	//member variables
	/**
	 * The String name
	 */
	private String name;
	
	/**
	 * The String email
	 */
	private String email;
	
	/**
	 * The Address address
	 */
	private Address address;
	
	/**
	 * The ArrayList supplimentList
	 */
	private ArrayList<Supplement> supplimentList;
	
	//default contractor
	/**
	 * Default Constructor of Customer class                          
	 * <p>
	 * This class is used to represent the default value of String name and email followed by
	 * a list of supplements the customer interested in
	 * </p>
	 * Precondition - Nil  <br>
	 * Postcondition - A Customer object is created with the default value of name, email and supplement list 
	 */
	public Customer() {
		name = "NoName";
		email ="NoEmail";
		address = new Address();
		supplimentList = new ArrayList<Supplement>();
	}
	
	//constructor with parameters
	/**
	 * Constructor of Customer class                          
	 * <p>
	 * This method is used to represent a String of name, email, street name, suburb and
	 * long value of street number and post code and a list of supplement.
	 * </p>
	 * Precondition - The value of cusName, cusEmail, stName and newSuburb must be a String value 
	 * and the value of stNumber, newPostcode is long type and an ArrayList of supplement object.<br>
	 * Postcondition - A Customer object is created with the passed in value
	 * @param  cusName Name of the customer 
	 * @param  cusEmail Customer's email address   
	 * @param  list A list of supplement  
	 * @param  stNumber Street number of Address
	 * @param  stName Street name of Address
	 * @param  newSuburb Suburb of Address
	 * @param  newPostcode Post code of Address
	 */
	public Customer(String cusName, String cusEmail, ArrayList<Supplement> list, 
			long stNumber, String stName, String newSuburb, long newPostcode) {
		name = cusName;
		email = cusEmail;
		supplimentList = list;
		address.SetStreetName(stName);
		address.SetStreetNumber(stNumber);
		address.SetSuburb(newSuburb); 
		address.SetPostcode(newPostcode);
	}
	
	/**
	 * Constructor of Customer class                          
	 * <p>
	 * This method is used to represent a String of name, email and an Address object
	 * followed by a list of supplement.
	 * </p>
	 * Precondition - The value of cusName and cusEmail must be a String value 
	 * and an ArrayList of supplement object and the newAddress is an Address class object.<br>
	 * Postcondition - A Customer object is created with the passed in value
	 * @param  cusName Name of the customer 
	 * @param  cusEmail Customer's email address   
	 * @param  list A list of supplement  
	 * @param  newAddress Address object of the customer
	 */
	public Customer(String cusName, String cusEmail, ArrayList<Supplement> list, Address newAddress) {
		name = cusName;
		email = cusEmail;
		supplimentList = list;
		address = new Address(newAddress);
	}
	
	/**
	 * This method is used to set the name of the Customer object                        
	 * <p>
	 * Precondition: Take in a new customer name with String value and cannot be null <br>
	 * Postcondition: The name of the Customer object is set.
	 * </p>   
	 * @param newName Name of customer object      
	 */
	public void SetCustomerName(String newName) {
		name = newName;
	}

	/**
	 * This method is used to get the name of the customer object                        
	 * <p>
	 * Precondition: name is initialised at the Constructor <br>
	 * Postcondition: The name of the Customer object is returned.
	 * </p>   
	 * @return name Return the Customer object's name    
	 */
	public String GetCustomerName() {
		return name;
	}
	
	/**
	 * This method is used to set the email address of the Customer object                        
	 * <p>
	 * Precondition: Take in a new customer email with String value and cannot be null <br>
	 * Postcondition: The email of the Customer object is set.
	 * </p>   
	 * @param newEmail Email address of customer object      
	 */
	public void SetEmail(String newEmail) {
		email = newEmail;
	}
	
	/**
	 * This method is used to get the email address of the customer object                        
	 * <p>
	 * Precondition: email is initialised at the Constructor <br>
	 * Postcondition: The email of the Customer object is returned.
	 * </p>   
	 * @return email Return the Customer object's email address    
	 */
	public String GetEmail() {
		return email;
	}
	
	/**
	 * This method is used to set the address of the Customer object                        
	 * <p>
	 * Precondition: Take in a new customer address with Address class type and cannot be null <br>
	 * Postcondition: The address of the Customer object is set.
	 * </p>   
	 * @param newAddress Address of customer object      
	 */
	public void SetAddress(Address newAddress) {
		address = new Address(newAddress);
	}
	
	/**
	 * This method is used to get the address of the customer object                        
	 * <p>
	 * Precondition: address is initialised at the Constructor <br>
	 * Postcondition: The address of the Customer object is returned.
	 * </p>   
	 * @return address Return the Customer object's address    
	 */
	public Address GetAddress() {
		return address;
	}
	
	/**
	 * This method is get the supplement list of the customer                    
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: The customer's supplement list is returned			 
	 * </p>   
	 * @return supplimentList Customer's supplement list
	 */
	public ArrayList<Supplement> GetSuppList(){
		return supplimentList;
	}
	
	/**
	 * This method is set the supplement list of the customer                    
	 * <p>
	 * Precondition: Pass in an ArrayList of supplement class object <br>
	 * Postcondition: The customer's supplement list is set			 
	 * </p>   
	 * @param list Customer's new supplement list
	 */
	public void SetSuppList(ArrayList<Supplement> list){
		supplimentList = list;
	}
	
	/**
	 * This method is used to add new Supplement object to the supplement list                     
	 * <p>
	 * Precondition: Take in a new Supplement class object <br>
	 * Postcondition: The passed in Supplement object is add to the customer's supplement list
	 * </p>   
	 * @param supObj Supplement class object    
	 */
	public void AddSupplement(Supplement supObj) {
		supplimentList.add(supObj);
	}
	
	/**
	 * This method is used to remove an Supplement object from the supplement list                     
	 * <p>
	 * Precondition: Take in a Supplement class object <br>
	 * Postcondition: The method will loop through to find and remove the supplement object
	 * 				  from the customer's supplement list
	 * </p>   
	 * @param supObj Supplement class object    
	 */
	public void RemoveSupplement(Supplement supObj) {
		
		boolean found = false;
		
		for(int idx=0; idx<supplimentList.size(); idx++) {
			if(supplimentList.get(idx)==supObj) {
				supplimentList.remove(idx);
				System.out.println("Supplement " + supObj.GetName() + " successfully removed ");
				found = true;
			}
		}
		if(found==false)
			System.out.println("Supplement " + supObj.GetName() + " not found ");
	}
	
	/**
	 * This method is used to display all supplement in the supplement list                  
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: Display all information of supplements in the supplement list by using the 
	 * 				  Display() method of Supplement class object
	 * </p>   
	 */
	public void PrintSupplement() {
		for(int idx = 0; idx < supplimentList.size(); idx++) {   
		   supplimentList.get(idx).Display();
		}  
	}
	
	/**
	 * This method is used get the total weekly cost of all the supplements                      
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: The supplement's total weekly cost of the customer is returned 		 
	 * </p>   
	 * @return total Total weekly cost of all supplements 
	 */
	public double GetWeeklyPrice() {
		
		double total=0;
		
		for(int idx=0; idx<supplimentList.size(); idx++) {
			total+=supplimentList.get(idx).GetWeeklyCost();
		}
		return total;
	}
	
	/**
	 * This method is used to display customer's information              
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: Display all information of customer
	 * </p>   
	 */
	public void WriteOutput() {
		System.out.println("Customer name: " + name);
		System.out.println("Customer email: " + email);
		System.out.println("Customer address: " + address.toString());
		System.out.println("Your magazine is ready !!! ");
		System.out.println("Interested suppliments: ");
		System.out.println("---------------------------");
		PrintSupplement();
	}

	/*
	//testing for Customer class
	public static void main(String[] args) {
		
		String name = "Tee Yee Kang";
		String email = "Yeekang88@gmail.com";
		ArrayList<Supplement> list = new ArrayList<Supplement>();
		Address a = new Address(12345, "Ang Mo Kio Road", "AMK", 560466);
		
		Customer cus = new Customer(name, email, list, a);
		
		Supplement s1 = new Supplement("Book1", 10);
		Supplement s2 = new Supplement("Book2", 20);
		Supplement s3 = new Supplement("Book3", 30);
		Supplement s4 = new Supplement("Book4", 40);
		Supplement s5 = new Supplement("Book4", 40);
		
		cus.AddSupplement(s1);
		cus.AddSupplement(s2);
		cus.AddSupplement(s3);
		cus.AddSupplement(s4);
		
		cus.WriteOutput();
		
		cus.RemoveSupplement(s2);
	}
	*/
	
}