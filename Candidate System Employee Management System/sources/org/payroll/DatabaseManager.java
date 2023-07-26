package org.payroll;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
	
	String connectionString;
	
	Connection conn;
	Statement curs;
	
	public DatabaseManager(String db) {
		connectionString = "jdbc:sqlite:" + db;
		
		if (!(new File(db)).exists()) {
			connectToDatabase();
			initNewDatabase();
		} else {
			connectToDatabase();
		}
	}
	
	void connectToDatabase() {
		try {
			conn = DriverManager.getConnection(connectionString);
			curs = conn.createStatement();
			curs.setQueryTimeout(30);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	void initNewDatabase() {
		try {
			curs.executeUpdate( "CREATE TABLE login_ids(id INTEGER NOT NULL PRIMARY KEY, username STRING NOT NULL, password STRING NOT NULL)" );
			curs.executeUpdate( "INSERT INTO login_ids VALUES(null, \"admin\", \"password\")" );
			curs.executeUpdate(
					"CREATE TABLE departments(" +
							"id INTEGER NOT NULL PRIMARY KEY," +
							"dep_name STRING NOT NULL," +
							"basic_salary INTEGER NOT NULL," +
							"da INTEGER NOT NULL," +
							"hra INTEGER NOT NULL," +
							"pf INTEGER NOT NULL," +
							"gross_salary INTEGER NOT NULL," +
							"epf INTEGER NOT NULL," +
							"lic INTEGER NOT NULL," +
							"deductions INTEGER NOT NULL," +
							"net_salary INTEGER NOT NULL" +
					")"
				);
			curs.executeUpdate(
					"CREATE TABLE employees(" +
						"id INTEGER NOT NULL PRIMARY KEY," +
						"first_name STRING NOT NULL," +
						"last_name STRING NOT NULL," +
						"email STRING NOT NULL," +
						"department STRING NOT NULL" +
					")"
				);
		} catch (SQLException e) { 
			System.err.println(e.getMessage()); 
		}
	}
	
	public Boolean verifyLoginId(String username) {
		try {
			return curs.executeQuery(
					"SELECT * FROM login_ids WHERE username=\"" + username + "\""
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public Boolean verifyLoginId(String username, String password) {
		try {
			return curs.executeQuery(
					"SELECT * FROM login_ids WHERE username=\"" + username + "\" AND password=\"" + password + "\""
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public void createLoginId(String username, String password) {
		try {
			curs.executeUpdate("INSERT INTO login_ids VALUES(null, \"" + username + "\", \"" + password + "\")");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void deleteLoginId(String username) {
		try {
			curs.executeUpdate(
					"DELETE FROM login_ids WHERE username=\"" + username + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void changePassword(String username, String newPassword) {
		try {
			curs.executeUpdate(
					"UPDATE login_ids SET password=\"" + newPassword + "\" WHERE username=\"" + username + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public Boolean existsDepartment(String departmentName) {
		try {
			return curs.executeQuery(
					"SELECT * FROM departments WHERE dep_name=\"" + departmentName + "\""
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public void newDepartment(String departmentName, int basicSalary, int daPercent, int hraPercent, int pfPercent) {
		int da = (daPercent / 100) * basicSalary;
		int hra = (hraPercent / 100) * basicSalary;
		int pf = (pfPercent / 100) * basicSalary;
		int grossSalary = basicSalary + da + hra + pf;
		int epf = pf / 2;
		int lic = epf / 2;
		int deductions = epf + lic;
		int netSalary = grossSalary - deductions;
		
		try {
			curs.executeUpdate(
					"INSERT INTO departments VALUES(" +
							"null," +
							"\"" + departmentName + "\" ," +
							Integer.toString(basicSalary) + "," +
							Integer.toString(da) + "," +
							Integer.toString(hra) + "," +
							Integer.toString(pf) + "," +
							Integer.toString(grossSalary) + "," +
							Integer.toString(epf) + "," +
							Integer.toString(lic) + "," +
							Integer.toString(deductions) + "," +
							Integer.toString(netSalary) +
					")"
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void deleteDepartment(String departmentName) {
		try {
			curs.executeUpdate(
					"DELETE FROM departments WHERE dep_name=\"" + departmentName + "\""
				);
			curs.executeUpdate(
					"DELETE FROM employees WHERE department=\"" + departmentName + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void updateDepartment(String departmentName, int basicSalary, int da, int hra, int pf) {
		deleteDepartment(departmentName);
		newDepartment(departmentName, basicSalary, da, hra, pf);
	}
	
	public ArrayList<String> getListOfDepartments() {
		ArrayList<String> lst = new ArrayList<String>();
		
		try {
			ResultSet rs = curs.executeQuery("SELECT dep_name FROM departments");
			
			while (rs.next()) {
				lst.add(rs.getString("dep_name"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return lst;
	}
	
	public int getSalary(String departmentName) {
		try {
			ResultSet rs = curs.executeQuery("SELECT net_salary FROM departments WHERE dep_name=\"" + departmentName + "\"");
			
			if (rs.next()) {
				return rs.getInt("net_salary");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return 0;
	}
	
	public Boolean existsEmployeeID(int id) {
		try {
			return curs.executeQuery(
					"SELECT * FROM employees WHERE id=" + Integer.toString(id)
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public void createEmployee(String fn, String ln, String email, String department) {
		try {
			curs.executeUpdate("INSERT INTO employees VALUES(" +
					"null," +
					"\"" + fn + "\"," +
					"\"" + ln + "\"," + 
					"\"" + email + "\"," +
					"\"" + department + "\"" +
				")");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void deleteEmployee(int id) {
		try {
			curs.executeUpdate(
					"DELETE FROM employees WHERE id=" + Integer.toString(id)
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void updateEmployee(int id, String fn, String ln, String email, String department) {
		try {
			curs.executeUpdate(
					"UPDATE employees SET " +
					"first_name=\"" + fn + "\"," +
					"last_name=\"" + ln + "\"," +
					"email=\"" + email + "\"," +
					"department=\"" + department + "\" " +
					"WHERE id=" + Integer.toString(id)
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public Object[][] getEmployees() {
		ArrayList<Object[]> employees = new ArrayList<Object[]>();
		ResultSet rs;
		
		try {
			rs = curs.executeQuery(
					"SELECT * FROM employees"
				);
			
			while (rs.next()) {
				Object[] temp = {
					rs.getInt("id"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("email"),
					rs.getString("department"),
					getSalary(rs.getString("department"))
				};
				
				employees.add(temp);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return employees.toArray(new Object[employees.size()][]);
	}
}
