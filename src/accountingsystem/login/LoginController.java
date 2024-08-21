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
import accountingsystem.connections.connectDB;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
/**
 * FXML Controller class
 *
 * @author User
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    
    private int attemptCount = 0;
    private static final int MAX_ATTEMPTS = 3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
private void loginButton(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        if (authenticate(user, pass)) {
            
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
        } else {
            attemptCount++;
            if (attemptCount >= MAX_ATTEMPTS) {
                username.setDisable(true);
                password.setDisable(true);
                showAlert(AlertType.ERROR, "Login Failed", "You have reached the maximum number of login attempts.");
            } else {
                showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password. Attempt " + attemptCount + " of " + MAX_ATTEMPTS);
            }
        }
    }

    
     private boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM loginTable WHERE username = ? AND password = ?";
        try (Connection con = connectDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    }
    
