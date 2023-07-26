package org.payroll.preferences;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.payroll.*;

public class NewLoginIdFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	String chars = " ^&\\/|`~";
	
	String uppercaseAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String lowercaseAlphabets = "abcdefghijklmnopqrstuvwxyz";
	String numbers = "0123456789";
	
	JLabel lblUsername, lblNewPassword, lblRepeatPassword;
	JTextField txtUsername;
	JPasswordField txtNewPassword, txtRepeatPassword;
	JButton btnCancel, btnCreate;
	
	public NewLoginIdFrame() {
		initFrame();
		initComponents();
		addActionListeners();
		addComponentsToFrame();
	}
	
	void initFrame() {
		setTitle("New Login ID");
		setSize(338, 152);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
	}
	
	void initComponents() {
		lblUsername = new JLabel("              Username: ");
		txtUsername = new JTextField(18);
		lblNewPassword = new JLabel("     New Password: ");
		txtNewPassword = new JPasswordField(18);
		lblRepeatPassword = new JLabel("Repeat Password: ");
		txtRepeatPassword = new JPasswordField(18);
		btnCancel = new JButton("Cancel");
		btnCreate = new JButton("Create");
	}
	
	void addActionListeners() {
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isUsernameValid()) {
					if (!Main.dbManager.verifyLoginId(txtUsername.getText())) {
						if (Arrays.equals(txtNewPassword.getPassword(), txtRepeatPassword.getPassword())) {
							if (isStrongPassword()) {
								Main.dbManager.createLoginId(txtUsername.getText(), new String(txtNewPassword.getPassword()));
								setVisible(false);
								JOptionPane.showMessageDialog(null,"New Login ID created successfully","New Login ID Created",JOptionPane.INFORMATION_MESSAGE);
								dispose();
							} else { 
								JOptionPane.showMessageDialog(null,"Password is not strong enough","Weak Password",JOptionPane.ERROR_MESSAGE); 
							}
						} else { 
							JOptionPane.showMessageDialog( null, "Passwords don't match", "Passwords are different", JOptionPane.ERROR_MESSAGE); 
						}
					} else { 
						JOptionPane.showMessageDialog( null, "Username Already Taken", "Username already taken", JOptionPane.ERROR_MESSAGE ); 
					}
				} else { 
					JOptionPane.showMessageDialog( null, "Invalid Username. Username cannot contain these symbols: " + chars, "Invalid Username", JOptionPane.ERROR_MESSAGE ); 
				}
			}
		});
	}
	
	void addComponentsToFrame() {
		add(lblUsername);
		add(txtUsername);
		add(lblNewPassword);
		add(txtNewPassword);
		add(lblRepeatPassword);
		add(txtRepeatPassword);
		add(btnCancel);
		add(btnCreate);
	}
	
	Boolean isUsernameValid() {
		String username = txtUsername.getText();
		
		if (username.length() < 1) {
			return false;
		}
		
		for (int i=0; i<username.length(); i++) {
			for (int j=0; j<chars.length(); j++) {
				if (username.charAt(i) == chars.charAt(j)) { 
					return false; 
				}
			}
		}
		
		return true;
	}
	
	Boolean isStrongPassword() {
		String password = new String(txtNewPassword.getPassword());
		
		if ((password.length() > 6) &&
			(containsUppercase(password)) &&
			(containsLowercase(password)) &&
			(containsNumbers(password))) {
			return true;
		}
		
		return false;
	}
	
	Boolean containsUppercase(String password) {
		for (int i=0; i<password.length(); i++) {
			for (int j=0; j<uppercaseAlphabets.length(); j++) {
				if (password.charAt(i) == uppercaseAlphabets.charAt(j)) {
					return true;
				}
			}
		}
		return false;
	}
	
	Boolean containsLowercase(String password) {
		for (int i=0; i<password.length(); i++) {
			for (int j=0; j<lowercaseAlphabets.length(); j++) {
				if (password.charAt(i) == lowercaseAlphabets.charAt(j)) {
					return true;
				}
			}
		}
		return false;
	}
	
	Boolean containsNumbers(String password) {
		for (int i=0; i<password.length(); i++) {
			for (int j=0; j<numbers.length(); j++) {
				if (password.charAt(i) == numbers.charAt(j)) {
					return true;
				}
			}
		}
		return false;
	}
}
