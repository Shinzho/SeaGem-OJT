/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accountingsystem.connections;

/**
 *
 * @author marte
 */
public class reportData {
    private String assetName;
    private Double assetValue;
    private String liabilityName;
    private Double liabilityValue;
    private String equityName;
    private Double equityValue;

    public reportData(String assetName, Double assetValue, String liabilityName, Double liabilityValue, String equityName, Double equityValue) {
        this.assetName = assetName;
        this.assetValue = assetValue;
        this.liabilityName = liabilityName;
        this.liabilityValue = liabilityValue;
        this.equityName = equityName;
        this.equityValue = equityValue;
    }

    // Getters
    public String getAssetName() {
        return assetName;
    }

    public Double getAssetValue() {
        return assetValue;
    }

    public String getLiabilityName() {
        return liabilityName;
    }

    public Double getLiabilityValue() {
        return liabilityValue;
    }

    public String getEquityName() {
        return equityName;
    }

    public Double getEquityValue() {
        return equityValue;
    }

    // Setters
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setAssetValue(Double assetValue) {
        this.assetValue = assetValue;
    }

    public void setLiabilityName(String liabilityName) {
        this.liabilityName = liabilityName;
    }

    public void setLiabilityValue(Double liabilityValue) {
        this.liabilityValue = liabilityValue;
    }

    public void setEquityName(String equityName) {
        this.equityName = equityName;
    }

    public void setEquityValue(Double equityValue) {
        this.equityValue = equityValue;
    }
}

