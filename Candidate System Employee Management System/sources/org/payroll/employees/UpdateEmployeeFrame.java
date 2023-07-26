package org.payroll.employees;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.payroll.*;
import java.util.*;

public class UpdateEmployeeFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	ArrayList<String> departments = Main.dbManager.getListOfDepartments();
	
	JLabel lblId, lblFirstName, lblLastName, lblEmail, lblDepartment;
	JTextField txtId, txtFirstName, txtLastName, txtEmail;
	JComboBox<String> txtDepartment;
	JButton btnCancel, btnUpdate;
	
	public UpdateEmployeeFrame() {
		initFrame();
		initComponents();
		configureComponents();
		addActionListeners();
		addComponentsToFrame();
	}
	
	void initFrame() {
		setTitle("Update Employee");
		setSize(320, 195);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
	}
	
	void initComponents() {
		lblId = new JLabel			("                ID: ");
		lblFirstName = new JLabel			("First Name: ");
		lblLastName = new JLabel			("Last Name: ");
		lblEmail = new JLabel			("          Email: ");
		lblDepartment = new JLabel	("      Department: ");
		txtId = new JTextField(18);
		txtFirstName = new JTextField(18);
		txtLastName = new JTextField(18);
		txtEmail = new JTextField(18);
		txtDepartment = new JComboBox<String>(departments.toArray(new String[departments.size()]));
		btnCancel = new JButton("Cancel");
		btnUpdate = new JButton("Update");
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
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(txtId.getText());
					String fn = txtFirstName.getText();
					String ln = txtLastName.getText();
					String email = txtEmail.getText();
					String department = txtDepartment.getSelectedItem().toString();
					
					if (Main.dbManager.existsEmployeeID(id)) {
						Main.dbManager.updateEmployee(id, fn, ln, email, department);
						setVisible(false);
						JOptionPane.showMessageDialog(null,"ID Updated Successfully","Employee Updated",JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null,"ID doesn't exist.","Error",JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException e1) {
					System.err.println(e1.getMessage());
				}
			}
		});
	}
	
	void addComponentsToFrame() {
		add(lblId);
		add(txtId);
		add(lblFirstName);
		add(txtFirstName);
		add(lblLastName);
		add(txtLastName);
		add(lblEmail);
		add(txtEmail);
		add(lblDepartment);
		add(txtDepartment);
		add(btnCancel);
		add(btnUpdate);
	}
}
