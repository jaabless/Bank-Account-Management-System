
import java.time.LocalDate;

//abstract class
public abstract class BankAccount implements BankOperations {
    //attributes
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;
    protected TransactionNode transactionHistory;

    // Constructor
    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.transactionHistory = null;
    }

    //Make a transaction
    protected void addTransaction(String type, double amount) {
        Transaction transaction = new Transaction(type, amount, LocalDate.now());
        //to view recent activity or transaction
        transactionHistory = new TransactionNode(transaction, transactionHistory);
    }

    //Print Transaction History
    public String printTransactionHistory(int n) {
        //applying LinkedList
        TransactionNode current = transactionHistory;
        int count = 0;
        String message ="";
        while (current != null && count < n) {
            message += current.data.toString() + "\n";
            System.out.println(current.data);
            current = current.next;
            count++;
        }
        return "Recent Transactions for Account Number: "+accountNumber + "\n" + message;
    }

    public double getBalance() {
        return balance;
    }

    public abstract String printAccountDetails();
}
