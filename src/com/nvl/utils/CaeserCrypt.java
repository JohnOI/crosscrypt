/*
 *	CaeserCrypt.java
 *
 *
 */

package com.nvl.utils;

///**
// *	Class CaeserCrypt contains the caeser shift encryption and decryption
// *	methods.
// *
// *	@author Olumide
// */


/**
 * Class CaeserCrypt contains the caeser shift encryption and decryption
 * methods.
 * 
 * @author JohnOI
 */
public class CaeserCrypt {

	// the shift key
	private int key = 0;

	public CaeserCrypt ( int k ) {
		if ( k < 256 )
			key = k;
	}

	/**
	 * encrypt is the caeser shift encryption implementation.
	 *
	 *	@param pstr is the string to be encrypted
	 *	@return the encrypted string .
	 */
	public String encrypt ( String pstr) {
		// Buffer string to hold encrypted string
		StringBuffer str = new StringBuffer();
		char oldchar;
		char newchar;

		for (int i = 0; i< pstr.length(); i++) {
			oldchar = pstr.charAt(i);
			newchar = (char) ( ( ((int)oldchar) + key ) % 256 );

			// write to string
			str.append(newchar);

		}

		return str.toString();
	}

	/**
	 * encrypt is the caeser shift decryption implementation.
	 *
	 *	@param pstr is the string to be decrypted
	 *	@return the decrypted string .
	 */
	public String decrypt ( String cstr) {
		// Buffer string to hold decrypted string
		StringBuffer str = new StringBuffer();
		char oldchar;
		char newchar;

		for (int i = 0; i< cstr.length(); i++) {
			oldchar = cstr.charAt(i);
			newchar = (char) (  (((int)oldchar) + (256 - key) ) % 256 );

			// write to string
			str.append(newchar);
		}

		return str.toString();
	}

        /*
         *encryptByteArray is the caeser shift encryption implementation
         *
         * @param byteArr is the byte array to be encrypted
         * @return the encrypted byteArr
         */
        public byte[] encryptByteArray(byte[] byteArr){
            int num;
            for(int i=0; i<byteArr.length; i++){
                 num = new Byte(byteArr[i]).intValue();
                 num = encryptByte(num);
                 byteArr[i] = new Integer(num).byteValue();
            }
            return byteArr;
        }

        /*
         *decryptByteArray is the caeser shift decryption implementation
         *
         * @param byteArr is the byte array to be decrypted
         * @return the decrypted byteArr
         */
        public byte[] decryptByteArray(byte[] byteArr){
            int num;
            for(int i=0; i<byteArr.length; i++){
                num = new Byte(byteArr[i]).intValue();
                // decrypt a byte
                num = decryptByte(num);
                byteArr[i] = new Integer(num).byteValue();
            }
            return byteArr;
        }

        /**
         * encryptByte encrypts a byte of data
         * @param intValue int
         * @return int
         */
        public int encryptByte(int intValue){
            return (intValue + key)%256;
        }

        /**
         * decryptByte decrypts a byte of data
         * @param intValue int
         * @return int
         */
        public int decryptByte(int intValue){
            return (intValue + (256 - key) ) % 256;
        }


} // end class CaeserCrypt
