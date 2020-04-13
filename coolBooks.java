import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class coolBooks {
	
	//************* Creating an object and setting some variables ************* 
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //An object that takes in user input	
	Connection conn; //the connection variable 
	Statement stmt;  //Statement to call sql queries 
	String in;       //String to gather input 

	int id;          //the 4 types of variables that can be used in our database, each describing a feature of a book
	String title;    
	String author;
	int qty;

	//************* our main method ************* 

	public static void main(String[] args) throws IOException, SQLException {
		
	boolean exit = false; 
	coolBooks action = new coolBooks(); //declaring "action" as a Cool Book's object 
		
	System.out.println("Welcome to Cool Books!\n");   //The heading of the menu

	while(!exit) { //This program will run until exit is selected by the user
	 
		action.printMenu();  	
		action.getConnection(); //calling getConnection in order to connect to the JDBC 
		action.in = (action.br.readLine());  //request input from the user
		int choice = 0;
	 
	 try {
		 choice = Integer.parseInt(action.in);  //this try catch picks up if a non int is entered 
	 }
	 catch(NumberFormatException e) {
		 choice = 6; //make choice = default if a non int is entered 
	 }
	 
	 switch(choice) { //The user's choice is added as an input to a switch statement
	 
	  case 1: //requests the details of the book to be added and adds them to the Database

	   action.addBook();
	   break;

	  case 2:  //requests the ID of the book to be updated and allows the user to update it as required 

	   action.updateBook();
	   break;
	   
	  case 3: // Requests input and deletes the required book from the database

	   action.deleteBook(); 
	   break;
		  
	  case 4: // Requests input and uses a while statement to display the requested details 
		  
	   action.searchBook();
	   break;
		  
	   
	  case 0: // This closes the program if requested 
		 
	    exit = true;
	    System.out.println("\nThanks for coming!");
	    break;
		
	  default: // A default error response when the user inputs an int outside of (0-4)
		 
	    System.out.println("\nPlease enter one of the appropropriate options:\n");	
		break;

	    }
	 
	  action.stmt.close(); //closing the jdbc once the program has been completed
	  action.conn.close();
	 
	   }

	  }

	//************* The method for each executable action *************

	void getConnection() throws SQLException {
		
		conn = DriverManager.getConnection("URL","name","password"); //these details are updated according to the database being accessed
		stmt = conn.createStatement(); // Connecting to the JDBC 
		
	}

	void printMenu() {
		
		System.out.println("1. Add book"); //Printing out the user's options
		System.out.println("2. Update book");
		System.out.println("3. Delete book");
		System.out.println("4. Search books");
		System.out.println("0. Exit");
		System.out.println("\nPlease select your choice:");
		
	}

	void addBook() throws IOException, SQLException {	
		
		System.out.println("\nPlease enter the details of the book you would like to add: \n");
		
			System.out.println("ID:");  //The relevant details are requested and added to the database
		    id = Integer.parseInt(br.readLine());

		    System.out.println("Title");
		    title = (br.readLine());

		    System.out.println("Author");
		    author = (br.readLine());

		    System.out.println("Quantity");
		    qty = Integer.parseInt(br.readLine());

		    try {	//This try statement ensures no duplicate ID can be added 
		      stmt.executeUpdate("INSERT INTO books VALUES ('"+id+"','"+title+"','"+author+"','"+qty+"')");
		      System.out.println("\n"+title+" has been added\n");
		      }
		    
		      catch (java.sql.SQLIntegrityConstraintViolationException e) {
		      System.out.println("\nA book with that ID already exists in this Database\n");
		      }
		     	
	}

	void updateBook() throws IOException, SQLException {
		
		System.out.println("Book ID:");  //searches for the required ID and adds the new input to the database
		   
		  	id = Integer.parseInt(br.readLine());

		    System.out.println("New title:");
		    title = (br.readLine());

		    System.out.println("New author:");
		    author = (br.readLine());

		    System.out.println("New quantity");
		    qty = Integer.parseInt(br.readLine());
		    
		    stmt.executeUpdate("update books set title='"+title+"', author='"+author+"', qty='"+qty+"' where id= '"+id+"'");
		    System.out.println("\nThis book has been updated\n");
		    
	}

	void deleteBook() throws IOException, SQLException {	
		
		System.out.println("Enter the ID of the book to be deleted: "); //searchs for the ID and removes the book from the database
	    id = Integer.parseInt(br.readLine());
	 
	    stmt.executeUpdate("delete from books where id='"+id+"'");
	    System.out.println("\nThis book has been deleted \n");	
	}
		
	void searchBook() throws IOException, SQLException{
		
		System.out.println("Please enter the ID, title or author to be searched:"); //searches for and displays the book
		    String input =(br.readLine());
		    ResultSet rs;
		    try{
		    id = Integer.parseInt(input);
		    rs = stmt.executeQuery("select * from books where id= '"+id+"'"); //this try catch allows you to search for more than just ID
		    }
		    catch(NumberFormatException e) {
		    rs = stmt.executeQuery("select * from books where title= '"+input+"' or author= '"+input+"' "); 	
		    }
		    
		    while(rs.next()) {
		    	
		      System.out.println("\n");	
		      System.out.println(id =rs.getInt(1));
		      System.out.println(title = rs.getString(2));
		      System.out.println(author = rs.getString(3));
		      System.out.println(qty = rs.getInt(4));
		      System.out.println("\n");
		      }

		    rs.close();
		
		} 	
}
