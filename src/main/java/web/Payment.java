package web;

import java.util.LinkedList;
import java.util.List;

public class Payment {

    private List<Transaction> transactions = new LinkedList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
