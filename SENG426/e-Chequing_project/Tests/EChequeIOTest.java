package eCheque;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import junit.framework.*;

public class EChequeIOTest extends TestCase{
	
	private EChequeIO input;
	@Before
	public void setUp(){
		input = new EChequeIO();
	}
	
	@Test
	public void testsavecheque() {
		ECheque obj = new ECheque();
		String filename = "test.txt";
		ObjectInputStream in=null;
		try {
			input.savecheque(obj,filename);
			in=new ObjectInputStream(new FileInputStream(new File(filename)));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		assertNotNull(in);
		
	}
	@Test
	public void testreadcheque() {
		ECheque obj = new ECheque();
		ECheque obj2 = new ECheque();
		obj.setaccountholder("Cole");
		String filename = "test.txt";
		try {
			input.savecheque(obj,filename);
			obj2 = input.readcheque(filename);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		assertEquals(obj.getaccountholder(),obj2.getaccountholder());
		
	}
	
	@Test
	public void testEChequeIO(){
		assertNotNull(input);
	}
	
	@After
	public void teardown(){
		input = null;
	}
}
