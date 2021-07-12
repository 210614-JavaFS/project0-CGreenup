import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.data.AccountDAOImplement;
import com.revature.data.ProfileDAOImplement;
import com.revature.models.Account;
import com.revature.models.AccountTypes;
import com.revature.models.Profile;

public class DAOTest {

	public static AccountDAOImplement accImpl = new AccountDAOImplement();
	public static ProfileDAOImplement impl = new ProfileDAOImplement();
	public static Profile testProfile;
	public static Account testAccount;
	Logger log = LoggerFactory.getLogger(DAOTest.class);
	
	@BeforeAll
	public static void init() {
		testProfile = new Profile("John", "Smith", "a", "a", AccountTypes.ADMIN);
		
		if(!(impl.usernameTaken(testProfile.getUsername()))) {
			impl.addProfile(testProfile);
		}
		
		testAccount = new Account();
		testAccount.setAccountName("Test Bank Account");
		testAccount.setAccountType("Checking");
		testAccount.setOwner(testProfile);
		
		if(accImpl.findId(testAccount) == 0) {
			accImpl.addAccount(testAccount);
		}
		testAccount.setId(accImpl.findId(testAccount));
	}
	
	@Test
	public void changeProfileTest() {
		testProfile.setAccountType(AccountTypes.USER);
		testProfile.setPassword("passw");
		impl.updateProfile(testProfile, testProfile.getUsername());
	}
	
	@Test
	public void findAccountTest() {
		Account acc = accImpl.find(1);
		if(acc != null)
			log.info(acc.toString());
	}
	
	@Test
	public void updateAccountTest() {
		testAccount.setAccountName("Test Account");
		assertTrue(accImpl.updateAccount(testAccount, testAccount.getId()));
		
	}
	
	
}
