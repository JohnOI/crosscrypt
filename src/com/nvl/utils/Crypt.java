/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nvl.utils;

/**
 *
 * @author JohnOI
 */
public abstract class Crypt {
    
    protected byte[] keyData;

    public abstract byte[] generateSecretKey( int keySize );    
    
    public abstract String generateKey( int keySize );    
    
    public abstract byte[] encrypt(byte[] bytes);
    
    public abstract byte[] decrypt(byte[] bytes);
    
    public abstract String encrypt( String inString );
    
    public abstract String decrypt( String inString );    
    
    public abstract String doCrypt(int operation, String inString);    
    
//    public abstract int encryptByte(int intValue);
//    public abstract int decryptByte(int intValue);    

    // setters and getters
    public abstract String getKey();

    public abstract void setKey(String key);
    
    public byte[] getKeyData() {
        return keyData;
    }

    public void setKeyData(byte[] keyData) {
        this.keyData = keyData;
    }
        
    
}
