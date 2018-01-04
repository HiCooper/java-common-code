package com.berry.utils.encrypt;

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


    public static void main(String[] args) {
        String msg = "bj_simsys";
        //key长度必须为32位
        String key = "1A2B3C4D5E6F78901234A5B68E9F0";
        System.out.println("【加密前】：" + msg);

        //加密
        byte[] secretArr = ThreeDESecbUtil.encrypt(key, msg.getBytes());
        System.out.println("【加密后】：" + ConvertUtil.bytesToHexString(secretArr));

        //解密
        byte[] myMsgArr = ThreeDESecbUtil.decryptMode(key, secretArr);
        System.out.println("【解密后】：" + new String(myMsgArr));
    }

    /**
     * 加密
     *
     * @param enKey
     * @param src
     * @return
     */
    public static byte[] encrypt(String enKey, byte[] src) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(enKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
            securekey = keyFactory.generateSecret(dks);
            // 实例化负责加密/解密的Cipher工具类
            Cipher c1 = Cipher.getInstance(Algorithm);
            // 初始化为加密模式
            c1.init(Cipher.ENCRYPT_MODE, securekey);
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
     * 解密
     *
     * @param enKey
     * @param src
     * @return
     */
    public static byte[] decryptMode(String enKey, byte[] src) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(enKey.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);
            securekey = keyFactory.generateSecret(dks);

            Cipher c = Cipher.getInstance(Algorithm);
            // 初始化为解密模式
            c.init(Cipher.DECRYPT_MODE, securekey);
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
}
