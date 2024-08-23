package accountingsystem.inventory;

import accountingsystem.connections.StockItem;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import accountingsystem.connections.connectDB;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.ResultSet;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AddStockController implements Initializable {

    @FXML
    private TextField stockNametxt;
    @FXML
    private TextField quantitytxt;
    @FXML
    private TextField costtxt;
    @FXML
    private TextArea desctxt;
    @FXML
    private TextField suppliertxt;
    @FXML
    private TextField batchnumbertxt;
    @FXML
    private TextField locationtxt;
    @FXML
    private DatePicker receiptDatePicker;
    @FXML
    private Button addStock;
    @FXML
    private Button updateStock;
    private StockItem stockitem;
    private AddStockData dataListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

   public void setStockData(StockItem data) {
    this.stockitem = data;

    stockNametxt.setText(data.getName());
    quantitytxt.setText(String.valueOf(data.getQuantity()));
    costtxt.setText(String.valueOf(data.getPurchaseCost()));
    desctxt.setText(data.getDescription());
    suppliertxt.setText(data.getSupplierName());
    batchnumbertxt.setText(data.getBatchNumber());
    locationtxt.setText(data.getStorageLocation());

    String receiptDateString = convertDateToString(data.getDateOfReceipt());

    if (receiptDateString != null) {
        LocalDate receiptLocalDate = LocalDate.parse(receiptDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        receiptDatePicker.setValue(receiptLocalDate);
    }

    addStock.setDisable(true);
}

public String convertDateToString(Date date) {
    if (date == null) return null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    return sdf.format(date);
}

    public void setStockDataListener(AddStockData listener) {
        this.dataListener = listener;
    }
    
    @FXML
    private void addstock(ActionEvent event) {
    String stockName = stockNametxt.getText().trim();
    String quantity = quantitytxt.getText().trim();
    String cost = costtxt.getText().trim();
    String supplier = suppliertxt.getText().trim();
    String batchNumber = batchnumbertxt.getText().trim();
    String location = locationtxt.getText().trim();
    String description = desctxt.getText().trim(); // Description can be null
    LocalDate receiptDate = receiptDatePicker.getValue();

    if (stockName.isEmpty() || quantity.isEmpty() || cost.isEmpty() || supplier.isEmpty() || batchNumber.isEmpty() || location.isEmpty() || receiptDate == null) {
        showAlert("Missing Fields", "Please fill all fields, put 0 if not applicable.");
        return;
    }

    String sql = "INSERT INTO stockTable (productID, name, description, quantity, purchaseCost, supplierName, dateOfReceipt, storageLocation, batchNumber) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = connectDB.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, null); 
        pstmt.setString(2, stockName);
        pstmt.setString(3, description.isEmpty() ? null : description);
        pstmt.setInt(4, Integer.parseInt(quantity));
        pstmt.setDouble(5, Double.parseDouble(cost));
        pstmt.setString(6, supplier);
        pstmt.setDate(7, java.sql.Date.valueOf(receiptDate));
        pstmt.setString(8, location);
        pstmt.setString(9, batchNumber);

        pstmt.executeUpdate();
        showAlert("Success", "Stock data saved successfully.");
        clearFields();
    } catch (SQLException e) {
        showAlert("Error", "Error saving data to stock table.");
        e.printStackTrace();
    }
}

private void showAlert(String header, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}

private void clearFields() {
    stockNametxt.clear();
    quantitytxt.clear();
    costtxt.clear();
    suppliertxt.clear();
    batchnumbertxt.clear();
    locationtxt.clear();
    desctxt.clear();
    receiptDatePicker.setValue(null);
}

    @FXML
private void updateStockItemBtn(ActionEvent event) {
    if (stockitem == null) {
        return; 
    }

    String name = stockNametxt.getText();
    int quantity = Integer.parseInt(quantitytxt.getText());
    double purchaseCost = Double.parseDouble(costtxt.getText());
    String description = desctxt.getText();
    String supplierName = suppliertxt.getText();
    String batchNumber = batchnumbertxt.getText();
    String storageLocation = locationtxt.getText();
    LocalDate dateOfReceipt = receiptDatePicker.getValue();
    int id = stockitem.getProductID(); 

    if (stockItemExists(name, batchNumber)) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Duplicate Entry");
        alert.setContentText("A stock item with this name and batch number already exists.");
        alert.showAndWait();
        return;
    }

    try (Connection conn = connectDB.getConnection()) {
        String sql = "UPDATE stock SET name = ?, quantity = ?, purchaseCost = ?, description = ?, supplierName = ?, batchNumber = ?, storageLocation = ?, dateOfReceipt = ? WHERE productID = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, name);
        pstmt.setInt(2, quantity);
        pstmt.setDouble(3, purchaseCost);
        pstmt.setString(4, description);
        pstmt.setString(5, supplierName);
        pstmt.setString(6, batchNumber);
        pstmt.setString(7, storageLocation);
        pstmt.setDate(8, dateOfReceipt != null ? java.sql.Date.valueOf(dateOfReceipt) : null);
        pstmt.setInt(9, id); 

        pstmt.executeUpdate();
        showAlert("Success","Stock data updated");

        if (dataListener != null) {
            dataListener.stockDataChanged();
        }
        clearFields();
        closeForm(event);

    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error updating stock item.");
    }
}

private boolean stockItemExists(String name, String batchNumber) {
    try (Connection conn = connectDB.getConnection()) {
        String sql = "SELECT COUNT(*) FROM stock WHERE name = ? AND batchNumber = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, batchNumber);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0; // Return true if there's at least one matching record
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error checking for existing stock item.");
    }
    return false;
}

  private void closeForm(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();
}


}

