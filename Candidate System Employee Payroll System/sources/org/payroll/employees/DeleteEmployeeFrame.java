package org.payroll.employees;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.payroll.Main;

/**
 * DeleteEmployeeFrame class represents a GUI window for deleting an existing
 * employee
 * It displays a window that allows the user to enter the ID of the employee to be deleted.
 */
public class DeleteEmployeeFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(DeleteEmployeeFrame.class.getName());
	
	JLabel lblId;
	JTextField txtId;
	JButton btnCancel, btnDelete;
	
	/**
	 * creates a user interface for deleting an employee
	 */
	public DeleteEmployeeFrame() {
		initFrame();
		initComponents();
		configureComponents();
		addActionListeners();
		addComponentsToFrame();
	}
	
	void initFrame() {
		setTitle("Delete Employee");
		setSize(260, 100);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
	}
	
	void initComponents() {
		lblId = new JLabel("ID: ");
		txtId = new JTextField(18);
		btnCancel = new JButton("Cancel");
		btnDelete = new JButton("Delete");
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
		
		btnDelete.addActionListener(new ActionListener() {
			/**
			 * This method that handles the user action when the "Delete" button is clicked
			 * in a graphical user interface (GUI) frame.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseUnsignedInt(txtId.getText());
					if (Main.dbManager.existsEmployeeID(id)) {
						Main.dbManager.deleteEmployee(id);
						setVisible(false);
						JOptionPane.showMessageDialog(null, "Removed Employee Successfully", "Removed Employee", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE );
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
		add(btnCancel);
		add(btnDelete);
	}
}
