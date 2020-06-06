package com.internetExplorers.yuconzApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import com.internetExplorers.yuconzApp.user.PersonalDetails;
import com.internetExplorers.yuconzApp.user.ReviewRecord;
import com.internetExplorers.yuconzApp.user.User;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class Options {

	private String username;
	private JFrame frame;
	private MainController mc;
	private User user;

	private JLabel labelUsername = new JLabel();
	private JLabel lblAccess = new JLabel("");

	/**
	 * Create the application.
	 */
	public Options(MainController mc) {
		this.mc = mc;
		initialize();
		frame.setVisible(false);
	}

	/**
	 * Create frame
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setTitle("Options");
		frame.setBounds(100, 100, 572, 358);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lbltext = new JLabel("Logged in as");
		JButton btnLogout = new JButton("Logout");
		JButton btnReadOwnPersonal = new JButton("Read personal record");
		JButton btnReadOtherPersonal = new JButton("Read other personal records");
		JButton btnReadOtherReview = new JButton("Read other review records");
		JButton btnReadOwnReview = new JButton("Read current review");
		JButton btnCreatePersonalRecord = new JButton("Create personal record");
		JButton btnCreateReviewRecord = new JButton("Create review record");

		/**
		 * Allows for HR employees and directors to access a list of other personal
		 * records for reading/editing If the person is not either of these positions
		 * then they are unable to access this form.
		 */
		btnReadOtherPersonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] permisions = { "hr_employee", "director" };
				if (MainController.getAuthor().authorisationCheck(user.getUsername(), user.getPosition(), permisions,
						"Listing personal records")) {
					MainController.getPersonalSelector().setUser(user);
					MainController.getPersonalSelector().getFrame().setVisible(true);
					MainController.getPersonalSelector().populate(user);
				} else {
					lblAccess.setText("Access denied: Insufficient access rights");
				}
			}
		});

		/**
		 * Allows the user to the read the current in progress review if there is one.
		 * If there is not one then the user is prompted with a message saying that no
		 * review was found. Show the update button and hide the approve button since
		 * its their own review.
		 */
		btnReadOwnReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainController.getReview().getFrame().setVisible(true);
				MainController.getReview().setUser(user);
				MainController.getReview().currentReview(user.getUsername());
				MainController.getReview().showUpdate();
				MainController.getReview().hideApprove();

			}
		});

		/**
		 * Allows for a person logged in to read their own personal record. Gets the
		 * details of the personal details using the readPersonalDetails function in the
		 * main controller. Sets the personal record frame to be visible and sets the
		 * user viewing (this is for allowing for permission checks). Also sets the
		 * username of the employee record they are viewing, this is for when a user is
		 * viewing someone elses record but in this case they are viewing their own so
		 * its just the current logged in users username. sets the buttons to being
		 * hidden as they are reading their own record.
		 */

		btnReadOwnPersonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainController.getPersonal().getFrame().setVisible(true);
				MainController.getPersonal().setUser(user);
				MainController.getPersonal().setEmployee(username);
				MainController.getPersonal().populate(username, user);
				MainController.getPersonal().hideBoth();
				MainController.getPersonal().disableAll();
			}
		});

		/**
		 * Show the review selector and populate the review selector form with the
		 * appropriate data
		 */
		btnReadOtherReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainController.getReviewSelector().getFrame().setVisible(true);
				MainController.getReviewSelector().setUser(user);
				MainController.getReviewSelector().populate(user);
			}
		});

		lbltext.setBounds(57, 11, 76, 14);
		frame.getContentPane().add(lbltext);

		labelUsername.setBounds(290, 11, 48, 14);
		frame.getContentPane().add(labelUsername);

		btnReadOwnPersonal.setBounds(10, 48, 211, 23);
		frame.getContentPane().add(btnReadOwnPersonal);

		btnReadOtherPersonal.setBounds(10, 100, 211, 23);
		frame.getContentPane().add(btnReadOtherPersonal);

		btnReadOwnReview.setBounds(231, 48, 193, 23);
		frame.getContentPane().add(btnReadOwnReview);

		btnReadOtherReview.setBounds(231, 100, 193, 23);
		frame.getContentPane().add(btnReadOtherReview);

		/**
		 * Log the user out using the logout function in the main controller. Hide all
		 * the frames from the user when they logout and show them the login frame
		 */
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mc.logOut(user.getUsername());
				System.exit(0);
			}
		});
		btnLogout.setBounds(21, 227, 180, 23);
		frame.getContentPane().add(btnLogout);

		/**
		 * Open the personal form with the relevant update items. The current details in
		 * the form are emptied and the create button is made visible. The result text
		 * is set to being empty again. HR employees can create personal records of
		 * other users
		 */

		btnCreatePersonalRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] permissions = { "hr_employee" };
				if (MainController.getAuthor().authorisationCheck(user.getUsername(), user.getPosition(), permissions,
						"Creating a personal record")) {
					MainController.getPersonal().getFrame().setVisible(true);
					MainController.getPersonal().setUser(user);
					MainController.getPersonal().empty();
					MainController.getPersonal().showCreate();
					MainController.getPersonal().unlockAll();
				} else {
					showMessageDialog(null, "Only HR employees are allowed to create personal records");
				}
			}
		});

		btnCreatePersonalRecord.setBounds(10, 156, 211, 23);
		frame.getContentPane().add(btnCreatePersonalRecord);

		/**
		 * Create a review record based on the logged in users information and attempt
		 * to add it to the database
		 */

		btnCreateReviewRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReviewRecord tempRecord = new ReviewRecord(0, user.getUsername(), null, null, user.getSection(),
						user.getJobTitle(), null, null, null, null, null, 0, 0, 0, 0);
				if (mc.requestNewReviewRecord(tempRecord)) {
					// Now we can just act like they are reading their own current review
					MainController.getReview().getFrame().setVisible(true);
					MainController.getReview().setUser(user);
					MainController.getReview().currentReview(tempRecord.getUsername());
					MainController.getReview().showUpdate();
					MainController.getReview().hideApprove();
					showMessageDialog(null,
							"Review successfuly created: To re-open use the options and select 'Read own review'");

				} else {
					showMessageDialog(null, "Request failed: There may already be an active review");
				}
			}
		});
		btnCreateReviewRecord.setBounds(231, 156, 193, 23);
		frame.getContentPane().add(btnCreateReviewRecord);

		lblAccess.setBounds(231, 231, 315, 47);
		frame.getContentPane().add(lblAccess);

	}

	/**
	 * Sets the user for the form and updates the username label
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
		username = user.getUsername();
		labelUsername.setText(username);

	}

	public JFrame getFrame() {
		return frame;
	}
}
