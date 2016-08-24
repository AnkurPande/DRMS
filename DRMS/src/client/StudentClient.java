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
public class StudentClient {

	private LibraryServerInterface serverProxy;
	private String name =null, password = null, email = null, address = null,institutionName = null,	bookName =null, authorName =null;
	private int studentId = 0, fines =0;
	private int choice =0;
	static StudentClient student = null;
	
	public void showOptions(Scanner sc) {
		boolean valid = true;
		System.out.println("Welcome to DRMS System. The available options are:-");
		System.out.println("1. Create Account \n 2. Reserve Book.\n 3. Return Book.\n 4. Pay Fines\n 5. Exit");
		System.out.println("Please Enter Your Choice: ");
		choice = sc.nextInt();
		
		while(valid){
			switch(choice){
				case 1: student.createAccount(sc);
						valid = false; break;
				case 2:	student.reserveBook(sc); 
						valid = false; break;
				case 3: student.returnBook(sc);
						valid = false; break;
				case 4: student.payFines(sc);
						valid = false;break; 	
				case 5: System.out.println("Exited"); 
						System.exit(1);
						break; 	
				default:System.out.println("\nInvalid Choice. Please enter in range 1-5.");
						break;
			}
		}
	}
	
	
	/**
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public StudentClient(int choice) throws MalformedURLException, RemoteException, NotBoundException{
						
		switch(choice){
			case 1: serverProxy = (LibraryServerInterface) Naming.lookup("rmi://localhost:444/Concordia");
					break;
			case 2: serverProxy = (LibraryServerInterface) Naming.lookup("rmi://localhost:444/McGill");
					break;
			case 3: serverProxy = (LibraryServerInterface) Naming.lookup("rmi://localhost:444/UQAM");
					break;
			case 4: System.out.println("Exited"); 
					System.exit(1);
					break;
		}
	}
	
	/**
	 * Invoke server method and create account for a new user.
	 */
	public void createAccount(Scanner sc) {
		System.out.println("Enter Student Id number: "); 
		studentId = sc.nextInt();
		System.out.println("Enter name: "); 
		name = sc.next();
		System.out.println("Enter password: ");
		password = sc.next();
		System.out.println("Enter address: ");
		address = sc.next();
		System.out.println("Enter email: ");
		email = sc.next();
		System.out.println("Enter name of institute: ");
		institutionName = sc.next();
		
		try {
			if(serverProxy.createAccount(studentId, name, password, address, email, institutionName)){
				System.out.println("Account Created Successfully. ");
			}
			else{System.out.println(" Failed!! ");}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		student.showOptions(sc);
	}
	
	/**
	 * Reserve book in library by invoking server side method.
	 */
	public void reserveBook(Scanner sc){
		System.out.println("Enter name: "); 
		name = sc.next();
		System.out.println("Enter password: ");
		password = sc.next();
		System.out.println("Enter bookName: ");
		bookName = sc.next();
		System.out.println("Enter authorName: ");
		authorName = sc.next();
				
		try {
			if(serverProxy.reserveBook(name, password, bookName, authorName)){
				System.out.println("Book Reserved Successfully. ");
			}
			else{System.out.println(" Failed!! ");}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		student.showOptions(sc);
	}
	
	/**
	 * Return book and Increment the copies of available for rent.
	 */
	public void returnBook(Scanner sc){
		System.out.println("Enter name: "); 
		name = sc.next();
		System.out.println("Enter password: ");
		password = sc.next();
		System.out.println("Enter bookName: ");
		bookName = sc.next();
		System.out.println("Enter authorName: ");
		authorName = sc.next();
				
		try {
			if(serverProxy.returnBook(name, password, bookName, authorName)){
				System.out.println("Book Returned Successfully. ");
			}
			else{System.out.println(" Failed!! ");}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		student.showOptions(sc);
	}
	
	/**
	 * Pay accumulated fines 
	 */
	public void payFines(Scanner sc){
		System.out.println("Enter name: "); 
		name = sc.next();
		System.out.println("Enter password: ");
		password = sc.next();
		System.out.println("Enter FineValue: ");
		fines = sc.nextInt();
						
		try{
			fines =serverProxy.payFines(name, password, fines);
			System.out.println("Fine Remaining on User: "+fines);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		student.showOptions(sc);
	}
	
	/**
	 * @param args
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void main(String args[]) throws MalformedURLException, RemoteException, NotBoundException {
		Scanner sc  = new Scanner(System.in);
		int choice =0; 
		
		boolean valid = false;
		System.out.println("\n Welcome to the Online Library System " );
		System.out.println("\n 1. Concordia Library " );
		System.out.println("\n 2. McGill Library " );
		System.out.println("\n 3. UQAM Library " );
		System.out.println("\n 4. Exit" );
		System.out.println("\n Enter Your Choice: " );
		choice = sc.nextInt();
	
		while(!valid){
			switch(choice){
			case 1: student = new StudentClient(choice); 
					valid =true; break;
			case 2: student = new StudentClient(choice);
					valid = true; break;
			case 3:	student = new StudentClient(choice);
					valid =true; break;
			case 4: System.out.println("Exited"); 
					System.exit(1);
					break; 			
			default :System.out.println("Invalid choice Please Re-enter.");
					valid =false;
			}
		}
		student.showOptions(sc);
	}
}
