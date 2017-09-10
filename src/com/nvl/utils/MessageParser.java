
/*
 * MessageParser.java
 *
 * Created on 12 June 2006, 11:40
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

// Note: Two exception classes defined at the end of the file


package com.nvl.utils;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * An XML parser that parses and retrieves data from XML files.
 * 
 * @author JohnOI
 */

public class MessageParser {

	private String msg = null; 		 // XML string to be parsed.
	private int pos = 0;	   		 // current position in the XML string
	private String	lastRead = null;           // the last string (tag ID or value) read
        private String s;
        private BufferedReader in;
        public boolean hasNext = true;
        public boolean tagCount= false;
        static  boolean tagFound = false;
        private int start = 0;
        private int end;

	// constructor
	public MessageParser (String message ) {
		msg = message;
                end = msg.length() - 1;
	}

        public MessageParser (File xmlFile) throws XMLException{
            try {
                // a debug message
//             System.out.println("Input XML file: " + xmlFile.getCanonicalPath());
             in = new BufferedReader(new FileReader(xmlFile));

             while((s = in.readLine())!= null)
		msg += s + "\n";
             in.close();
             end = msg.length() - 1;

            } catch (FileNotFoundException e) {

                System.out.println("Unable to read xmlFile:");
                System.out.println("\t" + e.getMessage());
                throw  new XMLException("Cannot find xml file");

            } catch (IOException e){

                System.out.println("EXCEPTION ERROR: " + e.getMessage());

            }

        }

/*
<?xml version="1.0"?><getGameParams><clientPreferences><list><clientPrefTuple><client>1</client><arrival>4</arrival><departure>5</departure><hotel>52.0000</hotel><ticketPreferences><list><typePriceTuple><type>1</type><price>48.0000</price></typePriceTuple><typePriceTuple><type>2</type><price>156.0000</price></typePriceTuple><typePriceTuple><type>3</type><price>83.0000</price></typePriceTuple></list></ticketPreferences></clientPrefTuple><clientPrefTuple>
*/

	/**
	 *	getNextTag starts from the current position as indicated by 'pos',
	 *	finds the next tag and returns its name (tag ID).
	 *
	 */
	private String getNextTag(){ //throws EOSException{
		int tagStart = msg.indexOf("<", pos);
		int tagEnd = msg.indexOf(">", pos);

		String tagID = null;
		if ( tagEnd != -1 ) {
			tagID = msg.substring(tagStart+1, tagEnd);

			//  advance the current string position to the NEXT char to be read,
			//	store value read as the last string read.
			pos = tagEnd+1;
			lastRead = tagID;
		}
		else {

                    hasNext = false;
                    tagID = "!error!";
                    //throw new EOSException();

		}

                return tagID;
        }


	/**
	 *	getNextValue starts from the current position as indicated by 'pos',
	 *	and returns the next tag value.
	 *
	 */
	private String getNextValue(String tagName) {
		int startPos = pos;
		// int endPos = msg.indexOf("</", pos);
                String endStr = "</"+tagName+">";
                int endPos = msg.indexOf(endStr, pos);

		String value;

		if ( endPos != -1) {
			value = msg.substring(startPos, endPos);

			//  advance the current string position to the NEXT char to be read,
			//	store value read as the last string read.
			pos = endPos;
			lastRead = value;
		}
		else {
			value = "!error!";
		}

		return value;
	}

       /**
        *   This method counts the number of occurrencies of a specified
        *    tag in the XML stream.
        */
       public int getTagCount(String tagName) {

           int tagCount = 0;
           boolean endOfFile = false;

           reset(); //resets the pointer to the begining of xml string

            while (endOfFile == false) {

               if (hasNext) {
                   if (tagName.equalsIgnoreCase(getNextTag())) {
                       tagCount++;
                   }
                } else endOfFile = true;

          }

           reset();//resets the pointer to the beginning of xml string

             return tagCount;
       }

       /**
        *This method resets all the counter variables to their original states
        *that is you get brand new parser object that can be used and reused
        *as far as you have called reset method between all use
        */

       public void reset() {

            pos = 0;                    // current position in the XML string
            lastRead = null;            // the last string (tag ID or value) read
            hasNext = true;
            tagFound = false;

       }

        //to check that there we have not reached the end of the xml file
        public boolean hasNext(){
             return hasNext;
         }


