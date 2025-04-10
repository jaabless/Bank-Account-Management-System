import java.time.LocalDate;

public class FixedDepositAccount extends BankAccount {
    private LocalDate maturityDate;

    public FixedDepositAccount(String accountNumber, String accountHolder, double balance, LocalDate maturityDate) {
        super(accountNumber, accountHolder, balance);
        this.maturityDate = maturityDate;
    }


    @Override
    public String deposit(double amount) {
        return "No deposits allowed after account creation.";
    }

    @Override
    public String withdraw(double amount) {
        if (LocalDate.now().isAfter(maturityDate)) {
            if (amount <= balance) {
                balance -= amount;
                addTransaction("Withdraw", amount);
                return "Withdrawal of $" + amount + " successful.";
            } else {
                return "Insufficient funds. Available balance: $" + balance;
            }
        } else {
            return "Cannot withdraw before maturity date: " + maturityDate;
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
