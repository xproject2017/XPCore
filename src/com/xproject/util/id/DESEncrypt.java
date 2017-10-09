package com.xproject.util.id;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.Security;

public class DESEncrypt 
{
	private static String strDefaultKey = "national";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;
	
	/**
	 * 默认构造方法，使用默认密钥 
	 * @throws Exception
	 */
	public DESEncrypt() throws Exception
	{
		this(strDefaultKey);
	}
	
	/**
	 *  指定密钥构造方法 
	 *  @param strKey
	 *  指定的密钥 
	 *  @throws Exception
	 */
	public DESEncrypt(String strKey) throws Exception
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());
		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}
	
	/**
	 * 加密字节数组
	 * @param arrB
	 * 需加密的字节数组 
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arrB) throws Exception
	{
		return encryptCipher.doFinal(arrB);
	}
	
	/**
	 * 加密字符串
	 * @param strIn           
	 * 需加密的字符串 
	 * @return 加密后的字符串 
	 * @throws Exception
	 */
	public String encrypt(String strIn) throws Exception
	{
		return HexString.byteArr2HexStr(encrypt(strIn.getBytes()));
	}
	
	/**
	 * 解密字节数组 
	 *  需解密的字节数组
	 * @return 解密后的字节数组 
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arrB) throws Exception
	{
		return decryptCipher.doFinal(arrB);
	}
	
	/**
	 * 解密字符串
	 * 需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public String decrypt(String strIn) throws Exception
	{
		return new String(decrypt(HexString.hexStr2ByteArr(strIn)));
	}
	
	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * @param arrBTmp
	 *     构成该字符串的字节数组 
	 * @return 生成的密钥  
	 * @throws Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception
	{
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++)
		{
			arrB[i] = arrBTmp[i];
		}
		
		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}
} 











