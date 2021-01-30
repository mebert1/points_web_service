package web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    /* ----- Unit tests ----- */

    Transaction trans1 = new Transaction("DANNON", 300, new Date());
    Transaction trans2 = new Transaction("UNILEVER", 200, new Date());

    @Test
    void createUserTest() {
        UserController controller = new UserController();
        boolean response = controller.createUser();
        assertTrue(response);
    }

    @Test
    void getEmptyBalanceTest() {
        UserController controller = new UserController();
        controller.createUser();
        int response = controller.getBalance(0);
        assertEquals(response, 0);
    }

    @Test
    void processTransactionTest() {
        UserController controller = new UserController();
        controller.createUser();
        int result = controller.processTransaction(trans1, 0);
        assertEquals(result, 300);
        result = controller.processTransaction(trans2, 0);
        assertEquals(result, 500);
    }

    @Test
    void getPayer_listTest() {
        // Create reference object
        Map<String,Integer> payer_list = new HashMap<>();
        payer_list.put(trans1.getPayer_name(), trans1.getPoints());
        payer_list.put(trans2.getPayer_name(), trans2.getPoints());

        // Set up controller
        UserController controller = new UserController();
        controller.createUser();
        controller.processTransaction(trans1, 0);
        controller.processTransaction(trans2, 0);
        Map<String,Integer> result = controller.getPayer_list(0);

        // Compare results
        assertEquals(payer_list.get(trans1.getPayer_name()), result.get(trans1.getPayer_name()));
        assertEquals(payer_list.get(trans2.getPayer_name()), result.get(trans2.getPayer_name()));
    }

    @Test
    void makePaymentTest() {
        // Set up controller
        UserController controller = new UserController();
        controller.createUser();

        // Collect points
        controller.processTransaction(trans1, 0);
        controller.processTransaction(trans2, 0);

        // Spend points
        Payment payment = new Payment();
        payment.addTransaction(new Transaction(
                trans1.getPayer_name(),
                trans1.getPoints() * (-1),
                new Date()
        ));
        payment.addTransaction(new Transaction(
                trans2.getPayer_name(),
                trans2.getPoints() * (-1),
                new Date()
        ));
        Payment result = controller.makePayment(500, 0);

        // Compare results
        assertEquals(result.getTransactions().get(0).getPayer_name(), payment.getTransactions().get(0).getPayer_name());
        assertEquals(result.getTransactions().get(1).getPayer_name(), payment.getTransactions().get(1).getPayer_name());
        assertEquals(result.getTransactions().get(0).getPoints(), payment.getTransactions().get(0).getPoints());
        assertEquals(result.getTransactions().get(1).getPoints(), payment.getTransactions().get(1).getPoints());
        assertEquals(result.getTransactions().get(0).getDate().toString(), payment.getTransactions().get(0).getDate().toString());
        assertEquals(result.getTransactions().get(1).getDate().toString(), payment.getTransactions().get(1).getDate().toString());
    }

    @Test
    void fullFlowTest() {
        // Set up controller
        UserController controller = new UserController();
        assertTrue(controller.createUser());

        // Collect points
        int balance;
        balance = controller.processTransaction(trans1, 0);
        assertEquals(balance, 300);
        balance = controller.processTransaction(trans2, 0);
        assertEquals(balance, 500);

        // Spend points
        Payment payment = new Payment();
        payment.addTransaction(new Transaction(
                trans1.getPayer_name(),
                trans1.getPoints() * (-1),
                new Date()
        ));
        payment.addTransaction(new Transaction(
                trans2.getPayer_name(),
                trans2.getPoints() * (-1),
                new Date()
        ));
        assertEquals(controller.getBalance(0), 500);
        Payment result = controller.makePayment(500, 0);

        // Compare results
        assertEquals(result.getTransactions().get(0).getPayer_name(), payment.getTransactions().get(0).getPayer_name());
        assertEquals(result.getTransactions().get(1).getPayer_name(), payment.getTransactions().get(1).getPayer_name());
        assertEquals(result.getTransactions().get(0).getPoints(), payment.getTransactions().get(0).getPoints());
        assertEquals(result.getTransactions().get(1).getPoints(), payment.getTransactions().get(1).getPoints());
        assertEquals(result.getTransactions().get(0).getDate().toString(), payment.getTransactions().get(0).getDate().toString());
        assertEquals(result.getTransactions().get(1).getDate().toString(), payment.getTransactions().get(1).getDate().toString());
        assertEquals(controller.getBalance(0), 0);
    }

    /* ----- Integration tests ----- */

    @Autowired
    private MockMvc mvc;

    @Test
    void emptyBalance() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/newUser");
        mvc.perform(request);
        RequestBuilder request2 = MockMvcRequestBuilders.get("/balance");
        MvcResult result = mvc.perform(request2).andReturn();
        assertEquals(Integer.parseInt(result.getResponse().getContentAsString()), 0);
    }
}
