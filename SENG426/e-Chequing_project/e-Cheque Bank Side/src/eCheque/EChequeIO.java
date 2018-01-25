/*
 * savecheque.java
 *
 * Created on March 4, 2007, 9:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package eCheque;

import java.io.*;

/**
 *
 * @author Basel
 */
public class EChequeIO
{
        
   public void savecheque(ECheque obj,String filename)//(ECheque x,String filebath)
   throws IOException {
       
       ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(new File(filename)));//new File(filename))
      
       out.writeObject(obj);//(xj);
       
         out.close();
        
   }
   public ECheque readcheque(String filename) throws IOException, ClassNotFoundException {
       
       ObjectInputStream in=new ObjectInputStream(new FileInputStream(new File(filename)));//new File(filename))
       ECheque cheq; 
      
       cheq=(ECheque)in.readObject();
       in.close();
      
       return cheq;
    }
    
    
    /** Creates a new instance of savecheque */
   public EChequeIO() {
   
   }
    
 }
