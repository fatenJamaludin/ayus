package com.app.tbd.utils;

import android.util.Base64;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;

public class FlyAes
{
    private String encryptionKey;

    public FlyAes(String encryptionKey)
    {
        this.encryptionKey = encryptionKey;
    }

    public String encrypt(String plainText) throws Exception
    {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        encryptedBytes =  Base64.encode(encryptedBytes, Base64.DEFAULT);
        //return Base64.encodeBase64String(encryptedBytes);
        return new String(encryptedBytes);

    }

    public String decrypt(String encrypted) throws Exception
    {
        //Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        //byte[] plainBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
        //byte[] plainBytes = cipher.doFinal(Base64.encode(encrypted,Base64.DEFAULT));
        return null;
        //return new String(plainBytes);
    }

    private Cipher getCipher(int cipherMode)throws Exception
    {
        String encryptionAlgorithm = "AES";
        SecretKeySpec keySpecification = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), encryptionAlgorithm);
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(cipherMode, keySpecification);

        return cipher;
    }

    /*public String decrypt2(String encryptedData, String initialVectorString, String secretKey) {

        String decryptedData = null;

        try {

            SecretKeySpec skeySpec = new SecretKeySpec(md5(secretKey).getBytes(), "AES");
            IvParameterSpec initialVector = new IvParameterSpec(initialVectorString.getBytes());
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, initialVector);
            byte[] encryptedByteArray = (new org.apache.commons.codec.binary.Base64()).decode(encryptedData.getBytes());
            byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);
            decryptedData = new String(decryptedByteArray, "UTF8");

        } catch (Exception e) {
            //LOGGER.debug("Problem decrypting the data", e);
     }

        return decryptedData;

    }*/

    //public void encrypt2(String value)
    //{
        /*iv = mcrypt_create_iv($this->getIvSize(), $this->getRandomizer());
        value = base64_encode($this->padAndMcrypt(value, iv));
        mac = $this->hash(iv = base64_encode(iv), value);
        return base64_encode(json_encode(compact('iv', 'value', 'mac')));


        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        encryptedBytes =  Base64.encode(encryptedBytes, Base64.DEFAULT);
        //return Base64.encodeBase64String(encryptedBytes);
        return new String(encryptedBytes);*/

        public static String decrypt(byte[] keyValue, String ivValue, String encryptedData) throws Exception {
        //public static String decrypt(byte[] keyValue, String ivValue, String encryptedData) throws Exception {
        Key key = new SecretKeySpec(keyValue, "AES");
        byte[] iv = Base64.decode(ivValue.getBytes("UTF-8"), Base64.DEFAULT);
        byte[] decodedValue = Base64.decode(encryptedData.getBytes("UTF-8"), Base64.DEFAULT);

        Cipher c = Cipher.getInstance("AES/CBC/PKCS7Padding"); // or PKCS5Padding
        c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decValue = c.doFinal(decodedValue);

        int firstQuoteIndex = 0;
        while(decValue[firstQuoteIndex] != (byte)'"') firstQuoteIndex++;
        return new String(Arrays.copyOfRange(decValue, firstQuoteIndex + 1, decValue.length - 2));
   // }

    }

     /*   public static String decrypt3(byte[] keyValue, String ivValue, String encryptedData) throws Exception {


            //KeySpec keySpec = new PBEKeySpec("owNLfnLjPvwbQH3hUmj5Wb7wBIv83pR7", ivValue, null);

           // Key key = new SecretKeySpec(keyValue, "AES");
            //SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            byte[] iv = Base64.decode(ivValue.getBytes("UTF-8"), Base64.DEFAULT);
            byte[] decodedValue = Base64.decode(encryptedData.getBytes("UTF-8"), Base64.DEFAULT);

            Cipher c = Cipher.getInstance("AES/CBC/PKCS7Padding"); // or PKCS5Padding
            c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] decValue = c.doFinal(decodedValue);

            int firstQuoteIndex = 0;
            while(decValue[firstQuoteIndex] != (byte)'"') firstQuoteIndex++;
            return new String(Arrays.copyOfRange(decValue, firstQuoteIndex + 1, decValue.length-2));
        }*/

    public void kodahkenapa(){
        String iv = "fedcba9876543210";
        IvParameterSpec ivspec;
        KeyGenerator keygen;
        Key key;

        ivspec = new IvParameterSpec(iv.getBytes());

       // keygen = KeyGenerator.getInstance("AES");
       // keygen.init(128);
       // key = keygen.generateKey();

       // KeySpec keyspec = new SecretKeySpec(key.getEncoded(), "AES");

        Cipher cipher;
        byte[] encrypted;

       // cipher = Cipher.getInstance("AES/CBC/NoPadding");
       // cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        //encrypted = cipher.doFinal(padString(text).getBytes());
    }





}