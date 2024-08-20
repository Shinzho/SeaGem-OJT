/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.employee;



import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import accountingsystem.connections.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AddEmployeeController implements Initializable {

    @FXML
    private TextField addLname;
    @FXML
    private TextField addFname;
    @FXML
    private TextField addEmail;
    @FXML
    private TextField AddPhone;
    @FXML
    private TextArea addAddress;
    @FXML
    private DatePicker addDob;
    @FXML
    private TextField addDept;
    @FXML
    private TextField addDesignation;
    @FXML
    private TextField addSalary;
    @FXML
    private DatePicker addDateHired;
    @FXML
    private MenuButton addStatus;
    @FXML
    private MenuButton addRole;

    private String selectedStatus;
    private String selectedRole;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuButton();
    }    
    
    private void menuButton(){
         MenuItem statusActive = new MenuItem("Active");
        MenuItem statusInactive = new MenuItem("Inactive");
        MenuItem statusOnLeave = new MenuItem("On Leave");
        addStatus.getItems().addAll(statusActive, statusInactive, statusOnLeave);

        MenuItem roleAdmin = new MenuItem("Admin");
        MenuItem roleEmployee = new MenuItem("Employee");
        addRole.getItems().addAll(roleAdmin, roleEmployee);
        
        statusActive.setOnAction(e -> selectedStatus = "Active");
        statusInactive.setOnAction(e -> selectedStatus = "Inactive");
        statusOnLeave.setOnAction(e -> selectedStatus = "On Leave");

        roleAdmin.setOnAction(e -> selectedRole = "Admin");
        roleEmployee.setOnAction(e -> selectedRole = "Employee");       
    }

    @FXML
    private void addEmployeeBtn(ActionEvent event) {
    
    String fname = addFname.getText();
    String lname = addLname.getText();
    String email = addEmail.getText();
    String phone = AddPhone.getText();
    String address = addAddress.getText();
    LocalDate dob = addDob.getValue();
    String department = addDept.getText();
    String designation = addDesignation.getText();
    Double salary = Double.parseDouble(addSalary.getText());
    LocalDate hireDate = addDateHired.getValue();
    String status = selectedStatus;
    String role = selectedRole;

    try (Connection conn = connectDB.getConnection()) {
        String sql = "INSERT INTO employees (id, fname, lname, email, salary, department, designation, hire_date, address, phone, dob, status, role) " +
                     "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);

    
        pstmt.setString(1, fname);
        pstmt.setString(2, lname);
        pstmt.setString(3, email);
        pstmt.setDouble(4, salary);
        pstmt.setString(5, department);
        pstmt.setString(6, designation);
        pstmt.setDate(7, hireDate != null ? java.sql.Date.valueOf(hireDate) : null);
        pstmt.setString(8, address);
        pstmt.setString(9, phone);
        pstmt.setDate(10, dob != null ? java.sql.Date.valueOf(dob) : null);
        pstmt.setString(11, status);
        pstmt.setString(12, role);

        pstmt.executeUpdate();
        System.out.println("Employee data saved successfully!");

    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error saving employee data.");
    }
}

    
   
    
}
