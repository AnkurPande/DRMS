package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

	/**
	 * @author Ankurp
	 *
	 */

public interface LibraryServerInterface extends Remote {

	/**
	 * @param sid
	 * @param name
	 * @param password
	 * @param address
	 * @param email
	 * @param educationInstitution
	 */
	public Boolean createAccount(int sid, String name, String password,String address,String email,String educationInstitution ) throws RemoteException;
	
/*	*//**
	 * @param username
	 * @param password
	 * @param numOfDays
	 *//*
	public void setDuration(String username, String bookName, int numOfDays) throws RemoteException;
	
	*/
	/**
	 * @param username
	 * @param password
	 * @param institution
	 * @param numOfDays
	 * @return
	 * @throws RemoteException
	 */
	public String getNonReturners(String username, String password,String institution, int numOfDays) throws RemoteException;
	
	/**
	 * @param name
	 * @param password
	 * @param bookName
	 * @param authorName
	 */
	public Boolean reserveBook(String name, String password, String bookName, String authorName) throws RemoteException;
	
	/**
	 * @param name
	 * @param password
	 * @param bookName
	 * @param authorName
	 */
	public Boolean returnBook(String name, String password, String bookName, String authorName) throws RemoteException;
	
	/**
	 * @param name
	 * @param password
	 * @param fineValue
	 */
	public int payFines(String name, String password,int fineValue) throws RemoteException;
	
	/**
	 * @param bookId
	 * @param bookName
	 * @param authorName
	 * @param numOfCopies
	 */
	public void addBooks(int bookId,String bookName,String authorName, int numOfCopies) throws RemoteException;
		
	/**
	 * @throws RemoteException
	 */
	public void listBook() throws RemoteException;	
}
