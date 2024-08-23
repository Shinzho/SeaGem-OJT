/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.employee;



import accountingsystem.connections.EmployeeData;
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
import javafx.scene.Node;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

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
     private EmployeeDataListener dataListener;
    private EmployeeData employeeData;
    @FXML
    private Button addButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuButton();
    }    
    
    public void setEmployeeData(EmployeeData data) {
    this.employeeData = data;

    addFname.setText(data.getFname());
    addLname.setText(data.getLname());
    addEmail.setText(data.getEmail());
    AddPhone.setText(data.getPhone());
    addAddress.setText(data.getAddress());

    String dobString = convertDateToString((Date) data.getDob());
    String hireDateString = convertDateToString((Date) data.getHireDate());

    if (dobString != null) {
        LocalDate dobLocalDate = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        addDob.setValue(dobLocalDate);
    }
    if (hireDateString != null) {
        LocalDate hireDateLocalDate = LocalDate.parse(hireDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        addDateHired.setValue(hireDateLocalDate);
    }

    addDept.setText(data.getDepartment());
    addDesignation.setText(data.getDesignation());
    addSalary.setText(String.valueOf(data.getSalary()));
    addStatus.setText(data.getStatus());
    addRole.setText(data.getRole());
    
    selectedStatus = data.getStatus();
    selectedRole = data.getRole();
    addButton.setDisable(true);
}
    
    public String convertDateToString(Date date) {
    if (date == null) return null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    return sdf.format(date);
}
    
    
   private void menuButton() {
    MenuItem statusActive = new MenuItem("Active");
    MenuItem statusInactive = new MenuItem("Inactive");
    MenuItem statusOnLeave = new MenuItem("On Leave");
    addStatus.getItems().addAll(statusActive, statusInactive, statusOnLeave);

    MenuItem roleAdmin = new MenuItem("Admin");
    MenuItem roleEmployee = new MenuItem("Employee");
    addRole.getItems().addAll(roleAdmin, roleEmployee);

   
    addStatus.setText("Select Status");
    addRole.setText("Select Role");

    statusActive.setOnAction(e -> {
        selectedStatus = "Active";
        addStatus.setText(statusActive.getText()); 
    });
    statusInactive.setOnAction(e -> {
        selectedStatus = "Inactive";
        addStatus.setText(statusInactive.getText()); 
    });
    statusOnLeave.setOnAction(e -> {
        selectedStatus = "On Leave";
        addStatus.setText(statusOnLeave.getText()); 
    });

    roleAdmin.setOnAction(e -> {
        selectedRole = "Admin";
        addRole.setText(roleAdmin.getText()); 
    });
    roleEmployee.setOnAction(e -> {
        selectedRole = "Employee";
        addRole.setText(roleEmployee.getText());
    });
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

        
        if (dataListener != null) {
            dataListener.onEmployeeDataChanged();
        }
        clearFields();
        closeForm(event);
        
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error saving employee data.");
    }
}

    public void setEmployeeDataListener(EmployeeDataListener listener) {
        this.dataListener = listener;
    }
   
    private void clearFields() {
    addFname.clear();
    addLname.clear();
    addEmail.clear();
    AddPhone.clear();
    addAddress.clear();
    addDob.setValue(null);
    addDept.clear();
    addDesignation.clear();
    addSalary.clear();
    addDateHired.setValue(null);
    selectedStatus = null; 
    selectedRole = null; 
}
    private void closeForm(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();
}

    @FXML
    private void updateEmployeeBtn(ActionEvent event) {
         if (employeeData == null) {
            return; 
        }

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
        int id = employeeData.getId(); 

        if(employeeExists(fname, lname)){
            
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Duplicate Entry");
            alert.setContentText("Records Already Exists");
        
        }
        
        try (Connection conn = connectDB.getConnection()) {
            String sql = "UPDATE employees SET fname = ?, lname = ?, email = ?, salary = ?, department = ?, designation = ?, hire_date = ?, address = ?, phone = ?, dob = ?, status = ?, role = ? WHERE id = ?";

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
            pstmt.setInt(13, id); // Set the ID in the query

            pstmt.executeUpdate();
            System.out.println("Employee data updated successfully!");

            if (dataListener != null) {
                dataListener.onEmployeeDataChanged();
            }
            clearFields();
            closeForm(event);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating employee data.");
        }
    }
    
    private boolean employeeExists(String fname, String lname) {
    try (Connection conn = connectDB.getConnection()) {
        String sql = "SELECT COUNT(*) FROM employees WHERE fname = ? AND lname = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, fname);
        pstmt.setString(2, lname);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0; // Return true if there's at least one matching record
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error checking for existing employee.");
    }
    return false;
}
}
