import org.junit.Test;
import static org.junit.Assert.*;

import java.security.KeyPair;

import javax.crypto.Cipher;

public class RSAGeneratorTest {

	RSAGenerator rsaGenerator = new RSAGenerator();
	
	String text = "This is some random text hello world.";
	
	@Test
	public void testGenerateRSAKeys() throws Exception {
		KeyPair kp1 = rsaGenerator.GenerateRSAKeys();
		KeyPair kp2 = rsaGenerator.GenerateRSAKeys();
		
		assertNotNull(kp1);
		assertNotEquals(kp1, kp2);
		
		final Cipher cipher = Cipher.getInstance("RSA");
		
		cipher.init(Cipher.ENCRYPT_MODE, kp1.getPublic());
		byte[] cipherText = cipher.doFinal(text.getBytes());
		
		byte[] decryptedText = null;
		cipher.init(Cipher.DECRYPT_MODE, kp1.getPrivate());
		
		decryptedText = cipher.doFinal(cipherText);
		String decryptedString = new String(decryptedText, "UTF-8");
		
		assertEquals(text, decryptedString);
	}
	
}