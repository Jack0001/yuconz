package com.internetExplorers.yuconzApp;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	private JFrame frmLogin;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private MainController mc;
	JLabel lblPassword = new JLabel("Password");
	JLabel labelResult = new JLabel("");
	JLabel lblUsername = new JLabel("Username");
	JLabel lblLogin = new JLabel("Login");
	JButton btnSubmit = new JButton("Submit");
	
	/**
	 * Create the application.
	 */
	public Login(MainController mc) {
		this.mc = mc;
		initialize();
		frmLogin.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 450, 300);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		

		
		lblLogin.setBounds(0, 11, 434, 14);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		frmLogin.getContentPane().add(lblLogin);
		

		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String username = textUsername.getText();
				String password = String.valueOf(textPassword.getPassword());
				
				String result = mc.login(username, password);
				
				if(result.equals("User not found")) {
					labelResult.setText("Login failed - Username not found");
				} else if (result.equals("Incorrect password")){
					labelResult.setText("Login failed - Incorrect password");
				} else if (result.equals("Logged in")) {
					labelResult.setText("Login successful");
					frmLogin.setVisible(false);
					MainController.getOptions().getFrame().setVisible(true);			
					
				}
			}
		});
		btnSubmit.setBounds(140, 182, 145, 23);
		frmLogin.getContentPane().add(btnSubmit);
		

		lblUsername.setBounds(49, 50, 120, 67);
		frmLogin.getContentPane().add(lblUsername);
		

		lblPassword.setBounds(49, 104, 120, 67);
		frmLogin.getContentPane().add(lblPassword);
		
		textUsername = new JTextField();
		textUsername.setBounds(196, 73, 96, 20);
		frmLogin.getContentPane().add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(196, 127, 96, 20);
		frmLogin.getContentPane().add(textPassword);
		

		labelResult.setBounds(94, 236, 262, 14);
		frmLogin.getContentPane().add(labelResult);
	}
	
	public JFrame getFrame() {
		return frmLogin;
	}
	
	public JLabel getLabel() {
		return labelResult;
	}
}
