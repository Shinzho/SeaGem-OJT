/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.accounts;


import accountingsystem.connections.AccountsPayable;
import accountingsystem.connections.AccountsReceivable;
import java.sql.Connection;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import accountingsystem.connections.connectDB;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AddItemsController implements Initializable {

    @FXML
    private TextField aiVCName;
    @FXML
    private TextField aiInvoiceNo;
    @FXML
    private DatePicker aiInvoiceDate;
    @FXML
    private DatePicker aiInvoiceDue;
    @FXML
    private TextField aiAmountDue;
    @FXML
    private MenuButton aiStatus;
    @FXML
    private TextArea aiDescription;
     private AccountDataListener dataListener;
     private AccountsPayable accountPayable;
     private AccountsReceivable accountReceivable;
    @FXML
    private Button payableBtn;
    @FXML
    private Button receivableBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (Status status : Status.values()) {
            MenuItem menuItem = new MenuItem(status.name());
            menuItem.setOnAction(this::handleStatusSelection);
            aiStatus.getItems().add(menuItem);
        }

       
        aiStatus.setText(Status.PENDING.name());
    }  
    
     private void handleStatusSelection(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        aiStatus.setText(selectedItem.getText());
    }
    public enum Status {
    PENDING,
    PAID,
    OVERDUE;
}
    
     public void setAddItemData(AccountsPayable data) {
    this.accountPayable = data;
    
    if (data != null) {
        aiVCName.setText(data.getVendorName());
        aiInvoiceNo.setText(data.getInvoiceNumber());
        
        // Convert Date to LocalDate for DatePicker
        LocalDate invoiceDate = convertDateToLocalDate((java.sql.Date) data.getInvoiceDate());
        LocalDate dueDate = convertDateToLocalDate((java.sql.Date) data.getDueDate());
        
        aiInvoiceDate.setValue(invoiceDate);
        aiInvoiceDue.setValue(dueDate);
        
        aiAmountDue.setText(String.valueOf(data.getAmountDue()));
        aiStatus.setText(data.getStatus());
        aiDescription.setText(data.getDescription());
        receivableBtn.setDisable(true);
       
    }
}
     
     public void setAddItemData(AccountsReceivable data) {
    this.accountReceivable = data;
    
    if (data != null) {
        aiVCName.setText(data.getCustomerName());
        aiInvoiceNo.setText(data.getInvoiceNumber());

        LocalDate invoiceDate = convertDateToLocalDate((java.sql.Date) data.getInvoiceDate());
        LocalDate dueDate = convertDateToLocalDate((java.sql.Date) data.getDueDate());
        
        aiInvoiceDate.setValue(invoiceDate);
        aiInvoiceDue.setValue(dueDate);
        
        aiAmountDue.setText(String.valueOf(data.getAmountDue()));
        aiStatus.setText(data.getStatus());
        aiDescription.setText(data.getDescription());
        payableBtn.setDisable(true);

    }
}

