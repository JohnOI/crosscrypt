/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nvl.utils;

import org.apache.commons.codec.binary.Base64;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import javax.crypto.KeyGenerator;

import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 *
 * @author JohnOI
 */
public class DESCrypt extends Crypt {
    
    protected SecretKey desKey;
    
    
    @Override
    public byte[] generateSecretKey(int keySize) {
        
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("DES");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Note: key size for DES should always be 56 
        if ( keySize != 56 ) {
            // return error here or throw an exception (e.g. InvalidNVLKeySizeException...)
            System.out.println("Invalid key size in DESCrypt! Key size input = " +  keySize);
            // @todo: write to the applications error log... 
        }
        else {
            keyGenerator.init( keySize, new java.security.SecureRandom());                
        }
        
        SecretKey desKey = keyGenerator.generateKey();        
        
        // some debug printing...        
        System.out.println( "DES Key: " + desKey.toString() + ", Algorithm:  " + desKey.getAlgorithm()  + ", key encoding format: " + desKey.getFormat() + ", encoded: " + desKey.getEncoded() );        
        
        return desKey.getEncoded();
    }
    
    @Override
    public String generateKey( int keySize ) {
        
        byte[] newDESKey = this.generateSecretKey(keySize);
        
        String keyStringInBase64 = new String (  Base64.encodeBase64(newDESKey) );
        // debug
//        System.out.println( "Key String in Base64: " + keyStringInBase64 );
        return keyStringInBase64;        
    }
    
    
    @Override
    public byte[] encrypt(byte[] inBytes) {
//        System.out.println("\nIn encrypt(byte[] inBytes)...");        

        Cipher desCipher;

        byte[] outBytes = null;

        try {
            
            byte[] encodedKey =  this.getKeyData();
            
            // debug
//            System.out.println("In encrypt()-> encodedKey: " + encodedKey );
            
            SecretKey originalDESKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DES");
            // Create the cipher
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            try {
                // Initialize the cipher for encryption
                desCipher.init(Cipher.ENCRYPT_MODE, originalDESKey);

                // Encrypt the clear text
                outBytes = desCipher.doFinal(inBytes);
           
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (NoSuchAlgorithmException ex) {
            System.out.println("No Such Algorithm exists... " + ex);
            Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            System.out.println("No Such Padding exists... " + ex);            
            Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return outBytes;
    }
    
    @Override    
    public byte[] decrypt(byte[] inBytes) {
       // System.out.println("\nIn decrypt(byte[] inBytes)...");
        Cipher desCipher;
 
        byte[] outBytes = null;
        
        try {
               
            byte[] encodedKey =  this.getKeyData();
            
            SecretKey originalDESKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DES");

            // Create the cipher
             desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");            

            // Create the cipher
            try {
                // Initialize the cipher for decryption
                desCipher.init(Cipher.DECRYPT_MODE, originalDESKey);
                
                // debug code
//                System.out.println("length in bytes -> " + inBytes.length);
//                System.out.println("ciphertext-> " + new String(inBytes) ); 
                
                // Decrypt the text, with Base64 encoding removed
                outBytes = desCipher.doFinal(inBytes);
                  
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return outBytes;
    }
    

    /**
     * doCrypt executes the denoted crypt operation on the passed in String.
     * @param operation 0 - encrypt, anything else decrypt
     * @param inString
     * @return the result of the crypt operation on the input string.
     */
    @Override
    public String doCrypt(int operation, String inString) {
        if ( operation == 0 ) {
             return encrypt(inString);
        }
        else {
            return decrypt(inString);
        }
    }

    @Override
    public String encrypt( String inString ) {
        
        byte[] outBytes = null;
        try {
            outBytes = this.encrypt(inString.getBytes("UTF8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DESCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        // to make the ciphertext a string of characters, encode it in Base64
        String outputInBase64 = Base64.encodeBase64String(outBytes);   
        return outputInBase64;
    }

    @Override
    public String decrypt( String inString ) {
//        System.out.println("\nIn decrypt()...");
        
        byte[] outBytes = this.decrypt(Base64.decodeBase64(inString));
        return new String(outBytes);
    }

    
    void printBytes(byte[] inputBytes) {
        // display byte contents
        for (int i=0; i<inputBytes.length; i++ )  {
            System.out.println("[" + i + "]"  + " " +  inputBytes[i]);
        }
    }
    
//    @Override
//    public int encryptByte(int intValue) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public int decryptByte(int intValue) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }    
    
    // method below no longer needed
//    byte[] padInputToMultipleOf8(byte[] inputBytes) {
//        
//        // display byte contents
//        System.out.println(" ------------ ");
//        printBytes(inputBytes);
//        
//        int size = (inputBytes.length / 8 ) + ( (inputBytes.length % 8 == 0) ? 0 : 1 ) ;
//        
//        int newLength = size * 8;
//        int lengthOfPad = newLength - inputBytes.length;
//        
//        // set the pad character according to PKCS5Padding algorithm
//        byte padByte = 0;
//        switch (lengthOfPad) {
//            case 7 : padByte = (byte) 0x07; break;
//            case 6 : padByte = (byte) 0x06; break;
//            case 5 : padByte = (byte) 0x05; break;
//            case 4 : padByte = (byte) 0x04; break;         
//            case 3 : padByte = (byte) 0x03; break;        
//            case 2 : padByte = (byte) 0x02; break;      
//            case 1 : padByte = (byte) 0x01; break;               
//        }
//        
//        ByteBuffer byteBuffer = ByteBuffer.allocate(newLength);
//        
//        byteBuffer.put(inputBytes);
//        
//        byte[] paddedUp = new byte[size * 8];
//        
//        for (int j=0;j<inputBytes.length;j++) {
//            paddedUp[j]=byteBuffer.get(j);
//        }
//        
//        for (int k=inputBytes.length;k<paddedUp.length;k++) {
//            paddedUp[k]=padByte;
//        }        
//        
//        return paddedUp;
//    }

    @Override
    public void setKey(String key) {
        // get the key from it's base64 encoded value
        String keyStringInBase64 = key;
        
        byte[] encodedKey = Base64.decodeBase64(keyStringInBase64); 
        
        this.setKeyData(encodedKey);
        
    }

    @Override
    public String getKey() {
        String keyStringInBase64 = new String (  Base64.encodeBase64(this.getKeyData()) );
        // debug
//        System.out.println( "Key String in Base64: " + keyStringInBase64 );
        return keyStringInBase64;    
    }

    
}
