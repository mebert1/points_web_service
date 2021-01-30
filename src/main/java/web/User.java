package web;

import java.util.*;

public class User {

    private final int id;
    private int balance = 0;
    private List<Transaction> available_points = new LinkedList<>();

    private Map<String, Integer> payer_list = new HashMap<>();

    User(int id) {
        this.id = id;
    }

    /* ----------------- public methods ----------------- */

    public void addPoints(Transaction transaction) {
        available_points.add(transaction);
        updatePayer_list(transaction);
        updateBalance(transaction.getPoints());
    }

    public Payment makePayment(int amount_due) {
        Payment payment = new Payment();
        do {
            Transaction oldest_points = findOldestPoints();
            // case 1: User needs all points from the transaction
            if (oldest_points.getPoints() <= amount_due) {

                Transaction required_points = new Transaction(
                        oldest_points.getPayer_name(),
                        oldest_points.getPoints() * (-1),
                        new Date());

                payment.addTransaction(required_points);
                updateBalance(required_points.getPoints());
                available_points.remove(oldest_points);
                updatePayer_list(required_points);

                amount_due -= oldest_points.getPoints();
            } else {
                // case 2: User does not need all points from the transaction -> split points to keep rest
                Transaction required_points = new Transaction(
                        oldest_points.getPayer_name(),
                        (oldest_points.getPoints() - amount_due) * (-1),
                        new Date());
                Transaction unused_points = new Transaction(
                        oldest_points.getPayer_name(),
                        amount_due - oldest_points.getPoints(),
                        oldest_points.getDate());

                payment.addTransaction(required_points);
                updateBalance(required_points.getPoints());
                available_points.remove(oldest_points);
                available_points.add(unused_points);
                updatePayer_list(required_points);

                amount_due -= required_points.getPoints();
            }
        } while (amount_due > 0);
        return payment;
    }

    /* ----------------- private/internal "helping" methods ----------------- */

    private void updatePayer_list(Transaction transaction) {
        String payer_name = transaction.getPayer_name();
        Integer new_points = transaction.getPoints();
        if(payer_list.containsKey(payer_name)) {
            Integer balance = payer_list.get(payer_name);
            payer_list.put(payer_name, balance + new_points);
        } else {
            payer_list.put(payer_name, new_points);
        }
    }

    private void updateBalance(int amount) {
        balance += amount;
    }

    private Transaction findOldestPoints() {
        Transaction oldest = new Transaction("", 0, new Date());
        for(Transaction transaction : available_points) {
            if(transaction.getDate().before(oldest.getDate())) {
                oldest = transaction;
            }
        }
        return oldest;
    }


    /* ----------------- Getters & Setters ----------------- */

    public int getBalance() {
        return balance;
    }

    public Map<String, Integer> getPayer_list() {
        return payer_list;
    }

}
