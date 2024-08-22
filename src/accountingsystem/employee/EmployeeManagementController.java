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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class EmployeeManagementController implements Initializable, EmployeeDataListener {

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
    
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEmployeeData();
        loadColumnData();
    }
 
    private void loadColumnData() {
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
                private final Button btn = new Button();
                private final Button updateBtn = new Button();
                private final Button attendanceBtn = new Button();

                {
                   
                    Image deleteIcon = new Image(getClass().getResourceAsStream("/resources/delete.png"));
                    Image updateIcon = new Image(getClass().getResourceAsStream("/resources/refresh.png"));
                    Image attendanceIcon = new Image(getClass().getResourceAsStream("/resources/checked.png"));
                    
                    btn.setGraphic(new ImageView(deleteIcon));
                    updateBtn.setGraphic(new ImageView(updateIcon));
                    attendanceBtn.setGraphic(new ImageView(attendanceIcon));

                   
                    btn.setStyle("-fx-background-color: transparent; -fx-border-color:transparent;");
                    updateBtn.setStyle("-fx-background-color: transparent; -fx-border-color:transparent;");
                    attendanceBtn.setStyle("-fx-background-color: transparent; -fx-border-color:transparent;");
                    
                    btn.setPrefHeight(35);
                    btn.setPrefWidth(30);
                    
                    updateBtn.setPrefHeight(35);
                    updateBtn.setPrefWidth(30);
                    
                    attendanceBtn.setPrefHeight(35);
                    attendanceBtn.setPrefWidth(30);
                    
                    
                    
                    btn.setOnAction(e -> {
                        EmployeeData data = getTableView().getItems().get(getIndex());
                        handleButtonAction(data, "delete");
                    });

                    updateBtn.setOnAction(e -> {
                        EmployeeData data = getTableView().getItems().get(getIndex());
                        handleButtonAction(data, "update");
                    });
                    
                    attendanceBtn.setOnAction(e ->{
                        EmployeeData data = getTableView().getItems().get(getIndex());
                        handleButtonAction(data, "attendance");
                    });
                }
                
                

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(1, btn, updateBtn, attendanceBtn);
                        setGraphic(hbox);
                    }
                }
            };
        }
    });
}

    private void handleButtonAction(EmployeeData data, String action) {
    if ("delete".equals(action)) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Record");
        alert.setHeaderText("Delete this record?");
        alert.setContentText("This can't be undone");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                deleteEmployee(data.getId());
                employeeData.remove(data);
                employeeTableView.setItems(employeeData);
                
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Record Deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText("The record has been successfully deleted.");
                successAlert.showAndWait();
            } catch (Exception e) {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("An error occurred while deleting the record.");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        }
    } else if ("update".equals(action)) {
        openEmployeeFormForUpdate(data);
    }else if ("attendance".equals(action)){
        openAttendanceForm(data);
    }
}

    private void loadEmployeeData(){
        try (Connection con = connectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM employees")) {
            employeeData.clear();
            while(rs.next()){
                Integer id = rs.getInt("id");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                Double salary = rs.getDouble("salary");
                String department = rs.getString("department");
                String designation = rs.getString("designation");
                Date hireDate = rs.getDate("hire_date");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Date dob = rs.getDate("dob");
                String status = rs.getString("status");
                String role = rs.getString("role");
                
                employeeData.add(new EmployeeData(id, fname, lname, email, salary, department, designation, hireDate, address, phone, dob, status, role));
            }
            employeeTableView.setItems(employeeData);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void openEmployeeForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
            Parent newRoot = loader.load();

            AddEmployeeController addEmployeeController = loader.getController();
            addEmployeeController.setEmployeeDataListener(this); 

            Stage newStage = new Stage();
            Scene newScene = new Scene(newRoot);
            newStage.setScene(newScene);
            newStage.setTitle("Employee Form");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    

    @Override
    public void onEmployeeDataChanged() {
        loadEmployeeData(); // Refresh the employee data
    }

    public void deleteEmployee(int id){
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private void openEmployeeFormForUpdate(EmployeeData data) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
        Parent newRoot = loader.load();

        AddEmployeeController addEmployeeController = loader.getController();
        addEmployeeController.setEmployeeDataListener(this);

        addEmployeeController.setEmployeeData(data);

        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Update Employee");
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    private void openAttendanceForm(EmployeeData data) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeWorkDetails.fxml"));
        Parent newRoot = loader.load();

        EmployeeWorkDetailsController wdController = loader.getController();
        wdController.setEmployeeData(data); // Pass the data here

        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Work Details");
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    
}
