package com.internetExplorers.yuconzApp;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.internetExplorers.yuconzApp.user.PersonalDetails;
import com.internetExplorers.yuconzApp.user.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import static javax.swing.JOptionPane.showMessageDialog;


/**
 * 
 * Personal records form.
 * @author Jack Oliver
 */
public class Personal {

	private JFrame frmPersonal;
	private MainController mc;
	private User user;
	private JTextField textSurname = new JTextField();
	private JTextField textName = new JTextField();
	private JTextField textDOB = new JTextField();
	private JTextField textAddress = new JTextField();
	private JTextField textTownCity = new JTextField();
	private JTextField textCounty = new JTextField();
	private JTextField textPostcode = new JTextField();
	private JTextField textTelephoneNumber = new JTextField();
	private JTextField textMobileNumber = new JTextField();
	private JTextField textEmergencyContact = new JTextField();
	private JTextField textEmergencyContactNumber = new JTextField();
	private JLabel lblPersonalRecordOf = new JLabel("Personal record of");
	private JLabel lblSurname = new JLabel("Surname");
	private JLabel lblName = new JLabel("Name");
	private JLabel lblDateOfBirth = new JLabel("Date of Birth");
	private JLabel lblAddress = new JLabel("Address");
	private JLabel lblTowncity = new JLabel("Town/City");
	private JLabel lblCounty = new JLabel("County");
	private JLabel lblPostcode = new JLabel("Postcode");
	private JLabel lblTelephoneNumber = new JLabel("Telephone number");
	private JLabel lblMobileNumber = new JLabel("Mobile number");
	private JLabel lblEmergencyContactNumber = new JLabel("Emergency contact number");
	private JLabel lblEmergencyContact = new JLabel("Emergency contact");
	private JButton btnUpdate = new JButton("Update");
	private JButton btnCreate = new JButton("Create");
	private JLabel labelUsername = new JLabel("");
	private PersonalDetails personalDetails;
	private JLabel lblResult = new JLabel("");
	private JTextField textUsername;

	/**
	 * Create the application.
	 */
	public Personal(MainController mc) {
		this.mc = mc;
		initialize();
		frmPersonal.setVisible(false);
		btnUpdate.setVisible(false);
		btnCreate.setVisible(false);
	}

