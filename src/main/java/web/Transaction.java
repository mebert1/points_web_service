package web;

import java.util.Date;

public class Transaction {

    private final String payer_name;
    private final int points;
    private final Date date;

    Transaction(String payer_name, int points, Date date) {
        this.payer_name = payer_name;
        this.points = points;
        this.date = date;
    }

    public String getPayer_name() {
        return payer_name;
    }

    public int getPoints() {
        return points;
    }

    public Date getDate() {
        return date;
    }

}
