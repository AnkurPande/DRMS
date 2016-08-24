package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import server.LibraryServerInterface;

/**
 * @author Ankurp
 *
 */
public class AdminClient {
	private String username =null, password = null, instituteName= null;
	private int numOfDays =0, choice =0;
	private LibraryServerInterface serverProxy = null;
	static AdminClient admin= null;
	
	/**
	 * @return user name 
	 */ 
	public String getUsername() {
		return username;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return instituteName
	 */
	public String getInstituteName() {
		return instituteName;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param instituteName
	 */
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	
	/**
	 * @param sc
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public void displayServersList(Scanner sc) throws MalformedURLException, RemoteException, NotBoundException{
		boolean valid =false;
		do{
			System.out.println("\n Welcome to the Online Library System " );
			System.out.println("\n 1. Concordia Library " );
			System.out.println("\n 2. McGill Library " );
			System.out.println("\n 3. UQAM Library " );
			System.out.println("\n 4. Exit" );
			System.out.println("\n Enter Your Choice: " );
			choice =sc.nextInt();
			
			switch(choice){
				case 1: serverProxy = (LibraryServerInterface) Naming.lookup("rmi://localhost:444/Concordia");
						this.setInstituteName("CONCORDIA");
						valid =true; break;
				case 2: serverProxy = (LibraryServerInterface) Naming.lookup("rmi://localhost:444/McGill");
						this.setInstituteName("MCGILL"); 
						valid =true; break;
				case 3: serverProxy = (LibraryServerInterface) Naming.lookup("rmi://localhost:444/UQAM");
						this.setInstituteName("UQAM");
						valid =true; break;
				case 4: System.out.println("Exited"); 
						System.exit(1);	valid =true; break;
				default : System.out.println("Choice should be in range of 1-4");
						valid = false; break;
			}
			
		}while(!valid);
	}
	
	/**
	 * @param sc
	 * @throws RemoteException
	 */
	public void getNonReturners(Scanner sc) throws RemoteException{
			
	      
	      String username,password;
	      int days;
	      do
	      {
	         //gets user name from the console
	         System.out.println("UserName: ");
	         username = sc.next();
	              
	         //gets password from the console
	         System.out.println("Password: ");
	         password = sc.next();
	         
	         System.out.println("No Of Days past loan date: ");
	         days = sc.nextInt();
	         
	         //inputs validation error messages
	         if(username.length() < 6 && username.length() > 0) System.out.println("The user name is too short!!!");
	         if(username.length() > 15 && username.length() > 0) System.out.println("The user name is too long!!!");
	         if(password.length() < 8) System.out.println("The password is too short!!!");
	         if(username.length() == 0 && password.length() == 0) System.out.println("The fields are empty!!!");
	         if(username.length() == 0) System.out.println("The user name field is empty!!!");
	         if(password.length() == 0) System.out.println("The password field is empty!!!");
	         if(days<0) System.out.println("Number of days should be non negative!!!");
	      }while(username.length() < 6 || username.length() > 15 || password.length() < 8 || days<0 );
	     
	      	  this.setUsername(username);
	      	  this.setPassword(password);
	      	  this.numOfDays =days;
	      	  System.out.println(serverProxy.getNonReturners(this.username, this.password, this.instituteName, this.numOfDays));
	 }
	
	/**
	 * @param sc
	 * @throws RemoteException
	 */
	public void addBooks(Scanner sc) throws RemoteException{
		int bookID, numOfCopies; 
		String bookName, authorName;
		System.out.println("\nEnter Book Name: " );
		bookName =sc.next();
		System.out.println("\nEnter Book Author Name: " );
		authorName =sc.next();
		System.out.println("\nEnter No of copies to add: " );
		numOfCopies =sc.nextInt();
		System.out.println("\nEnter Unique Book ID: " );
		bookID =sc.nextInt();
		serverProxy.addBooks(bookID, bookName, authorName, numOfCopies);
	}
	
	/**
	 * @throws RemoteException
	 */
	public void listBooks() throws RemoteException{
		serverProxy.listBook();
	}
	
	/**
	 * @param args
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void main(String args[]) throws MalformedURLException, RemoteException, NotBoundException{
		
		admin = new AdminClient();
		Scanner sc = new Scanner(System.in);
		int choice =0;
		
		admin.displayServersList(sc);
		boolean valid = true;
		while(valid){
			System.out.println("\n Please select the operation. Enter your choice:  " );
			System.out.println("\n 1. Get Non Returners " );
			System.out.println("\n 2. Add Books To Library " );
			System.out.println("\n 3. list books at server" );
			System.out.println("\n 4. Exit" );
			System.out.println("\n Enter Your Choice: " );
			choice =sc.nextInt();
			
			switch(choice){
				case 1: admin.getNonReturners(sc);
						valid = true;break;
				case 2:	admin.addBooks(sc);
						valid = true;break;
				case 3: admin.listBooks();
						valid = true;break;
				case 4: System.out.println("Exited"); 
						valid = false;System.exit(1); break;
				default:System.out.println("Choice should be in range of 1-3");
						valid =true;break;
			}			
		}
		
		

	}
	
}
