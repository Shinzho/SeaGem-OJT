/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.dashboard;

import accountingsystem.inventory.AddStockController;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DashboardController implements Initializable {

    @FXML
    private Button dashboadBtn;
    @FXML
    private Button ledgerBtn;
    @FXML
    private Button payableBtn;

    @FXML
    private Button inventoryBtn;
    @FXML
    private Button payrollBtn;
    @FXML
    private Button reportsBtn;
    @FXML
    private Button expensesBtn;
    @FXML
    private AnchorPane featurePane;
    @FXML
    private Button employeeBtn;
    @FXML
    private Label systemDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFXML("/accountingsystem/ledger/GeneralLedger.fxml");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy"); // Example format
        LocalDate today = LocalDate.now();
        systemDate.setText(today.format(formatter));
        payableBtn.setOnAction(e -> loadFXML("/accountingsystem/accounts/Accounts.fxml"));
        ledgerBtn.setOnAction(e -> loadFXML("/accountingsystem/ledger/GeneralLedger.fxml"));
        inventoryBtn.setOnAction(e -> loadFXML("/accountingsystem/inventory/Inventory.fxml"));
        payrollBtn.setOnAction(e -> loadFXML("/accountingsystem/payroll/Payroll.fxml"));
        reportsBtn.setOnAction(e -> loadFXML("/accountingsystem/reporting/Reports.fxml"));
        expensesBtn.setOnAction(e -> loadFXML("/accountingsystem/tracking/ExpenseTracking.fxml"));
        employeeBtn.setOnAction(e -> loadFXML("/accountingsystem/employee/EmployeeManagement.fxml"));
    }    
    
    private void loadFXML(String fxmlFile) {
        try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane pane = loader.load();
            
            featurePane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }

    @FXML
private void logOut(ActionEvent event) {
    // Create a confirmation alert
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Logout Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to log out?");

    // Add buttons to the alert
    ButtonType yesButton = new ButtonType("Yes");
    ButtonType noButton = new ButtonType("No");
    alert.getButtonTypes().setAll(yesButton, noButton);

    Optional<ButtonType> result = alert.showAndWait();
 
    if (result.isPresent() && result.get() == yesButton) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/accountingsystem/login/login.fxml"));
        Parent newRoot = loader.load();


        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Login");
        newStage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}

    
}
