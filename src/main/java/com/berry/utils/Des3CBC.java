package com.berry.utils;


import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class Des3CBC {
    private static IvParameterSpec iv = null;

    public static IvParameterSpec ivDes3CBC(byte[] key) {
        try {
            // DES算法要求有一个可信任的随机数源
            //此类提供加密的强随机数生成器 (RNG)。许多实现都是伪随机数生成器 (PRNG) 形式
            SecureRandom random = new SecureRandom();
            byte seed[] = random.generateSeed(8);//调用 generateSeed 方法来生成给定的种子字节数
            iv = new IvParameterSpec(seed);
            System.out.println("十六进制的iv的随机数的ASCII:" + byteToHexASICC(seed));
            return iv;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 加密数据
    public static byte[] encryptDes3CBC(byte[] message, byte[] key) {
        try {
            //SecretKey secretKey = new SecretKeySpec(key, "DESede"); //密钥默认24位
            DESedeKeySpec dks = new DESedeKeySpec(key);//密钥32位
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);

            //encrypt
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
            // 现在，获取数据并加密
            // 正式执行加密操作
            byte[] encryptedData = cipher.doFinal(message);
            return encryptedData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解密密数据
    public static byte[] decryptDes3CBC(byte[] message, byte[] key) {
        try {
            //SecretKey secretKey = new SecretKeySpec(key, "DESede"); //密钥默认24位
            DESedeKeySpec dks = new DESedeKeySpec(key);//密钥32位
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);

            //decrypt
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
            // 现在，获取数据并加密
            // 正式执行加密操作
            byte[] decryptPlainText = cipher.doFinal(message);
            return decryptPlainText;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byteToHexASICC(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
//		// 添加新安全算法,如果用JCE就要把它添加进去
//		//Security.addProvider(new com.sun.crypto.provider.SunJCE());

        byte[] key = "1A2B3C4D5E6F78901234A5B6C7D8E9F0".getBytes();
        byte[] username = "bj_simsys".getBytes();

        ivDes3CBC(key);//一次加解密所用的随机数必须是同一个
        // IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
        byte[] encryptPlainText = encryptDes3CBC(username, key);
        System.out.println("加密_3des_cbc: " + byteToHexASICC(encryptPlainText));

        byte[] decryptPlainText = decryptDes3CBC(encryptPlainText, key);
        System.out.println("解密_3des_cbc: " + new String(decryptPlainText));

    }


    //完整方法
  /*  public static void main(String[] args) throws Exception {
//		// 添加新安全算法,如果用JCE就要把它添加进去
//		//Security.addProvider(new com.sun.crypto.provider.SunJCE());


    	byte[] key = "1A2B3C4D5E6F78901234A5B6C7D8E9F0".getBytes();
        byte[] username = "bj_simsys".getBytes();
        IvParameterSpec iv = null;

       // SecretKey secretKey = new SecretKeySpec(key, "DESede"); //密钥默认24位
        DESedeKeySpec dks = new DESedeKeySpec(key);//密钥32位
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		byte seed[] = random.generateSeed(8);//调用 generateSeed 方法来生成给定的种子字节数
	    IvParameterSpec iv = new IvParameterSpec(seed);

		//encrypt
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
        byte[] encryptedData = cipher.doFinal(username);
        System.out.println("encrypt_3des_cbc: " + byteToHexString(encryptedData));

		//decrypt
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
        byte[] decryptPlainText = cipher.doFinal(encryptedData);
        System.out.println("encrypt_3des_cbc22: " + new String(decryptPlainText));
	}*/

}
