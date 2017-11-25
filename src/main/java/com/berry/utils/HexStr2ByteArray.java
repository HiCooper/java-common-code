package com.berry.utils;

public class HexStr2ByteArray {
    /**
     * 功能:将byte[]的字节数组转ASICC码
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
    //111111111111111111111111111111111111111111111 over

    /** 转换数据 */
    private static final char[] HEXDIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    /**
     * 功能:将byte[]的转换为相应的十六进制字符串
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
    //22222222222222222222222222222222222222222222222222222222222222 over

    /**
     * 功能:将十六进制字符串转换为字节数组
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
    //33333333333333333333333333333333333333333333333333333 over

    /**
     * 功能:将十六进制的char转换为十进制的int值
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
    //444444444444444444444444444444444444444444444444444444444444 over

    /**
     * 功能:字符串转换为十六进制ASCII
     * @param str 普通字符串，如：bj_simsys
     * @return 十六进制ASCII
     */
    public static String parseAscii(String str){
        StringBuilder sb=new StringBuilder();
        byte[] bs=str.getBytes();
        for(int i=0;i<bs.length;i++){
            sb.append(toHex(bs[i]));
        }
        return sb.toString();
    }

    public static String toHex(int n){
        StringBuilder sb=new StringBuilder();
        if(n/16==0){
            return toHexUtil(n);
        }else{
            String t=toHex(n/16);
            int nn=n%16;
            sb.append(t).append(toHexUtil(nn));
        }
        return sb.toString();
    }

    private static String toHexUtil(int n){
        String rt="";
        switch(n){
            case 10:rt+="A";break;
            case 11:rt+="B";break;
            case 12:rt+="C";break;
            case 13:rt+="D";break;
            case 14:rt+="E";break;
            case 15:rt+="F";break;
            default:
                rt+=n;
        }
        return rt;
    }
    //5555555555555555555555555555555555555555555555555555 over

    //?6
    private static byte[] hexToByte(String str) {
        if (str.length() % 2 != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[str.length() / 2];
        for (int n = 0; n < str.length(); n += 2) {
            String item = str.substring(n, n + 2);

            b2[(n / 2)] = ((byte) Integer.parseInt(item, 16));
        }
        return b2;
    }
    //66666666666666666666666666666666666666666666???????????? over

    //7?
    /**
     * 转换成十六进制字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }
    //7777777777777777777777777777777777777777777777777???????????? over
}
