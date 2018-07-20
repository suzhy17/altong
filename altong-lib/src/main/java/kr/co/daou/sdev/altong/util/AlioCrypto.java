/**
 * Copyright 2016 DAOU TECH Inc. All right reserved.
 **/
package kr.co.daou.sdev.altong.util;
/**

 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AlioCrypto {

  /**
   * 자체 config의 password를 암호화 할 키
   * KEY = ewin
   * IV  = bizclient&server
   */
  public static final byte[] PubCONST_KEY = { 0x65, 0x77, 0x69, 0x6E };
  public static final byte[] PubCONST_IV  = { 0x62, 0x69, 0x7A, 0x63, 0x6C, 0x69, 0x65, 0x6E, 0x74, 0x26, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72 };

  /**
   * 서버로 보낼 password를 암호화할 키정
   * KEY = $13bizppurio@237
   * IV  = bizclient&server
   */
  public static final byte[] PubSrvCONST_KEY = { 0x24, 0x31, 0x33, 0x62, 0x69, 0x7A, 0x70, 0x70, 0x75, 0x72, 0x69, 0x6F, 0x40, 0x32, 0x33, 0x37 };
  public static final byte[] PubSrvCONST_IV  = { 0x62, 0x69, 0x7A, 0x63, 0x6C, 0x69, 0x65, 0x6E, 0x74, 0x26, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72 };

  /**
   * 서버로 보낼 password를 암호화할 키정
   * KEY = $13bizppurio@237
   * IV  = bizclient&server
   */
  public static final byte[] msgSrvConst_KEY = {0x24 ,0x31,0x31,0x62,0x69,0x7A,0x73,0x65,0x72,0x76,0x65,0x72,0x31,0x33,0x26,0x3F};
  public static final byte[] msgSrvConst_IV = {0x26,0x38,0x33,0x63,0x6C,0x69,0x65,0x6E,0x74,0x62,0x69,0x7A,0x31,0x33,0x24,0x21};
  public AlioCrypto() {
  }

  public static String stringToHex(String s) {
    String result = "";
    for (int i = 0; i < s.length(); i++) {
      result += Integer.toHexString((int)s.charAt(i));
    }
    return result;
  }

  public static byte[] hexToByteArray(String hex) {
	if (hex == null || hex.length() == 0) {
      	return null;
	}
	byte[] ba = new byte[hex.length() / 2];
	for (int i = 0; i < ba.length; i++) {
		ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	}
	return ba;
  }

  // byte[] to hex
  public static String byteArrayToHex(byte[] ba) {
	if (ba == null || ba.length == 0) {
		return null;
	}

    StringBuffer sb = new StringBuffer(ba.length * 2);
    String hexNumber;
    for (int x = 0; x < ba.length; x++) {
    	hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
        sb.append(hexNumber.substring(hexNumber.length() - 2));
	}
    return sb.toString();
  }

  public static String convert_to_ksc(String str) {
    String result = null;
    try {
    	byte[] kscBytes = str.getBytes("8859_1");
        result = new String(kscBytes, "KSC5601");
	} catch(Exception e) {}
    return result;
  }

  /**
	 * InKey와 Const Key 값을 조합하여 InValue 값을 암호화하여 리
	 *
	 * @param InKey	: ConstKey 값과 조합될 값
	 * @param InValue : 암호화 대상값
	 *
	 * @return String 암호화 된 문자열
	 */
  public static String Encrypt(String InKey, String InValue) throws Exception {

    if (InValue == null) return null;
    if (InKey == null) return InValue;

    String LS_Encrypt = null;

    InKey = byteArrayToHex(InKey.getBytes());
    String pubKey = byteArrayToHex(AlioCrypto.PubCONST_KEY);
    byte[] keyString = hexToByteArray(pubKey + InKey) ;
    byte[] iv= AlioCrypto.PubCONST_IV;
    IvParameterSpec ivSpec = new IvParameterSpec(iv);

    SecretKeySpec key = new SecretKeySpec(keyString, "AES");

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
    byte[] LB_EncryptValue = cipher.doFinal(InValue.getBytes());
    LS_Encrypt = byteArrayToHex(LB_EncryptValue);

    return LS_Encrypt;
  }

  /**
	 * ConstKey 값을 이용하여 InValue 값을 암호화하여 리턴
	 *
	 * @param InValue : 암호화 대상값
	 *
	 * @return String 암호화 된 문자열
	 */
  public static String EncryptSrv( String InValue ) throws Exception {

	if (InValue == null) return null;

	String LS_Encrypt = null;

	int tail = InValue.length()%16;
	String trimchar = "";
	for( int i=0; i < 16-tail ; i++){
		trimchar = trimchar + " ";
	}
	InValue = InValue + trimchar;

	byte[] keyString = AlioCrypto.PubSrvCONST_KEY;
    byte[] iv= AlioCrypto.PubSrvCONST_IV;
	IvParameterSpec ivSpec = new IvParameterSpec(iv);
    SecretKeySpec key = new SecretKeySpec(keyString, "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

	cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
	byte[] LB_EncryptValue = cipher.doFinal(InValue.getBytes());
	LS_Encrypt = byteArrayToHex(LB_EncryptValue);

	return LS_Encrypt;
  }
  
  /**
	 * ConstKey 값을 이용하여 InValue 값을 암호화하여 리턴
	 *
	 * @param InValue : 암호화 대상값
	 *
	 * @return String 암호화 된 문자열
	 */
public static String MsgEncryptSrv( String InValue ) throws Exception {

	if (InValue == null) return null;

	String LS_Encrypt = null;
		
	int tail = InValue.getBytes("euc-kr").length%16;
	String trimchar = "";
	for( int i=0; i < 16-tail ; i++){
		trimchar = trimchar + " ";
	}
	InValue = InValue + trimchar;
	
	byte[] keyString = AlioCrypto.msgSrvConst_KEY;
    byte[] iv= AlioCrypto.msgSrvConst_IV;
	IvParameterSpec ivSpec = new IvParameterSpec(iv);
    SecretKeySpec key = new SecretKeySpec(keyString, "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

	cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

	byte[] LB_EncryptValue = cipher.doFinal(InValue.getBytes("euc-kr"));
	LS_Encrypt = byteArrayToHex(LB_EncryptValue);
	return LS_Encrypt;
}

/**
 * ConstKey 값을 이용하여 InValue 값을 암호화하여 리턴
 *
 * @param InValue : 암호화 대상값
 *
 * @return String 암호화 된 문자열
 */
public static String FileEncryptSrv( byte[] InValue, int Len ) throws Exception {

	if (InValue == null) return null;
	
	String LS_Encrypt = null;

	int tail = Len %16;
	byte[] trimchar = new byte[16-tail];
	
	for( int i=0; i < 16-tail ; i++){
		trimchar[i] = (byte)0x20;
	}	
	
	byte[] newInValue = new byte[Len + trimchar.length];
	System.arraycopy(InValue, 0, newInValue, 0, Len);	
	System.arraycopy(trimchar, 0, newInValue, Len, trimchar.length);
	
	byte[] keyString = AlioCrypto.msgSrvConst_KEY;
	byte[] iv= AlioCrypto.msgSrvConst_IV;
	IvParameterSpec ivSpec = new IvParameterSpec(iv);
	SecretKeySpec key = new SecretKeySpec(keyString, "AES");
	Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	
	cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

	byte[] LB_EncryptValue = cipher.doFinal(newInValue);
	LS_Encrypt = byteArrayToHex(LB_EncryptValue);
	return LS_Encrypt;
}

/**
	 * 암호화의 사용할 InKey 값을 생성한다.
	 *
	 * @param id : InKey 생성에 사용할 id
	 *
	 * @return String InKey
	 */
  public static String makeInKey( String id ) throws Exception {
	String InKey = "";
	
	if( id.length() >= 13 ) {
	  InKey = id.substring(0, 12);
	} else {
	  String trimchar = "";
	  for( int i=0; i < 12-id.length(); i++){
		trimchar = trimchar + "#";
	  }
	  InKey = id + trimchar;
	}
	return InKey;
  }
  
  /**
	 * ConstKey 값을 이용하여 InValue 값을 복호화하여 리턴
	 *
	 * @param InValue : 복호화 대상값
	 *
	 * @return String 복호화 된 문자열
	 */
public static String MsgDecryptSrv(String InValue) throws Exception {

	  if (InValue == null) return null;
	
	  String LS_Decrypt = null;
	
	  byte[] keyString = AlioCrypto.msgSrvConst_KEY;
	  byte[] iv = AlioCrypto.msgSrvConst_IV;
	
	  IvParameterSpec ivSpec = new IvParameterSpec(iv);
	  SecretKeySpec key = new SecretKeySpec(keyString, "AES");
	
	  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	
	  cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
	  byte[] LB_Value = hexToByteArray(InValue);
	  byte[] LB_DecryptValue = cipher.doFinal(LB_Value);
	  LS_Decrypt = new String(LB_DecryptValue);
	  System.out.println("DecryptSrv/decrypted : ["+byteArrayToHex(LB_DecryptValue).toString()+"]");
	  System.out.println("DecryptSrv/destination : ["+LS_Decrypt+"]");
	
	  return LS_Decrypt;
}

/**
 * ConstKey 값을 이용하여 InValue 값을 복호화하여 리턴
 *
 * @param InValue : 복호화 대상값
 *
 * @return String 복호화 된 문자열
 */
public static byte[] FileDecryptSrv(String InValue) throws Exception {

	if (InValue == null) return null;
	
	byte[] keyString = AlioCrypto.msgSrvConst_KEY;
	byte[] iv = AlioCrypto.msgSrvConst_IV;
	
	IvParameterSpec ivSpec = new IvParameterSpec(iv);
	SecretKeySpec key = new SecretKeySpec(keyString, "AES");
	
	Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	
	cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
	byte[] LB_Value = hexToByteArray(InValue);
	byte[] LB_DecryptValue = cipher.doFinal(LB_Value);
	
	return LB_DecryptValue;
}

/**
	 * InKey와 Const Key 값을 조합하여 InValue 값을 복호화하여 리턴
	 *
	 * @param InKey	: ConstKey 값과 조합될 값
	 * @param InValue : 복호화 대상값
	 *
	 * @return String 복호화 된 문자열
	 */
  public static String Decrypt(String InKey, String InValue) throws Exception {

    if (InValue == null) return null;
    if (InKey == null) return InValue;

    String LS_Decrypt = null;

    String pubKey = byteArrayToHex(AlioCrypto.PubCONST_KEY);
    byte[] keyString = hexToByteArray(pubKey + stringToHex(InKey));
    byte[] iv = AlioCrypto.PubCONST_IV;
    IvParameterSpec ivSpec = new IvParameterSpec(iv);

    SecretKeySpec key = new SecretKeySpec(keyString, "AES");

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
    byte[] LB_Value = hexToByteArray(InValue);
    byte[] LB_DecryptValue = cipher.doFinal(LB_Value);
    LS_Decrypt = new String(LB_DecryptValue, "euc-kr");

    return LS_Decrypt;
  }

  /**
	 * ConstKey 값을 이용하여 InValue 값을 복호화하여 리턴
	 *
	 * @param InValue : 복호화 대상값
	 *
	 * @return String 복호화 된 문자열
	 */
  public static String DecryptSrv(String InValue) throws Exception {

    if (InValue == null) return null;

    String LS_Decrypt = null;

    byte[] keyString = AlioCrypto.PubSrvCONST_KEY;
    byte[] iv = AlioCrypto.PubSrvCONST_IV;

    IvParameterSpec ivSpec = new IvParameterSpec(iv);
    SecretKeySpec key = new SecretKeySpec(keyString, "AES");

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
    byte[] LB_Value = hexToByteArray(InValue);
    byte[] LB_DecryptValue = cipher.doFinal(LB_Value);
    LS_Decrypt = new String(LB_DecryptValue, "euc-kr");

    return LS_Decrypt;
  }

  /**
  * 문자열을 SHA-256 방식으로 암호화
  * @param src 암호화 하려하는 문자열
  * @return String
  * @throws Exception
  */
  	public static String toSHA256String(String src) throws NoSuchAlgorithmException {
	    StringBuffer sb = new StringBuffer();
	   
	    MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
	    msgDigest.update(src.getBytes());
	   
	    byte[] msgByteArr = msgDigest.digest() ;
	   
	    for (int i=0; i < msgByteArr.length; i++) {
	      byte tmpStrByte = msgByteArr[i];
	      String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);
	   
	      sb.append(tmpEncTxt) ;
	    }
	   
	    return sb.toString();
  	}
  
	public static String toMD5String(String src) throws NoSuchAlgorithmException {
		byte[] digest = MessageDigest.getInstance("MD5").digest(src.getBytes());
		
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<digest.length; i++) {
			sb.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
			sb.append(Integer.toString(digest[i] & 0x0f, 16));
		}
		
		return sb.toString();
	}
}