private LocalDate convertDateToLocalDate(java.sql.Date date) {
    if (date == null) return null;
    return date.toLocalDate(); // Using the method toLocalDate() directly on java.sql.Date
}


     
     
    
    
     
    public void setAccountDataListener(AccountDataListener listener) {
        this.dataListener = listener;
    }
    
    private void saveToAccountsPayable(ActionEvent event) {
    String vendorName = aiVCName.getText();
    String invoiceNumber = aiInvoiceNo.getText();
    LocalDate invoiceDate = aiInvoiceDate.getValue();
    LocalDate dueDate = aiInvoiceDue.getValue();
    Double amountDue = Double.parseDouble(aiAmountDue.getText());
    String status = aiStatus.getText();
    String description = aiDescription.getText();

    String sql = "INSERT INTO accounts_payable (vendor_name, invoice_number, invoice_date, due_date, amount_due, status, description) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = connectDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, vendorName);
        pstmt.setString(2, invoiceNumber);
        pstmt.setDate(3, invoiceDate != null ? java.sql.Date.valueOf(invoiceDate) : null);
        pstmt.setDate(4, dueDate != null ? java.sql.Date.valueOf(dueDate) : null);
        pstmt.setDouble(5, amountDue);
        pstmt.setString(6, status);
        pstmt.setString(7, description);

        pstmt.executeUpdate();
        System.out.println("Data saved to accounts_payable.");

        clearFields();
        closeForm(event);

    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error saving data to accounts_payable.");
    }
}
    
    private void saveToAccountsReceivable(ActionEvent event) {
    String customerName = aiVCName.getText();
    String invoiceNumber = aiInvoiceNo.getText();
    LocalDate invoiceDate = aiInvoiceDate.getValue();
    LocalDate dueDate = aiInvoiceDue.getValue();
    Double amountDue = Double.parseDouble(aiAmountDue.getText());
    String status = aiStatus.getText();
    String description = aiDescription.getText();

    String sql = "INSERT INTO accounts_receivable (customer_name, invoice_number, invoice_date, due_date, amount_due, status, description) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = connectDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, customerName);
        pstmt.setString(2, invoiceNumber);
        pstmt.setDate(3, invoiceDate != null ? java.sql.Date.valueOf(invoiceDate) : null);
        pstmt.setDate(4, dueDate != null ? java.sql.Date.valueOf(dueDate) : null);
        pstmt.setDouble(5, amountDue);
        pstmt.setString(6, status);
        pstmt.setString(7, description);

        pstmt.executeUpdate();
        System.out.println("Data saved to accounts_receivable.");

        clearFields();
        closeForm(event);

    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error saving data to accounts_receivable.");
    }
}

    
    private void clearFields() {
    // Clear TextFields
    aiVCName.clear(); 
    aiInvoiceNo.clear();      
    aiAmountDue.clear(); 
    aiDescription.clear();   

    aiInvoiceDate.setValue(null); 
    aiInvoiceDue.setValue(null);  
    

    aiStatus.setText(Status.PENDING.name()); 

}

    private void closeForm(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();
}



    @FXML
    private void payable(ActionEvent event) {
        
         if (accountPayable != null) {
        updateAccountsPayable(event);
    } else {
       saveToAccountsPayable(event);
    }
    }

    @FXML
    private void receivable(ActionEvent event) {
         if (accountReceivable != null) {
        updateAccountsReceivable(event);
    } else {
       saveToAccountsReceivable(event);
    }
    }
    
    private void updateAccountsPayable(ActionEvent event) {
    if (accountPayable == null) {
        return; // No data to update
    }

    String vendorName = aiVCName.getText();
    String invoiceNumber = aiInvoiceNo.getText();
    LocalDate invoiceDate = aiInvoiceDate.getValue();
    LocalDate dueDate = aiInvoiceDue.getValue();
    double amountDue = Double.parseDouble(aiAmountDue.getText());
    String status = aiStatus.getText();
    String description = aiDescription.getText();
    int id = accountPayable.getId(); 

    
    if (invoiceExists(invoiceNumber)) {
        try (Connection conn = connectDB.getConnection()) {
        String sql = "UPDATE accounts_payable SET vendor_name = ?, invoice_number = ?, invoice_date = ?, due_date = ?, amount_due = ?, status = ?, description = ? WHERE id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, vendorName);
        pstmt.setString(2, invoiceNumber);
        pstmt.setDate(3, invoiceDate != null ? java.sql.Date.valueOf(invoiceDate) : null);
        pstmt.setDate(4, dueDate != null ? java.sql.Date.valueOf(dueDate) : null);
        pstmt.setDouble(5, amountDue);
        pstmt.setString(6, status);
        pstmt.setString(7, description);
        pstmt.setInt(8, id);

        pstmt.executeUpdate();
        showAlert("Success","Accounts Payable data updated successfully!");

        if (dataListener != null) {
            dataListener.onAccountDataChanged();
        }
        clearFields();
        closeForm(event);

    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error updating Accounts Payable data.");
    }
        return; 
    }

    
}

private void showAlert(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}
    
private boolean invoiceExists(String invoiceNumber) {
    try (Connection conn = connectDB.getConnection()) {
        String sql = "SELECT COUNT(*) FROM accounts_payable WHERE invoice_number = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, invoiceNumber);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0; 
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error checking for existing invoice.");
    }
    return false;
}

private void updateAccountsReceivable(ActionEvent event) {
    if (accountReceivable == null) {
        return; // No data to update
    }
 
    String customerName = aiVCName.getText();
    String invoiceNumber = aiInvoiceNo.getText();
    LocalDate invoiceDate = aiInvoiceDate.getValue();
    LocalDate dueDate = aiInvoiceDue.getValue();
    double amountDue = Double.parseDouble(aiAmountDue.getText());
    String status = aiStatus.getText();
    String description = aiDescription.getText();
    int id = accountReceivable.getId(); 

    if (invoiceExists1(invoiceNumber)) {
        try (Connection conn = connectDB.getConnection()) {
        String sql = "UPDATE accounts_receivable SET customer_name = ?, invoice_number = ?, invoice_date = ?, due_date = ?, amount_due = ?, status = ?, description = ? WHERE id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, customerName);
        pstmt.setString(2, invoiceNumber);
        pstmt.setDate(3, invoiceDate != null ? java.sql.Date.valueOf(invoiceDate) : null);
        pstmt.setDate(4, dueDate != null ? java.sql.Date.valueOf(dueDate) : null);
        pstmt.setDouble(5, amountDue);
        pstmt.setString(6, status);
        pstmt.setString(7, description);
        pstmt.setInt(8, id);

        pstmt.executeUpdate();
        showAlert("Success","Accounts Receivable data updated successfully!");

         if (dataListener != null) {
            dataListener.onAccountDataChanged();
        }
        
        clearFields();
        closeForm(event);

    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error updating Accounts Receivable data.");
    }
        return;
    }

    
}

private boolean invoiceExists1(String invoiceNumber) {
    try (Connection conn = connectDB.getConnection()) {
        String sql = "SELECT COUNT(*) FROM accounts_receivable WHERE invoice_number = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, invoiceNumber);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0; 
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error checking for existing invoice.");
    }
    return false;
}


}
