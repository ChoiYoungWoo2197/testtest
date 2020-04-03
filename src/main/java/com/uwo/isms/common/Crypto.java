package com.uwo.isms.common;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;

public class Crypto {
	//private final static String KEY = "01234567890123456789012345678901";
	private final static String KEY = "abcdefghijklmnopqrstuvwxyz012345";
	private final static String KEY_128 = KEY.substring(0, 128/8);
	private final static String KEY_256 = KEY.substring(0, 256/8);
	public static byte[] byteIv  = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	public static String decrypt(String strData) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		  byte[] byteText = Base64.decodeBase64(strData);
		  //byte[] byteText = strData.getBytes("UTF-8");
		  AlgorithmParameterSpec aps = new IvParameterSpec(byteIv);
		  SecretKeySpec sks = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
		  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		  cipher.init(Cipher.DECRYPT_MODE, sks, aps);
		  return new String(cipher.doFinal(byteText), "UTF-8");
	}

	public static String encrypt(String strData) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		  byte[] byteText = strData.getBytes("UTF-8");
		  AlgorithmParameterSpec aps = new IvParameterSpec(byteIv);
		  SecretKeySpec sks = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
		  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		  cipher.init(Cipher.ENCRYPT_MODE, sks, aps);
		  return Base64.encodeBase64String(cipher.doFinal(byteText));
	}	
	
	public static String encAes128(String string){
		try{
			
			byte[] key128Data = KEY_128.getBytes(CharEncoding.UTF_8);
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key128Data,"AES"),new IvParameterSpec(key128Data));
			
			byte[] encrypted = cipher.doFinal(string.getBytes(CharEncoding.UTF_8));
			
			byte[] base64Encoded = Base64.encodeBase64(encrypted);
			
			String result = new String(base64Encoded, CharEncoding.UTF_8);
			
			return result;
		}catch(Exception e){
			return null;
		}
	}
	
	
	public static String encAes256(String string){
		try{
			byte[] key256Data = KEY_256.getBytes(CharEncoding.UTF_8);
			byte[] key128Data = KEY_128.getBytes(CharEncoding.UTF_8);
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key256Data,"AES"),new IvParameterSpec(key128Data));
			
			byte[] encrypted = cipher.doFinal(string.getBytes(CharEncoding.UTF_8));
			
			byte[] base64Encoded = Base64.encodeBase64(encrypted);
			
			String result = new String(base64Encoded, CharEncoding.UTF_8);
			
			return result;
		}catch(Exception e){
			return null;
		}
	}
	
	public static String decAes256(String string){
		try{
			byte[] key256Data = KEY_256.getBytes(CharEncoding.UTF_8);
			byte[] key128Data = KEY_128.getBytes(CharEncoding.UTF_8);
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key256Data,"AES"),new IvParameterSpec(key128Data));
			
			byte[] base64Decoded = Base64.decodeBase64(string.getBytes(CharEncoding.UTF_8));
			
			byte[] decrypted = cipher.doFinal(base64Decoded);
			
			
			
			String result = new String(decrypted, CharEncoding.UTF_8);
			
			return result;
		}catch(Exception e){
			return null;
		}
	}
}
