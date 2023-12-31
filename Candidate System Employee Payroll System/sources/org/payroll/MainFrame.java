package org.payroll;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.payroll.departments.DeleteDepartmentFrame;
import org.payroll.departments.ModifyDepartmentFrame;
import org.payroll.departments.NewDepartmentFrame;
import org.payroll.employees.DeleteEmployeeFrame;
import org.payroll.employees.NewEmployeeFrame;
import org.payroll.employees.UpdateEmployeeFrame;
import org.payroll.preferences.ChangePasswordFrame;
import org.payroll.preferences.DeleteLoginIdFrame;
import org.payroll.preferences.NewLoginIdFrame;

/**
 * MainFrame class represents a GUI window for a login screen in an Employee
 * Payroll System.
 * It is a a window that displays employee information in a table and provides a
 * menu bar with various options to manage employees, departments, and
 * preferences.
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	String username;

	Object[] columns = {
			"ID",
			"First Name",
			"Last Name",
			"Email",
			"Department",
			"Net Salary"
	};
	Object[][] data;

	//////////////////////////////////////
	// Components

	JTable table;
	JScrollPane scrollpane;
	JTextField txtLoggedInAs;
	JMenuBar menubar;
	JMenu fileMenu, tableMenu, employeesMenu, departmentsMenu, preferencesMenu, helpMenu;
	JMenuItem logoutMI, exitMI; // fileMenu
	JMenuItem reloadMI; // tableMenu
	JMenuItem newEmployeeMI, updateEmployeeMI, deleteEmployeeMI; // employeesMenu
	JMenuItem newDepartmentMI, modifyDepartmentMI, deleteDepartmentMI; // departmentsMenu
	JMenuItem newLoginIdMI, changePasswordMI, deleteLoginIdMI; // preferencesMenu
	JMenuItem aboutMI; // helpMenu

	/** MainFrame Constructor */

	public MainFrame(String username) {
		this.username = username;

		initFrame();
		initComponents();
		configureComponents();
		addActionListeners();
		addComponentsToFrame();
	}

	void initFrame() {
		setTitle("Payroll System by Sanjan Geet Singh");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	}

	void initComponents() {
		data = Main.dbManager.getEmployees();

		menubar = new JMenuBar();
		txtLoggedInAs = new JTextField("Logged in as " + username);

		// Menus
		fileMenu = new JMenu("File");
		tableMenu = new JMenu("Table");
		employeesMenu = new JMenu("Employees");
		departmentsMenu = new JMenu("Departments");
		preferencesMenu = new JMenu("Preferences");
		helpMenu = new JMenu("Help");

		// File Menu
		logoutMI = new JMenuItem("Logout");
		exitMI = new JMenuItem("Exit");

		// Table Menu
		reloadMI = new JMenuItem("Reload");

		// Employees Menu
		newEmployeeMI = new JMenuItem("New Employee");
		updateEmployeeMI = new JMenuItem("Update Employee");
		deleteEmployeeMI = new JMenuItem("Delete Employee");

		// Departments Menu
		newDepartmentMI = new JMenuItem("New Department");
		modifyDepartmentMI = new JMenuItem("Modify Department");
		deleteDepartmentMI = new JMenuItem("Delete Department");

		// Preferences Menu
		newLoginIdMI = new JMenuItem("New Login ID");
		changePasswordMI = new JMenuItem("Change Password");
		deleteLoginIdMI = new JMenuItem("Delete Login ID");

		// Help Menu
		aboutMI = new JMenuItem("About");

		// Table
		if (data == null) {
			table = new JTable();
		} else {
			table = new JTable(data, columns);
		}

		scrollpane = new JScrollPane(table);
	}

	void configureComponents() {
		table.setEnabled(false);
		txtLoggedInAs.setEditable(false);
	}

	void addActionListeners() {
		logoutMI.addActionListener(new ActionListener() { // File Menu
			/**
			 * It allows user to logout and then displays the login form again to let the
			 * user login
			 */
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				(new LoginFrame()).setVisible(true);
				dispose();
			}
		});
		exitMI.addActionListener(new ActionListener() {
			/** This Action Listener lets the user exit */
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		reloadMI.addActionListener(new ActionListener() { // Table Menu
			/** This Action Listener lets the user reload/refresh */
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				(new MainFrame(username)).setVisible(true);
				dispose();
			}
		});
		newEmployeeMI.addActionListener(new ActionListener() { // Employees Menu
			/** This Action Listener displays the employee menu dropdown */
			public void actionPerformed(ActionEvent e) {
				(new NewEmployeeFrame()).setVisible(true);
			}
		});
		updateEmployeeMI.addActionListener(new ActionListener() {
			/** This Action Listener navigates to the UpdateEmployeeFrame */
			public void actionPerformed(ActionEvent e) {
				(new UpdateEmployeeFrame()).setVisible(true);
			}
		});
		deleteEmployeeMI.addActionListener(new ActionListener() {
			/** This Action Listener navigates to the DeleteEmployeeFrame */
			public void actionPerformed(ActionEvent e) {
				(new DeleteEmployeeFrame()).setVisible(true);
			}
		});

		newDepartmentMI.addActionListener(new ActionListener() { // Departments Menu
			/** This Action Listener displays the department menu dropdown */
			public void actionPerformed(ActionEvent e) {
				(new NewDepartmentFrame()).setVisible(true);
			}
		});
		modifyDepartmentMI.addActionListener(new ActionListener() {
			/** This Action Listener navigates to the ModifyDepartmentFrame */
			public void actionPerformed(ActionEvent e) {
				(new ModifyDepartmentFrame()).setVisible(true);
			}
		});
		deleteDepartmentMI.addActionListener(new ActionListener() {
			/** This Action Listener navigates to the DeleteDepartmentFrame */
			public void actionPerformed(ActionEvent e) {
				(new DeleteDepartmentFrame()).setVisible(true);
			}
		});
		newLoginIdMI.addActionListener(new ActionListener() { // Preferences Menu
			/** This Action Listener navigates to the newLoginFrame */
			public void actionPerformed(ActionEvent e) {
				(new NewLoginIdFrame()).setVisible(true);
			}
		});
		changePasswordMI.addActionListener(new ActionListener() {
			/** This Action Listener navigates to the ChangePasswordFrame */
			public void actionPerformed(ActionEvent e) {
				(new ChangePasswordFrame(username)).setVisible(true);
			}
		});
		deleteLoginIdMI.addActionListener(new ActionListener() {
			/** This Action Listener navigates to the DeleteLoginIdFrame */
			public void actionPerformed(ActionEvent e) {
				(new DeleteLoginIdFrame()).setVisible(true);
			}
		});
		aboutMI.addActionListener(new ActionListener() { // Help Menu
			/** This Action Listener displays the help Menu when clicked */
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Employee Payroll System\nAuthor: Sanjan Geet Singh\nEmail: sanjangeet2109s@gmail.com", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	void addComponentsToFrame() {
		// File Menu
		fileMenu.add(logoutMI);
		fileMenu.add(exitMI);

		// Table Menu
		tableMenu.add(reloadMI);

		// Employees Menu
		employeesMenu.add(newEmployeeMI);
		employeesMenu.add(updateEmployeeMI);
		employeesMenu.add(deleteEmployeeMI);

		// Departments Menu
		departmentsMenu.add(newDepartmentMI);
		departmentsMenu.add(modifyDepartmentMI);
		departmentsMenu.add(deleteDepartmentMI);

		// Preferences Menu
		preferencesMenu.add(newLoginIdMI);
		preferencesMenu.add(changePasswordMI);
		preferencesMenu.add(deleteLoginIdMI);

		// Help Menu
		helpMenu.add(aboutMI);

		// Menus
		menubar.add(fileMenu);
		menubar.add(tableMenu);
		menubar.add(employeesMenu);
		menubar.add(departmentsMenu);
		menubar.add(preferencesMenu);
		menubar.add(helpMenu);

		setJMenuBar(menubar);
		add(scrollpane, BorderLayout.CENTER);
		add(txtLoggedInAs, BorderLayout.SOUTH);
	}
}
