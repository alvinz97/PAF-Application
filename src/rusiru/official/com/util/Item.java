package rusiru.official.com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

/**
* ${rusiru.official.com.util.Item}
* @ClassName:  ${Item}
* @Description: (Here is one sentence to describe the role of this class)
* @author: Rusiru Kulathunga
* @date:   05-03-2021
*/


public class Item {
	public Connection connect() {
		Connection connection = null;
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306";
		String database = "application";
		String user = "root";
		String password = "issa123";
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url+"/"+database, user, password);
			
			System.out.println("Successfully Connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}

	//Insert Item method
	public String insertItem(String code, String name, String price, String description)
	{
		String output = "";
		try {
			Connection connection = connect();
			if (connection == null) {
				return "Error while connecting to the database";
			}

			String query = "INSERT INTO items(`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)" + " VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = connection.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, description); 

			preparedStmt.execute();
			connection.close();
			output = "Successfully Inserted";
		} catch (Exception e) {
			output = "Error While Inserting";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//Read Items method
	public String readItems() {
	String output = "";
		try {
			Connection connection = connect();
			if (connection == null)
			{
				return "Error while connecting to the database.";
			}
			output = "<table class='table table-bordered' border='1'><tr><th>Item Code</th>"
			+"<th>Item Name</th><th>Item Price</th>"
			+ "<th>Item Description</th>"
			+ "<th>Update</th><th>Remove</th></tr>";
			
			String query = "SELECT * FROM items";
			Statement stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);

			while (resultSet.next()) {
				String itemID = Integer.toString(resultSet.getInt("itemID"));
				String itemCode = resultSet.getString("itemCode");
				String itemName = resultSet.getString("itemName");
				String itemPrice = Double.toString(resultSet.getDouble("itemPrice"));
				String itemDesc = resultSet.getString("itemDesc");
				
				output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>"; 
				output += "<td>" + itemDesc + "</td>";

				output += "<td><input name='btnUpdate' "
				+ " type='button' value='Update' class='btn btn-sm btn-success'></td>"
				+ "<td><form method='post' action='items.jsp'>"
				+ "<input name='btnRemove' "
				+ " type='submit' value='Remove' class='btn btn-sm btn-danger'>"
				+ "<input name='itemID' type='hidden' "
				+ " value='" + itemID + "'>" + "</form></td></tr>";
			}
			connection.close();
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//Delete Items
	public String deleteItem(int itemID) {
        try {
			Connection con = connect();
			PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM items WHERE itemID=?");
			
			preparedStatement.setInt(1, itemID);
			preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return null;
    }
}
