
//Title     : FT MUR T122 ICT373 B – Assignment 2 (Supplement class)
//Author    : Tee Yee Kang
//Date      : 27/Mar/2022
//File Name : FTB-34315323-Assignment 02
//Purpose  	: This is the supplement class. The name of the supplement
//            and weekly cost of the supplement are the member variables of this
//            class. The purpose of this class is to represent the magazine's
//            supplements and the supplement that customer interested in. 


/**
 * @author      TeeYeeKang    yeekang88@gmail.com
 * @version     1.1          
 */

public class Supplement implements java.io.Serializable {
	
	//member variables
	/**
	 * The String name
	 */
	private String name;
	
	/**
	 * The double weeklyCost
	 */
	private double weeklyCost;
	
	//default constructor
	/**
	 * Default Constructor of Supplement class                          
	 * <p>
	 * This class is used to represent the default value of String name and double weeklyCost 
	 * </p>
	 * Precondition - Nil  <br>
	 * Postcondition - A Supplement object is created with the default value of name and weeklyCost   
	 */
	public Supplement() {
		name = "N/A";
		weeklyCost = 0;
	}
	
	//constructor with parameters
	/**
	 * Constructor of Supplement class                          
	 * <p>
	 * This method is used to represent a String of name and double weeklyCost
	 * </p>
	 * Precondition - The value of name and weeklyCost must be a String and positive double value respectively and cannot be null.<br>
	 * Postcondition - A Supplement object is created with the value of name and weeklyCost
	 * @param  names Name of Supplement object
	 * @param  cost Weekly cost of the Supplement object      
	 */
	public Supplement(String names, double cost) {
		name = names;
		weeklyCost = cost;
	}
	
	/**
	 * This method is used to set the name of the Supplement object                        
	 * <p>
	 * Precondition: Take in a new name of Supplement object with String value and cannot be null <br>
	 * Postcondition: The name of the Supplement object is set.
	 * </p>   
	 * @param newName Name of Supplement object      
	 */
	public void SetName(String newName) {
		name = newName;
	}
	
	/**
	 * This method is used to get the name of the Supplement object                        
	 * <p>
	 * Precondition: Name is initialised at the Constructor <br>
	 * Postcondition: The name of the Supplement object is returned.
	 * </p>   
	 * @return name Return the Supplement object's name      
	 */
	public String GetName() {
		return name;
	}
	
	/**
	 * This method is used to set the weekly cost of the Supplement object                        
	 * <p>
	 * Precondition: Take in the cost of Supplement object with double value and cannot be null <br>
	 * Postcondition: The weeklyCost of the Supplement object is set.
	 * </p>   
	 * @param newCost Weekly cost of Supplement object      
	 */
	public void SetWeeklyCost(double newCost) {
		weeklyCost = newCost;
	}

	/** 
	 * This method is used to get the weekly cost of the Supplement object                        
	 * <p>
	 * Precondition: Cost is initialised at the Constructor <br>
	 * Postcondition: The weekly cost of the Supplement object is returned.
	 * </p>   
	 * @return weeklyCost Return the Supplement object's weekly cost      
	 */
	public double GetWeeklyCost() {
		return weeklyCost;
	}
	
	/**
	* This method is to display the name and weekly cost of Supplement object     
	** <p>
	* Precondition - Nil<br>
	* Postcondition - Display output of Supplement object.
	* </p>
	*/
	public void Display() {
		System.out.println("Supplement name: " + name);
		System.out.println("Weekly cost: " + weeklyCost);
		System.out.println();
	}
	
	public String toString() {
		String output = name + "," + weeklyCost;
		return output;
	}
}
