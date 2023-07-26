package org.payroll.preferences;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.payroll.Main;

public class ChangePasswordFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	String username;
	String uppercaseAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String lowercaseAlphabets = "abcdefghijklmnopqrstuvwxyz";
	String numbers = "0123456789";
	
	JLabel lblOldPassword, lblNewPassword, lblRepeatPassword;
	JPasswordField txtOldPassword, txtNewPassword, txtRepeatPassword;
	JButton btnCancel, btnOK;
	
	public ChangePasswordFrame(String username) {
		this.username = username;
		initFrame();
		initComponents();
		addActionListeners();
		addComponentsToFrame();
	}
	
	void initFrame() {
		setTitle("Change Password");
		setSize(355, 145);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
	}
	
	void initComponents() {
		lblOldPassword = new JLabel("      Old Password: ");
		lblNewPassword = new JLabel("     New Password: ");
		lblRepeatPassword = new JLabel("Repeat Password: ");
		txtOldPassword = new JPasswordField(20);
		txtNewPassword = new JPasswordField(20);
		txtRepeatPassword = new JPasswordField(20);
		btnCancel = new JButton("Cancel");
		btnOK = new JButton("OK");
	}
	
	void addActionListeners() {
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Main.dbManager.verifyLoginId(username, new String(txtOldPassword.getPassword()))) {
					if (Arrays.equals(txtNewPassword.getPassword(), txtRepeatPassword.getPassword())) {
						if (isStrongPassword(new String(txtNewPassword.getPassword()))) {
							Main.dbManager.changePassword(username, new String(txtNewPassword.getPassword()));
							setVisible(false);
							JOptionPane.showMessageDialog(null,"Your login id's password is changed successfully","Password changed",JOptionPane.INFORMATION_MESSAGE);
						} else { 
							JOptionPane.showMessageDialog(null,"Password not strong enough","Weak Password",JOptionPane.ERROR_MESSAGE); 
						}
					} else { 
						JOptionPane.showMessageDialog(null,"Password don't match","Password Error",JOptionPane.ERROR_MESSAGE); 
					}
				} else { 
					JOptionPane.showMessageDialog(null,"Invalid Login ID","Invalid Login ID",JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
	}
	
	void addComponentsToFrame() {
		add(lblOldPassword);
		add(txtOldPassword);
		add(lblNewPassword);
		add(txtNewPassword);
		add(lblRepeatPassword);
		add(txtRepeatPassword);
		add(btnCancel);
		add(btnOK);
	}
	
	Boolean isStrongPassword(String password) {
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
