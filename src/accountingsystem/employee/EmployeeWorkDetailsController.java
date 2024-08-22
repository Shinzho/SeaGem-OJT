/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.employee;

import accountingsystem.connections.EmployeeData;
import accountingsystem.connections.connectDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * FXML Controller class
 *
 * @author User
 */
public class EmployeeWorkDetailsController implements Initializable {

    @FXML
    private TextField empFname;
    @FXML
    private TextField empLname;
    @FXML
    private MenuButton empMonth;
    @FXML
    private DatePicker empStartDate;
    @FXML
    private DatePicker empEndDate;
    @FXML
    private Label empId;
    @FXML
    private TextField empAllowances;
    @FXML
    private TextField empAbsent;
    @FXML
    private TextField empLate;
    
    private EmployeeData employeeData;
    private EmployeeDataListener dataListener;
    @FXML
    private TextField empOvertime;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuButton();
    }    
    
    public void setEmployeeData(EmployeeData data) {
        this.employeeData = data;
        
        if (data != null) {
            empFname.setText(data.getFname());
            empLname.setText(data.getLname());
            empId.setText(String.valueOf(data.getId()));
            
           
        }
    }
    
  private void menuButton() {
   
    empMonth.getItems().clear();

    MenuItem jan = new MenuItem("January");
    MenuItem feb = new MenuItem("February");
    MenuItem mar = new MenuItem("March");
    MenuItem apr = new MenuItem("April");
    MenuItem may = new MenuItem("May");
    MenuItem jun = new MenuItem("June");
    MenuItem jul = new MenuItem("July");
    MenuItem aug = new MenuItem("August");
    MenuItem sep = new MenuItem("September");
    MenuItem oct = new MenuItem("October");
    MenuItem nov = new MenuItem("November");
    MenuItem dec = new MenuItem("December");

 
    empMonth.getItems().addAll(jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec);

  
    jan.setOnAction(e -> empMonth.setText("January"));
    feb.setOnAction(e -> empMonth.setText("February"));
    mar.setOnAction(e -> empMonth.setText("March"));
    apr.setOnAction(e -> empMonth.setText("April"));
    may.setOnAction(e -> empMonth.setText("May"));
    jun.setOnAction(e -> empMonth.setText("June"));
    jul.setOnAction(e -> empMonth.setText("July"));
    aug.setOnAction(e -> empMonth.setText("August"));
    sep.setOnAction(e -> empMonth.setText("September"));
    oct.setOnAction(e -> empMonth.setText("October"));
    nov.setOnAction(e -> empMonth.setText("November"));
    dec.setOnAction(e -> empMonth.setText("December"));
}


    @FXML
private void saveWorkDetails(ActionEvent event) {

    String empIdText = empId.getText();
    String startDateText = empStartDate.getValue() != null ? empStartDate.getValue().toString() : null;
    String endDateText = empEndDate.getValue() != null ? empEndDate.getValue().toString() : null;
    String absencesText = empAbsent.getText();
    String allowancesText = empAllowances.getText();
    String latesText = empLate.getText();
    String overtimeText = empOvertime.getText();
    String month = empMonth.getText();
 
    if (empIdText == null || empIdText.isEmpty() || startDateText == null || endDateText == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all required fields.");
        alert.showAndWait();
        return;
    }

   
    String sql = "INSERT INTO workingdetails (empId, empMonth, startDate, endDate, absences, allowances, overtime, lates) VALUES (?,?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = connectDB.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, Integer.parseInt(empIdText));
        pst.setString(2, month);
        pst.setDate(3, java.sql.Date.valueOf(startDateText));
        pst.setDate(4, java.sql.Date.valueOf(endDateText));
        pst.setInt(5, Integer.parseInt(absencesText));
        pst.setInt(6, Integer.parseInt(allowancesText));
        pst.setInt(7, Integer.parseInt(overtimeText)); 
        pst.setInt(8, Integer.parseInt(latesText));
        

       
        pst.executeUpdate();

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Work details saved successfully.");
        successAlert.showAndWait();

        clearFields();

    } catch (SQLException e) {
        e.printStackTrace();
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("An error occurred while saving the details.");
        errorAlert.setContentText(e.getMessage());
        errorAlert.showAndWait();
    }
}

private void clearFields() {
    empStartDate.setValue(null);
    empEndDate.setValue(null);
    empAbsent.clear();
    empAllowances.clear();
    empLate.clear();
    empMonth.setText("Select Month");
    empOvertime.clear();
}


   public void setEmployeeDataListener(EmployeeDataListener listener) {
        this.dataListener = listener;
    }
   
   
      
    
}
