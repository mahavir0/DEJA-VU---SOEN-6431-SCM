package org.payroll.departments;

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
 * NewDepartmentFrame class represents a GUI window for creating a new
 * department
 * It displays a window that allows the user to enter the details of a new
 * department, such as department name, basic salary, DA (Dearness Allowance)
 * percentage, HRA (House Rent Allowance) percentage, and PF (Provident Fund)
 * percentage.
 */
public class NewDepartmentFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(NewDepartmentFrame.class.getName());

	JLabel lblDepartmentName, lblBasicSalary, lblDA, lblHRA, lblPF;
	JTextField txtDepartmentName, txtBasicSalary, txtDA, txtHRA, txtPF;
	JButton btnCancel, btnCreate;

	/**
	 * creates a user interface for creating a new department
	 */
	public NewDepartmentFrame() {
		initFrame();
		initComponents();
		configureComponents();
		addActionListeners();
		addComponentsToFrame();
	}

	void initFrame() {
		setTitle("New Department");
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
		txtDepartmentName = new JTextField(18);
		txtBasicSalary = new JTextField(18);
		txtDA = new JTextField(18);
		txtHRA = new JTextField(18);
		txtPF = new JTextField(18);
		btnCancel = new JButton("Cancel");
		btnCreate = new JButton("Create");
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
			/**
			 * This method that handles the user action when the "Create" button is clicked
			 * in a graphical user interface (GUI) frame.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					String departmentName = txtDepartmentName.getText();
					int basicSalary = Integer.parseInt(txtBasicSalary.getText());
					int da = Integer.parseInt(txtDA.getText());
					int hra = Integer.parseInt(txtHRA.getText());
					int pf = Integer.parseInt(txtPF.getText());

					if (!Main.dbManager.existsDepartment(departmentName)) {
						Main.dbManager.newDepartment(departmentName, basicSalary, da, hra, pf);
						setVisible(false);
						JOptionPane.showMessageDialog(null, "Created Department successfully", "Department created",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Department already exists", "Department already exists",
								JOptionPane.ERROR_MESSAGE);
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
