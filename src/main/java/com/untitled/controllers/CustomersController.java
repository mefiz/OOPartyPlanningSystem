package main.java.com.untitled.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import main.java.com.untitled.Customer;
import main.java.com.untitled.dao.CustomerDAO;

public class CustomersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane customersPane;

    @FXML
    private TableView customersTable;

    @FXML
    private TableColumn<CustomerDAO, String> customerIDColumn;

    @FXML
    private TableColumn<CustomerDAO, String> customerNameColumn;

    @FXML
    private TableColumn<CustomerDAO, Integer> customerBankColumn;

    @FXML
    private TableColumn<CustomerDAO, Integer> customerContactNumColumn;

    @FXML
    private TableColumn<CustomerDAO, String> customerEmailIDColumn;

    @FXML
    private JFXTextField addCustomerID;

    @FXML
    private JFXTextField addCustomerContactNum;

    @FXML
    private JFXTextField addCustomerBankNum;

    @FXML
    private JFXTextField addCustomerName;

    @FXML
    private JFXTextField addCustomerEmail;

    @FXML
    private JFXButton addCustomerButton;

    @FXML
    private JFXButton modifyCustomerButton;

    @FXML
    private JFXTextField modifyCustomerContactNum;

    @FXML
    private JFXTextField modifyCustomerBankAccount;

    @FXML
    private JFXTextField modifyCustomerName;

    @FXML
    private JFXTextField modifyCustomerEmail;

    @FXML
    private JFXComboBox<String> modifyCustomerIDSelect;

    @FXML
    private JFXButton removeCustomer;

    @FXML
    private JFXComboBox<String> removeCustomerIDSelect;

    @FXML
    void initialize() {
        //intialize the tableview and the combo boxes
        updateTableView();
        updateIDComboBoxes();
        
        /*
        listeners and regex patterns to make the number fields formatted
        */
        addCustomerContactNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?")) {
                    Platform.runLater(() -> {
                        addCustomerContactNum.setText("");
                    });
                }
            }
            
        });//end addCustomerContactNum.textProperty().addListener;
        
        modifyCustomerContactNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,7}?")) {
                    Platform.runLater(() -> {
                        modifyCustomerContactNum.setText("");
                    });
                }
            }
            
        });//end modifyCustomerContactNum.textProperty().addListener;
        
        addCustomerBankNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,13}?")) {
                    Platform.runLater(() -> {
                        addCustomerBankNum.setText("");
                    });
                }
            }
            
        });//end addCustomerBankNum.textProperty().addListener;
        
        modifyCustomerBankAccount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d{0,13}?")) {
                    Platform.runLater(() -> {
                        modifyCustomerBankAccount.setText("");
                    });
                }
            }
            
        });//end modifyCustomerBankAccount.textProperty().addListener;
        
        /* end regex patterns */
        
        /*
        When a value is selected from the id drop down box, set the text fields to the data of that id
        */
        modifyCustomerIDSelect.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            //create a new Customer Data access object
            CustomerDAO customerDAO = new CustomerDAO();
            
            try {
                //store the data in a hashmap
                HashMap<String, String> data = customerDAO.getCustomerDataBasedOnID(newValue);
                
                //update the text fields
                modifyCustomerName.setText(data.get("name"));
                modifyCustomerBankAccount.setText(data.get("bankAccountNum"));
                modifyCustomerContactNum.setText(data.get("contactNum"));
                modifyCustomerEmail.setText(data.get("email"));
                
            } catch (SQLException ex) {
                Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); //end modifyCustomerIDSelect.getSelectionModel().selectedItemProperty().addListener
    }
    
    //update the customers table
    public void updateTableView(){
        //create the list to hold the data in
        ObservableList<CustomerDAO> customersList = FXCollections.observableArrayList();
        
        //set the columns
        customerIDColumn.setCellValueFactory(cellData -> cellData.getValue().getIdNum());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        customerBankColumn.setCellValueFactory(cellData -> cellData.getValue().getBankAccountNum().asObject());
        customerContactNumColumn.setCellValueFactory(cellData -> cellData.getValue().getContactNum().asObject());
        customerEmailIDColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailAddress());
        
        //create a new database access object
        CustomerDAO customers = new CustomerDAO();
        
        try {
            //get the data from the database
            customersList = customers.getCustomerRecords();
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        customersTable.setItems(customersList);
    }//end updateTableView(){}
    
    //add data to the id combo boxes
    public void updateIDComboBoxes(){
        //create a new customerDAO
        CustomerDAO customerDAO = new CustomerDAO();
        
        try {
            ObservableList customerNames = customerDAO.getListOfAllCustomerIDs();
            modifyCustomerIDSelect.setItems(customerNames);
            removeCustomerIDSelect.setItems(customerNames);
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end updateIDComboBoxes(){}
    
    //add the new customer to the database
    public void addCustomerToDatabase(){
        //get the data
        String customerID  = addCustomerID.getText();
        String customerName = addCustomerName.getText();
        int customerBankNum = Integer.parseInt(addCustomerBankNum.getText());
        int customerContactNumber = Integer.parseInt(addCustomerContactNum.getText());
        String customerEmail = addCustomerEmail.getText();
        
        //insert to a customer object
        Customer customer = new Customer(customerID, customerName, customerBankNum,
                                         customerContactNumber, customerEmail);
        //create a new customer data access object
        CustomerDAO customerDAO = new CustomerDAO(customer);
        //insert the data into the table
        try {
            customerDAO.insertIntoTable();
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table views
        updateTableView();
        updateIDComboBoxes();
        
        //reset the text fields
        addCustomerID.setText("");
        addCustomerName.setText("");
        addCustomerEmail.setText("");
        addCustomerBankNum.setText("");
        addCustomerContactNum.setText("");
    }//end addCustomerToDatabase()
    
    //modify an existing customer
    public void modifyCustomerInDatabase(){
        //get the data
        String customerID  = modifyCustomerIDSelect.getValue();
        String customerName = modifyCustomerName.getText();
        int customerBankNum = Integer.parseInt(modifyCustomerBankAccount.getText());
        int customerContactNumber = Integer.parseInt(modifyCustomerContactNum.getText());
        String customerEmail = modifyCustomerEmail.getText();
        
        //insert the data to a customer object
        Customer customer = new Customer(customerID, customerName, customerBankNum,
                                         customerContactNumber, customerEmail);
        
        //create a new customer data access object
        CustomerDAO customerDAO = new CustomerDAO(customer);
        //insert the data into the table
        try {
            customerDAO.updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table views
        updateTableView();
        updateIDComboBoxes();
        
        //reset the text fields
        modifyCustomerName.setText("");
        modifyCustomerEmail.setText("");
        modifyCustomerBankAccount.setText("");
        modifyCustomerContactNum.setText("");
    }//end modifyCustomerInDatabase(){}
    
    //delete a customer from the database
    public void deleteCustomerInDatabase(){
        //get the data
        String customerID  = removeCustomer.getText();
        
        //pass the data to a customer object
        Customer customer = new Customer(customerID);
        
        //create a new customer data access object
        CustomerDAO customerDAO = new CustomerDAO(customer);
        //dele the data in the table
        try {
            customerDAO.deleteFromTable();
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //update the table views
        updateTableView();
        updateIDComboBoxes();
    }//end deleteCustomerInDatabase(){}
    
}
