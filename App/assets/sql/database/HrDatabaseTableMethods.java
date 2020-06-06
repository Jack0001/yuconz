package sql.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.internetExplorers.yuconzApp.user.Objective;
import com.internetExplorers.yuconzApp.user.PersonalDetails;
import com.internetExplorers.yuconzApp.user.ReviewRecord;
import com.internetExplorers.yuconzApp.user.User;

public class HrDatabaseTableMethods {

	private String database;

	public HrDatabaseTableMethods(String database) {
		this.database = database;
	}

	public ArrayList<PersonalDetails> findPersonalDetails(String username, String type) {
		ArrayList<PersonalDetails> personalDetails = new ArrayList<PersonalDetails>();

		String sql = "";
		if (type.equals("one")) {
			sql = "SELECT * FROM personal_details WHERE username = ?";
		} else if (type.equals("all")) {
			sql = "SELECT * FROM personal_details";
		}
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			if (type.equals("one")) {
				pstmt.setString(1, username);
			}
			//
			ResultSet rs = pstmt.executeQuery();
			// loop through the result set
			while (rs.next()) {

				PersonalDetails details = new PersonalDetails(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13));

				personalDetails.add(details);
			}
			return personalDetails;
		} catch (

		SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Insert a personal details in to the table
	 *
	 * @param personalDetails - The personal details to add to the database
	 * @return true or false based on the result of the insert
	 */
	public boolean insertPersonalDetails(PersonalDetails personalDetails) {
		String sql = "INSERT INTO personal_details (username, surname, name, dob, address, town, county, postcode, telephone, mobile, emergency_contact, emergency_contact_no) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, personalDetails.getUsername());
			pstmt.setString(2, personalDetails.getSurname());
			pstmt.setString(3, personalDetails.getName());
			pstmt.setString(4, personalDetails.getDob());
			pstmt.setString(5, personalDetails.getAddress());
			pstmt.setString(6, personalDetails.getTownCity());
			pstmt.setString(7, personalDetails.getCounty());
			pstmt.setString(8, personalDetails.getPostCode());
			pstmt.setString(9, personalDetails.getTelephoneNo());
			pstmt.setString(10, personalDetails.getMobileNo());
			pstmt.setString(11, personalDetails.getEmergencyContact());
			pstmt.setString(12, personalDetails.getEmergencyContactNo());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Update personal record of the username entered using the details entered
	 *
	 * @param username  - The username to update
	 * @param details[] - The details to update with
	 */
	public boolean updatePersonalDetails(PersonalDetails personalDetails) {
		String sql = "UPDATE personal_details SET surname = ?, name = ?, dob = ?, address = ?, town = ?,"
				+ " county = ?, postcode = ?, telephone = ?, mobile = ?, emergency_contact = ?, emergency_contact_no = ? "
				+ "WHERE username = ?";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// set the corresponding params for the update
			pstmt.setString(1, personalDetails.getSurname());
			pstmt.setString(2, personalDetails.getName());
			pstmt.setString(3, personalDetails.getDob());
			pstmt.setString(4, personalDetails.getAddress());
			pstmt.setString(5, personalDetails.getTownCity());
			pstmt.setString(6, personalDetails.getCounty());
			pstmt.setString(7, personalDetails.getPostCode());
			pstmt.setString(8, personalDetails.getTelephoneNo());
			pstmt.setString(9, personalDetails.getMobileNo());
			pstmt.setString(10, personalDetails.getEmergencyContact());
			pstmt.setString(11, personalDetails.getEmergencyContactNo());
			pstmt.setString(12, personalDetails.getUsername());

			// update
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * @param reviewRecord
	 * @return
	 */
	public boolean updateReviewRecord(ReviewRecord reviewRecord) {
		// The only values that can change in a review are: summary, recommendation,
		// comments, last_edited, signed_reviewee, signed_reviewer, signed_reviewer2
		// A review must be approved through the related functions
		String sql = "UPDATE review SET summary = ?, recommendation = ?, comments = ?, last_edited = DATETIME(), signed_reviewee = ?, signed_reviewer = ?, signed_reviewer2 = ? WHERE rID = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, reviewRecord.getSummary());
			pstmt.setString(2, reviewRecord.getRecommendation());
			pstmt.setString(3, reviewRecord.getComments());
			pstmt.setInt(4, reviewRecord.getSignedReviewee());
			pstmt.setInt(5, reviewRecord.getSignedReviewer());
			pstmt.setInt(6, reviewRecord.getSignedReviewer2());
			pstmt.setInt(7, reviewRecord.getrID());
			if (pstmt.executeUpdate() == 0) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Try to insert the reviewer in to the table as given
	 * 
	 * @param details   - The details denoting the reviewer at [0] and reviewee at
	 *                  [1]
	 * @param secondary - Whether we are updating the initial or secondary reviewer
	 * @return - True or false based on if the update was successful
	 */

	public boolean setReviewer(ReviewRecord reviewRecord, String reviewer, boolean secondary) {
		// Initialise sql statement based on whether we are looking for a primary or
		// secondary reviewer to update
		String sql;

		if (!secondary) {
			sql = "UPDATE review SET reviewer = ?, last_edited = DATETIME() WHERE username = ? AND approved = 0";
		} else {
			sql = "UPDATE review SET second_reviewer = ?, last_edited = DATETIME() WHERE username = ? AND approved = 0";
		}

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, reviewer);
			pstmt.setString(2, reviewRecord.getUsername());
			if (pstmt.executeUpdate() == 0) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Get the current in progress review using the username and the assumption the
	 * current review is unapproved
	 * 
	 * @param username - The username of the record owner
	 * @return reviewRecords - An array list of review record objects based on the
	 *         sql results
	 */
	public ArrayList<ReviewRecord> getCurrentReview(String username) {
		String sql;
		ArrayList<ReviewRecord> reviewRecords = new ArrayList<ReviewRecord>();

		// Select the the review based on the id the function is called with
		sql = "SELECT * FROM review WHERE username = ? AND approved = '0'";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				// in order: id, username, reviewer, second_reviewer, section, job_title,
				// summary, recommendation, comments,
				// date_created, last_edited, signed_reviewee, signed_reviewer, signed_reviewer2
				ReviewRecord reviewRecord = new ReviewRecord(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11), rs.getInt(12), rs.getInt(13),
						rs.getInt(14), rs.getInt(15));
				reviewRecords.add(reviewRecord);
			}
			return reviewRecords;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return reviewRecords;
	}

	/**
	 * Sign off a review as approved Select the current review thats in progress
	 * using a combination of the username of the reviewee and the approved to get
	 * current in progress review
	 * 
	 * @param reviewee_username
	 * @return True or false based on the result
	 */
	public boolean signOff(String reviewee_username) {
		String sql;
		// Selecting the review needed
		// This should select the current in progress reviews (approved = N as in not
		// finished)
		// And that the document has been signed by all three participants (assuming if
		// they are not empty they are signed)

		sql = "Select * FROM review WHERE approved = 0 AND username = ? " + "AND signed_reviewee = 1 "
				+ "AND signed_reviewer = 1 " + "AND signed_reviewer2 IS NOT NULL";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			pstmt.setString(1, reviewee_username);
			ResultSet rs = pstmt.executeQuery();

			// Get the size of the result set by using the id of the last row
			// As there should only be one review at a time with the state of approved being
			// 'N' we should check this
			// This should not happen because of the checks when creating a review, but this
			// is here just in case
			int size = 0;
			while (rs.next()) {
				size++;
			}

			if (pstmt.executeQuery().next() && size == 1) {
				int id = rs.getInt(1);

				sql = "UPDATE review SET approved = '1', last_edited = DATETIME() WHERE rID = ?";

				PreparedStatement pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, id);
				pstmt2.executeUpdate();
				return true;
			} else {
				System.out
						.println("Failed to update review - Query failed or there is more than one unapproved review");
			}

		} catch (SQLException e) {
			System.out.println("Failed to sign off review");
		}
		return false;
	}

	/**
	 * Insert the review record in to the table
	 * 
	 * @param details
	 * @return
	 */

	public boolean insertReviewRecord(ReviewRecord reviewRecord) {
		String sql;

		// Selecting any reviews that are currently in progress
		// If any are selected then the review cannot be created as the current one
		// needs to be finished

		sql = "Select * FROM review WHERE approved = '0' AND username = ? ";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			pstmt.setString(1, reviewRecord.getUsername());
			ResultSet rs = pstmt.executeQuery();

			// Get the size of the result set by using the id of the last row
			// As there should only be one review at a time with the state of approved being
			// '0' we should check this
			// This should not happen because of the checks when creating a review, but this
			// is here just in case
			int size = 0;
			while (rs.next()) {
				size++;
			}
			if (size == 0) {

				sql = "INSERT INTO review(username, section, job_title, date_created, last_edited, approved) VALUES(?,?,?,DATETIME(),DATETIME(),0)";
				PreparedStatement pstmt2 = conn.prepareStatement(sql);
				pstmt2.setString(1, reviewRecord.getUsername());
				pstmt2.setString(2, reviewRecord.getSection());
				pstmt2.setString(3, reviewRecord.getJobTitle());
				pstmt2.executeUpdate();
				return true;
			} else {
				System.out.println(
						"Failed to update review - The is a currently unfinished review that needs to be completed");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Find if the reviewer is a reviewer of a reviewee entered based on the ID This
	 * is for checking to see if a reviewer is able to read a review as they are
	 * related to it
	 * 
	 * @param ID        - The id of the record
	 * @param requester - The username of the person requesting the record incase
	 *                  checks for if they are involved need to be made
	 * @return reviewRecords - An ArrayList of ReviewRecord objects based on the
	 *         results returned from the sql query
	 */
	public ArrayList<ReviewRecord> getReview(int ID, String requester) {
		String sql;
		ArrayList<ReviewRecord> reviewRecords = new ArrayList<ReviewRecord>();
		// Select the the review based on the id the function is called with
		sql = "SELECT * FROM review WHERE rID= ?";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			pstmt.setInt(1, ID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				// in order: id, username, reviewer, second_reviewer, section, job_title,
				// summary, recommendation, comments,
				// date_created, last_edited, signed_reviewee, signed_reviewer, signed_reviewer2
				ReviewRecord reviewRecord = new ReviewRecord(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11), rs.getInt(12), rs.getInt(13),
						rs.getInt(14), rs.getInt(15));
				reviewRecords.add(reviewRecord);
			}
			return reviewRecords;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	/**
	 * Check if the user requesting to read a specific review record is allowed to
	 * They either have to be in hr, a director or related to that specific review
	 * to read it
	 * 
	 * @param username - The username of the user trying to read the review
	 * @param position - The position of the user
	 * @param ID       - The id of the review
	 * @return True or false based of the request
	 */
	public boolean requestRead(String username, String position, int ID) {
		if (position.equals("director") || position.equals("hr_employee")) {
			return true;
		} else if (isRelated(username, ID)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check if the user requesting to edit a specific review record is allowed to
	 * For editing the user must be related to the review
	 * 
	 * @param username - The username of the user requesting to edit the review
	 * @param ID       - The id of the review attempting to be edited
	 * @return
	 */
	public boolean requestEdit(String username, int ID) {
		if (isRelated(username, ID)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Find if the username entered is related to a review This is for checking to
	 * see if the user is allowed to read/edit the review they are related to
	 * 
	 * @param reviewee - The username of the person to check if they are related to
	 *                 a review
	 * @param ID       - The id of the record
	 * @return True or false based on the result
	 */
	public boolean isRelated(String username, int ID) {
		String sql;

		// Select the review by id if the reviewer or secondary reviewer matches the
		// string entered
		sql = "SELECT * FROM review WHERE rID= ? AND (username = ? OR reviewer = ? OR second_reviewer = ?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			pstmt.setInt(1, ID);
			pstmt.setString(2, username);
			pstmt.setString(3, username);
			pstmt.setString(4, username);
			ResultSet rs = pstmt.executeQuery();
			// If a value was returned and placed in the record set then there is a relation
			// to the review
			// This means they are allowed to read and edit the review
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	/**
	 * List all the reviews that the input user is a part of for editing or reading
	 * purposes
	 * 
	 * @param username    - The username of the person to be selected
	 * @param permissions - The level of permission the user has - This is mainly
	 *                    for if a person is a director
	 * @return results - The results set from the query
	 */
	public ArrayList<ReviewRecord> listReviews(User user) {
		String sql;
		String username = user.getUsername();
		String position = user.getPosition();
		boolean prepare = false;
		ArrayList<ReviewRecord> reviewRecords = new ArrayList<ReviewRecord>();
		// Directors and hr employees are allowed to read all reviews and therefore all
		// will be listed to them
		if (position.equals("director") || position.equals("hr_employee")) {
			sql = "SELECT * FROM review";
		} else {
			sql = "SELECT * FROM review WHERE username = ? OR reviewer = ? OR second_reviewer = ?";
			prepare = true;
		}

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			if (prepare) {
				pstmt.setString(1, username);
				pstmt.setString(2, username);
				pstmt.setString(3, username);
			}
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewRecord reviewRecord = new ReviewRecord(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11), rs.getInt(12), rs.getInt(13),
						rs.getInt(14), rs.getInt(15));
				reviewRecords.add(reviewRecord);
			}
			return reviewRecords;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	/**
	 * Find all the objectives related to a user
	 * 
	 * @param username - The username to use to fetch the objectives
	 * @return objectives - An array list of objective objects to be used in the
	 *         reviews
	 */
	public ArrayList<Objective> findObjectives(String username, int id) {
		String sql;
		ArrayList<Objective> objectives = new ArrayList<Objective>();

		// Select all objectives in objectives that are related to the user
		sql = "SELECT * FROM objectives WHERE username = ? AND rID <= ?";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			pstmt.setString(1, username);
			pstmt.setInt(2, id);
			ResultSet rs = pstmt.executeQuery();
			// If a value was returned and placed in the record set then there is a relation
			// to the review
			// This means they are allowed to read and edit the review
			while (rs.next()) {
				Objective objective = new Objective(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5));
				objectives.add(objective);
			}
			return objectives;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public boolean insertObjective(ReviewRecord reviewRecord, Objective objective) {
		String sql;

		sql = "INSERT INTO objectives (username, rID, objective, date_created) VALUES (?,?,?,DATETIME())";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			pstmt.setString(1, reviewRecord.getUsername());
			pstmt.setInt(2, reviewRecord.getrID());
			pstmt.setString(3, objective.getObjective());
			pstmt.executeUpdate();
			// If the objective is updated then the last edited for that review has to be
			// edited
			sql = "UPDATE review SET last_edited = DATETIME()";
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			pstmt2.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;

	}

	public boolean updateObjective(ReviewRecord reviewRecord, Objective objective) {
		String sql;

		sql = "UPDATE objectives SET username = ?, rID = ?, objective = ?, date_created = DATETIME() WHERE oID = ?";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			pstmt.setString(1, reviewRecord.getUsername());
			pstmt.setInt(2, reviewRecord.getrID());
			pstmt.setString(3, objective.getObjective());
			pstmt.setInt(4, objective.getoId());
			pstmt.executeUpdate();
			// If the objective is updated then the last edited for that review has to be
			// edited
			sql = "UPDATE review SET last_edited = DATETIME()";
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			pstmt2.executeUpdate();
			return true;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Check if the objective trying to be edited is part of an already completed
	 * review
	 * 
	 * @param reviewID - The review to check for if it has been approved
	 * @return True or false based on the result of the check
	 */
	public boolean checkObjective(int reviewID) {
		// Check if the objectives are linked to an approved review
		String sql = "SELECT DISTINCT approved FROM review, objectives WHERE review.rID = ? AND review.rID = objectives.rID";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Set the value for the prepared statement
			pstmt.setInt(1, reviewID);
			ResultSet rs = pstmt.executeQuery();
			// check the returned value to see if it is allowed to be edited, 0 means not
			// approved and 1 means approved
			while (rs.next()) {
				if (rs.getInt(1) == 0) {
					return true;
				} else {
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Private Method Connect to the database
	 * 
	 * @return the Connection object
	 */
	private Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:DataBase/" + database;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
}
