package com.nvl.utils;


import com.nvl.utils.FileManipulator;
import com.nvl.utils.CaeserCrypt;

import java.lang.reflect.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author John Olumide
 * @version 1.0
 */
public class ApplicationObfuscator {
    public ApplicationObfuscator() {
    }


    public static void main (String args[]) {

        // Ensure that the same key is used in encrypting and decrypting the
        // files, by reading from a file and deriving it using a formula, i.e. x+3
        // or setting a global variable.
        // or the day of the week, or day of the month etc.

        String classFileName = 	System.getProperty("user.home")
                                +  File.separatorChar + "ClientGUI.class" ;
        // System.out.println("Download location and file name: " + classFileName);
        String crossFileName = 	System.getProperty("user.home")
                                +  File.separatorChar + "ClientGUI.cross" ;

        encryptClientClass( classFileName, crossFileName );
        // change the class file name, i.e append '2'
        classFileName = 	System.getProperty("user.home")
                                +  File.separatorChar + "ClientGUI.class2" ;
        decryptClientClass( crossFileName, classFileName );
        // NOTE: source file should be deleted at some point, i.e. the plain class file

    }

    /**
     * Reads the contents of a file, encrypts it and writes it to the file designated
     * by the second parameter.
     * @param String The name of the class file to be encrypted
     * @param String The name of the destination file, the encrypted file
     */
    public static void encryptClientClass(String classFileName, String outputFileName) {
        // class(es) to encrypt, location, name
       byte plainFileBytes[] = null;
       byte cryptFileBytes[] = null;

       // read the file into a byte array
       // public static byte[] getBytesFromFile(String fullFileName) throws IOException {
       try {
              plainFileBytes = FileManipulator.getBytesFromFile(classFileName);
       } catch (IOException e) {
               System.out.println("IO Exception " + e);
       }

        // encrypt the file
  //	 int key = 70;
        int key = 1;
        CaeserCrypt crypt = new CaeserCrypt(key);
        cryptFileBytes = crypt.encryptByteArray(plainFileBytes);

        // write the file to file system
        try {
                File file = new File ( outputFileName );
                file.createNewFile();

                //FileOutputStream fileOut = new FileOutputStream(sFileName);
                FileOutputStream fileOut = new FileOutputStream(file);

                fileOut.write(cryptFileBytes);
                fileOut.close();
        }
        catch (IOException e) {
                System.out.println("IO Exception " + e);
       }
    } // end encryptClientClass

    /**
     * Reads the contents of a file, decrypts it and writes it to the file designated
     * by the second parameter.
     * @param String The name of the class file to be decrypted
     * @param String The name of the destination file, the decrypted file
     */
    public static void decryptClientClass(String crossFileName, String classFileName) {
        // class(es) to encrypt, location, name
       byte plainFileBytes[] = null;
       byte cryptFileBytes[] = null;

       // read the file into a byte array
       // public static byte[] getBytesFromFile(String fullFileName) throws IOException {
       try {
              cryptFileBytes = FileManipulator.getBytesFromFile(crossFileName);
       } catch (IOException e) {
               System.out.println("IO Exception " + e);
       }

       // decrypt the file
       // int key = 70;
        int key = 1;
        CaeserCrypt crypt = new CaeserCrypt(key);
        plainFileBytes = crypt.decryptByteArray(cryptFileBytes);

        // write the file to file system
        try {
                File file = new File ( classFileName );
                file.createNewFile();

                //FileOutputStream fileOut = new FileOutputStream(sFileName);
                FileOutputStream fileOut = new FileOutputStream(file);

                fileOut.write(plainFileBytes);
                fileOut.close();
        }
        catch (IOException e) {
                System.out.println("IO Exception " + e);
       }
    } // decryptClientClass
}
