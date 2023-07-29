package org.payroll.departments;

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
 * ModifyDepartmentFrame class represents a GUI window for updating department
 * settings.
 * It displays a window that allows the user to select a department from a
 * drop-down list (JComboBox) and update its basic salary, DA (Dearness
 * Allowance), HRA (House Rent Allowance), and PF (Provident Fund) percentages.
 */
public class ModifyDepartmentFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(ModifyDepartmentFrame.class.getName());

	ArrayList<String> departments = Main.dbManager.getListOfDepartments();

	JLabel lblDepartmentName, lblBasicSalary, lblDA, lblHRA, lblPF;
	JComboBox<String> txtDepartmentName;
	JTextField txtBasicSalary, txtDA, txtHRA, txtPF;
	JButton btnCancel, btnCreate;

	/**
	 * creates a user interface for modifying a department
	 */

	public ModifyDepartmentFrame() {
		initFrame();
		initComponents();
		configureComponents();
		addActionListeners();
		addComponentsToFrame();
	}

	void initFrame() {
		setTitle("Update Department");
		setSize(333, 193);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
	}

	void initComponents() {
		lblDepartmentName = new JLabel("Department Name: ");
		lblBasicSalary = new JLabel("           Basic Salary: ");
		lblDA = new JLabel("                          DA%: ");
		lblHRA = new JLabel("                       HRA%: ");
		lblPF = new JLabel("                          PF%: ");
		txtDepartmentName = new JComboBox<String>(departments.toArray(new String[departments.size()]));
		txtBasicSalary = new JTextField(18);
		txtDA = new JTextField(18);
		txtHRA = new JTextField(18);
		txtPF = new JTextField(18);
		btnCancel = new JButton("Cancel");
		btnCreate = new JButton("Update");
	}

	void configureComponents() {
		txtDA.setText("10");
		txtHRA.setText("14");
		txtPF.setText("8");
	}

	void addActionListeners() {
		btnCancel.addActionListener(new ActionListener() {
			/** Action Listener for a Cancel Button */
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

		btnCreate.addActionListener(new ActionListener() {
			/** Modifying of the Department and shows errors if they exist */
			public void actionPerformed(ActionEvent e) {
				try {
					String departmentName = txtDepartmentName.getSelectedItem().toString();
					int basicSalary = Integer.parseInt(txtBasicSalary.getText());
					int da = Integer.parseInt(txtDA.getText());
					int hra = Integer.parseInt(txtHRA.getText());
					int pf = Integer.parseInt(txtPF.getText());

					if (Main.dbManager.existsDepartment(departmentName)) {
						Main.dbManager.updateDepartment(departmentName, basicSalary, da, hra, pf);
						setVisible(false);
						JOptionPane.showMessageDialog(null, "Updated department settings", "Department updated",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Department does not exist",
								"Department does not exist exist", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException e1) {
					logger.log(Level.SEVERE, "An error occurred: " + e1.getMessage(), e1);
				}
			}
		});
	}

	void addComponentsToFrame() {
		add(lblDepartmentName);
		add(txtDepartmentName);
		add(lblBasicSalary);
		add(txtBasicSalary);
		add(lblDA);
		add(txtDA);
		add(lblHRA);
		add(txtHRA);
		add(lblPF);
		add(txtPF);
		add(btnCancel);
		add(btnCreate);
	}
}
