
package com.nvl.utils;

/*
 *This excpetion is thrown by the getNextTagValue() method
 *
 */
public class TagNotFoundException extends Exception {

         String message = null;

	 public TagNotFoundException(String msg) {
		message = msg;
	 }

	public String getMessage() {

		return message;
	}
}

