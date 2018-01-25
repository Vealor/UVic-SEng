/*
 * RSAGenerator.java
 *
 * Created on March 28, 2007, 4:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author SaaD
 */
package eCheque;

import java.security.*;
// This class generates PKI keys using Rsa with key size 1024

import javax.crypto.KeyGenerator;

public class RSAGenerator {
   
    public KeyPair GenerateRSAKeys()throws NoSuchAlgorithmException 
    {
        SecureRandom secRandom = new SecureRandom();
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024, secRandom);
        KeyPair rsaKeys = keyGenerator.generateKeyPair();
        return rsaKeys;
    }
}
