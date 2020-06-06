package com.internetExplorers.yuconzApp;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;

import com.internetExplorers.yuconzApp.user.PersonalDetails;
import com.internetExplorers.yuconzApp.user.User;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * Personal selector form for selecting a sepecific personal record to read or edit.
 * @author Jack Oliver
 */
public class PersonalSelector {

	private JFrame frame;
	private JScrollPane scrollPane = new JScrollPane();
	private JLabel lblPleaseSelectThe = new JLabel("Please select the personal record for reading or editing");
	private JButton btnEdit = new JButton("Edit");
	private JButton btnRead = new JButton("Read");
	private User user;
	private MainController mc;
	private List list = new List();

	/**
	 * Create the application.
	 */
	public PersonalSelector(MainController mc) {
		this.mc = mc;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 550);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblPleaseSelectThe.setBounds(10, 11, 579, 14);
		frame.getContentPane().add(lblPleaseSelectThe);

		/**
		 * Allow editing of the personal details selected by the user. These can only be
		 * edited by HR employees and therefore need to pass an authorisation check. if
		 * passed then the frame is made visible with the relevant contents and if
		 * failed a message box is shown to the user
		 */
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] permissions = { "hr_employee" };

				if (MainController.getAuthor().authorisationCheck(user.getUsername(), user.getPosition(), permissions, "Reading personal details for editing purpose")) {

					String username = list.getSelectedItem();
					MainController.getPersonal().getFrame().setVisible(true);
					MainController.getPersonal().setUser(user);
					MainController.getPersonal().setEmployee(username);
					MainController.getPersonal().populate(username, user);
					MainController.getPersonal().showUpdate();
					MainController.getPersonal().unlockAll();
					MainController.getPersonal().lockUsername();
					
				}
				else {
					showMessageDialog(null, "Failed authorisation check");
				}
			}
		});

		btnEdit.setBounds(450, 451, 89, 23);
		frame.getContentPane().add(btnEdit);

		/**
		 * Read the personal details of the selected user, set the user and employee for
		 * the Personal frame, populate and hide both the buttons
		 */
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] permissions = { "hr_employee", "director" };
				String username = list.getSelectedItem();
				if (user.getUsername().equals(username)
						|| MainController.getAuthor().authorisationCheck(user.getUsername(), user.getPosition(), permissions, "Reading other personal details")) {
					MainController.getPersonal().getFrame().setVisible(true);
					MainController.getPersonal().setUser(user);
					MainController.getPersonal().setEmployee(username);
					MainController.getPersonal().populate(username, user);
					MainController.getPersonal().hideBoth();
					MainController.getPersonal().disableAll();
				}
			}
		});

		btnRead.setBounds(100, 451, 89, 23);
		frame.getContentPane().add(btnRead);

		scrollPane.setBounds(72, 418, 467, -355);
		frame.getContentPane().add(scrollPane);

	}

	/**
	 * Populate the list with the relevant personal records to the person Employees,
	 * Managers - Will only show up with their review HR Employees and Directors -
	 * All records will show up for selection
	 */
	public void populate(User user) {
		//empty the list before populating it
		list.removeAll();
		String[] permissions = { "hr_employee", "director" };
		ArrayList<PersonalDetails> tempDetails = new ArrayList<PersonalDetails>();

		if (user.getPosition().equals("hr_employee") || user.getPosition().equals("director")) {
			tempDetails = mc.readPersonalDetails(user.getUsername(), user, "all");
		} else {
			tempDetails = mc.readPersonalDetails(user.getUsername(), user, "one");
		}

		if (tempDetails == null || tempDetails.isEmpty()) {
			showMessageDialog(null, "Database failure: could not find records");
		} else {
			list.setBounds(50, 50, 500, 400);
			frame.getContentPane().add(list);

			String[] usernames = new String[tempDetails.size()];

			for (int i = 0; i < tempDetails.size(); i++) {
				usernames[i] = tempDetails.get(i).getUsername();
				list.add(usernames[i]);
			}
		}
	}
	

	public void setUser(User user) {
		this.user = user;
	}

	public JFrame getFrame() {
		return frame;
	}
}
