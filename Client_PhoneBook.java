/*
 * Application features:
 * 1.check the user to perform another task after each mistake or finished task.
 * 2.has the ability to create, see and edit contact. Name is the PK
 */

package assign;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Client_PhoneBook {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		try {
			boolean again = false;
			do {	//while loop to check if the user want to perform another task
			DB_Access db = new DB_Access();
			System.out.println("Please choose what would you like to do today\n"
					+ "select 1 to create contact\n"
					+ "select 2 to search for a contact\n"
					+ "select 3 to edit a contact");
			String selection = s.nextLine();
			if(!selection.equals("1") && !selection.equals("2") && !selection.equals("3")){		//validating user input
				System.out.println("Oops it must be either 1, 2 or 3 only!");
				again = again(s);
			}
			
			switch(selection){
			
			case "1":	//create a new contact.
				System.out.println("Creating a new contact");
				System.out.println("Enter phone number:");
				String phoneNumber = s.nextLine();
				System.out.println("Enter name:");
				String name = s.nextLine();
				System.out.println("Enter address:");
				String address = s.nextLine();
				if(over_60(name) || over_60(address)){		//validating user input
					System.out.println("Oops name & address must NOT be over 60 char!\n\n\n\n");
					again = again(s);
					break;
				}
				if(!is_A_Number(phoneNumber)){		//validating user input
					System.out.println("Oops number field must contain ONLY numbers!\n\n\n\n");
					again = again(s);
					break;
				}
				Data data = new Data(phoneNumber, name, address);
				boolean stored = db.storeData(data);
				if(stored){
					System.out.println("Contact has been added successflly");
				}else{
					System.out.println("Contact has NOT been added successflly!");
				}
				again = again(s);
				break;
				
				
				
			case "2":	//search for a name.
				System.out.println("Search for a name");
				System.out.println("Enter the name you want to search:");
				String person = s.nextLine();
				ArrayList<Data> list = db.searchData(person);
				display(list);
				if(list.size() == 0){
					System.out.println("No information has been found for this name!");
				}
				again = again(s);
				break;
				
				
				
				
			case "3":	//edit a name.
				System.out.println("Update a name");
				System.out.println("Enter name to edit:");
				String nameToEdit = s.nextLine();
				Data dataR = db.searchName(nameToEdit);
				if(dataR != null){
					System.out.println("Name has been found. What would you like to eidt in this contact\n"
							+ "select 1 to change number("+ dataR.getNumber() +")\n"
							+ "select 2 to change address("+ dataR.getAddress() +")");
					String answer = s.nextLine();
					if(!answer.equals("1") && !answer.equals("2")){
						again = again(s);
						break;
					}
					
					switch(answer){		//check what the user want to change.
					
					case "1":		//change number.
						System.out.println("Enter the new number");
						// add loop to prevent String input
						String newNumber = s.nextLine();
						if(!is_A_Number(newNumber)){
							System.out.println("Oops number field must contain ONLY numbers!\n");
							again = true;
							break;
						}else{
							again = false;
						}
						dataR.setNumber(newNumber);
						break;
						
					case "2":		//change address.
						System.out.println("Enter a new address");
						String newAddress = s.nextLine();
						if(over_60(newAddress)){
							System.out.println("Oops name & address must NOT be over 60 char!\n");
							again = true;
							break;
						}else{
							again = false;
						}
						dataR.setAddress(newAddress);
						break;
					}
					
					boolean changeMade = db.updateData(dataR);		//updating data after all the chnges have been done.
					
					if(changeMade && !again){
						System.out.println("Contact has been edited");
					}else{
						System.out.println("Contact has NOT been edited!");
					}
					
				}else{	//this else for if(dataR != null)
					System.out.println("Name can not be found");
				}
				again = again(s);	//prompting the suer to do another task.
				break;
			
			}
			} while (again);	//the end of the while statement for perfoming another task.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	//A method to check whether a field contains numbers only or not
	public static boolean is_A_Number(String number){
		boolean success = true;
		if(!number.isEmpty()){
			for (int i = 0; i < number.length(); i++){
				if(!Character.isDigit(number.charAt(i))){
					success = false;
				}
			}
		}else{
			success = false;
		}
		return success;
	}
	
	
	//A method to check if string over 60 char. 
	public static boolean over_60(String string){
		boolean success = false;
		if(string.length() > 60){
			success = true;
		}
		return success;
	}
	
	
	
	//A method to display the final results.
	public static void display(ArrayList<Data> list){
		
		for (Data data : list){
			System.out.println("Name: " + data.getName());
			System.out.println("Number: " + data.getNumber());
			System.out.println("Address: " + data.getAddress());
		}
		
		
	}
	
	
	//A method to promote the user to perform other tasks.
	public static boolean again (Scanner s){
		boolean success = false;	//to be returned
		boolean again= false;	//for the while inside this method 
		do{
		System.out.println("\n\n\nWould you like to preform another task? \nY --> for yes\nN --> for no");
		String anotherTask = s.nextLine();
		if (!anotherTask.equals("Y") && !anotherTask.equals("N")){
			again = true;
			System.out.println("Oops you have made a mistake!");
		}else{
			if (anotherTask.equals("Y")){
				success = true;
			}else{
				System.out.println("Thank you for using the application. Good by :)");
				again = false;
			}
		}
		} while (again);
		
		return success;
	}
	
}
