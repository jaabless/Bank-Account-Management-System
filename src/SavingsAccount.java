//Savings Account
public class SavingsAccount extends BankAccount {
    private static final double MIN_BALANCE = 100.0;

    public SavingsAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }


    @Override
    public String deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposit", amount);
            return "Deposit of $" + amount + " successful.";
        }
        return "Amount must be greater than 0";
    }

    //Override withdrawal method, implementing rules for
    //withdrawal in Savings Account
    @Override
    public String withdraw(double amount) {
        if (balance - amount >= MIN_BALANCE) {//overdraft not allowed
            balance -= amount;
            addTransaction("Withdraw", amount);
            return "Withdrawal of $" + amount + " successful.";
        } else {
            return "Insufficient balance. Must maintain minimum of $" + MIN_BALANCE;
        }
    }

    //Print out Account Details - Account Number,Name and Balance
    @Override
    public String printAccountDetails() {
        if (accountNumber == null || !accountNumber.matches("\\d+")) {
            return "Error: Account number must be a non-null numeric value.";
        }

        return "Account Number " + accountNumber +
                " | Account Name: " + accountHolder +
                " | Account Balance: $" + balance;
    }


}
