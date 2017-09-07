

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	
	static ArrayList<Pair<String, Integer>> numList =  new ArrayList<Pair<String, Integer>>(50);
	static ArrayList<Pair<String, String>> strList = new ArrayList<Pair<String, String>>(50);
	
	
	
	
	public static void main(String [] args) throws FileNotFoundException {
		
		String str = "";
		int pointer = 0;
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < 40;i++){
			list.add(i, new  ArrayList<String>());
		}
		
		
		try{
		Scanner scan = new Scanner(System.in);
	//	File file = new File(args[1]);
		File file = new File("test2.txt");
		scan = new Scanner(file);
	
		while(scan.hasNext()){  //This prints out the file line by line, now
								// to decide what to do with it.
				str = scan.nextLine();	
				//System.out.println(str);// HMMMMMM ?
			String[] words = str.split("\\s+");
			//System.out.println(words.length);
			for(int i = 0; i < words.length; i++){
				list.get(pointer).add(i, words[i]);
			//	System.out.println(words[i]);
			}

			pointer += 1;
		}
		scan.close();
	

		
		}
		catch (Exception ex){
			//System.out.println("Error during scanning in file " +ex.toString());
		}
	//	System.out.println(list.size());
		for(int i = 0; i < list.size(); i++){
			//System.out.println("new array");
			for(int k = 0; k < list.get(i).size(); k++){
				//System.out.println(list.get(i).get(k));
			}
		} //this is for testing
		//parse through every ArrayList in List, determine what each arraylist does
		//THIS DETERMINES THE V
		//ALSO check for null pointer exceptions
		
		
		for (int i = 0; i < list.size(); i++) {

			for (int k = 1; k < list.get(i).size(); k++) {
				if (list.get(i).get(k).equals("=") && isNumeric(list.get(i).get(k + 1)) == true) {
					// if prev equals("*"
						saveVariableInt(list.get(i).get(k - 1), list.get(i).get(k + 1));
					
				} else if (list.get(i).get(k).equals("=") && isNumeric(list.get(i).get(k + 1)) == false) {
					saveVariableString(list.get(i).get(k - 1), list.get(i).get(k + 1));
				} else if(list.get(i).get(k).equals("*=")){
					//System.out.println("IN MULTIPLY METHOD");
					 multiply(list.get(i).get(k-1), list.get(i).get(k+1));
				} else if(list.get(i).get(k).equals("+=")){
					//CALL ADDITION
					addition(list.get(i).get(k-1), list.get(i).get(k+1));
				} else if(list.get(i).get(k).equals("-=")){
					//CALL SUBTRACTION
					subtraction(list.get(i).get(k-1), list.get(i).get(k+1));
				}
				//ADD FOR and NESTED FOR
				 else if(list.get(i).get(0).equals("FOR")){
				//NESTED FOR IF, ELSE maybe
					 
				String iter = list.get(i).get(1);
				int interation = Integer.parseInt(iter);
				int counter = 2;
				
					while (true) {
						if (list.get(i).get(counter).equals("-=")) {
							subtraction(list.get(i).get(counter - 1), list.get(i).get(counter + 1));
						}
						if (list.get(i).get(counter).equals("+=")) {
							addition(list.get(i).get(counter - 1), list.get(i).get(counter + 1));
						}
						if (list.get(i).get(counter).equals("*=")) {
							multiply(list.get(i).get(counter - 1), list.get(i).get(counter + 1));
						}
						if (list.get(i).get(counter).equals("ENDFOR")) {
							break; // break out of the while loop
						}
						counter++;
					}//END OF WHILE
				}//END OF elseif for "FOR"
				 else if(list.get(i).get(0).equals("PRINT")){
					 //CALL PRINT FUNCTION; HOW DOES THE PRINT FUNCTION WORK
					 printScreen(list.get(i).get(1));
					 System.out.println("printscreen called for " + list.get(i).get(1));
				 }
			}
			
		}
		for(int c = 0; c < numList.size(); c++){
			System.out.println(" var is = "+numList.get(c).first +", value is = "+numList.get(c).second);
		}
	}
	
	//Called when "=" appears without +,-,*
	public static void saveVariableString(String var, String str){
		// Save to ArrayList of String, String 
		for (int i = 0; i < numList.size(); i++) {
			if (numList.get(i).first != null && numList.get(i).first.equals(var)) {
				numList.set(i, new Pair<String, Integer>(null, null));
			}
		}

		// Base Case
		if (strList.size() < 1) {
			strList.add(0, new Pair<String, String>(var, str));
		} else if (strList.size() == 1) {
			if (strList.get(0).first.equals(var)) {// If first thing in list is									// about to add
				strList.add(0, new Pair<String, String>(var, str));
			} else {
				strList.add(1, new Pair<String, String>(var, str));
			}
		}
		else {
			for (int n = 0; n < numList.size(); n++) {
				//System.out.println("n value is " + n);
				if (numList.get(n).first.equals(var)) { // this check is if a
														// var in the list is
														// equal to given var
					strList.set(n, new Pair<String, String>(var, str));
					break;
				} else {// Find next null
					strList.add(strList.size(), new Pair<String, String>(var, str));
					break; // break out for loop
				}

			}
		}
		//Save to ArrayList of String, int
	}  //end of saveVariableString()
	
	// Called when "=" appears and the str after it is not an integer
	public static void saveVariableInt(String var, String str) {
		int num = Integer.parseInt(str);
		boolean notin = false;
		// Save to ArrayList of String, String
		for (int i = 0; i < strList.size(); i++) {
			if (strList.get(i) != null && strList.get(i).first.equals(var)) {
				strList.set(i, new Pair<String, String>(null, null));
			}
		}

		// Base Case
		if (numList.size() < 1) {
			numList.add(0, new Pair<String, Integer>(var, num));
		} else if (numList.size() == 1) {
			if (numList.get(0).first.equals(var)) {// If first thing in list is
													// equal to what we are
													// about to add
				numList.set(0, new Pair<String, Integer>(var, num));
			} else {;
				numList.add(1, new Pair<String, Integer>(var, num));
			}
		}

		else {
			for (int n = 0; n < numList.size(); n++) {
				if (numList.get(n).first.equals(var)) { // this check is if a
														// var in the list is
														// equal to given var
					notin = true;
					numList.set(n, new Pair<String, Integer>(var, num));
					break;
				} else {// Find next null
					//nEED TO FIX INDEX Here, is index going to be n? or numList.size()
					//TO DO: 
					//NEED TO LOOP THROUGH ARRAY TO FINE IF VALUE IS IN THERE, if is, replace it, if not add to end
					// BELOW COULD BE ADDED BACK
					 //numList.add(n, new Pair<String, Integer>(var, num));
					//break; // break out for loop
				}
			}
			if(notin == false){
				numList.add(numList.size(), new Pair<String, Integer>(var, num));
			}
		}
	}// end of saveVariableInt
	
	public static boolean isNumeric(String str)//Check if str after = is num or int
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public static void multiply(String str1, String str2){
		int num1 = 0;
		int save = 0;
		int num2 = 0;
		int product = 0;
		
		for(int i = 0; i < numList.size(); i++){
			if(numList.get(i).first.equals(str1)){
				num1 = numList.get(i).second;
				save = i;
			}
		}
		
		if(isNumeric(str2) == true){
			num2 = Integer.parseInt(str2);
			product = num1* num2;
			numList.set(save,  new Pair<String, Integer>(str1, product));
		}
		else{
			for(int k = 0; k < numList.size(); k++){
				if(numList.get(k).first.equals(str2)){
					num2 = numList.get(k).second;
				    product = num1 * num2;
					numList.set(save, new Pair<String, Integer>(str2, product));
				}
			}
		}
	} //End of multiply method
	public static void addition(String str1, String str2){
		int num1 = 0;
		int save = 0;
		int num2 = 0;
		int sum = 0;
		
		for(int i = 0; i < numList.size(); i++){
			if(numList.get(i).first.equals(str1)){
				num1 = numList.get(i).second;
				save = i;
			}
		}
		
		if(isNumeric(str2) == true){
			num2 = Integer.parseInt(str2);
			sum = num1 + num2;
			numList.set(save,  new Pair<String, Integer>(str1, sum));
		}
		else{
			for(int k = 0; k < numList.size(); k++){
				if(numList.get(k).first.equals(str2)){
					num2 = numList.get(k).second;
				    sum = num1 + num2;
					numList.set(save, new Pair<String, Integer>(str2, sum));
				}
			}
		}
	} //End of addition method
	public static void subtraction(String str1, String str2){
		int num1 = 0;
		int save = 0;
		int num2 = 0;
		int difference = 0;
		
		for(int i = 0; i < numList.size(); i++){
			if(numList.get(i).first.equals(str1)){
				num1 = numList.get(i).second;
				save = i;
			}
		}
		
		if(isNumeric(str2) == true){
			num2 = Integer.parseInt(str2);
			difference = num1 - num2;
			numList.set(save,  new Pair<String, Integer>(str1, difference));
		}
		else{
			for(int k = 0; k < numList.size(); k++){
				if(numList.get(k).first.equals(str2)){
					num2 = numList.get(k).second;
				    difference = num1 - num2;
					numList.set(save, new Pair<String, Integer>(str2, difference));
				}
			}
		}
	} //End of subtraction method
	public static void printScreen(String str){
		int ret = 0;
		String ret2 = "";
			//check numList
			for(int i = 0; i < numList.size(); i++){
				if(numList.get(i).first != null && numList.get(i).first.equals(str)){
					System.out.println("in print method for numeric");
					ret = numList.get(i).second;
					System.out.println(str+"="+ret);
					i = numList.size();
				}
				}
			
			for(int j = 0; j < strList.size(); j++){
				if(strList.get(j).first != null && strList.get(j).first.equals(str)){
					System.out.println("in print method for non numeric");
					ret2 = strList.get(j).second;
					System.out.println(str+"="+ret2);
				}
			}
		//}	
	}
}