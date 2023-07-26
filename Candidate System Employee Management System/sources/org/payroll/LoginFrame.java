package org.payroll;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JLabel lblInfo, lblUsername, lblPassword;
	JButton btnLogin, btnExit;
	JTextField txtUsername;
	JPasswordField txtPassword;
	
	public LoginFrame() {
		initFrame();
		initComponents();
		addActionListeners();
		addComponentsToFrame();
	}
	
	void initFrame() {
		setTitle("Login");
		setSize(300, 140);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new FlowLayout());
	}
	
	void initComponents() {
		lblInfo = new JLabel("Employee Payroll System by Sanjan Geet Singh");
		lblUsername = new JLabel("Username: ");
		lblPassword = new JLabel("Password: ");
		txtUsername = new JTextField(18);
		txtPassword = new JPasswordField(18);
		btnExit = new JButton("Exit");
		btnLogin = new JButton("Login");
	}
	
	void addActionListeners() {
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Main.dbManager.verifyLoginId(txtUsername.getText(), new String(txtPassword.getPassword()))) {
					loginSuccessful();
				} else {
					loginFailed();
				}
			}
		});
	}
	
	void addComponentsToFrame() {
		add(lblInfo);
		add(lblUsername);
		add(txtUsername);
		add(lblPassword);
		add(txtPassword);
		add(btnExit);
		add(btnLogin);
	}
	
	void loginSuccessful() {
		JOptionPane.showMessageDialog(
				null,
				"Login Successful",
				"Login Successful",
				JOptionPane.INFORMATION_MESSAGE
			);
		
		setVisible(false);
		(new MainFrame(txtUsername.getText())).setVisible(true);
		dispose();
	}
	
	void loginFailed() {
		JOptionPane.showMessageDialog(
				null,
				"Wrong username or password",
				"Login Failed",
				JOptionPane.ERROR_MESSAGE
			);
		
		txtUsername.setText("");
		txtPassword.setText("");
	}
}
