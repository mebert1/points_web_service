package web;

import java.util.Map;

public interface UserServiceInterface {

    boolean createUser ();
    int getBalance(int user_id);
    int processTransaction(Transaction transaction, int user_id);
    Payment makePayment(int amount, int user_id);
    Map<String,Integer> getPayer_list(int user_id);
}
