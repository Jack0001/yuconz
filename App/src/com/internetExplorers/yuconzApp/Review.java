package com.internetExplorers.yuconzApp;

import java.awt.EventQueue;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.internetExplorers.yuconzApp.user.Objective;
import com.internetExplorers.yuconzApp.user.PersonalDetails;
import com.internetExplorers.yuconzApp.user.ReviewRecord;
import com.internetExplorers.yuconzApp.user.User;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTextPane;

/**
 * 
 * Review form.
 * @author Jack Oliver
 */
public class Review {

	private JFrame frmReview;
	private MainController mc;
	private User user;
	private JLabel lblReviewerText = new JLabel("");
	private JButton btnEditObjective = new JButton("Edit objective");
	private JLabel lblReviewer2Text = new JLabel("");
	private JLabel lblSectionText = new JLabel("");
	private JLabel lblJobTitleText = new JLabel("");
	private JLabel lblPerformanceReviewOf = new JLabel("Performance review of");
	private JLabel lblUsername = new JLabel("");
	private JLabel lblReviewer = new JLabel("Reviewer");
	private JLabel lblReviewer2 = new JLabel("Second Reviewer");
	private JLabel lblSection = new JLabel("Section");
	private JLabel lblJobTitle = new JLabel("Job Title");
	private JLabel lblPreviousReviewObjectives = new JLabel("Review objectives, outcomes and date set");
	private JButton btnAddObjective = new JButton("Add objective");
	private JLabel lblSummary = new JLabel("Summary");
	private JLabel lblRevieweeSignature = new JLabel("Reviewee Signature");
	private JLabel lblReviewerSignature = new JLabel("Reviewer Signature");
	private JLabel lblReviewer2Signature = new JLabel("Second Reviewer Signature");
	private List list = new List();
	private JLabel lblResult = new JLabel("");
	private String[] recommendations = { "Stay", "Raise", "Promotion", "Probation", "Termination" };
	private JComboBox comboRecommendation = new JComboBox(recommendations);
	private JLabel lblRecommendation = new JLabel("Recommendation");
	private JButton btnUpdateReview = new JButton("Update review");
	private JCheckBox chckbxReviewee = new JCheckBox("Signed");
	private JCheckBox chckbxReviewer = new JCheckBox("Signed");
	private JCheckBox chckbxReviewer2 = new JCheckBox("Signed");
	private JButton btnApproveReview = new JButton("Sign off Review");
	private JLabel lblComments = new JLabel("Comments");
	private JLabel labelCCReviewer2 = new JLabel("Cant change");
	private JLabel labelCCReviewer = new JLabel("Cant change");
	private JLabel labelCCReviewee = new JLabel("Cant change");
	private JButton btnAssignReviewer = new JButton("Assign Reviewer");
	private JButton btnAssignReviewer2 = new JButton("Assign Second Reviewer");
	private JTextPane textSummary = new JTextPane();
	private JTextPane textComments = new JTextPane();
	private int[] rIdList;
	private int[] oIdList;
	private boolean firstTime = true;

