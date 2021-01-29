package web;

public interface UserServiceInterface {

    public boolean createUser ();
    public int getBalance(int user_id);
    public int processTransaction(Transaction transaction, int user_id);
    public Payment makePayment(int amount, int user_id);
}
