package org.payroll.preferences;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.payroll.*;

public class DeleteLoginIdFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JLabel lblUsername, lblPassword;
	JTextField txtUsername;
	JPasswordField txtPassword;
	JButton btnCancel, btnDelete;
	
	public DeleteLoginIdFrame() {
		initFrame();
		initComponents();
		addActionListeners();
		addComponentsToFrame();
	}
	
	void initFrame() {
		setTitle("Delete Login ID");
		setSize(300, 115);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
	}
	
	void initComponents() {
		lblUsername = new JLabel("Username: ");
		txtUsername = new JTextField(18);
		lblPassword = new JLabel("Password: ");
		txtPassword = new JPasswordField(18);
		btnCancel = new JButton("Cancel");
		btnDelete = new JButton("Delete");
	}
	
	void addActionListeners() {
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Main.dbManager.verifyLoginId(txtUsername.getText(), new String(txtPassword.getPassword()))) {
					Main.dbManager.deleteLoginId(txtUsername.getText());
					setVisible(false);
					JOptionPane.showMessageDialog(
							null,
							"Login ID deleted successfully",
							"Deletion Successful",
							JOptionPane.INFORMATION_MESSAGE
						);
					dispose();
				} else {
					JOptionPane.showMessageDialog(
							null,
							"Wrong username or password",
							"Deletion Failed",
							JOptionPane.ERROR_MESSAGE
						);
				}
			}
		});
	}
	
	void addComponentsToFrame() {
		add(lblUsername);
		add(txtUsername);
		add(lblPassword);
		add(txtPassword);
		add(btnCancel);
		add(btnDelete);
	}
}
