/*
 * EChequeRegisteration.java
 *
 * Created on May 19, 2007, 7:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eCheque;

import java.io.Serializable;

/**
 *
 * @author Saad
 */
public class EChequeRegisteration implements Serializable {
    
    private String bankName;
    private String bankAddress;
    private String accountNumber;
    private String clientName;
    private String eWalletLocation;
    private int userNameHash;
    private int passwordHash;
    
    /** Creates a new instance of EChequeRegisteration */
    public EChequeRegisteration() {
    }
    
    public void setBankName(String bName){
        bankName = bName;
    }

       
    public void setBankAddress(String URL){
         bankAddress = URL;
    }
    
    public void setAccountNumber(String account){
        accountNumber = account;
    }
    
    public void setClientName(String cName){
        clientName = cName;
    }
    
    public void setEWalletLoaction(String path){
        eWalletLocation = path;
    }
    
    public void setUsername(int hashValue){
        userNameHash = hashValue;
    }
    
    public void setPasword(int hashValue){
        passwordHash = hashValue;
    }
    
    public String getBankName(){
        return bankName;
    }

    public String getBankAddress(){
         return bankAddress;
    }
    
    public String getAccountNumber(){
        return accountNumber;
    }
    
    public String getClientName(){
        return clientName;
    }
    
    public String getEWalletLoaction(){
        return eWalletLocation;
    }
    
    public int getUsername(){
        return userNameHash;
    }
    
    public int getPasword(){
        return passwordHash;
    }
    
}