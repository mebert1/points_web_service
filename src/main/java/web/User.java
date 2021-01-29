package web;

import java.util.*;

public class User {

    private final int id;
    private int balance = 0;
    private List<Transaction> transactions = new LinkedList<Transaction>();
    private Map<String, Integer> payer_list = new HashMap<String, Integer>();

    User (int id, int balance, List<Transaction> transactions, Map<String, Integer> payer_list) {
        this.balance = balance;
        this.transactions = transactions;
        this.payer_list = payer_list;
        this.id = id;
    }

    User(int id) {
        this.id = id;
    }

    /* ----------------- public methods ----------------- */

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        updatePayer_list(transaction);
        updateBalance(transaction.getPoints());
    }

    /* ----------------- private/internal "helping" methods ----------------- */

    private void updatePayer_list(Transaction transaction) {
        String payer_name = transaction.getPayer_name();
        Integer new_points = transaction.getPoints();
        Integer balance = 0;
        if(payer_list.containsKey(payer_name)) {
            balance = payer_list.get(payer_name);
            payer_list.put(payer_name, balance + new_points);

        } else {
            payer_list.put(payer_name, new_points);
        }
    }

    private void updateBalance(int amount) {
        balance += amount;
    }

    private Transaction findOldestTransaction() {
        Transaction oldest = new Transaction("", 0, new Date());
        for(Transaction transaction : transactions) {
            if(transaction.getDate().before(oldest.getDate())) {
                oldest = transaction;
            }
        }
        return oldest;
    }


    /* ----------------- Getters & Setters ----------------- */

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

}
