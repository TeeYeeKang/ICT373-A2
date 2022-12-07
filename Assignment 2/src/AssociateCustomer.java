//Title     : FT MUR T122 ICT373 B – Assignment 2 (AssociateCustomer class)
//Author    : Tee Yee Kang
//Date      : 27/Mar/2022
//File Name : FTB-34315323-Assignment 02
//Purpose  	: This is the AssociateCustomer class. AssociateCustomer is derive class of Customer
//            class object. It contains all Customer's member variables and methods.
//            The purpose of this class is to represent the associate customer

import java.util.ArrayList;

/**
 * @author      TeeYeeKang    yeekang88@gmail.com
 * @version     1.1          
 */

public class AssociateCustomer extends Customer implements java.io.Serializable{

	//member variables
	//none
	
	//default constructor
	/**
	 * Default Constructor of AssociateCustomer class                          
	 * <p>
	 * This class is used to represent the default value of Customer parent class. 
	 * </p>
	 * Precondition - Nil  <br>
	 * Postcondition - A AssociateCustomer object is created with the all default value
	 */
	public AssociateCustomer() {
		super();
	}
	
	//constructor with parameters 
	/**
	 * Constructor of AssociateCustomer class                          
	 * <p>
	 * This method is used to represent the AssociteCustomer with all member variables
	 * </p>
	 * Precondition - The value of cusName and cusEmail must be a String value and an ArrayList of supplement object.
	 *                The value of address such as stNumber and newPostcode must be long value and stName
	 *                and stName must be String type.<br>
	 * Postcondition - An AssociateCustomer object is created with the passed in value
	 * @param  cusName Name of the customer 
	 * @param  cusEmail Customer's email address   
	 * @param  list A list of supplement 
	 * @param  stNumber Street number of Address 
	 * @param  stName Street name of Address 
	 * @param  newSuburb Suburb of Address 
	 * @param  newPostcode Post code of Address 
	 */
	public AssociateCustomer(String cusName, String cusEmail, ArrayList<Supplement> list, 
			long stNumber, String stName, String newSuburb, long newPostcode) {
		super(cusName, cusEmail, list, stNumber, stName, newSuburb, newPostcode);
	}
	
	/**
	 * Constructor of AssociateCustomer class                          
	 * <p>
	 * This method is used to represent the AssociteCustomer with all member variables
	 * </p>
	 * Precondition - The value of cusName and cusEmail must be a String value, an ArrayList of supplement object
	 *                and the value of address is user define Address class type.<br>
	 * Postcondition - An AssociateCustomer object is created with the passed in value
	 * @param  cusName Name of the customer 
	 * @param  cusEmail Customer's email address   
	 * @param  list A list of supplement 
	 * @param  address An address of the customer 
	 */
	public AssociateCustomer(String cusName, String cusEmail, ArrayList<Supplement> list, Address address) {
		super(cusName, cusEmail, list, address);
	}
	
	/*
	//testing purpose
	public static void main(String[]args) {
		
		AssociateCustomer cus = new AssociateCustomer();
		cus.SetCustomerName("Tee");
		cus.SetEmail("tyk.gmail.com");
		
		
		Supplement s1 = new Supplement("Book1", 10);
		Supplement s2 = new Supplement("Book2", 20);
		Supplement s3 = new Supplement("Book3", 30);
		Supplement s4 = new Supplement("Book4", 40);
		
		cus.AddSupplement(s1);
		cus.AddSupplement(s2);
		cus.AddSupplement(s3);
		cus.AddSupplement(s4);
		cus.RemoveSupplement(s3);
		
		cus.WriteOutput();
	}
	*/
}