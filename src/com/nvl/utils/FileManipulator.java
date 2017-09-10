package com.nvl.utils;

// import com.nvl.slc.common.Globals;

//import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: New Vision Labs </p>
 *
 * @author John Olumide
 * @version 1.0
 */


public class FileManipulator {

    public FileManipulator() {
    }


    /**
     * The method gets the content of a file and return it as byte array
     * @param File The file to be read
     * @return byte[]
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
//        if(Globals.bLogAll)
//                System.out.println("Entering getBytesFromFile()...");
        InputStream is = new FileInputStream(file);
        //System.out.println("\nDEBUG: FileInputStream is " + file);

        // Get the size of the file
        long length = file.length();
        //System.out.println("DEBUG: Length of " + file + " is " + length + "\n");

        /*
         * You cannot create an array using a long type. It needs to be an int
         * type. Before converting to an int type, check to ensure that file is
         * not loarger than Long.MAX_VALUE;
         */
        if (length > Long.MAX_VALUE) {
            System.out.println("Error: File is too large to process");
            return null;
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while ( (offset < bytes.length) && ( (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) ) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
//        if(Globals.bLogAll)
//                System.out.println("Leaving getBytesFromFile()......");
        return bytes;

        } // end getBytesFromFile

   /**
    * This is an overload of the method getBytesFromFile.
    * @param String The full name of the file to be read, including the path
    * @return byte[] The contents of the file.
    */
    public static byte[] getBytesFromFile(String fullFileName) throws IOException {
        // Construct a File object with the String fullFileName
        File file = new File (fullFileName);
        return getBytesFromFile(file);
    }

}
