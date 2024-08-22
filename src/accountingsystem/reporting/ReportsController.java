/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.reporting;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import accountingsystem.connections.EmployeeData;
import accountingsystem.connections.connectDB;
import accountingsystem.connections.reportData;
import java.sql.ResultSet;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;



/**
 * FXML Controller class
 *
 * @author User
 */
public class ReportsController implements Initializable {

    @FXML
    private TableColumn<reportData, String> assetName;

    @FXML
    private TableColumn<reportData, Double> assetValue;

    @FXML
    private Label assets;

    @FXML
    private Label equity;

    @FXML
    private TableColumn<reportData, String> equityName;

    @FXML
    private TableColumn<reportData, Double> equityValue;

    @FXML
    private Label liabilities;

    @FXML
    private TableColumn<reportData, String> liabilityName;

    @FXML
    private TableColumn<reportData, Double> liabilityValue;

    @FXML
    private TableView<reportData> table_breakdown;
    
    private ObservableList<reportData> reportList = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assetName.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        assetValue.setCellValueFactory(new PropertyValueFactory<>("assetValue"));
        liabilityName.setCellValueFactory(new PropertyValueFactory<>("liabilityName"));
        liabilityValue.setCellValueFactory(new PropertyValueFactory<>("liabilityValue"));
        equityName.setCellValueFactory(new PropertyValueFactory<>("equityName"));
        equityValue.setCellValueFactory(new PropertyValueFactory<>("equityValue"));
        
        loadDatafromDatabase();
    }  
    
    private void loadDatafromDatabase(){
        
        String query = "SELECT * FROM reports";
        double totalAssets = 0.0;
        double totalLiabilities = 0.0;
        double totalEquity = 0.0;
        
        try (Connection connection = connectDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                reportData data = new reportData(
                resultSet.getString("assetName"),
                resultSet.getDouble("assetValue"),
                resultSet.getString("liabilityName"),
                resultSet.getDouble("liabilityValue"),
                resultSet.getString("equityName"),
                resultSet.getDouble("equityValue")
                );
                
                reportList.add(data);
                
                totalAssets += resultSet.getDouble("assetValue");
                totalLiabilities += resultSet.getDouble("liabilityValue");
                totalEquity += resultSet.getDouble("equityValue");
                
            }
            
            table_breakdown.setItems(reportList);
            
            assets.setText(String.format("%.2f", totalAssets));
            liabilities.setText(String.format("%.2f", totalLiabilities));
            equity.setText(String.format("%.2f", totalEquity));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }       
}
