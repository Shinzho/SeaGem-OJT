/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package accountingsystem.ledger;

import accountingsystem.connections.JournalEntry;
import accountingsystem.connections.LedgerEntry;
import accountingsystem.connections.TrialBalance;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import accountingsystem.connections.connectDB;
import accountingsystem.connections.reportData;
import java.sql.ResultSet;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author marte
 */
public class GeneralLedgerController implements Initializable {

    @FXML
    private TableColumn<JournalEntry, String> journalAccount;

    @FXML
    private TableColumn<JournalEntry, Double> journalCredit;

    @FXML
    private TableColumn<JournalEntry, String> journalDate;

    @FXML
    private TableColumn<JournalEntry, Double> journalDebit;

    @FXML
    private TableColumn<JournalEntry, String> journalDesc;

    @FXML
    private TableColumn<LedgerEntry, Double> ledgerBalance;

    @FXML
    private TableColumn<LedgerEntry, Double> ledgerCredit;

    @FXML
    private TableColumn<LedgerEntry, String> ledgerDate;

    @FXML
    private TableColumn<LedgerEntry, Double> ledgerDebit;

    @FXML
    private TableColumn<LedgerEntry, String> ledgerDesc;

    @FXML
    private TableView<JournalEntry> journalTable;

    @FXML
    private TableView<TrialBalance> trialBalanceTable;

    @FXML
    private TableView<LedgerEntry> ledgerTable;

    @FXML
    private Label nameCode;

    @FXML
    private Label nameType;

    @FXML
    private TableColumn<TrialBalance, Double> trialCredit;

    @FXML
    private TableColumn<TrialBalance, Double> trialDebit;

    @FXML
    private TableColumn<TrialBalance, String> trialName;

    private ObservableList<JournalEntry> journalData;
    private ObservableList<LedgerEntry> ledgerData;
    private ObservableList<TrialBalance> trialBalanceData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        journalData = FXCollections.observableArrayList();
        ledgerData = FXCollections.observableArrayList();
        trialBalanceData = FXCollections.observableArrayList();

        journalDate.setCellValueFactory(new PropertyValueFactory<>("journalDate"));
        journalAccount.setCellValueFactory(new PropertyValueFactory<>("journalAccount"));
        journalDebit.setCellValueFactory(new PropertyValueFactory<>("journalDebit"));
        journalCredit.setCellValueFactory(new PropertyValueFactory<>("journalCredit"));
        journalDesc.setCellValueFactory(new PropertyValueFactory<>("journalDesc"));

        ledgerDate.setCellValueFactory(new PropertyValueFactory<>("ledgerDate"));
        ledgerDesc.setCellValueFactory(new PropertyValueFactory<>("ledgerDesc"));
        ledgerDebit.setCellValueFactory(new PropertyValueFactory<>("ledgerDebit"));
        ledgerCredit.setCellValueFactory(new PropertyValueFactory<>("ledgerCredit"));
        ledgerBalance.setCellValueFactory(new PropertyValueFactory<>("ledgerBalance"));

        trialName.setCellValueFactory(new PropertyValueFactory<>("trialName"));
        trialDebit.setCellValueFactory(new PropertyValueFactory<>("trialDebit"));
        trialCredit.setCellValueFactory(new PropertyValueFactory<>("trialCredit"));

        loadJournalData();
        loadLedgerData();
        loadTrialBalanceData();
    }

    private void loadJournalData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT date, account, debit, credit, description FROM journal";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                String account = rs.getString("account");
                Double debit = rs.getDouble("debit");
                Double credit = rs.getDouble("credit");
                String description = rs.getString("description");

                journalData.add(new JournalEntry(date, account, debit, credit, description));
            }

            journalTable.setItems(journalData);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLedgerData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT date, description, debit, credit, balance FROM ledger";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                String description = rs.getString("description");
                Double debit = rs.getDouble("debit");
                Double credit = rs.getDouble("credit");
                Double balance = rs.getDouble("balance");

                ledgerData.add(new LedgerEntry(date, description, debit, credit, balance));
            }

            ledgerTable.setItems(ledgerData);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTrialBalanceData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectDB.getConnection();
            String query = "SELECT trial_name, trial_debit, trial_credit FROM trial_balance";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("trial_name");
                double debit = rs.getDouble("trial_debit");
                double credit = rs.getDouble("trial_credit");

                trialBalanceData.add(new TrialBalance(name, debit, credit));
            }

            trialBalanceTable.setItems(trialBalanceData);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
