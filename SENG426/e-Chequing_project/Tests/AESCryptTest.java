package eCheque;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import junit.framework.*;
import sun.misc.IOUtils;

import org.junit.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;

public class AESCryptTest extends TestCase{

	private AESCrypt input;
	@Before
	public void setUp(){
		input = new AESCrypt();
	}
	
	@Test
	public void testGenerateRandomAESKey(){
		SecretKey k1=null;
		SecretKey k2=null;
		try{
		k1 = input.GenerateRandomAESKey();
		k2 = input.GenerateRandomAESKey();
		}catch(Exception e){
			
		}
		assertNotSame(k1,k2);
	}
	@Test
	public void testinitializeCipher(){
		Cipher cipherObj;  
		Cipher cipherObj2; 
		int CipherMode;
		try{
		SecretKey key = KeyGenerator.getInstance("AES").generateKey();
		CipherMode = Cipher.ENCRYPT_MODE;
		cipherObj = Cipher.getInstance("AES");
		cipherObj.init(CipherMode,key);
		assertEquals(input.initializeCipher(key,0).getParameters(),cipherObj.getParameters());
		//1
		CipherMode = Cipher.DECRYPT_MODE;
		cipherObj = Cipher.getInstance("AES");
		cipherObj.init(CipherMode,key);
		assertEquals(input.initializeCipher(key,1).getParameters(),cipherObj.getParameters());
		//2
		CipherMode = Cipher.WRAP_MODE;
		cipherObj = Cipher.getInstance("AES");
		cipherObj.init(CipherMode,key);
		assertEquals(input.initializeCipher(key,2).getParameters(),cipherObj.getParameters());
		//3
		CipherMode = Cipher.UNWRAP_MODE;
		cipherObj = Cipher.getInstance("AES");
		cipherObj.init(CipherMode,key);
		assertEquals(input.initializeCipher(key,3).getParameters(),cipherObj.getParameters());
		}catch(Exception e){
			
		}
	}
	
	@Test
	public void testcrypt(){
		try{
		OutputStream out1 = new FileOutputStream("output.txt");
		InputStream in1 = new FileInputStream("cole.txt");
		
		
		Cipher cipherObj = Cipher.getInstance("AES");
		SecretKey key = KeyGenerator.getInstance("AES").generateKey();	
		cipherObj.init(Cipher.ENCRYPT_MODE, key);
		
		input.crypt(in1,out1,cipherObj);
		in1.close();
		out1.close();
		InputStream in2 = new FileInputStream("output.txt");
		StringBuilder builder = new StringBuilder();
		int ch;
		while((ch = in2.read()) != -1){
		    builder.append((char)ch);
		}
		in1 = new FileInputStream("cole.txt");
		StringBuilder builder2 = new StringBuilder();
		int ch2;
		while((ch2 = in1.read()) != -1){
		    builder2.append((char)ch2);
		}
		cipherObj.init(Cipher.DECRYPT_MODE, key);
        String decrypted = new String(cipherObj.doFinal(builder.toString().getBytes()));
		assertEquals(decrypted,builder2.toString());
		} catch(Exception e){
			
		}
		
  
     }
	
	@Test
	public void testinilizeAESKeyByPassword(){
		String pass = "password";
		SecretKeySpec key = new SecretKeySpec(pass.getBytes(),"AES");
		assertEquals(input.inilizeAESKeyByPassword(pass),key);
	}

	
	
	
}