	/**
	 * Create the application.
	 */
	public Review(MainController mc) {
		this.mc = mc;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReview = new JFrame();
		frmReview.setTitle("Review");
		frmReview.setBounds(100, 100, 1000, 667);
		frmReview.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frmReview.getContentPane().setLayout(null);

		lblPerformanceReviewOf.setBounds(10, 11, 184, 14);
		frmReview.getContentPane().add(lblPerformanceReviewOf);

		lblUsername.setBounds(233, 11, 80, 14);
		frmReview.getContentPane().add(lblUsername);

		lblReviewer.setBounds(10, 70, 110, 14);
		frmReview.getContentPane().add(lblReviewer);

		lblReviewer2.setBounds(10, 121, 145, 14);
		frmReview.getContentPane().add(lblReviewer2);

		lblSection.setBounds(10, 166, 110, 14);
		frmReview.getContentPane().add(lblSection);

		lblJobTitle.setBounds(10, 218, 110, 14);
		frmReview.getContentPane().add(lblJobTitle);

		list.setBounds(514, 31, 443, 406);
		frmReview.getContentPane().add(list);

		lblPreviousReviewObjectives.setBounds(581, 11, 311, 14);
		frmReview.getContentPane().add(lblPreviousReviewObjectives);

		btnAddObjective.setBounds(571, 443, 130, 23);
		frmReview.getContentPane().add(btnAddObjective);

		lblSummary.setBounds(10, 280, 110, 14);
		frmReview.getContentPane().add(lblSummary);

		lblRevieweeSignature.setBounds(74, 547, 145, 14);
		frmReview.getContentPane().add(lblRevieweeSignature);

		lblReviewerSignature.setBounds(244, 547, 131, 14);
		frmReview.getContentPane().add(lblReviewerSignature);

		lblReviewer2Signature.setBounds(385, 547, 179, 14);
		frmReview.getContentPane().add(lblReviewer2Signature);

		btnEditObjective.setBounds(766, 443, 145, 23);
		frmReview.getContentPane().add(btnEditObjective);

		lblReviewerText.setBounds(169, 70, 227, 14);
		frmReview.getContentPane().add(lblReviewerText);

		lblReviewer2Text.setBounds(169, 121, 227, 14);
		frmReview.getContentPane().add(lblReviewer2Text);

		lblSectionText.setBounds(169, 166, 227, 14);
		frmReview.getContentPane().add(lblSectionText);

		lblJobTitleText.setBounds(169, 218, 227, 14);
		frmReview.getContentPane().add(lblJobTitleText);

		lblResult.setBounds(495, 494, 397, 30);
		frmReview.getContentPane().add(lblResult);

		lblRecommendation.setBounds(10, 494, 145, 14);
		frmReview.getContentPane().add(lblRecommendation);

		comboRecommendation.setMaximumRowCount(5);
		comboRecommendation.setBounds(217, 490, 96, 22);
		frmReview.getContentPane().add(comboRecommendation);

		btnUpdateReview.setBounds(820, 594, 137, 23);
		frmReview.getContentPane().add(btnUpdateReview);

		chckbxReviewee.setBounds(84, 568, 97, 23);
		frmReview.getContentPane().add(chckbxReviewee);

		chckbxReviewer.setBounds(254, 568, 97, 23);
		frmReview.getContentPane().add(chckbxReviewer);

		chckbxReviewer2.setBounds(441, 568, 97, 23);
		frmReview.getContentPane().add(chckbxReviewer2);

		btnApproveReview.setBounds(659, 594, 135, 23);
		frmReview.getContentPane().add(btnApproveReview);

		lblComments.setBounds(10, 406, 110, 14);
		frmReview.getContentPane().add(lblComments);

		labelCCReviewee.setBounds(74, 598, 107, 14);
		labelCCReviewee.setVisible(false);
		frmReview.getContentPane().add(labelCCReviewee);

		labelCCReviewer.setBounds(244, 598, 107, 14);
		labelCCReviewer.setVisible(false);
		frmReview.getContentPane().add(labelCCReviewer);

		labelCCReviewer2.setBounds(417, 598, 147, 14);
		labelCCReviewer2.setVisible(false);
		frmReview.getContentPane().add(labelCCReviewer2);

		btnAssignReviewer.setBounds(546, 535, 155, 23);
		frmReview.getContentPane().add(btnAssignReviewer);
		btnAssignReviewer.setVisible(true);

		btnAssignReviewer2.setBounds(727, 535, 184, 23);
		btnAssignReviewer2.setVisible(true);
		frmReview.getContentPane().add(btnAssignReviewer2);

		textSummary.setBounds(188, 243, 275, 94);
		frmReview.getContentPane().add(textSummary);

		textComments.setBounds(188, 368, 275, 94);
		frmReview.getContentPane().add(textComments);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public JFrame getFrame() {
		return frmReview;
	}

	public void showUpdate() {
		btnUpdateReview.setVisible(true);
	}

	public void showApprove() {
		btnApproveReview.setVisible(true);
	}

	public void hideUpdate() {
		btnUpdateReview.setVisible(true);
	}

	public void hideApprove() {
		btnApproveReview.setVisible(true);
	}

	/**
	 * Used to populate based on the current review button on the options form Sends
	 * with the id being 0 as the flag for getting the current users in progress
	 * review
	 */
	public void currentReview(String username) {
		ArrayList<ReviewRecord> reviewRecord = mc.readReviewRecord(username, user, 0);

		if (reviewRecord == null || reviewRecord.isEmpty()) {
			lblResult.setText("Database failure: could not find records");
		} else {
			if (firstTime) {
				initialiseEvents(reviewRecord.get(0));
				firstTime = false;
			}
			populate(reviewRecord.get(0));
			lblResult.setText("Successful Read");
		}
	}

	/**
	 * Used to populate based on reading another persons review. Since they are
	 * reading then everything can be disabled
	 * 
	 * @param username - The username of the reviewee being read
	 * @param user     - The username of the current logged in user
	 * @param id       - The id of the review to read
	 */
	public void readOtherReview(String username, User user, int id) {
		ArrayList<ReviewRecord> reviewRecord = mc.readReviewRecord(username, user, id);

		if (reviewRecord == null || reviewRecord.isEmpty()) {
			lblResult.setText("Database failure: could not find records");
		} else {
			if (firstTime) {
				initialiseEvents(reviewRecord.get(0));
				firstTime = false;
			}
			populate(reviewRecord.get(0));
			lblResult.setText("Successful read");
		}

	}

	/**
	 * Populate the frame with the review record passed
	 * 
	 * @param reviewRecord - The review record to use to populate the frame
	 */
	public void populate(ReviewRecord reviewRecord) {
		list.removeAll();
		frmReview.getContentPane().add(list);

		refreshObjectives(reviewRecord);

		lblUsername.setText(reviewRecord.getUsername());
		lblReviewerText.setText(reviewRecord.getReviewer());
		lblReviewer2Text.setText(reviewRecord.getSecondReviewer());
		lblSectionText.setText(reviewRecord.getSection());
		lblJobTitleText.setText(reviewRecord.getJobTitle());
		textSummary.setText(reviewRecord.getSummary());
		textComments.setText(reviewRecord.getComments());
		comboRecommendation.setSelectedItem(reviewRecord.getRecommendation());

		// Disable everything and enable it with the features the user can use
		textSummary.setEnabled(false);
		textComments.setEnabled(false);
		comboRecommendation.setEnabled(false);
		chckbxReviewee.setEnabled(false);
		chckbxReviewer.setEnabled(false);
		chckbxReviewer2.setEnabled(false);
		btnAddObjective.setEnabled(false);
		btnEditObjective.setEnabled(false);
		btnAssignReviewer.setEnabled(false);
		btnAssignReviewer2.setEnabled(false);
		btnApproveReview.setEnabled(false);
		btnUpdateReview.setEnabled(false);

		if (reviewRecord.getApproved() == 0) {
			// Check to make the check boxes ticked if they are signed
			// False/True in the database are represented by ints of 0/1 respectively
			if (reviewRecord.getSignedReviewee() == 1) {
				chckbxReviewee.setSelected(true);
			} else {
				chckbxReviewee.setSelected(false);
			}

			if (reviewRecord.getSignedReviewer() == 1) {
				chckbxReviewer.setSelected(true);
			} else {
				chckbxReviewer.setSelected(false);
			}

			if (reviewRecord.getSignedReviewer2() == 1) {
				chckbxReviewer2.setSelected(true);
			} else {
				chckbxReviewer2.setSelected(false);
			}

			// Check to see if the user is a review participant so they can edit
			// Check to make the check boxes disabled if they are not the logged in user
			// Example: Someone who is not the reviewee cannot update the reviewee signed
			// box
			// Reviewers are allowed to attempt approval

			if (user.getUsername().equals(lblUsername.getText()) || user.getUsername().equals(lblReviewerText.getText())
					|| user.getUsername().equals(lblReviewer2Text.getText())) {
				textSummary.setEnabled(true);
				textComments.setEnabled(true);
				comboRecommendation.setEnabled(true);
				btnAddObjective.setEnabled(true);
				btnEditObjective.setEnabled(true);
				btnUpdateReview.setEnabled(true);

				if (user.getUsername().equals(lblUsername.getText())) {
					chckbxReviewee.setEnabled(true);
				} else {
					chckbxReviewee.setEnabled(false);

					if (user.getUsername().equals(lblReviewerText.getText())) {
						chckbxReviewer.setEnabled(true);
						btnApproveReview.setEnabled(true);
					} else {
						chckbxReviewer.setEnabled(false);
					}

					if (user.getUsername().equals(lblReviewerText.getText())) {
						chckbxReviewer2.setEnabled(true);
						btnApproveReview.setEnabled(true);
					} else {
						chckbxReviewer2.setEnabled(false);
					}
				}
			}

			// Only hr can assign a reviewer to a review so only make it visible for them

			if (user.getPosition().equals("hr_employee")) {
				btnAssignReviewer.setEnabled(true);
				btnAssignReviewer2.setEnabled(true);
				btnApproveReview.setEnabled(true);
			} else {
				btnAssignReviewer.setEnabled(false);
				btnAssignReviewer2.setEnabled(false);
			}

		}
	}

	public void initialiseEvents(ReviewRecord reviewRecord) {
		// Remove all event listeners to be reinitialised
		// btnApproveReview.removeActionListener(btnApproveReview.getAc());

		/**
		 * Attempt to sign off the review This is done here as the reviewRecord is
		 * needed
		 */
		btnApproveReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check if the review is approved - It cant be edited when it is
				if (!checkIfApproved(reviewRecord)) {
					// Check if the review has been signed off by all parties
					if (chckbxReviewee.isSelected() && chckbxReviewer.isSelected() && chckbxReviewer2.isSelected()) {
						// The review should be up to date before being approved
						update(reviewRecord);
						// Extra confirmation that the user would like to approve the review
						Object[] options = { "Yes", "No" };
						int choice = JOptionPane.showOptionDialog(getFrame(),
								"Are you sure? approving a review makes it un-changeable", null,
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
								options[1]);
						// Check if the user is a reviewer or secondary reviewer for signing off
						if (choice == 0) {
							if (user.getPosition().equals("hr_employee")) {
								if (mc.signOffReview(reviewRecord.getUsername(), user)) {
									lblResult.setText("Successfully signed off: This record can no longer be edited");
									btnApproveReview.setEnabled(false);
								} else {
									lblResult.setText(
											"Failed to sign off review - Remember to update before signing off");
								}
							} else {
								lblResult.setText("You cannot sign off this review");
							}

						}
					} else {
						showMessageDialog(null, "This review is already approved");
					}
				}
			}
		});

		/**
		 * Prompt the user with an input box to add a reviewer by username to the
		 * current review
		 * 
		 */
		btnAssignReviewer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				assignReviewer(reviewRecord, false);
			}
		});

		/**
		 * Same as above, but for a secondary reviewer
		 */
		btnAssignReviewer2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				assignReviewer(reviewRecord, true);
			}
		});

		btnUpdateReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(reviewRecord);
			}
		});

		btnEditObjective.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() == -1) {
					showMessageDialog(null, "Select an objective to edit");
				} else {
					String currentObjective = list.getSelectedItem();
					int rid = rIdList[list.getSelectedIndex()];
					int oid = oIdList[list.getSelectedIndex()];
					// Check if the current review is approved and check if the review the objective
					// was created in is approved - It cant be edited when it is
					if (!checkIfApproved(reviewRecord) && mc.objectivesCheck(reviewRecord, rid, user)) {
						String tempObjective = (String) JOptionPane.showInputDialog(getFrame(), "Enter Objective",
								"Enter objective", JOptionPane.PLAIN_MESSAGE, null, null, currentObjective);
						if ((tempObjective != null) && (tempObjective.length() > 0)) {
							Objective objective = new Objective(oid, reviewRecord.getUsername(), rid, tempObjective,
									"");
							mc.ammendObjective(reviewRecord, objective, user);
							refreshObjectives(reviewRecord);
						}
					}
				}
			}
		});

		btnAddObjective.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check if the review is approved - It cant be edited when it is
				if (!checkIfApproved(reviewRecord)) {
					// Create an input box for the user to put in the reviewers username
					String tempObjective = (String) JOptionPane.showInputDialog(getFrame(), "Enter Objective",
							"Enter objective", JOptionPane.PLAIN_MESSAGE, null, null, null);
					if ((tempObjective != null) && (tempObjective.length() > 0)) {
						Objective objective = new Objective(0, reviewRecord.getUsername(), reviewRecord.getrID(),
								tempObjective, "");
						mc.insertObjective(reviewRecord, objective, user);
						refreshObjectives(reviewRecord);
						// populate(reviewRecord);
					}
				}

			}
		});
	}

	/**
	 * Simple check if the review is approved
	 * 
	 * @param reviewRecord - The review record to check
	 * @return - True of false based on the result
	 */
	public boolean checkIfApproved(ReviewRecord reviewRecord) {
		if (reviewRecord.getApproved() == 1) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Assigns the reviewer to the correct position on the review using the boolean
	 * as a base
	 * 
	 * @param reviewRecord - The review record to assign the reviewer to
	 * @param secondary    - True if secondary reviewer False if primary
	 * @return True or false based on the result of the assignment
	 */
	public void assignReviewer(ReviewRecord reviewRecord, boolean secondary) {
		// Check if the review is approved - It cant be edited when it is
		if (!checkIfApproved(reviewRecord)) {
			// Only hr employees are allowed to assign reviewers to a review
			String[] permissions = { "hr_employee" };
			if (MainController.getAuthor().authorisationCheck(user.getUsername(), user.getPosition(), permissions, "Assigning reviewer")) {

				// Create an input box for the user to put in the reviewers username
				String reviewer = (String) JOptionPane.showInputDialog(getFrame(), "Enter reviewer");

				// If a string was returned then try to update the reviewer. Message dialogs are
				// used this time as the form is updated when successful
				if ((reviewer != null) && (reviewer.length() > 0)) {
					if (mc.recordAllocatedReviewer(reviewRecord, user, reviewer, secondary)) {
						showMessageDialog(null, "Successful update. Refresh the review to see changes");

					} else {
						showMessageDialog(null, "Failed to update");
					}
				}
			} else {
				showMessageDialog(null, "You are not allowed to assign a reviewer");
			}
		}

	}

	public void update(ReviewRecord reviewRecord) {
		// Can only be updated by review participants
		if (user.getUsername().equals(reviewRecord.getUsername())
				|| user.getUsername().equals(reviewRecord.getReviewer())
				|| user.getUsername().equals(reviewRecord.getSecondReviewer())) {
			// Check if the review is approved - It cant be edited when it is
			if (!checkIfApproved(reviewRecord)) {
				// Checking if the boxes are selected for appropriate changes
				int signedReviewee = 0;
				int signedReviewer = 0;
				int signedReviewer2 = 0;
				String recommendation = (String) comboRecommendation.getSelectedItem();

				if (chckbxReviewee.isSelected()) {
					signedReviewee = 1;
				} else {
					signedReviewee = 0;
				}

				if (chckbxReviewer.isSelected()) {
					signedReviewer = 1;
				} else {
					signedReviewer = 0;
				}

				if (chckbxReviewer2.isSelected()) {
					signedReviewer2 = 1;
				} else {
					signedReviewer2 = 0;
				}

				ReviewRecord tempRecord = new ReviewRecord(reviewRecord.getrID(), lblUsername.getText(),
						lblReviewerText.getText(), lblReviewer2Text.getText(), lblSectionText.getText(),
						lblJobTitleText.getText(), textSummary.getText(), recommendation, textComments.getText(),
						reviewRecord.getDate_created(), reviewRecord.getLast_edited(), signedReviewee, signedReviewer,
						signedReviewer2, reviewRecord.getApproved());
				if (mc.amendReviewRecords(tempRecord, user)) {
					// Repopulate the review with the new record
					populate(mc.readReviewRecord(reviewRecord.getUsername(), user, reviewRecord.getrID()).get(0));
					lblResult.setText("Successfully updated review");

				} else {
					showMessageDialog(null, "Failed to update");
				}
			}
		} else {
			showMessageDialog(null, "Only participants can update a review");
		}
	}

	public void refreshObjectives(ReviewRecord reviewRecord) {
		list.removeAll();
		ArrayList<Objective> tempObjectives = mc.readObjectives(reviewRecord, user);

		if (tempObjectives == null || tempObjectives.isEmpty()) {
			lblResult.setText("No objectives found");
		} else {

			String[] objectives = new String[tempObjectives.size()];
			rIdList = new int[tempObjectives.size()];
			oIdList = new int[tempObjectives.size()];
			for (int i = 0; i < tempObjectives.size(); i++) {
				objectives[i] = tempObjectives.get(i).getObjective();
				rIdList[i] = tempObjectives.get(i).getrId();
				oIdList[i] = tempObjectives.get(i).getoId();
				list.add(objectives[i]);
			}
		}
	}
}
