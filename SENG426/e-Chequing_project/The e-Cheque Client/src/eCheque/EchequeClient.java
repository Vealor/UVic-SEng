/*
 * EchequeClient.java
 *
 * Created on April 27, 2007, 8:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author SAAD
 */

package eCheque;

import java.net.*;
import java.io.* ;   
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.omg.CORBA.TRANSIENT;


public class EchequeClient implements Runnable{
    
    /** Creates a new instance of EchequeClient */

private Socket ClientConnection;
private ObjectInputStream SocketInputObject;
private ObjectOutputStream SocketOutputObject;
private InputStream SocketInput;
private OutputStream SocketOutput;
private DigitalCertificate clientCerit;
private EChequeRegistration registrationData;
private ECheque depositCheque;
private JTextArea screenShell;
private Key sessionKey;
private String chequePath;
private String walletPath;
private String hostname;
private int portID;
private int bankmode;
private boolean getServerConnection;
private boolean getSocketConnection;
private boolean getProcessConnection;
private boolean bankConnection;


public EchequeClient(JTextArea screen , DigitalCertificate DC,Key aesKey,String wPath, String cPath, String host, int port){

    screenShell = screen;
    clientCerit = DC;
    sessionKey = aesKey;
    walletPath = wPath;
    chequePath = cPath;
    hostname = host;
    portID = port;
    getServerConnection = false;
    getSocketConnection = false;
    getProcessConnection= false;
    bankConnection = false;
}

public EchequeClient(int port, int mode, String host, EChequeRegistration register, DigitalCertificate DC){
        
     portID = port;
     bankmode = mode;
     hostname = host;
     registrationData = register;
     clientCerit = DC;
     bankConnection = true;
   
}

public EchequeClient(int port, int mode, String host,EChequeRegistration register, ECheque chq){
    portID = port;
    bankmode = mode;
    hostname= host;
    registrationData = register;
    depositCheque = chq;
    bankConnection = true;
}     

private void ConnectToServer( ) throws Exception {
    
      ClientConnection = new Socket(InetAddress.getByName(hostname),portID);
      getServerConnection = true;
}

private void getSocketStream()throws Exception {
    
     SocketInput=ClientConnection.getInputStream() ;
     SocketOutput=ClientConnection.getOutputStream();
     SocketOutput.flush();
     SocketInputObject=new ObjectInputStream(ClientConnection.getInputStream());
     SocketOutputObject=new ObjectOutputStream(ClientConnection.getOutputStream());
     SocketOutputObject.flush();
     
     getServerConnection = true;
 }

private void processConnection()throws IOException,Exception,ClassNotFoundException,
    NoSuchAlgorithmException,NoSuchPaddingException {
     
    DigitalCertificate serverCerit;
    boolean sessionDone = false;
        
        if(!sessionDone){
             //exchange the Digital Ceritificates
             SocketOutputObject.writeObject(clientCerit);
             SocketOutputObject.flush();
             
             serverCerit = (DigitalCertificate)SocketInputObject.readObject();
             
             //send session key
             Cipher cipher = Cipher.getInstance("RSA");
             cipher.init(Cipher.WRAP_MODE, serverCerit.getpublicKey());
             byte[] wrappedKey = cipher.wrap(sessionKey);
             int keyLength = wrappedKey.length;
             
             SocketOutputObject.writeInt(keyLength);
             SocketOutputObject.flush();
             
             SocketOutput.write(wrappedKey);
             SocketOutput.flush();
             
             //send encrypted cheque.
             FileInputStream cheqOut = new FileInputStream(chequePath);
             byte[] buffer = new byte[1024];
             int numread;
             while((numread=cheqOut.read(buffer))>=0)
             {
                 SocketOutput.write(buffer, 0, numread);
             }
             cheqOut.close(); 
      }
      
    getProcessConnection= true;
        
}

private void CloseConnection(){
    try
    {
        if(getSocketConnection){
            SocketInput.close();
            SocketOutput.close();
            SocketInputObject.close();
            SocketOutputObject.close();
        }
        if(getServerConnection){
            ClientConnection.close();
        }
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,"illegeal close for communication channel","Connection Error",JOptionPane.ERROR_MESSAGE);
    }
}
private void processBankConection()throws IOException, ClassNotFoundException{
            
           String confirm;
           SocketOutputObject.writeObject("Hello");
           SocketOutputObject.flush();
           SocketOutputObject.writeInt(bankmode);
           SocketOutputObject.flush();
           
           if(bankmode ==0 ){
               SocketOutputObject.writeObject(registrationData);
               SocketOutputObject.flush();
               SocketOutputObject.writeObject(clientCerit);
               SocketOutputObject.flush();
               // save registration data.
               ObjectOutputStream outObj = new ObjectOutputStream(new FileOutputStream("Config.epc"));
               outObj.writeObject(registrationData);
               outObj.close();
                                                            
           }
           if(bankmode==1){
               SocketOutputObject.writeObject(depositCheque);
               SocketOutputObject.flush();
               SocketOutputObject.writeObject(registrationData.getAccountNumber());
               SocketOutputObject.flush();
               JOptionPane.showMessageDialog(null,"send info for deposit done");
               
           }
           if(bankmode == 2){
               SocketOutputObject.writeObject(depositCheque);
               SocketOutputObject.flush();
           }
           confirm = (String)SocketInputObject.readObject();
           JOptionPane.showMessageDialog(null,confirm);
    
}

public void RunClient() 
 {
     
    try {
           
           if(!bankConnection)
                screenShell.append("\n\n>> Connecting to echeque host");
           ConnectToServer ();
           if(!bankConnection)
                screenShell.append("\n\n>> you are connected");
           getSocketStream ();
           if(!bankConnection)
                screenShell.append("\n\n>> you are connected");
           
           if(!bankConnection){
               screenShell.append("\n\n>> Starting cheque tarnsfer");
               processConnection ();
           }
           else
              processBankConection();
     }
    
     catch(Exception error)
     {
         JOptionPane.showMessageDialog(null,error.getMessage(),"Connection Error",JOptionPane.ERROR_MESSAGE);
         
     }
     
     finally
         {
             CloseConnection();
         }
  }

 public void run(){
    RunClient();
 }
    
}

