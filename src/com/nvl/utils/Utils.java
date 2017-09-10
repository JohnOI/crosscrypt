
package com.nvl.utils;

import java.io.File;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.io.IOException;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

//dapo added this comment
/* Utils.java is a 1.4 example used by FileChooserDemo2.java. */

public class Utils {
    public final static String java = "java";
    public final static String txt = "txt";
    public final static String rtf = "rtf";
    public final static String doc = "doc";
    public final static String bat = "bat";
    public final static String aspx = "aspx";
    public final static String asp = "asp";
    public final static String jsp = "jsp";
    public final static String xml = "xml";
    public final static String cs = "cs";
    public final static String html = "html";
    public final static String htm = "htm";
    public final static String ini = "ini";


    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /*
     *  Validates a specified IP Address
     */
    public static boolean validateIPAddress(String inetAddress){
        if(inetAddress == null || inetAddress.equals("")){
            JOptionPane.showMessageDialog(null, "IP Address can not be empty!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else{
            if(inetAddress.equalsIgnoreCase("localHost"))
                return true;
            StringTokenizer token = new StringTokenizer(inetAddress, ".");
            if(token.countTokens()<4){
                JOptionPane.showMessageDialog(null, "IP Address is not well formatted. FORMAT -> [###].[###].[###].[###]", "Information", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }else{
                while(token.hasMoreTokens()){
                    String str = token.nextToken();
                    int intValue;
                    try{
                        intValue = Integer.parseInt(str);
                    }catch(NumberFormatException e){
                        System.out.print(e.getMessage());
                        JOptionPane.showMessageDialog(null, "IP Address must comprise numbers separated by period", "Information", JOptionPane.INFORMATION_MESSAGE);
                        return false;
                    }
                    if(intValue < 0 || intValue > 255){
                        JOptionPane.showMessageDialog(null, "Each of the numbers separated by period must be less than 255", "Information", JOptionPane.INFORMATION_MESSAGE);
                        return false;
                    }
                }
                return true;
            }

        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * An utility function that returns the current timestamp using the format
     * "HHmmsss"
     * @return String
     */
    public static String getTimeStamp () {
       // timestamp
       java.text.SimpleDateFormat dateFormat =
                  new java.text.SimpleDateFormat("HHmmsss");
       java.util.Date time = new java.util.Date();
       String timeStamp = dateFormat.format(time);
       return timeStamp;
    }
    
    /**
     * An utility function that returns the current date and timestamp using the format
     * "HHmmsss"
     * @return String
     */
    public static String getDateTimeStamp () {
       // timestamp
       java.text.SimpleDateFormat dateFormat =
                  new java.text.SimpleDateFormat("yyyyMMddHHmmsss");
       java.util.Date time = new java.util.Date();
       String timeStamp = dateFormat.format(time);
       return timeStamp;
    }    

    /**
     * An utility function that returns the current date and timestamp 
     * using the format received as an argument
     * 
     * @param format The SimpleDateFormat class string, e.g. yyyyMMddHHmmsss
     * @return
     */
    public static String getDateTimeStamp (String format ) {
       // timestamp
       java.text.SimpleDateFormat dateFormat =
                  new java.text.SimpleDateFormat(format);
       java.util.Date time = new java.util.Date();
       String timeStamp = dateFormat.format(time);
       return timeStamp;
    }       
    
    // 
  public static boolean fileExists (String fileName) {

    try {
      File f = new File (fileName);

      if ( f.isFile() ) {
        return true;
      }

     }
     catch (NullPointerException ex) {
       return false;
     }
     // if we get here the file exists
     return false;

  }

  public static boolean dirExists( String dirName ) {
    try {
      File f = new File (dirName);

      if ( f.isDirectory() ) {
        return true;
      }

     }
     catch (NullPointerException ex) {
       return false;
     }
     // if we get here the dir exists
     return false;

  }

  public static boolean createFile( String fileName ) {
    File f = null;
    try {
      f = new File (fileName);
      return f.createNewFile();
     }
     catch (NullPointerException ex) {
       return false;
     }
     catch (IOException ex) {
       return false;
     }

  }


  public static boolean makeDir( String dirName ) {
    File f = null;
    try {
      f = new File (dirName);
      return f.mkdirs();
     }
     catch (NullPointerException ex) {
       return false;
     }
  }

  /**
   * Returns the extension of the file
   * @param file
   * @return extension component of the file name
   */
  public static String getFileExtension(String fileName) {
    if ( fileName != null) {
      int i = fileName.lastIndexOf(".");
      if (i>0 && i< (fileName.length()-1) ) {
        return fileName.substring(i+1).toLowerCase();
      }
    }
    return null;
  }


  public static String mappingToString(Hashtable mapping) {
    Set keys = mapping.entrySet();
    Iterator it = keys.iterator();
    String result ="";
    while ( it.hasNext()  ) {
      // get next
      Object key = it.next();
      result += key.toString()+"\n";
    }
    System.out.print( result );
    return result;
  }
    
  
  /**
   * Returns the name of the operating system on which the client is being run
   * @return Operating System name. Values include: "Linux" , "Windows XP", "Windows Vista"
   */
    public static String getOperatingSystemName() {
        String OSName = System.getProperty("os.name");
      return OSName;
  }
    
}
