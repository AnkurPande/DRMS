package modals;

/**
 * @author Ankurp
 *
 */
public class Book {

	private String name;
	private String author;
	private int id;
	private int noOfCopies;
	private Boolean availabilty =false;
	
	/**
	 * @param name
	 * @param author
	 * @param id
	 * @param numOfCopies
	 */
	public Book(String name,String author,int id,int numOfCopies){
		this.name = name;
		this.author = author;
		this.id =id;
		this.noOfCopies = numOfCopies;
	}
	
	/**
	 * @return availability
	 */
	public Boolean getAvailabilty() {
		if(this.noOfCopies>0){
			availabilty = true;
		}
		return availabilty;
	}

	/**
	 * @param availabilty
	 */
	public void setAvailabilty(Boolean availabilty) {
		this.availabilty = availabilty;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @return getId
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return noOfCopies
	 */
	public int getNoOfCopies() {
		return noOfCopies;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @param noOfCopies
	 */
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	
	/**
	 * Decrement available copies of books.
	 */
	public void reserveBook(){
		this.noOfCopies --;
	}
	
	/**
	 * Increment available copies of book for reservation.
	 */
	public void returnBook(){
		this.noOfCopies++;
	}
	
}
