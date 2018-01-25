package eCheque;

import java.io.*;

public class DigitalCertificateIO {
    
    public void SaveDC(DigitalCertificate a, String filePath)throws IOException
    {
        //To create a new file to store Digtial Certificate Object.
        ObjectOutputStream out =new ObjectOutputStream(new FileOutputStream(new File(filePath)));
        
        out.writeObject(a);
        
        out.close();    
    }
    public DigitalCertificate readDigitalCertificate(String filePath) throws IOException, ClassNotFoundException            
    {
        ObjectInputStream In =new ObjectInputStream(new FileInputStream(new File(filePath)));
         DigitalCertificate DC;   
         DC =(DigitalCertificate)In.readObject();
        
        In.close();
        return DC;
        
    }

    /** Creates a new instance of SaveDC */
    public DigitalCertificateIO () {
        
    }
    
}