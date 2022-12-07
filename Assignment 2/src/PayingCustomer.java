//Title     : FT MUR T122 ICT373 B – Assignment 2 (PayingCustomer class)
//Author    : Tee Yee Kang
//Date      : 27/Mar/2022
//File Name : FTB-34315323-Assignment 02
//Purpose  	: This is the PayingCustomer class. PayingCustomer is derive class of Customer
//            class object. It contains all Customer's member variables and methods plus some
//            of its additional variables and method. The purpose of this class
//            is to represent the paying customer.

import java.util.ArrayList;

/**
 * @author      TeeYeeKang    yeekang88@gmail.com
 * @version     1.1          
 */

public class PayingCustomer extends Customer implements java.io.Serializable{

	//member variables
	/**
	 * The Payment paymentMethod
	 */
	private Payment paymentMethod;
	
	/**
	 * The ArrayList customerList
	 */
	private ArrayList<AssociateCustomer> customerList;
	
	//default constructor
	/**
	 * Default Constructor of PayingCustomer class                          
	 * <p>
	 * This class is used to represent the default value of Customer parent class and
	 * its own member variables. It contains a PaymentMethod and a list of AssociateCustomer object
	 * </p>
	 * Precondition - Nil  <br>
	 * Postcondition - A PayingCustomer object is created with the all default value
	 */
	public PayingCustomer() {
		super();
		paymentMethod = new Payment();
		customerList = new ArrayList<AssociateCustomer>();
	} 
	
	//constructor with parameters
	/**
	 * Constructor of PayingCustomer class                          
	 * <p>
	 * This class is used to represent a all Customer class variable and its own member variables
	 * </p>
	 * Precondition - The value of cusName, cusEmail, stName and newPostcode must be a String value and 
	 *                the value of stNumber and newPostcode is long type and an ArrayList of supplement object.
	 *                Followed by a bankAcc with long type, option of int type and a list of AssociateCustomer stored in
	 *                ArrayList. <br>
	 * Postcondition - A PayingCustomer object is created with the pass in value
	 * @param  cusName Name of the customer 
	 * @param  cusEmail Customer's email address   
	 * @param  list A list of supplement  
	 * @param  bankAcc Bank account number 
	 * @param  option Type of payment method 
	 * @param  cusList A list of associate customer 
	 * @param  stNumber Street number of Address object
	 * @param  stName Street name of Address object
	 * @param  newSuburb Suburb of Address object
	 * @param  newPostcode post code of Address object
	 */
	public PayingCustomer(String cusName, String cusEmail, ArrayList<Supplement> list, long bankAcc, 
			int option, ArrayList<AssociateCustomer> cusList, long stNumber, String stName, String newSuburb, long newPostcode) {
		super(cusName, cusEmail, list, stNumber, stName, newSuburb, newPostcode);
		paymentMethod = new Payment(bankAcc, option);
		customerList = cusList;
	}
	
	/**
	 * Constructor of PayingCustomer class                          
	 * <p>
	 * This class is used to represent a all Customer class variable and its own member variables
	 * </p>
	 * Precondition - The value of cusName and cusEmail must be a String value and 
	 *                an ArrayList of supplement object. The value of address is Address class type.
	 *                Followed by a bankAcc with long type, option of int type and a list of AssociateCustomer stored in
	 *                ArrayList. <br>
	 * Postcondition - A PayingCustomer object is created with the pass in value
	 * @param  cusName Name of the customer 
	 * @param  cusEmail Customer's email address   
	 * @param  list A list of supplement  
	 * @param  bankAcc Bank account number 
	 * @param  option Type of payment method 
	 * @param  cusList A list of associate customer 
	 * @param  address An Address object of the customer
	 */
	public PayingCustomer(String cusName, String cusEmail, ArrayList<Supplement> list, long bankAcc, 
			int option, ArrayList<AssociateCustomer> cusList, Address address) {
		super(cusName, cusEmail, list, address);
		paymentMethod = new Payment(bankAcc, option);
		customerList = cusList;
	}
	
	/**
	 * Constructor of PayingCustomer class                          
	 * <p>
	 * This class is used to represent a all Customer class variable and its own member variables
	 * </p>
	 * Precondition - The value of cusName and cusEmail must be a String value and 
	 *                an ArrayList of supplement object. The value of address is Address class type.
	 *                Followed by the pay of Payment type and a list of AssociateCustomer stored in
	 *                ArrayList. <br>
	 * Postcondition - A PayingCustomer object is created with the pass in value
	 * @param  cusName Name of the customer 
	 * @param  cusEmail Customer's email address   
	 * @param  list A list of supplement  
	 * @param  pay A Payment class object
	 * @param  cusList A list of associate customer 
	 * @param  address An Address object of the customer
	 */
	public PayingCustomer(String cusName, String cusEmail, ArrayList<Supplement> list, Payment pay, 
			ArrayList<AssociateCustomer> cusList, Address address) {
		super(cusName, cusEmail, list, address);
		paymentMethod = pay;
		customerList = cusList;
	}
	
