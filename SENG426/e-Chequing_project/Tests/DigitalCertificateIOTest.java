import org.junit.Test;
import static org.junit.Assert.*;

public class DigitalCertificateIOTest {

	DigitalCertificateIO digitalCertIO = new DigitalCertificateIO();
	DigitalCertificate digitalCert = new DigitalCertificate();
	String filePath = "C:\\Users\\bleec\\workspace\\cert.txt";
	
	@Test
	public void testSaveDC() throws Exception {
		digitalCertIO.SaveDC(digitalCert, filePath);
		assertNotNull(digitalCertIO.readDigitalCertificate(filePath));
	}
}
