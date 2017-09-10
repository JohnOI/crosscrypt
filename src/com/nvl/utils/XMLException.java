
package com.nvl.utils;

 /*
 *A generic exception used for unaticipated XML exception
 */

 public class XMLException extends Exception {

 	String message = null;

        public XMLException (String message) {
            this.message = message;
        }

 	public String getMessage(){
		return message;
	}
 }
