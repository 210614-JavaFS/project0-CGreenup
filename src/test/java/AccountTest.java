import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.services.Account;
import com.revature.services.NegativeAccountException;


public class AccountTest {

	public static Account acc;
	
	public static String name;
	public static String accountType;
	
	public static double balance;
	
	public AccountTest() {
		name = "John Smith";
		accountType = "Checking";
		balance = 2000;
		acc = new Account(name, accountType, balance);
	}
	
	
	
	
	@BeforeEach
	public void initialize() {
		name = "John Smith";
		accountType = "Checking";
		balance = 2000;
		acc = new Account(name, accountType, balance);
	}
	
	
	
	@Test
	public void testWithdrawal() {
		assertThrows(NegativeAccountException.class, ()->{
			acc.changeBalance(-2500);
			});
	}
	
	@Test
	public void testingTest() {
		assertTrue(true);
	}
	
}
