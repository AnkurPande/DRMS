package server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import modals.Administrator;
import modals.Book;
import modals.Student;

/**
 * @author Ankurp
 *
 */
public class Server implements LibraryServerInterface{
	private String serverName =null;
	private Student student =null;
	private static Administrator admin = null;
	private Book book = null;
	private Map<String,Student> studentData = new HashMap<String,Student>(); 
	private Boolean newUser = false;
	private Map<String,Book> bookShelf = new HashMap<String,Book>();
	private static final int DEFAULT_DURATION = 14;
	private static final int PORT_OF_RMI =444;
	private static Server concordiaServer =null;
	private static Server mcgillServer =null;
	private static Server udemServer =null;
	
	/**
	 * @param name
	 */
	public Server(String name){
		this.setServerName(name);
	}
	
	/**
	 * @param sid
	 * @param name
	 * @param password
	 * @param address
	 * @param email
	 * @param educationInstitution
	 */
	@Override
	public Boolean createAccount(int sid, String name, String password,String address,String email,String educationInstitution ) throws RemoteException {
		student = new Student(sid,name,password,address,email,educationInstitution);
		
			if(!this.studentData.containsKey(name)){
				this.studentData.put(student.getName(), student);
				newUser =true;
			}
			else {
				System.out.println("Not a new user. Account already exist.");
				newUser = false;
			}	
	
		
		return newUser;
	}
	
	/**
	 * @param username
	 * @param bookName
	 * @param numOfDays
	 */
	public void setDuration(String username, String bookName, int numOfDays) throws RemoteException {
		
	}
	
	/**
	 * @param username
	 * @param password
	 */
	@Override
	public String getNonReturners(String username, String password,String institution, int numOfDays) throws RemoteException {
		if(!username.equals(admin.getUsername())){
			return null;
		}
		
		if(!password.equals(admin.getPassword())){
			return null;
		}
		String msg = "";
		for(Map.Entry<String, Student> entry : studentData.entrySet()){
			if(!entry.getValue().getBooks().isEmpty()){
				if(numOfDays <= entry.getValue().getBorrow().getDueDays()){
					msg  = msg + entry.getValue().getName() + " "+entry.getValue().getEducationalInstitute()+ " "+entry.getValue().getEmail()+"\n";  
				}
			}
			else {
				System.out.println("The Student "+ entry.getValue().getName()+" has no borrow.");
			}
		}
		return msg;
	}
	
	/**
	 * @param name
	 * @param password
	 * @param bookName
	 * @param authorName
	 * @return Boolean
	 */
	@Override
	public Boolean reserveBook(String name, String password, String bookName, String authorName) throws RemoteException {
		Boolean reserved= false;
		
			Student student = this.studentData.get(name);
			if(student ==null){
				System.out.println("Student with such username does not exit.");
				return false;
			}
			
			if(!student.getPass().equals(password)){
				System.out.println("The password is incorrect for Student with username "+ name);
				return false;
			}
				
			Book book = this.bookShelf.get(bookName);
			synchronized(book){
				if(book ==null){
					System.out.println("No such book exist.");
					return false;
				}
				
				if(!book.getAvailabilty()){
					System.out.println("Book not available to reserve.");
					return false;
				} 
						
				if(book.getAuthor().equals(authorName )){
					student.reserveBook(bookName, DEFAULT_DURATION);
					book.reserveBook();
					reserved = true;
					System.out.println("Book "+book.getName()+" Reserved successfully by student "+student.getName()+" .");
				}
				else{
					System.out.println("The author Name is incorrect for book  "+bookName);
				}
			}
			
		return reserved;
	}
	
	/**
	 * @param name
	 * @param password
	 * @param bookName
	 * @param authorName
	 */
	@Override
	public Boolean returnBook(String name, String password, String bookName, String authorName) throws RemoteException {
		Boolean returned= false;
		Student student = this.studentData.get(name);
		if(student ==null){
			System.out.println("Student with such username does not exit.");
			return false;
		}
		
		if(!student.getPass().equals(password)){
			System.out.println("The password is incorrect for Student with username "+ name);
			return false;
		}
			
		Book book = this.bookShelf.get(bookName);
		synchronized(book){
			if(book ==null){
				System.out.println("No such book exist.");
				return false;
			}
								
			if(book.getAuthor().equals(authorName )){
				book.returnBook();
				returned = true;
				System.out.println("Book "+book.getName()+" Returned successfully by student "+student.getName()+" .");
			}
			else{
				System.out.println("The author Name is incorrect for book  "+bookName);
			}
		}
		return returned;
		
	}
	
