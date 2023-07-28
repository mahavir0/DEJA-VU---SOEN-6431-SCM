package org.payroll;

/** Loader Class */

public class Main {

	/** Shared Database Manager */
	public static DatabaseManager dbManager = null;

	/** Main Class */
	public static void main(String[] args) {
		dbManager = new DatabaseManager("employee_payroll_system");
		// If "the path to database file" is empty, a temporary in-memory database is
		// opened.

		(new LoginFrame()).setVisible(true);
	}
}
