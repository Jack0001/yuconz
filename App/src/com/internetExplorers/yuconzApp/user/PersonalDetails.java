package com.internetExplorers.yuconzApp.user;

/**
 * 
 * Personal Details Class used to create objects for use in the program
 * @author Jack Oliver
 * @version 0.1
 */
public class PersonalDetails {


	private int pdId;
	private String username;
	private String surname;
	private String name;
	private String dob;
	private String address;
	private String townCity;
	private String county;
	private String postCode;
	private String telephoneNo;
	private String mobileNo;
	private String emergencyContact;
	private String emergencyContactNo;

	/**
	 * Constructor for a personal details object built with the results from the SQL
	 */
	
	public PersonalDetails(int pdId, String username, String surname, String name, String dob, String address,
			String townCity, String county, String postCode, String telephoneNo, String mobileNo,
			String emergencyContact, String emergencyContactNo) {
		this.pdId = pdId;
		this.username = username;
		this.surname = surname;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.townCity = townCity;
		this.county = county;
		this.postCode = postCode;
		this.telephoneNo = telephoneNo;
		this.mobileNo = mobileNo;
		this.emergencyContact = emergencyContact;
		this.emergencyContactNo = emergencyContactNo;
	}
	
	public int getPdId() {
		return pdId;
	}

	public String getUsername() {
		return username;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public String getDob() {
		return dob;
	}

	public String getAddress() {
		return address;
	}

	public String getTownCity() {
		return townCity;
	}

	public String getCounty() {
		return county;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}

}
