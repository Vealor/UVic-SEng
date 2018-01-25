/*
 * certificate.java
 *
 * Created on March 28, 2007, 3:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Abu^S3ooD
 */
package eCheque;

import java.io.Serializable;
import java.security.*;
// this class generates Digital Signature

public class DigitalCertificate implements Serializable
{
  private String HolderName;
  private String Subject ;
  private String Issuer ;
  private String SerialNumber;
  private String ValidFrom;
  private String ValidTo;
  private PublicKey publicKey;
  private byte[] IssuerSignature;
    
    /** Creates a new instance of certificate */
    public DigitalCertificate() {
    }
    public void setHolderName(String x)
    {
       HolderName= x;
    }
    public void setSubject(String x)
    {
        Subject= x;
    }
    public void setIssuer(String x)
    {
        Issuer= x;
    }
    public void setSerialNumber(String x)
    {
        SerialNumber= x;
    }
    public void setValidFrom (String x)
    {
        ValidFrom= x;
    }
    public void setValidTo (String x)
    {
        ValidTo= x;
    }
    public void setPublicKey (PublicKey x)
    {
        publicKey= x;
    }
    public void setIssuerSignature (byte [] x)
    {
        IssuerSignature= x;
    }
    
    public String getHolderName()
    {
        return HolderName ;
    }
     public String getSubject()
    {
        return Subject ;
    }
      public String getIssuer()
    {
        return Issuer;
    }
       public String getSerialNumber()
    {
        return SerialNumber;
    }
        public String getValidFrom()
    {
        return ValidFrom;
    }
    public String getValidTo()
    {
        return ValidTo;
    }
    public PublicKey getpublicKey()
    {
        return publicKey;
    }
    
         public byte[] getIssuerSignature()
    {
        return IssuerSignature;
    }
     
}