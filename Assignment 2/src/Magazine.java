
//Title     : FT MUR T122 ICT373 B – Assignment 2 (Magazine class)
//Author    : Tee Yee Kang
//Date      : 27/Mar/2022
//File Name : FTB-34315323-Assignment 02
//Purpose  	: This is the magazine class. The name of the magazine,
//       	  weekly cost and a list of supplement of the magazine are be the member variables of this
//            class. The purpose of this class is to represent the magazine
//            and provide appropriate method for user to control and modify the magazine.


import java.util.ArrayList;

/**
 * @author      TeeYeeKang    yeekang88@gmail.com
 * @version     1.1          
 */
public class Magazine implements java.io.Serializable{
	
	//member variables
	/**
	 * The String name
	 */
	private String name;
	
	/**
	 * The double weeklyCost
	 */
	private double weeklyCost;
	
	/**
	 * The ArrayList suppList
	 */
	private ArrayList<Supplement> suppList;
	
	//default constructor
	/**
	 * Default Constructor of Magazine class                          
	 * <p>
	 * This class is used to represent the default value of String name, double weeklyCost 
	 * and a list of supplement 
	 * </p>
	 * Precondition - Nil  <br>
	 * Postcondition - A Magazine object is created with the default value of name, weeklyCost and supplement list
	 */
	public Magazine() {
		name = "N/A";
		weeklyCost = 0;
		suppList = new ArrayList<Supplement>();
	}
	
	//constructor with parameters
	/**
	 * Constructor of Magazine class                          
	 * <p>
	 * This method is used to represent a String of name, double weeklyCost and supplement list
	 * </p>
	 * Precondition - The value of name and weeklyCost must be a String and positive double value respectively and cannot be null.<br>
	 *                Follow by an ArrayList of supplement
	 * Postcondition - A Magazine object is created with the value of name, weeklyCost and a list of supplement
	 * @param  names Name of Magazine object
	 * @param  cost Weekly cost of the Magazine object   
	 * @param  list Supplement list of the Magazine object    
	 */
	public Magazine(String names, double cost, ArrayList<Supplement> list) {
		name = names;
		weeklyCost = cost;
		suppList = list;
	}
	
	public Magazine(Magazine newMagazine) {
		name = newMagazine.GetName();
		weeklyCost = newMagazine.GetTotalMagazineCost();
		suppList = newMagazine.GetMazSupplement();
	}
	
	/**
	 * This method is used to set the name of the Magazine class object                        
	 * <p>
	 * Precondition: Take in a name of Magazine object with String value and cannot be null <br>
	 * Postcondition: The name of the Magazine object is set.
	 * </p>   
	 * @param newName Name of Magazine object      
	 */
	public void SetName(String newName) {
		name = newName;
	}
	
	/**
	 * This method is used to get the name of the Magazine object                        
	 * <p>
	 * Precondition: Name is initialised at the Constructor <br>
	 * Postcondition: The name of the Magazine object is returned.
	 * </p>   
	 * @return name Return the Magazine object's name      
	 */
	public String GetName() {
		return name;
	}
	
	/**
	 * This method is used to get the weekly cost of the Magazine object                        
	 * <p>
	 * Precondition: weeklyCost is initialised at the Constructor <br>
	 * Postcondition: The weekly cost of the Magazine object is returned.
	 * </p>   
	 * @return weeklyCost Return the Magazine object's weekly cost      
	 */
	public double GetWeeklyCost() {
		return weeklyCost;
	}
	
	/**
	 * This method is used to set the weekly cost of the Magazine class object                        
	 * <p>
	 * Precondition: Take in a new cost of Magazine object with double value and cannot be null <br>
	 * Postcondition: The weekly cost of the Magazine object is set.
	 * </p>   
	 * @param newCost Weekly cost of Magazine object      
	 */
	public void SetWeeklyCost(double newCost) {
		weeklyCost = newCost;
	}
	
	//magazine's weekly cost + supplements' weekly cost
	/**
	 * This method is used to calculate and return the total weekly of the Magazine object  
	 * including the weekly cost of magazine and weekly cost of all its supplements                      
	 * <p>
	 * Precondition: Nil. <br>
	 * Postcondition: The total weekly cost is returned.
	 * </p>   
	 * @return finalCost Return the Magazine object's total weekly cost     
	 */
	public double GetTotalMagazineCost() {
		double total = 0;
		
		for(int idx=0; idx<suppList.size(); idx++) {
			total+=suppList.get(idx).GetWeeklyCost();
		}
		double finalCost = total+weeklyCost;
		return finalCost;
	}
	
	/**
	 * This method is used to set the supplement list the Magazine class object                        
	 * <p>
	 * Precondition: Take in a new ArrayList of supplement object <br>
	 * Postcondition: The supplement list of the Magazine object is set.
	 * </p>   
	 * @param newList New supplement list of Magazine object      
	 */
	public void SetSupplimentList(ArrayList<Supplement> newList) {
		suppList = newList;
	}
	
	/**
	 * This method is used to return the supplement list of Magazine object                      
	 * <p>
	 * Precondition: Nil. <br>
	 * Postcondition: The supplement list is returned
	 * </p>   
	 * @return suppList Return the supplement list of Magazine object     
	 */
	public ArrayList<Supplement> GetMazSupplement(){
		return suppList;
	}
	
	/**
	 * This method is used to add a new supplement into the Magazine object                      
	 * <p>
	 * Precondition: Take in a new supplement with user define Supplement class type. <br>
	 * Postcondition: The supplement is added to the Magazine's supplement list.
	 * </p>   
	 * @param supplement New supplement object   
	 */
	public void AddSupplement(Supplement supplement) {
		suppList.add(supplement);
	} 
	
	/**
	* This method is to display the information of the Magazine object, including name
	* weekly cost, total cost and corresponding supplements 
	** <p>
	* Precondition - Nil<br>
	* Postcondition - Display output of Magazine object.
	* </p>
	*/
	public void WriteOutput() {
		System.out.println("Magazine name: " + name);
		System.out.println("Magazine cost: " + weeklyCost);
		System.out.println("Total weekly cost - including all supplements: " + GetTotalMagazineCost());
		System.out.println("Suppliments: ");
		System.out.println("---------------------------");
		
		for(int idx=0; idx<suppList.size(); idx++) {
			suppList.get(idx).Display();
		}
	}
	
	public String toString() {
		
		String output = name + "," + weeklyCost + "\n";
		for(int idx=0; idx<suppList.size(); idx++) {
			output += suppList.get(idx).toString() + "\n";
		}
		return output;
	}
}
