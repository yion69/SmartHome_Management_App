/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos101;

import com.formdev.flatlaf.IntelliJTheme;
import java.sql.ResultSetMetaData;
import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class Main_form extends javax.swing.JFrame {
	
	UserInformation userInfo;

	ImageIcon homeIcon = new ImageIcon(getClass().getResource("/cos101/Assets/home.png"));
	ImageIcon clientIcon = new ImageIcon(getClass().getResource("/cos101/Assets/people.png"));
	ImageIcon itemIcon = new ImageIcon(getClass().getResource("/cos101/Assets/item.png"));
	ImageIcon createIcon = new ImageIcon(getClass().getResource("/cos101/Assets/create.png"));
	ImageIcon modifyIcon = new ImageIcon(getClass().getResource("/cos101/Assets/modify.png"));
	ImageIcon duckIcon = new ImageIcon(getClass().getResource("/cos101/Assets/duck.png"));
	ImageIcon moreTableIcon = new ImageIcon(getClass().getResource("/cos101/Assets/moreTable.png"));
	//DO NOT CHANGE THE String passwor to String password I REPEAT DO NOT CORRECT THE String passwor VARIABLE
	public Main_form() {

		initComponents();

		jTabbedPane1.setIconAt(0, homeIcon);
		jTabbedPane1.setIconAt(1, clientIcon);
		jTabbedPane1.setIconAt(2, itemIcon);
		jTabbedPane1.setIconAt(3, createIcon);
		jTabbedPane1.setIconAt(4, modifyIcon);
		jTabbedPane1.setIconAt(5, moreTableIcon);
		picFrame.setIcon(scaleIcon(duckIcon));
		String[] list = {"Int", "Boolean", "Float", "varchar(15)", "varchar(25)", "varchar(35)", "varchar(50)"};
		JComboBox cmb = new JComboBox(list);
		create_table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cmb));

		displayLoginInformation();
		displayData("client_table");
		displayData("item_table");
		displayTables();
		displayOtherTableData();
		cellCenter(create_table, SwingConstants.CENTER);

	}
	public void setUser(UserInformation info){
		this.userInfo = info;
		txtUserID.setText(userInfo.UserID);
		txtUserPassword.setText(userInfo.getUserPassword());
	}
	
	public void displayLoginInformation() {
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;

			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			String tableCount = null;
			String dbName = con.getCatalog();
			String sql = "SELECT COUNT(*) AS `Count` FROM information_schema.tables WHERE  table_schema='" + dbName + "' ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				tableCount = rs.getString("Count");
			}
			txtDBName.setText(dbName);
			txtTableCount.setText(tableCount);

		} catch (java.sql.SQLException err) {
			System.out.println(err);
		}
	}

	public void displayTables() {

		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) clientTable.getModel();

			String sql = "show tables";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			int columnCount = rs.getMetaData().getColumnCount();
			Object[] data = new Object[columnCount];
			DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<>();
			cboTables.setModel(newModel);
			confirmTableDeletion.setSelected(false);
			btnDeleteTable.setEnabled(false);
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					data[i - 1] = rs.getObject(i);
					cboTables.addItem(data[i - 1].toString());
				}
			}

		} catch (java.sql.SQLException err) {
			System.out.println(err);
		}
	}
	
	public void displayOtherTableData(){
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) modify_table.getModel();

			con = DriverManager.getConnection(url, username, passwor);
			String dbName = con.getCatalog();
			String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = '"+dbName+"' AND table_name NOT IN ('client_table', 'item_table');";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			int columnCount = rs.getMetaData().getColumnCount();
			Object[] data = new Object[columnCount];
			DefaultComboBoxModel<String> newModel = new DefaultComboBoxModel<>();
			cboOtherTables.setModel(newModel);
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					data[i - 1] = rs.getObject(i);
					cboOtherTables.addItem(data[i - 1].toString());
				}
			}

		} catch (java.sql.SQLException err) {
			System.out.println(err);
		}
	}
	public void displayData(String table) {

		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = null;
			if (table.equals("client_table")) {
				model = (DefaultTableModel) clientTable.getModel();
				cellCenter(clientTable, SwingConstants.CENTER);
			} else {
				model = (DefaultTableModel) itemTable.getModel();
				cellCenter(itemTable, SwingConstants.CENTER);
			}

			String sql = "select * from " + table + "";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			int columnCount = rs.getMetaData().getColumnCount();

			model.setRowCount(0);

			while (rs.next()) {
				Object[] data = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					data[i - 1] = rs.getObject(i);
				}
				model.addRow(data);
			}
		} catch (java.sql.SQLException err) {
			System.out.println(err);
		}
	}

	public void descTableData(String tableName) {
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) modify_table.getModel();

			String sql = "desc " + tableName + " ";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			int columnCount = rs.getMetaData().getColumnCount();
			cellCenter(modify_table, SwingConstants.CENTER);
			model.setRowCount(0);

			while (rs.next()) {
				Object[] data = new Object[columnCount];
				Object[] colHead = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					data[i - 1] = rs.getObject(i);
				}

				model.addRow(data);
			}
		} catch (java.sql.SQLException err) {
			System.out.println(err);
		}
	}

	public void clearTextFields() {
		txtCID.setText("");
		txtCName.setText("");
		txtCAge.setText("");
		txtCAddress.setText("");
		txtCTelephone.setText("");
		txtCGender.setText("");
		txtCAge.setText("");

		txtTName.setText("");
		txtTColName.setText("");
		txtTType.setText("");
		txtTPrimary.setText("");
		txtTNull.setText("");

	}

	public static void cellCenter(JTable table, int alignment) {
		DefaultTableCellRenderer cRender = new DefaultTableCellRenderer();
		cRender.setHorizontalAlignment(alignment);

		TableModel tableModel = table.getModel();

		for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
			table.getColumnModel().getColumn(columnIndex).setCellRenderer(cRender);
		}
	}

	public final ImageIcon scaleIcon(ImageIcon img) {
		try {

			Image tempImg = img.getImage();
			Image scaleImg = tempImg.getScaledInstance(560, 360, java.awt.Image.SCALE_SMOOTH);
			ImageIcon respond = new ImageIcon(scaleImg);
			return respond;

		} catch (NullPointerException err) {

			System.err.println(err);
			return null;

		} catch (Exception err) {

			System.err.println(err);
			return null;

		}
	}

	public void insertData(String id, String name, String address, String phone, String email, String gender, String age) {
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
			String sql = "insert into client_table values('" + id + "', '" + name + "', '" + address + "', '" + phone + "', '" + email + "', '" + gender + "', '" + age + "' )";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Data Added");
			displayData("client_table");

		} catch (Exception err) {
			System.out.println(err);
		}
	}

	public void insertDataItem(String tid, String tname, String ttype, String tquantity, String tprice, String cid) {
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
			String sql = "insert into item_table values('" + tid + "', '" + tname + "', '" + ttype + "', " + tquantity + ", " + tprice + ", '" + cid + "')";
			System.out.println(sql);
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Data Added");
			displayData("item_table");

		} catch (Exception err) {
			System.out.println(err);
		}
	}

	public void insertDataOther(){
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel)other_table.getModel();
			
			int colCount = model.getColumnCount();
			int rowCount = model.getRowCount();
			String tName = cboOtherTables.getSelectedItem().toString();
			String[] data = new String[colCount];

			for(int i = 0; i<colCount;i++){
				data[i] = model.getValueAt(rowCount-1, i).toString();
				
			}

			String queryAppend = "'"+data[0]+"'";
			System.out.println(data.length);
			for (int j=1; j<data.length;j++){
				queryAppend += ",'"+data[j]+"'";
			}
			String sql = "insert into "+tName+" values("+queryAppend+")";
			System.out.println(sql);
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Data Added");
			displayOtherTableData();
			
		} catch (Exception err) {
			System.out.println(err);
		}
	}
	
	public void updateData(String id, String name, String address, String telephone, String email, String gender, String age) {
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
			String sql = "update client_table set client_Name='" + name + "', client_Add='" + address + "', client_Tel='" + telephone + "', client_Email='" + email + "', client_Gender='" + gender + "', client_Age='" + age + "' where client_ID='" + id + "' ";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Data Updated");
			displayData("client_table");

		} catch (Exception err) {
			System.out.println(err);
		}
	}

	public void updateDataItem(){
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
			String sql = "update item_table set item_Name='" + txtIName.getText() + "', item_Type='" + txtIType.getText() + "', item_Quantity='" + txtIQuantity.getText() + "', item_Price='" + txtIPrice.getText() + "', client_ID='" + txtICID.getText() + "' where item_ID='" + txtIID.getText() + "' ";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Data Updated");
			displayData("item_table");

		} catch (Exception err) {
			System.out.println(err);
		}
	}
	
	public void deleteData() {
		String request = JOptionPane.showInputDialog("Please Input ID");
		System.out.println(request);
		if (request.equals(null)) {
			return;
		}

		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
			String sql = "delete from client_table where client_id='" + request + "' ";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Data Deleted");
			displayData("client_table");

		} catch (Exception err) {
			System.out.println(err);
		}
	}
	
	public void deleteDataItem() {
		String request = JOptionPane.showInputDialog("Please Input ID");
		System.out.println(request);
		if (request.equals(null)) {
			return;
		}

		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
			String sql = "delete from item_table where item_ID='" + request + "' ";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Data Deleted");
			displayData("item_table");

		} catch (Exception err) {
			System.out.println(err);
		}
	}
	public void deleteDataOther(){
		String tableName = cboOtherTables.getSelectedItem().toString();
		String request = JOptionPane.showInputDialog("Please Input value from first column");
		System.out.println(request);
		if (request.equals(null)) {
			return;
		}

		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) other_table.getModel();
			String[] columnN = new String[2];
			columnN = model.getColumnName(0).split(" ");
			String sql = "delete from "+tableName+" where "+columnN[0]+"='" + request + "' ";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Data Deleted");

		} catch (Exception err) {
			System.out.println(err);
		}
	}
	
	public void deleteTable(String table_name) {
		if (txtMTableName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please select or input Table name");
			return;
		}
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
			String sql = "drop table " + table_name + "";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Table Deleted");

		} catch (Exception err) {
			System.out.println(err);
		}
	}

	public void setRow() {
		clearTextFields();
		DefaultTableModel model = (DefaultTableModel) create_table.getModel();
		int rowCount = model.getRowCount();
		int colCount = model.getColumnCount();
		int addRow = Integer.parseInt(cmbColCount.getSelectedItem().toString());
		Object[] data = new Object[5];
		model.setRowCount(0);
		for (int i = 1; i <= addRow; i++) {
			data[0] = i;
			data[1] = "eg.ColumnName";
			data[2] = "Int";
			data[3] = false;
			data[4] = false;
			model.addRow(data);
		}

	}
	public void displayOtherData(String table){
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) other_table.getModel();

			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			String dbName = con.getCatalog();
			String sql = "SELECT column_name,data_type FROM information_schema.columns WHERE table_schema='"+dbName+"' AND table_name ='"+table+"'ORDER BY ORDINAL_POSITION";
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			model.setRowCount(0);
			model.setColumnCount(0);
			while(rs.next()){
				model.addColumn(rs.getString("column_name")+" ("+rs.getString("data_type")+")");
			}
			
			sql = "SELECT * FROM "+table+" ";
			rs = stmt.executeQuery(sql);
			int columnCount = rs.getMetaData().getColumnCount();
			cellCenter(other_table, SwingConstants.CENTER);
			while (rs.next()) {
				Object[] data = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					data[i - 1] = rs.getObject(i);
				} 
				model.addRow(data);
			}
			Object[] emptyRow = new Object[columnCount];
			for (int i = 0; i < columnCount; i++) {
				emptyRow[i] = "add"; 
			}
			model.addRow(emptyRow);
		} catch (java.sql.SQLException err) {
			System.out.println(err);
		} catch (ArrayIndexOutOfBoundsException err){
			System.err.println(err);
		}

	}
	public String formatTableCreation(String colName, String colType, String pKey, String checkNull) {
		String[] data = new String[5];
		String primaryKey = "";
		String isNull = "NOT NULL";
		if (pKey.equals("true")) {
			primaryKey = "primary key";
		}
		if (checkNull.equals("true")) {
			isNull = "NULL";
		}

		data[0] = colName;
		data[1] = colType;
		data[2] = isNull;
		data[3] = primaryKey;
		return "" + data[0] + " " + data[1] + " " + data[2] + " " + data[3] + "";
	}

	public void createTableColumn() {
		DefaultTableModel model = (DefaultTableModel) modify_table.getModel();
		int rowCount = model.getRowCount();
		Boolean checkIfPrimaryExists = false;

		for (int i = 0; i <= rowCount - 1; i++) {
			Object tempCheck = model.getValueAt(i, 3);
			if (tempCheck.equals("PRI")) {
				checkIfPrimaryExists = true;
			}
		}
		if (checkIfPrimaryExists == true && chkMPrimary.isSelected() == true) {
			JOptionPane.showMessageDialog(this, "You can't have multiple Primary key");
			return;
		}
		checkIfPrimaryExists = false;
		String url = "jdbc:mysql://localhost:3305/testDb";
		String username = "root";
		String passwor = "thutanaing";
		String driver = "com.mysql.jdbc.Driver";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String pri = null;
		if (chkMPrimary.isSelected() == true && chkMisNull.isSelected() == true) {
			JOptionPane.showMessageDialog(this, "You can't have Nullable Primary key");
			return;
		}
		if (chkMPrimary.isSelected() == true) {
			pri = "true";
		}
		try {
			String putNull = "false";
			if (chkMisNull.isSelected() == true) {
				putNull = "true";
			}
			String dataOne = formatTableCreation(
				txtMColName.getText(),
				cboMType.getSelectedItem().toString(),
				pri, putNull
			);

			String sql = "ALTER TABLE " + txtMTableName.getText() + " ADD " + dataOne + " ";
			System.out.println(sql);
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(this, "Column Inserted");
		} catch (Exception err) {
			System.err.println(err);
		}
	}

	public void dropPrimaryKey(String table_name) {
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			String sql = "ALTER TABLE " + table_name + " DROP PRIMARY KEY";
			System.out.println(sql + "This is from dropPrimaryKey function");
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			System.out.println("Primary key dropped");
		} catch (Exception err) {
			System.out.println(err);
		}
	}

	public void updateTableColumn() {
		DefaultTableModel model = (DefaultTableModel) modify_table.getModel();
		int rowCount = model.getRowCount();
		Boolean checkIfPrimaryExists = false;

		for (int i = 0; i <= rowCount - 1; i++) {
			Object tempCheck = model.getValueAt(i, 3);
			if (tempCheck.equals("PRI")) {
				checkIfPrimaryExists = true;
			}
		}
		if (checkIfPrimaryExists == true && chkMPrimary.isSelected() == true) {
			JOptionPane.showMessageDialog(this, "You can't have multiple Primary key");
			return;
		}
		if (checkIfPrimaryExists == true && chkMPrimary.isSelected() == false) {
			dropPrimaryKey(txtMTableName.getText());
		}
		checkIfPrimaryExists = false;
		String url = "jdbc:mysql://localhost:3305/testDb";
		String username = "root";
		String passwor = "thutanaing";
		String driver = "com.mysql.jdbc.Driver";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String pri = "";
		if (chkMPrimary.isSelected() == true && chkMisNull.isSelected() == true) {
			JOptionPane.showMessageDialog(this, "You can't have Nullable Primary key");
			return;
		}
		if (chkMPrimary.isSelected() == true) {
			pri = ", ADD PRIMARY KEY(" + txtMColName.getText() + ")";
		}
		try {
			String putNull = "NOT NULL";
			if (chkMisNull.isSelected() == true) {
				putNull = "NULL";
			}

			String dType = cboMType.getSelectedItem().toString();
			String sql = "ALTER TABLE " + txtMTableName.getText() + " MODIFY COLUMN " + txtMColName.getText() + " " + dType + " " + putNull + "" + pri + " ";
			System.out.println(sql);
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(this, "Column Updated");
		} catch (Exception err) {
			System.err.println(err);
		}
	}

	public void deleteTableColumn() {
		if (txtMColName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please select or input Column name");
			return;
		}
		try {
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
			String sql = "ALTER TABLE " + txtMTableName.getText() + " DROP " + txtMColName.getText() + "";
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			stmt.execute(sql);
			JOptionPane.showMessageDialog(this, "Column Deleted");

		} catch (Exception err) {
			System.out.println(err);
		}
	}

	public void createTable(int count) {

		DefaultTableModel model = (DefaultTableModel) create_table.getModel();
		String url = "jdbc:mysql://localhost:3305/testDb";
		String username = "root";
		String passwor = "thutanaing";
		String driver = "com.mysql.jdbc.Driver";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			if (count == 1) {
				String dataOne = formatTableCreation(
					model.getValueAt(0, 1).toString(),
					model.getValueAt(0, 2).toString(),
					model.getValueAt(0, 3).toString(),
					model.getValueAt(0, 4).toString()
				);
				String sql = "CREATE TABLE " + txtTName.getText() + " (" + dataOne + ")";
				con = DriverManager.getConnection(url, username, passwor);
				stmt = con.createStatement();
				stmt.execute(sql);
			} else if (count == 2) {
				String dataOne = formatTableCreation(
					model.getValueAt(0, 1).toString(),
					model.getValueAt(0, 2).toString(),
					model.getValueAt(0, 3).toString(),
					model.getValueAt(0, 4).toString()
				);
				String dataTwo = formatTableCreation(
					model.getValueAt(1, 1).toString(),
					model.getValueAt(1, 2).toString(),
					model.getValueAt(1, 3).toString(),
					model.getValueAt(1, 4).toString()
				);
				String sql = "CREATE TABLE " + txtTName.getText() + " (" + dataOne + ", " + dataTwo + ")";
				con = DriverManager.getConnection(url, username, passwor);
				stmt = con.createStatement();
				stmt.execute(sql);
			} else if (count == 3) {
				String dataOne = formatTableCreation(
					model.getValueAt(0, 1).toString(),
					model.getValueAt(0, 2).toString(),
					model.getValueAt(0, 3).toString(),
					model.getValueAt(0, 4).toString()
				);
				String dataTwo = formatTableCreation(
					model.getValueAt(1, 1).toString(),
					model.getValueAt(1, 2).toString(),
					model.getValueAt(1, 3).toString(),
					model.getValueAt(1, 4).toString()
				);
				String dataThree = formatTableCreation(
					model.getValueAt(2, 1).toString(),
					model.getValueAt(2, 2).toString(),
					model.getValueAt(2, 3).toString(),
					model.getValueAt(2, 4).toString()
				);
				String sql = "CREATE TABLE " + txtTName.getText() + " (" + dataOne + ", " + dataTwo + ", " + dataThree + ")";
				con = DriverManager.getConnection(url, username, passwor);
				stmt = con.createStatement();
				stmt.execute(sql);
			} else if (count == 4) {
				String dataOne = formatTableCreation(
					model.getValueAt(0, 1).toString(),
					model.getValueAt(0, 2).toString(),
					model.getValueAt(0, 3).toString(),
					model.getValueAt(0, 4).toString()
				);
				String dataTwo = formatTableCreation(
					model.getValueAt(1, 1).toString(),
					model.getValueAt(1, 2).toString(),
					model.getValueAt(1, 3).toString(),
					model.getValueAt(1, 4).toString()
				);
				String dataThree = formatTableCreation(
					model.getValueAt(2, 1).toString(),
					model.getValueAt(2, 2).toString(),
					model.getValueAt(2, 3).toString(),
					model.getValueAt(2, 4).toString()
				);
				String dataFour = formatTableCreation(
					model.getValueAt(3, 1).toString(),
					model.getValueAt(3, 2).toString(),
					model.getValueAt(3, 3).toString(),
					model.getValueAt(3, 4).toString()
				);
				String sql = "CREATE TABLE " + txtTName.getText() + " (" + dataOne + ", " + dataTwo + ", " + dataThree + ", " + dataFour + ")";
				con = DriverManager.getConnection(url, username, passwor);
				stmt = con.createStatement();
				stmt.execute(sql);
			} else if (count == 5) {
				String dataOne = formatTableCreation(
					model.getValueAt(0, 1).toString(),
					model.getValueAt(0, 2).toString(),
					model.getValueAt(0, 3).toString(),
					model.getValueAt(0, 4).toString()
				);
				String dataTwo = formatTableCreation(
					model.getValueAt(1, 1).toString(),
					model.getValueAt(1, 2).toString(),
					model.getValueAt(1, 3).toString(),
					model.getValueAt(1, 4).toString()
				);
				String dataThree = formatTableCreation(
					model.getValueAt(2, 1).toString(),
					model.getValueAt(2, 2).toString(),
					model.getValueAt(2, 3).toString(),
					model.getValueAt(2, 4).toString()
				);
				String dataFour = formatTableCreation(
					model.getValueAt(3, 1).toString(),
					model.getValueAt(3, 2).toString(),
					model.getValueAt(3, 3).toString(),
					model.getValueAt(3, 4).toString()
				);
				String dataFive = formatTableCreation(
					model.getValueAt(4, 1).toString(),
					model.getValueAt(4, 2).toString(),
					model.getValueAt(4, 3).toString(),
					model.getValueAt(4, 4).toString()
				);
				String sql = "CREATE TABLE " + txtTName.getText() + " (" + dataOne + ", " + dataTwo + ", " + dataThree + ", " + dataFour + ", " + dataFive + " )";
				con = DriverManager.getConnection(url, username, passwor);
				stmt = con.createStatement();
				stmt.execute(sql);
			} else if (count == 6) {
				String dataOne = formatTableCreation(
					model.getValueAt(0, 1).toString(),
					model.getValueAt(0, 2).toString(),
					model.getValueAt(0, 3).toString(),
					model.getValueAt(0, 4).toString()
				);
				String dataTwo = formatTableCreation(
					model.getValueAt(1, 1).toString(),
					model.getValueAt(1, 2).toString(),
					model.getValueAt(1, 3).toString(),
					model.getValueAt(1, 4).toString()
				);
				String dataThree = formatTableCreation(
					model.getValueAt(2, 1).toString(),
					model.getValueAt(2, 2).toString(),
					model.getValueAt(2, 3).toString(),
					model.getValueAt(2, 4).toString()
				);
				String dataFour = formatTableCreation(
					model.getValueAt(3, 1).toString(),
					model.getValueAt(3, 2).toString(),
					model.getValueAt(3, 3).toString(),
					model.getValueAt(3, 4).toString()
				);
				String dataFive = formatTableCreation(
					model.getValueAt(4, 1).toString(),
					model.getValueAt(4, 2).toString(),
					model.getValueAt(4, 3).toString(),
					model.getValueAt(4, 4).toString()
				);
				String dataSix = formatTableCreation(
					model.getValueAt(5, 1).toString(),
					model.getValueAt(5, 2).toString(),
					model.getValueAt(5, 3).toString(),
					model.getValueAt(5, 4).toString()
				);
				String sql = "CREATE TABLE " + txtTName.getText() + " (" + dataOne + ", " + dataTwo + ", " + dataThree + ", " + dataFour + "," + dataFive + "," + dataSix + " )";
				con = DriverManager.getConnection(url, username, passwor);
				stmt = con.createStatement();
				stmt.execute(sql);
			}
		} catch (java.sql.SQLException err) {
			System.err.print(err);
		}
		JOptionPane.showMessageDialog(this, "Table Created");
		clearTextFields();
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jMenuItem1 = new javax.swing.JMenuItem();
                jPanel1 = new javax.swing.JPanel();
                jTabbedPane1 = new javax.swing.JTabbedPane();
                jPanel2 = new javax.swing.JPanel();
                jPanel8 = new javax.swing.JPanel();
                picFrame = new javax.swing.JLabel();
                jPanel9 = new javax.swing.JPanel();
                txtTableCount = new javax.swing.JTextField();
                jLabel32 = new javax.swing.JLabel();
                txtDBName = new javax.swing.JTextField();
                jLabel33 = new javax.swing.JLabel();
                txtUserPassword = new javax.swing.JTextField();
                jLabel34 = new javax.swing.JLabel();
                jLabel35 = new javax.swing.JLabel();
                jLabel36 = new javax.swing.JLabel();
                txtUserID = new javax.swing.JTextField();
                jButton14 = new javax.swing.JButton();
                jPanel3 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                clientTable = new javax.swing.JTable();
                txtCID = new javax.swing.JTextField();
                jLabel1 = new javax.swing.JLabel();
                txtCName = new javax.swing.JTextField();
                jLabel2 = new javax.swing.JLabel();
                txtCAddress = new javax.swing.JTextField();
                jLabel4 = new javax.swing.JLabel();
                cboCSearch = new javax.swing.JComboBox<>();
                txtCSearch = new javax.swing.JTextField();
                jLabel7 = new javax.swing.JLabel();
                txtCTelephone = new javax.swing.JTextField();
                jLabel8 = new javax.swing.JLabel();
                txtCGender = new javax.swing.JTextField();
                jLabel9 = new javax.swing.JLabel();
                txtCAge = new javax.swing.JTextField();
                jLabel10 = new javax.swing.JLabel();
                jButton1 = new javax.swing.JButton();
                jButton2 = new javax.swing.JButton();
                jButton3 = new javax.swing.JButton();
                txtCEmail = new javax.swing.JTextField();
                jButton4 = new javax.swing.JButton();
                jLabel11 = new javax.swing.JLabel();
                jButton12 = new javax.swing.JButton();
                jButton13 = new javax.swing.JButton();
                jPanel5 = new javax.swing.JPanel();
                jScrollPane4 = new javax.swing.JScrollPane();
                itemTable = new javax.swing.JTable();
                txtIID = new javax.swing.JTextField();
                jLabel3 = new javax.swing.JLabel();
                txtIName = new javax.swing.JTextField();
                jLabel12 = new javax.swing.JLabel();
                txtIType = new javax.swing.JTextField();
                cboISearch = new javax.swing.JComboBox<>();
                txtISearch = new javax.swing.JTextField();
                jLabel14 = new javax.swing.JLabel();
                txtIPrice = new javax.swing.JTextField();
                jLabel24 = new javax.swing.JLabel();
                txtIQuantity = new javax.swing.JTextField();
                jLabel25 = new javax.swing.JLabel();
                jButton7 = new javax.swing.JButton();
                btnISearch = new javax.swing.JButton();
                jButton15 = new javax.swing.JButton();
                txtICID = new javax.swing.JTextField();
                jButton16 = new javax.swing.JButton();
                jLabel27 = new javax.swing.JLabel();
                jButton17 = new javax.swing.JButton();
                jButton18 = new javax.swing.JButton();
                jLabel28 = new javax.swing.JLabel();
                jPanel4 = new javax.swing.JPanel();
                jScrollPane2 = new javax.swing.JScrollPane();
                create_table = new javax.swing.JTable();
                txtTColName = new javax.swing.JTextField();
                jLabel15 = new javax.swing.JLabel();
                jLabel16 = new javax.swing.JLabel();
                txtTType = new javax.swing.JTextField();
                jLabel17 = new javax.swing.JLabel();
                txtTNull = new javax.swing.JTextField();
                txtTPrimary = new javax.swing.JTextField();
                jLabel18 = new javax.swing.JLabel();
                cmbColCount = new javax.swing.JComboBox<>();
                txtTName = new javax.swing.JTextField();
                jLabel19 = new javax.swing.JLabel();
                jButton5 = new javax.swing.JButton();
                jPanel6 = new javax.swing.JPanel();
                jScrollPane3 = new javax.swing.JScrollPane();
                modify_table = new javax.swing.JTable();
                txtMColName = new javax.swing.JTextField();
                jLabel21 = new javax.swing.JLabel();
                chkMPrimary = new javax.swing.JCheckBox();
                confirmTableDeletion = new javax.swing.JCheckBox();
                jLabel22 = new javax.swing.JLabel();
                jLabel23 = new javax.swing.JLabel();
                cboTables = new javax.swing.JComboBox<>();
                jButton6 = new javax.swing.JButton();
                btnDeleteTable = new javax.swing.JButton();
                jButton8 = new javax.swing.JButton();
                jLabel20 = new javax.swing.JLabel();
                txtMTableName = new javax.swing.JTextField();
                cboMType = new javax.swing.JComboBox<>();
                jButton9 = new javax.swing.JButton();
                chkMisNull = new javax.swing.JCheckBox();
                jButton10 = new javax.swing.JButton();
                jButton11 = new javax.swing.JButton();
                jPanel7 = new javax.swing.JPanel();
                jScrollPane5 = new javax.swing.JScrollPane();
                other_table = new javax.swing.JTable();
                cboOtherTables = new javax.swing.JComboBox<>();
                jLabel26 = new javax.swing.JLabel();
                jButton19 = new javax.swing.JButton();
                jButton21 = new javax.swing.JButton();
                txtOSearch = new javax.swing.JTextField();
                jLabel29 = new javax.swing.JLabel();
                btnISearch1 = new javax.swing.JButton();
                jButton22 = new javax.swing.JButton();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();

                jMenuItem1.setText("jMenuItem1");

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setMinimumSize(new java.awt.Dimension(900, 510));
                setResizable(false);
                setSize(new java.awt.Dimension(970, 530));
                getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jPanel1.setBackground(new java.awt.Color(253, 246, 227));
                jPanel1.setForeground(new java.awt.Color(248, 244, 225));
                jPanel1.setMinimumSize(new java.awt.Dimension(970, 530));
                jPanel1.setName(""); // NOI18N
                jPanel1.setPreferredSize(new java.awt.Dimension(978, 510));
                jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jTabbedPane1.setPreferredSize(new java.awt.Dimension(950, 490));

                jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jPanel8.setBackground(new java.awt.Color(253, 246, 227));
                jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(116, 139, 117), 2));
                jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
                jPanel8.add(picFrame, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 360));

                jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 560, 360));

                jPanel9.setBackground(new java.awt.Color(253, 246, 227));
                jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(116, 139, 117), 2));
                jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                txtTableCount.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
                txtTableCount.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                txtTableCount.setFocusable(false);
                jPanel9.add(txtTableCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 250, 30));

                jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel32.setForeground(new java.awt.Color(116, 139, 117));
                jLabel32.setText("Table Count");
                jPanel9.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 110, -1));

                txtDBName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
                txtDBName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                txtDBName.setFocusable(false);
                jPanel9.add(txtDBName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 250, -1));

                jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel33.setForeground(new java.awt.Color(116, 139, 117));
                jLabel33.setText("Database Name");
                jPanel9.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 130, -1));

                txtUserPassword.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
                txtUserPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                txtUserPassword.setFocusable(false);
                jPanel9.add(txtUserPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 250, -1));

                jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel34.setForeground(new java.awt.Color(116, 139, 117));
                jLabel34.setText("User Password");
                jPanel9.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 120, -1));

                jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel35.setForeground(new java.awt.Color(116, 139, 117));
                jLabel35.setText("User ID");
                jPanel9.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 70, -1));

                jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                jLabel36.setForeground(new java.awt.Color(116, 139, 117));
                jLabel36.setText("User Information");
                jPanel9.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 160, -1));

                txtUserID.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
                txtUserID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                txtUserID.setFocusable(false);
                jPanel9.add(txtUserID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 250, -1));

                jButton14.setBackground(new java.awt.Color(248, 113, 113));
                jButton14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton14.setForeground(new java.awt.Color(255, 255, 255));
                jButton14.setText("LOGOUT");
                jButton14.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton14ActionPerformed(evt);
                        }
                });
                jPanel9.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 250, -1));

                jPanel2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, 330, 360));

                jTabbedPane1.addTab("Home", jPanel2);

                jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                clientTable.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {

                        },
                        new String [] {
                                "ID", "Name", "Address", "Telephone", "Email", "Gender", "Age"
                        }
                ));
                clientTable.getTableHeader().setResizingAllowed(false);
                clientTable.getTableHeader().setReorderingAllowed(false);
                clientTable.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                clientTableMouseClicked(evt);
                        }
                });
                jScrollPane1.setViewportView(clientTable);
                if (clientTable.getColumnModel().getColumnCount() > 0) {
                        clientTable.getColumnModel().getColumn(6).setPreferredWidth(50);
                        clientTable.getColumnModel().getColumn(6).setMaxWidth(50);
                        clientTable.getColumnModel().getColumn(6).setHeaderValue("Age");
                }

                jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 940, 240));

                txtCID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtCID.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtCIDActionPerformed(evt);
                        }
                });
                jPanel3.add(txtCID, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 80, -1));

                jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel1.setText("Client ID");
                jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 70, -1));

                txtCName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtCName.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtCNameActionPerformed(evt);
                        }
                });
                jPanel3.add(txtCName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 170, -1));

                jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel2.setText("Name");
                jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 50, -1));

                txtCAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtCAddress.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtCAddressActionPerformed(evt);
                        }
                });
                jPanel3.add(txtCAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 260, -1));

                jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel4.setText("Address");
                jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 120, -1));

                cboCSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Name" }));
                cboCSearch.setFocusable(false);
                cboCSearch.setPreferredSize(new java.awt.Dimension(68, 26));
                cboCSearch.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboCSearchActionPerformed(evt);
                        }
                });
                jPanel3.add(cboCSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 339, 70, 30));

                txtCSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtCSearch.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtCSearchActionPerformed(evt);
                        }
                });
                jPanel3.add(txtCSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 339, 180, 30));

                jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel7.setText("Search");
                jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(592, 320, 120, -1));

                txtCTelephone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtCTelephone.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtCTelephoneActionPerformed(evt);
                        }
                });
                jPanel3.add(txtCTelephone, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 140, -1));

                jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel8.setText("Telephone");
                jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 120, -1));

                txtCGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtCGender.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtCGenderActionPerformed(evt);
                        }
                });
                jPanel3.add(txtCGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, 60, -1));

                jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel9.setText("Gender");
                jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 260, 50, -1));

                txtCAge.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtCAge.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtCAgeActionPerformed(evt);
                        }
                });
                jPanel3.add(txtCAge, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 280, 40, -1));

                jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel10.setText("Age");
                jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 260, 30, -1));

                jButton1.setBackground(new java.awt.Color(245, 251, 239));
                jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton1.setForeground(new java.awt.Color(116, 139, 117));
                jButton1.setText("Delete");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });
                jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(833, 280, 115, -1));

                jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton2.setText("Search");
                jButton2.setFocusable(false);
                jButton2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton2ActionPerformed(evt);
                        }
                });
                jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 339, 110, 30));

                jButton3.setBackground(new java.awt.Color(245, 251, 239));
                jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton3.setForeground(new java.awt.Color(116, 139, 117));
                jButton3.setText("Update");
                jButton3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton3ActionPerformed(evt);
                        }
                });
                jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(711, 280, 115, -1));

                txtCEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtCEmail.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtCEmailActionPerformed(evt);
                        }
                });
                jPanel3.add(txtCEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 260, -1));

                jButton4.setBackground(new java.awt.Color(245, 251, 239));
                jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton4.setForeground(new java.awt.Color(116, 139, 117));
                jButton4.setText("Insert");
                jButton4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton4ActionPerformed(evt);
                        }
                });
                jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 280, 115, -1));

                jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel11.setText("Email");
                jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 320, 120, -1));

                jButton12.setBackground(new java.awt.Color(245, 251, 239));
                jButton12.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
                jButton12.setForeground(new java.awt.Color(116, 139, 117));
                jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cos101/Assets/refresh.png"))); // NOI18N
                jButton12.setText("Refresh");
                jButton12.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton12ActionPerformed(evt);
                        }
                });
                jPanel3.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 250, 116, -1));

                jButton13.setBackground(new java.awt.Color(245, 251, 239));
                jButton13.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
                jButton13.setForeground(new java.awt.Color(116, 139, 117));
                jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cos101/Assets/clear.png"))); // NOI18N
                jButton13.setText("Clear");
                jButton13.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton13ActionPerformed(evt);
                        }
                });
                jPanel3.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 250, 115, -1));

                jTabbedPane1.addTab("Client Table", jPanel3);

                jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                itemTable.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {

                        },
                        new String [] {
                                "Item ID", "Item Name", "Item Type", "Item Quantity", "Item_Price", "Client ID"
                        }
                ));
                itemTable.getTableHeader().setResizingAllowed(false);
                itemTable.getTableHeader().setReorderingAllowed(false);
                itemTable.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                itemTableMouseClicked(evt);
                        }
                });
                jScrollPane4.setViewportView(itemTable);
                if (itemTable.getColumnModel().getColumnCount() > 0) {
                        itemTable.getColumnModel().getColumn(0).setHeaderValue("Item ID");
                        itemTable.getColumnModel().getColumn(1).setHeaderValue("Item Name");
                        itemTable.getColumnModel().getColumn(2).setHeaderValue("Item Type");
                        itemTable.getColumnModel().getColumn(3).setHeaderValue("Item Quantity");
                        itemTable.getColumnModel().getColumn(4).setHeaderValue("Item_Price");
                        itemTable.getColumnModel().getColumn(5).setHeaderValue("Client ID");
                }

                jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 940, 240));

                txtIID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtIID.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtIIDActionPerformed(evt);
                        }
                });
                jPanel5.add(txtIID, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 80, -1));

                jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel3.setText("Item ID");
                jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 70, -1));

                txtIName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtIName.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtINameActionPerformed(evt);
                        }
                });
                jPanel5.add(txtIName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 170, -1));

                jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel12.setText("Item Name");
                jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 90, -1));

                txtIType.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtIType.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtITypeActionPerformed(evt);
                        }
                });
                jPanel5.add(txtIType, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 260, -1));

                cboISearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Name" }));
                cboISearch.setFocusable(false);
                cboISearch.setPreferredSize(new java.awt.Dimension(68, 26));
                cboISearch.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboISearchActionPerformed(evt);
                        }
                });
                jPanel5.add(cboISearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 339, 70, 30));

                txtISearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtISearch.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtISearchActionPerformed(evt);
                        }
                });
                jPanel5.add(txtISearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 339, 180, 30));

                jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel14.setText("Search");
                jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(592, 320, 120, -1));

                txtIPrice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtIPrice.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtIPriceActionPerformed(evt);
                        }
                });
                jPanel5.add(txtIPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 140, -1));

                jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel24.setText("Item Price");
                jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 120, -1));

                txtIQuantity.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtIQuantity.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtIQuantityActionPerformed(evt);
                        }
                });
                jPanel5.add(txtIQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 280, 110, -1));

                jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel25.setText("Item Quantity");
                jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 260, 90, -1));

                jButton7.setBackground(new java.awt.Color(245, 251, 239));
                jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton7.setForeground(new java.awt.Color(116, 139, 117));
                jButton7.setText("Delete");
                jButton7.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton7ActionPerformed(evt);
                        }
                });
                jPanel5.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(833, 280, 115, -1));

                btnISearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnISearch.setText("Search");
                btnISearch.setFocusable(false);
                btnISearch.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnISearchActionPerformed(evt);
                        }
                });
                jPanel5.add(btnISearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 339, 110, 30));

                jButton15.setBackground(new java.awt.Color(245, 251, 239));
                jButton15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton15.setForeground(new java.awt.Color(116, 139, 117));
                jButton15.setText("Update");
                jButton15.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton15ActionPerformed(evt);
                        }
                });
                jPanel5.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(711, 280, 115, -1));

                txtICID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtICID.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtICIDActionPerformed(evt);
                        }
                });
                jPanel5.add(txtICID, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 260, -1));

                jButton16.setBackground(new java.awt.Color(245, 251, 239));
                jButton16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton16.setForeground(new java.awt.Color(116, 139, 117));
                jButton16.setText("Insert");
                jButton16.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton16ActionPerformed(evt);
                        }
                });
                jPanel5.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 280, 115, -1));

                jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel27.setText("Item Type");
                jPanel5.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 319, 120, -1));

                jButton17.setBackground(new java.awt.Color(245, 251, 239));
                jButton17.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
                jButton17.setForeground(new java.awt.Color(116, 139, 117));
                jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cos101/Assets/refresh.png"))); // NOI18N
                jButton17.setText("Refresh");
                jButton17.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton17ActionPerformed(evt);
                        }
                });
                jPanel5.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 250, 116, -1));

                jButton18.setBackground(new java.awt.Color(245, 251, 239));
                jButton18.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
                jButton18.setForeground(new java.awt.Color(116, 139, 117));
                jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cos101/Assets/clear.png"))); // NOI18N
                jButton18.setText("Clear");
                jButton18.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton18ActionPerformed(evt);
                        }
                });
                jPanel5.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 250, 115, -1));

                jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel28.setText("Client ID");
                jPanel5.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 320, 120, -1));

                jTabbedPane1.addTab("Item Table", jPanel5);

                jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                create_table.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                { new Integer(1), "eg.ColumnOne", "<Select>", null, null}
                        },
                        new String [] {
                                "No.", "Column Name", "Data Type", "Primary Key", "Null"
                        }
                ) {
                        Class[] types = new Class [] {
                                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class
                        };
                        boolean[] canEdit = new boolean [] {
                                false, true, true, true, true
                        };

                        public Class getColumnClass(int columnIndex) {
                                return types [columnIndex];
                        }

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                create_table.setRowHeight(35);
                create_table.setShowGrid(true);
                create_table.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                create_tableMouseClicked(evt);
                        }
                });
                jScrollPane2.setViewportView(create_table);
                if (create_table.getColumnModel().getColumnCount() > 0) {
                        create_table.getColumnModel().getColumn(0).setResizable(false);
                        create_table.getColumnModel().getColumn(0).setPreferredWidth(20);
                        create_table.getColumnModel().getColumn(3).setPreferredWidth(50);
                        create_table.getColumnModel().getColumn(4).setPreferredWidth(50);
                }

                jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 610, 380));

                txtTColName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtTColName.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtTColNameActionPerformed(evt);
                        }
                });
                jPanel4.add(txtTColName, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 110, 260, -1));

                jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel15.setText("Column Name");
                jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 90, 120, -1));

                jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel16.setText("Data Type ");
                jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 150, 120, -1));

                txtTType.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtTType.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtTTypeActionPerformed(evt);
                        }
                });
                jPanel4.add(txtTType, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 170, 260, -1));

                jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel17.setText("isNULL");
                jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 280, 120, -1));

                txtTNull.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtTNull.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtTNullActionPerformed(evt);
                        }
                });
                jPanel4.add(txtTNull, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 300, 260, -1));

                txtTPrimary.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtTPrimary.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtTPrimaryActionPerformed(evt);
                        }
                });
                jPanel4.add(txtTPrimary, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 240, 260, -1));

                jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel18.setText("Primary Key");
                jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 220, 120, -1));

                cmbColCount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", " " }));
                cmbColCount.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cmbColCountActionPerformed(evt);
                        }
                });
                jPanel4.add(cmbColCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 40, 100, 30));

                txtTName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtTName.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtTNameActionPerformed(evt);
                        }
                });
                jPanel4.add(txtTName, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 40, 150, 30));

                jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel19.setText("Table Name");
                jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 120, -1));

                jButton5.setBackground(new java.awt.Color(245, 251, 239));
                jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton5.setForeground(new java.awt.Color(116, 139, 117));
                jButton5.setText("Insert");
                jButton5.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton5ActionPerformed(evt);
                        }
                });
                jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 340, 260, 40));

                jTabbedPane1.addTab("Table Creation", jPanel4);

                jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                modify_table.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {

                        },
                        new String [] {
                                "Column Name", "Data Type", "isNULL", "PRIMARY KEY"
                        }
                ));
                modify_table.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                modify_tableMouseClicked(evt);
                        }
                });
                jScrollPane3.setViewportView(modify_table);

                jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 910, 250));

                txtMColName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtMColName.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMColNameActionPerformed(evt);
                        }
                });
                jPanel6.add(txtMColName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 190, 30));

                jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel21.setText("Data Type ");
                jPanel6.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 280, 120, -1));

                chkMPrimary.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                chkMPrimary.setText("Primary Key");
                jPanel6.add(chkMPrimary, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, -1, 30));

                confirmTableDeletion.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
                confirmTableDeletion.setText("Click here to Confirm");
                confirmTableDeletion.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                confirmTableDeletionActionPerformed(evt);
                        }
                });
                jPanel6.add(confirmTableDeletion, new org.netbeans.lib.awtextra.AbsoluteConstraints(785, 330, 140, 20));

                jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel22.setText("Column Name");
                jPanel6.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 120, -1));

                jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel23.setText("Table Name");
                jPanel6.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 120, -1));

                cboTables.setFocusable(false);
                cboTables.setPreferredSize(new java.awt.Dimension(68, 26));
                cboTables.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboTablesActionPerformed(evt);
                        }
                });
                jPanel6.add(cboTables, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 190, -1));

                jButton6.setBackground(new java.awt.Color(245, 251, 239));
                jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton6.setForeground(new java.awt.Color(116, 139, 117));
                jButton6.setText("Insert");
                jButton6.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton6ActionPerformed(evt);
                        }
                });
                jPanel6.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 360, 110, -1));

                btnDeleteTable.setBackground(new java.awt.Color(245, 251, 239));
                btnDeleteTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnDeleteTable.setForeground(new java.awt.Color(116, 139, 117));
                btnDeleteTable.setText("Delete Table");
                btnDeleteTable.setEnabled(false);
                btnDeleteTable.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDeleteTableActionPerformed(evt);
                        }
                });
                jPanel6.add(btnDeleteTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(785, 360, 150, -1));

                jButton8.setBackground(new java.awt.Color(245, 251, 239));
                jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton8.setForeground(new java.awt.Color(116, 139, 117));
                jButton8.setText("Delete");
                jButton8.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton8ActionPerformed(evt);
                        }
                });
                jPanel6.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 360, 110, -1));

                jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel20.setText("Tables");
                jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 120, -1));

                txtMTableName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtMTableName.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMTableNameActionPerformed(evt);
                        }
                });
                jPanel6.add(txtMTableName, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 160, -1));

                cboMType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "int", "boolean", "float", "varchar(15)", "varchar(25)", "varchar(35)", "varchar(50)" }));
                jPanel6.add(cboMType, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 160, 30));

                jButton9.setBackground(new java.awt.Color(245, 251, 239));
                jButton9.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
                jButton9.setForeground(new java.awt.Color(116, 139, 117));
                jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cos101/Assets/refresh.png"))); // NOI18N
                jButton9.setText("Refresh Table");
                jButton9.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton9ActionPerformed(evt);
                        }
                });
                jPanel6.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 270, 127, -1));

                chkMisNull.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                chkMisNull.setText("isNULL");
                jPanel6.add(chkMisNull, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, -1, 30));

                jButton10.setBackground(new java.awt.Color(245, 251, 239));
                jButton10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton10.setForeground(new java.awt.Color(116, 139, 117));
                jButton10.setText("Update");
                jButton10.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton10ActionPerformed(evt);
                        }
                });
                jPanel6.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 110, -1));

                jButton11.setBackground(new java.awt.Color(245, 251, 239));
                jButton11.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
                jButton11.setForeground(new java.awt.Color(116, 139, 117));
                jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cos101/Assets/clear.png"))); // NOI18N
                jButton11.setText("Clear TextFields");
                jButton11.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton11ActionPerformed(evt);
                        }
                });
                jPanel6.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(683, 270, -1, -1));

                jTabbedPane1.addTab("Table Modification", jPanel6);

                jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                other_table.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {

                        },
                        new String [] {

                        }
                ));
                other_table.getTableHeader().setResizingAllowed(false);
                other_table.getTableHeader().setReorderingAllowed(false);
                other_table.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                other_tableMouseClicked(evt);
                        }
                });
                jScrollPane5.setViewportView(other_table);

                jPanel7.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 940, 290));

                cboOtherTables.setFocusable(false);
                cboOtherTables.setPreferredSize(new java.awt.Dimension(68, 26));
                cboOtherTables.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboOtherTablesActionPerformed(evt);
                        }
                });
                jPanel7.add(cboOtherTables, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 250, 30));

                jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel26.setText("Tables");
                jPanel7.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 320, 120, -1));

                jButton19.setBackground(new java.awt.Color(245, 251, 239));
                jButton19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton19.setForeground(new java.awt.Color(116, 139, 117));
                jButton19.setText("Insert");
                jButton19.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton19ActionPerformed(evt);
                        }
                });
                jPanel7.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 340, 170, 30));

                jButton21.setBackground(new java.awt.Color(245, 251, 239));
                jButton21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton21.setForeground(new java.awt.Color(116, 139, 117));
                jButton21.setText("Delete");
                jButton21.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton21ActionPerformed(evt);
                        }
                });
                jPanel7.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 340, 170, 30));

                txtOSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                txtOSearch.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtOSearchActionPerformed(evt);
                        }
                });
                jPanel7.add(txtOSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 340, 210, 30));

                jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel29.setText("Search (First Column Only)");
                jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(642, 320, 150, -1));

                btnISearch1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnISearch1.setText("Search");
                btnISearch1.setFocusable(false);
                btnISearch1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnISearch1ActionPerformed(evt);
                        }
                });
                jPanel7.add(btnISearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 340, 110, 30));

                jButton22.setBackground(new java.awt.Color(245, 251, 239));
                jButton22.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
                jButton22.setForeground(new java.awt.Color(116, 139, 117));
                jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cos101/Assets/refresh.png"))); // NOI18N
                jButton22.setText("Refresh");
                jButton22.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton22ActionPerformed(evt);
                        }
                });
                jPanel7.add(jButton22, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 300, 120, -1));

                jTabbedPane1.addTab("Other Tables", jPanel7);

                jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 970, 430));

                jLabel5.setFont(new java.awt.Font("Sylfaen", 3, 18)); // NOI18N
                jLabel5.setForeground(new java.awt.Color(116, 139, 117));
                jLabel5.setText("Home Decorations");
                jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 43, 160, 20));

                jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
                jLabel6.setForeground(new java.awt.Color(116, 139, 117));
                jLabel6.setText("Smart Home");
                jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 220, 30));

                getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 510));

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

        private void txtCIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCIDActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtCIDActionPerformed

        private void txtCNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCNameActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtCNameActionPerformed

        private void txtCAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCAddressActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtCAddressActionPerformed

        private void txtCSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCSearchActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtCSearchActionPerformed

        private void txtCTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCTelephoneActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtCTelephoneActionPerformed

        private void txtCGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCGenderActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtCGenderActionPerformed

        private void txtCAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCAgeActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtCAgeActionPerformed

        private void txtCEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCEmailActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtCEmailActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		if (txtCSearch.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please input id or name");
			return;
		}
		try {
			String request = txtCSearch.getText().toUpperCase();

			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) clientTable.getModel();

			String searchFormat = cboCSearch.getSelectedItem().toString();
			String sql = sql = "select * from client_table where client_ID= '" + request + "' ";
			if (searchFormat.equals("Name")) {
				sql = "select * from client_table where client_Name like '" + request + "%' ";
			}
			System.out.println(sql);
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			int columnCount = rs.getMetaData().getColumnCount();
			cellCenter(clientTable, SwingConstants.CENTER);
			model.setRowCount(0);

			while (rs.next()) {
				Object[] data = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					data[i - 1] = rs.getObject(i);
				}
				model.addRow(data);
			}
		} catch (java.sql.SQLException err) {
			System.out.println(err);
		}
        }//GEN-LAST:event_jButton2ActionPerformed

        private void clientTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientTableMouseClicked
		DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
		int rowIndex = clientTable.getSelectedRow();
		try {
			txtCID.setText(clientTable.getValueAt(rowIndex, 0).toString());
			txtCName.setText(clientTable.getValueAt(rowIndex, 1).toString());
			txtCAddress.setText(clientTable.getValueAt(rowIndex, 2).toString());
			txtCTelephone.setText(clientTable.getValueAt(rowIndex, 3).toString());
			txtCEmail.setText(clientTable.getValueAt(rowIndex, 4).toString());
			txtCGender.setText(clientTable.getValueAt(rowIndex, 5).toString());
			txtCAge.setText(clientTable.getValueAt(rowIndex, 6).toString());
		} catch (NullPointerException err) {
			System.out.println(err);
		} catch (Exception err) {
			System.out.println(err);
		}

        }//GEN-LAST:event_clientTableMouseClicked

        private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
		insertData(txtCID.getText(), txtCName.getText(),
			txtCAddress.getText(), txtCTelephone.getText(),
			txtCEmail.getText(), txtCGender.getText(), txtCAge.getText());
        }//GEN-LAST:event_jButton4ActionPerformed

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		deleteData();
        }//GEN-LAST:event_jButton1ActionPerformed

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
		updateData(txtCID.getText(), txtCName.getText(),
			txtCAddress.getText(), txtCTelephone.getText(),
			txtCEmail.getText(), txtCGender.getText(), txtCAge.getText());
        }//GEN-LAST:event_jButton3ActionPerformed

        private void txtTColNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTColNameActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtTColNameActionPerformed

        private void txtTTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTTypeActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtTTypeActionPerformed

        private void txtTNullActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTNullActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtTNullActionPerformed

        private void txtTPrimaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTPrimaryActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtTPrimaryActionPerformed

        private void txtTNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTNameActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtTNameActionPerformed

        private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
		DefaultTableModel model = (DefaultTableModel) create_table.getModel();
		int rowCount = model.getRowCount();
		createTable(rowCount);
		displayTables();
		cmbColCount.setSelectedIndex(0);
		setRow();
        }//GEN-LAST:event_jButton5ActionPerformed

        private void cmbColCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColCountActionPerformed
		setRow();
        }//GEN-LAST:event_cmbColCountActionPerformed

        private void create_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_create_tableMouseClicked
		DefaultTableModel model = (DefaultTableModel) create_table.getModel();
		int rowIndex = create_table.getSelectedRow();
		try {
			txtTColName.setText(create_table.getValueAt(rowIndex, 1).toString());
			txtTType.setText(create_table.getValueAt(rowIndex, 2).toString());
			txtTPrimary.setText(create_table.getValueAt(rowIndex, 3).toString());
			txtTNull.setText(create_table.getValueAt(rowIndex, 4).toString());
		} catch (NullPointerException err) {
			System.out.println(err);
		} catch (Exception err) {
			System.out.println(err);
		}
        }//GEN-LAST:event_create_tableMouseClicked

        private void cboCSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCSearchActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_cboCSearchActionPerformed

        private void txtMColNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMColNameActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtMColNameActionPerformed

        private void txtMTableNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMTableNameActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtMTableNameActionPerformed

        private void cboTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTablesActionPerformed
		String tableName = cboTables.getSelectedItem().toString();
		descTableData(tableName);
		txtMTableName.setText(tableName);
        }//GEN-LAST:event_cboTablesActionPerformed

        private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
		createTableColumn();
		descTableData(txtMTableName.getText());
        }//GEN-LAST:event_jButton6ActionPerformed

        private void btnDeleteTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTableActionPerformed
		String tableName = txtMTableName.getText();
		deleteTable(tableName);
		displayTables();
        }//GEN-LAST:event_btnDeleteTableActionPerformed

        private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
		deleteTableColumn();
        }//GEN-LAST:event_jButton8ActionPerformed

        private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
		descTableData(txtMTableName.getText());
        }//GEN-LAST:event_jButton9ActionPerformed

        private void confirmTableDeletionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmTableDeletionActionPerformed
		Boolean confirm = confirmTableDeletion.isSelected();
		if (confirm.equals(true)) {
			btnDeleteTable.setEnabled(true);
			return;
		}
		btnDeleteTable.setEnabled(false);
        }//GEN-LAST:event_confirmTableDeletionActionPerformed

        private void modify_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modify_tableMouseClicked

		DefaultTableModel model = (DefaultTableModel) modify_table.getModel();
		int rowIndex = modify_table.getSelectedRow();

		try {
			String checkBoxisNull = model.getValueAt(rowIndex, 2).toString();
			String checkBoxPrimary = model.getValueAt(rowIndex, 3).toString();

			txtMColName.setText(model.getValueAt(rowIndex, 0).toString());
			cboMType.setSelectedItem(model.getValueAt(rowIndex, 1).toString());
			if (checkBoxisNull.equals("YES")) {
				chkMisNull.setSelected(true);
			} else {
				chkMisNull.setSelected(false);
			}
			if (checkBoxPrimary.equals("PRI")) {
				chkMPrimary.setSelected(true);
			} else {
				chkMPrimary.setSelected(false);
			}
		} catch (Exception err) {
			System.err.println(err);
		}
        }//GEN-LAST:event_modify_tableMouseClicked

        private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
		updateTableColumn();
        }//GEN-LAST:event_jButton10ActionPerformed

        private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
		txtMColName.setText("");
		cboMType.setSelectedIndex(0);
		chkMPrimary.setSelected(false);
		chkMisNull.setSelected(false);
        }//GEN-LAST:event_jButton11ActionPerformed

        private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
		displayData("client_table");
        }//GEN-LAST:event_jButton12ActionPerformed

        private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
		txtCID.setText("");
		txtCName.setText("");
		txtCAddress.setText("");
		txtCTelephone.setText("");
		txtCGender.setText("");
		txtCEmail.setText("");
		txtCAge.setText("");
		txtCSearch.setText("");
        }//GEN-LAST:event_jButton13ActionPerformed

        private void itemTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemTableMouseClicked
		DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
		int rowIndex = itemTable.getSelectedRow();
		try {
			txtIID.setText(itemTable.getValueAt(rowIndex, 0).toString());
			txtIName.setText(itemTable.getValueAt(rowIndex, 1).toString());
			txtIType.setText(itemTable.getValueAt(rowIndex, 2).toString());
			txtIQuantity.setText(itemTable.getValueAt(rowIndex, 3).toString());
			txtIPrice.setText(itemTable.getValueAt(rowIndex, 4).toString());
			txtICID.setText(itemTable.getValueAt(rowIndex, 5).toString());
		} catch (NullPointerException err) {
			System.out.println(err);
		} catch (Exception err) {
			System.out.println(err);
		}

        }//GEN-LAST:event_itemTableMouseClicked

        private void txtIIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIIDActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtIIDActionPerformed

        private void txtINameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtINameActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtINameActionPerformed

        private void txtITypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtITypeActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtITypeActionPerformed

        private void cboISearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboISearchActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_cboISearchActionPerformed

        private void txtISearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtISearchActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtISearchActionPerformed

        private void txtIPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIPriceActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtIPriceActionPerformed

        private void txtIQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIQuantityActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtIQuantityActionPerformed

        private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
		deleteDataItem();
        }//GEN-LAST:event_jButton7ActionPerformed

        private void btnISearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnISearchActionPerformed
		if (txtISearch.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please input id or name");
			return;
		}
		try {
			String request = txtISearch.getText().toUpperCase();

			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel) itemTable.getModel();

			String searchFormat = cboISearch.getSelectedItem().toString();
			String sql = sql = "select * from item_table where item_ID= '" + request + "' ";
			if (searchFormat.equals("Name")) {
				sql = "select * from item_table where item_Name like '" + request + "%' ";
			}
			System.out.println(sql);
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			int columnCount = rs.getMetaData().getColumnCount();
			cellCenter(clientTable, SwingConstants.CENTER);
			model.setRowCount(0);

			while (rs.next()) {
				Object[] data = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					data[i - 1] = rs.getObject(i);
				}
				model.addRow(data);
			}
		} catch (java.sql.SQLException err) {
			System.out.println(err);
		}
        }//GEN-LAST:event_btnISearchActionPerformed

        private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
		updateDataItem();
        }//GEN-LAST:event_jButton15ActionPerformed

        private void txtICIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtICIDActionPerformed
		// TODO add your handling code here:
        }//GEN-LAST:event_txtICIDActionPerformed

        private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
		insertDataItem(
			txtIID.getText(),
			txtIName.getText(),
			txtIType.getText(),
			txtIQuantity.getText(),
			txtIPrice.getText(),
			txtICID.getText()
		);
        }//GEN-LAST:event_jButton16ActionPerformed

        private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
		displayData("item_table");
        }//GEN-LAST:event_jButton17ActionPerformed

        private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
		txtIID.setText("");
		txtIName.setText("");
		txtIType.setText("");
		txtIQuantity.setText("");
		txtIPrice.setText("");
		txtICID.setText("");
		txtISearch.setText("");
        }//GEN-LAST:event_jButton18ActionPerformed

        private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
		Login_form login = new Login_form();
		login.setVisible(true);
		this.dispose();
		
        }//GEN-LAST:event_jButton14ActionPerformed

        private void other_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_other_tableMouseClicked
                // TODO add your handling code here:
        }//GEN-LAST:event_other_tableMouseClicked

        private void cboOtherTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOtherTablesActionPerformed
                String comboName = cboOtherTables.getSelectedItem().toString();
		displayOtherData(comboName);
        }//GEN-LAST:event_cboOtherTablesActionPerformed

        private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
                insertDataOther();
        }//GEN-LAST:event_jButton19ActionPerformed

        private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
		deleteDataOther();
        }//GEN-LAST:event_jButton21ActionPerformed

        private void txtOSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSearchActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_txtOSearchActionPerformed

        private void btnISearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnISearch1ActionPerformed
		if (txtOSearch.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Please input id or name");
			return;
		}
		try {
			String request = txtOSearch.getText().toUpperCase();		
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			DefaultTableModel model = (DefaultTableModel)other_table.getModel();
			
			String tableN = cboOtherTables.getSelectedItem().toString();
			String[] columnN = new String[2];
			columnN = model.getColumnName(0).split(" ");
			String sql = "select * from "+tableN+" where "+columnN[0]+" LIKE '" + request + "%' ";
		
			System.out.println(sql);
			con = DriverManager.getConnection(url, username, passwor);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			int columnCount = rs.getMetaData().getColumnCount();
			cellCenter(clientTable, SwingConstants.CENTER);
			model.setRowCount(0);

			while (rs.next()) {
				Object[] data = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					data[i - 1] = rs.getObject(i);
				}
				model.addRow(data);
			}
			String comboName = cboOtherTables.getSelectedItem().toString();
			displayOtherData(comboName);
		} catch (java.sql.SQLException err) {
			System.out.println(err);
		}
        }//GEN-LAST:event_btnISearch1ActionPerformed

        private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
		String comboName = cboOtherTables.getSelectedItem().toString();
		displayOtherData(comboName);
        }//GEN-LAST:event_jButton22ActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		IntelliJTheme.setup(Login_form.class.getResourceAsStream("solarizedLight.json"));
		UIManager.put("Button.arc", 10);
		UIManager.put("TextComponent.arc", 10);
		UIManager.put("Button.font", "h1.regular.font");

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main_form().setVisible(true);
			}
		});
	}

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnDeleteTable;
        private javax.swing.JButton btnISearch;
        private javax.swing.JButton btnISearch1;
        private javax.swing.JComboBox<String> cboCSearch;
        private javax.swing.JComboBox<String> cboISearch;
        private javax.swing.JComboBox<String> cboMType;
        private javax.swing.JComboBox<String> cboOtherTables;
        private javax.swing.JComboBox<String> cboTables;
        private javax.swing.JCheckBox chkMPrimary;
        private javax.swing.JCheckBox chkMisNull;
        private javax.swing.JTable clientTable;
        private javax.swing.JComboBox<String> cmbColCount;
        private javax.swing.JCheckBox confirmTableDeletion;
        private javax.swing.JTable create_table;
        private javax.swing.JTable itemTable;
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton10;
        private javax.swing.JButton jButton11;
        private javax.swing.JButton jButton12;
        private javax.swing.JButton jButton13;
        private javax.swing.JButton jButton14;
        private javax.swing.JButton jButton15;
        private javax.swing.JButton jButton16;
        private javax.swing.JButton jButton17;
        private javax.swing.JButton jButton18;
        private javax.swing.JButton jButton19;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton21;
        private javax.swing.JButton jButton22;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
        private javax.swing.JButton jButton5;
        private javax.swing.JButton jButton6;
        private javax.swing.JButton jButton7;
        private javax.swing.JButton jButton8;
        private javax.swing.JButton jButton9;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel16;
        private javax.swing.JLabel jLabel17;
        private javax.swing.JLabel jLabel18;
        private javax.swing.JLabel jLabel19;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel20;
        private javax.swing.JLabel jLabel21;
        private javax.swing.JLabel jLabel22;
        private javax.swing.JLabel jLabel23;
        private javax.swing.JLabel jLabel24;
        private javax.swing.JLabel jLabel25;
        private javax.swing.JLabel jLabel26;
        private javax.swing.JLabel jLabel27;
        private javax.swing.JLabel jLabel28;
        private javax.swing.JLabel jLabel29;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel32;
        private javax.swing.JLabel jLabel33;
        private javax.swing.JLabel jLabel34;
        private javax.swing.JLabel jLabel35;
        private javax.swing.JLabel jLabel36;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JMenuItem jMenuItem1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JPanel jPanel8;
        private javax.swing.JPanel jPanel9;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JScrollPane jScrollPane5;
        private javax.swing.JTabbedPane jTabbedPane1;
        private javax.swing.JTable modify_table;
        private javax.swing.JTable other_table;
        private javax.swing.JLabel picFrame;
        private javax.swing.JTextField txtCAddress;
        private javax.swing.JTextField txtCAge;
        private javax.swing.JTextField txtCEmail;
        private javax.swing.JTextField txtCGender;
        private javax.swing.JTextField txtCID;
        private javax.swing.JTextField txtCName;
        private javax.swing.JTextField txtCSearch;
        private javax.swing.JTextField txtCTelephone;
        private javax.swing.JTextField txtDBName;
        private javax.swing.JTextField txtICID;
        private javax.swing.JTextField txtIID;
        private javax.swing.JTextField txtIName;
        private javax.swing.JTextField txtIPrice;
        private javax.swing.JTextField txtIQuantity;
        private javax.swing.JTextField txtISearch;
        private javax.swing.JTextField txtIType;
        private javax.swing.JTextField txtMColName;
        private javax.swing.JTextField txtMTableName;
        private javax.swing.JTextField txtOSearch;
        private javax.swing.JTextField txtTColName;
        private javax.swing.JTextField txtTName;
        private javax.swing.JTextField txtTNull;
        private javax.swing.JTextField txtTPrimary;
        private javax.swing.JTextField txtTType;
        private javax.swing.JTextField txtTableCount;
        private javax.swing.JTextField txtUserID;
        private javax.swing.JTextField txtUserPassword;
        // End of variables declaration//GEN-END:variables
}
