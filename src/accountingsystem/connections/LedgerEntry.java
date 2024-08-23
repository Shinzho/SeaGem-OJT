/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accountingsystem.connections;

/**
 *
 * @author marte
 */
public class LedgerEntry {
    private String ledgerDate;
    private String ledgerDesc;
    private double ledgerDebit;
    private double ledgerCredit;
    private double ledgerBalance;

    public LedgerEntry(String ledgerDate, String ledgerDesc, double ledgerDebit, double ledgerCredit, double ledgerBalance) {
        this.ledgerDate = ledgerDate;
        this.ledgerDesc = ledgerDesc;
        this.ledgerDebit = ledgerDebit;
        this.ledgerCredit = ledgerCredit;
        this.ledgerBalance = ledgerBalance;
    }

    public String getLedgerDate() {
        return ledgerDate;
    }

    public void setLedgerDate(String ledgerDate) {
        this.ledgerDate = ledgerDate;
    }

    public String getLedgerDesc() {
        return ledgerDesc;
    }

    public void setLedgerDesc(String ledgerDesc) {
        this.ledgerDesc = ledgerDesc;
    }

    public double getLedgerDebit() {
        return ledgerDebit;
    }

    public void setLedgerDebit(double ledgerDebit) {
        this.ledgerDebit = ledgerDebit;
    }

    public double getLedgerCredit() {
        return ledgerCredit;
    }

    public void setLedgerCredit(double ledgerCredit) {
        this.ledgerCredit = ledgerCredit;
    }

    public double getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(double ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }
}