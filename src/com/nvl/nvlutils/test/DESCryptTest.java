/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nvl.nvlutils.test;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
// import java.security.Key;

//import sun.misc.BASE64Encoder;
//import sun.misc.BASE64Decoder;


/**
 *
 * @author JohnOI
 */
public class DESCryptTest {

    // protected SecretKey desKey;
    static protected String inText = "WOW! DES encryption is cool!";
    static protected String key;
    static protected String outText;


	public static void main (String args[]) {
            System.out.println("DES Encryption sample and test!");

        try {

            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            SecretKey desKey = keygen.generateKey();
            System.out.println( "DES Key: " + desKey.toString() + " _ " + desKey.getAlgorithm()  + " _ " + desKey.getFormat() + " _ " + desKey.getEncoded() );

            Cipher desCipher;

            // Create the cipher
            // desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher = Cipher.getInstance("DES");            
            
            try {
                // Initialize the cipher for encryption
                desCipher.init(Cipher.ENCRYPT_MODE, desKey);
                
                byte[] cleartext;

                // get the clear text as bytes
//                byte[] cleartext = inText.getBytes();                    
                cleartext = "WOW! DES encryption is cool!".getBytes(); //"UTF8");

                // Encrypt the clear text
                byte[] ciphertext = desCipher.doFinal(cleartext);

                // Initialize the same cipher for decryption
                desCipher.init(Cipher.DECRYPT_MODE, desKey);

                byte[] cleartext1 = desCipher.doFinal(ciphertext);

                System.out.println("cleartext -> " + new String(cleartext) );
                System.out.println("ciphertext-> " + new String(ciphertext) );                    
                System.out.println("cleartext1-> " + new String(cleartext1) );
                
                
                // the generated key base64 encoded desKey-> W8ˉh*8
                System.out.println("desKey-> " + new String(desKey.getEncoded()) );                
                
//                
//                System.out.println("cleartext -> " + new sun.misc.BASE64Encoder().encode(cleartext) );
//                System.out.println("ciphertext-> " + new sun.misc.BASE64Encoder().encode(ciphertext) );                    
//                System.out.println("cleartext1-> " + new sun.misc.BASE64Encoder().encode(cleartext1) );                      

            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(DESCryptTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(DESCryptTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(DESCryptTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DESCryptTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(DESCryptTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}
