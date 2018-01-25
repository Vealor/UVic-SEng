import org.junit.Test;
import static org.junit.Assert.*;

import java.security.*;

public class DigitalCertificateTest {

	DigitalCertificate digitalCert = new DigitalCertificate();

	/*public void testSetBankName() {	  
		   eChequeReg.setBankName(bankName);
		   assertEquals(bankName, eChequeReg.getBankName());
	}*/
	
	String Bill = "Bill";
	String John = "John";
	String serialNum = "1";
	String dateFrom = "01-02-2903";
	@Test
	public void testSetHolderName(){
		digitalCert.setHolderName(John);
		assertEquals(John, digitalCert.getHolderName());
	}
	
	@Test
	public void testSetSubject(){
		digitalCert.setSubject(Bill);
		assertEquals(Bill, digitalCert.getSubject());
	}
	
	@Test
	public void testSetIssuer(){
		digitalCert.setIssuer(John);
		assertEquals(John, digitalCert.getIssuer());
	}

	@Test
	public void testSetSerialNumber(){
		digitalCert.setSerialNumber(serialNum);
		assertEquals(serialNum, digitalCert.getSerialNumber());
	}

	@Test
	public void testValidFrom(){
		digitalCert.setValidFrom(dateFrom);
		assertEquals(dateFrom, digitalCert.getValidFrom());
	}
	
	@Test
	public void testSetValidTo(){
		digitalCert.setValidTo(dateFrom);
		assertEquals(dateFrom, digitalCert.getValidTo());
	}
	    
	@Test
	public void testSetPublicKey() throws Exception{
		SecureRandom SecRandom= new SecureRandom();
	    KeyPairGenerator KeyGenerator= KeyPairGenerator.getInstance("RSA");
	    KeyGenerator.initialize(1024, SecRandom);
	    KeyPair RSAKeys = KeyGenerator.generateKeyPair();
		digitalCert.setPublicKey(RSAKeys.getPublic());
		assertEquals(RSAKeys.getPublic(), digitalCert.getpublicKey());
	}
	
	@Test
	public void testSetIssuerSignature() throws Exception{
		Digitalsigneture digitalSig = new Digitalsigneture();
		SecureRandom SecRandom= new SecureRandom();
	    KeyPairGenerator KeyGenerator= KeyPairGenerator.getInstance("RSA");
	    KeyGenerator.initialize(1024, SecRandom);
	    KeyPair RSAKeys = KeyGenerator.generateKeyPair();
	    byte[] sig = digitalSig.signeture("yo", RSAKeys.getPrivate());
		digitalCert.setIssuerSignature(sig);
		assertEquals(sig, digitalCert.getIssuerSignature());
	}

}
