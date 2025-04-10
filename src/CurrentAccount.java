public class CurrentAccount extends BankAccount {
    private static final double OVERDRAFT_LIMIT = 500.0;//overdraft limit = $500

    public CurrentAccount(String accountNumber, String accountHolder, double balance) {
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

    @Override
    public String withdraw(double amount) {
        if (balance + OVERDRAFT_LIMIT >= amount) {
            balance -= amount;
            addTransaction("Withdraw", amount);
            return "Withdrawal of $" + amount + " successful.";
        } else {
            return  "Withdrawal exceeds overdraft limit.";
        }
    }

    @Override
    public String printAccountDetails() {
        if (accountNumber == null || !accountNumber.matches("\\d+")) {
            return "Error: Account number must be a non-null numeric value.";
        }

        return "Savings Account:\nAccount Number " + accountNumber +
                " | Account : " + accountHolder +
                " | Account Balance: $" + balance;
    }
}
