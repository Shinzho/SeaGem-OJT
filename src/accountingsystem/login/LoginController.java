/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import accountingsystem.connections.connectDB;
import java.sql.SQLException;
/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
private void loginButton(ActionEvent event) {
    Connection connection = null;
    
    try {
  
            connection = connectDB.getConnection();
            System.out.println("Connection established successfully!");
            
          
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    try {
        
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/accountingsystem/dashboard/Dashboard.fxml"));
        Parent newRoot = loader.load();

        Stage newStage = new Stage();
        Scene newScene = new Scene(newRoot);
        newStage.setScene(newScene);
        newStage.setTitle("Dashboard");
        newStage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

    } catch (IOException e) {
        e.printStackTrace();
       
    }
    }

    }
    
