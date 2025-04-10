import java.time.LocalDate;

//Transaction Class
public class Transaction {
    private String type;
    private double amount;
    private LocalDate date;

    //Transaction constructor
    public Transaction(String type, double amount, LocalDate date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    //Print transaction
    public String toString() {
        return date + " - " + type + ": " + amount;//2025-04-09 - Deposit: 200.0
    }
}
