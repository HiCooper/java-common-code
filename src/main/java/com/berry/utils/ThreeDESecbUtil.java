package com.berry.utils;


import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * ThreeDESecbUtil {3DES加密解密的工具类 }
 * 32位密钥  加密方式：DESede即DESede/ECB/PKCS5Padding
 *
 * @author ly
 * @date 2017-11-15
 */
public class ThreeDESecbUtil {

    /**
     * 定义加密算法，有DES、DESede(即3DES)、Blowfish
     */
    private static final String Algorithm = "DESede";
    /**
     * 密钥
     */
    private static SecretKey securekey;

    /**
     * 加密方法
     *
     * @param src 源数据的字节数组
     * @return
     */
    public static byte[] encryptMode(String enKey, byte[] src) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(enKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
            securekey = keyFactory.generateSecret(dks);

            Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
            c1.init(Cipher.ENCRYPT_MODE, securekey); // 初始化为加密模式
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }


    /**
     * 解密函数
     *
     * @param src 密文的字节数组
     * @return
     */
    public static byte[] decryptMode(String enKey, byte[] src) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(enKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
            securekey = keyFactory.generateSecret(dks);

            Cipher c = Cipher.getInstance(Algorithm);
            c.init(Cipher.DECRYPT_MODE, securekey); // 初始化为解密模式
            return c.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 功能:将十六进制字符串转换为字节数组
     *
     * @param hex 十六进制字符串
     * @return byte 字节数组
     */
    public static byte[] hextobyteString(String hex) {
        int len = hex.length();
        byte[] buf = new byte[(len + 1) / 2];
        int i = 0;
        int j = 0;
        if ((len % 2) == 1) {
            buf[j++] = (byte) hexchartoint(hex.charAt(i++));
        }
        while (i < len) {
            buf[j++] = (byte) ((hexchartoint(hex.charAt(i++)) << 4) | hexchartoint(hex.charAt(i++)));
        }
        return buf;
    }

    /**
     * (将十六进制的char转换为十进制的int值)
     */
    public static int hexchartoint(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        }
        if (ch >= 'A' && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if (ch >= 'a' && ch <= 'f') {
            return ch - 'a' + 10;
        }
        throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
    }

    /**
     * 根据字符串生成密钥字节数组
     *
     * @param keyStr 密钥字符串
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24]; // 声明一个位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
         * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
        if (key.length > temp.length) {
            // 如果temp不够位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            // 如果temp大于位，则拷贝temp数组个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        System.out.println("【十六进制24字节-密钥】：1A2B3C4D5E6F78901234");
        // System.out.println("【ASCII-密钥】："+byteToHexString(key));

        return key;
    }

    //字节(二进制）转ASICC码
    public static String byteToHexString(byte[] bytes) {
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

    public static void main(String[] args) {
        String msg = "bj_simsys";//3DES加密解密案例
        String key = "1A2B3C4D5E6F78901234A5B6C7D8E9F0";//3DES加密解密案例
        System.out.println("【加密前】：" + msg);

        //加密
        byte[] secretArr = ThreeDESecbUtil.encryptMode(key, msg.getBytes());
        // System.out.println("【加密后】：" + new String(secretArr));
        System.out.println("【加密后】：" + byteToHexString(secretArr));

        //解密
        byte[] myMsgArr = ThreeDESecbUtil.decryptMode(key, secretArr);
        System.out.println("【解密后】：" + new String(myMsgArr));
    }
}
