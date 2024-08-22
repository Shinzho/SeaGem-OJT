/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.payroll;

import accountingsystem.connections.EmployeeData;
import accountingsystem.connections.connectDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import java.time.temporal.ChronoUnit;
import java.sql.Date; // For java.sql.Date
import java.time.LocalDate;


/**
 * FXML Controller class
 *
 * @author User
 */
public class PayrollController implements Initializable {

    @FXML
    private TableView<EmployeeData> emptable;
    @FXML
    private TableColumn<EmployeeData, Integer> empid;
    @FXML
    private TableColumn<EmployeeData, String> enpfname;
    @FXML
    private TableColumn<EmployeeData, String> emplname;
    @FXML
    private TableColumn<EmployeeData, String> empDept;
    @FXML
    private TableColumn<EmployeeData, String> empPos;
    @FXML
    private Label payrollEmpId;
    @FXML
    private Label payrollEmpName;
    @FXML
    private Label payrollEmpDept;
    @FXML
    private Label payrollEmpDeisgnation;
    @FXML
    private Label payrollNo;
    @FXML
    private Label payrollDate;
    @FXML
    private Label payrollEmpRate;
    @FXML
    private Label payrollBasicPay;
    @FXML
    private Label payrollOvertime;
    @FXML
    private Label payrollAllowance;
    @FXML
    private Label payrollOthers;
    @FXML
    private Label earningsTotal;
    @FXML
    private Label payrollSSS;
    @FXML
    private Label payrollPagibig;
    @FXML
    private Label payrollPhilhealth;
    @FXML
    private Label payrollOthers2;
    @FXML
    private Label deductionTotal;
    @FXML
    private Label netSalary;
    @FXML
    private Label payrollLate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadColumnData();
        loadEmployeeData();
        
        emptable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        updateLabelsWithSelectedRow(newValue); 
    });
    }    
    
    
    private void loadColumnData() {
    empid.setCellValueFactory(new PropertyValueFactory<>("id"));
    enpfname.setCellValueFactory(new PropertyValueFactory<>("fname"));
    emplname.setCellValueFactory(new PropertyValueFactory<>("lname"));
    empDept.setCellValueFactory(new PropertyValueFactory<>("department"));
    empPos.setCellValueFactory(new PropertyValueFactory<>("designation"));
    
    }
    
     private final ObservableList<EmployeeData> workingData = FXCollections.observableArrayList();

   private void loadEmployeeData(){
        try (Connection con = connectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM employees")) {
            workingData.clear();
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
                
                workingData.add(new EmployeeData(id, fname, lname, email, salary, department, designation, hireDate, address, phone, dob, status, role));
            }
            emptable.setItems(workingData);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
   
   double payrollsss,payrollpagibig,payrollph,payrolltax;
   
    private void loadContributionData() {
        try (Connection con = connectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM contributions")) {

            while (rs.next()) {
                Integer id = rs.getInt("id");
                Double sss = rs.getDouble("sss");
                Double pagibig = rs.getDouble("pagibig");
                Double philhealth = rs.getDouble("philhealth");
                Double withholdingTax = rs.getDouble("withholding_tax");
                
                payrollsss = sss /100;
                payrollpagibig = pagibig /100;
                payrollph = philhealth /100;
                payrolltax = withholdingTax /100;
                
            }
           

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
   
   private void loadEmployeeAndWorkingDetails(int employeeId) {
    String sql = "SELECT wd.id, wd.empId, wd.empMonth, wd.startDate, wd.endDate, wd.absences, wd.allowances, wd.overtime, wd.lates, " +
                 "e.fname, e.lname, e.department, e.designation, e.salary " +
                 "FROM workingdetails wd " +
                 "JOIN employees e ON wd.empId = e.id " +
                 "WHERE e.id = ?";

    try (Connection con = connectDB.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, employeeId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            // Retrieve employee details
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            String department = rs.getString("department");
            String designation = rs.getString("designation");
            double salary = rs.getDouble("salary");

            payrollEmpId.setText(String.valueOf(employeeId));
            payrollEmpName.setText(fname + " " + lname);
            payrollEmpDept.setText(department);
            payrollEmpDeisgnation.setText(designation);

            // Retrieve and display working details
            Date startDate = rs.getDate("startDate");
            Date endDate = rs.getDate("endDate");
            int absences = rs.getInt("absences");
            int allowances = rs.getInt("allowances");
            int overtime = rs.getInt("overtime");
            int lates = rs.getInt("lates");

            double dailyRate = salary / 22; 
            double perhour = dailyRate / 8;
            double absentrate = absences * dailyRate;
            double attendanceDays = 22 - absences;
            double otr = perhour * 1.25;
            double overtimes = overtime * otr;
            double late = lates * perhour;

            // Format numbers to two decimal places
            String formattedOvertime = String.format("%.2f", overtimes);
            String formattedAbsentrate = String.format("%.2f", absentrate);
            String formattedLate = String.format("%.2f", late);

            payrollOvertime.setText(formattedOvertime);
            payrollAllowance.setText(String.valueOf(allowances));
            payrollOthers.setText(formattedAbsentrate);
            payrollLate.setText(formattedLate);
           
            double taxs = salary * payrolltax;
            if(salary > 20853){
                payrollOthers2.setText(String.format("%.2f", taxs));
                taxs = 0;
            } else {
                payrollOthers2.setText("0.00");
            }
            
            double eseses = salary * payrollsss;
            double pegebeg = salary * payrollpagibig;
             double health = salary * payrollph;
            double deductTotal = eseses + pegebeg + health + taxs;
            String formatteddeduct = String.format("%.2f", deductTotal);
            deductionTotal.setText(formatteddeduct);
                    
        payrollSSS.setText(String.valueOf(eseses));
        payrollPagibig.setText(String.valueOf(pegebeg));
        payrollPhilhealth.setText(String.valueOf(health));
        
        double earnings = salary + overtimes + allowances;
        String formattedearnings = String.format("%.2f", earnings);
        earningsTotal.setText(String.valueOf(formattedearnings));
        
        double net = earnings - deductTotal;
        String formattednet = String.format("%.2f", net);
        
        
        netSalary.setText(formattednet);
        
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

   
   
   private void updateLabelsWithSelectedRow(EmployeeData selectedEmployee) {
    if (selectedEmployee != null) {
  
        payrollEmpId.setText(String.valueOf(selectedEmployee.getId()));
        payrollEmpName.setText(selectedEmployee.getFname() + " " + selectedEmployee.getLname());
        payrollEmpDept.setText(selectedEmployee.getDepartment());
        payrollEmpDeisgnation.setText(selectedEmployee.getDesignation());
        payrollEmpRate.setText(String.valueOf(selectedEmployee.getSalary()));
        payrollBasicPay.setText(String.valueOf(selectedEmployee.getSalary()));
        
        //para sa date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        payrollDate.setText(formattedDate);

        String rand = generatePayrollNumber();
        payrollNo.setText(rand);
        
        loadContributionData();
        loadEmployeeAndWorkingDetails(selectedEmployee.getId());
        
       
        
        

    }
}

   private String generatePayrollNumber() {
    int randomNumber = (int) (Math.random() * 1000000); 
    return String.format("%06d", randomNumber);
}

    
}
