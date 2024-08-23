/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accountingsystem.connections;

/**
 *
 * @author marte
 */
public class TrialBalance {
    private String trialName;
    private double trialDebit;
    private double trialCredit;

    public TrialBalance(String trialName, double trialDebit, double trialCredit) {
        this.trialName = trialName;
        this.trialDebit = trialDebit;
        this.trialCredit = trialCredit;
    }

    public String getTrialName() {
        return trialName;
    }

    public void setTrialName(String trialName) {
        this.trialName = trialName;
    }

    public double getTrialDebit() {
        return trialDebit;
    }

    public void setTrialDebit(double trialDebit) {
        this.trialDebit = trialDebit;
    }

    public double getTrialCredit() {
        return trialCredit;
    }

    public void setTrialCredit(double trialCredit) {
        this.trialCredit = trialCredit;
    }
}
