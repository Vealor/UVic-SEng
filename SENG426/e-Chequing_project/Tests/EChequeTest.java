package eCheque;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import junit.framework.*;

public class EChequeTest extends TestCase{

	String result = "asdf1234$";
	
	private ECheque input;
	@Before
	public void setUp(){
		input = new ECheque();
	}
	
	@Test
	public void testsetaccountholder(){
		input.setaccountholder(result);
		assertEquals(input.getaccountholder(),result);
	}
	@Test
	public void testsetaccountNumber(){
		input.setaccountNumber(result);
		assertEquals(input.getaccountNumber(),result);
	}
	@Test
	public void testsetbankname(){
		input.setbankname(result);
		assertEquals(input.getbankname(),result);
	}
	@Test
	public void testsetpayToOrderOf(){
		input.setpayToOrderOf(result);
		assertEquals(input.getpayToOrderOf(),result);
	}
	@Test
	public void testsetamountofMony(){
		input.setamountofMony(result);
		assertEquals(input.getMoney(),result);
	}
	@Test
	public void testsetcurrencytype(){
		input.setcurrencytype(result);
		assertEquals(input.getcurrencytype(),result);
	}
	@Test
	public void testsetchequeNumber(){
		input.setchequeNumber(result);
		assertEquals(input.getchequeNumber(),result);
	}
	@Test
	public void testsetguaranteed(){
		input.setguaranteed(true);
		assertEquals(input.getguaranteed(),true);
	}
	@Test
	public void testsetearnday(){
		input.setearnday(result);
		assertEquals(input.getearnday(),result);
	}
	@Test
	public void testsetbanksignature(){
		byte[] b = result.getBytes();
		input.setbanksignature(b);
		assertEquals(input.getbanksignature(),b);
	}
	@Test
	public void testsetdrawersiganure(){
		byte[] b = result.getBytes();
		input.setdrawersiganure(b);
		assertEquals(input.getdrawersiganure(),b);
	}
	@Test
	public void testECheque(){
		assertNotNull(input);
	}
	@After
	public void teardown(){
		input = null;
	}
	
}
