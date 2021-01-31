package web;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController implements UserServiceInterface {

    UserService user_service = new UserService();

    @GetMapping("/newUser")
    public boolean createUser() {
        System.out.println("New user generated!");
        return user_service.createUser();
    }

    @GetMapping("/balance")
    public int getBalance(@RequestParam(value = "user", required = true, defaultValue = "0") int user_id) {
        return user_service.getBalance(user_id);
    }

    @GetMapping("/payer_list")
    public Map<String,Integer> getPayer_list(@RequestParam(value = "user", required = true, defaultValue = "0") int user_id) {
        return user_service.getPayer_list(user_id);
    }

    @PostMapping(
            value = "/transaction/{id}", consumes = "application/JSON", produces = "application/JSON")
    public int processTransaction(@RequestBody Transaction transaction, @PathVariable int id) {
        System.out.println("Request received!");
        System.out.println("Transaction name: " + transaction.getPayer_name());
        System.out.println("Transaction points: " + transaction.getPoints());
        System.out.println("User id: " + id);
        return user_service.processTransaction(transaction, id);
    }

    @GetMapping("/pay")
    public Payment makePayment(@RequestParam(value = "amount", defaultValue = "0") int amount, @RequestParam(value = "user", defaultValue= "0") int user_id) {
        return user_service.makePayment(amount, user_id);
    }
}
