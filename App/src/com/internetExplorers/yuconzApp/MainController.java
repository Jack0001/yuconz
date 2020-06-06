/**
 * 
 * MainController is the container for all objects used in the app including users, authentication server and authorisation.
 * The GUI will interact solely with the MainController.
 * @author Jordan Montford Robinson
 * @version 0.1
 */
package com.internetExplorers.yuconzApp;

import com.internetExplorers.yuconzApp.AuthenticationServer;
import com.internetExplorers.yuconzApp.user.*;
import com.internetExplorers.yuconzApp.Authorisation;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class MainController {

	private static AuthenticationServer aS;
	private static HashMap<String, User> users;
	private static HrDatabase hD;
	private static Authorisation author;
	private static Login login;
	private static Options options;
	private static Personal personal;
	private static PersonalSelector personalSelector;
	private static Review review;
	private static ReviewSelector reviewSelector;

	public static void main(String[] args) throws Exception {
		final MainController app = new MainController("test.db");
		login = new Login(app);
		options = new Options(app);
		personal = new Personal(app);
		personalSelector = new PersonalSelector(app);
		review = new Review(app);
		reviewSelector = new ReviewSelector(app);
	}

	/**
	 * 
	 * Constructor, initialises Authentication Server and Users container.
	 */
	public MainController(String database) {
		aS = new AuthenticationServer(database);
		users = new HashMap<String, User>();
		hD = new HrDatabase(database);
		author = new Authorisation(database);
	}

	public User getUser(String username) // this is needed for testing as there is no gui or server.
	{
		try {
			if (this.isLoggedIn(username)) {
				return users.get(username);
			}
		} catch (Exception e) {
			System.out.println("Error: user not logged in");
			return null;
		}
		return null;
	}

	/**
	 * Login Method - lets users log in. it creates an user object using previously
	 * stored data
	 * 
	 * @throws Exception UserDoesNotExist
	 * @param username(String), Password(String), requestedAccess(String)
	 */
	public String login(String un, String pw) {
		String[] obj = null;
		try {
			obj = aS.login(un, pw);
			if (obj[0] == "incorrect password") {
				return "Incorrect password";
			} else if (obj[0] == "incorrect username") {
				return "User not found";
			} else {
				User user = new User(obj[0], obj[1], obj[2], obj[3], obj[4]);
				users.put(obj[0], user);
				options.setUser(user);
				return "Logged in";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	/**
	 * LogOut Method - lets users log out. it removes an user object form the users
	 * hashmap
	 * 
	 * @throws Exception UserCannotBeFound
	 * @param usr user
	 */
	public void logOut(String username) {
		try {
			if (isLoggedIn(username)) {
				users.remove(username);
				System.out.println("logged out");
			} else {
				System.out.println("User does not exist or is not logged in");
			}
		} catch (Exception e) {
			System.out.println("Exception: Cannot find user");
		}
	}

	/**
	 * isLoggedIn Method - checks user is logged in.
	 * 
	 * @param User(User)
	 * @return Boolean
	 */
	public boolean isLoggedIn(String username) throws Exception {
		try {
			if (users.containsKey(username)) {
				System.out.println(username + " is logged in");
				return true;
			} else {
				System.out.println(username + " is not logged in");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception: User does not exist or is not logged in");
			return false;
		}
	}

	/**
	 * Read personal details stored in the database and put them in an array list of
	 * PersonalDetail objects If the person making the request is a HR employee or
	 * director then all of the personal details are fetched Otherwise it only
	 * fetches their own personal record
	 * 
	 * @param recordOwner - The owner of the record that is attempting to be read
	 * @param reader      - The user attempting to read the personal details
	 * @param type        - The type of request being made - "one" for one personal
	 *                    details or "all" for all personal details
	 * @return <PersonalDetails> an array list of personal details for use in the
	 *         forms
	 */
	public ArrayList<PersonalDetails> readPersonalDetails(String recordOwner, User reader, String type) {
		// check permissions - HR employees should be able to read every review
		String[] permissions = { "hr_employee", "director" };

		if (recordOwner.equals(reader.getUsername()) || author.authorisationCheck(reader.getUsername(), reader.getPosition(), permissions, "Read personal details")) {
			return hD.getPersonalDetails(recordOwner, type);
		}
		return null;
	}

	/**
	 * Attempt to ammend the personal details of the current logged in person
	 * To do this they either need to be a member of HR
	 * 
	 * @return position
	 */
	public boolean amendPersonalDetails(User reader, PersonalDetails personalDetails) {
		String[] permissions = { "hr_employee" };
		if (author.authorisationCheck(reader.getUsername(), reader.getPosition(), permissions,
				"Editing personal details")) {

			if (hD.setPersonalDetails(personalDetails)) {
				System.out.println("Successful update");
				return true;
			} else {
				System.out.println("Failed to update");
			}
		}
		return false;
	}

	/**
	 * Request a new personal details record. this can be done by any employee but cannot be edited after being made
	 * Only hr can edit the personal details
	 * 
	 * @return position
	 * @throws IOException
	 */
	public boolean requestNewPersonalDetails(PersonalDetails personalDetails, User reader) {
		String[] permissions = { "hr_employee", "director", "manager", "employee" };
		if (author.authorisationCheck(reader.getUsername(), reader.getPosition(), permissions, "Requesting new personal details")) {
			if (hD.createPersonalDetails(personalDetails)) {
				System.out.println("database has created record.");
				return true;
			} else {
				System.out.println("Failed to save file");
			}
		}
		return false;
	}

	/**
	 * Record an allocated reviewer/secondary reviewer to a review
	 * 
	 * @param reviewer_username - The username of the reviewer being allocated
	 * @param reviewee_username - The username of the person being reviewed
	 * @param assigner          - The user assigning the reviewer
	 * @param secondary         - Denotes if the reviewer is a primary (false) or
	 *                          secondary (true) reviewer
	 * @return - True or false based on the outcome
	 * 
	 */
	public boolean recordAllocatedReviewer(ReviewRecord reviewRecord, User assigner, String reviewer,
			boolean secondary) {
		// To edit, the user needs to be a hr employee
		String[] permissions = { "hr_employee" };

		// Check if the user has the level of authorisation required to edit or update
		// the reviewer
		if (author.authorisationCheck(assigner.getUsername(), assigner.getPosition(), permissions, "Adding a reviewer to a review")) {
			if (hD.updateReviewer(reviewRecord, reviewer, secondary)) {
				System.out.println("database has allocated reviewer.");
				return true;
			} else {
				System.out.println("Failed to allocate reviewer");
				return false;
			}
		}

		System.out.println("Authorisation check failed");
		return false;
	}

	/**
	 * Attempts to sign off the review of the username entered Only works if there
	 * are values in the database that represent the review being signed off (not
	 * null values)
	 * 
	 * @param reviewee_username - The username of the reviewee, the review to sign
	 *                          off
	 * @param approver          - The user attempting to sign off the review
	 * @return True or false and a message based on the result of the attempted sign
	 *         off
	 */
	public boolean signOffReview(String reviewee_username, User approver) {
		// To sign off, the user needs to be a hr employee

		String[] permissions = { "hr_employee" };

		if (author.authorisationCheck(approver.getUsername(), approver.getPosition(), permissions, "Approving a review")) {
			if (hD.finishReview(reviewee_username)) {
				System.out.println("Successfully signed off review.");
				return true;
			} else {
				System.out.println("Failed to sign off review");
				return false;
			}
		}

		return false;
	}

	/**
	 * Request a new review record to be created in the database
	 * 
	 * @param recordOwner - The person the record belongs to
	 * @param creator     - The user creating the record
	 * @return True or false based on outcome
	 * @throws IOException
	 */
	public boolean requestNewReviewRecord(ReviewRecord reviewRecord) {
		if (hD.createReviewRecord(reviewRecord)) {
			System.out.println("database has created record.");
			return true;
		} else {
			System.out.println("Failed to save file");
		}
		return false;
	}

	/**
	 * Read a review record HR and directors can read review records, and reviewers
	 * can read records they assisted in reviewing
	 * 
	 * @return position
	 */
	public ArrayList<ReviewRecord> readReviewRecord(String recordOwner, User reader, int id) {
		// check permissions - HR and Directors are allowed to read all review records
		String[] permissions = { "hr_employee", "director" };
		// if its their own review, they pass the authorisation check or are related to
		// the review they are allowed to read it
		if (recordOwner.equals(reader.getUsername()) || author.authorisationCheck(reader.getUsername(), reader.getPosition(), permissions, "Read a review records")
				|| hD.checkRelated(reader.getUsername(), id)) {
			// If the id is 0 this is the flag for getting the current in progress review
			if (id == 0) {
				return hD.getInProgressReview(recordOwner);
			} else {
				return hD.getReview(id, recordOwner);
			}
		} else {
			System.out.println("You do not have premission to do this. Contact HR");
		}
		return null;
	}

	public ArrayList<Objective> readObjectives(ReviewRecord reviewRecord, User reader) {
		// if its their own review, they pass the authorisation check or are related to
		// the review they are allowed to read it

			return hD.getObjectives(reviewRecord.getUsername(), reviewRecord.getrID());
	}

	/**
	 * Amend a review record
	 * 
	 * @return position
	 */
	public boolean amendReviewRecords(ReviewRecord reviewRecord, User user) {
		// To ammend a review, the user must be a review participant
		// Meaning they are the reviewee, reviewer or second reviewer
		if (hD.checkRelated(user.getUsername(), reviewRecord.getrID())) {
			hD.setReviewRecord(reviewRecord);
			System.out.println("database has stored file.");
			return true;
		} else {
			System.out.println("Failed to save file");
		}

		return false;
	}

	public boolean insertObjective(ReviewRecord reviewRecord, Objective objective, User user) {
		if (hD.checkRelated(user.getUsername(), reviewRecord.getrID())) {
			return hD.addObjective(reviewRecord, objective);
		} else {
			System.out.println("Failed to add objective");
			return false;
		}
	}

	public boolean ammendObjective(ReviewRecord reviewRecord, Objective objective, User user) {
		if (hD.checkRelated(user.getUsername(), reviewRecord.getrID())) {
			return hD.setObjective(reviewRecord, objective);
		} else {
			System.out.println("Failed to insert objective");
			return false;
		}
	}

	public ArrayList<ReviewRecord> fetchReviewList(User user) {
		return hD.getReviewList(user);
	}

	/**
	 * Check if an objective is available for editing as completed reviews are
	 * unable to be edited
	 * 
	 * @param reviewRecord - The review record to check if it can be edited by the
	 *                     user
	 * @param reviewID     - The id of the review the objective is a part of
	 * @param user         - The user attempting to edit or add the objective
	 * @return True or false based on the result of the check
	 */
	public boolean objectivesCheck(ReviewRecord reviewRecord, int reviewID, User user) {
		if (hD.checkRelated(user.getUsername(), reviewRecord.getrID())) {
			return hD.checkUpdateable(reviewID);
		}
		return false;
	}

	public static AuthenticationServer getaS() {
		return aS;
	}

	public static HashMap<String, User> getUsers() {
		return users;
	}

	public static HrDatabase gethD() {
		return hD;
	}

	public static Authorisation getAuthor() {
		return author;
	}

	public static Login getLogin() {
		return login;
	}

	public static Options getOptions() {
		return options;
	}

	public static Personal getPersonal() {
		return personal;
	}

	public static PersonalSelector getPersonalSelector() {
		return personalSelector;
	}

	public static Review getReview() {
		return review;
	}

	public static ReviewSelector getReviewSelector() {
		return reviewSelector;
	}
}
