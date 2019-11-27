package userinterface.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton logOutButton;

    @FXML
    private Label currentUserName;

    @FXML
    private JFXButton goToSalesButton;

    @FXML
    private JFXButton goToPartiesButton;

    @FXML
    private JFXButton goToVenuesButton;

    @FXML
    private JFXButton goToAddOnsButton;

    @FXML
    private JFXButton goToCustomersButton;

    @FXML
    private Label settingsLabel;

    @FXML
    private JFXButton goToUsersButton;

    @FXML
    void initialize() {
        
    }
    
    public void initHome(final UIManager uiManager, String currentUsername){
        currentUserName.setText(currentUsername);
        
    }
    
    public void setCurrentUsername(String currentUsername){
        currentUserName.setText(currentUsername);
    }
}