	/**
	 * Initialize the contents of the frmPersonal.
	 */
	private void initialize() {

		frmPersonal = new JFrame();
		frmPersonal.setBounds(100, 100, 700, 700);
		frmPersonal.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmPersonal.getContentPane().setLayout(null);

		lblPersonalRecordOf.setBounds(47, 11, 211, 14);
		frmPersonal.getContentPane().add(lblPersonalRecordOf);

		labelUsername.setBounds(290, 11, 259, 14);
		frmPersonal.getContentPane().add(labelUsername);

		lblSurname.setBounds(47, 50, 200, 14);
		frmPersonal.getContentPane().add(lblSurname);

		lblName.setBounds(47, 100, 200, 14);
		frmPersonal.getContentPane().add(lblName);

		lblDateOfBirth.setBounds(47, 150, 200, 14);
		frmPersonal.getContentPane().add(lblDateOfBirth);

		lblAddress.setBounds(47, 200, 200, 14);
		frmPersonal.getContentPane().add(lblAddress);

		lblTowncity.setBounds(47, 250, 200, 14);
		frmPersonal.getContentPane().add(lblTowncity);

		lblCounty.setBounds(47, 300, 200, 14);
		frmPersonal.getContentPane().add(lblCounty);

		lblPostcode.setBounds(47, 350, 200, 14);
		frmPersonal.getContentPane().add(lblPostcode);

		lblTelephoneNumber.setBounds(47, 400, 200, 14);
		frmPersonal.getContentPane().add(lblTelephoneNumber);

		lblMobileNumber.setBounds(47, 450, 200, 14);
		frmPersonal.getContentPane().add(lblMobileNumber);

		lblEmergencyContact.setBounds(47, 500, 200, 14);
		frmPersonal.getContentPane().add(lblEmergencyContact);

		lblEmergencyContactNumber.setBounds(47, 550, 200, 14);
		frmPersonal.getContentPane().add(lblEmergencyContactNumber);

		textSurname.setBounds(290, 47, 259, 20);
		frmPersonal.getContentPane().add(textSurname);
		textSurname.setColumns(10);

		textName.setBounds(290, 97, 259, 20);
		frmPersonal.getContentPane().add(textName);
		textName.setColumns(10);

		textDOB.setBounds(290, 147, 96, 20);
		frmPersonal.getContentPane().add(textDOB);
		textDOB.setColumns(10);

		textAddress.setBounds(290, 197, 259, 39);
		frmPersonal.getContentPane().add(textAddress);
		textAddress.setColumns(10);

		textTownCity.setBounds(290, 247, 259, 20);
		frmPersonal.getContentPane().add(textTownCity);
		textTownCity.setColumns(10);

		textCounty.setColumns(10);
		textCounty.setBounds(290, 297, 259, 20);
		frmPersonal.getContentPane().add(textCounty);

		textPostcode.setColumns(10);
		textPostcode.setBounds(290, 347, 259, 20);
		frmPersonal.getContentPane().add(textPostcode);

		textTelephoneNumber.setColumns(10);
		textTelephoneNumber.setBounds(290, 394, 259, 20);
		frmPersonal.getContentPane().add(textTelephoneNumber);

		textMobileNumber.setColumns(10);
		textMobileNumber.setBounds(290, 447, 259, 20);
		frmPersonal.getContentPane().add(textMobileNumber);

		textEmergencyContact.setColumns(10);
		textEmergencyContact.setBounds(290, 497, 259, 20);
		frmPersonal.getContentPane().add(textEmergencyContact);

		textEmergencyContactNumber.setColumns(10);
		textEmergencyContactNumber.setBounds(290, 547, 259, 20);
		frmPersonal.getContentPane().add(textEmergencyContactNumber);

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonalDetails newPersonalDetails = new PersonalDetails(personalDetails.getPdId(),
						personalDetails.getUsername(), textSurname.getText(), textName.getText(), textDOB.getText(),
						textAddress.getText(), textTownCity.getText(), textCounty.getText(), textPostcode.getText(),
						textTelephoneNumber.getText(), textMobileNumber.getText(), textEmergencyContact.getText(),
						textEmergencyContactNumber.getText());
				if (mc.amendPersonalDetails(user, newPersonalDetails)) {
					lblResult.setText("Successful update");
				} else {
					lblResult.setText("Failed to update");
				}

			}
		});

		btnUpdate.setBounds(314, 627, 89, 23);
		frmPersonal.getContentPane().add(btnUpdate);

		lblResult.setBounds(10, 627, 341, 23);
		frmPersonal.getContentPane().add(lblResult);

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Check if the form is completed or has a value in each field
				if (textSurname.getText() != null && textName.getText() != null && textSurname.getText() != null
						&& textName.getText() != null && textDOB.getText() != null && textAddress.getText() != null
						&& textTownCity.getText() != null && textCounty.getText() != null
						&& textPostcode.getText() != null && textTelephoneNumber.getText() != null
						&& textMobileNumber.getText() != null && textPostcode.getText() != null
						&& textTelephoneNumber.getText() != null && textMobileNumber.getText() != null
						&& textPostcode.getText() != null && textEmergencyContact.getText() != null
						&& textEmergencyContactNumber.getText() != null) {
					
					//The id is 0 because it is created by the database
					PersonalDetails newPersonalDetails = new PersonalDetails(0,
							textUsername.getText(), textSurname.getText(), textName.getText(), textDOB.getText(),
							textAddress.getText(), textTownCity.getText(), textCounty.getText(), textPostcode.getText(),
							textTelephoneNumber.getText(), textMobileNumber.getText(), textEmergencyContact.getText(),
							textEmergencyContactNumber.getText());

					if (mc.requestNewPersonalDetails(newPersonalDetails, user)) {
						lblResult.setText("Successful create");
					} else {
						lblResult.setText("Failed to create");
					}
				} else {
					showMessageDialog(null, "Please complete the form");
				}
			}
		});
		btnCreate.setBounds(460, 627, 89, 23);
		frmPersonal.getContentPane().add(btnCreate);

		textUsername = new JTextField();
		textUsername.setBounds(290, 8, 259, 20);
		frmPersonal.getContentPane().add(textUsername);
		textUsername.setColumns(10);
	}

	/**
	 * Populate the form with the relevant data
	 * 
	 * @param username
	 * @param reader
	 */
	public void populate(String username, User reader) {

		ArrayList<PersonalDetails> tempDetails = mc.readPersonalDetails(username, reader, "one");

		if (tempDetails == null || tempDetails.isEmpty()) {
			lblResult.setText("Database failure: could not find records");
			hideBoth();
		} else {

			// if the user is a hr employee they are allowed to create personal records of
			// users
			if (user.getPosition().equals("hr_employee")) {
				labelUsername.setVisible(false);
				textUsername.setVisible(true);
			}

			personalDetails = tempDetails.get(0);
			textSurname.setText(personalDetails.getSurname());
			textName.setText(personalDetails.getName());
			textDOB.setText(personalDetails.getDob());
			textAddress.setText(personalDetails.getAddress());
			textTownCity.setText(personalDetails.getTownCity());
			textCounty.setText(personalDetails.getCounty());
			textPostcode.setText(personalDetails.getPostCode());
			textTelephoneNumber.setText(personalDetails.getTelephoneNo());
			textMobileNumber.setText(personalDetails.getMobileNo());
			textEmergencyContact.setText(personalDetails.getEmergencyContact());
			textEmergencyContactNumber.setText(personalDetails.getEmergencyContactNo());
		}

	}

	/**
	 * Empty the frames contents for reuse
	 */
	public void empty() {
		textUsername.setText("");
		textSurname.setText("");
		textName.setText("");
		textDOB.setText("");
		textAddress.setText("");
		textTownCity.setText("");
		textCounty.setText("");
		textPostcode.setText("");
		textTelephoneNumber.setText("");
		textMobileNumber.setText("");
		textEmergencyContact.setText("");
		textEmergencyContactNumber.setText("");
		lblResult.setText("");

	}

	/**
	 * Show the create button and hide the update button
	 */
	public void showCreate() {
		btnUpdate.setVisible(false);
		btnCreate.setVisible(true);
	}

	/**
	 * Show the update button and hide the create button
	 */
	public void showUpdate() {
		btnCreate.setVisible(false);
		btnUpdate.setVisible(true);
	}

	/**
	 * Hides both the update and create buttons in the case someone is reading a
	 * record they cannot edit
	 */
	public void hideBoth() {
		btnCreate.setVisible(false);
		btnUpdate.setVisible(false);
	}

	/**
	 * Set the user for use in the form
	 * 
	 * @param user - The current logged in user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Getter for the frame
	 * 
	 * @return The JFrame
	 */
	public JFrame getFrame() {
		return frmPersonal;
	}

	/**
	 * Set the employee for the form This is needed as a logged in user may be able
	 * to read others personal details
	 * 
	 * @param employee - The employee whos record is being used
	 */
	public void setEmployee(String employee) {
		textUsername.setText(employee);
	}

	/**
	 * Lock the username from being edited. This is so an HR employee cant change
	 * the username related to an already created personal record
	 */
	public void lockUsername() {
		textUsername.setEnabled(false);
	}
	
	public void disableAll() {
		textUsername.setEnabled(false);
		textSurname.setEnabled(false);
		textName.setEnabled(false);
		textDOB.setEnabled(false);
		textAddress.setEnabled(false);
		textTownCity.setEnabled(false);
		textCounty.setEnabled(false);
		textPostcode.setEnabled(false);
		textTelephoneNumber.setEnabled(false);
		textMobileNumber.setEnabled(false);
		textEmergencyContact.setEnabled(false);
		textEmergencyContactNumber.setEnabled(false);
		lblResult.setEnabled(false);
	}
	
	public void unlockAll(){
			textUsername.setEnabled(true);
			textSurname.setEnabled(true);
			textName.setEnabled(true);
			textDOB.setEnabled(true);
			textAddress.setEnabled(true);
			textTownCity.setEnabled(true);
			textCounty.setEnabled(true);
			textPostcode.setEnabled(true);
			textTelephoneNumber.setEnabled(true);
			textMobileNumber.setEnabled(true);
			textEmergencyContact.setEnabled(true);
			textEmergencyContactNumber.setEnabled(true);
			lblResult.setEnabled(true);
		
	}
}
