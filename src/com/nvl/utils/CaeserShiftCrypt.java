/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nvl.utils;

/**
 *
 * @author JohnOI
 */
public class CaeserShiftCrypt extends Crypt {
    // LIMITATION. The byte based methods will only work correctly with ASCII characters,
    // i.e. an 8-bit character set.
    
    // @todo: should it be mod 256 or mod 128? confirm

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
        
        StringBuffer str = new StringBuffer();
        char oldchar;
        char newchar;
//        int shiftKey = Integer.parseInt(key);
        // get the first byte as an integer        
        int shiftKey = this.getKeyData()[0];
        
        // if shift key does not fall into the range 0...256, set it to 256
        // should be max of 128? i.e. range of 0..128?
        if (  ! (( shiftKey >= 0 ) && ( shiftKey <= 256 )) ) {
            shiftKey = 256;
        }

        for (int i = 0; i< inString.length(); i++) {
            oldchar = inString.charAt(i);
            newchar = (char) ( ( ((int)oldchar) + shiftKey ) % 256 );
            // write to string
            str.append(newchar);
        }

        return str.toString();    
    }

    @Override
    public String decrypt(String inString) {
        
        // Buffer string to hold decrypted string
        StringBuffer str = new StringBuffer();
        char oldchar;
        char newchar;
//        int shiftKey = Integer.parseInt(key);
        // get the first byte of keyData as an integer
        int shiftKey = this.getKeyData()[0];        
        
        // if shift key does not fall into the range 0...256, set it to 256        
        if (  ! (( shiftKey >= 0 ) && ( shiftKey <= 256 )) ) {
            shiftKey = 256;
        }        
        
        for (int i = 0; i< inString.length(); i++) {
                oldchar = inString.charAt(i);
                newchar = (char) (  (((int)oldchar) + (256 - shiftKey) ) % 256 );
                // write to string
                str.append(newchar);
        }

        return str.toString();
    }
    
    

    @Override
    public byte[] encrypt(byte[] bytes) {
        return encryptByteArray(bytes);
    }

    @Override
    public byte[] decrypt(byte[] bytes) {
        return decryptByteArray(bytes);
    }    
    
    /*
     *encryptByteArray is the caeser shift encryption implementation
     *
     * @param byteArr is the byte array to be encrypted
     * @return the encrypted byteArr
     */
    public byte[] encryptByteArray(byte[] byteArr){
        byte num;
        for(int i=0; i<byteArr.length; i++){
             num = byteArr[i];
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
        byte num;
        for(int i=0; i<byteArr.length; i++){
            num = byteArr[i];
            // decrypt a byte
            num = decryptByte(num);
            byteArr[i] = new Integer(num).byteValue();
        }
        return byteArr;
    }    

//    @Override
    public byte encryptByte(byte value) {
        byte shiftKey = this.getKeyData()[0];         
        return (byte) ((value + shiftKey) % 256);
    }

//    @Override
    public byte decryptByte(byte value) {
        byte shiftKey = this.getKeyData()[0];
        return (byte) ((value + (256 - shiftKey) ) % 256);
    }

    @Override
    public String generateKey( int keySize ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public byte[] generateSecretKey(int keySize) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getKey() {
        int shiftKey = this.getKeyData()[0];
        return new Integer(shiftKey).toString();
    }

    @Override
    public void setKey(String key) {
        Integer shiftKey = Integer.parseInt(key);
        byte[] keyByte = new byte[1];
        keyByte[0] = shiftKey.byteValue();
        this.setKeyData(keyByte);

    }

    
}
