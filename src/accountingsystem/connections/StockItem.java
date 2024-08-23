/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accountingsystem.connections;

/**
 *
 * @author User
 */
import java.time.LocalDate;
import java.util.Date;

public class StockItem {
    private int productID;
    private String name;
    private String description;
    private int quantity;
    private double purchaseCost;
    private String supplierName;
    private Date dateOfReceipt;
    private String storageLocation;
    private String batchNumber;

  
    public StockItem(int productID, String name, String description, int quantity, double purchaseCost,
                     String supplierName, Date dateOfReceipt, String storageLocation, String batchNumber) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.purchaseCost = purchaseCost;
        this.supplierName = supplierName;
        this.dateOfReceipt = dateOfReceipt;
        this.storageLocation = storageLocation;
        this.batchNumber = batchNumber;
    }

    // Getters and Setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}

