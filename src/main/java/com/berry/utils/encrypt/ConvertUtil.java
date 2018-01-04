package com.berry.utils.encrypt;

import com.berry.utils.valid;

import java.io.ByteArrayOutputStream;

/**
 * 常用类型转换
 */
public final class ConvertUtil {

    private final static String hexStr = "0123456789ABCDEF";

    /**
     * 短整型与字节的转换
     */
    public final static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            // 将最低位保存在最低位
            b[i] = new Integer(temp & 0xff).byteValue();
            // 向右移8位
            temp = temp >> 8;
        }
        return b;
    }

    /**
     * 字节的转换与短整型
     */
    public final static short byteToShort(byte[] b) {
        short s;
        // 最低位
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    /**
     * 整型与字节数组的转换
     */
    public final static byte[] intToByte(int i) {
        byte[] bt = new byte[4];
        bt[0] = (byte) (0xff & i);
        bt[1] = (byte) ((0xff00 & i) >> 8);
        bt[2] = (byte) ((0xff0000 & i) >> 16);
        bt[3] = (byte) ((0xff000000 & i) >> 24);
        return bt;
    }

    /**
     * 整型数组转换为字节数组的转换
     *
     * @param arr 整型数组
     */
    public final static byte[] intToByte(int[] arr) {
        byte[] bt = new byte[arr.length * 4];
        for (int i = 0; i < arr.length; i++) {
            byte[] t = intToByte(arr[i]);
            System.arraycopy(t, 0, bt, i + 4, 4);
        }
        return bt;
    }

    public final static byte[] encodeBytes(byte[] source, char split) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(source.length);
        for (byte b : source) {
            if (b < 0) {
                b += 256;
            }
            bos.write(split);
            char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
            char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
            bos.write(hex1);
            bos.write(hex2);
        }
        return bos.toByteArray();
    }

    /**
     * bytes数组转char数组
     * bytes to chars
     *
     * @param bytes bytes数组
     */
    public final static char[] bytesToChars(byte[] bytes) {
        char[] chars = new char[]{};
        if (valid.valid(bytes)) {
            chars = new char[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                chars[i] = (char) bytes[i];
            }
        }
        return chars;
    }

    /**
     * 字节数组和整型的转换
     */
    public final static int bytesToInt(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }

    /**
     * 字节数组和长整型的转换
     */
    public final static byte[] longToByte(long number) {
        long temp = number;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();
            // 将最低位保存在最低位
            temp = temp >> 8;
            // 向右移8位
        }
        return b;
    }

    /**
     * 字节数组和长整型的转换
     */
    public final static long byteToLong(byte[] b) {
        long s;
        // 最低位
        long s0 = b[0] & 0xff;
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        // 最低位
        long s4 = b[4] & 0xff;
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        // s0不变
        long s7 = b[7] & 0xff;
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    /**
     * 将16进制字符串转换为二进制字符数组
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将十六进制字符串转为二进制字符串
     *
     * @param hexStr 十六进制字符串
     */
    public final static String hexStringtoBinarg(String hexStr) {
        hexStr = hexStr.replaceAll("\\s", "").replaceAll("0x", "");
        char[] achar = hexStr.toCharArray();
        String result = "";
        for (char a : achar) {
            result += Integer.toBinaryString(
                    Integer.valueOf(String.valueOf(a), 16)) + " ";
        }
        return result;
    }

    /**
     * 将byte[]的字节数组转ASICC码
     *
     * @param bytes 字节数组
     * @return ASICC码(也是16进制)
     */
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

    /**
     * 功能:将十六进制的char转换为十进制的int值
     *
     * @param ch 十六进制的char
     * @return 十进制 int值
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
     * 将二进制转换为十六进制字符串输出
     *
     * @param bytes bytes数组
     */
    public final static String bytesToHexString(byte[] bytes) {
        String result = "";
        String hex;
        for (byte b : bytes) {
            //字节高4位
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex;
        }
        return result;
    }

    /**
     * 把16进制字符串转换成字节数组
     *
     * @param hexString 16进制字符串
     * @return byte[]
     */
    public final static byte[] hexStringToByte(String hexString) {
        int len = (hexString.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hexString.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private final static int toByte(char c) {
        return (byte) hexStr.indexOf(c);
    }


    /**
     * 转换数据
     */
    private static final char[] HEXDIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 功能:将byte[]的转换为相应的十六进制字符串
     *
     * @param ba 字节数组
     * @return 十六进制字符串
     */
    public static String bytetohex(byte[] ba, int offset, int length) {
        char[] buf = new char[length * 2];
        int j = 0;
        int k;
        for (int i = offset; i < offset + length; i++) {
            k = ba[i];
            buf[j++] = HEXDIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEXDIGITS[k & 0x0F];
        }
        return new String(buf);
    }


    /**
     * ----------------------------------------------------------------------------------------
     * 华丽分割线
     * ----------------------------------------------------------------------------------------
     */

    /**
     * 功能:将十六进制字符串转换为字节数组
     *
     * @param hex 十六进制字符串
     * @return 字节数组
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
     * ----------------------------------------------------------------------------------------
     * 华丽分割线
     * ----------------------------------------------------------------------------------------
     */

    /**
     * 功能:字符串转换为十六进制ASCII
     *
     * @param str 普通字符串，如：bj_simsys
     * @return 十六进制ASCII
     */
    public static String parseAscii(String str) {
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();
        for (int i = 0; i < bs.length; i++) {
            sb.append(toHex(bs[i]));
        }
        return sb.toString();
    }

    private static String toHex(int n) {
        StringBuilder sb = new StringBuilder();
        if (n / 16 == 0) {
            return toHexUtil(n);
        } else {
            String t = toHex(n / 16);
            int nn = n % 16;
            sb.append(t).append(toHexUtil(nn));
        }
        return sb.toString();
    }

    private static String toHexUtil(int n) {
        String rt = "";
        switch (n) {
            case 10:
                rt += "A";
                break;
            case 11:
                rt += "B";
                break;
            case 12:
                rt += "C";
                break;
            case 13:
                rt += "D";
                break;
            case 14:
                rt += "E";
                break;
            case 15:
                rt += "F";
                break;
            default:
                rt += n;
        }
        return rt;
    }

    /**
     * ----------------------------------------------------------------------------------------
     * 华丽分割线
     * ----------------------------------------------------------------------------------------
     */

    private static byte[] hexToByte(String str) {
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[str.length() / 2];
        for (int n = 0; n < str.length(); n += 2) {
            String item = str.substring(n, n + 2);

            b2[(n / 2)] = ((byte) Integer.parseInt(item, 16));
        }
        return b2;
    }

    /**
     * ----------------------------------------------------------------------------------------
     * 华丽分割线
     * ----------------------------------------------------------------------------------------
     */

    /**
     * 转换成十六进制字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }
        return hs.toUpperCase();
    }
}
