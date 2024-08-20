/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.employee;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import accountingsystem.connections.EmployeeData;
import accountingsystem.connections.connectDB;
import java.io.IOException;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author User
 */
public class EmployeeManagementController implements Initializable {

    @FXML
    private TableColumn<EmployeeData, String> fnameColumn;
    @FXML
    private TableColumn<EmployeeData, String> lnameColumn;
    @FXML
    private TableColumn<EmployeeData, String> emailColumn;
    @FXML
    private TableColumn<EmployeeData, Double> salaryColumn;
    @FXML
    private TableColumn<EmployeeData, String> departmentColumn;
    @FXML
    private TableColumn<EmployeeData, String> designationColumn;
    @FXML
    private TableColumn<EmployeeData, Date> hireDateColumn;
    @FXML
    private TableColumn<EmployeeData, String> addressColumn;
    @FXML
    private TableColumn<EmployeeData, String> phoneColumn;
    @FXML
    private TableColumn<EmployeeData, String> statusColumn;
    @FXML
    private TableColumn<EmployeeData, String> roleColumn;
    
    private final ObservableList<EmployeeData> employeeData = FXCollections.observableArrayList();
    @FXML
    private TableView<EmployeeData> employeeTableView;
    @FXML
    private TableColumn<EmployeeData, Void> actionColumn;
    @FXML
    private Button addEmployeeBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEmployeeData();
        loadColumnData();
    }    
    
    private void loadColumnData(){
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        
         actionColumn.setCellFactory(new Callback<TableColumn<EmployeeData, Void>, TableCell<EmployeeData, Void>>() {
            @Override
            public TableCell<EmployeeData, Void> call(TableColumn<EmployeeData, Void> param) {
                return new TableCell<EmployeeData, Void>() {
                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction(e -> {
                            EmployeeData data = getTableView().getItems().get(getIndex());
                           
                            handleButtonAction(data);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
    
    }
    
    private void handleButtonAction(EmployeeData data) {
        System.out.println("Button clicked for employee: " + data.getFname());
    }
    
    private void loadEmployeeData(){
    
    try(
         Connection con = connectDB.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT * FROM employees")){
        while(rs.next()){
                Integer id = rs.getInt("id");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                Double salary = rs.getDouble("salary");
                String department = rs.getString("department");
                String designation = rs.getString("designation");
                Date hireDate = rs.getDate("hire_date"); // Convert to String
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Date dob = rs.getDate("dob");
                String status = rs.getString("status");
                String role = rs.getString("role");
                
                employeeData.add(new EmployeeData(id, fname, lname, email, salary, department, designation, hireDate, address, phone, dob, status, role));
        }
        employeeTableView.setItems(employeeData);
    
    }catch(SQLException e){
        e.printStackTrace();
    }
    
    
    }

    @FXML
    private void openEmployeeForm(ActionEvent event) {
        
        try {
        
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
        Parent newRoot = loader.load();

        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Employee Form");
        newStage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

    } catch (IOException e) {
        e.printStackTrace();
       
    }
    }
    
    
}
