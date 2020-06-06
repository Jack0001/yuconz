/**
 * 
 * AuthenticationServer authenticates users.
 * @author Jordan Montford Robinson
 * @version 0.1
 */
package com.internetExplorers.yuconzApp;

import sql.database.AccessRightsTableMethods;
import sql.database.UsersTableMethods;

public class AuthenticationServer {

	private UsersTableMethods userTable;
	private AccessRightsTableMethods accessRightsTable;

	/**
	 * Constructor, tries to initialise the login file and login.
	 */
	public AuthenticationServer(String database) {

		userTable = new UsersTableMethods(database);
		accessRightsTable = new AccessRightsTableMethods(database);
	}

	/**
	 * Attempts to log in the user using the information provided
	 * 
	 * @param username username
	 * @param password password
	 * @return user details including user's position or null
	 */
	public String[] login(String username, String password) {
		String[] user = userTable.findUserByUsername(username);
		// Check if the username and password entered are valid by attempting to find
		// the username with the matching password
		// If one cant be found then the attempt at logging in is logged in the database
		// as "N"
		// If the database finds a user and the password matches then they are logged in
		// and the attempt is logged with "Y"
		if (user[0].equals("")) {
			accessRightsTable.logAttempt(username, 0);
			user[0] = "incorrect username";
		} else if (user[1].equals(password)) {
			accessRightsTable.logAttempt(username, 1);
		} else {
			user[0] = "incorrect password";
			accessRightsTable.logAttempt(username, 0);
		}

		return user;
	}
}
