package web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

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
    void makePaymentTest() {
        UserController controller = new UserController();
        // Create User
        controller.createUser();
        // Collect points
        controller.processTransaction(trans1, 0);
        controller.processTransaction(trans2, 0);
        // Spend points
        Payment payment = new Payment();
        payment.addTransaction(trans1);
        payment.addTransaction(trans2);
        Payment result = controller.makePayment(500, 0);
        // Compare results
        assertEquals(result.getTransactions().get(0).getPayer_name(), payment.getTransactions().get(0).getPayer_name());
        assertEquals(result.getTransactions().get(1).getPayer_name(), payment.getTransactions().get(1).getPayer_name());
        assertEquals(result.getTransactions().get(0).getPoints(), payment.getTransactions().get(0).getPoints());
        assertEquals(result.getTransactions().get(1).getPoints(), payment.getTransactions().get(1).getPoints());
        assertEquals(result.getTransactions().get(0).getDate().toString(), payment.getTransactions().get(0).getDate().toString());
        assertEquals(result.getTransactions().get(1).getDate().toString(), payment.getTransactions().get(1).getDate().toString());
    }

    /* ----- Integration tests ----- */
    @Autowired
    private MockMvc mvc;

    @Test
    void emptyBalance() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/newUser");
        mvc.perform(request);
        RequestBuilder request2 = MockMvcRequestBuilders.get("/balance");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(Integer.parseInt(result.getResponse().getContentAsString()), 0);
    }
}
