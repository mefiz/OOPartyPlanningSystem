/*
    Defines a "data access object" for the add to parties class
 */
package main.java.com.untitled.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.java.com.untitled.Party;

public class PartyDAO implements DAO{
    
    //variable declarations
    private String type;
    private int price;
    private String taskOne;
    private String taskTwo;
    private String taskThree;
    private String taskFour;
    
    //constructor
    public PartyDAO(Party addParty){
        this.type = addParty.getType();
        this.price = addParty.getPrice();
        this.taskOne = addParty.getTaskOne();
        this.taskTwo = addParty.getTaskTwo();
        this.taskThree = addParty.getTaskThree();
        this.taskFour = addParty.getTaskFour();
    }
    
    @Override
    public void insertIntoTable() throws SQLException {
        //create a connection object
        Connection connection = DriverManager.getConnection(JDBC_URL);
        
        //define the sql statement
        String insertIntoPartiesTable = "INSERT INTO parties "
                                         + "(Type, Price, TaskOne, TaskTwo, TaskThree, TaskFour) "
                                         + "VALUES "
                                         + "('" + this.type + "', "
                                         + this.price +", "
                                         + "'" + this.taskOne + ", "
                                         + "'" + this.taskTwo + ", "
                                         + "'" + this.taskThree + ", "
                                         + "'" + this.taskFour +")";
        
       //prepare the sql statement
       PreparedStatement ps = connection.prepareStatement(insertIntoPartiesTable);
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
        String deleteFromPartiesTable = "DELETE FROM parties WHERE Type = '" + this.type + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(deleteFromPartiesTable);
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
        String updatePartiesTable = "UPDATE parties SET "
                                  + "Price = " + this.price + ", "
                                  + "TaskOne = '" + this.taskOne + ", "
                                  + "TaskTwo = '" + this.taskTwo + ", "
                                  + "TaskThree = '" + this.taskThree + ", "
                                  + "TaskFour = '" + this.taskFour + " "
                                  + "WHERE Type = '" + this.type + "'";
        
        //prepare the statement
        PreparedStatement ps = connection.prepareStatement(updatePartiesTable);
        //execute statement
        ps.execute();
        
        //close the statement and the connection
        ps.close();
        connection.close();
    }
    
}
