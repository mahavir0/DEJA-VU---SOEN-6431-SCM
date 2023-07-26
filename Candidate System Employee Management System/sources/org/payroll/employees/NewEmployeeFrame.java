package org.payroll.employees;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.payroll.*;
import java.util.*;

public class NewEmployeeFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	ArrayList<String> departments = Main.dbManager.getListOfDepartments();
	
	JLabel lblFirstName, lblLastName, lblEmail, lblDepartment;
	JTextField txtFirstName, txtLastName, txtEmail;
	JComboBox<String> txtDepartment;
	JButton btnCancel, btnCreate;
	
	public NewEmployeeFrame() {
		initFrame();
		initComponents();
		configureComponents();
		addActionListeners();
		addComponentsToFrame();
	}
	
	void initFrame() {
		setTitle("New Employee");
		setSize(320, 170);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
	}
	
	void initComponents() {
		lblFirstName = new JLabel			("First Name: ");
		lblLastName = new JLabel			("Last Name: ");
		lblEmail = new JLabel			("          Email: ");
		lblDepartment = new JLabel	("      Department: ");
		txtFirstName = new JTextField(18);
		txtLastName = new JTextField(18);
		txtEmail = new JTextField(18);
		txtDepartment = new JComboBox<String>(departments.toArray(new String[departments.size()]));
		btnCancel = new JButton("Cancel");
		btnCreate = new JButton("Create");
	}
	
	void configureComponents() {
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
				Main.dbManager.createEmployee(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtDepartment.getSelectedItem().toString());
				setVisible(false);
				JOptionPane.showMessageDialog(
						null,
						"New Employee Added",
						"New Employee Added",
						JOptionPane.INFORMATION_MESSAGE
					);
				dispose();
			}
		});
	}
	
	void addComponentsToFrame() {
		add(lblFirstName);
		add(txtFirstName);
		add(lblLastName);
		add(txtLastName);
		add(lblEmail);
		add(txtEmail);
		add(lblDepartment);
		add(txtDepartment);
		add(btnCancel);
		add(btnCreate);
	}
}
