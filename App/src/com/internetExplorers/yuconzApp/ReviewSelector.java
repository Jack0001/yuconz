package com.internetExplorers.yuconzApp;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import com.internetExplorers.yuconzApp.user.PersonalDetails;
import com.internetExplorers.yuconzApp.user.ReviewRecord;
import com.internetExplorers.yuconzApp.user.User;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class ReviewSelector {

	private JFrame frame;
	private User user;
	private MainController mc;
	private JLabel lblPleaseSelectThe = new JLabel("Please select the review record for reading or editing");
	private JButton btnOpen = new JButton("Open");
	private DefaultTableModel model = new DefaultTableModel();
	private String[] columnNames = { "Username", "Reviewer", "Second Reviewer", "Date created", "Last Edited", "Approved" };
	private JTable table = new JTable(model);
	private int[] ids;
	private String[] usernames;
	/**
	 * Create the application.
	 */
	public ReviewSelector(MainController mc) {
		this.mc = mc;
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initialiseEvents();
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 550);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblPleaseSelectThe.setBounds(10, 11, 454, 14);
		frame.getContentPane().add(lblPleaseSelectThe);

		btnOpen.setBounds(279, 446, 89, 23);
		frame.getContentPane().add(btnOpen);

		table.setBounds(48, 47, 550, 388);
		
		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);

		}
	}

	public void populate(User user) {
		model.setRowCount(0);
		ArrayList<ReviewRecord> tempDetails = new ArrayList<ReviewRecord>();

		tempDetails = mc.fetchReviewList(user);
		ids = new int[tempDetails.size()];
		usernames = new String[tempDetails.size()];
		if (tempDetails == null || tempDetails.isEmpty()) {
			showMessageDialog(null, "Database failure: could not find records");
		} else {
			table.setBounds(50, 50, 500, 400);
			frame.getContentPane().add(table);

			for (int i = 0; i < tempDetails.size(); i++) {
				model.addRow(new Object[] { tempDetails.get(i).getUsername(), tempDetails.get(i).getReviewer(),
						tempDetails.get(i).getSecondReviewer(), tempDetails.get(i).getDate_created(),
						tempDetails.get(i).getLast_edited(), tempDetails.get(i).getApproved() });
				ids[i] = tempDetails.get(i).getrID();
				usernames[i] = tempDetails.get(i).getUsername();
			}
		}
	}
	
	public void initialiseEvents() {
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					showMessageDialog(null, "Please select a review");
				} else {
					MainController.getReview().getFrame().setVisible(true);
					MainController.getReview().setUser(user);
					MainController.getReview().readOtherReview(usernames[table.getSelectedRow()], user,
							ids[table.getSelectedRow()]);
				}
			}
		});
	}
		
	

	public void setUser(User user) {
		this.user = user;
	}

	public JFrame getFrame() {
		return frame;
	}
}
