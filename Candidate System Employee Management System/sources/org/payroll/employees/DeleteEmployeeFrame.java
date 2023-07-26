package org.payroll.employees;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.payroll.Main;

public class DeleteEmployeeFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	JLabel lblId;
	JTextField txtId;
	JButton btnCancel, btnDelete;
	
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
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
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
					System.err.println(e1.getMessage());
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
