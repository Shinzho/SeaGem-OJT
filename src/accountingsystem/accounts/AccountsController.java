/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.accounts;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Connection;
import accountingsystem.connections.connectDB;
import accountingsystem.connections.AccountsPayable;
import accountingsystem.connections.AccountsReceivable;
import accountingsystem.employee.EmployeeWorkDetailsController;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author User
 */
public class AccountsController implements Initializable, AccountDataListener {

    @FXML
    private TableView<AccountsPayable> payableTable;
    @FXML
    private TableColumn<AccountsPayable, Integer> pID;
    @FXML
    private TableColumn<AccountsPayable, String> pVendor;
    @FXML
    private TableColumn<AccountsPayable, String> pInvoice;
    @FXML
    private TableColumn<AccountsPayable, Date> pInvoiceDate;
    @FXML
    private TableColumn<AccountsPayable, Date> pDueDate;
    @FXML
    private TableColumn<AccountsPayable, Double> pAmountDue;
    @FXML
    private TableColumn<AccountsPayable, String> pStatus;
    @FXML
    private TableColumn<AccountsPayable, String> pDesc;
    @FXML
    private TableView<AccountsReceivable> receivableTable;
    @FXML
    private TableColumn<AccountsReceivable, Integer> rID;
    @FXML
    private TableColumn<AccountsReceivable, String> rCustName;
    @FXML
    private TableColumn<AccountsReceivable, String> rInvoice;
    @FXML
    private TableColumn<AccountsReceivable, Date> rInvoiceDate;
    @FXML
    private TableColumn<AccountsReceivable, Date> rDueDate;
    @FXML
    private TableColumn<AccountsReceivable, Double> rAmount;
    @FXML
    private TableColumn<AccountsReceivable, String> rStatus;
    @FXML
    private TableColumn<AccountsReceivable, String> rDesc;
    
