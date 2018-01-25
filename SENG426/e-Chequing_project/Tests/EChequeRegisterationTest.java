import org.junit.Test;
import static org.junit.Assert.*;

public class EChequeRegisterationTest {
	
   String bankName = "CIBC";
   String bankAddress = "http://www.cibc.com";
   String accountNumber = "123456789";
   String clientName = "Tom Sawyer";
   String eWalletLocation = "./EWallet";
   int username = 192837465;
   int password = 1199229933;
   
   EChequeRegisteration eChequeReg = new EChequeRegisteration();

   @Test
   public void testSetBankName() {	  
	   eChequeReg.setBankName(bankName);
	   assertEquals(bankName, eChequeReg.getBankName());
   }
   
   @Test
   public void testSetBankAddress() {	  
	   eChequeReg.setBankAddress(bankAddress);
	   assertEquals(bankAddress, eChequeReg.getBankAddress());
   }
   
   @Test
   public void testSetAccountNumber() {	  
	   eChequeReg.setAccountNumber(accountNumber);
	   assertEquals(accountNumber, eChequeReg.getAccountNumber());
   }
   
   @Test
   public void testSetClientName() {	  
	   eChequeReg.setClientName(clientName);
	   assertEquals(clientName, eChequeReg.getClientName());
   }
   
   @Test
   public void testSetEWalletLoaction() {	  
	   eChequeReg.setEWalletLoaction(eWalletLocation);
	   assertEquals(eWalletLocation, eChequeReg.getEWalletLoaction());
   }
   
   @Test
   public void testSetUsername() {	  
	   eChequeReg.setUsername(username);
	   assertEquals(username, eChequeReg.getUsername());
   }
   
   @Test
   public void testSetPasword() {	  
	   eChequeReg.setPasword(password);
	   assertEquals(password, eChequeReg.getPasword());
   }
}