
package com.nvl.utils;

/*
 *This exception is thrown by the getNextTag() method
 */
public class EOSException extends Exception {

	public String getMessage() {
		return "The end of the XML file has been reached.";
	}
 }
