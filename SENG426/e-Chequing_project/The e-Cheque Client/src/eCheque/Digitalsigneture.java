/*
 * Digitalsigneture.java
 *
 * Created on March 28, 2007, 4:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Basel
 */
package eCheque;

import java.security.*;
import java.io.*;

public class Digitalsigneture 
{
     public Digitalsigneture()
     {
     }
//this function use to sign  cheque data by  RSA algorthem 
    public byte[] signeture(String message, PrivateKey privKey)throws Exception
    {
         Signature signmessage = Signature.getInstance("SHA1withRSA");
         signmessage.initSign(privKey);
         signmessage.update(message.getBytes());
         byte[]signature = signmessage.sign();
         return signature;
                
    }
    //this function use to verifay sign  to cheque data by  RSA algorthem 
    
         public boolean verifySignature(byte[]messagesign,String message,PublicKey pubKey)
         throws Exception
         {
             Signature veryMessage = Signature.getInstance("SHA1withRSA");
             veryMessage.initVerify(pubKey);
             veryMessage.update(message.getBytes());
             if (!veryMessage.verify(messagesign))
             
              return false;
             
             else
                 
                 return true;
         }
             
}
   
    
    
    
    
    
    
    
 
    
    

