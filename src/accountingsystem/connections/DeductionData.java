/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accountingsystem.connections;

/**
 *
 * @author User
 */
public class DeductionData {
    private int id;
    private Double SSS;
    private Double pagibig;
    private Double philhealth;
    private Double withholdingTax;

  
    public DeductionData(int id, Double SSS, Double pagibig, Double philhealth, Double withholdingTax) {
        this.id = id;
        this.SSS = SSS;
        this.pagibig = pagibig;
        this.philhealth = philhealth;
        this.withholdingTax = withholdingTax;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Double getSSS() {
        return SSS;
    }

    public Double getPagibig() {
        return pagibig;
    }

    public Double getPhilhealth() {
        return philhealth;
    }

    public Double getWithholdingTax() {
        return withholdingTax;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setSSS(Double SSS) {
        this.SSS = SSS;
    }

    public void setPagibig(Double pagibig) {
        this.pagibig = pagibig;
    }

    public void setPhilhealth(Double philhealth) {
        this.philhealth = philhealth;
    }

    public void setWithholdingTax(Double withholdingTax) {
        this.withholdingTax = withholdingTax;
    }
}

