/*
    Define a "Data Access Object" for the addons class
 */
package main.java.com.untitled.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.untitled.Addon;
import static main.java.com.untitled.dao.DAO.JDBC_URL;

public class AddonsDAO implements DAO{
    //variable declarations
    private IntegerProperty rowNum; //used only in tableview generation
    private StringProperty addonType;
    private IntegerProperty addonPrice;
    
    //getters and setters
    //row num
    public IntegerProperty getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = new SimpleIntegerProperty(rowNum);
    }
    
    //type
    public StringProperty getAddonType() {
        return addonType;
    }
    public void setAddonType(String addonType) {
        this.addonType = new SimpleStringProperty(addonType);
    }
    
    //price
    public IntegerProperty getAddonPrice() {
        return addonPrice;
    }
    public void setAddonPrice(int addonPrice) {
        this.addonPrice = new SimpleIntegerProperty(addonPrice);
    }
    //end getters and setters
    
    //constructor
    public AddonsDAO(Addon addons){
        this.addonType = new SimpleStringProperty(addons.getAddonType());
        this.addonPrice = new SimpleIntegerProperty(addons.getAddonPrice());
    }
    
    public AddonsDAO(){
        //empty
    }
    
    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoAddonsTable = "INSERT INTO addons "
                                     + "(Type, Price) "
                                     + "VALUES "
                                     + "('" + this.addonType.get() + "', "
                                     + this.addonPrice.get() + ")";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(insertIntoAddonsTable);
        //execute statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }

    @Override
    public void deleteFromTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String deleteFromAddonsTable = "DELETE FROM addons WHERE Type = '" + this.addonType.get() + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromAddonsTable);
        //execute statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }

    @Override
    public void updateTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String updateAddonsTable = "UPDATE addons SET "
                            + "Price = " + this.addonPrice.get() + " "
                            + "WHERE Type = '" + this.addonType.get() + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updateAddonsTable);
        //execute the statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
    //get the data for table view generation
    public ObservableList<AddonsDAO> getAddonsRecords() throws SQLException{
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the statement
        String selectAddonsRecords = "SELECT * FROM addons";
        //prepare the statment
        PreparedStatement ps = connection.prepareStatement(selectAddonsRecords);
        
        ResultSet rs = ps.executeQuery(); //get the result set
        
        ObservableList<AddonsDAO> addonsList = getAddonsObjects(rs);  //get the user objects
        
        return addonsList;
    }// public ObservableList<AddonsDAO> getAddonsRecords()
    
    //get the add on objects from the database
    private ObservableList<AddonsDAO> getAddonsObjects(ResultSet rs) throws SQLException{
        ObservableList<AddonsDAO> addonsList = FXCollections.observableArrayList();
        
        int i = 1; //this is a counter to set the rows in the table view
        while(rs.next()){
            AddonsDAO addonsDAO = new AddonsDAO();
            addonsDAO.setRowNum(i);
            addonsDAO.setAddonType(rs.getString("Type"));
            addonsDAO.setAddonPrice(rs.getInt("Price"));
            
            addonsList.add(addonsDAO);
            i++;
        }
        
        return addonsList;
        
    }//end Public  ObservableList<AddonsDAO> getAddonsObjects(ResultSet rs)
    
    //get a list of all the addon types
    public ObservableList<String> getAllAddonsTypes() throws SQLException{
        //create the conncetion object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement("SELECT Type FROM addons");
        
        //get the result set
        ResultSet rs = ps.executeQuery();
        
        //add the results to an observable list
        ObservableList<String> typesList = FXCollections.observableArrayList();
        
        while(rs.next()){
            typesList.add(rs.getString("Type"));
        }
        
        return typesList;
    }//end getAllAddonsTypes
}
