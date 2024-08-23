package accountingsystem.inventory;

import accountingsystem.connections.StockItem;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import accountingsystem.connections.connectDB;
import accountingsystem.employee.AddEmployeeController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryController implements Initializable, AddStockData {

    @FXML
    private TableView<StockItem> stockTable;
    @FXML
    private TableColumn<StockItem, Integer> stockId;
    @FXML
    private TableColumn<StockItem, String> stockName;
    @FXML
    private TableColumn<StockItem, String> description;
    @FXML
    private TableColumn<StockItem, Integer> quantity;
    @FXML
    private TableColumn<StockItem, Double> purchaseCost;
    @FXML
    private TableColumn<StockItem, String> supplierName;
    @FXML
    private TableColumn<StockItem, Date> dateOfReceipt;
    @FXML
    private TableColumn<StockItem, String> storageLocation;
    @FXML
    private TableColumn<StockItem, String> batchNumber;
    @FXML
    private Button addStockBtn;

    private final ObservableList<StockItem> stockData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadColumnData();
        loadStockData();
    }

    @FXML
    private void addStockBtn(ActionEvent event) {
        // Implement logic to add a new stock item
    }

    private void loadColumnData() {
        stockId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        stockName.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchaseCost.setCellValueFactory(new PropertyValueFactory<>("purchaseCost"));
        supplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        dateOfReceipt.setCellValueFactory(new PropertyValueFactory<>("dateOfReceipt"));
        storageLocation.setCellValueFactory(new PropertyValueFactory<>("storageLocation"));
        batchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));

        // Action column for edit and delete operations
        TableColumn<StockItem, Void> actionColumn = new TableColumn<>("Actions");

        actionColumn.setCellFactory(new Callback<TableColumn<StockItem, Void>, TableCell<StockItem, Void>>() {
            @Override
            public TableCell<StockItem, Void> call(TableColumn<StockItem, Void> param) {
                return new TableCell<StockItem, Void>() {
                    private final Button editBtn = new Button();
                    private final Button deleteBtn = new Button();

                    {
                        // Setup buttons
                        editBtn.setText("Edit");
                        deleteBtn.setText("Delete");

                        editBtn.setOnAction(e -> {
                            StockItem data = getTableView().getItems().get(getIndex());
                            handleButtonAction(data, "edit");
                        });

                        deleteBtn.setOnAction(e -> {
                            StockItem data = getTableView().getItems().get(getIndex());
                            handleButtonAction(data, "delete");
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(5, editBtn, deleteBtn);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
        stockTable.getColumns().add(actionColumn);
    }

    private void handleButtonAction(StockItem data, String action) {
        if ("delete".equals(action)) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Record");
            alert.setHeaderText("Delete this record?");
            alert.setContentText("This can't be undone");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    deleteStockItem(data.getProductID());
                    stockData.remove(data);
                    stockTable.setItems(stockData);

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
        } else if ("edit".equals(action)) {
           
            openEditForm(data);
        }
    }

    private void loadStockData() {
        try (Connection con = connectDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM stock")) {

            stockData.clear();
            while (rs.next()) {
                int productID = rs.getInt("productID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                double purchaseCost = rs.getDouble("purchaseCost");
                String supplierName = rs.getString("supplierName");
                Date dateOfReceipt = rs.getDate("dateOfReceipt");
                String storageLocation = rs.getString("storageLocation");
                String batchNumber = rs.getString("batchNumber");

                stockData.add(new StockItem(productID, name, description, quantity, purchaseCost, supplierName, dateOfReceipt, storageLocation, batchNumber));
            }
            stockTable.setItems(stockData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteStockItem(int productID) throws SQLException {
        try (Connection conn = connectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM stock WHERE productID = ?")) {
            pstmt.setInt(1, productID);
            pstmt.executeUpdate();
        }
    }

    private void openEditForm(StockItem item) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addStock.fxml"));
        Parent newRoot = loader.load();

        AddStockController addstock = loader.getController();
        addstock.setStockDataListener(this);

        addstock.setStockData(item);

        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Update Employee");
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    @Override
    public void stockDataChanged(){
    loadStockData();

}
}

