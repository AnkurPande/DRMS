package modals;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * @author Ankurp
 *
 */
public class Borrow {
	private int duration = 0;
	private Date issueTime;
	
	/**
	 * @param duration
	 */
	public Borrow(int duration){
		setDuration(duration);
		setIssueTime(new Date());
	}
	
	/**
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return issueTime
	 */
	public Date getIssueTime() {
		return issueTime;
	}

	/**
	 * @param issueTime
	 */
	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	/**
	 * @return dueDays
	 */
	public int getDueDays(){
		Date currentTime = new Date();
		long startTime = getIssueTime().getTime();
		long endTime = currentTime.getTime();
		long diffTime = endTime - startTime;
		int diffDays = (int)TimeUnit.MILLISECONDS.toDays(diffTime);
		int dueDays = this.getDuration() - diffDays;
		return dueDays ;
	}
	
	
}
