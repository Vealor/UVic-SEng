/*
 * Echqueserver.java
 *
 * Created on April 27, 2007, 8:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Basel
 */ 
package eCheque;

//import com.sun.crypto.provider.AESCipher;
import java.net.*;
import java.io.* ;       
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.crypto.Cipher;
import java.security.*;

public class Echqueserver implements Runnable{
    
/** Creates a new instance of Echqueserver */
private ServerSocket server;
private Socket ServerConnection;
private ObjectInputStream socketInputObject;
private ObjectOutputStream socketOutputObject;
private InputStream socketInput;
private OutputStream socketOutput;
private JTextArea screenShell;
private EChequeRegistration eChequeRegist;
private DigitalCertificate serverCerit;
private String walletPath;
private int portID;
private PrivateKey privKey;

 public Echqueserver(JTextArea screen,DigitalCertificate DC, String wPath,PrivateKey privateKey, ServerSocket serverSockect){

    screenShell = screen;
    serverCerit = DC;
    walletPath = wPath;
    privKey = privateKey;
    server = serverSockect;
}

 //private void startServer() throws Exception{
    
   // server = new ServerSocket(portID);
//}

 private void acceptConnection()throws IOException{
     ServerConnection = server.accept();
} 

 private void getsocketStream()throws Exception {
    socketOutput=ServerConnection.getOutputStream();
    socketOutput.flush(); 
    socketInput=ServerConnection.getInputStream() ;

    socketOutputObject=new ObjectOutputStream(ServerConnection.getOutputStream());
    socketOutputObject.flush();
    socketInputObject = new ObjectInputStream(ServerConnection.getInputStream());
   }

 private void ProcessConnection()throws IOException,ClassNotFoundException,NoSuchAlgorithmException,NoSuchPaddingException, IllegalBlockSizeException,InvalidKeyException,Exception {
       
        boolean sessionDone = false;
        DigitalCertificate clientCerit;
        
        if(!sessionDone){
            
            //exchange digital certificates
            clientCerit = (DigitalCertificate)socketInputObject.readObject();
            socketOutputObject.writeObject(serverCerit);
            socketOutputObject.flush();
            
            //get the wraeped key and uwraped it
            byte[] wrappedKey;
            byte[] unwrappedKey;
            Key sessionKey;
            int keyLength; 
            
            //read the session key form the socket
            keyLength = socketInputObject.readInt();
            wrappedKey = new byte[keyLength];
            socketInput.read(wrappedKey);
            
            //decrypt the session key with the user private key.
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.UNWRAP_MODE,privKey);
            sessionKey = cipher.unwrap(wrappedKey,"AES",Cipher.SECRET_KEY);
            
            //
            Calendar currTime = new GregorianCalendar();
            String cheqName ="";
            cheqName += currTime.get(currTime.YEAR);
            cheqName += currTime.get(currTime.MONTH);
            cheqName += currTime.get(currTime.DAY_OF_MONTH);
            cheqName += currTime.get(currTime.HOUR_OF_DAY);
            cheqName += currTime.get(currTime.MILLISECOND);
                                 
            //read the cheque from the socket
            FileOutputStream chqIn = new FileOutputStream(walletPath+"\\In Coming\\"+cheqName+".cry");
            byte[] buffer = new byte[1024];
            int numread;
            while ((numread = socketInput.read(buffer))>=0)
            {
               chqIn.write(buffer, 0, numread);
            }
            
            chqIn.close(); 
        
            //validate the received cheque.
            InputStream in = new FileInputStream(walletPath+"\\In Coming\\"+cheqName+".cry");
            OutputStream out = new FileOutputStream(walletPath+"\\My Cheques\\"+cheqName+".sec");
            
            //create AES object to decrypt the received cheque
            AESCrypt aesObj = new AESCrypt();
            Cipher aesCipher = aesObj.initializeCipher(sessionKey,1);
            aesObj.crypt(in,out,aesCipher);
            in.close();
            out.close();
           
            // verify the cheque siganture using the sender public key.
            Digitalsigneture digitalSign = new Digitalsigneture();
            
            // load decrypted chequeObject.
            EChequeIO readChq = new EChequeIO();
            ECheque recivedChq = new ECheque();
            recivedChq = readChq.readcheque(walletPath+"\\My Cheques\\"+cheqName+".sec");
            String chqSign = ChequeReferenceString(recivedChq);
            
            boolean verifySign = digitalSign.verifySignature(recivedChq.getdrawersiganure(),chqSign,clientCerit.getpublicKey());
            if(verifySign){
                  JOptionPane.showMessageDialog(null,"The signature is vaild", "e-Cheque Clear",
                  JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                  JOptionPane.showMessageDialog(null,"The signature is not vaild", "e-Cheque not Clear",
                  JOptionPane.WARNING_MESSAGE);
            }
                
           
        }
        
}
       
 private void closeConnection() {
    try
    {
    
        socketInput.close();
        socketOutput.close();
        socketInputObject.close();
        socketOutputObject.close();
        ServerConnection.close();
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,e.getMessage());
        e.printStackTrace();
    }
     
 }
 
 public void RunServer() {
    try {
          
           screenShell.append("\n>>Status: Starting The Sever");
           //startServer(); 
           screenShell.append("\n>>Status: Wating for connection");
           acceptConnection();
           screenShell.append("\n>>Status: connection accepted");
           getsocketStream ();
           screenShell.append("\n>>Status: Socket I/O complete");
           screenShell.append("\n>>Status: processing started");
           ProcessConnection ();
           screenShell.append("\n>>Status: process complete");
        }

    catch(Exception error)
    {
        JOptionPane.showMessageDialog(null,error.getMessage());
        error.printStackTrace();
    }
     
    finally
    {
          closeConnection();
    }
}

 private String ChequeReferenceString(ECheque chq){
        
        String ref="";
        ref+= chq.getaccountNumber()+chq.getaccountholder()+chq.getbankname()+chq.getchequeNumber()+
              chq.getMoney()+chq.getcurrencytype()+chq.getearnday()+chq.getguaranteed()+chq.getpayToOrderOf();
       
        return ref;       
}  
    
 public void run(){

     RunServer();
 }

}