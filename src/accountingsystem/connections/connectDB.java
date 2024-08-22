/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accountingsystem.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class connectDB {
    private static final String URL ="jdbc:mysql://localhost:3306/AccountingSystem";
    private static final String USER ="root";
    private static final String PASSWORD ="";
    
    public static Connection getConnection() throws SQLException{
    
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            throw new SQLException("Class not Found", e);
        }
       return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    
}
