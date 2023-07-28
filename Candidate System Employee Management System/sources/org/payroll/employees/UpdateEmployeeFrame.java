package org.payroll.employees;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.payroll.Main;

/**
 * UpdateEmployeeFrame class represents a GUI window for updating an exsiting
 * employee in a database system
 * It displays a window that allows the user to edit the details of an
 * employee
 */
public class UpdateEmployeeFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(UpdateEmployeeFrame.class.getName());
	
	ArrayList<String> departments = Main.dbManager.getListOfDepartments();
	
	JLabel lblId, lblFirstName, lblLastName, lblEmail, lblDepartment;
	JTextField txtId, txtFirstName, txtLastName, txtEmail;
	JComboBox<String> txtDepartment;
	JButton btnCancel, btnUpdate;
	
	/**
	 * creates a user interface for updating the fields of an employee
	 */
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
			/** Action Listener for a Cancel Button */
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			/**
			 * This method that handles the user action when the "Update" button is clicked
			 * in a graphical user interface (GUI) frame.
			 */
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
					logger.log(Level.SEVERE, "An error occurred: " + e1.getMessage(), e1);
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
