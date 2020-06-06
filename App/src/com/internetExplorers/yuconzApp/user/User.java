/**
 * 
 * User Class
 * @author Jordan Montford Robinson
 * @version 0.1
 */
package com.internetExplorers.yuconzApp.user;

public class User {

	private String username;
	private String password;
	private String position;
	private String section;
	private String jobTitle;

	/**
	 * 
	 * Constructor, initialises username, password and position
	 * 
	 * @param username
	 * @param password
	 * @param position
	 */
	public User(String username, String password, String position, String section, String jobTitle) {
		this.username = username;
		this.password = password;
		this.position = position;
		this.section = section;
		this.jobTitle = jobTitle;
	}

	/**
	 * Getter for username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Getter for password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Getter for position
	 * 
	 * @return position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Getter for section
	 * 
	 * @return section
	 */
	
	public String getSection() {
		return section;
	}

	/**
	 * Getter for jobTitle
	 * 
	 * @return jobTitle
	 */
	
	public String getJobTitle() {
		return jobTitle;
	}

}