	/**
	 * @param name
	 * @param password
	 * @param fineValue
	 */
	@Override
	public int payFines(String name, String password,int fineValue) throws RemoteException {
		int netFine =0;
		Student student = this.studentData.get(name);
		synchronized(student){
			if(student !=null){
				if(student.getPass().equals(password)){
					student.payFines(fineValue);
					netFine = student.getFinesAccumulated();
				}
				else {
					System.out.println("The password is incorrect for Student with username "+ name);
				}
			}
			else {
				System.out.println("No such student with name "+name +" exist in the records.");
			}
		}
		return netFine;
	}
	
	/**
	 * @param bookId
	 * @param bookName
	 * @param authorName
	 * @param numOfCopies
	 */
	@Override
	public void addBooks(int bookId,String bookName,String authorName, int numOfCopies){
		boolean found = false;
		book = new Book(bookName, authorName,bookId,numOfCopies);
		for(Map.Entry<String, Book> entry: bookShelf.entrySet()){
			Book tempBook = entry.getValue();
			if((tempBook.getName().equalsIgnoreCase(book.getName())) && (tempBook.getAuthor().equalsIgnoreCase(book.getAuthor()))){
				numOfCopies = book.getNoOfCopies() + tempBook.getNoOfCopies();
				tempBook.setNoOfCopies(numOfCopies);
				System.out.println("Book  "+book.getName() +" already exist in the records.");
				entry.setValue(tempBook);
				found = true;
			}
		}
		if(!found){
			this.bookShelf.put(book.getName(), book);
			System.out.println("Book  "+book.getName() +" added in the records.");
		}
		
	}
	
	@Override
	public void listBook() throws RemoteException {
		// TODO Auto-generated method stub
		listAllBooks();
	}
	
	/**
	 * List all available books listed in the library.
	 */
	public void listAllBooks(){
		for(Map.Entry<String,Book> entry: this.bookShelf.entrySet()){
			Book book = entry.getValue();
			System.out.println(book.getName()+" , Copies Available:- "+book.getNoOfCopies()+" , Author Name :-  "+book.getAuthor());
			System.out.println();
		}
	}
	
	/**
	 * List all the registered students in the library system.
	 */
	public void printAllStudents(){
		for(Map.Entry<String, Student> entry: studentData.entrySet()){
			Student std = entry.getValue();
			System.out.println(std.getName()+"|"+std.getEmail()+"|"+std.getEducationalInstitute());
			System.out.println("Reserved Book : "+std.printAllReservedBooks());
		}
		
	}
	
	/**
	 * @return serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @param args
	 * @throws RemoteException
	 * @throws AlreadyBoundException 
	 */
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		// TODO Auto-generated method stub
		concordiaServer = new Server("Concordia");
		admin = new Administrator();
		admin.setUsername("AdminAdmin");
		admin.setPassword("Admin001");
		
		concordiaServer.createAccount(66140, "aaa","aaa1" , "montreal", "aaa@gmail.com", "concordia");
		concordiaServer.createAccount(66141, "bbb","bbb1" , "montreal", "bbb@gmail.com", "concordia");
		concordiaServer.createAccount(66142, "ccc","ccc1" , "montreal", "ccc@gmail.com", "concordia");
		concordiaServer.createAccount(66143, "ddd","ddd1" , "montreal", "ddd@gmail.com", "concordia");
		
		
		concordiaServer.addBooks(1, "physics", "kingston", 4);
		concordiaServer.addBooks(1, "physics", "kingston", 4);
		concordiaServer.addBooks(2, "chemistry", "Charles", 8);
		concordiaServer.addBooks(3, "mathematics", "Steve", 2);
		concordiaServer.addBooks(4, "biology", "Robin", 7);
		concordiaServer.addBooks(5, "General Knowledge", "Barley", 5);
		concordiaServer.listAllBooks();
		
		concordiaServer.reserveBook("aaa", "aaa1", "physics", "kingston");
		concordiaServer.reserveBook("bbb", "bbb1", "physics", "kingston");
		concordiaServer.reserveBook("ddd", "ddd1", "physics", "kingston");
		concordiaServer.printAllStudents();
		
		Registry registry = LocateRegistry.createRegistry(PORT_OF_RMI);
		Remote obj = UnicastRemoteObject.exportObject(concordiaServer, PORT_OF_RMI);
		registry.bind(concordiaServer.getServerName(), obj);
		System.out.println(concordiaServer.getServerName() + " concordiaServer is running!");
		
	}
}
