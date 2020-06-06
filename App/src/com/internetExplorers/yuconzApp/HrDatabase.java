/**
 * 
 * HR DataBase
 * @author Jordan Montford Robinson
 * @version 0.1
 */
package com.internetExplorers.yuconzApp;

import java.util.ArrayList;

import com.internetExplorers.yuconzApp.user.Objective;
import com.internetExplorers.yuconzApp.user.PersonalDetails;
import com.internetExplorers.yuconzApp.user.ReviewRecord;
import com.internetExplorers.yuconzApp.user.User;

import sql.database.HrDatabaseTableMethods;

public class HrDatabase {

	private HrDatabaseTableMethods hrDB;

	/**
	 * Constructor, tries to initialise the login file and login.
	 */
	public HrDatabase(String database) {

		hrDB = new HrDatabaseTableMethods(database);
	}

	/**
	 * Get personal details
	 * @param username - The username of the user
	 * @param type - The type of request being made
	 * @return true or false based on the results
	 */
	public ArrayList<PersonalDetails> getPersonalDetails(String username, String type) {
		return hrDB.findPersonalDetails(username, type);

	}
	/**
	 * Get review
	 * @param id - The id of the review to get
	 * @param username - The username of the requester
	 * @return
	 */
	public ArrayList<ReviewRecord> getReview(int id, String username) {
		return hrDB.getReview(id, username);

	}

	public ArrayList<ReviewRecord> getInProgressReview(String username) {
		return hrDB.getCurrentReview(username);
	}

	/**
	 * setRecord
	 */
	public boolean setPersonalDetails(PersonalDetails personalDetails) {
		if (hrDB.updatePersonalDetails(personalDetails)) {
			return true;
		}
		return false;
	}

	public boolean setReviewRecord(ReviewRecord reviewRecord) {
		if (hrDB.updateReviewRecord(reviewRecord)) {
			return true;
		}
		return false;
	}

	/**
	 * createRecord
	 */
	public boolean createPersonalDetails(PersonalDetails personalDetails) {
		if (hrDB.insertPersonalDetails(personalDetails)) {
			return true;
		}
		return false;
	}

	public boolean createReviewRecord(ReviewRecord reviewRecord) {
		if (hrDB.insertReviewRecord(reviewRecord)) {
			return true;
		}
		return false;
	}

	public boolean updateReviewer(ReviewRecord reviewRecord, String reviewer, boolean secondary) {
		if (hrDB.setReviewer(reviewRecord, reviewer, secondary)) {
			return true;
		}
		return false;
	}

	public boolean finishReview(String reviewee_username) {
		if (hrDB.signOff(reviewee_username)) {
			return true;
		}
		return false;
	}

	public boolean checkRelated(String username, int ID) {
		if (hrDB.isRelated(username, ID)) {
			return true;
		}
		return false;
	}

	public ArrayList<Objective> getObjectives(String username, int id) {
		return hrDB.findObjectives(username, id);

	}
	
	public boolean addObjective (ReviewRecord reviewRecord, Objective objective) {
		return hrDB.insertObjective(reviewRecord, objective);
	}
	
	public boolean setObjective (ReviewRecord reviewRecord, Objective objective) {
		return hrDB.updateObjective(reviewRecord, objective);
	}
	
	public boolean checkUpdateable(int reviewID) {
		return hrDB.checkObjective(reviewID);
	}
	
	public ArrayList<ReviewRecord> getReviewList(User user){
		return hrDB.listReviews(user);
	}

}
