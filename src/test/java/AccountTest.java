import static org.junit.jupiter.api.Assertions.assertEquals;
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
	public void testOverdrawing() {
		assertThrows(NegativeAccountException.class, ()->{
			acc.changeBalance(-acc.getBalance() - 201.678);
			});
	}
	
	@Test
	public void testWithdrawingAndRounding() {
		double prevBalance = acc.getBalance();
		try {
			acc.changeBalance(500);
			acc.changeBalance(-200.678435345);
			assertEquals(prevBalance + 500 - 200.68, acc.getBalance());
		} catch (NegativeAccountException e) {
			e.printStackTrace();
		} finally {
			assertEquals(prevBalance + 500 - 200.68, acc.getBalance());
		}
	}
	
	@Test
	public void testingTest() {
		assertTrue(true);
	}
	
}
