package com.internetExplorers.yuconzApp;

import sql.database.AccessRightsTableMethods;

/**
 * 
 * Authorisation server used to check if users have the correct authorisation
 * for the actions they want to do.
 * @author Jack Oliver
 */

public class Authorisation {

	private AccessRightsTableMethods accessRightsTable;

	/**
	 * Constructor, tries to initialise the login file and login.
	 */
	public Authorisation(String database) {
		accessRightsTable = new AccessRightsTableMethods(database);
	}

	/**
	 * Log any attempt at authorisation, the permissions they are trying to get and
	 * the action they are trying to do.
	 * 
	 * @param username        - The username of the user trying to do an action
	 * @param position        - The users position or authorisation level
	 * @param permissions     - The correct permissions for the attempted access
	 * @param attemptedAction - The action they are trying to perform
	 * @return
	 */
	public boolean authorisationCheck(String username, String position, String[] permissions, String attemptedAction) {
		boolean pass = false;
		String passedPermission = "";
		String failedPermission = "";
		for (String permission : permissions) {
			if (accessRightsTable.hasRights(position, permission) != null) {
				pass = true;
				passedPermission = permission;
			} else {
				failedPermission = permission;
			}
		}
		
		if(pass == true) {
			accessRightsTable.logAuthorisation(username, passedPermission, attemptedAction, 1);
			return true;
		} else {
			accessRightsTable.logAuthorisation(username, failedPermission, attemptedAction, 0);
		}
		
		return false;

	}
}
