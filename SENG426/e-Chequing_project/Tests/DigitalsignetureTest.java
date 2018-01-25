import org.junit.Test;
import static org.junit.Assert.*;

import java.security.*;

public class DigitalsignetureTest {

	Digitalsigneture digitalSig = new Digitalsigneture();
	
	String mess = "hello world this is a message";
	
	byte[] sig;
	
	@Test
	public void testSigneture() throws Exception {
		SecureRandom SecRandom= new SecureRandom();
	    KeyPairGenerator KeyGenerator= KeyPairGenerator.getInstance("RSA");
	    KeyGenerator.initialize(1024, SecRandom);
	    KeyPair RSAKeys = KeyGenerator.generateKeyPair();
	    
	    sig = digitalSig.signeture(mess, RSAKeys.getPrivate());
	    
	    assertTrue(digitalSig.verifySignature(sig, mess, RSAKeys.getPublic()));
	}
	
	@Test
	public void testSignetureFalse() throws Exception {
		SecureRandom SecRandom= new SecureRandom();
	    KeyPairGenerator KeyGenerator= KeyPairGenerator.getInstance("RSA");
	    KeyGenerator.initialize(1024, SecRandom);
	    KeyPair RSAKeys = KeyGenerator.generateKeyPair();
	    
	    sig = digitalSig.signeture(mess, RSAKeys.getPrivate());
	    
	    assertFalse(digitalSig.verifySignature("e04fd020ea3a6910a2d808002b30309de04fd020ea3a6910a2d808002b30309de04fd020ea3a6910a2d808002b30309de04fd020ea3a6910a2d808002b30309d".getBytes(), mess, RSAKeys.getPublic()));
	}
}