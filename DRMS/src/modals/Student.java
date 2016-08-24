package modals;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Ankurp
 *
 */
public class Student {
	private int sid;
	private String name;
	private String address;
	private String email;
	private String  pass;
	private int finesAccumulated ;
	private String educationalInstitute;
	private Map<String, Borrow> books = new HashMap<String, Borrow>();
	private Borrow borrow =null;
	
	/**
	 * @return books
	 */
	public Map<String, Borrow> getBooks() {
		return books;
	}

	/**
	 * @param books
	 */
	public void setBooks(Map<String, Borrow> books) {
		this.books = books;
	}

	/**
	 * @return borrow
	 */
	public Borrow getBorrow() {
		return borrow;
	}

	/**
	 * @param borrow
	 */
	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	/**
	 * @return str
	 */
	public String printAllReservedBooks(){
		String str = " ";
		for(Map.Entry<String,Borrow> entry : books.entrySet()){
		str = str + entry.getKey();
		}
		return str;
	}

	/**
	 * @param sid
	 * @param name
	 * @param password
	 * @param address
	 * @param email
	 * @param eduInst
	 */
	public Student(int sid,String name,String password,String address,String email,String eduInst){
		this.sid = sid;
		this.name = name;
		this.address = address;
		this.email =email;
		this.pass = password;
		this.finesAccumulated = 0;
		this.educationalInstitute = eduInst;
	}
	
	
	/**
	 * @return sid
	 */
	public int getSid() {
		return sid;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @return pass
	 */
	public String getPass() {
		return pass;
	}
	
	/**
	 * @param sid
	 */
	public void setSid(int sid) {
		this.sid = sid;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @param pass
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	/**
	 * @return finesAccumulated
	 */
	public int getFinesAccumulated() {
		return finesAccumulated;
	}

	/**
	 * @param finesAccumulated
	 */
	public void setFinesAccumulated(int finesAccumulated) {
		this.finesAccumulated = finesAccumulated;
	}
	
	/**
	 * @return educationalInstitute
	 */
	public String getEducationalInstitute() {
		return educationalInstitute;
	}

	/**
	 * @param educationalInstitute
	 */
	public void setEducationalInstitute(String educationalInstitute) {
		this.educationalInstitute = educationalInstitute;
	}

	/**
	 * @param bookName
	 * @param duration
	 */
	public void reserveBook(String bookName,int duration){
		try{
			borrow =new Borrow(duration);
			this.books.put(bookName, borrow);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param bookName
	 * @return boolean
	 */
	public Boolean returnBook(String bookName){
		if(this.books.containsKey(bookName)){
			this.books.remove(bookName);
		}
		return this.books.containsKey(bookName);
	}
	
	/**
	 * Add accumulated fines on daily basis.
	 */
	public void addDailyFines(){
		this.finesAccumulated++;
	}
	
	/**
	 * @param fineValue
	 */
	public void payFines(int fineValue){
		setFinesAccumulated(getFinesAccumulated() - fineValue); 
	}
}