	//add associate customer to associate customer list
	/**
	 * This method is used to add new AssociateCustomer object to the associate customer list                     
	 * <p>
	 * Precondition: Take in a new AssociateCustomer class object <br>
	 * Postcondition: The passed in AssociateCustomer object is add to the customer's associate customer list
	 * </p>   
	 * @param customer AssociateCustomer class object    
	 */
	public void AddCustomer(AssociateCustomer customer) {
		customerList.add(customer);
	}
	
	//remove associate customer from associate customer list
	/**
	 * This method is used to remove an AssociateCustomer object from the associate customer list                     
	 * <p>
	 * Precondition: Take in a AssociateCustomer class object <br>
	 * Postcondition: The method will find and remove the same AssociateCustomer object from the
	 *   			  associate customer list
	 * </p>   
	 * @param customer AssociateCustomer class object    
	 */
	public void RemoveCustomer(AssociateCustomer customer) {
		
		boolean found = false;
		
		for(int idx=0; idx<customerList.size(); idx++) {
			if(customerList.get(idx)==customer) {
				customerList.remove(idx);
				System.out.println("Associate customer " + customer.GetCustomerName() + " has been removed ");
				found = true;
			}
		}
		if(found==false)
			System.out.println("Associate customer " + customer.GetCustomerName() + " not found ");
	}
	
	/**
	 * This method is used to get the Paying object of the PayingCustomer                         
	 * <p>
	 * Precondition: paymentMethod is initialised at the Constructor <br>
	 * Postcondition: The paymentMethod of the PayingCustomer object is returned.
	 * </p>   
	 * @return paymentMethod Return the PayingCustomer object's paymentMethod   
	 */
	public Payment GetPayment() {
		return paymentMethod;
	}
	
	/**
	 * This method is used to get PayingCustomer's associate customer list                   
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: The customerList is returned.
	 * </p>   
	 * @return customerList Return the PayingCustomer object's associate customer list  
	 */
	public ArrayList<AssociateCustomer> GetAssoCusList(){
		return customerList;
	}
	
	/**
	 * This method is used to set PayingCustomer's associate customer list                   
	 * <p>
	 * Precondition: Pass in an ArrayList of associate customer <br>
	 * Postcondition: The customerList is set.
	 * </p>   
	 * @param list An ArrayList of associate customer
	 */
	public void SetAssoCusList(ArrayList<AssociateCustomer> list){
		customerList=list;
	}

	/**
	 * This method is used to display the name of all associate customer of the Paying customer                       
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: The name of all associate customer in the associate customer list is returned.
	 * </p>   
	 */
	public void PrintCustomer() {
		for(int idx = 0; idx < customerList.size(); idx++) {   
			System.out.println(customerList.get(idx).GetCustomerName());
		}  
	}
	
	//Get the total supplement weekly cost including it own supplement list
	//and all the associate customer's supplements 
	/**
	 * This method is used to get PayingCustomer's total supplement weekly cost
	 * including his/her associate customer                        
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: The total weekly cost is returned.
	 * </p>   
	 * @return total Return the PayingCustomer object's total supplement weekly cost   
	 */
	public double GetWeeklyPrice() {
		
		double total = super.GetWeeklyPrice();
	
		for(int idx=0; idx<customerList.size(); idx++) {
			AssociateCustomer customer = customerList.get(idx);
			total += customer.GetWeeklyPrice();
		}
		return total;
	}
	
	/**
	 * This method is used to display the info of the paying customer                      
	 * <p>
	 * Precondition: Nil <br>
	 * Postcondition: Output of paying customer is displayed
	 * </p>    
	 */
	public void WriteOutput() {
		super.WriteOutput();
		paymentMethod.Display();
		System.out.println("Total weekly charge: $" + GetWeeklyPrice());
		System.out.println("Associated customer: ");
		System.out.println("------------------------- ");
		PrintCustomer();
	}
	
	/*
	//testing purpose
	public static void main(String[] args) {
		
		ArrayList<Supplement> list = new ArrayList<Supplement>();
		ArrayList<AssociateCustomer> cusList = new ArrayList<AssociateCustomer>();
		Address a = new Address(12345, "Ang Mo Kio Road", "AMK", 560466);
		PayingCustomer cus = new PayingCustomer("Tee Yee Kang", "yeekang@gmail.com",
				list, 990103, 0, cusList, a);
		
		Supplement s1 = new Supplement("Book1", 10);
		Supplement s2 = new Supplement("Book2", 20);
		Supplement s3 = new Supplement("Book3", 30);
		Supplement s4 = new Supplement("Book4", 40);
		cus.AddSupplement(s1);
		cus.AddSupplement(s2);
		cus.AddSupplement(s3);
		cus.AddSupplement(s4);
		//cus.RemoveSupplement(s4);
		
		cus.WriteOutput();
	}
	*/
	
}