/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accountingsystem.connections;

/**
 *
 * @author marte
 */

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

public class JournalEntry {
    private final StringProperty journalDate;
    private final StringProperty journalDesc;
    private final DoubleProperty journalDebit;
    private final DoubleProperty journalCredit;
    private final StringProperty journalAccount;

    public JournalEntry(String journalDate, String journalAccount, double journalDebit, double journalCredit, String journalDesc) {
        this.journalDate = new SimpleStringProperty(journalDate);
        this.journalAccount = new SimpleStringProperty(journalAccount);
        this.journalDebit = new SimpleDoubleProperty(journalDebit);
        this.journalCredit = new SimpleDoubleProperty(journalCredit);
        this.journalDesc = new SimpleStringProperty(journalDesc);
    }

    public String getJournalDate() {
        return journalDate.get();
    }

    public void setJournalDate(String journalDate) {
        this.journalDate.set(journalDate);
    }

    public StringProperty journalDateProperty() {
        return journalDate;
    }

    public String getJournalAccount() {
        return journalAccount.get();
    }

    public void setJournalAccount(String journalAccount) {
        this.journalAccount.set(journalAccount);
    }

    public StringProperty journalAccountProperty() {
        return journalAccount;
    }

    public double getJournalDebit() {
        return journalDebit.get();
    }

    public void setJournalDebit(double journalDebit) {
        this.journalDebit.set(journalDebit);
    }

    public DoubleProperty journalDebitProperty() {
        return journalDebit;
    }

    public double getJournalCredit() {
        return journalCredit.get();
    }

    public void setJournalCredit(double journalCredit) {
        this.journalCredit.set(journalCredit);
    }

    public DoubleProperty journalCreditProperty() {
        return journalCredit;
    }

    public String getJournalDesc() {
        return journalDesc.get();
    }

    public void setJournalDesc(String journalDesc) {
        this.journalDesc.set(journalDesc);
    }

    public StringProperty journalDescProperty() {
        return journalDesc;
    }
}