    private final ObservableList<AccountsPayable> payableData = FXCollections.observableArrayList();
    private final ObservableList<AccountsReceivable> receivableData = FXCollections.observableArrayList();
    @FXML
    private TableColumn<AccountsPayable, Void> pAction;
    @FXML
    private TableColumn<AccountsReceivable, Void> rAction;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    loadAccountsPayableData(); 
    loadAccountsReceivableData();
    loadColumnData();
    }    
    
     

    @FXML
    private void addPayable(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addItems.fxml"));
        Parent newRoot = loader.load();

      
        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Add Item Details");
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void addReceivable(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addItems.fxml"));
        Parent newRoot = loader.load();

      
        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Add Item Details");
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    
    private void loadColumnData() {
    // Accounts Payable Columns
    pID.setCellValueFactory(new PropertyValueFactory<>("id"));
    pVendor.setCellValueFactory(new PropertyValueFactory<>("vendorName"));
    pInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
    pInvoiceDate.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
    pDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    pAmountDue.setCellValueFactory(new PropertyValueFactory<>("amountDue"));
    pStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    pDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

    pAction.setCellFactory((TableColumn<AccountsPayable, Void> param) -> new TableCell<AccountsPayable, Void>() {
        private final Button updateBtn = new Button();
        private final Button deleteBtn = new Button();
        
        {
            Image updateIcon = new Image(getClass().getResourceAsStream("/resources/refresh.png"));
            Image deleteIcon = new Image(getClass().getResourceAsStream("/resources/delete.png"));
            
            updateBtn.setGraphic(new ImageView(updateIcon));
            deleteBtn.setGraphic(new ImageView(deleteIcon));
            
            updateBtn.setStyle("-fx-background-color: transparent; -fx-border-color:transparent;");
            deleteBtn.setStyle("-fx-background-color: transparent; -fx-border-color:transparent;");
            
            updateBtn.setPrefHeight(35);
            updateBtn.setPrefWidth(30);
            
            deleteBtn.setPrefHeight(35);
            deleteBtn.setPrefWidth(30);
            
            updateBtn.setOnAction(e -> {
                AccountsPayable data = getTableView().getItems().get(getIndex());
                handleButtonAction(data, "update");
            });
            
            deleteBtn.setOnAction(e -> {
                AccountsPayable data = getTableView().getItems().get(getIndex());
                handleButtonAction(data, "delete");
            });
        }
        
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            
            if (empty) {
                setGraphic(null);
            } else {
                HBox hbox = new HBox(1, updateBtn, deleteBtn);
                setGraphic(hbox);
            }
        }
    });

    // Accounts Receivable Columns
    rID.setCellValueFactory(new PropertyValueFactory<>("id"));
    rCustName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    rInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
    rInvoiceDate.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
    rDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    rAmount.setCellValueFactory(new PropertyValueFactory<>("amountDue"));
    rStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    rDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

    rAction.setCellFactory((TableColumn<AccountsReceivable, Void> param) -> new TableCell<AccountsReceivable, Void>() {
        private final Button updateBtn = new Button();
        private final Button deleteBtn = new Button();
        
        {
            Image updateIcon = new Image(getClass().getResourceAsStream("/resources/refresh.png"));
            Image deleteIcon = new Image(getClass().getResourceAsStream("/resources/delete.png"));
            
            updateBtn.setGraphic(new ImageView(updateIcon));
            deleteBtn.setGraphic(new ImageView(deleteIcon));
            
            updateBtn.setStyle("-fx-background-color: transparent; -fx-border-color:transparent;");
            deleteBtn.setStyle("-fx-background-color: transparent; -fx-border-color:transparent;");
            
            updateBtn.setPrefHeight(35);
            updateBtn.setPrefWidth(30);
            
            deleteBtn.setPrefHeight(35);
            deleteBtn.setPrefWidth(30);
            
            updateBtn.setOnAction(e -> {
                AccountsReceivable data = getTableView().getItems().get(getIndex());
                handleButtonAction1(data, "update");
            });
            
            deleteBtn.setOnAction(e -> {
                AccountsReceivable data = getTableView().getItems().get(getIndex());
                handleButtonAction1(data, "delete");
            });
        }
        
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            
            if (empty) {
                setGraphic(null);
            } else {
                HBox hbox = new HBox(1, updateBtn, deleteBtn);
                setGraphic(hbox);
            }
        }
    });
}

    @Override
    public void onAccountDataChanged() {
        loadAccountsPayableData();
    }
    
    private void loadAccountsPayableData() {
    try (Connection con = connectDB.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT * FROM accounts_payable")) {

        payableData.clear(); 
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String vendorName = rs.getString("vendor_name");
            String invoiceNumber = rs.getString("invoice_number");
            Date invoiceDate = rs.getDate("invoice_date");
            Date dueDate = rs.getDate("due_date");
            double amountDue = rs.getDouble("amount_due");
            String status = rs.getString("status");
            String description = rs.getString("description");
            
            payableData.add(new AccountsPayable(id, vendorName, invoiceNumber, invoiceDate, dueDate, amountDue, status, description));
        }
        
        payableTable.setItems(payableData);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    private void loadAccountsReceivableData() {
    try (Connection con = connectDB.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT * FROM accounts_receivable")) {

       receivableData.clear(); 
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String customerName = rs.getString("customer_name");
            String invoiceNumber = rs.getString("invoice_number");
            Date invoiceDate = rs.getDate("invoice_date");
            Date dueDate = rs.getDate("due_date");
            double amountDue = rs.getDouble("amount_due");
            String status = rs.getString("status");
            String description = rs.getString("description");
            
            receivableData.add(new AccountsReceivable(id, customerName, invoiceNumber, invoiceDate, dueDate, amountDue, status, description));
        } 
        receivableTable.setItems(receivableData);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    private void handleButtonAction(AccountsPayable data, String action) {
    if ("update".equals(action)) {
        
        openPayableFormForUpdate(data);
    } else if ("delete".equals(action)) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Record");
        alert.setHeaderText("Delete this record?");
        alert.setContentText("This can't be undone.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                deletePayable(data.getId());
                payableData.remove(data);
                payableTable.setItems(payableData);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Record Deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText("The record has been successfully deleted.");
                successAlert.showAndWait();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("An error occurred while deleting the record.");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }
}
    
    private void handleButtonAction1(AccountsReceivable data, String action) {
    if ("update".equals(action)) {
        
       openReceivableFormForUpdate(data);
    } else if ("delete".equals(action)) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Record");
        alert.setHeaderText("Delete this record?");
        alert.setContentText("This can't be undone.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                deletePayable(data.getId());
                receivableData.remove(data);
                receivableTable.setItems(receivableData);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Record Deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText("The record has been successfully deleted.");
                successAlert.showAndWait();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("An error occurred while deleting the record.");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }
}

    private void openPayableFormForUpdate(AccountsPayable data) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addItems.fxml"));
        Parent newRoot = loader.load();

        AddItemsController cont = loader.getController();
        cont.setAccountDataListener(this);
        cont.setAddItemData(data);

        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Work Details");
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
}
    
    private void openReceivableFormForUpdate(AccountsReceivable data) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addItems.fxml"));
        Parent newRoot = loader.load();

         AddItemsController conts = loader.getController();
        conts.setAccountDataListener(this);
        conts.setAddItemData(data);
        
        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Work Details");
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
}

private void deletePayable(int id) {
    
    String sql = "DELETE FROM accounts_payable WHERE id = ?";
        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
 
}

private void deleteReceivables(int id) {
    
    String sql = "DELETE FROM accounts_receivable WHERE id = ?";
        try (Connection con = connectDB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
 
}

    @FXML
    private void refresh(ActionEvent event) {
        loadAccountsPayableData(); 
    loadAccountsReceivableData();
    }

    
    
    
   

    
    
}
