import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BankManagement extends Application {
    private final Map<String, BankAccount> accounts = new HashMap<>(); //create and store accounts temporay

    @Override
    public void start(Stage primaryStage) {
        // UI Elements
        Label title = new Label("Bank Account Management");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Button viewAccountBtn = new Button("View Account"); //Display View Account button
        Button createAccountBtn = new Button("Create Account");//Display View Account button
        Button depositBtn = new Button("Deposit");//Display Deposit button
        Button withdrawBtn = new Button("Withdraw");//Display Withdraw button
        Button viewTransactionsBtn = new Button("View Transactions");//Display View Past Transactions button

        // Button Styles
        String buttonStyle = "-fx-background-color: #1C74E9; -fx-text-fill: white; -fx-font-size: 14px;";
        viewAccountBtn.setStyle(buttonStyle);
        createAccountBtn.setStyle(buttonStyle);
        depositBtn.setStyle(buttonStyle);
        withdrawBtn.setStyle(buttonStyle);
        viewTransactionsBtn.setStyle(buttonStyle);

        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(20);// Horizontal space between columns
        buttonGrid.setVgap(20); // Vertical space between rows
        buttonGrid.setAlignment(Pos.CENTER); // Center the grid

        // Add buttons to 2 rows and 2 columns
        buttonGrid.add(viewAccountBtn, 0, 0);     // Column 0, Row 0
        buttonGrid.add(createAccountBtn, 1, 0);   // Column 1, Row 0 (this is changed)
        buttonGrid.add(depositBtn, 0, 1);         // Column 0, Row 1
        buttonGrid.add(withdrawBtn, 1, 1);        // Column 1, Row 1
        buttonGrid.add(viewTransactionsBtn, 0, 2); // Column 1, Row 1

        // Button Actions
        viewAccountBtn.setOnAction(e -> viewAccount());
        createAccountBtn.setOnAction(e -> createAccount());
        depositBtn.setOnAction(e -> initiateDeposit());
        withdrawBtn.setOnAction(e -> withdrawPopup());
        viewTransactionsBtn.setOnAction(e -> viewTransactions());

        VBox layout = new VBox(15);//set spaces between items to 10
        layout.setPadding(new Insets(30));//set padding to 20//set padding to 20
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(title, buttonGrid);//confirm what to display on UI in order they should appear
        layout.setStyle("-fx-background-color: #fff;");

        Scene scene = new Scene(layout, 450, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bank Management System");
        primaryStage.show();
    }

    //To view account details
    private void viewAccount() {
        //Create UI using JavaFX
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Account Details");

        Label accountLabel = new Label("Account Number:");//Label for Account Label
        TextField accountField = new TextField();//Textfield to accept user account number
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-background-color: #1C74E9; -fx-text-fill: white; -fx-font-size: 14px;");

        confirmBtn.setOnAction(e -> {
            String accountNum = accountField.getText();
            BankAccount account = accounts.get(accountNum);
            if (account != null) {
                showAlert(account.printAccountDetails());//Print account Details
                popup.close();
            } else {
                showAlert("Account not found.");
            }
        });

        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(15));
        popupLayout.getChildren().addAll(accountLabel, accountField, confirmBtn);

        popup.setScene(new Scene(popupLayout, 300, 200));
        popup.showAndWait();
    }

    //to create an account
    private void createAccount() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Create Account");

        Label accountNumberLabel = new Label("Account Number:");
        TextField accountNumberField = new TextField();//Get Account Number
        Label accountName = new Label("Enter your Name:");
        TextField accountNameField = new TextField();//Get preferred Account Name
        Label depositAmount = new Label("Amount to Deposit:");
        TextField depositAmountField = new TextField();//Get initial Deposit Amount
        ComboBox<String> accountTypeComboBox = new ComboBox<>();//Create a dropdown List for various account types
        accountTypeComboBox.getItems().addAll("Savings Account", "Current Account", "Fixed Deposit Account");
        accountTypeComboBox.setValue("Savings Account");
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-background-color: #1C74E9; -fx-text-fill: white; -fx-font-size: 14px;");

        confirmBtn.setOnAction(e -> {
            try {
                String preferredAccountNumber = accountNumberField.getText();//Pass account Number
                String preferredAccountName = accountNameField.getText();//Pass account name
                double amount = Double.parseDouble(depositAmountField.getText());//Pass initial deposit amount
                String type = accountTypeComboBox.getValue();//Get Selected Account Type

                BankAccount account;
                switch (type) {
                    case "Savings Account":
                        account = new SavingsAccount(preferredAccountNumber, preferredAccountName, amount);//create savings account
                        break;
                    case "Current Account":
                        account = new CurrentAccount(preferredAccountNumber, preferredAccountName, amount);//create current account
                        break;
                    case "Fixed Deposit Account":
                        account = new FixedDepositAccount(preferredAccountNumber, preferredAccountName, amount, LocalDate.now().plusDays(30));//create current account
                        break;
                    default:
                        showAlert("Invalid account type selected.");
                        return;
                }

                accounts.put(preferredAccountNumber, account);//Account Created
                showAlert("Account created successfully.");
                popup.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid input. Please enter a valid number.");
            }
        });

        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(15));
        popupLayout.getChildren().addAll(accountNumberLabel, accountNumberField, accountName, accountNameField, depositAmount, depositAmountField, accountTypeComboBox, confirmBtn);

        popup.setScene(new Scene(popupLayout, 300, 300));
        popup.showAndWait();
    }

    private void initiateDeposit() {
        transactionPopup("Deposit");
    }

    private void withdrawPopup() {
        transactionPopup("Withdraw");
    }

    private void transactionPopup(String type) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(type);

        Label accountLabel = new Label("Account Number:");
        TextField accountField = new TextField();
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-background-color: #1C74E9; -fx-text-fill: white; -fx-font-size: 14px;");

        confirmBtn.setOnAction(e -> {
            try {
                String accNum = accountField.getText();
                double amount = Double.parseDouble(amountField.getText());
                BankAccount account = accounts.get(accNum);
                if (account != null) {
                    String message = type.equals("Deposit") ? account.deposit(amount) : account.withdraw(amount);
                    showAlert(message);
                    popup.close();
                } else {
                    showAlert("Account not found.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid input. Please enter a valid number.");
            }
        });

        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(15));
        popupLayout.getChildren().addAll(accountLabel, accountField, amountLabel, amountField, confirmBtn);
        popup.setScene(new Scene(popupLayout, 300, 200));
        popup.showAndWait();
    }

    private void viewTransactions() {
        Stage popup = new Stage();
        popup.setTitle("Transaction History");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label accountLabel = new Label("Account Number:");
        TextField accountField = new TextField();
        Label transactionNumberLabel = new Label("Enter number of transactions to show:");
        TextField transactionNumber = new TextField();
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setStyle("-fx-background-color: #1C74E9; -fx-text-fill: white; -fx-font-size: 14px;");

        confirmBtn.setOnAction(e -> {
            try {
                String accNum = accountField.getText();
                int num = Integer.parseInt(transactionNumber.getText());
                BankAccount account = accounts.get(accNum);
                if (account != null) {
                    showAlert(account.printTransactionHistory(num));
                    popup.close();
                } else {
                    showAlert("Account not found.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid input. Please enter valid numbers.");
            }
        });

        layout.getChildren().addAll(accountLabel, accountField, transactionNumberLabel, transactionNumber, confirmBtn);
        popup.setScene(new Scene(layout, 350, 200));
        popup.show();
    }

    //method to display alerts from various account types
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
