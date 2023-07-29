package org.payroll.departments;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.payroll.Main;

/**
 * DeleteDepartmentFrame class represents a GUI window for deleting a department
 * It displays a window that allows the user to select a department from a drop-down list (JComboBox) and delete the selected department.
 */
public class DeleteDepartmentFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<String> departments = Main.dbManager.getListOfDepartments();
	
	JLabel lblDepartmentName;
	JComboBox<String> combobox;
	JButton btnCancel, btnDelete;
	
	/**
	 * creates a user interface for deleting a department
	 */
	public DeleteDepartmentFrame() {
		initFrame();
		initComponents();
		addActionListeners();
		addComponentsToFrame();
	}
	
	void initFrame() {
		setTitle("Delete Department");
		setSize(330, 120);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
	}
	
	void initComponents() {
		lblDepartmentName = new JLabel("Department Name: ");
		combobox = new JComboBox<String>(departments.toArray(new String[departments.size()]));
		btnCancel = new JButton("Cancel");
		btnDelete = new JButton("Delete");
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
			/** Action Listener for a Delete Button */
			public void actionPerformed(ActionEvent e) {
				Main.dbManager.deleteDepartment(combobox.getSelectedItem().toString());
				setVisible(false);
				dispose();
			}
		});
	}
	
	void addComponentsToFrame() {
		add(lblDepartmentName);
		add(combobox);
		add(btnCancel);
		add(btnDelete);
	}
}
