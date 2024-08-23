package accountingsystem.tracking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;

public class ExpenseTrackingController {

    @FXML
    private Button AddButton;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private Button RemoveButton;

    @FXML
    private TableColumn<Expense, Integer> TableColumnID;

    @FXML
    private TableColumn<Expense, LocalDate> TableColumnDate;

    @FXML
    private TableColumn<Expense, String> TableColumnCategory;

    @FXML
    private TableColumn<Expense, Double> TableColumnAmount;

    @FXML
    private TableView<Expense> TableExpense;

    @FXML
    private TextField TextFAmount;

    @FXML
    private TextField TextFCategory;

    @FXML
    private TextField TextFTotal;

    private ObservableList<Expense> expenseList;
    private int nextId = 1;
    private static final String PESO_SIGN = "₱";
    private static final DecimalFormat DECIMAL_FORMAT;

    static {
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator(',');
    DECIMAL_FORMAT = new DecimalFormat(PESO_SIGN + "#,##0.00", symbols);
}

    public class Expense {
        private int id;
        private LocalDate date;
        private String category;
        private double amount;

        public Expense(int id, LocalDate date, String category, double amount) {
            this.id = id;
            this.date = date;
            this.category = category;
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public LocalDate getDate() {
            return date;
        }

        public String getCategory() {
            return category;
        }

        public double getAmount() {
            return amount;
        }
    }

    @FXML
    public void initialize() {
        expenseList = FXCollections.observableArrayList();

        TableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumnAmount.setCellFactory(column -> new TableCell<Expense, Double>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(formatAmount(amount));
                }
            }
        });

        TableExpense.setItems(expenseList);

        loadExpensesFromDatabase();

        TextFAmount.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                try {
                    String formattedText = formatAmount(parseAmount(newText));
                    if (!newText.equals(formattedText)) {
                        TextFAmount.setText(formattedText);
                        TextFAmount.positionCaret(formattedText.length());
                    }
                } catch (NumberFormatException e) {
                    
                }
            }
        });
    }

    @FXML
    void handleAddButton(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        double amount;
        try {
            amount = parseAmount(TextFAmount.getText());
        } catch (NumberFormatException e) {
            return;
        }

        Expense expense = new Expense(nextId++, DatePicker.getValue(), TextFCategory.getText(), amount);
        expenseList.add(expense);

        saveExpenseToDatabase(expense);

        updateTotal();

        DatePicker.setValue(null);
        TextFCategory.clear();
        TextFAmount.clear();
    }

    @FXML
    void handleRemoveButton(ActionEvent event) {
        Expense selectedExpense = TableExpense.getSelectionModel().getSelectedItem();
        if (selectedExpense != null) {
            expenseList.remove(selectedExpense);

            removeExpenseFromDatabase(selectedExpense);

            if (expenseList.isEmpty()) {
                nextId = 1;
            } else {
                for (int i = 0; i < expenseList.size(); i++) {
                    expenseList.get(i).setId(i + 1);
                }
                nextId = expenseList.size() + 1;
            }
            updateTotal();
        }
    }
    
    @FXML
    private void handleDatePickerAction(ActionEvent event) {
    
    LocalDate selectedDate = DatePicker.getValue();
    System.out.println("Date selected: " + selectedDate);
}
    
    
    private boolean validateInputs() {
        if (DatePicker.getValue() == null) {
            showAlert("Validation Error", "Please select a date.");
            return false;
        }

        if (TextFCategory.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a category.");
            return false;
        }

        String amountText = TextFAmount.getText().replace(PESO_SIGN, "").replace(",", "").trim();
        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showAlert("Validation Error", "Amount must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid number for the amount.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateTotal() {
        double total = expenseList.stream().mapToDouble(Expense::getAmount).sum();
        TextFTotal.setText(formatAmount(total));
    }

    private String formatAmount(double amount) {
        return DECIMAL_FORMAT.format(amount);
    }

    private double parseAmount(String amountText) throws NumberFormatException {
        return Double.parseDouble(amountText.replace(PESO_SIGN, "").replace(",", "").trim());
    }

    private Connection connectToDatabase() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/accountingsystem"; 
        String username = "root"; 
        String password = ""; 

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            showAlert("Database Connection Error", "Failed to connect to the database: " + e.getMessage());
        }

        return connection;
    }

    private void saveExpenseToDatabase(Expense expense) {
        String query = "INSERT INTO expenses (date, category, amount) VALUES (?, ?, ?)";

        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, java.sql.Date.valueOf(expense.getDate()));
            statement.setString(2, expense.getCategory());
            statement.setDouble(3, expense.getAmount());

            statement.executeUpdate();
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to save the expense: " + e.getMessage());
        }
    }

    private void loadExpensesFromDatabase() {
        String query = "SELECT * FROM expenses";

        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");

                Expense expense = new Expense(id, date, category, amount);
                expenseList.add(expense);
            }

            nextId = expenseList.size() + 1;

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load expenses: " + e.getMessage());
        }
    }

    private void removeExpenseFromDatabase(Expense expense) {
        String query = "DELETE FROM expenses WHERE id = ?";

        try (Connection connection = connectToDatabase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, expense.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            showAlert("Database Error", "Failed to remove the expense: " + e.getMessage());
        }
    }
}
