
//Title     : FT MUR T122 ICT373 B – Assignment 2 (Payment class)
//Author    : Tee Yee Kang
//Date      : 27/Mar/2022
//File Name : FTB-34315323-Assignment 02
//Purpose  	: This is the Payment class. Member variable contains a bank account 
//            number and the type of payment. The purpose of this class is to represent the 
//            payment method of the customer. 


enum PaymentMethod{
	DEBITCARD, 
	CREDITCARD
}

/**
 * @author      TeeYeeKang    yeekang88@gmail.com
 * @version     1.1          
 */
public class Payment implements java.io.Serializable{
	
	//member variable
	/**
	 * The long bankAcc 
	 */
	private long bankAcc;
	
	/**
	 * The PaymentMethod pay
	 */
	private PaymentMethod pay;
	
	//default constructor
	/**
	 * Default Constructor of Payment class                          
	 * <p>
	 * This class is used to represent the default value of long bankAcc and PaymentMethod pay 
	 * </p>
	 * Precondition - Nil  <br>
	 * Postcondition - A Payment object is created with the default value of bankAcc and pay   
	 */
	public Payment() { 
		bankAcc = 0;
		pay = PaymentMethod.DEBITCARD; //by default is debit 
		
	}
	
	//constructor
	//option 0 = debitcard  ;  option 1 = creditcard
	/**
	 * Constructor of Payment class                          
	 * <p>
	 * This method is used to represent a long of bankAcc and int option
	 * </p>
	 * Precondition - The value of bank and option must be a long value and int type respectively and cannot be null.<br>
	 * Postcondition - A Payment object is created with the value of bank and option
	 * @param  bank Bank account number of Payment object
	 * @param  option Option of the payment type     
	 */
	public Payment(long bank, int option) {
		bankAcc = bank; 
		
		if(option==0)
			pay = PaymentMethod.DEBITCARD;
		else if(option==1)
			pay = PaymentMethod.CREDITCARD;	
	}
	
	/**
	 * This method is used to set the bankAcc of the Payment object                        
	 * <p>
	 * Precondition: Take in a new account number with long value and cannot be null <br>
	 * Postcondition: The bankAcc of the Payment object is set.
	 * </p>   
	 * @param accNumber Bank account of Payment object      
	 */
	public void SetBankAcc(long accNumber) {
		bankAcc = accNumber;
	}
	
	/**
	 * This method is used to get the bankAcc of the Payment object                        
	 * <p>
	 * Precondition: bankAcc is initialised at the Constructor <br>
	 * Postcondition: The bankAcc of the Payment object is returned.
	 * </p>   
	 * @return bankAcc Return the Payment object's bank account number      
	 */
	public long GetBankAcc() {
		return bankAcc;
	}
	
	/**
	 * This method is used to get the payment type of Payment class object                       
	 * <p>
	 * Precondition: pay is initialised at the Constructor <br>
	 * Postcondition: Return a description of payment type in String value
	 * </p>   
	 * @return type Return the Payment object's payment method type    
	 */
	public String GetBankAccType() {
		
		String type = null;
		
		switch(pay) {
		 	case DEBITCARD:
		 		type = "Direct debit user";
		 		break;
		    case CREDITCARD:
		    	type = "Credit card user";
		    	break;
		}
		return type;
	}
	
	/**
	 * This method is used to set the type of the Payment object                        
	 * <p>
	 * Precondition: Take in a option with int value and cannot be null <br>
	 * Postcondition: The bank type of the Payment object is set.
	 * </p>   
	 * @param option Bank account's payment type    
	 */
	public void SetBankType(int option) {
		if(option==0) {
			pay = PaymentMethod.DEBITCARD;
		}else if(option==1) {
			pay = PaymentMethod.CREDITCARD;
		}else {
			System.out.println("Invalid Input");
		}
	}
	
	/**
	* This method is to display the information of Payment object     
	** <p>
	* Precondition - Nil<br>
	* Postcondition - Display output of Payment object.
	* </p>
	*/
	public void Display() {
		System.out.println("Bank Acc Num: " + bankAcc);
		System.out.println("Payment method: " + GetBankAccType());
	}
	
	/*
	//testing purpose
	public static void main(String[] args) {
		
		Payment p1 = new Payment();
		p1.SetBankAcc(112233);
		p1.Display();
		
		Payment p2 = new Payment(990103, 1);
		p2.Display();	
	}
	*/

}
