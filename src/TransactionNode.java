//LinkedList
public class TransactionNode {
    Transaction data;
    TransactionNode next;

    //Tracking transaction history in the form of LinkedList
    public TransactionNode(Transaction data, TransactionNode next) {
        this.data = data;
        this.next = next;
    }
}
