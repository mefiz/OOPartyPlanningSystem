/*
a data access object template
*/
package main.java.com.untitled.dao;

import java.sql.SQLException;

public interface DAO {
    public final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver"; //specifies the driver
    public final String JDBC_URL = "jdbc:derby:Database;create=true"; //specifies the database URL
    
    
    public void insertIntoTable() throws SQLException; //add to the database
    public void deleteFromTable() throws SQLException; //remove from the database
    public void updateTable() throws SQLException; //update the database
    
}