	/**
	 *	getNextTagValue finds the next tag matching the argument, and returns
	 *	its value. It calls the methods getNextTag() and getNextValue()
	 *
	 */
	public String getNextTagValue(String tagName) throws TagNotFoundException {

            String value=null;
            if ( tagName.equals(lastRead) ) {
                value = getNextValue(tagName);

            } else {

                boolean stop = false;
                String nextTag;

                do {

                    if (hasNext){
                        nextTag = getNextTag();

                        if (nextTag.equalsIgnoreCase(tagName)) {

                            value = getNextValue(tagName);
                            tagFound = true;//telling the computer that we found tag and therefore should not throw exception
                            stop = true;

                        } else if (nextTag.equals("!error!") ) {
                            stop = true;
                            //value = "!error!";
                        }

                    } else if (tagFound==false){
                        throw new TagNotFoundException("could not find " + tagName);
                    }

                } while (!stop);
            }

		return value;
	}


	public int getNextTagValueInt(String tagName) {
            String value ;
            try {
                value = getNextTagValue( tagName );
            } catch (TagNotFoundException e) {
                System.out.println(e.getMessage());
		value = "!error!";
            }
            if (!value.equals("!error!") ) {
                int i = -1;
                try {
                    i = Integer.parseInt( value );
                }catch (Exception e) {
                    System.out.println("Error while converting message value to int");
                }
                return i;
            }else // error
                return -999;
        }


	public long getNextTagValueLong(String tagName) {
	String value;

		try {
		 value = getNextTagValue( tagName );

		}catch (TagNotFoundException e){

			System.out.println(e.getMessage());
			value = "!error!";
		}

		if (!value.equals("!error!") ) {
			long i = -1;
			try {
				i = Long.parseLong( value );
			}
			catch (Exception e) {
				System.out.println("Error while converting message value to long");
			}

			return i;
		}
		else // error
			return -999;

	}

	public float getNextTagValueFloat(String tagName) {
            String value;
            try {
                value = getNextTagValue( tagName );
            } catch (TagNotFoundException e) {
                System.out.println(e.getMessage());
		value = "!error!";
            }
            if (!value.equals("!error!") ) {
                float i = (float) -1.0;
		try {
                    i = Float.parseFloat( value );
		} catch (Exception e) {
                    System.out.println("Error while converting message value to long");
		}
		return i;
            } else // error
		return -999;
	}

	/**
	 *	Finds and returns the value of the tag specified. It returns the value
	 *	of the FIRST matching tag it finds. It does NOT use or advance the
	 *	position 'pos'.
	 */
	public String getTagValue( String tag ) throws TagNotFoundException {

            String value = null;
            boolean pass = true;

            String searchStr = "<" + tag + ">";
            int part = msg.indexOf(searchStr);
            if (part >= 0  && part < end) {

                int startPos = msg.indexOf(">", part);
                int endPos = msg.indexOf("</"+ tag +">", part);

                if (endPos > 0 && endPos <= end) {
                    value = msg.substring(startPos + 1, endPos);

                } else throw new TagNotFoundException("Can not find end tag of " + tag);

            } else throw new TagNotFoundException("cannot find " + tag);

            return value;
	}


	int getTagValueInt(String tagName) {

            String value = null;
            try {
		value = getTagValue( tagName );

            }catch (TagNotFoundException e){
             System.out.println("Caught tag not found");
            }

		int i = -1;

		try {
			i = Integer.parseInt( value );
		}
		catch (Exception e) {
			System.out.println("Error converting " + tagName + " value to Int");
		}

		return i;
	}


	public float getTagValueFloat(String tagName) {
            String value = null;

            try {
		value = getTagValue( tagName );

            }catch (TagNotFoundException e){
             System.out.println("Caught tag not found");
            }

		float i = -1;
		try {
			i = Float.parseFloat( value );
		}
		catch (Exception e) {
			System.out.println("Error converting " + tagName + " value to float");
		}

		return i;
	}


	public long getTagValueLong(String tagName) {
            String value = null;

            try {
		value = getTagValue( tagName );

            }catch (TagNotFoundException e){
             System.out.println("Caught tag not found");
            }
		long i = -1;
		try {
			i = Long.parseLong( value );
		}
		catch (Exception e) {
			System.out.println("Error converting " + tagName + " value to long");
		}

		return i;
	}

}




/*
// old code
 //    	private String getNextValue() {
                int startPos = pos;
                int endPos = msg.indexOf("</", pos);

                String value;

                if ( endPos != -1) {
                        value = msg.substring(startPos, endPos);

                        //  advance the current string position to the NEXT char to be read,
                        //	store value read as the last string read.
                        pos = endPos;
                        lastRead = value;
                }
                else {
                        value = "!error!";
                }

                return value;
        }
*/


