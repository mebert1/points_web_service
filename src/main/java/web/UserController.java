package web;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController implements UserServiceInterface {

    UserService user_service = new UserService();

    @GetMapping("/newUser")
    public boolean createUser() {
        return user_service.createUser();
    }

    @GetMapping("/balance")
    public int getBalance(@RequestParam(value = "user", required = true, defaultValue = "0") int user_id) {
        return user_service.getBalance(user_id);
    }

    @PostMapping("/transaction")
    public int processTransaction(@RequestBody Transaction transaction, int user_id) {
        return user_service.processTransaction(transaction, user_id);
    }

    @PostMapping("/pay")
    public Payment makePayment(@RequestBody int amount, int user_id) {
        return user_service.makePayment(amount, user_id);
    }

}
